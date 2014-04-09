/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.notification;

import java.util.ArrayList;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

import edu.wpi.cs.wpisuitetng.modules.core.models.Carrier;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.game.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.requirement.Requirement;

/**
 * @author doruk
 *
 */
public class testSMSNotification {
	
	Game g;
	User u1, u2, u3;
	User[] team1;
	String[] support1;
	Project p;
	SMSNotification sms;
	ArrayList<Requirement> reqs;
	
	@Before
	public void setUp()
	{
		reqs = new ArrayList<Requirement>();
		g = new Game("Email Test Game", "Email Test Game Description", "Game Owner", reqs, false, false);
		u1 = new User("testuser1", "user1", "pass", "paleondires@wpi.edu", "peter.leondires", 1123);
		u1.setCarrier(Carrier.VERIZON);
		u1.setPhoneNumber("1234567890");
		team1 = new User[3];
		team1[0] = u1;

		support1 = new String[3];
		support1[0] = "defecttracker";
		support1[1] = "postboard";
		support1[2] = "planningpoker";
		
		p = new Project("testemailproject", "proj3", u1, team1, support1);
		g.setProject(p);
		sms = new SMSNotification(g);
	}
	
	@Test
	public void testAppendCarrier()
	{
		
		assertEquals("1234567890@vtext.com",sms.appendCarrier(u1));
	}
	
	@Test
	public void testSendSMSVerizon()
	{
		/**
		 *  Commented out to prevent spam. Works as of 4/8/14
		 *  u1.setPhoneNumber("");
		 *  u1.setCarrier(Carrier.VERIZON);
		 *  sms.sendSMS(sms.login(), u1);
		 */
	}
	
	@Test
	public void testSendSMSTMOBILE()
	{
		/**
		 *  Commented out to prevent spam. Works as of 4/8/14
		 *  u1.setPhoneNumber("");
		 *  u1.setCarrier(Carrier.TMOBILE);
		 *  sms.sendSMS(sms.login(), u1);
		 */
	}
	
	@Test
	public void testSendSMSSPRINT()
	{
		/**
		 *  Commented out to prevent spam. Works as of 4/8/14
		 *  u1.setPhoneNumber("");
		 *  u1.setCarrier(Carrier.SPRINT);
		 *  sms.sendSMS(sms.login(), u1);
		 */
	}
	
	@Test
	public void testSendSMSATT()
	{
		/**
		 *  Commented out to prevent spam. Works as of 4/8/14
		 *  u1.setPhoneNumber("");
		 *  u1.setCarrier(Carrier.ATT);
		 *  sms.sendSMS(sms.login(), u1);
		 */
	}
	
	@Test
	public void testSendSMSUSCellular()
	{
		/**
		 *  Commented out to prevent spam. Works as of 4/8/14
		 *  u1.setPhoneNumber("");
		 *  u1.setCarrier(Carrier.USCELLULAR);
		 *  sms.sendSMS(sms.login(), u1);
		 */
	}

}

