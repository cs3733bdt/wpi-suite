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

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.MainView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation.CreateGamePanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.AddGameRequestObserver;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This controller adds the name of game and the game creator to the model as a
 * new game when the user clicks the create a new game button.
 * 
 * ******This class should be modified after the planningpoker.models and .view
 * are finished.******
 *  * @author Andrew Busch
 * 
 * @author tianchanggu
 * 
 */
public class AddGameController implements ActionListener {
	private final GameModel model;
	private final CreateGamePanel view;
	private String newGameName;

	/**
	 * Construct an AddGameController for the game model.
	 * 
	 * @param model
	 *            the game model containing the name of game and the creator
	 * @param view
	 *            the view where the user created a new game
	 */

	public AddGameController(GameModel model, CreateGamePanel view) {
		this.model = model;
		this.view = view;
		newGameName = "";
		
		// delete this line of comment after adding this.view=view
		// and adding the parameter view in this constructor
	}

	/**
	 * This method is called when the user click the create a new game button.
	 * ******This method should be modified after the planningpoker.view is
	 * created.******
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent event){
		newGameName = view.getNameText();
		System.out.println(newGameName);
		System.out.println("Hi I made it this far.");
		
		String currentUser = ConfigManager.getConfig().getUserName(); //Gets the currently active user
		
		final Request request = Network.getInstance().makeRequest("planningpoker/game", HttpMethod.PUT); // PUT == create
		request.setBody(new Game(newGameName, currentUser, true).toJSON()); // put the new message in the body of the request
		request.addObserver(new AddGameRequestObserver(this)); // add an observer to process the response
		request.send(); // send the request
		System.out.println("Yo I made it this far too.");
	}

	/**
	 * When the name of a new game is received back from the server, add it to
	 * the local model. ******Need to be modified after the relative method is
	 * created in the model class******
	 * 
	 * @param newGame
	 * 			Game to be added to the data model
	 */
	public void addGameToModel(Game newGame) {
		model.addGame(newGame);
	}

	/**
	 * When the name of the game creator is received back from the server, add
	 * it to the local model. ******Need to be modified after the relative
	 * method is created in the model class******
	 * 
	 * @param creator
	 */
	public void addCreatorToModel(Game creator) {

	}

}
