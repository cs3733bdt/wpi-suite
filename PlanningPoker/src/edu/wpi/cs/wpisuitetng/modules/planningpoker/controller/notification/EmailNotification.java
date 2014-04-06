package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.notification;

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
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;


/**
 * This is a class that will send out email notifications
 * whenever a game is successfully created.
 *
 */
public class EmailNotification {
	
	private Game g;
	
	/**
	 * Constructs an email notification for a given game
	 * @param g The game to notify users about.
	 */
	public EmailNotification(Game g) {
		this.g = g;
	}
	
	/**
	 * This method implements the sendEmail method to 
	 * send email notifications to all the users on a team
	 */
	public void sendEmails () 
	{
		// Get the users that are expected to play the game
		User[] users = g.getProject().getTeam();
		
		//Check to see if no users are attached to this project
		if(users[0] != null) {
			for (int i = 0; i < users.length; i++) {
				sendEmail(users[i]);
			}
		} else {
			System.out.println("Project: " + g.getProject().getName() + ", has no users in its team.");
			System.out.println("No Emails were sent.");
		}
	}
	
	/**
	 * This method uses the javaMail API library to send an email to the user
	 * This code is inspired by http://www.mkyong.com/java/javamail-api-sending-email-via-gmail-smtp-example/
	 * @param user The user to be emailed.
	 */
	public void sendEmail(User user) {
		// Recipient's email ID needs to be mentioned.
		String to = "";
		
		//Check if user has an email in case user was created without one
		// and print name of user
		if (user.getEmail() != null) {
			to = user.getEmail();
		} else {
			System.out.println("User: " + user.getName() + ", does not have an email stored.");
		}

		// Sender's email ID needs to be mentioned
		final String username = "WPI.Suite.BDT.NoReply@gmail.com";
		final String password = "bobbydroptables";

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
							protected PasswordAuthentication getPasswordAuthentication() {
								return new PasswordAuthentication(username, password);
							}
						  });
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
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
				message.setText("Game Description: " + g.getDescription() + "\n\n"
						+ "Game Requirements: " + g.getRequirements());
			} else {
				message.setText("There are no current requirements.");
			}
			
			try {
				// Send message
				Transport.send(message);
				System.out.println("Sent message successfully....");
			} catch(MailConnectException e) {
				try {
					// Waiting 5 seconds and retrying
					Thread.sleep(5000);
					System.err.println("Couldn't connect to host, trying again...");
					Transport.send(message);
					System.out.println("Sent message successfully....");
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
}
