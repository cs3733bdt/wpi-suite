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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.models.vote;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * TODO: add documentation
 * @author Bobby Drop Tables
 *
 */
public class VoteTest {
	Vote aVote;
	
	@Before
	public void setUp() throws Exception {
		aVote = new Vote("Steve", 37);
	}

	@Test
	public void storageTest() {
		assertEquals(aVote.getUsername(), "Steve");
		assertEquals(aVote.getVoteNumber(), 37);
	}

}
