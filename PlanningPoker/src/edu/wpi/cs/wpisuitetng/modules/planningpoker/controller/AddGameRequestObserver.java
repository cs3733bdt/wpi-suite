/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Bobby Drop Tables
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.notification.EmailNotification;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.notification.FacebookNotification;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.notification.SMSNotification;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.game.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.game.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;
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
	
	private final Game theGame;
	
	public AddGameRequestObserver(AddGameController controller, Game theGame){
		this.controller=controller;
		this.theGame = theGame;
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
		
		// Email Team Users on game creation Success
		EmailNotification en = new EmailNotification(name);
		en.sendEmails();
		
		// Send Text messages to users on game creation success.
		SMSNotification sms = new SMSNotification(name);
		sms.sendSMSMessages();
		
		// Send Facebook notifications to users with stored facebook usernames.
		FacebookNotification fbn = new FacebookNotification(name);
		fbn.sendFacebookNotifications();
		
		System.out.println("The request to add a game has succeeded!");
	}
	
	@Override
	public void responseError(IRequest iReq) {
		System.err.println("The request to add a Game failed. Response Error! " + iReq.getResponse().toString());
		redisplayGame();
	}

	@Override
	public void fail(IRequest iReq, Exception exception) {
		System.err.println("The request to add a Game failed.");
		redisplayGame();
	}
	
	private void redisplayGame(){
		GameModel.getInstance().removeGameFromModel(theGame);
		ViewEventController.getInstance().updateGame(theGame, true);
	}
}
