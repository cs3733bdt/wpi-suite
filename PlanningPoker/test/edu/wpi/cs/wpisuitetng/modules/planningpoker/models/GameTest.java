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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.MockNetwork;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirement.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.ActiveGamesPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.EstimatePanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

/**
 * TODO: add documentation
 * @author Bobby Drop Tables
 *
 */
public class GameTest {

	@Before
	public void setUp() throws Exception {
		Network.initNetwork(new MockNetwork());
		Network.getInstance().setDefaultNetworkConfiguration(
				new NetworkConfiguration("http://wpisuitetng"));
		GameModel.getInstance().emptyModel();
	}

	@Test
	public void testJSONConversion() {
		List<Requirement> reqList = new ArrayList<Requirement>();
		
		Game object = new Game("Game A", "Test description", reqList, true, false);
		Requirement req = new Requirement("Test Requirement", "Test description");
		
		reqList.add(req);
		//Test existing object
		assertEquals(object.getName(), "Game A");
		assertEquals(object.getDescription(), "Test description");
		assertEquals(object.getCreator(), ConfigManager.getConfig().getUserName());
		assertFalse(object.getRequirements().isEmpty());
		assertEquals(object.getRequirements().get(0), req);
		assertEquals(object.isComplete(), false);
		assertEquals(object.doesUseCards(), false);
		
		String jsonMessage = object.toJSON(); //Test conversion to jSON
		Game newObject = Game.fromJSON(jsonMessage); //Test conversion back from jSON
		
		assertEquals(newObject.getName(), "Game A");
		assertEquals(newObject.getDescription(), "Test description");
		assertEquals(newObject.getCreator(), ConfigManager.getInstance().getConfig().getUserName());
		assertFalse(newObject.getRequirements().isEmpty());
		assertEquals(newObject.getRequirements().get(0).getName(), req.getName());
		assertEquals(newObject.getRequirements().get(0).getDescription(), req.getDescription());
		assertEquals(newObject.isComplete(), false);
		assertEquals(newObject.doesUseCards(), false);
		
	}
	
	@Test
	public void testCopyContentsOfOneGameToAnother() {
		
		// create two games, one with fields initialized, one without
		Game game1 = new Game("Game A", "Test description", new ArrayList<Requirement>(), true, false);
		List<Requirement> removeList = new ArrayList<Requirement>();
		removeList.add(new Requirement("Requirement", "Description"));
		Game game2 = new Game("Game B", "A Description to be removed", removeList, false, true);
		
		
		// copy the fields from one game to another
		assertTrue(game2.copyFrom(game1));
		assertFalse(game2.copyFrom(game1)); //Second time no changes should be detected
		
		// test that fields correctly copied from one game to another
		assertEquals(game2.getName(), "Game A");
		assertEquals(game2.getDescription(), "Test description");
		assertEquals(game2.getCreator(), ConfigManager.getInstance().getConfig().getUserName());
		assertTrue(game2.getRequirements().isEmpty());
		assertEquals(game2.isComplete(), false);
		assertEquals(game2.doesUseCards(), false);
		
		List<Requirement> keepList = new ArrayList<Requirement>();
		keepList.add(new Requirement("Requirement One", "Description One"));
		game1 = new Game(game1.getName(), game1.getDescription(), keepList, true, game1.doesUseCards());
		
		assertTrue(game2.copyFrom(game1));
		
		assertEquals(1, game2.getRequirements().size());
		
		
	}
	
	@Test
	public void testGameIdentifyMethod() {
		
		Game game1 = new Game();
		Game game2 = new Game();
		Game game3 = new Game();
		Game game4 = new Game();
		Requirement req1 = new Requirement();
		
		// Set game3 and game4 to have identical UUIDs
		game4.setIdentifier(game3.getIdentity());
		
		// Ensures the games do, in fact, have the same UUID
		assertEquals(game3.getIdentity(), game4.getIdentity());
		
		// This should return false because the UUID of these two games are not the same
		assertFalse(game1.identify(game2));
		
		// Both of these games have identical UUIDs, therefore they are recognized as the 
		// same game
		assertTrue(game3.identify(game4));
		
		assertFalse(game1.identify(req1));
		
		assertFalse(game1.identify(null));
		
	}
	
	@Test
	public void testCheckActive(){
		Game game1 = new Game("Game 1", "Description", new ArrayList<Requirement>(), true, true);
		Game game2 = new Game("Game 2", "Other Description", new ArrayList<Requirement>(), false, false);
		game2.makeComplete();
		boolean complete1 = game1.isComplete();
		boolean complete2 = game2.isComplete();
		assertFalse(complete1);
		assertTrue(complete2);
		
	}
	
	@Test
	public void testCustomDeck() {
		List<Requirement> reqList = new ArrayList<Requirement>();
		List<String> customDeck = new ArrayList<String>();
		//populate customDeck
		int maxSumCheck = 0;
		for (int i = 0; i < 6; i++) {
			customDeck.add("" + i + "");
			maxSumCheck += i;
		}
		
		Game game1 = new Game("Game A", "Test description", reqList, true, false);
	
		Requirement req = new Requirement("Test Requirement", "Test description");
		reqList.add(req);
		
		EstimatePanel estimatePanel = new EstimatePanel(game1, req, customDeck);
		
		assertEquals(maxSumCheck,estimatePanel.getMaxSum());
		
		ActiveGamesPanel ActivePanel = new ActiveGamesPanel(game1);
		ActivePanel.updateEstimatePanel(game1, req);
		
		assertEquals(ActivePanel.getGame().getRequirements().size(),1);
		
	}
	

}
