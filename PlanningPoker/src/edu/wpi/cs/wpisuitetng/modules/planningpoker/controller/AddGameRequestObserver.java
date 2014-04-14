/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Bobby Drop Tables
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.game.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.game.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * This observer is called when a response is received from a request to the server to add a game
 * 
 * ******Need to be modified according to the methods in the game models class******
 * @author tianchanggu
 *
 */
public class AddGameRequestObserver implements RequestObserver{
	private final AddGameController controller;
	private final Game theGame;
	
	/**
	 * constructs an AddGameRequestObserver with a controller and a game to observe
	 * @param controller the controller acting on the game
	 * @param theGame the game being sent through
	 */
	public AddGameRequestObserver(AddGameController controller, Game theGame){
		this.controller=controller;
		this.theGame = theGame;
	}
	
	/**
	 * Parse the details of the new game that was received from the server 
	 * then pass them to the controller
	 * 
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest iReq){
		// Get the response to the given request
		final ResponseModel response = iReq.getResponse();
		
		// Parse the name of the game out of the response body
		//******need to modified to parse the creator from the game model at the same time******
		Game game = Game.fromJSON(response.getBody());
		
		if (!game.isNotifiedOfCreation() && game.isActive()) {
			// Send out email, text, and facebook notifications for game creation
			game.sendNotifications();
			game.setNotifiedOfCreation(true);
			//GameModel.getInstance().update(game, true);
		} else if (!game.isNotifiedOfCompletion() && game.isComplete()) {
			// Send out email, text, and facebook notifications for game completion
			// TODO make a different method for sending completion text
			game.sendNotifications();
			game.setNotifiedOfCompletion(true);
			//GameModel.getInstance().update(game, true);
		}
		
		System.out.println("The request to add a game has succeeded!");
	}
	
	@Override
	public void responseError(IRequest iReq) {
		System.err.println("The request to add a Game failed. Response Error! " + iReq.getResponse().toString());
		redisplayGame();
	}

	@Override
	public void fail(IRequest iReq, Exception exception) {
		System.err.println("The request to add a Game failed.");
		redisplayGame();
	}
	
	private void redisplayGame(){
		GameModel.getInstance().removeGameFromModel(theGame);
		ViewEventController.getInstance().updateGame(theGame, true);
	}
}
