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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.controllers;

import java.util.logging.Level;
import java.util.logging.Logger;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.models.PPRequirement;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.observers.UpdatePPRequirementRequestObserver;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This controller responds when the user does something to update the game.
 * 
 * @author Chris Knapp
 */
public class UpdatePPRequirementController {

	private static UpdatePPRequirementController instance = null;
	private UpdatePPRequirementRequestObserver observer;
	private static final Logger logger = Logger.getLogger(UpdatePPRequirementController.class
			.getName());
	private UpdatePPRequirementController() {
		observer = new UpdatePPRequirementRequestObserver(this);
	}

	/**
	 * @return a new instance of an UpdateGameController
	 */
	public static UpdatePPRequirementController getInstance() {
		if (instance == null) {
			instance = new UpdatePPRequirementController();
		}
		return instance;
	}

	/**
	 * updates a game with the same UUID on server
	 * 
	 * @param newReq
	 *            Game to be updated TODO: Need model for game data
	 */
	public void updateRequirement(PPRequirement newReq) {
		logger.log(Level.INFO, "Updating " + newReq.getName() + " to server");
		// Update request
		Request request = Network.getInstance().makeRequest(
				"planningpoker/requirement", HttpMethod.POST);
		// Set the game to update
		request.setBody(newReq.toJSON());
		// Add observer to get response
		request.addObserver(observer);
		// Send the update request
		request.send();
	}
}
