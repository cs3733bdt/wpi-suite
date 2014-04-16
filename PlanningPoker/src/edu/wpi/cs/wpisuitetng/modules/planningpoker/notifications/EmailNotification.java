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

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.util.MailConnectException;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirement.models.Requirement;

/**
 * This is a class that will send out email notifications
 * whenever a game is successfully created.
 *
 * @author Bobby Drop Tables
 */
public class EmailNotification {
	
	/** Game to get users from to send emails to */
	private final Game g;
	/** Sender Email Information */
	private final String username = "WPI.Suite.BDT.NoReply@gmail.com";
	private final String password = "bobbydroptables";
	
	/**
	 * Constructs an email notification for a given game
	 * @param g The game to notify users about.
	 */
	public EmailNotification(Game g) {
		this.g = g;
	}
	
	/**
	 * TODO: fill out this documentation
	 * @return
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
							return new PasswordAuthentication(username, password);
						}
					  });
		} catch(NullPointerException e) {
			try {
				// Waiting 5 seconds then trying again.
				Thread.sleep(5000);
				System.err.println("Session.getInstance "
						+ "threw a NullPointerException, trying again...");
				session = Session.getInstance(properties,
						  new javax.mail.Authenticator() {
							@Override
							protected PasswordAuthentication getPasswordAuthentication() {
								return new PasswordAuthentication(username, password);
							}
						  });
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		
		return session;
	}
	
	/**
	 * This method implements the sendEmail method to 
	 * send email notifications to all the users on a team
	 */
	public void sendEmails() 
	{
		// Get the users that are expected to play the game
		User[] users = null;
		// Make sure there is a project and a team before
		// setting the users to the team
		try {
			users = g.getProject().getTeam();
		} catch(NullPointerException e) {
			e.printStackTrace();
			System.err.println("Could not set the list 'users' to the list 'team'.");
		}
		
		//Check to see if no users are attached to this project
		if(users[0] != null) {
			// Send email to each user
			for (int i = 0; i < users.length; i++) {
				// TODO implement verify if email format
				if (users[i].getEmail() != null)
					sendEmail(login(), users[i]);
				else
					System.err.println(users[i].getName() + " doesn't have an email Stored.");
			}
		} else {
			System.out.println("Project: " + g.getProject().getName() 
					+ ", has no users in its team.");
			System.out.println("No Emails were sent.");
		}
	}
	
	/**
	 * This method uses the javaMail API library to send an email to the user
	 * This code is inspired by 
	 * http://www.mkyong.com/java/javamail-api-sending-email-via-gmail-smtp-example/
	 * @param session the session with email server
	 * @param user The user to be emailed.
	 */
	public void sendEmail(Session session, User user) {
		// Recipient's email ID needs to be mentioned.
		String to = "";
		
		//Check if user has an email in case user was created without one
		// and print name of user
		if (user.getEmail() != null) {
			to = user.getEmail();
		} else {
			System.out.println("User: " + user.getName() + ", does not have an email stored.");
		}

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(username));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					to));

			// Set Subject: header field
			message.setSubject("Voting is Required for game: " + g.getName());

			// If the game doesn't have requirements, say that instead
			// of printing null requirements.
			// Then set the actual message.
			if (!g.getRequirements().isEmpty()) {
				if(!g.isComplete()){
					message.setText(generateCreateGameMessage());
				} else {
					message.setText(generateEndGameMessage());
				}
				
			} else {
				message.setText("There are no current requirements.");
			}
			
			try {
				// Send message
				Transport.send(message);
				System.out.println("Sent email successfully....");
			} catch(MailConnectException e) {
				try {
					// Waiting 5 seconds and retrying
					Thread.sleep(5000);
					System.err.println("Couldn't connect to host, trying again...");
					Transport.send(message);
					System.out.println("Sent email successfully....");
				} catch (InterruptedException e1) {
					System.err.println("Can't connect to host; either internet or host is down");
					System.err.println("Users won't get emails for game: " + g.getName());
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
	 * @return String representing message to be sent on game creation.
	 */
	private String generateCreateGameMessage(){
		String reqs = "";
		
		for(Requirement r : g.getRequirements()){
			reqs = reqs + r.toString();
		}
		
		return "Game Description: " + g.getDescription() + "\n\n"
				+ "\nGame Ending : " + g.getEndDate().toString()
				+ "\nGame Requirements: " + reqs;
	}
	
	/**
	 * Used to generate the message text for notifying users of game end,
	 * contains ...
	 * @return String representing message to be sent on game end.
	 */
	private String generateEndGameMessage(){
		//TODO
		return "Needs updating...";
	}
}
