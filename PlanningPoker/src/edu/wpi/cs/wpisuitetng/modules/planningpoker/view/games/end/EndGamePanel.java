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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.end;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.abstractmodel.IModelObserver;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.abstractmodel.ObservableModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.models.PPRequirement;
/**
 * used to display the completed game and requirements within it
 * @author TomPaolillo
 */
public class EndGamePanel extends JSplitPane implements IModelObserver, IEndedGamePanel{
	
	private final Game active;

	/**
	 * Set the gameName equal to the name of the game that was selected from the
	 * active games list
	 */
	private final JTextArea gameName = new JTextArea();

	/**
	 * Set the gameDesc equal to the description of the game that was selected
	 * from the active games list
	 */
	private final JTextArea gameDesc = new JTextArea();

	/**
	 * Set the userStoryDesc equal to the description of the requirement being
	 * selected in the table
	 */
	private final JTextArea userStoryDesc = new JTextArea();

	/**
	 * The estText is needed when the user inputs their estimate, since it must
	 * be added to the server
	 */
	/** Shows the names of the errors */
	JLabel errorField = new JLabel();
	
	private Game game;
	
	private boolean isEstimatePanelCreated = false;

	private StatisticsPanel rightHalf;
	
	private EndGameLeftHalf leftHalf;
	/**
	 * Creates a scrollPane to contain everything
	 * @param game the game panel requested
	 */
	//private JScrollPane activeGameScrol

	public EndGamePanel(Game game) {
		this.game = game;

		rightHalf = new StatisticsPanel(game);
		leftHalf = new EndGameLeftHalf(game, this);
	
		game.addObserver(this); //Makes this the observer for the game
		active = game;
		isEstimatePanelCreated = false;
	
		setLeftComponent(leftHalf);
		setRightComponent(rightHalf);
		
		rightHalf.setMinimumSize(new Dimension(430, 500));
		setDividerLocation(420);
		
	}
	
	public void updateRightHalf(PPRequirement req) {
		rightHalf.reqClicked(req);
		return;		
	}

	public void setGameName(String newGameName) {
		gameName.setText(newGameName);
	}

	public void setGameDesc(String newGameDesc) {
		gameDesc.setText(newGameDesc);
	}

	public void setUserStoryDesc(String newUserStoryDesc) {
		userStoryDesc.setText(newUserStoryDesc);
	}

	public boolean readyToRemove() {
		return true;
	}

	public Game getGame() {
		return active;
	}

	@Override
	public void update(ObservableModel o, Object arg) {
		if(o instanceof Game){
		}
	}
	
	public void endGameButtonPressed(){
		active.makeComplete();
		active.notifyObservers();
	}
}