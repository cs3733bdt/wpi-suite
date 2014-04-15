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

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.border.Border;


public class NewRightHalfActiveGamePanel extends JScrollPane {
	
	private JTextArea nameTextField;
	private JTextArea descriptionTextField;
	
	private final Border defaultBorder = (new JTextField()).getBorder();
	
	NewRightHalfActiveGamePanel(){
		Container rightView = new Container(); 				//Creates the container for everything in the view
		SpringLayout layout = new SpringLayout(); 			//Creates the layout to be used: Spring Layout
		rightView.setLayout(layout);							//Sets the container to have the spring layout

		/**
		 * Create and/or initialize components
		 */
		JLabel nameLabel = new JLabel("Requirements");			//Creates the Label for the Name
		JLabel reqLabel = new JLabel("Requirement Name");                     //Creates the Label for the Name
		JLabel desLabel = new JLabel("Requirement Description");              //Creates the Label for the Description
				
		nameTextField = new JTextArea(1,30);				//Initializes the textfield for the game name and sets the size to 30
		nameTextField.setText("Requirement1");              //dummy Requirement
		nameTextField.setBorder(defaultBorder);
		nameTextField.setEditable(false);
		
		descriptionTextField = new JTextArea(3, 30);		//Initializes the textarea for the game description
		descriptionTextField.setText("Sleep");              //dummy description
		descriptionTextField.setBorder(defaultBorder);
		descriptionTextField.setEditable(false);
		

		/**
		 * Initializes a table's columns and rows and the table
		 */
		String[] columnNames = {"Requirement", "Description"};
		Object[][] data = {};
		ActiveGamesTable table = new ActiveGamesTable(data, columnNames);
		table.setBorder(defaultBorder);
		
		/**
		 * Adds temporary data into the table. 
		 * DELETE THIS ONCE DATA IS SUCCESSFULLY IMPORTED FROM REQUIREMENT MANAGER!!!!!!!!!!!!
		 */
		table.tableModel.addRow(new Object[]{"Requirement1", "Dinner"});
		table.tableModel.addRow(new Object[]{"Requirement2", "Homework"});
		table.tableModel.addRow(new Object[]{"Requirement3", "Review"});
		table.tableModel.addRow(new Object[]{"Requirement4", "Sleep"});
		table.tableModel.addRow(new Object[]{"Requirement1", "Dinner"});
		table.tableModel.addRow(new Object[]{"Requirement2", "Homework"});
		table.tableModel.addRow(new Object[]{"Requirement3", "Review"});
		table.tableModel.addRow(new Object[]{"Requirement4", "Sleep"});
		
		JScrollPane tablePanel=new JScrollPane(table);
		tablePanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		tablePanel.setPreferredSize(new Dimension(200,100));
		
		/**
		 * Add components to container
		 */
		rightView.add(nameLabel);							//Adds name label to the container
		rightView.add(tablePanel);					//Adds description field to the container
		rightView.add(reqLabel);
		rightView.add(nameTextField);
		rightView.add(desLabel);
		rightView.add(descriptionTextField);
		
		layout.putConstraint(SpringLayout.WEST, nameLabel, 40, SpringLayout.WEST, rightView);
		layout.putConstraint(SpringLayout.EAST, nameLabel, -40, SpringLayout.EAST, rightView);//Adds the name label to the far left
		layout.putConstraint(SpringLayout.NORTH, nameLabel, 10, SpringLayout.NORTH, rightView);
		
		layout.putConstraint(SpringLayout.WEST, tablePanel, 40, SpringLayout.WEST, rightView);
		layout.putConstraint(SpringLayout.EAST, tablePanel, -40, SpringLayout.EAST, rightView);
		layout.putConstraint(SpringLayout.NORTH, tablePanel, 5, SpringLayout.SOUTH, nameLabel);
		
		layout.putConstraint(SpringLayout.WEST, reqLabel, 40, SpringLayout.WEST, rightView);
		layout.putConstraint(SpringLayout.NORTH, reqLabel, 10, SpringLayout.SOUTH, tablePanel);
		
		layout.putConstraint(SpringLayout.WEST, nameTextField, 40, SpringLayout.WEST, rightView);
		layout.putConstraint(SpringLayout.NORTH, nameTextField, 0, SpringLayout.SOUTH, reqLabel);
		
		layout.putConstraint(SpringLayout.WEST, desLabel, 40, SpringLayout.WEST, rightView);
		layout.putConstraint(SpringLayout.NORTH, desLabel, 5, SpringLayout.SOUTH, nameTextField);
		
		layout.putConstraint(SpringLayout.WEST, descriptionTextField, 40, SpringLayout.WEST, rightView);
		layout.putConstraint(SpringLayout.NORTH, descriptionTextField, 0, SpringLayout.SOUTH, desLabel);
		
		this.getViewport().add(rightView);			//Sets the view of the scrollpane to be the entire container which has everything contained within it

	}
	
}
