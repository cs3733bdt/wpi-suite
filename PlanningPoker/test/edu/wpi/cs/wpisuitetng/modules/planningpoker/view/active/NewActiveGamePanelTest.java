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
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.models.PPRequirement;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.tree.GameTree;

public class NewActiveGamePanelTest {
	
	private ActiveGamePanel activeGamePanel;
	private Game activeGame;
	private ArrayList<PPRequirement> reqs;
	private RightHalfActiveGamePanel rightHalf;
	private GameTree gameTree;

	
	
	@Before
	public void setUp() { // $codepro.audit.disable accessorMethodNamingConvention
		reqs = new ArrayList<PPRequirement>();
		reqs.add(new PPRequirement("Test Req1", "Test Desc1"));
		
		activeGame = new Game("ActiveGame1", "This is active game1",
				reqs, true, true);
		
		gameTree = GameTree.getInstance();
		
		activeGamePanel = new ActiveGamePanel(activeGame);
		rightHalf = (RightHalfActiveGamePanel) activeGamePanel.getRightComponent();
		
	}
	
	@Test
	public void activeGamePanelGetRequirementsTest() {
		rightHalf.submitButton();
		rightHalf.submitButtonPressed();
		assertEquals("Test Req1", activeGamePanel.getReqTable().getSelectedReq().getName());
		assertEquals("Test Req1", rightHalf.getRequirement().getName());
	}
	
	@Test 
	public void activeGamePanelGetGameTest() {
		assertEquals("ActiveGame1",activeGamePanel.getGame().getName());
	}
		
}
