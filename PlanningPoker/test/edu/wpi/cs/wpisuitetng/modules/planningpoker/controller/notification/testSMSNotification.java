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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.notification;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.notifications.SMSNotification;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.models.PPRequirement;

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
	List<PPRequirement> reqs;
	
	@Before
	public void setUp() // $codepro.audit.disable accessorMethodNamingConvention
	{
		reqs = new ArrayList<PPRequirement>();
		g = new Game("Email Test Game", "Email Test Game Description", reqs, false, false);
		u1 = new User("testuser1", "user1", "pass", "paleondires@wpi.edu", "peter.leondires", 1123);
		u1.setCarrier("VERIZON");
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
	public void testSendSMSVerizon() // $codepro.audit.disable missingAssertInTestMethod
	{

		u1.setPhoneNumber("");
		u1.setCarrier("VERIZON");
		sms.sendSMS(sms.login(), u1);

	}
	
	@Test
	public void testSendSMSTMOBILE() // $codepro.audit.disable missingAssertInTestMethod
	{

		u1.setPhoneNumber("");
		u1.setCarrier("TMOBILE");
		sms.sendSMS(sms.login(), u1);

	}
	
	@Test
	public void testSendSMSSPRINT() // $codepro.audit.disable missingAssertInTestMethod
	{

		u1.setPhoneNumber("");
		u1.setCarrier("SPRINT");
		sms.sendSMS(sms.login(), u1);

	}
	
	@Test
	public void testSendSMSATT() // $codepro.audit.disable missingAssertInTestMethod
	{

		u1.setPhoneNumber("");
		u1.setCarrier("ATT");
		sms.sendSMS(sms.login(), u1);

	}
	
	@Test
	public void testSendSMSUSCellular() // $codepro.audit.disable missingAssertInTestMethod
	{

		u1.setPhoneNumber("");
		u1.setCarrier("USCELLULAR");
		sms.sendSMS(sms.login(), u1);

	}

}

