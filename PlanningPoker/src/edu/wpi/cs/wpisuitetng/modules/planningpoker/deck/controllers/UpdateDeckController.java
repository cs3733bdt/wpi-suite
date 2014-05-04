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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.deck.controllers;

import java.util.logging.Level;
import java.util.logging.Logger;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.abstractmodel.AbstractStorageModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.deck.models.Deck;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.deck.observers.UpdateDeckRequestObserver;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This controller responds when the user does something to update the game.
 * 
 * @author Chris Knapp
 */
public class UpdateDeckController {

	private static UpdateDeckController instance = null;
	private UpdateDeckRequestObserver observer;
	private static final Logger logger = Logger.getLogger(UpdateDeckController.class
			.getName());
	private UpdateDeckController() {
		observer = new UpdateDeckRequestObserver(this);
	}

	/**
	 * @return a new instance of an UpdateGameController
	 */
	public static UpdateDeckController getInstance() {
		if (instance == null) {
			instance = new UpdateDeckController();
		}
		return instance;
	}

	/**
	 * updates a game with the same UUID on server
	 * 
	 * @param newDeck
	 *            Game to be updated TODO: Need model for game data
	 */
	public void updateDeck(Deck newDeck) {
		logger.log(Level.INFO,"Updating " + newDeck.getName() + " to server");
		// Update request
		Request request = Network.getInstance().makeRequest(
				"planningpoker/deck", HttpMethod.POST);
		// Set the game to update
		request.setBody(newDeck.toJSON());
		// Add observer to get response
		request.addObserver(observer);
		// Send the update request
		request.send();
	}
}
