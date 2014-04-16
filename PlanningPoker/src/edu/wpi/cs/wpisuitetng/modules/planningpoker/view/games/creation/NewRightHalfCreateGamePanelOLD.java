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

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.border.Border;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.requirement.Requirement;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.ActiveGamesTable;

/**
 * TODO DOCUMENTATION
 */
public class NewRightHalfCreateGamePanelOLD extends JScrollPane {
	JPanel reqPanel;    //initialize new add requirements panel
	
	//NewAddReqImportReqPanel importPanel;    //initialize the panel with the buttons "Add Requirement" and "Import Requirements"
	
	NewCreateGamePanel createGamePanel;  //initialize variable to hold panel above this panel
	
	//THIS IS THE REQUIREMENT NAME FIELD THAT WILL BE NEEDED FOR CONTROLLER
	private JTextField nameArea = new JTextField();
		
	//THIS IS THE REQUIREMENT DESCRIPTION FIELD THAT WILL BE NEEDED FOR CONTROLLER
	private JTextArea descArea = new JTextArea();
	
	SpringLayout layout = new SpringLayout();
	
	final Container rightView = new Container();
	
	private JPanel currentReqsPanel = new JPanel();
	
	private JPanel createReqPanel = new JPanel();
	
	private JPanel addReqImportReqPanel = new JPanel();
	
	private ActiveGamesTable table2;
	
	private JButton addReqButton;
	
	private JButton importReq;
	
	public NewRightHalfCreateGamePanelOLD(NewCreateGamePanel createGamePanel){
		
		/**
		 * Initializes objects for use in table
		 */
		table2 = initializeTable();
		Font labelFont = makeFont();
		
		Border defaultBorder = BorderFactory.createLineBorder(Color.black);
		
		JPanel currentReqsPanel = new JPanel();
	        SpringLayout currentLayout = new SpringLayout();
	        currentReqsPanel.setLayout(currentLayout);
	        currentReqsPanel.setBorder(defaultBorder);
	        
	        JLabel currentReqs = new JLabel("Current Requirements");
	        currentReqs.setFont(labelFont);
	        
	        /**
	         * Initializes objects for use in table
	         */
	        String[] columnNames2 = {"Requirement", "Description"};
	        Object[][] data2 = {};
	        
	        JScrollPane tablePanel2 = new JScrollPane(table2);
	        tablePanel2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
	        
	        currentReqsPanel.add(currentReqs);
	        currentReqsPanel.add(tablePanel2);
	        
	        currentLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, currentReqs, 5, SpringLayout.HORIZONTAL_CENTER, currentReqsPanel);
	        //currentLayout.putConstraint(SpringLayout.WEST, currentReqs, 5, SpringLayout.WEST, currentReqsPanel);
	        //currentLayout.putConstraint(SpringLayout.EAST, currentReqs, -5, SpringLayout.EAST, currentReqsPanel);
	        
	        currentLayout.putConstraint(SpringLayout.NORTH, tablePanel2, 5, SpringLayout.SOUTH, currentReqs);
	        currentLayout.putConstraint(SpringLayout.WEST, tablePanel2, 5, SpringLayout.WEST, currentReqsPanel);
	        currentLayout.putConstraint(SpringLayout.EAST, tablePanel2, -5, SpringLayout.EAST, currentReqsPanel);
	        
	        rightView.add(currentReqsPanel);
	        
	        layout.putConstraint(SpringLayout.NORTH, currentReqsPanel, 5, SpringLayout.NORTH, rightView);
	        layout.putConstraint(SpringLayout.SOUTH, currentReqsPanel, -5, SpringLayout.SOUTH, rightView);
	        layout.putConstraint(SpringLayout.WEST, currentReqsPanel, 5, SpringLayout.WEST, rightView);
	        layout.putConstraint(SpringLayout.EAST, currentReqsPanel, -5, SpringLayout.EAST, rightView);
	        
