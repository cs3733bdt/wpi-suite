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


import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.game.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.observers.AbstractModelObserver;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.observers.ObservableModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.requirement.Requirement;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation.NewCreateGamePanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation.NewLeftHalfCreateGamePanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation.NewRightHalfCreateGamePanel;


public class NewActiveGamePanel extends JSplitPane implements AbstractModelObserver{
	Game currentGame;
	
	
	NewLeftHalfActiveGamePanel leftHalf;
	NewRightHalfActiveGamePanel rightHalf; 
	
	public NewActiveGamePanel(final Game game) {
		game.addObserver(this);	
		currentGame = game;
		
		leftHalf = new NewLeftHalfActiveGamePanel(currentGame);
		rightHalf = new NewRightHalfActiveGamePanel();
		
		this.setRightComponent(rightHalf);
		this.setLeftComponent(leftHalf);
		this.setDividerLocation(400);
		
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
	
	/*
	public static void main(String args[]){
		JFrame frame = new JFrame("Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        ArrayList<Requirement> reqs = new ArrayList<Requirement>();
        reqs.add(new Requirement("test", "test"));
        Game testGame;
        testGame = new Game("Test Name", "Test Description", reqs, false, true);
        testGame.setCreator("Doruk");
        //User currentUser;
        //currentUser = new User("Doruk","Doruk", "123", "blank", "blank", 5);
        
        //Set up the content pane.
        frame.add(new NewActiveGamePanel(testGame));
        frame.setMinimumSize(new Dimension(1000, 600));

        //Display the window.
        frame.pack();
        frame.setVisible(true);
	}
*/
	
}
