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

import java.util.logging.Level;
import java.util.logging.Logger;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.controllers.UpdatePPRequirementController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.models.PPRequirement;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * This observer is called when a response is received from a request
 * to the server to update a game
 * 
 * @author Chris Knapp
 */
public class UpdatePPRequirementRequestObserver implements RequestObserver {
	/** We don't actually use the controller,
	 * in the defect tracker they use it to print
	 * error messages.
	 */
	private final UpdatePPRequirementController controller;
	private static final Logger logger = Logger.getLogger(UpdatePPRequirementRequestObserver.class
			.getName());
	/**
	 * Constructs an observer for updating games controller
	 * @param controller updateGameController to be observed
	 */
	public UpdatePPRequirementRequestObserver(UpdatePPRequirementController controller) {
		this.controller = controller;
	}
	
	/**
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		final ResponseModel response = iReq.getResponse();
		
		// The game that got updated
		PPRequirement req = PPRequirement.fromJSON(response.getBody());
		
		logger.log(Level.INFO,"The request to update a game has succeeded!");
	}
	
	/**
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseError(IRequest iReq) {
		logger.log(Level.WARNING,"Response Error: " + iReq.getResponse().getStatusMessage());
	}
	
	/**
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(edu.wpi.cs.wpisuitetng.network.models.IRequest, java.lang.Exception)
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		logger.log(Level.WARNING, "The request to update a game failed");
	}
}
