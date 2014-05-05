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

import java.util.logging.Level;
import java.util.logging.Logger;

import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.controllers.AddGameController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.notification.controllers.GetGameNotificationController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.notification.models.GameNotification;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.notification.models.GameNotificationModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * This observer is called when a response is received from a request to the
 * server to add a game ******Need to be modified according to the methods in
 * the game models class******
 * 
 * @author tianchanggu
 * 
 */
public class AddGameRequestObserver implements RequestObserver {
	/**
	 * We don't actually use the controller, in the defect tracker they use it
	 * to print error messages.
	 */
	private final AddGameController controller;
	private final Game theGame;
	private static final Logger logger = Logger.getLogger(AddGameRequestObserver.class
			.getName());

	/**
	 * constructs an AddGameRequestObserver with a controller and a game to
	 * observe
	 * 
	 * @param controller
	 *            the controller acting on the game
	 * @param theGame
	 *            the game being sent through
	 */
	public AddGameRequestObserver(AddGameController controller, Game theGame) {
		this.controller = controller;
		this.theGame = theGame;
	}

	/**
	 * Parse the details of the new game that was received from the server then
	 * pass them to the controller
	 * 
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		// Get the response to the given request
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
		
		GetGameNotificationController.getInstance().retrieveGameNotifications();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		GameNotification gn = GameNotificationModel.getInstance().getGameNotification(game.getIdentity());

		if (gn != null) {
			// Send out email, text, and facebook notifications on game creation
			if (!gn.getGameCreationNotified() && game.isActive()) {
					gn.setGameCreationNotified(true);
					gn.notifyObservers();
					realGame.sendNotifications();
			}
		} else {
			logger.log(Level.INFO, "The GameNotification is Null for game: " + game.getName());
		}

		logger.log(Level.INFO,"The request to add a game has succeeded!");
	}

	/**
	 * Prints out response error message and ensures the game doesn't get added
	 * to the server. Also removes the game from the current model.
	 */
	@Override
	public void responseError(IRequest iReq) {
		logger.log(Level.WARNING,"The request to add a Game failed. Response Error: "
				+ iReq.getResponse().getStatusMessage());
		redisplayGame();
	}

	/**
	 * Called on game add failed. Prints out error message and removes game from
	 * current model.
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		logger.log(Level.WARNING,"The request to add a Game failed with exception: "
				+ exception.getMessage());
		redisplayGame();
	}

	/**
	 * Removes the game from current model and updates the tree so that it will
	 * not show that game that failed to get added.
	 */
	private void redisplayGame() {
		GameModel.getInstance().removeGameFromModel(theGame);
		ViewEventController.getInstance().updateGame(theGame, true);
	}
}
