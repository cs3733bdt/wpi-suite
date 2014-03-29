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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * @author Andrew Busch
 *
 */
public class GetGameController implements ActionListener {

	private final GameModel model;

	public GetGameController(GameModel model) {
		this.model = model;
	}
	
	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
	    // Send a request to the core to save this Game
	    final Request request = Network.getInstance().makeRequest("postboard/postboardGame", HttpMethod.GET); // GET == read
	    request.addObserver(new GetGameRequestObserver(this)); // add an observer to process the response
	    request.send(); // send the request
	}
	
	/**
	 * Add the given Games to the local model (they were received from the core).
	 * This method is called by the GetGamesRequestObserver
	 * 
	 * @param Games an array of Games received from the server
	 */
	public void receivedGames(Game[] Games) {
	    // Empty the local model to eliminate duplications
	    model.emptyModel();

	    // Make sure the response was not null
	    if (Games != null) {

	        // add the Games to the local model
	        model.addGames(Games);
	    }
	}
}
