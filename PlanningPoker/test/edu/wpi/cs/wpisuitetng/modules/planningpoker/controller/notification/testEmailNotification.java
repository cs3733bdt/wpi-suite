/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.notification;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.notification.EmailNotification;

/**
 * @author alfeey44
 *
 */
public class testEmailNotification {

	Game g;
	User u1, u2, u3;
	User[] team1;
	String[] support1;
	Project p;
	EmailNotification en;
	
	@Before
	public void setUp()
	{
		g = new Game(43, false);
		u1 = new User("derp", "derp1", "derp12", "alfeey44@gmail.com", 1123);
		u2 = new User("derp1", "derp2", "dofwef", "cs3733bdt@wpi.edu", 1291);
		u3 = new User("de2rp1", "de2rp2", "dof2wef", "doruk.uzunoglu@gmail.com", 12911);
		
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
		en = new EmailNotification(g);
	}
	
	@Test
	public void testSendEmail() {
		en.sendEmail(u1);
	}

	@Test
	public void testSendEmails() {
		en.sendEmails(g);
	}
}
