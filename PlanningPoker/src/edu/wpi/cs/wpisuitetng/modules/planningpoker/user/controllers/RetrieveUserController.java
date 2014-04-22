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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirement.controllers.RetrieveRequirementController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirement.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirement.models.RequirementModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirement.observers.RetrieveRequirementRequestObserver;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.user.observers.RetrieveUserRequestObserver;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This controller handles retrieving users from the server.
 * 
 */
public class RetrieveUserController implements ActionListener{

	private RetrieveUserRequestObserver observer;
	private static RetrieveUserController instance;
	private User currentUser;

	/**
	 * Constructs the controller 
	 */
	private RetrieveUserController() {
		
		observer = new RetrieveUserRequestObserver(this);
		retrieveUsers();
	}
	
	/**
	 * getter for an instance of the RetrieveRequirementsController
	 * @return the instance of the RetrieveRequirementsController or creates one if it does not
	 * exist.
	 */
	public static RetrieveUserController getInstance()
	{
		if(instance == null)
		{
			instance = new RetrieveUserController();
		}
		
		return instance;
	}
	
	/**
	 * Sends an HTTP request to store a user when the
	 * update button is pressed
	 * @param e ActionEvent
	 * @see java.awt.event.ActionListener#actionPerformed(ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Send a request to the core to save this requirement
		final Request request = 
				Network.getInstance().makeRequest("core/user", HttpMethod.GET);
		request.addObserver(observer); 
		request.send();
	}
	
	/**
	 * Sends an HTTP request to retrieve the current user
	 */
	public void retrieveUsers() {
		final Request request = 
				Network.getInstance().makeRequest("core/user", HttpMethod.GET);
		request.addObserver(observer); 
		request.send();
	}

	/**
	 * Get the users from the server in order to set the currentUser
	 * This method is called by the RetrieveUserRequestObserver
	 * 
	 * @param users array of users received from the server
	 */
	public void receivedUsers(User[] users) {
		
		String currentUsername = ConfigManager.getInstance().getConfig().getUserName();
		// Make sure the response was not null
		if (users != null) {
			//Compare the current username to the array of users to find correct User
			for (User u : users){
				if(u.getUsername().equals(currentUsername)){
					currentUser = u;
					return;
				}
			}
			
		}
	}
	
	public User getCurrentUser(){
		return currentUser;
	}
}
