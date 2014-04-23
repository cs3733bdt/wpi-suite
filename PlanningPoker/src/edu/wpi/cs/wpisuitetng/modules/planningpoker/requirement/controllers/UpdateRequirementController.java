package edu.wpi.cs.wpisuitetng.modules.planningpoker.requirement.controllers;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirement.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirement.observers.UpdateRequirementRequestObserver;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class UpdateRequirementController {
	private static UpdateRequirementController instance;
	private UpdateRequirementRequestObserver observer;
	
	private UpdateRequirementController() {
		observer = new UpdateRequirementRequestObserver(this);
	}
	
	/**
	 * @return a new instance of an UpdateGameController
	 */
	public static UpdateRequirementController getInstance() {
		if(instance == null) {
			instance = new UpdateRequirementController();
		}
		return instance;
	}
	
	/**
	 * updates a game with the same UUID on server
	 * @param newGame Game to be updated
	 * TODO: Need model for game data
	 */
	public void updateRequirement(Requirement newReq) {
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
