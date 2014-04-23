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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.observers;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.controllers.RetrievePPRequirementController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.models.PPRequirement;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
 * This observer handles responses to requests for all requirements
 * 
 * @author tianchanggu
 *
 */
public class RetrievePPRequirementRequestObserver implements RequestObserver {
	
	private RetrievePPRequirementController controller;
	
	/**
	 * Constructs the observer given a RetrieveRequirementsController
	 * @param controller the controller used to retrieve requirements
	 */
	public RetrievePPRequirementRequestObserver(RetrievePPRequirementController controller) {
		this.controller = controller;
	}

	/**
	 * Parse the requirements out of the response body and pass them to the controller
	 * 
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		// Convert the JSON array of requirements to a Requirement object array
		PPRequirement[] requirements = PPRequirement.fromJsonArray(iReq.getResponse().getBody());

		// Pass these Requirements to the controller
		controller.receivedRequirements(requirements);
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseError(IRequest iReq) {
		fail(iReq, null);
	}

	/**
	 * Put an error requirement in the PostBoardPanel if the request fails.
	 * 
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(edu.wpi.cs.wpisuitetng.network.models.IRequest, java.lang.Exception)
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		PPRequirement[] errorRequirement = { new PPRequirement("Error", "error desc") };
		controller.receivedRequirements(errorRequirement);
	}

}
