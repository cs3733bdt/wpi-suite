/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Bobby Drop Tables
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.game.observers;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.controller.GetGameController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
 * This observer is called when a response is received from a request to the server to get a game
 * 
 * @author Andrew Busch
 */
public class GetGameRequestObserver implements RequestObserver {
	/** Used to print messages from the controller */
	private GetGameController controller;
	
	/**
	 * Constructs an observer for requesting games
	 * @param controller getGameController to be observed
	 */
	public GetGameRequestObserver(GetGameController controller) {
		this.controller = controller;
	}
	
	/**
	 * Parse the messages out of the response body and pass them to the controller
	 * 
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
	    Game[] games = Game.fromJsonArray(iReq.getResponse().getBody());
	    controller.receivedGames(games);
	}
	
	/**
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseError(IRequest iReq) {
	    System.err.println("Response Error: " + iReq.getResponse().getStatusMessage());
	}

	/**
	 * Put an error message in the PlanningPoker panel if the request fails.
	 * 
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(edu.wpi.cs.wpisuitetng.network.models.IRequest, java.lang.Exception)
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
	    System.err.println("Failed to get games with exception: " + exception.getMessage());
	}
}
