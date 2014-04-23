/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.requirement.observers;

import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirement.controllers.UpdateRequirementController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirement.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirement.models.RequirementModel;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * @author andrew
 *
 */
public class UpdateRequirementRequestObserver implements RequestObserver {

	private final UpdateRequirementController controller;
	
	public UpdateRequirementRequestObserver(UpdateRequirementController controller) {
		this.controller = controller;
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		final ResponseModel response = iReq.getResponse();
		
		// The Requirement that got updated
		Requirement requirement = Requirement.fromJSON(response.getBody());
		
		try {
			Requirement realReq = RequirementModel.getInstance().getRequirementById(requirement.getIdentity());
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			System.err.println(requirement.getName() + ": Does not exist");
		}
		
		System.out.println("The request to update a Requirement has succeeded!");
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseError(IRequest iReq) {
		System.err.println("Response Error: " + iReq.getResponse().getStatusMessage());
	}
	
	/**
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(edu.wpi.cs.wpisuitetng.network.models.IRequest, java.lang.Exception)
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		System.err.println("The request to update a game failed with exception: "
				+ exception.getMessage());
	}
}
