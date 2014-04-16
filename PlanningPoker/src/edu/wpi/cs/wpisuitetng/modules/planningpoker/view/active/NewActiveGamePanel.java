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

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.requirement.Requirement;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation.NewCreateGamePanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation.NewLeftHalfCreateGamePanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation.NewRightHalfCreateGamePanelOLD;


public class NewActiveGamePanel extends JSplitPane {
	NewLeftHalfActiveGamePanel leftHalf = new NewLeftHalfActiveGamePanel();
	NewRightHalfActiveGamePanel rightHalf = new NewRightHalfActiveGamePanel();
	
	public NewActiveGamePanel() {

		this.setRightComponent(rightHalf);
		//rightHalf.setMinimumSize(new Dimension(500, 400));
		this.setLeftComponent(leftHalf);
		this.setDividerLocation(400);
		
	}
	
	public static void main(String args[]){
		JFrame frame = new JFrame("Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up the content pane.
        frame.add(new NewActiveGamePanel());
        frame.setMinimumSize(new Dimension(1000, 600));

        //Display the window.
        frame.pack();
        frame.setVisible(true);
	}
}
