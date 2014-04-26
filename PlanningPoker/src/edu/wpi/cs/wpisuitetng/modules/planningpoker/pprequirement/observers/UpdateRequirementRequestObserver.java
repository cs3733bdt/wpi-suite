/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.observers;

import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.controllers.UpdatePPRequirementController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.models.PPRequirement;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.models.PPRequirementModel;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * @author andrew
 *
 */
public class UpdateRequirementRequestObserver implements RequestObserver {

	private final UpdatePPRequirementController controller;
	
	public UpdateRequirementRequestObserver(UpdatePPRequirementController controller) {
		this.controller = controller;
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		final ResponseModel response = iReq.getResponse();
		
		// The Requirement that got updated
		PPRequirement requirement = PPRequirement.fromJSON(response.getBody());
		
		try {
			PPRequirement realReq = PPRequirementModel.getInstance().getRequirementById(requirement.getIdentity());
		} catch (NotFoundException e) {
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
