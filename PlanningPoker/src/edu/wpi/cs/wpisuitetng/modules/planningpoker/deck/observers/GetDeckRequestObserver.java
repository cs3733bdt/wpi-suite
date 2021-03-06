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

import java.util.logging.Level;
import java.util.logging.Logger;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.deck.controllers.GetDeckController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.deck.models.Deck;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
 * This observer is called when a response is received from a request to the server to get a Deck
 * 
 * @author Andrew Busch
 */
public class GetDeckRequestObserver implements RequestObserver {
	/** Used to print messages from the controller */
	private GetDeckController controller;
	
	private static final Logger logger = Logger.getLogger(GetDeckRequestObserver.class
			.getName());
	
	/**
	 * Constructs an observer for requesting Decks
	 * @param controller getDeckController to be observed
	 */
	public GetDeckRequestObserver(GetDeckController controller) {
		this.controller = controller;
	}
	
	/**
	 * Parse the messages out of the response body and pass them to the controller
	 * 
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
	    Deck[] decks = Deck.fromJsonArray(iReq.getResponse().getBody());
	    controller.receivedDecks(decks);
	}
	
	/**
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseError(IRequest iReq) {
		logger.log(Level.WARNING,"Response Error: " + iReq.getResponse().getStatusMessage());
	}

	/**
	 * Put an error message in the PlanningPoker panel if the request fails.
	 * 
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(edu.wpi.cs.wpisuitetng.network.models.IRequest, java.lang.Exception)
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		logger.log(Level.WARNING,"Failed to get deck ");
	}
}
