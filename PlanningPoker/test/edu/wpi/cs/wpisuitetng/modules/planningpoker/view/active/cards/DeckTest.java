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

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class DeckTest {

	private Deck defaultDeck;
	private Deck defaultMoreCardsDeck;
	private Deck customDeck;
	private ArrayList<String> deckArray;
	
	@Before
	public void setUp() throws Exception {
		deckArray = new ArrayList<String>();
		deckArray.add("10");
		deckArray.add("12");
		deckArray.add("14");
		deckArray.add("16");
		deckArray.add("18");
		deckArray.add("20");
		
		defaultDeck = new Deck();
		defaultMoreCardsDeck = new Deck(10);
		customDeck = new Deck(deckArray);
		
	}
	
	@Test
	public void defaultDeckTest() {
		assertEquals(1, defaultDeck.get(0));
		assertEquals(1, defaultDeck.get(1));
		assertEquals(2, defaultDeck.get(2));
		assertEquals(3, defaultDeck.get(3));
		assertEquals(5, defaultDeck.get(4));
		assertEquals(8, defaultDeck.get(5));
		assertEquals(13, defaultDeck.get(6));
	}
	
	@Test
	public void defaultMoreCardsDeckTest() {
		assertEquals(1, defaultMoreCardsDeck.get(0));
		assertEquals(1, defaultMoreCardsDeck.get(1));
		assertEquals(2, defaultMoreCardsDeck.get(2));
		assertEquals(3, defaultMoreCardsDeck.get(3));
		assertEquals(5, defaultMoreCardsDeck.get(4));
		assertEquals(8, defaultMoreCardsDeck.get(5));
		assertEquals(13, defaultMoreCardsDeck.get(6));
		assertEquals(21, defaultMoreCardsDeck.get(7));
		assertEquals(34, defaultMoreCardsDeck.get(8));
		assertEquals(55, defaultMoreCardsDeck.get(9));	
	}
	
	@Test
	public void customDeckTest() {
		assertEquals(10, customDeck.get(0));
		assertEquals(12, customDeck.get(1));
		assertEquals(14, customDeck.get(2));
		assertEquals(16, customDeck.get(3));
		assertEquals(18, customDeck.get(4));
		assertEquals(20, customDeck.get(5));
	}
	
	
}
