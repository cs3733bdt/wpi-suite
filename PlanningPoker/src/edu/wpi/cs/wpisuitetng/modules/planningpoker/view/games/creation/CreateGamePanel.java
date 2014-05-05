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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.notification.models.GameNotification;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.notification.models.GameNotificationModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.models.PPRequirement;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.DescriptionJTextArea;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.IValidateButtons;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.NameJTextField;

/**
 * Used to create a new Planning Poker game using the input of the user.
 */
public class CreateGamePanel extends JSplitPane implements ICreateGamePanel,
		IValidateButtons {
	private LeftHalfCreateGamePanel leftHalf = new LeftHalfCreateGamePanel(this);
	private RightHalfCreateGamePanel rightHalf = new RightHalfCreateGamePanel(
			this);

	private boolean readyToClose = false;
	private boolean readyToRemove = true; // The window starts off ready to
											// remove because no changes have
											// happened
	private Game currentGame;

	private String savedName;
	private String savedDescription;
	private boolean useCards;
	private String savedEndDate;
	private List<PPRequirement> savedRequirements = new ArrayList<PPRequirement>();

	private static final Logger logger = Logger.getLogger(CreateGamePanel.class
			.getName());
	
	/**
	 * Creates a NewCreateGamePanel with the game setting the fields for the
	 * panel. This is used to edit an existing game in the model that has not
	 * yet been made active
	 * 
	 * @param game
	 *            the game that we are editing
	 */
	public CreateGamePanel(Game game) {
		currentGame = game;
		leftHalf = new LeftHalfCreateGamePanel(this);
		rightHalf = new RightHalfCreateGamePanel(this);

		setLeftComponent(leftHalf);
		setRightComponent(rightHalf);
		rightHalf.setMinimumSize(new Dimension(333, 500));
		setDividerLocation(420);

		if (game == null) {
			leftHalf.getLaunchGameButtonPanel().getLaunchGameButton().setEnabled(false);
			leftHalf.getSaveGameButtonPanel().getSaveGameButton().setEnabled(false);
		} else if (!validateField(false, false)) {
			leftHalf.getLaunchGameButtonPanel().getLaunchGameButton().setEnabled(false);
			leftHalf.getSaveGameButtonPanel().getSaveGameButton().setEnabled(false);
		} else {
			leftHalf.getLaunchGameButtonPanel().getLaunchGameButton().setEnabled(true);
			leftHalf.getSaveGameButtonPanel().getSaveGameButton().setEnabled(true);
		}

		revalidate();
		repaint();

		savedName = getBoxName().getText();
		savedDescription = getBoxDescription().getText();
		useCards = leftHalf.doesUseCards();
		savedEndDate = leftHalf.dateToString();
		savedRequirements = rightHalf.getRequirements();

	}

	/**
	 * Creates a NewCreateGamePanel This is equivalent to calling
	 * NewCreateGamePanel(null)
	 */
	public CreateGamePanel() {
		this(null);
		currentGame = null;

	}

	/**
	 * Creates a NewCreateGamePanel with a game's information on it
	 * 
	 * @param game
	 *            The game to be created
	 * @param withError
	 *            sees if there is an error or not
	 */
	public CreateGamePanel(Game game, boolean withError) {
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

	public static void main(String args[]) {
		JFrame frame = new JFrame("Demo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Set up the content pane.
		frame.add(new CreateGamePanel(new Game()));
		frame.setMinimumSize(new Dimension(300, 300));

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public Game getGame() {
		return currentGame;
	}

	/**
	 * Checks to see if the panel has unsaved changes
	 * 
	 * @return whether the CreateGamePanel as a whole is ready to be removed.
	 */
	public boolean readyToRemove() {
		if (readyToClose || !hasChanges()) {
			return true;
		}

		int result = JOptionPane.showConfirmDialog(this,
				"Discard unsaved changes and close tab?", "Discard Changes?",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		return result == 0;

	}

	private boolean containsData() {
		return (!(getBoxName().getText().isEmpty())
				|| !(getBoxDescription().getText().isEmpty()) || !(rightHalf
					.getRequirements().isEmpty()));
	}

	private boolean hasChanges() {
		return leftHalf.hasChanges() || rightHalf.hasChanges();
	}

	/**
	 * Checks to see if all of this panels sub elements are valid to be saved or
	 * launched
	 * 
	 * @param showLabel
	 *            Show the label or not
	 * @param showBox
	 * 			  Show the box or not
	 * @return true when the all of this panel's sub elements are valid
	 */
	public boolean validateField(boolean showLabel, boolean showBox) {
		boolean rightPanelValid;
		boolean leftPanelValid;
		
		rightPanelValid = rightHalf.validateField(leftHalf.getErrorField(), showLabel, showBox);
		leftPanelValid = leftHalf.validateField(leftHalf.getErrorField(), showLabel, showBox);
		
		return leftPanelValid && rightPanelValid;
	}

	/**
	 * Triggered when the save game button is pressed using the mouse listener
	 * 
	 * @return true when a game is sucsessfully added
	 */
	public boolean SaveGamePressed() {
		if (leftHalf.getBoxName().validateField(leftHalf.getErrorField(), true, true)) {
			leftHalf.getErrorField().setText("");
			saveGame();
			readyToClose = true;
			ViewEventController.getInstance().removeTab(this);
			logger.log(Level.INFO,"Add Game Pressed Passed.");
			return true;
		} else {
			logger.log(Level.INFO,"Add Game Pressed Failed.");
			return false;
		}

	}

	/**
	 * Called by the 'Launch Game' action listener
	 * 
	 * @return true if the game was launched/started sucessfully
	 */
	public boolean LaunchGamePressed() {
		if (validateField(true, true)) {
			launchGame();
			readyToClose = true;
			ViewEventController.getInstance().removeTab(this);
			ViewEventController.getInstance().joinGame(currentGame);
			logger.log(Level.INFO,"Launch Game Pressed Passed.");
			return true;
		} else {
			logger.log(Level.INFO,"Launch Game Pressed Failed.");
			return false;
		}

	}

	/**
	 * Adds the game to the model and to the server and sets it to inactive
	 */
	public void saveGame() {
		if (currentGame == null) {
			currentGame = new Game();
			setCurrentGame(false);
			GameNotificationModel.getInstance().addGameNotification(new GameNotification(currentGame.getIdentity()));
			GameModel.getInstance().addGame(currentGame); // New Game gets added
															// to the server
			logger.log(Level.INFO,"Launch Game Pressed Passed.");
		} else {
			setCurrentGame(false);
		}
		ViewEventController.getInstance().refreshGameTree();
	}

	/**
	 * Adds the game to the model and to the server and sets it to active
	 */
	public void launchGame() {
		if (currentGame == null) {
			currentGame = new Game();
			setCurrentGame(true);
			GameNotificationModel.getInstance().addGameNotification(new GameNotification(currentGame.getIdentity()));
			GameModel.getInstance().addGame(currentGame); // New Game gets added
															// to the server
		} else {
			setCurrentGame(true);
		}
		ViewEventController.getInstance().refreshGameTree();
	}

	/**
	 * Constructs the current game with the data from the fields in this panel
	 * 
	 * @param active
	 *            Whether to make this game active or not
	 */
	private void setCurrentGame(boolean active) {
		currentGame.setName(getBoxName().getText());
		currentGame.setDescription(getBoxDescription().getText());
		currentGame.setActive(active);
		currentGame.setUsesCards(doesUseCards());
		currentGame.setRequirements(getRequirements());
		currentGame.setEndDate(getEndDateField().getEndDate());
		currentGame.setCreator(ConfigManager.getConfig().getUserName());
		currentGame.setDeck(leftHalf.getDeck());
		currentGame.notifyObservers();
	}

	/**
	 * sets buttons to be enabled or disabled depending on if conditions were
	 * met
	 */
	public void updateButtons() {
		if (validateField(false, false)) {
			leftHalf.getLaunchGameButtonPanel().getLaunchGameButton().setEnabled(true);
		} else {
			leftHalf.getLaunchGameButtonPanel().getLaunchGameButton().setEnabled(false);
		}

		if (validateField(false, false)) {
			leftHalf.getSaveGameButtonPanel().getSaveGameButton().setEnabled(true);
		} else {
			leftHalf.getSaveGameButtonPanel().getSaveGameButton().setEnabled(false);
		}

	}

	/**
	 * Gets the requirements for this panel
	 * 
	 * @return the requirements that have been created for this game.
	 */
	private List<PPRequirement> getRequirements() {
		return rightHalf.getRequirements();
	}

	private DescriptionJTextArea getBoxDescription() {
		return leftHalf.getBoxDescription();
	}

	private NameJTextField getBoxName() {
		return leftHalf.getBoxName();
	}

	private AddEndDatePanel getEndDateField() {
		return leftHalf.getEndDateField();
	}

	private boolean doesUseCards() {
		return leftHalf.doesUseCards();
	}

	public LeftHalfCreateGamePanel getLeftHalf() {
		return leftHalf;
	}

	public RightHalfCreateGamePanel getRightHalf() {
		return rightHalf;
	}

	/**
	 * 
	 * @return true if the requirement lists are the same
	 */
	private boolean sameReqLists() {
		if (savedRequirements == rightHalf.getRequirements()) {
			return true;
		}
		if (rightHalf.getRequirements() == null) {
			return false;
		}
		if (rightHalf.getRequirements().size() != savedRequirements.size()) {
			return false;
		}
		for (PPRequirement p : rightHalf.getRequirements()) {
			boolean sameElement = false;
			for (PPRequirement q : savedRequirements) {
				if ((p.getName().equals(q.getName())) // check the name is the
														// same
						&& (p.getDescription().equals(q.getDescription()))) {// check
																				// the
																				// description
																				// is
																				// the
																				// same
					sameElement = true;
				}
			}
			if (!sameElement) {
				return false;
			}
		}
		return true;
	}
}