	        setViewportView(rightView);
//		
//		//currentReqPanel outside constraints
//		
//		/*
//		 * anchors top of currentReqPanel to top of container
//		 */
//		layout.putConstraint(SpringLayout.NORTH, currentReqsPanel, 5, SpringLayout.NORTH, rightView);
//		
//		/**
//		 * anchors the top of the addReqButton to the bottom of the currentReqsPanel
//		 */
//		layout.putConstraint(SpringLayout.SOUTH, currentReqsPanel, 10, SpringLayout.NORTH, addReqButton);
//		
//		/**
//		 * anchors the bottom of the addReqbutton to the bottom of the container
//		 */
//		layout.putConstraint(SpringLayout.SOUTH, addReqButton, -15, SpringLayout.SOUTH, rightView);
//		
//		
//		/**
//		 * anchors bottom of table to bottom of container
//		 */
//		currentReqsPanelLayout.putConstraint(SpringLayout.SOUTH, currentReqsPanel, -50, SpringLayout.SOUTH, rightView);
//
//		
//		
//		//currentReqPanel inside constraints
//	    /**
//	     * anchors the top of the label to the top of the panel
//	     */
//	    currentReqsPanelLayout.putConstraint(SpringLayout.NORTH, currentReqsLabel, 5, SpringLayout.NORTH, currentReqsPanel);
//		
//		/**
//		 * anchors the top of the table panel to the bottom of the label
//		 */
//	    currentReqsPanelLayout.putConstraint(SpringLayout.NORTH, tablePanel2, 5, SpringLayout.SOUTH, currentReqsLabel);
//		
//	    /**
//	     * anchors the bottom of the table to the bottom of the panel
//	     */
//	    currentReqsPanelLayout.putConstraint(SpringLayout.SOUTH, tablePanel2, -5, SpringLayout.SOUTH, currentReqsPanel);
//		
//
////		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, currentReqsLabel, 
////				5, 
////				SpringLayout.HORIZONTAL_CENTER, rightView);
//		
//		
//		//table constraints
//		/**
//		 * anchors the right side of the table to the right side of the container
//		 */
//		currentReqsPanelLayout.putConstraint(SpringLayout.EAST, tablePanel2, -5, SpringLayout.EAST, currentReqsPanel);
//		
//		/**
//		 * anchors the left side of the table to the left side of the container
//		 */
//		currentReqsPanelLayout.putConstraint(SpringLayout.WEST, tablePanel2, 5, SpringLayout.WEST, currentReqsPanel);
//		
//		/**
//		 * anchors bottom of table to bottom of container
//		 */
//		currentReqsPanelLayout.putConstraint(SpringLayout.SOUTH, tablePanel2, -5, SpringLayout.SOUTH, currentReqsPanel);
//		
		
		//Label Constraints 
		/**
		 * anchors the bottom of the label to the top of currentReqPanel
		 */
//		layout.putConstraint(SpringLayout.NORTH, tablePanel2,
//				5, 
//				SpringLayout.SOUTH, currentReqsLabel);
		
		/**
		 * anchors the top of the label to the top of the container
		 */
//		layout.putConstraint(SpringLayout.NORTH, currentReqsLabel,
//				5, 
//				SpringLayout.NORTH, rightView);
		
//		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, currentReqsLabel, 
//				5, 
//				SpringLayout.HORIZONTAL_CENTER, rightView);
		
		
		//table constraints
//		/**
//		 * anchors the right side of the table to the right side of the container
//		 */
//		layout.putConstraint(SpringLayout.EAST, tablePanel2,
//				5, 
//				SpringLayout.EAST, rightView);
//		
//		/**
//		 * anchors the left side of the table to the left side of the container
//		 */
//		layout.putConstraint(SpringLayout.WEST, tablePanel2,
//				5, 
//				SpringLayout.WEST, rightView);
//		
//		/**
//		 * anchors bottom of table to bottom of container
//		 */
//		layout.putConstraint(SpringLayout.SOUTH, rightView,
//				50, 
//				SpringLayout.SOUTH, tablePanel2);
		
