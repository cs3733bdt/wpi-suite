/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.notification;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Properties;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.game.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.requirement.Requirement;

/**
 * @author dstapply
 *
 */

public class testEmailNotification {

	Game g;
	User u1, u2, u3, u4;
	User[] team1;
	String[] support1;
	Project p;
	EmailNotification en;
	ArrayList<Requirement> reqs;
	
	@Before
	public void setUp()
	{
		team1 = new User[4];
		reqs = new ArrayList<Requirement>();
		g = new Game("Game A", "Test description", reqs, true, false);
		u1 = new User("testuser1", "user1", "pass", "paleondires@wpi.edu", "peter.leondires", 1123);
		u2 = new User("testuser2", "user2", "pass", "cs3733bdt@wpi.edu", "fbTest", 1291);
		u3 = new User("testuser3", "user3", "pass", "doruk.uzunoglu@gmail.com", "fbTest", 12911);
		u4 = new User("testuser4", "user4", "pass", "abbusch@wpi.edu", "fbtest", 12411);

		team1[0] = u1;
		team1[1] = u2;
		team1[2] = u3;
		team1[3] = u4;
		support1 = new String[3];
		support1[0] = "defecttracker";
		support1[1] = "postboard";
		support1[2] = "planningpoker";
		p = new Project("testemailproject", "proj3", u1, team1, support1);
		g.setProject(p);
		en = new EmailNotification(g);
	}
	
	/**
	 * Best Test we can do, can't directly compare session objects because
	 * getInstance will return different values for each session even
	 * with the same username and password.
	 */
	@Test
	public void testLogin() {
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		
		// Initialize session
		Session session;
		
		// Set session with authenticator
		session = Session.getInstance(properties,
				  new javax.mail.Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("WPI.Suite.BDT.NoReply@gmail.com", "bobbydroptables");
					}
				  });
		
		assertEquals(session.getProperties(), en.login().getProperties());
	}
	
	/**
	 * Commended out to prevent spamming u1 with emails.
	 * (Works as of 4/7/14)
	 */
	@Test
	public void testSendEmail() {
		//en.sendEmail(en.login(), u1);
	}

	/**
	 * Commented out to prevent spamming the alias.
	 * (Works as of 4/7/14)
	 */
	@Test
	public void testSendEmails() {
		//en.sendEmails();
	}
}
