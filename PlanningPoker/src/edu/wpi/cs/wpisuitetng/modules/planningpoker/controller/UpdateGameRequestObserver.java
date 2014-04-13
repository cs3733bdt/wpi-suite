package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.game.Game;
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
	
	@Override
	public void responseSuccess(IRequest iReq) {
		final ResponseModel response = iReq.getResponse();
		
		final Game game = Game.fromJSON(response.getBody());	
		
		//Send out email, text, and facebook notifications for game creation
		game.sendNotifications();
		
		System.out.println("The request to update a game has succeeded!");
	}
	
	@Override
	public void responseError(IRequest iReq) {
		System.err.println(iReq.getResponse().getStatusMessage());
		System.err.println("The request to update a game failed.");
	}
	
	@Override
	public void fail(IRequest iReq, Exception exception) {
		System.err.println("The request to update a game failed.");
	}

}
