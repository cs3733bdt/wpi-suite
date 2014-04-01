package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.notification;

import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;

/*
 * This class contains the methods used to notify users by email when a planning
 * poker session has started and/or ended.
 */

public class EmailNotification {
	
	private Game g;
	
	public EmailNotification(Game g) {
		this.g = g;
	}
	
	public void sendEmails (Game g) 
	{
		User[] users = g.getProject().getTeam();
		
		for (int i = 0; i < users.length; i++) {
			sendEmail(users[i]);
		}
	}
	
	/**
	 * This method uses the javaMail API library to send an email to the user
	 * This code is inspired by http://www.mkyong.com/java/javamail-api-sending-email-via-gmail-smtp-example/
	 * @param user
	 */
	public void sendEmail(User user) {
		// Recipient's email ID needs to be mentioned.
		String to = user.getEmail();

		// Sender's email ID needs to be mentioned
		final String username = "WPI.Suite.BDT.NoReply@gmail.com";
		final String password = "bobbydroptables";

		// Get system properties
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");

		Session session = Session.getInstance(properties,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				  });

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(username));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					to));

			// Set Subject: header field
			message.setSubject("Game Title TODO");

			// Now set the actual message
			message.setText("We did it! #martyrequirements #rekt #nailedit");

			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}
