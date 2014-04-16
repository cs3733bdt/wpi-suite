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

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

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
public class NewRightHalfCreateGamePanel extends JScrollPane {
	NewAddRequirementsPanel reqPanel;    //initialize new add requirements panel
	NewAddReqImportReqPanel importPanel;    //initialize the panel with the buttons "Add Requirement" and "Import Requirements"
	NewCreateGamePanel createGamePanel;  //initialize variable to hold panel above this panel
	
	private final Border defaultBorder = (new JTextField()).getBorder();
	
	private ActiveGamesTable table2;
	
	public NewRightHalfCreateGamePanel(NewCreateGamePanel createGamePanel){
		
		Container rightView = new Container(); 				//Creates the container for everything in the view
		SpringLayout layout = new SpringLayout(); 			//Creates the layout to be used: Spring Layout
		rightView.setLayout(layout);						//Sets the container to have the spring layout		
		
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
		
		
		JPanel currentReqsPanel = new JPanel();
		SpringLayout currentLayout = new SpringLayout();
		currentReqsPanel.setLayout(currentLayout);
		currentReqsPanel.setBorder(defaultBorder);
		
		JLabel currentReqs = new JLabel("Current Requirements");
		currentReqs.setFont(bigFont);
		
		/**
		 * Initializes objects for use in table
		 */
		String[] columnNames2 = {"Requirement", "Description"};
		Object[][] data2 = {};
		table2 = new ActiveGamesTable(data2, columnNames2);
		
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
		
		JPanel createReqPanel = new JPanel();
		createReqPanel.setBorder(defaultBorder);
		
		setMinimumSize(new Dimension(350, 350));
		getViewport().add(rightView);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
/*		this.createGamePanel = createGamePanel;
		
		//Initializes a container with SpringLayout and adds it to this panel 
		final Container rightView = new Container();
		SpringLayout layout = new SpringLayout();
		rightView.setLayout(layout);
		rightView.setMinimumSize(new Dimension(250, 500)); //@TODO change the 500 to something more appropriate
		
		
		
		rightView.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				rightView.repaint();
			}
			
		});
		
		reqPanel =  new NewAddRequirementsPanel(this);
		//importPanel = new NewAddReqImportReqPanel(reqPanel);
		
		//Anchor AddReqPanel's left side to left side of container
		layout.putConstraint(SpringLayout.WEST, reqPanel,
				5, //add -5 if issues with the bar
				SpringLayout.WEST, rightView);

		
		//Anchor AddReqPanel top to the top of the container
		layout.putConstraint(SpringLayout.NORTH, reqPanel,
				5,
				SpringLayout.NORTH, rightView);

		//Anchor the AddReqPanel's right side to the right side of the container
		layout.putConstraint(SpringLayout.EAST, reqPanel, 
				-5, 
				SpringLayout.EAST, rightView);

//		//Anchor the top of the import panel to the bottom of the AddReqPanel
//		layout.putConstraint(SpringLayout.NORTH, importPanel,
//				5,
//				SpringLayout.SOUTH, reqPanel);
//
//		//Anchor the left side of the import panel to the left side of the container
//		layout.putConstraint(SpringLayout.WEST, importPanel,
//				5,
//				SpringLayout.WEST, rightView);
//
//		//Anchor the right side of the import panel to the right side of the container
//		layout.putConstraint(SpringLayout.EAST, importPanel,
//				5,
//				SpringLayout.EAST, rightView);
		
		

		rightView.add(reqPanel);
		//rightView.add(importPanel);
		this.getViewport().add(rightView);*/
	}

	public void addRequirement(Requirement requirement) {
		createGamePanel.addRequirement(requirement);
	}
	
}