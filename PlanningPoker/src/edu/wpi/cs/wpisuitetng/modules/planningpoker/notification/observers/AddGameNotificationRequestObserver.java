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

import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.controllers.AddGameController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.notification.controllers.AddGameNotificationController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.notification.models.GameNotification;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.notification.models.GameNotificationModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * This observer is called when a response is received from a request to the
 * server to add a game ******Need to be modified according to the methods in
 * the game models class*****
 */

public class AddGameNotificationRequestObserver implements RequestObserver {
	/**
	 * We don't actually use the controller, in the defect tracker they use it
	 * to print error messages.
	 */
	private final AddGameNotificationController controller;
	private final GameNotification theGN;
	private static final Logger logger = Logger.getLogger(AddGameNotificationRequestObserver.class
			.getName());

	
	public AddGameNotificationRequestObserver(AddGameNotificationController controller, GameNotification theGN) {
		this.controller = controller;
		this.theGN = theGN;
	}

	@Override
	public void responseSuccess(IRequest iReq) {
		// Get the response to the given request
		final ResponseModel response = iReq.getResponse();

		GameNotification gn = GameNotification.fromJSON(response.getBody());

		logger.log(Level.INFO,"The request to add a game has succeeded!");
	}

	/**
	 * Prints out response error message and ensures the game doesn't get added
	 * to the server. Also removes the gamenotification from the current model.
	 */
	@Override
	public void responseError(IRequest iReq) {
		logger.log(Level.WARNING,"The request to add a GameNotification failed. Response Error: "
				+ iReq.getResponse().getStatusMessage());
	}

	/**
	 * Called on game add failed. Prints out error message and removes gamenotification from
	 * current model.
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		logger.log(Level.WARNING,"The request to add a Game failed");
	}
}
