package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * This observer is called when a response is received from a request to the server to add a game
 * 
 * ******Need to be modified according to the methods in the game models class******
 * @author tianchanggu
 *
 */
public class AddGameRequestObserver implements RequestObserver{
	private final AddGameController controller;
	
	public AddGameRequestObserver(AddGameController controller){
		this.controller=controller;
	}
	
	/**
	 * Parse the details of the new game that was received from the server 
	 * then pass them to the controller
	 * 
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest iReq){
		// Get the response to the given request
		final ResponseModel response = iReq.getResponse();
		
		// Parse the name of the game out of the response body
		//******need to modified to parse the creator from the game model at the same time******
		final Game name = Game.fromJSON(response.getBody());
		final Game creator = Game.fromJSON(response.getBody());
		
		// Pass the messages back to the controller
		controller.addGameToModel(name);	
		controller.addCreatorToModel(creator);
	}
	
	@Override
	public void responseError(IRequest iReq) {
		System.err.println("The request to add a Game failed.");
	}

	@Override
	public void fail(IRequest iReq, Exception exception) {
		System.err.println("The request to add a Game failed.");
	}
}
