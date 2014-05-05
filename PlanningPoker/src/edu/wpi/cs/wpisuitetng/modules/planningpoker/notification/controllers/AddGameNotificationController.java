/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Bobby Drop Tables
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.notification.controllers;

import java.util.logging.Level;
import java.util.logging.Logger;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.notification.models.GameNotification;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.notification.observers.AddGameNotificationRequestObserver;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This controller adds the name of game and the game creator to the model as a
 * new game when the user clicks the create a new game button.
 * 
 * ******This class should be modified after the planningpoker.models and .view
 * are finished.******
 */

public class AddGameNotificationController {
	
	private static AddGameNotificationController instance = null;
	private static final Logger logger = Logger.getLogger(AddGameNotificationController.class
			.getName());
	
	private AddGameNotificationController() {
	}

	/**
	 * @return returns a new instance of an AddGameController
	 */
	public static AddGameNotificationController getInstance() {
		if (instance == null) {
			instance = new AddGameNotificationController();
		}
		return instance;
	}

	/**
	 * This method adds a game to the server
	 * 
	 * @param newGame
	 *            is the game to add to the server
	 */
	public void addGameNotification(GameNotification newGN) {
		logger.log(Level.INFO,"Adding: " + newGN.getName() + " to server");
		final Request request = Network.getInstance().makeRequest(
				"planningpoker/gamenotification", HttpMethod.PUT);
		request.setBody(newGN.toJSON());
		final AddGameNotificationRequestObserver observer = new AddGameNotificationRequestObserver(
				this, newGN);
		request.addObserver(observer);
		request.send();
	}
}
