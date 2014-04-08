
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.notification;

/**
 * @author dstapply
 *
 */

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.sasl.SASLDigestMD5Mechanism;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;

public class FacebookNotification {
	
	// Game to get users to send facebook notifications to.
	private final Game g;
	// Authentication information
	private final String XMPP_HOST = "chat.facebook.com";
	private final int XMPP_PORT = 5222;
	private final String username = "wpi.suite.bdt.noreply@gmail.com";
	private final String password = "bobbytablesfb";
	private final String ACCESS_TOKEN = "CAACEdEose0cBAK50ftArXl15NAUnvNi3CqyS5xTWzunsG6uFCELImQFpGNqIZCBCspATzaZAi5nCZCZBESLlRgvBmJDoH3FOUVgMvPZC3uJ59R0zfX2VI2kIpkN5ha5BcwmJ8yndF6xKPtTaloU26PJZChNGaodKJ39BZBv4d4ZBo1VMJWap1IDYJB8OeYQPWNPDmSbhCCXz3AZDZD";

	public FacebookNotification(Game g) {
		this.g = g;
	}

	/**
	 * Creates a connection with the Facebook Servers.
	 * @return the connection
	 * @throws XMPPException
	 */
	public XMPPConnection login() throws XMPPException {
		SASLAuthentication.registerSASLMechanism("DIGEST-MD5", SASLDigestMD5Mechanism.class);
		SASLAuthentication.supportSASLMechanism("DIGEST-MD5", 0);

		ConnectionConfiguration config = new ConnectionConfiguration(XMPP_HOST, XMPP_PORT);
		XMPPConnection connection = new XMPPConnection(config);
		connection.connect();
		connection.login(username, password);

		return connection;
	}
	
	/**
	 * Sends facebook notifications to all users involved
	 * in the game's project team.
	 */
	public void sendFacebookNotifications() {
		User[] users = g.getProject().getTeam();
		
		// Make sure there are users in the team
		if (users[0] != null) {
			XMPPConnection connection = null;
			
			try {
				connection = login();
			} catch (XMPPException e) {
				System.err.println("Could not connect to Facebook with given login.");
				e.printStackTrace();
			}
			
			for (int i = 0; i < users.length; i++)
				sendFacebookNotification(connection, users[i]);
			
		} else {
			System.err.println("There are no users on the team of Project: " + g.getProject().getName());
		}
	}
	
	/**
	 * Sends a facebook notification to the user
	 * @param connection to allow the notification to be sent
	 * @param user the user to get the notification
	 */
	public void sendFacebookNotification(XMPPConnection connection, User user) {
		String uid = "100000454218856";//getUserId(user.getFBUsername());
		
		Chat chat = connection.getChatManager().createChat("-" + uid + "@chat.facebook.com", null);
		Message message = new Message("-" + uid + "@chat.facebook.com", Message.Type.chat);
		

		message.setBody("Voting is required for game: " + g.getName());
		
		try {
			chat.sendMessage(message);
		} catch (XMPPException e) {
			System.err.println("Failed to send Facebook Notification.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the facebook UserId of a user via their username
	 * @param username the user's username
	 * @return the user's UserId
	 */
	public String getUserId(String username) {
		FacebookClient  facebookClient = new DefaultFacebookClient();
		com.restfb.types.User fbUser = facebookClient.fetchObject(username, com.restfb.types.User.class);
		
		return fbUser.getId();
	}
}

