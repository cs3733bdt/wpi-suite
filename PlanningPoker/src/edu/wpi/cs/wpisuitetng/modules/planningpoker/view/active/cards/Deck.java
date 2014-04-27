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

import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.IDataField;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.IErrorView;

/**
 * 
 * @author Justin
 *This class is for building decks to use in planning poker and has 3 constructors to produce different decks as needed
 */
public class Deck implements IDataField {
	
	//The deck variable holds the list of Strings, each string corresponding to a card in the deck
	private ArrayList<String> deck = new ArrayList<String>();
	
	//set to true if there is an error with the created deck
	private boolean errorBit = false;
	
	
	//boolean variable that states whether this deck object represents a default deck or not
	private boolean isDefaultDeck;

	//Integer that holds the number of cards in this deck object
	private int numCards = 0;
	
	/**
	 * Constructs a default deck
	 */
	public Deck() {
		isDefaultDeck = true;
		numCards = 7;
		buildDefaultDeck(numCards);
	}

	/**
	 * Constructs a default deck with numCards number of cards from the Fibonacci sequence
	 * this constructor sets the error bit 
	 */
	public Deck(int numCards) {
		isDefaultDeck = true;
		this.numCards = numCards;
		buildDefaultDeck(numCards);
	}

	/**
	 * Constructs a default deck from the passed Deck
	 * @param Deck an ArrayList of strings, each string corresponding to a card in the created Deck object
	 */
	public Deck(ArrayList<String> deck) {
		isDefaultDeck = false;
		numCards = deck.size();
		this.deck = deck;
	}
	
	
	/**
	 * @param numCards number of Cards to be added. 0 returns an error. 
	 */
	private void buildDefaultDeck(int numCards) {
		int numLoops;
		if (numCards == 0) {
			errorBit = true; 
			return;
		}
		else { 
			numLoops = numCards - 1;
		}
		int firstnum = 0;
		int secondnum = 1;
		int currnum;
		deck.add(Integer.toString(secondnum));
		
		for (int i = 0; i < numLoops; i++) {
			currnum = firstnum + secondnum;
			deck.add(Integer.toString(currnum));
			firstnum = secondnum;
			secondnum = currnum;
		}
		deck.add("?");
	}

	/**
	 * returns true if the deck validates
	 */
	public boolean validateField(IErrorView warningField, boolean showLabel, boolean showBox) {
		return !errorBit;
	}

	@Override
	public boolean hasChanges() {
		// TODO Auto-generated method stub
		return false;
	}
	/**
	 * @param index
	 * @return the integer at the index. does not work for index size - 1 because it is a string. 
	 */
	public int intGet(int index) {
		if (index != (size() -1)) {
			return Integer.parseInt(deck.get(index));
		}
		else {
			return -1;
		}
	}	
	/**
	 * @param index
	 * @return the string at the index. works for any index
	 */
	public String get(int index) {
		return deck.get(index);
	}
	
	
	public int size() {
		return deck.size();
	}
	
	public List<String> getDeck() {
		return deck;
	}
	
	
	
	
	
	
	
	
}
