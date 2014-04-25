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
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.NewActiveGamePanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.tree.GameTree;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation.NewCreateGamePanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.end.EndGamePanel;



/**
 * GUI test for the main view. Initializes the view event controller and adds/removes tabs
 * @author Bobby Drop Tables
 *
 */
public class MainViewTest {

	
	private ViewEventController vec;
	private ToolbarView toolbar;
	private GameTree gameTree;
	private TabbedView tabs;
	private NewActiveGamePanel activeGameTab;
	private NewCreateGamePanel createGameTab;
	private EndGamePanel endGameTab;
	private Game activeGame;
	private ArrayList<PPRequirement> reqs;
	
	@Before
	public void setUp() throws Exception {
		reqs = new ArrayList<PPRequirement>();
		reqs.add(new PPRequirement("Test Req1", "Test Desc1"));
		
		activeGame = new Game("ActiveGame1", "This is active game1",
				reqs, true, true);
				
		vec = ViewEventController.getInstance();
		toolbar = new ToolbarView(true);
		tabs = new TabbedView();
		gameTree = GameTree.getInstance();
		createGameTab = new NewCreateGamePanel(activeGame);
		endGameTab = new EndGamePanel(activeGame);
		activeGameTab = new NewActiveGamePanel(activeGame);
		
		
		vec.setGameOverviewTree(gameTree);
		vec.setTabbedView(tabs);
		vec.setToolBar(toolbar);
		
		
	}
	
	@Test
	public void numberOfTabs() {
		assertEquals(1, tabs.getTabCount());
		
		tabs.addTab(activeGame.getName(),createGameTab);
		assertEquals(2, tabs.getTabCount());
		
		tabs.addTab("EndGame 1", endGameTab);
		assertEquals(3, tabs.getTabCount());
		
		vec.createGame();
		assertEquals(4, tabs.getTabCount());
		vec.createDeck();
		assertEquals(5, tabs.getTabCount());
		
		vec.removeTab(endGameTab);
		assertEquals(4, tabs.getTabCount());
		
		vec.closeOthers();
		assertEquals(1, tabs.getTabCount());
		
		tabs.addTab("EndGame 1", endGameTab);
		assertEquals(2, tabs.getTabCount());
		
		vec.closeAllTabs();
		assertEquals(1, tabs.getTabCount());
		
		tabs.addTab(activeGame.getName(),activeGameTab);
		assertEquals(2, tabs.getTabCount());
		
	}
	
}
