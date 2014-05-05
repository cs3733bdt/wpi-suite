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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.notification.controllers;

import java.util.logging.Level;
import java.util.logging.Logger;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.notification.models.GameNotification;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.notification.observers.UpdateGameNotificationRequestObserver;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This controller responds when the user does something to update the game.
 */
public class UpdateGameNotificationController {

	private static UpdateGameNotificationController instance = null;
	private UpdateGameNotificationRequestObserver observer;
	private static final Logger logger = Logger.getLogger(UpdateGameNotificationController.class
			.getName());
	
	private UpdateGameNotificationController() {
		observer = new UpdateGameNotificationRequestObserver(this);
	}

	/**
	 * @return a new instance of an UpdateGameController
	 */
	public static UpdateGameNotificationController getInstance() {
		if (instance == null) {
			instance = new UpdateGameNotificationController();
		}
		return instance;
	}

	/**
	 * updates a game with the same UUID on server
	 * 
	 * @param newGame
	 */
	public void updateGameNotification(GameNotification newGN) {
		logger.log(Level.INFO,"Updating " + newGN.getName() + " to server");
		// Update request
		Request request = Network.getInstance().makeRequest(
				"planningpoker/gamenotification", HttpMethod.POST);
		// Set the game to update
		request.setBody(newGN.toJSON());
		// Add observer to get response
		request.addObserver(observer);
		// Send the update request
		request.send();
	}
}
