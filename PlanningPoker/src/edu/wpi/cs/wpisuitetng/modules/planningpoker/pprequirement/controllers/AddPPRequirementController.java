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

import edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.models.PPRequirement;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.observers.AddPPRequirementRequestObserver;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This controller responds when the user
 * add the contents of the requirement text fields to the model as a new
 * requirement.
 * @author tianchanggu
 *
 */
public class AddPPRequirementController{
	
	private static AddPPRequirementController instance;
	private AddPPRequirementRequestObserver observer;
	
	/**
	 * Construct an AddRequirementController for the given model, view pair
	 */
	private AddPPRequirementController() {
		observer = new AddPPRequirementRequestObserver(this);
	}
	
	/**
	 * @return the instance of the AddRequirementController or creates one if it does not exist.
	 */
	public static AddPPRequirementController getInstance()
	{
		if(instance == null)
		{
			instance = new AddPPRequirementController();
		}
		
		return instance;
	}

	/**
	 * This method adds a requirement to the server.
	 * @param newRequirement is the requirement to be added to the server.
	 */
	public void addRequirement(PPRequirement newRequirement) 
	{
		final Request request = 
				Network.getInstance().makeRequest("planningpoker/requirement", HttpMethod.PUT);
		request.setBody(newRequirement.toJSON()); 
		request.addObserver(observer); 
		request.send(); 
	}
}

