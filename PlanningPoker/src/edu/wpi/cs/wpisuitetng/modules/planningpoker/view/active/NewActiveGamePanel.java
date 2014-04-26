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
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active;

import java.util.Date;

import javax.swing.JSplitPane;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.abstractmodel.IModelObserver;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.abstractmodel.ObservableModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;

public class NewActiveGamePanel extends JSplitPane implements IModelObserver, IActiveGamePanel{
	Game currentGame;
	
	
	NewLeftHalfActiveGamePanel leftHalf;
	NewRightHalfActiveGamePanel rightHalf; 
	
	public NewActiveGamePanel(final Game game) {
		game.addObserver(this);	
		currentGame = game;
		
		leftHalf = new NewLeftHalfActiveGamePanel(currentGame, this);
		rightHalf = new NewRightHalfActiveGamePanel(currentGame);
		
		setRightComponent(rightHalf);
		setLeftComponent(leftHalf);
		setDividerLocation(400);
		
	}
	
	public void endGame(){
		Date date = new Date();
		currentGame.setEndDate(date);
		
		currentGame.makeComplete();
		currentGame.notifyObservers();
		ViewEventController.getInstance().removeTab(this);
	}
	
	@Override
	public void update(ObservableModel o, Object arg) {
		if(o instanceof Game){
			//TODO Handle an update to a model
		}	
	}
	
	public Game getGame() {
		return currentGame;
	}

	public boolean readyToRemove() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public RequirementTable getReqTable(){
		return rightHalf.getReqTable();
	}
	
}
