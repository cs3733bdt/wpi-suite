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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.requirement.observers;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirement.controllers.AddRequirementController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirement.models.Requirement;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * This observer is called when a response is received from a request
 * to the server to add a requirement.
 * @author tianchanggu
 *
 */
public class AddRequirementRequestObserver implements RequestObserver {
	
	private AddRequirementController controller;
	
	/**
	 * Constructs the observer given an AddRequirementController
	 * @param controller the controller used to add requirements
	 */
	public AddRequirementRequestObserver(AddRequirementController controller) {
		this.controller = controller;
	}
	
	/**
	 * Parse the requirement that was received from the server then pass them to
	 * the controller.
	 * 
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		// Get the response to the given request
		final ResponseModel response = iReq.getResponse();
		
		// Parse the requirement out of the response body
		final Requirement requirement = Requirement.fromJson(response.getBody());	
		
		System.out.println("The request to add a requirement succeeded.");
		
	}

	/**
	 * Takes an action if the response results in an error.
	 * Specifically, outputs that the request failed.
	 * @param iReq IRequest

	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(IRequest)
	 */
	@Override
	public void responseError(IRequest iReq) {
		System.err.println("The request to add a requirement failed.");
	}

	/**
	 * Takes an action if the response fails.
	 * Specifically, outputs that the request failed.
	 * @param iReq IRequest
	 * @param exception Exception
	
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(IRequest, Exception)
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		System.err.println("The request to add a requirement failed.");
	}
}
