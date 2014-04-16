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

import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.controllers.AddGameController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * This observer is called when a response is received from a request to the server to add a game
 * ******Need to be modified according to the methods in the game models class******
 * @author tianchanggu
 *
 */
public class AddGameRequestObserver implements RequestObserver {
	/**
	 * We don't actually use the controller,
	 * in the defect tracker they use it to print
	 * error messages.
	 */
	private final AddGameController controller;
	private final Game theGame;
	
	/**
	 * constructs an AddGameRequestObserver with a controller and a game to observe
	 * @param controller the controller acting on the game
	 * @param theGame the game being sent through
	 */
	public AddGameRequestObserver(AddGameController controller, Game theGame) {
		this.controller = controller;
		this.theGame = theGame;
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
		
		// The game that got added
		Game game = Game.fromJSON(response.getBody());
		
		// Send out email, text, and facebook notifications on game creation
		if (!game.isNotifiedOfCreation() && game.isActive()) {
			Game realGame = GameModel.getInstance().getGameById(game.getIdentity());
			// getGameByName will return null if a game with that name doesn't exist yet
			// So do a null check
			if (!realGame.equals(null)) {
				// Have to set Project because it doesn't have it yet
				// and will throw a null pointer
				realGame.setProject(game.getProject());
				// Set notified before sending notifications, to ensure no looping
				realGame.setNotifiedOfCreation(true);
				realGame.sendNotifications();
			} else {
				System.err.println(game.getName() + ": Does not exist");
			}
		}
		
		System.out.println("The request to add a game has succeeded!");
	}
	
	/**
	 * Prints out response error message and ensures the game 
	 * doesn't get added to the server. Also removes the
	 * game from the current model.
	 */
	@Override
	public void responseError(IRequest iReq) {
		System.err.println("The request to add a Game failed. Response Error: " + iReq.getResponse().getStatusMessage());
		redisplayGame();
	}

	/**
	 * Called on game add failed. Prints out error message
	 * and removes game from current model.
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		System.err.println("The request to add a Game failed with exception: " + exception.getMessage());
		redisplayGame();
	}
	
	/**
	 * Removes the game from current model and
	 * updates the tree so that it will not show
	 * that game that failed to get added.
	 */
	private void redisplayGame() {
		GameModel.getInstance().removeGameFromModel(theGame);
		ViewEventController.getInstance().updateGame(theGame, true);
	}
}