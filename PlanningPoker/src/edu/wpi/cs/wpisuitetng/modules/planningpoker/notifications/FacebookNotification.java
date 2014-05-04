/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Team Bobby Drop Tables
 *******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.notifications;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.sasl.SASLDigestMD5Mechanism;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;

/**
 * This is a class that will send out facebook notifications whenever a game is
 * successfully created.
 * 
 * @author Bobby Drop Tables
 */
public class FacebookNotification {

	/** Game to get users to send facebook notifications to */
	private final Game g;
	/** Authentication information */
	private final String XMPP_HOST = "chat.facebook.com";
	private final int XMPP_PORT = 5222;
	private final String username = "wpi.suite.bdt.noreply@gmail.com";
	private final String password = "bobbytablesfb";
	private static final Logger logger = Logger.getLogger(FacebookNotification.class
			.getName());
	/**
	 * Constructs an Facebook notification for a given game
	 * 
	 * @param g
	 *            The game to notify users about.
	 */
	public FacebookNotification(Game g) {
		this.g = g;
	}

	/**
	 * Creates a connection with the Facebook Servers.
	 * 
	 * @return the connection
	 * @throws XMPPException
	 */
	public XMPPConnection login() throws XMPPException {
		// Facebook uses SASL authentication
		SASLAuthentication.registerSASLMechanism("DIGEST-MD5",
				SASLDigestMD5Mechanism.class);
		SASLAuthentication.supportSASLMechanism("DIGEST-MD5", 0);

		// Connect to facebook servers using XMPP
		ConnectionConfiguration config = new ConnectionConfiguration(XMPP_HOST,
				XMPP_PORT);
		XMPPConnection connection = new XMPPConnection(config);
		connection.connect();
		connection.login(username, password);

		return connection;
	}

	/**
	 * Sends facebook notifications to all users involved in the game's project
	 * team.
	 */
	public void sendFacebookNotifications() {

		// Get the users that are expected to play the game
		User[] users = null;
		// Make sure there is a project and a team before
		// setting the users to the team
		try {
			users = g.getProject().getTeam();
		} catch (NullPointerException e) {
			e.printStackTrace();
			logger.log(Level.WARNING,"Could not set the list 'users' to the list 'team'.");
		}

		// Make sure there are users in the team
		if (users[0] != null) {
			// Try to connect to facebook servers
			XMPPConnection connection = null;
			try {
				connection = login();
			} catch (XMPPException e) {
				logger.log(Level.WARNING,"Could not connect to Facebook with given login.");
				e.printStackTrace();
			}

			// Send facebook notifications
			for (int i = 0; i < users.length; i++) {
				// Make sure users have a username stored
				if (users[i].getNotificationPreferences().contains("F")) {
					if (users[i].getFacebookUsername() != null && !users[i].getFacebookUsername().equals("") ) {
						sendFacebookNotification(connection, users[i]);
					} else {
						logger.log(Level.WARNING, "User doesn't have a facebook Username Stored.");
					}
				} else {
					logger.log(Level.WARNING, "User doesn't want to receive facebook notifications");
				}
			}
		} else {
			logger.log(Level.WARNING,"There are no users on project team");
		}
	}

	/**
	 * Sends a facebook notification to the user
	 * 
	 * @param connection
	 *            to allow the notification to be sent
	 * @param user
	 *            the user to get the notification
	 */
	public void sendFacebookNotification(XMPPConnection connection, User user) {
		String uid = getUserId(user.getFacebookUsername());

		// Make sure uid is valid (facebook username)
		if (uid != null) {
			// Connect to chat server
			Chat chat = connection.getChatManager().createChat(
					"-" + uid + "@chat.facebook.com", null);
			// Set message
			Message message = new Message("-" + uid + "@chat.facebook.com",
					Message.Type.chat);

			if (!g.isComplete()) {
				message.setBody(generateCreateGameMessage());
			} else {
				message.setBody(generateEndGameMessage());
			}
			// Try to send the message
			try {
				chat.sendMessage(message);
				logger.log(Level.INFO,"Sent facebook message successfully");
			} catch (XMPPException e) {
				logger.log(Level.WARNING,"Failed to send Facebook Notification.");
				e.printStackTrace();
			}
		} else {
			logger.log(Level.WARNING, "User does not have a valid facebook username stored.");
		}
	}

	/**
	 * Gets the facebook UserId of a user via their username
	 * 
	 * @param username
	 *            the user's username
	 * @return the user's UserId
	 */
	public String getUserId(String username) {
		FacebookClient facebookClient = new DefaultFacebookClient();
		// Need to force restfb User type
		com.restfb.types.User fbUser = facebookClient.fetchObject(username,
				com.restfb.types.User.class);

		return fbUser.getId();
	}

	/**
	 * Used to generate the message text for notifying users of game creation,
	 * contains game's name, and end date.
	 * 
	 * @return String representing message to be sent on game creation.
	 */
	private String generateCreateGameMessage() {
		return "Voting is required for game: " + g.getName()
				+ "\nGame Ending : " + g.getEndDate().toString();
	}

	/**
	 * Used to generate the message text for notifying users of game end,
	 * contains ...
	 * 
	 * @return String representing message to be sent on game end.
	 */
	private String generateEndGameMessage() {
		return "Game: " + g.getName() + " has ended. \n"
				+ "Login to Janeway to see end game statistics.\n"
				+ "Bobby Drop Tables\nWPI Suite";
	}
}
