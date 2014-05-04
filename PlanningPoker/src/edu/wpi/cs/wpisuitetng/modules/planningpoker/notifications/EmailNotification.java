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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.util.MailConnectException;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.abstractmodel.AbstractStorageModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.models.PPRequirement;

/**
 * This is a class that will send out email notifications whenever a game is
 * successfully created.
 * 
 * @author Bobby Drop Tables
 */
public class EmailNotification {
	private static final Logger logger = Logger.getLogger(AbstractStorageModel.class
			.getName());
	/** Game to get users from to send emails to */
	private final Game g;
	/** Sender Email Information */
	private final String username = "WPI.Suite.BDT.NoReply@gmail.com";
	private final String password = "bobbydroptables";

	/**
	 * Constructs an email notification for a given game
	 * 
	 * @param g
	 *            The game to notify users about.
	 */
	public EmailNotification(Game g) {
		this.g = g;
	}

	/**
	 * logs in the user into the email server
	 * 
	 * @return session that contains the email server
	 */
	public Session login() {
		// Get system properties
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");

		// Initialize session
		Session session = null;

		try {
			// Set session with authenticator
			session = Session.getInstance(properties,
					new javax.mail.Authenticator() {
						@Override
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(username,
									password);
						}
					});
		} catch (NullPointerException e) {
			logger.log(Level.WARNING, "Session.getInstance "
					+ "threw a NullPointerException, trying again...", e);
			try {
				// Waiting 5 seconds then trying again.
				Thread.sleep(5000);
				session = Session.getInstance(properties,
						new javax.mail.Authenticator() {
							@Override
							protected PasswordAuthentication getPasswordAuthentication() {
								return new PasswordAuthentication(username,
										password);
							}
						});
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}

		return session;
	}

	/**
	 * This method implements the sendEmail method to send email notifications
	 * to all the users on a team
	 */
	public void sendEmails() {
		// Get the users that are expected to play the game
		User[] users = null;
		// Make sure there is a project and a team before
		// setting the users to the team
		try {
			users = g.getProject().getTeam();
		} catch (NullPointerException e) {
			e.printStackTrace();
			System.err
					.println("Could not set the list 'users' to the list 'team'.");
		}

		// Check to see if no users are attached to this project
		if (users[0] != null) {
			// Send email to each user
			for (int i = 0; i < users.length; i++) {
				// TODO implement verify if email format
				if (users[i].getNotificationPreferences().contains("E"))
					if (users[i].getEmail() != null && !users[i].getEmail().equals("")) {
						sendEmail(login(), users[i]);
					} else {
						System.err.println(users[i].getName()
								+ " doesn't have an email Stored.");
					}
				else {
				System.err.println(users[i].getName()
						+ " doesnt want to receive email notifications.");
				}
			}
		}

		else {
			logger.log(Level.INFO,"Project: " + g.getProject().getName()
					+ ", has no users in its team.");
			logger.log(Level.INFO,"No Emails were sent.");
		}
	}

