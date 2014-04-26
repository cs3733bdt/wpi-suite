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
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.cards;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.models.PPRequirement;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.NewActiveGamePanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.NewRightHalfActiveGamePanel;

/**
 * @author Bobby Drop Tables
 *
 */
public class ActiveCardsPanelTest {

	ActiveCardsPanel activeCardsPanel;
	NewActiveGamePanel activeGameTab;
	NewRightHalfActiveGamePanel rightHalf;
	ArrayList<PPRequirement> reqs;
	Game game;
	ArrayList<String> passedDeck;
	
	
	@Before
	public void setUp() throws Exception {
		
		reqs = new ArrayList<PPRequirement>();
		reqs.add(new PPRequirement("Test Req1", "Test Desc1"));
		
		passedDeck = new ArrayList<String>();
		passedDeck.add("5");
		passedDeck.add("10");
		passedDeck.add("20");
		passedDeck.add("25");
		passedDeck.add("50");
		
		
		game = new Game("ActiveGame1", "This is active game1",
				reqs, true, true);
		
		activeGameTab = new NewActiveGamePanel(game);
		rightHalf = (NewRightHalfActiveGamePanel) activeGameTab.getRightComponent();
		activeCardsPanel = new ActiveCardsPanel(passedDeck, rightHalf);
	}
	
	@Test
	public void cardsPanelGetMaxTest() {
		assertEquals(110, activeCardsPanel.getMaxSum());
	}
	
	@Test
	public void cardsPanelAddtoCardSumTest() {
		activeCardsPanel.addToCardSum(5);
		assertEquals(5, activeCardsPanel.getSum());
		activeCardsPanel.addToCardSum(7);
		assertEquals(12, activeCardsPanel.getSum());
	}
	
	@Test
	public void cardsPanelDecToCardSumTest() {
		activeCardsPanel.addToCardSum(20);
		activeCardsPanel.decToCardSum(5);
		assertEquals(15, activeCardsPanel.getSum());
		activeCardsPanel.decToCardSum(7);
		assertEquals(8, activeCardsPanel.getSum());
	}
	
	@Test
	public void cardsPanelClearSumTest() {
		activeCardsPanel.addToCardSum(20);
		activeCardsPanel.decToCardSum(5);
		assertEquals(15, activeCardsPanel.getSum());
		activeCardsPanel.clearSum();
		assertEquals(0, activeCardsPanel.getSum());
	}
	
	@Test
	public void cardsPanelClearCardsTest() {
		activeCardsPanel.getCardButtonArray().get(0).doClick();
		assertTrue(activeCardsPanel.getCardButtonArray().get(0).isSelected());
		activeCardsPanel.getCardButtonArray().get(1).doClick();
		assertTrue(activeCardsPanel.getCardButtonArray().get(1).isSelected());
			
		try {
			activeCardsPanel.clearCards();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertFalse(activeCardsPanel.getCardButtonArray().get(0).isSelected());
		assertFalse(activeCardsPanel.getCardButtonArray().get(1).isSelected());
	}
	
	@Test
	public void cardsPanelMemoryArrayTest() {
		activeCardsPanel.memoryArrayAddElt(5);
		assertEquals(1, activeCardsPanel.memoryArrayGetSize());
		assertEquals(5, activeCardsPanel.memoryArrayGetElt(0));
		activeCardsPanel.memoryArrayClear();
		assertEquals(0, activeCardsPanel.memoryArrayGetSize());
	}
}
