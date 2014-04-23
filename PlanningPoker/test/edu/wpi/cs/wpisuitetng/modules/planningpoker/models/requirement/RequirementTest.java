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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.models.requirement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirement.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirement.models.RequirementModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.end.StatisticsPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.vote.models.Vote;

/**
 * TODO: add documentation
 * @author Bobby Drop Tables
 *
 */
public class RequirementTest {
	Requirement req1;
	Requirement req2;
	Requirement req3;
	
	Requirement req2Dupe;
	Requirement req3Dupe;
	
	Vote vote1;
	
	List<Requirement> reqList;
	List<Requirement> emptyList;
	List<Vote> req1Votes;
	
	Game game1;
	Game game2;
	Project project1;
	Session session1;


	@Before
	public void setUp() throws Exception {
		req1 = new Requirement("Req1", "Desc1");
		req2 = new Requirement("Req2", "Desc2");
		req2.setId(2);
		req3 = new Requirement("Req3", "Desc3");
		
		vote1 = new Vote ("Steve", 7);		
		req1Votes = new ArrayList<Vote>();
		req1Votes.add(vote1);
		req1.addVote(vote1);
		
		req2Dupe = new Requirement("Req2 Dupe", "DescDupe2");
		req2Dupe.setId(req2.getId());
		
		req3Dupe = new Requirement("Req3 Dupe", "DescDupe2");
		req3Dupe.setId(req3.getId());
		
		reqList = new ArrayList<Requirement>();
		emptyList = new ArrayList<Requirement>();
		reqList.add(req1);
		reqList.add(req2);
		
		User Jeremy =  new User("Jeremy", "Jim", "", "generic.email", "fbtest", 14);
		
		game1 = new Game("Game 1", "Description",  emptyList, false, false);
		game2 = new Game("Game 2", "New Desc", reqList, false, false);
		
		project1 = new Project("project 1", "7");		
		session1 = new Session(Jeremy, project1, "");
	}

	@Test
	public void identitfyRequirementInfo() {
		assertEquals("Req1", req1.getName());
		assertTrue(req1.identify(req1));
		assertFalse(req1.identify(req2));
		assertEquals("\nName: Req1\nDescription: Desc1\n", req1.toString());
		
		assertEquals("Req2", req2.getName());
		assertTrue(req2.identify(req2));
		assertTrue(req2.identify(req2Dupe));
		assertFalse(req2.identify(req3Dupe));
		assertFalse(req2.identify(null));
		assertFalse(req2.identify("A string"));	
	}
	
	@Test
	public void testAddVote(){
		int voteCount = req3.getVoteCount();
		req3.addVote(new Vote("Steve", 20));
		assertEquals(++voteCount, req3.getVoteCount());
	}
	
	@Test
	public void testStatistics() {
		req1 = new Requirement("Req1", "Desc1");
		reqList = new ArrayList<Requirement>();
		ArrayList<Requirement> reqList2 = new ArrayList<Requirement>();
		
		req1.addVote(new Vote("Justin", 1));
		req1.addVote(new Vote("Phil", 10));
		req1.addVote(new Vote("Sam", 5));
		req1.addVote(new Vote("Bill", 4));
		reqList.add(req1);
		
		double computedMean = ((1.0 + 10.0 + 5.0 + 4.0) / 4.0);
		
		game1 = new Game("Game 1", "Description",  reqList, false, false);

		StatisticsPanel statPanel = new StatisticsPanel(game1);
		ArrayList<Integer> votesAmounts = statPanel.requirementToVotes(req1);
		statPanel.makeStatRow(req1);
		
		
		assertEquals((double)req1.getVoteCount(), statPanel.getStat("numVotes"), 0);
		assertEquals( computedMean, statPanel.getStat("mean"), 0);
		assertEquals(1.0,statPanel.getStat("min"),0);
		assertEquals(10.0,statPanel.getStat("max"),0);
		assertEquals(4.5,statPanel.getStat("median"),0);
		assertEquals(3.24,statPanel.getStat("stDev"),0.1);
		
		
	}
	
	@Test
	public void testCopyFrom(){
		Requirement copyTo = 
				new Requirement(req1.getName() + " copy", req1.getDescription() + " copy");
		copyTo.addVote(new Vote("Steve", 20));
		assertTrue(copyTo.copyFrom(req1)); //Should return true because changes were made
		assertFalse(copyTo.copyFrom(req1)); //Should return false; no changes are made to the class
		
		assertEquals(copyTo.getName(), req1.getName());
		assertEquals(copyTo.getDescription(), req1.getDescription());
		assertEquals(copyTo.getVoteCount(), req1.getVoteCount());
		
	}
	
	@Test
	public void testGetRequirements(){
		assertEquals(game1.getRequirements(), emptyList);
		assertEquals(game2.getRequirements(), reqList);
	}
	
	@Test
	public void testGetVotes(){
		assertEquals(req1.getVotes(), req1Votes);
	}

	@Test
	public void testGetInstance(){
		assertNotNull(RequirementModel.getInstance());
	}
}
