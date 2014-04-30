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
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.abstractmodel.AbstractStorageModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.abstractmodel.ObservableModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.deck.controllers.AddDeckController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.deck.controllers.UpdateDeckController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;

/**
 * Holds the all of the Decks for all users in the database
 * This DeckModel is designed to continuously reflect the exact model on the database
 * @author jonathanleitschuh
 *
 */
public class DeckModel extends AbstractStorageModel<Deck> {
	/** Contains the singleton instance of this class */
	private static DeckModel instance = null;
	
	/** Holds the logger for this class*/
	private static Logger logger = Logger.getLogger(DeckModel.class.getName());
	
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

	/**
	 * Adds a deck to the model as well as pushing this deck to the database.
	 * @param deck
	 */
	public void addDeck(Deck deck) {
		add(deck);
		try{
			AddDeckController.getInstance().addDeck(deck);
		}catch (NullPointerException e){
			logger.log(Level.WARNING, "Deck: " + deck.getName() + " could not be added", e);
		}catch (Exception e){
			logger.log(Level.WARNING, "Deck: " + deck.getName() + " could not be added", e);
		}
		
		ViewEventController.getInstance().refreshDeckTree();
	}


	public synchronized void addDecks(Deck[] deckList) {
		updateDecks(deckList);
		
		
	}
	
	/**
	 * Updates all of the decks within this model with the new version of the decks
	 * @param allDecks the decks to update the model with
	 */
	public synchronized void updateDecks(Deck[] allDecks) {
		boolean changes = updateModels(allDecks);
		if (changes){
			ViewEventController.getInstance().refreshDeckTree();
		}
	}


	/**
	 * Gets the contents of the DeckModel
	 * @return
	 */
	public List<Deck> getDecks() {
		return list;
	}
	
	@Override
	public void update(ObservableModel o, Object arg) {
		if(o instanceof Deck){
			UpdateDeckController.getInstance().updateDeck((Deck) o);
		}
		ViewEventController.getInstance().refreshDeckTree();
		
	}

}
