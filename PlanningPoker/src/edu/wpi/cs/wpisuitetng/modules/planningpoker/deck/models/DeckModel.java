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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractListModel;

import edu.wpi.cs.wpisuitetng.modules.Model;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.abstractmodel.IModelObserver;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.abstractmodel.ObservableModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;

/**
 * Holds the all of the Decks for all users in the database
 * @author jonathanleitschuh
 *
 */
public class DeckModel extends AbstractListModel<Deck> implements IModelObserver {
	private static DeckModel instance = null;
	
	private List<Deck> decks;
	
	private DeckModel(){
		decks = new ArrayList<Deck>();
	}
	
	
	/**
	 * Gets the singleton instance of the DeckModel
	 * @return the current instance of the DeckModel
	 */
	public static DeckModel getInstance() {
		if(instance == null){
			instance = new DeckModel();
		}
		return instance;
	}
	
	public void emptyModel(){
		int oldSize = getSize();
		Iterator<Deck> iterator = decks.iterator();
		while (iterator.hasNext()) {
			iterator.next();
			iterator.remove();
		}
		this.fireIntervalRemoved(this, 0, Math.max(oldSize - 1, 0));
	}

	@Override
	public int getSize() {
		return decks.size();
	}

	@Override
	public Deck getElementAt(int index) {
		return decks.get(index);
	}

	@Override
	public void update(ObservableModel o, Object arg) {
		// TODO Auto-generated method stub
		
	}
	
	public void update(Deck[] decks){
		//TODO add functionality
	}

	public void addDeck(Deck deck) {
		decks.add(deck);
		deck.addObserver(this);
		this.fireIntervalAdded(this, 0, 0);
		
	}


	public void addDecks(Deck[] deckList) {
		for(Deck d : deckList){
			decks.add(d);
		}
		//decks.addAll(Arrays.asList(deckList));
		
	}


	public List<Deck> getDecks() {
		return decks;
	}

}
