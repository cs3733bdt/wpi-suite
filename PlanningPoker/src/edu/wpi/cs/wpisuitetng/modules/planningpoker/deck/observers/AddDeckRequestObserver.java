/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Bobby Drop Tables
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.deck.observers;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.deck.controllers.AddDeckController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.deck.models.Deck;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * This observer is called when a response is received from a request to the server to add a game
 * ******Need to be modified according to the methods in the game models class******
 * @author tianchanggu
 *
 */
public class AddDeckRequestObserver implements RequestObserver {
	/**
	 * We don't actually use the controller,
	 * in the defect tracker they use it to print
	 * error messages.
	 */
	private final AddDeckController controller;
	
	/**
	 * constructs an AddGameRequestObserver with a controller and a game to observe
	 * @param controller the controller acting on the game
	 * @param theDeck the game being sent through
	 */
	public AddDeckRequestObserver(AddDeckController controller) {
		this.controller = controller;
	}
	
	/**
	 * Parse the details of the new game that was received from the server 
	 * then pass them to the controller
	 * 
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		// Get the response to the given request
		final ResponseModel response = iReq.getResponse();
		
		// The deck that got added
		Deck deck = Deck.fromJSON(response.getBody());
		
		System.out.println("The request to add a deck has succeeded!");
	}
	
	/**
	 * Prints out response error message and ensures the game 
	 * doesn't get added to the server. Also removes the
	 * game from the current model.
	 */
	@Override
	public void responseError(IRequest iReq) {
		System.err.println("The request to add a Deck failed. Response Error: "
				+ iReq.getResponse().getStatusMessage());
	}

	/**
	 * Called on game add failed. Prints out error message
	 * and removes game from current model.
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		System.err.println("The request to add a Deck failed with exception: "
				+ exception.getMessage());
	}
}
