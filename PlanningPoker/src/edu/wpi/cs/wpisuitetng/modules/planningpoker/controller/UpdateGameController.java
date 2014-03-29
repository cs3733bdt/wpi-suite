

package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

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
	public void updateGame() {
		//placeholder comment
	}
	
	

}
