/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Bobby Drop Tables
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.game.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.observers.GetGameRequestObserver;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Used in order to get the games off of the database.
 * This is used to fill the GameModel from the database on build
 * The first call of this method is inside of the GameTree on paint method
 * This is done in order to prevent trying get the data before the network has been instantiated.
 * 
 * @author Andrew Busch
 */
public class GetGameController implements ActionListener {
	private final GetGameRequestObserver observer;
	private static GetGameController instance;
	private Timer timer;
	private boolean isRunning = false;

	/**
	 * Gets the singleton instance of the GetGameController
	 * @return the current instance of the GetGameController
	 */
	public static GetGameController getInstance() {
		if (instance == null) {
			instance = new GetGameController();
		}
		return instance;
	}
	
	/**
	 * The constructor for the GetGameController
	 * Private in order to prevent multiple instantiations
	 */
	private GetGameController() {
		observer = new GetGameRequestObserver(this);
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public synchronized void actionPerformed(ActionEvent e) {
	    // Send a request to the core to read/get this Game
	    final Request request = 
	    		Network.getInstance().makeRequest("planningpoker/game", HttpMethod.GET);
	    // add an observer to process the response
	    request.addObserver(observer); 
	    // send the request
	    request.send();
	}
	
	/**
	 * Sends an HTTP request to retrieve all requirements
	 */
	public synchronized void retrieveGames() {
		if(!isRunning) {
			timer = new Timer(25000, this);
			timer.setInitialDelay(9000);
			timer.setCoalesce(true);
			timer.start();
			isRunning = true;
		}
		// Send a request to the core to read/get this Game
		final Request request = 
				Network.getInstance().makeRequest("planningpoker/game", HttpMethod.GET);
		// add an observer to process the response
		request.addObserver(observer);
		// send the request
		request.send(); 
	}
	
	/**
	 * Add the given Games to the local model (they were received from the core).
	 * This method is called by the GetGamesRequestObserver
	 * @param games an array of Games received from the server
	 */
	public synchronized void receivedGames(Game[] games) {
		System.out.println("The size of the list returned from the server is: " + games.length);
		for(Game game : games) {
			System.out.println("\t" + game.getName() + " " + game.getIdentity());
		}
	    // Make sure the response was not null
	    if (games != null) {
	        // add the Games to the local model
	        GameModel.getInstance().updateGames(games);
	    }
	}
}
