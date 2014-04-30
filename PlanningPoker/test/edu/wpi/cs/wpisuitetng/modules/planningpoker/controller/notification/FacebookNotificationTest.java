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
import edu.wpi.cs.wpisuitetng.modules.planningpoker.notifications.FacebookNotification;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.models.PPRequirement;

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
	List<PPRequirement> reqs;
	
	@Before
	public void setUp()
	{
		reqs = new ArrayList<PPRequirement>();
		g = new Game("Email Test Game", "Email Test Game Description", reqs, false, false);
		u1 = new User("testuser1", "user1", "pass", "paleondires@wpi.edu", "fbTest", 1123);
		u2 = new User("testuser2", "user2", "pass", "alfeey44@gmail.com", "dalton.tapply", 1291);
		u3 = new User("testuser3", "user3", "pass", "doruk.uzunoglu@gmail.com", "fbTest", 12911);
		
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
	public void testGetUserId() {
		assertEquals("100000454218856", client.getUserId(u2.getFacebookUsername()));
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSendFacebookNotification() throws Exception {
		//client.sendFacebookNotification(client.login(), u2);
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSendFacebookNotifications() throws Exception {
		//client.sendFacebookNotifications();
	}

}
