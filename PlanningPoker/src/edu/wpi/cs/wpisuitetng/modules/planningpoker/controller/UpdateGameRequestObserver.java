package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * This observer is called when a response is received from a request
 * to the server to update a game
 * 
 * @author Chris Knapp
 *
 */

public class UpdateGameRequestObserver implements RequestObserver{
	
	private final UpdateGameController controller;
	
	public UpdateGameRequestObserver(UpdateGameController controller) {
		this.controller = controller;
	}
	
	public void responseSuccess(IRequest iReq) {
		final ResponseModel response = iReq.getResponse();
	}
	
	public void responseError(IRequest iReq) {
		System.err.println(iReq.getResponse().getStatusMessage());
		System.err.println("The request to update a game failed.");
	}
	
	public void fail(IRequest iReq, Exception exception) {
		System.err.println("The request to update a game failed.");
	}

}
