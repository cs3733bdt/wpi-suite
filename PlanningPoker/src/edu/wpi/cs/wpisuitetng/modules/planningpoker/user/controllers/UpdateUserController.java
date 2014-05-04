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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.user.controllers;

import java.util.logging.Level;
import java.util.logging.Logger;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.user.observers.UpdateUserRequestObserver;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class UpdateUserController {

	private static UpdateUserController instance = null;
	private UpdateUserRequestObserver observer;
	private static final Logger logger = Logger.getLogger(UpdateUserController.class
			.getName());
	private UpdateUserController() {
		observer = new UpdateUserRequestObserver(this);
	}

	/**
	 * @return a new instance of an UpdateGameController
	 */
	public static UpdateUserController getInstance() {
		if (instance == null) {
			instance = new UpdateUserController();
		}
		return instance;
	}

	/**
	 * updates a User with the same username?? on server
	 * 
	 * @param newUser
	 *            User to be updated
	 */
	public void updateUser(User newUser) {
		logger.log(Level.INFO,"Updating user on server");
		// Update request
		Request request = Network.getInstance().makeRequest("core/user",
				HttpMethod.POST);
		// Set the game to update
		request.setBody(newUser.toJSON());
		// Add observer to get response
		request.addObserver(observer);
		// Send the update request
		request.send();
	}
}
