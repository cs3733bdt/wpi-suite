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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import javax.swing.SpringLayout;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.DefaultToolbarView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons.CreateGameButtonPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons.HelpButtonPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons.PreferencesButtonPanel;

/**
 * Sets up the upper toolbar of the PlanningPoker Window
 * 
 * 
 * @author jonathanleitschuh
 */
public class ToolbarView extends DefaultToolbarView {
	
	private final CreateGameButtonPanel createGameButton = new CreateGameButtonPanel();
	private final HelpButtonPanel helpButton = new HelpButtonPanel();
	private final PreferencesButtonPanel preferencesButton = new PreferencesButtonPanel();
	
	/**
	 * Creates and positions the buttons/other information in the tool bar
	 * @param visible returns true if the card is visible
	 */
	public ToolbarView(boolean visible){
		SpringLayout layout = new SpringLayout();
		setLayout(layout);
		addGroup(createGameButton);
		addGroup(helpButton);
		addGroup(preferencesButton);
	   
		layout.putConstraint(SpringLayout.WEST, createGameButton, 5, SpringLayout.WEST, this); 					//Adds the name label to the far left
		layout.putConstraint(SpringLayout.WEST, preferencesButton, 20, SpringLayout.EAST, createGameButton); 					//Adds the name label to the far top
		layout.putConstraint(SpringLayout.WEST, helpButton, 0, SpringLayout.EAST, preferencesButton); 	
		
		layout.putConstraint(SpringLayout.NORTH, createGameButton, 5, SpringLayout.NORTH, this); 					//Adds the name label to the far left
		layout.putConstraint(SpringLayout.SOUTH, createGameButton, -5, SpringLayout.SOUTH, this); 					//Adds the name label to the far top
		
		layout.putConstraint(SpringLayout.NORTH, preferencesButton, 5, SpringLayout.NORTH, this); 					//Adds the name label to the far left
		layout.putConstraint(SpringLayout.SOUTH, preferencesButton, -5, SpringLayout.SOUTH, this); 
		
		layout.putConstraint(SpringLayout.NORTH, helpButton, 5, SpringLayout.NORTH, this); 					//Adds the name label to the far left
		layout.putConstraint(SpringLayout.SOUTH, helpButton, -5, SpringLayout.SOUTH, this); 
		
		repaint();
	    
	}
	
	/**
	 * disable Help Button
	 * @param String message
	 */
	public void disableHelpButton(String message) {
		helpButton.disableHelpButton(message);
	}
	
	/**
	 * enable Help Button
	 */
	public void enableHelpButton() {
		helpButton.enableHelpButton();
	}
}