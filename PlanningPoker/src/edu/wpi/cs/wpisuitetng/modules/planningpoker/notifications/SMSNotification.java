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

import edu.wpi.cs.wpisuitetng.modules.core.models.Carrier;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;

/**
 * Sends text message to a user via email
 * 
 * @author doruk
 */
public class SMSNotification {
	
	/** Game to get users from to send SMS messages to */
	private final Game g;
	/** Sender Email Information */
	private final String username = "WPI.Suite.BDT.NoReply@gmail.com";
	private final String password = "bobbydroptables";
	
	
	/**
	 * Constructs an SMS notification for a given game
	 * @param g The game to notify users about.
	 */
	public SMSNotification(Game g) {
		this.g = g;
	}
	
	/**
	 * Creates a connection with SMS servers
	 * @return connection created
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
				System.err.println("Session.getInstance threw a NullPointerException, trying again...");
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
	public void sendSMSMessages() {
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
			for (int i = 0; i < users.length; i++) {
				// TODO implement verify if email format
				if (users[i].getEmail() != null)
					sendSMS(login(), users[i]);
				else
					System.err.println(users[i].getName() + " doesn't have an email Stored.");
			}
		} else {
			System.out.println("Project: " + g.getProject().getName() + ", has no users in its team.");
			System.out.println("No SMS messages were sent.");
		}
	}
	
	/**
	 * Appends the carrier to the end of the phone number
	 * which is needed to be sent.
	 * @param user
	 * @return String of user with selected carrier on the end of the string
	 */
	public String appendCarrier(User user) {
		String numberWithCarrier = user.getPhoneNumber();
		Carrier c = user.getCarrier();
		switch(c) {
		case ATT:
			numberWithCarrier = numberWithCarrier +"@txt.att.net";
			break;
		case VERIZON:
			numberWithCarrier = numberWithCarrier +"@vtext.com";
			break;
		case TMOBILE:
			numberWithCarrier = numberWithCarrier +"@tmomail.net";
			break;
		case SPRINT:
			numberWithCarrier = numberWithCarrier +"@messaging.sprintpcs.com";
			break;
		case USCELLULAR:
			numberWithCarrier = numberWithCarrier + "@email.uscc.net";
			break;
		default:
			System.out.print("No carrier.");
			numberWithCarrier = null;
			break;
		}
		
		return numberWithCarrier;
	}
	
	/**
	 * This method uses the javaMail API library to send an SMS to the user
	 * This code is inspired by http://www.mkyong.com/java/javamail-api-sending-email-via-gmail-smtp-example/
	 * @param session the session with the SMS server
	 * @param user The user to be messaged.
	 */
	public void sendSMS(Session session, User user) {
		// Recipient's SMS ID needs to be mentioned.
		String to = "";
		
		// Check if user has a phone number in case user was created without one
		// and print name of user
		if (user.getPhoneNumber() != null) {
			to = this.appendCarrier(user);
			
			try {
				// Create a default MimeMessage object.
				MimeMessage message = new MimeMessage(session);

				// Set From: header field of the header.
				message.setFrom(new InternetAddress(username));

				// Set To: header field of the header.
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(
						to));					

				//Sets message text. Doesn't include requirements to keep text message
				//a reasonable size
					message.setText("Voting is Required for game: " + g.getName() +
							"\nGame Ending : " + g.getEndDate().toString());
				
				try {
					// Send message
					Transport.send(message);
					System.out.println("Sent text message successfully....");
				} catch(MailConnectException e) {
					try {
						// Waiting 5 seconds and retrying
						Thread.sleep(5000);
						System.err.println("Couldn't connect to host, trying again...");
						Transport.send(message);
						System.out.println("Sent message successfully....");
					} catch (InterruptedException e1) {
						System.err.println("Can't connect to host; either internet or host is down");
						System.err.println("Users won't get messages for game: " + g.getName());
						e1.printStackTrace();
					}
				}
			} catch (MessagingException mex) {
				mex.printStackTrace();
			}
		} else {
			System.out.println("User: " + user.getName() + ", does not have a phone number stored.");
		}
	}
}