		//Add constraints to add req button

//		createReqPanel.setBorder(defaultBorder);
//		createReqPanel.setVisible(false);
//		reqPanel.add(createReqPanel);
//		
//		/**
//		 * Creates and adds the create requirement label
//		 */
//		JLabel createReq = new JLabel("Create Requirement");
//		createReq.setFont(labelFont);
//		createReq.setVisible(false);
////		
//		JLabel reqName = new JLabel("* Requirement Name:");
//		reqName.setVisible(false);
		
		
//		/**
//		 * Adds the requirement name text area (nameArea)
//		 */
////		c.gridx = 1;
////		c.gridy = 1;
//		nameArea.setPreferredSize(new Dimension(75, 25));
		//nameArea.setMinimumSize(new Dimension(75, 25));
//		createReqPanel.add(nameArea);
//		
//		/**
//		 * Creates and adds the requirement description label
//		 */
//		JLabel reqDesc = new JLabel("* Requirement Description:");
////		c.gridx = 0;
////		c.gridy = 2;
////		c.anchor = GridBagConstraints.CENTER;
//		createReqPanel.add(reqDesc);
//		
//		/**
//		 * Creates a scoll pane for the description
//		 */
//		JScrollPane descPane = new JScrollPane(descArea);
//		
//		/**
//		 * Creates and adds the description text area (descArea)
//		 */
//		descArea.setLineWrap(true);
////		c.gridwidth = 2;
////		c.gridheight = 3;
////		c.gridx = 0;
////		c.gridy = 3;
//		descPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
//		//descPane.setMinimumSize(new Dimension(500, 100));
//		descPane.setPreferredSize(new Dimension(500, 105));
//		createReqPanel.add(descPane);
//		
//		/**
//		 * Creates a new button to add the requirements to the game
//		 */
//		JButton submitAddReqButton = new JButton("Submit");
//		submitAddReqButton.addActionListener(new ActionListener() {
//			 @Override
//			public void actionPerformed(ActionEvent e) {
//				 newRightHalfCreateGamePanel.addRequirement(new Requirement(nameArea.getText(), descArea.getText()));
//				 addRequirement(new Requirement(nameArea.getText(), descArea.getText()));
//				 nameArea.setText("");
//				 descArea.setText("");
//				 createReqPanel.setVisible(false);
//				 importReqPanel.setVisible(false);
//				 currentReqsPanel.setVisible(true);
//			 }
//		});
//		/*
//		 * Format and Create Cancel Button
//		 */
//		JButton cancelRequirementButton = new JButton("Cancel");
//		cancelRequirementButton.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				nameArea.setText("");
//				descArea.setText("");
//				createReqPanel.setVisible(false);
//				importReqPanel.setVisible(false);
//				currentReqsPanel.setVisible(true);
//			}
//		});
		
		
		
		revalidate();
		repaint();
	
		
		
	//	reqPanel =  new NewAddRequirementsPanel(this);
	//	rightView.add(reqPanel);
	

////		//Anchor the right side of the import panel to the right side of the container
////		layout.putConstraint(SpringLayout.EAST, importPanel,
////				5,
////				SpringLayout.EAST, rightView);
	
		
	}

	private ActiveGamesTable initializeTable() {
		String[] columnNames2 = {"Requirement", "Description"};
		Object[][] data2 = {};
		return new ActiveGamesTable(data2, columnNames2);
	}

	public void addRequirement(Requirement requirement) {
		createGamePanel.addRequirement(requirement);
	}
	
	public SpringLayout getLayout() {
		return layout;
	}
	
	public Font makeFont() {
		/**
		 * Creates a new font for use later
		 */
		//create a dummy JTextArea
		JTextArea editingArea = new JTextArea();
		// get the current font
		Font f = editingArea.getFont();
		// create a new, larger font from the current font
		Font newFont = new Font(f.getFontName(), f.getStyle(), f.getSize()+8);		
		//set the bigger font for userStoryDesc
		Font bigFont = newFont;
		return bigFont;
	}

	
	
	public void addComponent(Component c) {
		rightView.add(c);
//		rightView.add(reqPanel);
		getViewport().add(rightView);
		revalidate();
		repaint();
		
	}
	
}