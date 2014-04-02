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

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This controller adds the name of game and the game creator to the model as a
 * new game when the user clicks the create a new game button.
 * 
 * ******This class should be modified after the planningpoker.models and .view
 * are finished.******
 * @author Andrew Busch
 * @author tianchanggu
 * @author Jonathan Leitschuh
 * 
 */
public class AddGameController {
	private static AddGameController instance;
	private AddGameRequestObserver observer;
	
	private AddGameController(){
		observer = new AddGameRequestObserver(this);
	}
	
	public static AddGameController getInstance(){
		if(instance == null){
			instance = new AddGameController();
		}
		return instance;
	}
	
	/**
	 * This method assd a requirement to the server
	 * @param newGame is the game to add to the server
	 */
	public void addGame(Game newGame){
		final Request request = Network.getInstance().makeRequest("planningpoker/game", HttpMethod.PUT);
		request.setBody(newGame.toJSON());
		request.addObserver(observer);
		request.send();
	}
	
}
