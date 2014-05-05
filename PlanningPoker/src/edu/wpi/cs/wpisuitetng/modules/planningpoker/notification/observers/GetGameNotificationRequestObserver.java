/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Bobby Drop Tables
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.notification.observers;

import java.util.logging.Level;
import java.util.logging.Logger;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.deck.observers.AddDeckRequestObserver;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.controllers.GetGameController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.notification.controllers.GetGameNotificationController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.notification.models.GameNotification;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * This observer is called when a response is received from a request to the server to get a game
 */
public class GetGameNotificationRequestObserver implements RequestObserver {
	/** Used to print messages from the controller */
	private GetGameNotificationController controller;
	
	private static final Logger logger = Logger.getLogger(GetGameNotificationRequestObserver.class
			.getName());
	
	/**
	 * Constructs an observer for requesting games
	 * @param controller getGameController to be observed
	 */
	public GetGameNotificationRequestObserver(GetGameNotificationController controller) {
		this.controller = controller;
	}
	
	/**
	 * Parse the messages out of the response body and pass them to the controller
	 * 
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		final ResponseModel response = iReq.getResponse();
		
	    GameNotification[] gns = GameNotification.fromJsonArray(iReq.getResponse().getBody());
	    
	    controller.receivedGameNotifications(gns);
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
		logger.log(Level.WARNING,"Failed to get games");
	}
}
