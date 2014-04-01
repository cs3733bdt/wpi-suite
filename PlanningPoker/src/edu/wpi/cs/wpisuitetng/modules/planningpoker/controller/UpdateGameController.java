

package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;


/**
 * This controller responds when the user clicks the Edit button 
 * 
 * @author Chris Knapp
 *
 */
public class UpdateGameController {
	
	private static UpdateGameController instance;
	private UpdateGameRequestObserver observer;
	
	private UpdateGameController() {
		observer = new UpdateGameRequestObserver(this);
	}
	
	public static UpdateGameController getInstance() {
		if(instance == null) {
			instance = new UpdateGameController();
		}
		return instance;
	}
	
	//Need model for game data
	public void updateGame(Game newGame) {
		Request request = Network.getInstance().makeRequest("planningpoker/game", HttpMethod.POST);
		request.setBody(newGame.toJSON());
		request.addObserver(observer);
		request.send();
	}
	
	

}
