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
package edu.wpi.cs.wpisuitetng.modules.planningpoker.deck.models;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.decks.creation.ColorEnum;

public class DeckModelTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testAddDeck() {
		DeckModel model = DeckModel.getInstance();
		Integer[] cards = {1, 2, 3, 4};
		List<Integer> deckNumbers = new ArrayList<Integer>(Arrays.asList(cards));
		Deck object = new Deck("Test Deck", "Test Description", deckNumbers, true, ColorEnum.BLUE);
		model.emptyModel();
		assertEquals(0, model.getSize());
		model.addDeck(object);
		assertEquals(1, model.getSize());
		
		Deck objectReturned = model.getElementAt(0);
		assertEquals("Test Deck", objectReturned.getName());
		assertEquals("Test Description", objectReturned.getDescription());
		assertEquals(ConfigManager.getConfig().getUserName(), objectReturned.getOwner());	
		
		model.emptyModel();
		assertEquals(0, model.getSize());
	}
	
	@Test
	public void testAddDeckList(){
		DeckModel model = DeckModel.getInstance();
		model.emptyModel();
		assertEquals(0, model.getSize());
		Deck[] deckList = new Deck[3];
		for(int i = 0; i < deckList.length; i++){	//Create a new deck for each index in the array
			deckList[i] = new Deck("Deck"+Integer.toString(i), "Description " + Integer.toString(i), new ArrayList<Integer>(), true, ColorEnum.BLUE);
		}
		
		model.addDecks(deckList);//Add this set of decks to the model
		assertEquals(deckList.length, model.getSize());
		for(int i = 0; i<deckList.length; i++){
			assertEquals("Deck"+Integer.toString(i), model.getElementAt(i).getName());
			assertEquals("Description " + Integer.toString(i), model.getElementAt(i).getDescription());
			assertEquals(ConfigManager.getInstance().getConfig().getUserName(), model.getElementAt(i).getOwner());
		}
		model.emptyModel();
		assertEquals(0, model.getSize());
	}
	
	@Test
	public void testUpdateDeckList(){
		DeckModel model = DeckModel.getInstance();
		model.emptyModel();
		assertEquals(0, model.getSize());
		Deck[] deckList = new Deck[3];
		for(int i = 0; i < 3; i++){	//Create a new deck for each index in the array
			deckList[i] = new Deck("Deck"+Integer.toString(i), "Description " + Integer.toString(i), new ArrayList<Integer>(), true, ColorEnum.BLUE);
		}
		
		model.addDecks(deckList);//Add this set of decks to the model
		assertEquals(deckList.length, model.getSize());
		
		Integer[] cards = {1, 2, 3, 4};
		deckList[0] = Deck.makeDeckSameID("New Deck Name", "New Deck Description", new ArrayList<Integer>(Arrays.asList(cards)), deckList[0]);
		
		for(int i = 0; i<deckList.length; i++){
			assertEquals("Deck"+Integer.toString(i), model.getElementAt(i).getName());
			assertEquals("Description " + Integer.toString(i), model.getElementAt(i).getDescription());
			assertEquals(ConfigManager.getInstance().getConfig().getUserName(), model.getElementAt(i).getOwner());
		}
		
		model.addDecks(deckList);
		assertEquals(deckList.length, model.getSize());
		assertEquals("New Deck Name", model.getElementAt(0).getName());
		assertEquals("New Deck Description", model.getElementAt(0).getDescription());
		assertEquals(5, model.getElementAt(0).getCards().size());
		
	}
	
}