	/**
	 * This method uses the javaMail API library to send an email to the user
	 * This code is inspired by
	 * http://www.mkyong.com/java/javamail-api-sending-email
	 * -via-gmail-smtp-example/
	 * 
	 * @param session
	 *            the session with email server
	 * @param user
	 *            The user to be emailed.
	 */
	public void sendEmail(Session session, User user) {
		// Recipient's email ID needs to be mentioned.
		String to = "";

		// Check if user has an email in case user was created without one
		// and print name of user
		if (user.getEmail() != null) {
			to = user.getEmail();
		} else {
			logger.log(Level.INFO,"User: " + user.getName()
					+ ", does not have an email stored.");
		}

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(username));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					to));

			// If the game doesn't have requirements, say that instead
			// of printing null requirements.
			// Then set the actual message.
			if (!g.getRequirements().isEmpty()) {
				if (!g.isComplete()) {
					message.setSubject("Planning Poker Game: " + g.getName()
							+ " has begun!");
					message.setText(generateCreateGameMessage());
				} else {
					message.setSubject("Planning Poker Game: " + g.getName()
							+ " has ended!");
					message.setText(generateEndGameMessage());
				}

			} else {
				message.setText("There are no current requirements.");
			}

			try {
				// Send message
				Transport.send(message);
				logger.log(Level.INFO,"Sent email successfully....");
			} catch (MailConnectException e) {
				logger.log(Level.WARNING,
						"Couldn't connect to host, trying again...", e);
				try {
					// Waiting 5 seconds and retrying
					Thread.sleep(5000);
					Transport.send(message);
					logger.log(Level.INFO,"Sent email successfully....");
				} catch (InterruptedException e1) {
					System.err
							.println("Can't connect to host; either internet or host is down");
					System.err.println("Users won't get emails for game: "
							+ g.getName());
					e1.printStackTrace();
				}
			}
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

	/**
	 * Used to generate the message text for notifying users of game creation,
	 * contains game's name, requirements, and end date.
	 * 
	 * @return String representing message to be sent on game creation.
	 */
	private String generateCreateGameMessage() {
		String reqs = "";
		for (PPRequirement r : g.getRequirements()) {
			reqs = reqs + r.toString();
		}

		return "Voting is required for game: " + g.getName() + "\n\n"
				+ "Game Description: " + g.getDescription() + "\n"
				+ "\nGame Ending : " + g.getEndDate().toString()
				+ "\n\nGame Requirements: " + reqs + "\n\n"
				+ "Bobby Drop Tables \nWPI Suite";
	}

	/**
	 * Used to generate the message text for notifying users of game end,
	 * contains ...
	 * 
	 * @return String representing message to be sent on game end.
	 */
	private String generateEndGameMessage() {
		String stats = "";
		ArrayList<Integer> voteArray = new ArrayList<Integer>();

		for (PPRequirement r : g.getRequirements()) {

			for (int i = 0; i < r.getVotes().size(); i++) {
				voteArray.add(r.getVotes().get(i).getVoteNumber());
			}

			stats += "\nRequirement: " + r.getName() + "\n" + "Mean: "
					+ mean(voteArray) + "\n" + "Median: " + median(voteArray)
					+ "\n" + "Standard Deviation: " + stDev(voteArray) + "\n\n";
			voteArray.clear();
		}

		return "Game: " + g.getName() + " has ended.\n\n"
				+ "Game Description: " + g.getDescription() + "\n\n"
				+ "Game Statistics: " + stats + "\n\n"
				+ "Bobby Drop Tables \nWPI Suite";
	}

	/**
	 * calculates the mean of votes of a requirement
	 * 
	 * @param m
	 *            the array list which contains the vote values
	 * @return the mean of votes
	 */
	private double mean(List<Integer> Votes) {
		double sum = 0;
		for (int i = 0; i < Votes.size(); i++) {
			sum += Votes.get(i);
		}
		return sum / ((double) Votes.size());
	}

	/**
	 * calculates the median of votes of a requirement
	 * 
	 * @param Votes
	 *            the array list which contains the vote values
	 * @return the median of votes
	 */
	private double median(List<Integer> Votes) {
		double median = 0;
		if (Votes.size() == 0) {
			median = 0;
		} else if (Votes.size() == 1) {
			median = Votes.get(0);
		} else {
			double[] a = new double[Votes.size()];
			for (int i = 0; i < Votes.size(); i++) {
				a[i] = Votes.get(i);
			}
			Arrays.sort(a);
			int mid = a.length / 2;

			if (a.length % 2 == 0) {
				median = (a[mid] + a[mid - 1]) / 2.0;
			} else {
				median = a[mid];
			}
		}
		return median;
	}

	/**
	 * calculates the standard deviation of votes of a requirement
	 * 
	 * @param Votes
	 *            the array list which contains the vote values
	 * @return the standard deviation of votes
	 */
	private double stDev(List<Integer> Votes) {
		double stDev = 0;
		double mean = mean(Votes);
		ArrayList<Double> numMinusMeanSquared = new ArrayList<Double>();

		for (int i = 0; i < Votes.size(); i++) {
			numMinusMeanSquared.add(Math.pow((Votes.get(i) - mean), 2));
		}

		double sum = 0;

		for (int j = 0; j < numMinusMeanSquared.size(); j++) {
			sum += numMinusMeanSquared.get(j);
		}

		double variance = (sum / (double) numMinusMeanSquared.size());
		stDev = Math.sqrt(variance);
		return stDev;
	}
}
