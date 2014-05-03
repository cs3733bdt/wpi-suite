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
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.MockNetwork;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.models.PPRequirement;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.tree.GameTree;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

public class CreateGamePanelTest {
	CreateGamePanel parent;
	CreateGamePanel parent2;
	Game game;
	PPRequirement req1, req2;
	ArrayList<PPRequirement> reqsArray;
	RightHalfCreateGamePanel rightHalf;
	ViewEventController vec;
	GameTree tree;
	GameModel model;
	
	@Before
	public void setUp() { // $codepro.audit.disable accessorMethodNamingConvention
		
		// Set up the network
				Network.initNetwork(new MockNetwork());
				Network.getInstance().setDefaultNetworkConfiguration(
						new NetworkConfiguration("http://wpisuitetng"));
				
		// Set up the data
		vec = ViewEventController.getInstance();
		model = GameModel.getInstance();
		tree = GameTree.getInstance();
		vec.setGameOverviewTree(tree);
		req1 = new PPRequirement();
		req1.setName("Req1");
		req1.setDescription("Desc1");
		req2 = new PPRequirement();
		req2.setName("Req2");
		req2.setDescription("Desc2");
		
		reqsArray = new ArrayList<PPRequirement>();
		reqsArray.add(req1);
		reqsArray.add(req2);
		
		game = new Game("Game1", "Game1 Description", reqsArray, true, true);
		
		parent = new CreateGamePanel(null);
		parent2 = new CreateGamePanel(game);
		rightHalf = parent.getRightHalf();
		
	}
	
	@Test
	public void launchGameTest() {
		model.emptyModel();
		parent.launchGame();
		assertEquals(1, model.getSize());
	}
	
	@Test
	public void saveGameTest() {
		model.emptyModel();
		parent.saveGame();
		assertEquals(1, model.getSize());
	}
	
	@Test
	public void importReqButtonTest() {
		assertTrue(rightHalf.getAddReqButton().isEnabled());
		assertTrue(rightHalf.getImportReqButton().isEnabled());
		rightHalf.getImportReqButton().doClick();
		assertFalse(rightHalf.getAddReqButton().isEnabled());
		assertFalse(rightHalf.getImportReqButton().isEnabled());
		assertFalse(rightHalf.getRemoveReqButton().isEnabled());
	}
	
	@Test
	public void addReqButtonTest() {
		assertTrue(rightHalf.getImportReqButton().isEnabled());
		rightHalf.getAddReqButton().doClick();
		assertFalse(rightHalf.getAddReqButton().isEnabled());
		assertFalse(rightHalf.getImportReqButton().isEnabled());
		assertFalse(rightHalf.getRemoveReqButton().isEnabled());
	}
		
	@Test
	public void submitAddReqButtonTest() {
		rightHalf.getCancelImportReqButton().doClick();
		assertTrue(rightHalf.getAddReqButton().isEnabled());
		assertTrue(rightHalf.getImportReqButton().isEnabled());
	}
}
