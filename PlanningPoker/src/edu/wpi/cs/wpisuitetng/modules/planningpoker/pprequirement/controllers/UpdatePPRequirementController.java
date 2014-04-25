package edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.controllers;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.models.PPRequirement;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.observers.UpdateRequirementRequestObserver;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class UpdatePPRequirementController {
	private static UpdatePPRequirementController instance;
	private UpdateRequirementRequestObserver observer;
	
	private UpdatePPRequirementController() {
		observer = new UpdateRequirementRequestObserver(this);
	}
	
	/**
	 * @return a new instance of an UpdateGameController
	 */
	public static UpdatePPRequirementController getInstance() {
		if(instance == null) {
			instance = new UpdatePPRequirementController();
		}
		return instance;
	}
	
	/**
	 * updates a game with the same UUID on server
	 * @param newGame Game to be updated
	 * TODO: Need model for game data
	 */
	public void updatePPRequirement(PPRequirement newReq) {
		System.out.println("Updating " + newReq.getName() + " to server");
		// Update request
		Request request = Network.getInstance().makeRequest("planningpoker/requirement", HttpMethod.POST);
		// Set the game to update
		request.setBody(newReq.toJSON());
		// Add observer to get response
		request.addObserver(observer);
		// Send the update request
		request.send();
	}

}
