/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.notification;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirementmodels.Requirement;

/**
 * @author dstapply
 *
 */
public class FacebookNotificationTest {
    
    FacebookNotification client;
    Game g;
	User u1, u2, u3;
	User[] team1;
	String[] support1;
	Project p;
	ArrayList<Requirement> reqs;
	
	@Before
	public void setUp()
	{
		reqs = new ArrayList<Requirement>();
		g = new Game("Email Test Game", "Email Test Game Description", "Game Owner", reqs, false, false);
		u1 = new User("testuser1", "user1", "pass", "paleondires@wpi.edu", 1123);
		u2 = new User("testuser2", "user2", "pass", "alfeey44@gmail.com", 1291);
		u3 = new User("testuser3", "user3", "pass", "doruk.uzunoglu@gmail.com", 12911);
		
		team1 = new User[3];
		team1[0] = u1;
		team1[1] = u2;
		team1[2] = u3;
		support1 = new String[3];
		support1[0] = "defecttracker";
		support1[1] = "postboard";
		support1[2] = "planningpoker";
		p = new Project("testemailproject", "proj3", u1, team1, support1);
		g.setProject(p);
		client = new FacebookNotification(g);
	}
	
	@Test
	public void testSendFacebookNotification() throws Exception {
		client.sendFacebookNotification(client.login(), u2);
	}

	@Test
	public void testSendFacebookNotifications() throws Exception {
		//client.sendFacebookNotifications();
	}

}
