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
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.requirement.Requirement;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.ActiveGamesTable;

/**
 * TODO DOCUMENTATION
 */
public class NewRightHalfCreateGamePanel extends JScrollPane {
	JPanel reqPanel;    //initialize new add requirements panel
	
	//NewAddReqImportReqPanel importPanel;    //initialize the panel with the buttons "Add Requirement" and "Import Requirements"
	
	NewCreateGamePanel createGamePanel;  //initialize variable to hold panel above this panel
	
	SpringLayout layout = new SpringLayout();
	
	final Container rightView = new Container();
	
	private JPanel currentReqsPanel = new JPanel();
	
	private JPanel createReqPanel = new JPanel();
	
	private JPanel addReqImportReqPanel = new JPanel();
	
	private ActiveGamesTable table2;
	
	private JButton addReqButton;
	
	private JButton importReq;
	
	public NewRightHalfCreateGamePanel(NewCreateGamePanel createGamePanel){
		this.createGamePanel = createGamePanel;
		
		//Initializes a container with SpringLayout and adds it to this panel 
		rightView.setLayout(layout);
		rightView.setMinimumSize(new Dimension(250, 500)); //@TODO change the 500 to something more appropriate
		
		final JLabel currentReqsLabel = new JLabel("Current Requirements");
		
		/**
		 * sets the font for the Current Requirements label
		 */
		Font labelFont = makeFont();
		currentReqsLabel.setFont(labelFont);
		
		
     	/**
		 * Initializes objects for use in table
		 */
		table2 = initializeTable();
		
		/**
		 * Puts the table within a scroll pane, and adds to the currentReqsPanel
		 */
		final JScrollPane tablePanel2 = new JScrollPane(table2);
		tablePanel2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		tablePanel2.setBorder(BorderFactory.createLineBorder(Color.black));
		
		/**
		 * adds the add requirement button
		 */
		
		
		/**
		 * Initializes Add Requirement button and sets its action listener
		 */
		addReqButton = new JButton("Add Requirement");
		
		rightView.add(tablePanel2);
		rightView.add(addReqButton);
		rightView.add(currentReqsLabel);
	//	rightView.add(importPanel);	
		getViewport().add(rightView);
		
		
		
		
		//Label Constraints 
		/**
		 * anchors the bottom of the label to the top of currentReqPanel
		 */
		layout.putConstraint(SpringLayout.NORTH, tablePanel2,
				5, 
				SpringLayout.SOUTH, currentReqsLabel);
		
		/**
		 * anchors the top of the label to the top of the container
		 */
		layout.putConstraint(SpringLayout.NORTH, currentReqsLabel,
				5, 
				SpringLayout.NORTH, rightView);
		
		
		//table constraints
		/**
		 * anchors the right side of the table to the right side of the container
		 */
		layout.putConstraint(SpringLayout.EAST, tablePanel2,
				5, 
				SpringLayout.EAST, rightView);
		
		/**
		 * anchors the left side of the table to the left side of the container
		 */
		layout.putConstraint(SpringLayout.WEST, tablePanel2,
				5, 
				SpringLayout.WEST, rightView);
		
		/**
		 * anchors the top of the addReqButton to the bottom of the tablepanel2
		 */
		layout.putConstraint(SpringLayout.NORTH, addReqButton,
				5, 
				SpringLayout.SOUTH, tablePanel2);
		
		/**
		 * anchors the bottom of the addReqbutton to the bottom of the container
		 */
		layout.putConstraint(SpringLayout.SOUTH, addReqButton,
				5, 
				SpringLayout.SOUTH, rightView);
		
		/**
		 * anchors bottom of table to bottom of container
		 */
		layout.putConstraint(SpringLayout.SOUTH, tablePanel2,
				25, 
				SpringLayout.SOUTH, rightView);
		
		
		addReqButton.addActionListener(new ActionListener() {
			 @Override
			public void actionPerformed(ActionEvent e) {
				 /*view.addRequirement(new Requirement(nameArea.getText(), descArea.getText()));
				 nameArea.setText("");
				 descArea.setText("");*/
				 
				 //set everything on create reqPanel to visible.setVisible(true);
				 
				//reqPanel.getImportReqPanel().setVisible(true); //Not applicable at time of coding 
				 currentReqsLabel.setVisible(false);
				 tablePanel2.setVisible(false);
			 }
		});
		
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