package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This controller adds the name of game and the game creator to the model
 * as a new game when the user clicks the create a new game button.
 * 
 * ******This class should be modified after the planningpoker.models and .view are finished.******
 * @author tianchanggu
 *
 */
public class AddGameController implements ActionListener {
	private final GameModel model;
	
	/**
	 * Construct an AddGameController for the game model.
	 * @param model the game model containing the name of game and the creator
	 * @param view the view where the user created a new game
	 */
	
	public AddGameController(GameModel model){
		this.model=model;
		//delete this line of comment after adding this.view=view
		//and adding the parameter view in this constructor
	}
	
	/*
	 * This method is called when the user click the create a new game button.
	 * ******This method should be modified after the planningpoker.view is created.******
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent event){
		
	}
	
	/**
	 * When the name of a new game is received back from the server, add it to the local model.
	 * ******Need to be modified after the relative method is created in the model class******
	 * @param game
	 */
	public void addGameToModel(Game name){
		
	}
	
	/**
	 * When the name of the game creator is received back from the server, add it to the local model.
	 * ******Need to be modified after the relative method is created in the model class******
	 * @param creator
	 */
	public void addCreatorToModel(Game creator){
		
	}
	
}
