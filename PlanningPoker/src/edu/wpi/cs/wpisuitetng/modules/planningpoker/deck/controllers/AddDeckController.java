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

import edu.wpi.cs.wpisuitetng.modules.planningpoker.deck.models.Deck;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This controller adds the name of game and the game creator to the model as a
 * new game when the user clicks the create a new game button.
 * 
 * ******This class should be modified after the planningpoker.models and .view
 * are finished.******
 * @author Andrew Busch
 * @author tianchanggu
 * @author Jonathan Leitschuh
 * 
 */
public class AddDeckController {
	private static AddDeckController instance;
	
	private AddDeckController() {}
	
	/**
	 * @return returns a new instance of an AddGameController
	 */
	public static AddDeckController getInstance() {
		if(instance == null) {
			instance = new AddDeckController();
		}
		return instance;
	}
	
	/**
	 * This method adds a game to the server
	 * @param newGame is the game to add to the server
	 */
	public void addGame(Deck newDeck) {
		System.out.println("Adding: " + newDeck.getName() + " to server");
		final Request request = 
				Network.getInstance().makeRequest("planningpoker/deck", HttpMethod.PUT);
		request.setBody(newDeck.toJSON());
		//final AddGameRequestObserver observer = new AddGameRequestObserver(this, newGame);
		//request.addObserver(observer) ;
		request.send();
	}
}
