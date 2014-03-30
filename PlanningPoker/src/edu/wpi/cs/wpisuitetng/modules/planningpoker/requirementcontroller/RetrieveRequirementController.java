package edu.wpi.cs.wpisuitetng.modules.planningpoker.requirementcontroller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirementmodels.Requirement;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirementmodels.RequirementModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This controller coordinates retrieving all of the requirements
 * from the server.
 * 
 * @author tianchanggu
 *
 */
public class RetrieveRequirementController implements ActionListener {

	private RetrieveRequirementRequestObserver observer;
	private static RetrieveRequirementController instance;

	/**
	 * Constructs the controller given a RequirementModel
	 */
	private RetrieveRequirementController() {
		
		observer = new RetrieveRequirementRequestObserver(this);
	}
	
	/**
	
	 * @return the instance of the RetrieveRequirementsController or creates one if it does not
	 * exist. */
	public static RetrieveRequirementController getInstance()
	{
		if(instance == null)
		{
			instance = new RetrieveRequirementController();
		}
		
		return instance;
	}
	
	/**
	 * Sends an HTTP request to store a requirement when the
	 * update button is pressed
	 * @param e ActionEvent
	
	 * @see java.awt.event.ActionListener#actionPerformed(ActionEvent) */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Send a request to the core to save this requirement
		final Request request = Network.getInstance().makeRequest("requirementmanager/requirement", HttpMethod.GET); // GET == read
		request.addObserver(observer); // add an observer to process the response
		request.send(); // send the request
	}
	
	/**
	 * Sends an HTTP request to retrieve all requirements
	 */
	public void retrieveRequirements() {
		final Request request = Network.getInstance().makeRequest("requirementmanager/requirement", HttpMethod.GET); // GET == read
		request.addObserver(observer); // add an observer to process the response
		request.send(); // send the request
	}

	/**
	 * Add the given requirements to the local model (they were received from the core).
	 * This method is called by the RetrieveRequirementsRequestObserver
	 * 
	 * @param requirements array of requirements received from the server
	 */
	public void receivedRequirements(Requirement[] requirements) {
		// Empty the local model to eliminate duplications
		RequirementModel.getInstance().emptyModel();
		
		// Make sure the response was not null
		if (requirements != null) {
			
			// add the requirements to the local model
			RequirementModel.getInstance().addRequirements(requirements);
		}
	}
}
