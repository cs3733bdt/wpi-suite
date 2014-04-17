/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Team Bobby Drop Tables
 *******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirement.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.DescriptionJTextArea;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.NameJTextField;

/**
 * Used to create a new Planning Poker game using the input of the user.
 */
public class NewCreateGamePanel extends JSplitPane implements ICreateGamePanel {
	private NewLeftHalfCreateGamePanel leftHalf = new NewLeftHalfCreateGamePanel(this);
	private NewRightHalfCreateGamePanel rightHalf = new NewRightHalfCreateGamePanel(this);
	
	
	private boolean readyToClose = false;
	private boolean readyToRemove = true; // The window starts off ready to
											// remove because no changes have
											// happened
	private Game currentGame;
	
	/**
	 * Creates a NewCreateGamePanel with the game setting the fields for the panel.
	 * This is used to edit an existing game in the model that has not yet been made active
	 * @param game the game that we are editing
	 */
	public NewCreateGamePanel(Game game) {
		currentGame = game;
		leftHalf = new NewLeftHalfCreateGamePanel(this);
		rightHalf = new NewRightHalfCreateGamePanel(this);
		
		
		setLeftComponent(leftHalf);
		setRightComponent(rightHalf);
		rightHalf.setMinimumSize(new Dimension(333, 500));
		setDividerLocation(420);
		
		revalidate();
		repaint();
		
	}
	
	
	/**
	 * Creates a NewCreateGamePanel
	 * This is equivalent to calling NewCreateGamePanel(null)
	 */
	public NewCreateGamePanel(){
		this(null);
	}
	
	public NewCreateGamePanel(Game game, boolean withError) {
		this(game);
		if (withError) {
			JOptionPane
					.showMessageDialog(
							null,
							"\tYour connection to the server has been lost.\n"
									+ "\tYour changes have been resored but no further changes to the server can be made.\n"
									+ "\tPlease save your changes to a text file and restart Janeway.",
							"Network Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void main(String args[]){
		JFrame frame = new JFrame("Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up the content pane.
        frame.add(new NewCreateGamePanel(new Game()));
        frame.setMinimumSize(new Dimension(300, 300));

        //Display the window.
        frame.pack();
        frame.setVisible(true);
	}
	
	public Game getGame(){
		return currentGame;
	}

	
	/**
	 * Checks to see if the panel has unsaved changes
	 * 
	 * @return whether the CreateGamePanel as a whole is ready to be removed.
	 */
	public boolean readyToRemove() {
		if (readyToClose)
			return true;

		// TODO Check fields to see if this window has unsaved changes

		if (readyToRemove) {
			return true;
		} else {
			int result = JOptionPane.showConfirmDialog(this,
					"Discard unsaved changes and close tab?",
					"Discard Changes?", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			return result == 0;
		}

	}
	
	/**
	 * Checks to see if all of this panels sub elements are valid to be saved or launched
	 * @param whether or not to show the error
	 * @return true when the all of this panel's sub elements are valid
	 */
	public boolean validateField(boolean show){
		boolean rightPanelValid = rightHalf.validateField(leftHalf.getErrorField());
		boolean leftPanelValid = leftHalf.validateField(null);
		
		if(show == false){
			leftHalf.getErrorField().setText("");
		}
		
		return leftPanelValid && rightPanelValid;
	}
	

	/**
	 * Triggered when the save game button is pressed using the mouse listener
	 * @return true when a game is sucsessfully added
	 */
	public boolean SaveGamePressed() {
		if(this.validateField(true)){
			saveGame();
			readyToClose = true;
			ViewEventController.getInstance().removeTab(this);
			System.out.println("Add Game Pressed Passed.");
			return true;
		} else {
			System.out.println("Add Game Pressed Failed.");
			return false;
		}
		
	}
	
	/**
	 * Called by the 'Launch Game' action listener
	 * @return
	 */
	public boolean LaunchGamePressed() {
		if(validateField(true)){
			launchGame();
			readyToClose = true;
			ViewEventController.getInstance().removeTab(this);
			System.out.println("Launch Game Pressed Passed.");
			return true;
		} else {
			System.out.println("Launch Game Pressed Failed.");
			return false;
		}
		
	}
	
	/**
	 * Adds the game to the model and to the server and sets it to inactive
	 * @param endDate 
	 */
	public void  saveGame(){	
		if(currentGame == null){
			currentGame = new Game();
			setCurrentGame(false);
			GameModel.getInstance().addGame(currentGame);		//New Game gets added to the server
			System.out.println("Launch Game Pressed Passed.");
		} else{
			setCurrentGame(false);
		}
		ViewEventController.getInstance().refreshGameTable();
		ViewEventController.getInstance().refreshGameTree();
	}
	
	/**
	 * Adds the game to the model and to the server and sets it to active
	 */
	public void  launchGame(){
		if(currentGame == null){
			currentGame = new Game();
			setCurrentGame(true);
			GameModel.getInstance().addGame(currentGame);		//New Game gets added to the server
		} else{
			setCurrentGame(true);
		}
		ViewEventController.getInstance().refreshGameTable();
		ViewEventController.getInstance().refreshGameTree();
	}
	
	/**
	 * Constructs the current game with the data from the fields in this panel
	 * @param active Whether to make this game active or not
	 */
	private void setCurrentGame(boolean active){
		currentGame.setName(getBoxName().getText());
		currentGame.setDescription(getBoxDescription().getText());
		currentGame.setActive(active);
		currentGame.setUsesCards(doesUseCards());
		currentGame.setRequirements(getRequirements());
		currentGame.setEndDate(getEndDateField().getEndDate());
		currentGame.setCreator(ConfigManager.getConfig().getUserName());
		currentGame.notifyObservers();
	}
	
	public void updateButtons(){
		if(validateField(false)){
			leftHalf.getSaveGameButtonPanel().getSaveGameButton().setEnabled(true);
			leftHalf.getLaunchGameButtonPanel().getLaunchGameButton().setEnabled(true);
		}
		else{
			leftHalf.getSaveGameButtonPanel().getSaveGameButton().setEnabled(false);
			leftHalf.getLaunchGameButtonPanel().getLaunchGameButton().setEnabled(false);
		}
	}

	/**
	 * Gets the requirements for this panel
	 * @return the requirements that have been created for this game.
	 */
	private List<Requirement> getRequirements() {
		return rightHalf.getRequirements();
	}

	private DescriptionJTextArea getBoxDescription() {
		return leftHalf.getBoxDescription();
	}

	private NameJTextField getBoxName() {
		return leftHalf.getBoxName();
	}
	
	private NewAddEndDatePanel getEndDateField(){
		return leftHalf.getEndDateField();
	}
	
	private boolean doesUseCards(){
		return leftHalf.doesUseCards();
	}
}