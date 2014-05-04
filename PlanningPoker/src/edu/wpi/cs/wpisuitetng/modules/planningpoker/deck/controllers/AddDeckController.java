/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Bobby Drop Tables
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.deck.controllers;

import java.util.logging.Level;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.abstractmodel.AbstractStorageModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.deck.models.Deck;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.deck.observers.AddDeckRequestObserver;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This controller adds the name of Deck and the Deck creator to the model as a
 * new Deck when the user clicks the Save Deck button.
 * 
 * ******This class should be modified after the planningpoker.models and .view
 * are finished.******
 * @author Andrew Busch
 * @author tianchanggu
 * @author Jonathan Leitschuh
 * 
 */
public class AddDeckController {
	private static AddDeckController instance = null;
	private AddDeckRequestObserver observer;
	private static final Logger logger = Logger.getLogger(AddDeckController.class
			.getName());
	private AddDeckController() {
		
		observer = new AddDeckRequestObserver(this);
	}
	
	/**
	 * @return returns a new instance of an AddDeckController
	 */
	public static AddDeckController getInstance() {
		if(instance == null) {
			instance = new AddDeckController();
		}
		return instance;
	}
	
	/**
	 * This method adds a Deck to the server
	 * @param newDeck is the Deck to add to the server
	 */
	public void addDeck(Deck newDeck) {	
		logger.log(Level.INFO,"Adding: " + newDeck.getName() + " to server");
		final Request request = 
				Network.getInstance().makeRequest("planningpoker/deck", HttpMethod.PUT);
		request.setBody(newDeck.toJSON());
		request.addObserver(observer);
		request.send();
	}
}
