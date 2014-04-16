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

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.requirement.Requirement;

/**
 * Used to create a new Planning Poker game using the input of the user.
 */


public class NewCreateGamePanel extends JSplitPane {
	NewLeftHalfCreateGamePanel leftHalf = new NewLeftHalfCreateGamePanel();
	NewRightHalfCreateGamePanel rightHalf = new NewRightHalfCreateGamePanel(this);
	
	public NewCreateGamePanel() {

		this.setRightComponent(rightHalf);
		rightHalf.setMinimumSize(new Dimension(500, 400));
		this.setLeftComponent(leftHalf);
		this.setDividerLocation(400);
		
	}
	
	public static void main(String args[]){
		JFrame frame = new JFrame("Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up the content pane.
        frame.add(new NewCreateGamePanel());
        frame.setMinimumSize(new Dimension(1000, 600));

        //Display the window.
        frame.pack();
        frame.setVisible(true);
	}

	public void addRequirement(Requirement requirement) {
//		this.requirements.add(requirement);
	}
	
}