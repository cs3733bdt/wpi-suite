package edu.wpi.cs.wpisuitetng.modules.planningpoker.models.vote;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class VoteTest {
	Vote aVote;
	
	@Before
	public void setUp() throws Exception {
		this.aVote = new Vote("Steve", 37);
	}

	@Test
	public void storageTest() {
		assertEquals(aVote.getUsername(), "Steve");
		assertEquals(aVote.getVoteNumber(), 37);
	}

}
