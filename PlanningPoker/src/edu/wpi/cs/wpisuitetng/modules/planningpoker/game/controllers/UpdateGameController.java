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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.game.controllers;

import java.util.logging.Level;
import java.util.logging.Logger;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.observers.UpdateGameRequestObserver;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This controller responds when the user does something to update the game.
 * 
 * @author Chris Knapp
 */
public class UpdateGameController {

	private static UpdateGameController instance = null;
	private UpdateGameRequestObserver observer;
	private static final Logger logger = Logger.getLogger(UpdateGameController.class
			.getName());
	private UpdateGameController() {
		observer = new UpdateGameRequestObserver(this);
	}

	/**
	 * @return a new instance of an UpdateGameController
	 */
	public static UpdateGameController getInstance() {
		if (instance == null) {
			instance = new UpdateGameController();
		}
		return instance;
	}

	/**
	 * updates a game with the same UUID on server
	 * 
	 * @param newGame
	 */
	public void updateGame(Game newGame) {
		logger.log(Level.INFO,"Updating game on server");
		
		// Send out email, text, and facebook notifications on game creation
				if (!newGame.isNotifiedOfCreation() && newGame.isActive()) {
						// Set the project of the game, without this it throws a null
						// pointer

						// Set notified before sending notifications to remove looping
						// possibility
						newGame.setNotifiedOfCreation(true);
						// Finally send
						newGame.sendNotifications();
					// Send out email, text, and facebook notifications on game
					// completion
				} else if (!newGame.isNotifiedOfCompletion() && newGame.isComplete()) {
						// Set notified before sending notifications to remove looping
						// possibility
						newGame.setNotifiedOfCompletion(true);
						// Finally Send
						newGame.sendNotifications();
				}
		
		// Update request
		Request request = Network.getInstance().makeRequest(
				"planningpoker/game", HttpMethod.POST);
		// Set the game to update
		request.setBody(newGame.toJSON());
		// Add observer to get response
		request.addObserver(observer);
		// Send the update request
		request.send();
	}
}
