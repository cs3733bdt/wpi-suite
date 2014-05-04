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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.game.observers;

import java.util.logging.Level;
import java.util.logging.Logger;

import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.controllers.UpdateGameController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.GameModel;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * This observer is called when a response is received from a request to the
 * server to update a game
 * 
 * @author Chris Knapp
 */
public class UpdateGameRequestObserver implements RequestObserver {
	private static final Logger logger = Logger.getLogger(UpdateGameRequestObserver.class
			.getName());
	/**
	 * We don't actually use the controller, in the defect tracker they use it
	 * to print error messages.
	 */
	private final UpdateGameController controller;

	/**
	 * Constructs an observer for updating games controller
	 * 
	 * @param controller
	 *            updateGameController to be observed
	 */
	public UpdateGameRequestObserver(UpdateGameController controller) {
		this.controller = controller;
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		final ResponseModel response = iReq.getResponse();
		Game realGame;
		// The game that got updated
		Game game = Game.fromJSON(response.getBody());
		//Retrieve game from game model based on the game ID from the response
		try {
			realGame = GameModel.getInstance().getGameById(game.getIdentity());
					
		} catch (NotFoundException e) {
			logger.log(Level.WARNING, "Game does not exist.", e);
			realGame = game;
		}

		// Send out email, text, and facebook notifications on game creation
		if (!realGame.isNotifiedOfCreation() && realGame.isActive()) {
				// Set the project of the game, without this it throws a null
				// pointer
				// if the game is created/added on an update call
				realGame.setProject(game.getProject());
				// Set notified before sending notifications to remove looping
				// possibility
				realGame.setNotifiedOfCreation(true);
				realGame.notifyObservers();
				// Finally send
				realGame.sendNotifications();
			// Send out email, text, and facebook notifications on game
			// completion
		} else if (!realGame.isNotifiedOfCompletion() && realGame.isComplete()) {
				// Set notified before sending notifications to remove looping
				// possibility
				realGame.setProject(game.getProject());
				realGame.setNotifiedOfCompletion(true);
				realGame.notifyObservers();
				// Finally Send
				realGame.sendNotifications();
		}

		logger.log(Level.INFO,"The request to update a game has succeeded!");
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseError(IRequest iReq) {
		logger.log(Level.WARNING,"Response Error: "
				+ iReq.getResponse().getStatusMessage());
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(edu.wpi.cs.wpisuitetng.network.models.IRequest,
	 *      java.lang.Exception)
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		logger.log(Level.WARNING,"The request to update a game failed with exception: "
						+ exception.getMessage());
	}
}
