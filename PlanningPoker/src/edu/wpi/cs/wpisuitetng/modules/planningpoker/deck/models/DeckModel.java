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
import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.abstractmodel.AbstractStorageModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.abstractmodel.ObservableModel;

/**
 * Holds the all of the Decks for all users in the database
 * @author jonathanleitschuh
 *
 */
public class DeckModel extends AbstractStorageModel<Deck> {
	private static DeckModel instance = null;
	
	
	private DeckModel(){
		super(new ArrayList<Deck>());
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
		super.emptyModel();
	}

	public void addDeck(Deck deck) {
		add(deck);	
	}


	public synchronized void addDecks(Deck[] deckList) {
		updateDecks(deckList);
		
	}
	
	public synchronized void updateDecks(Deck[] allDecks) {
		boolean changes = updateModels(allDecks);
	}


	public List<Deck> getDecks() {
		return list;
	}
	
	@Override
	public void update(ObservableModel o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
