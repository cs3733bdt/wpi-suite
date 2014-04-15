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

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.Border;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.NameJTextField;

/**
 * TODO DOCUMENTATION
 */
public class NewLeftHalfCreateGamePanel extends JScrollPane {
	
	private NameJTextField nameTextField;
	private JTextArea descriptionTextField;
	
	private JRadioButton cardsButton = new JRadioButton("Estimate With Cards");
	private JRadioButton textEntryButton = new JRadioButton("Estimate With Text Entry");
	
	private final Border defaultBorder = (new JTextField()).getBorder();
	
	private NewAddEndDatePanel endDateField;
	
	public NewLeftHalfCreateGamePanel() {
		Container leftView = new Container(); 				//Creates the container for everything in the view
		SpringLayout layout = new SpringLayout(); 			//Creates the layout to be used: Spring Layout
		leftView.setLayout(layout);							//Sets the container to have the spring layout
		
		/**
		 * Create and/or initialize components and/or set component properties
		 */
		JLabel nameLabel = new JLabel("Name * ");						//Creates the Label for the Name
		nameTextField = new NameJTextField(30);							//Initializes the text field for the game name and sets the size to 30
		
		JLabel descLabel = new JLabel("Description * ");				//Creates the label for the Description
		
		descriptionTextField = new JTextArea();							//Initializes the text area for the game description
		descriptionTextField.setBorder(defaultBorder);					//Sets the default border to the description text area
		
		JScrollPane descPane = new JScrollPane(descriptionTextField);	//Creates the scrollPane for the description field
		descPane.setPreferredSize(new Dimension(200, 200));				//Sets the preferred(which works as minimum for some reason) size of the description scroll pane
		
		JPanel estimateSelectionPanel = new JPanel();					//Creates the panel for the estimate radio buttons
		estimateSelectionPanel.add(cardsButton);						//Adds the card option radio button to the panel
		estimateSelectionPanel.add(textEntryButton);					//Adds the text option radio button to the panel
		
		cardsButton.setSelected(true);									//Sets the default radio button selection to card selection
		
		ButtonGroup radioGroup = new ButtonGroup();						//Creates a radio button group to make it so that you can only select one of the radio buttons in the group
		radioGroup.add(cardsButton);									//Adds the card option radio button to the group
		radioGroup.add(textEntryButton);								//Adds the text option radio button to the group
		
		endDateField = new NewAddEndDatePanel(this);					//Creates an end date panel
		
		/**
		 * Add components to container
		 */
		leftView.add(nameLabel);							//Adds name label to the container
		leftView.add(nameTextField);						//Adds name text field to the container
		leftView.add(descLabel);							//Adds description label to the container
		leftView.add(descPane);								//Adds description field to the container
		leftView.add(estimateSelectionPanel);				//Adds the panel with the radio buttons to the container
		leftView.add(endDateField);							//Adds the end date field to the container
		
		/**
		 * Adjust layout constraints to correctly setup the layout of each component
		 */
		layout.putConstraint(SpringLayout.WEST, nameLabel, 5, SpringLayout.WEST, leftView); 					//Adds the name label to the far left
		layout.putConstraint(SpringLayout.NORTH, nameLabel, 5, SpringLayout.NORTH, leftView); 					//Adds the name label to the far top
		
		layout.putConstraint(SpringLayout.WEST, nameTextField, 5, SpringLayout.WEST, leftView); 				//Adds the name text field to the far left
        layout.putConstraint(SpringLayout.NORTH, nameTextField, 5, SpringLayout.SOUTH, nameLabel); 				//Adds the name text field just underneath the name label
        layout.putConstraint(SpringLayout.EAST, nameTextField, -5, SpringLayout.EAST, leftView); 				//Makes sure the right side of the text field stretches with the right side of the container
        
        layout.putConstraint(SpringLayout.WEST, descLabel, 5, SpringLayout.WEST, leftView); 					//Adds the description label to the far left
        layout.putConstraint(SpringLayout.NORTH, descLabel, 5, SpringLayout.SOUTH, nameTextField); 				//Adds the description label underneath the name label
        
        layout.putConstraint(SpringLayout.WEST, descPane, 5, SpringLayout.WEST, leftView); 						//Adds the description text area to the right of the name label
        layout.putConstraint(SpringLayout.NORTH, descPane, 5, SpringLayout.SOUTH, descLabel); 					//Adds the description text area to the far top
        layout.putConstraint(SpringLayout.EAST, descPane, -5, SpringLayout.EAST, leftView);						//Makes sure the right side of the text area stretches with the right side of the container
        
        layout.putConstraint(SpringLayout.NORTH, estimateSelectionPanel, 5, SpringLayout.SOUTH, descPane);		//Adds the panel with the radio buttons underneath the description text area
		layout.putConstraint(SpringLayout.WEST, estimateSelectionPanel, 5, SpringLayout.WEST, leftView);		//Makes sure the left side of the panel stretches with the left side of the container
		layout.putConstraint(SpringLayout.EAST, estimateSelectionPanel, 5, SpringLayout.EAST, leftView);		//Makes sure the right side of the panel stretches with the right side of the container
        
		layout.putConstraint(SpringLayout.NORTH, endDateField, 5, SpringLayout.SOUTH, estimateSelectionPanel);	//Adds the end date field underneath the radio buttons panel
		layout.putConstraint(SpringLayout.WEST, endDateField, 5, SpringLayout.WEST, leftView);		//Makes sure the left side of the panel stretches with the left side of the container
		layout.putConstraint(SpringLayout.EAST, endDateField, 5, SpringLayout.EAST, leftView);		//Makes sure the right side of the panel stretches with the right side of the container
        
		this.setMinimumSize(new Dimension(400, 400));
		this.getViewport().add(leftView);			//Sets the view of the scrollpane to be the entire container which has everything contained within it
		
	}
	
}