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

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;
import javax.swing.JSplitPane;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.abstractmodel.IModelObserver;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.abstractmodel.ObservableModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.models.PPRequirement;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;

public class ActiveGamePanel extends JSplitPane implements IModelObserver, IActiveGamePanel{
	Game currentGame;
	
	
	LeftHalfActiveGamePanel leftHalf;
	RightHalfActiveGamePanel rightHalf; 
	
	private Timer timer = new Timer();
	
	/**
	 * 
	 * @param game
	 */
	public ActiveGamePanel(final Game game) {
		game.addObserver(this);	
		currentGame = game;
		
		leftHalf = new LeftHalfActiveGamePanel(currentGame, this);
		rightHalf = new RightHalfActiveGamePanel(currentGame, this);
		
		setRightComponent(rightHalf);
		setLeftComponent(leftHalf);
		setDividerLocation(400);
		
		timer.scheduleAtFixedRate(new TimerTask() {
			  @Override
			  public void run() {
				  leftHalf.updateOverallProgress();  
				  List<UserProgress> usersProgress = leftHalf.getUsersProgressPanel().getUserProgressList();
				  for(UserProgress u : usersProgress){
					  u.updateProgressBarValue();
				  }
				  rightHalf.updateVoteColumn();
			  }
			}, 2*1000, 2*1000);
	}
	
	/**
	 * end current game manually
	 */
	public void endGameManually(){
		currentGame.makeComplete();
		currentGame.notifyObservers();
		ViewEventController.getInstance().removeTab(this);
		ViewEventController.getInstance().viewEndGame(currentGame);
	}
	
	private void endGameByVoting(){
		currentGame.makeComplete();
		currentGame.notifyObservers();
		ViewEventController.getInstance().viewEndGame(currentGame);
	}
	
	@Override
	public void update(ObservableModel o, Object arg) {
		if(o instanceof Game){
		}	
	}
	
	public Game getGame() {
		return currentGame;
	}

	public boolean readyToRemove() {
		if (leftHalf.endManually()){
			return true;
		}else{
			if (allUsersVoted()){
				int result = JOptionPane.showConfirmDialog(this,
						"Since you are the last one to vote\nclosing this tab will end the game.",
						"Game Ends", JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
				if (result==0){
					endGameByVoting();
					return true;
				}else{
					return false;
				}
				
			}else{
				return true;
			}
		}
	}
	
	public RequirementTable getReqTable(){
		return rightHalf.getReqTable();
	}
	
	private boolean allUsersVoted(){
		for (PPRequirement p:currentGame.getRequirements()){
			if (p.getVoteCount() != currentGame.getUsers()){
				return false;
			}	
		}
		return true;
	}
}
