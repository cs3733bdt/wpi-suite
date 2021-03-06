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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.models.PPRequirement;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.models.PPRequirementModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.observers.RetrievePPRequirementRequestObserver;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This controller coordinates retrieving all of the requirements from the
 * server.
 * 
 * @author tianchanggu
 * 
 */
public class RetrievePPRequirementController implements ActionListener {

	private RetrievePPRequirementRequestObserver observer;
	private static RetrievePPRequirementController instance = null;

	/**
	 * Constructs the controller given a RequirementModel
	 */
	private RetrievePPRequirementController() {

		observer = new RetrievePPRequirementRequestObserver(this);
	}

	/**
	 * getter for an instance of the RetrieveRequirementsController
	 * 
	 * @return the instance of the RetrieveRequirementsController or creates one
	 *         if it does not exist.
	 */
	public static RetrievePPRequirementController getInstance() {
		if (instance == null) {
			instance = new RetrievePPRequirementController();
		}

		return instance;
	}

	/**
	 * Sends an HTTP request to store a requirement when the update button is
	 * pressed
	 * 
	 * @param e
	 *            ActionEvent
	 * @see java.awt.event.ActionListener#actionPerformed(ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Send a request to the core to save this requirement
		final Request request = Network.getInstance().makeRequest(
				"requirementmanager/requirement", HttpMethod.GET);
		request.addObserver(observer);
		request.send();
	}

	/**
	 * Sends an HTTP request to retrieve all requirements
	 */
	public void retrieveRequirements() {
		final Request request = Network.getInstance().makeRequest(
				"requirementmanager/requirement", HttpMethod.GET);
		request.addObserver(observer);
		request.send();
	}

	/**
	 * Add the given requirements to the local model (they were received from
	 * the core). This method is called by the
	 * RetrieveRequirementsRequestObserver
	 * 
	 * @param requirements
	 *            array of requirements received from the server
	 */
	public void receivedRequirements(PPRequirement[] requirements) {
		PPRequirementModel rModel = PPRequirementModel.getInstance();
		// Make sure requirements exist in the Requirement Manager
		if (requirements != null) {
			rModel.addRequirements(requirements);
		}
	}
}
