package edu.wpi.cs.wpisuitetng.modules.planningpoker.models.requirement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.game.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.vote.Vote;

public class RequirementTest {
	Requirement req1;
	Requirement req2;
	Requirement req3;
	
	Requirement req2Dupe;
	Requirement req3Dupe;
	
	ArrayList<Requirement> reqList;
	
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
		
		req2Dupe = new Requirement("Req2 Dupe", "DescDupe2");
		req2Dupe.setId(req2.getId());
		
		req3Dupe = new Requirement("Req3 Dupe", "DescDupe2");
		req3Dupe.setId(req3.getId());
		
		reqList = new ArrayList<Requirement>();
		reqList.add(req1);
		reqList.add(req2);
		
		User Jeremy =  new User("Jeremy", "Jim", "", "generic.email", 14);
		
		game1 = new Game("Game 1", "Description", "Jeremy", null, false, false);
		game2 = new Game("Game 2", "New Desc", "Steve", reqList, false, false);
		
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
	public void testCopyFrom(){
		Requirement copyTo = new Requirement(req1.getName() + " copy", req1.getDescription() + " copy");
		copyTo.addVote(new Vote("Steve", 20));
		assertTrue(copyTo.copyFrom(req1)); //Should return true because changes were made
		assertFalse(copyTo.copyFrom(req1)); //Should return false because no changes were made to the class
		
		assertEquals(copyTo.getName(), req1.getName());
		assertEquals(copyTo.getDescription(), req1.getDescription());
		assertEquals(copyTo.getVoteCount(), req1.getVoteCount());
	}
	
	@Test
	public void testGetRequirements(){
		assertEquals(game1.getRequirements(), null);
		assertEquals(game2.getRequirements(), reqList);
	}

}
