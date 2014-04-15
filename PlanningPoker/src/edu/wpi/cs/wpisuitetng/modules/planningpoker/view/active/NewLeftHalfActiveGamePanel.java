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

import java.awt.Color;
import java.awt.Container;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.Border;
import javax.swing.BorderFactory;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.NameJTextField;

/**
 * TODO Documentation
 * @author doruk
 *
 */
public class NewLeftHalfActiveGamePanel extends JScrollPane {
	
	private JLabel gameNameLabel;
	private JTextField gameName;
	private JLabel gameDescLabel;
	private JTextArea gameDesc;
	private JLabel gameCreatorLabel;
	private JLabel gameEndDateLabel;
	private JLabel gameCreatorName;
	private JLabel gameEndDate;
	private final Border defaultBorder = BorderFactory.createEtchedBorder();
	
	
	
	public NewLeftHalfActiveGamePanel() {
		
		Container newLeftView = new Container();	// Creates the container for everything in the view
		SpringLayout layout = new SpringLayout();   // Creates the layout to be used: SpringLayout
		newLeftView.setLayout(layout);				// Sets the layout to be used: SpringLayout
		
		
		/**
		 * Create and/or initialize components
		 */
		gameNameLabel = new JLabel("Game Name");			// Creates game name label
		
		gameName = new JTextField(30);						// Sets the horizontal size of game name label
		gameName.setText("Sample Game Name");				// Sets the text of game name label
		gameName.setBorder(defaultBorder);					// Adds border to game name label
		
		gameDescLabel = new JLabel("Description");			// Creates game description label
		
		gameDesc = new JTextArea(3, 30);					// Creates game description area
		gameDesc.setText("Sample Game Description");		// Sets the text of game desc. area
		gameDesc.setBorder(defaultBorder);					// Adds border to game desc. area
		
		gameCreatorLabel = new JLabel("Creator: ");			// Creates game creator label
		
		gameEndDateLabel = new JLabel("End date: ");		// Creates game end date label
		
		gameCreatorName = new JLabel("Sample Creator Name");// Creates game creator name label
		
		gameEndDate = new JLabel("Sample End Date");		// Creates game end date label
		
		
		newLeftView.add(gameNameLabel);
		newLeftView.add(gameName);
		newLeftView.add(gameDescLabel);
		newLeftView.add(gameDesc);
		newLeftView.add(gameCreatorLabel);
		newLeftView.add(gameEndDateLabel);
		newLeftView.add(gameCreatorName);
		newLeftView.add(gameEndDate);
		
		/**
		 * Adjust constraints on components
		 */
		layout.putConstraint(SpringLayout.NORTH, gameNameLabel, 10, SpringLayout.NORTH, newLeftView);
		layout.putConstraint(SpringLayout.WEST, gameNameLabel, 5, SpringLayout.WEST, newLeftView);
		layout.putConstraint(SpringLayout.NORTH, gameName, 5, SpringLayout.SOUTH , gameNameLabel);
		layout.putConstraint(SpringLayout.WEST, gameName, 5, SpringLayout.WEST, newLeftView);
		layout.putConstraint(SpringLayout.EAST, gameName, -5, SpringLayout.EAST, newLeftView);
		layout.putConstraint(SpringLayout.NORTH, gameDescLabel, 5, SpringLayout.SOUTH, gameName);
		layout.putConstraint(SpringLayout.WEST, gameDescLabel, 5, SpringLayout.WEST, newLeftView);
		layout.putConstraint(SpringLayout.NORTH, gameDesc, 5, SpringLayout.SOUTH, gameDescLabel);
		layout.putConstraint(SpringLayout.WEST, gameDesc, 5, SpringLayout.WEST, newLeftView);
		layout.putConstraint(SpringLayout.EAST, gameDesc, -5, SpringLayout.EAST, newLeftView);
		layout.putConstraint(SpringLayout.NORTH, gameCreatorName, 10, SpringLayout.SOUTH, gameDesc);
		layout.putConstraint(SpringLayout.WEST, gameCreatorName, 5, SpringLayout.EAST, gameCreatorLabel);
		layout.putConstraint(SpringLayout.NORTH, gameCreatorLabel, 10, SpringLayout.SOUTH, gameDesc);
		layout.putConstraint(SpringLayout.WEST, gameCreatorLabel, 5, SpringLayout.WEST, newLeftView);
		layout.putConstraint(SpringLayout.NORTH, gameEndDateLabel, 5, SpringLayout.SOUTH, gameCreatorLabel);
		layout.putConstraint(SpringLayout.WEST, gameEndDateLabel, 5, SpringLayout.WEST, newLeftView);
		layout.putConstraint(SpringLayout.NORTH, gameEndDate, 5, SpringLayout.SOUTH, gameCreatorLabel);	
		layout.putConstraint(SpringLayout.WEST, gameEndDate, 5, SpringLayout.EAST, gameEndDateLabel);
		
		this.getViewport().add(newLeftView);
	}

}
