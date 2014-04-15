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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.game.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.game.GameModel;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * This observer is called when a response is received from a request
 * to the server to update a game
 * 
 * @author Chris Knapp
 *
 */

public class UpdateGameRequestObserver implements RequestObserver{
	
	private final UpdateGameController controller;
	
	/**
	 * Constructs an observer for updating games controller
	 * @param controller updateGameController to be observed
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
		
		Game game = Game.fromJSON(response.getBody());
		
		System.out.println("Game name: " + game.getName());
		
		if (!game.isNotifiedOfCreation() && game.isActive()) {
			// Send out email, text, and facebook notifications for game creation
			Game realGame = GameModel.getInstance().getGameById(game.getIdentity());
			if (!realGame.equals(null)) {
				realGame.setProject(game.getProject());
				realGame.setNotifiedOfCreation(true);
				realGame.sendNotifications();
			} else {
				System.err.println(game.getName() + ": Does not exist");
			}
		} else if (!game.isNotifiedOfCompletion() && game.isComplete()) {
			// Send out email, text, and facebook notifications for game completion
			// TODO make a different method for sending completion text
			Game realGame = GameModel.getInstance().getGameById(game.getIdentity());
			if (!realGame.equals(null)) {
				realGame.setNotifiedOfCreation(true);
				realGame.sendNotifications();
			} else {
				System.err.println(game.getName() + ": Does not exist");
			}
		}
		
		System.out.println("The request to update a game has succeeded!");
	}
	
	/**
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseError(IRequest iReq) {
		System.err.println(iReq.getResponse().getStatusMessage());
		System.err.println("The request to update a game failed.");
	}
	
	/**
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(edu.wpi.cs.wpisuitetng.network.models.IRequest, java.lang.Exception)
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		System.err.println("The request to update a game failed.");
	}

}
