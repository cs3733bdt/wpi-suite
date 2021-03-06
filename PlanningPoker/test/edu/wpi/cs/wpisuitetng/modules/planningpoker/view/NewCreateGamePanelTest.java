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
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.models.PPRequirement;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.tree.GameTree;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.ErrorLabel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation.CreateGamePanel;

/**
 * Tests for NewCreateGamePanel
 * @author Bobby Drop Tables
 *
 */
public class NewCreateGamePanelTest {

	CreateGamePanel createGameTab;
	Game activeGame;
	private ArrayList<PPRequirement> reqs;
	private GameTree gameTree;
	private ViewEventController vec;
	private ErrorLabel errorText;
	CreateGamePanel createGameErrorTab;
	
	@Before
	public void setUp() throws Exception { // $codepro.audit.disable accessorMethodNamingConvention
		reqs = new ArrayList<PPRequirement>();
		reqs.add(new PPRequirement("Test Req1", "Test Desc1"));
		
		activeGame = new Game("ActiveGame1", "This is active game1",
				reqs, true, true);
		errorText = new ErrorLabel();
		errorText.setText("Error");
		vec = ViewEventController.getInstance();
		gameTree = GameTree.getInstance();
		vec.setGameOverviewTree(gameTree);
		
		
		//createGameTab = new NewCreateGamePanel(null);
		createGameTab = new CreateGamePanel(activeGame);
		
		
	}
	
	@Test
	public void LaunchGamePressedTest() {
		assertFalse(createGameTab.LaunchGamePressed());
		createGameTab.launchGame();
		
	}
	
	@Test
	public void CreateGameServerConnectionLostTest() { // $codepro.audit.disable missingAssertInTestMethod
		createGameTab = new CreateGamePanel(activeGame, true);
	}
}
