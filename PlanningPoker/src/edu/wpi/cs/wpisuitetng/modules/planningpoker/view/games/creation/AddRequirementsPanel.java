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
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirement.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.ActiveGamesTable;

/**
 * TODO: add documentation
 * @author Bobby Drop Tables
 *
 */
public class AddRequirementsPanel extends JPanel {
	
	//THIS IS THE REQUIREMENT NAME FIELD THAT WILL BE NEEDED FOR CONTROLLER
	private final JTextField nameArea = new JTextField();
		
	//THIS IS THE REQUIREMENT DESCRIPTION FIELD THAT WILL BE NEEDED FOR CONTROLLER
	private final JTextArea descArea = new JTextArea();
	
	private final Border defaultBorder = (new JTextField()).getBorder();
	
	private final JPanel createReqPanel = new JPanel();
	
	private final	JPanel importReqPanel = new JPanel();
	
	private final JPanel currentReqsPanel = new JPanel();
	
	private final ActiveGamesTable table2;
	
	public AddRequirementsPanel(final CreateGamePanel view){
		
		super(new GridBagLayout());
		
		Container reqPanel = new Container();
		reqPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		
		/**
		 * Panel for creating a requirement by text entry
		 */
		createReqPanel.setLayout(new GridBagLayout());
		createReqPanel.setBorder(defaultBorder);
		//c.insets = new Insets(0, 0, 0 ,0);
		//c.ipadx = 0;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 2;
		createReqPanel.setVisible(false);
		reqPanel.add(createReqPanel, c);
		
		/**
		 * panel for importing a requirement
		 */
		importReqPanel.setLayout(new GridBagLayout());
		importReqPanel.setBorder(defaultBorder);
		//c.insets = new Insets(0, 0, 0 ,0);
		//c.ipadx = 0;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 2;
		importReqPanel.setVisible(false);
		reqPanel.add(importReqPanel, c);
		
		/**
		 * panel for viewing the current requirements added to the game
		 */
		currentReqsPanel.setLayout(new GridBagLayout());
		currentReqsPanel.setBorder(defaultBorder);
		//c.insets = new Insets(0, 0, 0 ,0);
		//c.ipadx = 0;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 2;
		currentReqsPanel.setVisible(true);
		reqPanel.add(currentReqsPanel, c);
		
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
		
		/**
		 * Creates and adds the create requirement label
		 */
		JLabel createReq = new JLabel("Create Requirement");
		createReq.setFont(bigFont);
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		//c.anchor = GridBagConstraints.CENTER;
		createReqPanel.add(createReq, c);
		
		/**
		 * Creates and adds the requirement name label
		 */
		JLabel reqName = new JLabel("* Requirement Name:");
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 1;
		//c.anchor = GridBagConstraints.CENTER;
		createReqPanel.add(reqName, c);
		
		/**
		 * Adds the requirement name text area (nameArea)
		 */
		c.gridx = 1;
		c.gridy = 1;
		//nameArea.setPreferredSize(new Dimension(75, 25));
		//nameArea.setMinimumSize(new Dimension(75, 25));
		createReqPanel.add(nameArea, c);
		
		/**
		 * Creates and adds the requirement description label
		 */
		JLabel reqDesc = new JLabel("* Requirement Description:");
		c.gridx = 0;
		c.gridy = 2;
		c.anchor = GridBagConstraints.CENTER;
		createReqPanel.add(reqDesc, c);
		
		/**
		 * Creates a scoll pane for the description
		 */
		JScrollPane descPane = new JScrollPane(descArea);
		
		/**
		 * Creates and adds the description text area (descArea)
		 */
		descArea.setLineWrap(true);
		c.gridwidth = 2;
		c.gridheight = 3;
		c.gridx = 0;
		c.gridy = 3;
		descPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		//descPane.setMinimumSize(new Dimension(500, 100));
		descPane.setPreferredSize(new Dimension(500, 105));
		createReqPanel.add(descPane, c);
		
		/**
		 * Creates a new button to add the requirements to the game
		 */
		JButton submitAddReqButton = new JButton("Submit");
		submitAddReqButton.addActionListener(new ActionListener() {
			 @Override
			public void actionPerformed(ActionEvent e) {
				 view.addRequirement(new Requirement(nameArea.getText(), descArea.getText()));
				 addRequirement(new Requirement(nameArea.getText(), descArea.getText()));
				 nameArea.setText("");
				 descArea.setText("");
				 createReqPanel.setVisible(false);
				 importReqPanel.setVisible(false);
				 currentReqsPanel.setVisible(true);
			 }
		});
		/*
		 * Format and Create Cancel Button
		 */
		JButton cancelRequirementButton = new JButton("Cancel");
		cancelRequirementButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nameArea.setText("");
				descArea.setText("");
				createReqPanel.setVisible(false);
				importReqPanel.setVisible(false);
				currentReqsPanel.setVisible(true);
			}
		});
		cancelRequirementButton.setPreferredSize(new Dimension(150, 25));
		
		/**
		 * Formats and adds the button
		 */
		c.insets = new Insets(0, 0, 0, 300); //originally 0, 175, 0, 175
		c.gridx = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.gridy = 6;
		c.ipady = 0;
		submitAddReqButton.setPreferredSize(new Dimension(150, 25));
		createReqPanel.add(submitAddReqButton, c);
		
		c.insets = new Insets(0, 300, 0, 0);
		c.gridx = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.gridy = 6;
		c.ipady = 0;
		createReqPanel.add(cancelRequirementButton, c);
		
		/**
		 * Creates and adds the import requirement label
		 */
		JLabel importReq = new JLabel("Import Requirement");
		importReq.setFont(bigFont);
		c.insets = new Insets(0, 0, 0, 0);
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.CENTER;
		importReqPanel.add(importReq, c);
		
		/**
		 * Initializes a table's columns and rows and the table
		 */
		String[] columnNames = {"Requirement", "Description"};
		Object[][] data = {};
		ActiveGamesTable table = new ActiveGamesTable(data, columnNames);
		/**
		 * Adds temporary data into the table. 
		 * DELETE THIS ONCE DATA IS SUCCESSFULLY IMPORTED FROM REQUIREMENT MANAGER!!!!!!!!!!!!
		 */
		table.getTableModel().addRow(new Object[]{"Requirement1", "Description1"});
		table.getTableModel().addRow(new Object[]{"Requirement2", "Description2"});
		table.getTableModel().addRow(new Object[]{"Requirement3", "Description3"});
		table.getTableModel().addRow(new Object[]{"Requirement4", "Description4"});
		table.getTableModel().addRow(new Object[]{"Requirement5", "Description5"});
		table.getTableModel().addRow(new Object[]{"Requirement6", "Description6"});
		table.getTableModel().addRow(new Object[]{"Requirement7", "Description7"});
		table.getTableModel().addRow(new Object[]{"Requirement8", "Description8"});
		table.getTableModel().addRow(new Object[]{"Requirement9", "Description9"});
		table.getTableModel().addRow(new Object[]{"Requirement10", "Description10"});		
		
		/**
		 * Creates a scroll pane, and puts the table within this scroll pane,
		 * and adds the scroll pane to the view.
		 */
		JScrollPane tablePanel = new JScrollPane(table);
		c.gridwidth = 2;
		c.gridheight = 3;
		c.gridx = 0;
		c.gridy = 3;
		tablePanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		tablePanel.setMinimumSize(new Dimension(500, 100));
		tablePanel.setPreferredSize(new Dimension(500, 141));
		importReqPanel.add(tablePanel, c);
		
		/**
		 * Creates the submit button for importing a requirement
		 */
		JButton submitImportReqButton = new JButton("Submit");
		submitImportReqButton.addActionListener(new ActionListener() {
			 @Override
			public void actionPerformed(ActionEvent e) {
				 /*view.addRequirement(new Requirement(nameArea.getText(), descArea.getText()));
				 nameArea.setText("");
				 descArea.setText("");*/
				 createReqPanel.setVisible(false);
				 importReqPanel.setVisible(false);
				 currentReqsPanel.setVisible(true);
			 }
		});
		
		/**
		 * Formats and adds the submit button
		 */
		c.insets = new Insets(0, 175, 0, 175);
		c.gridx = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.gridy = 6;
		c.ipady = 0;
		submitImportReqButton.setPreferredSize(new Dimension(150, 25));
		importReqPanel.add(submitImportReqButton, c);
		
		/**
		 * Creates and adds a label for the Current Requirements
		 */
		JLabel currentReqs = new JLabel("Current Requirements");
		currentReqs.setFont(bigFont);
		c.insets = new Insets(0, 0, 0, 0);
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.CENTER;
		currentReqsPanel.add(currentReqs, c);
		
		/**
		 * Initializes objects for use in table
		 */
		String[] columnNames2 = {"Requirement", "Description"};
		Object[][] data2 = {};
		table2 = new ActiveGamesTable(data2, columnNames2);
		/**
		 * Adds temporary data into the table. 
		 * DELETE THIS ONCE DATA IS SUCCESSFULLY IMPORTED FROM REQUIREMENT MANAGER!!!!!!!!!!!!
		 */
		/*table2.tableModel.addRow(new Object[]{"Requirement1", "Description1"});
		table2.tableModel.addRow(new Object[]{"Requirement2", "Description2"});
		table2.tableModel.addRow(new Object[]{"Requirement3", "Description3"});
		table2.tableModel.addRow(new Object[]{"Requirement4", "Description4"});
		table2.tableModel.addRow(new Object[]{"Requirement5", "Description5"});
		table2.tableModel.addRow(new Object[]{"Requirement6", "Description6"});
		table2.tableModel.addRow(new Object[]{"Requirement7", "Description7"});
		table2.tableModel.addRow(new Object[]{"Requirement8", "Description8"});
		table2.tableModel.addRow(new Object[]{"Requirement9", "Description9"});
		table2.tableModel.addRow(new Object[]{"Requirement10", "Description10"});
		for(int i = 0; i < game.getRequirements().size(); i++){
			table.tableModel.addRow(new Object[]{game.getRequirements().get(i).getName(),game.getRequirements().get(i).getDescription()});
		}*/
		
		/**
		 * Puts the table within a scroll pane, and adds to the view.
		 */
		JScrollPane tablePanel2 = new JScrollPane(table2);
		c.gridwidth = 2;
		c.gridheight = 3;
		c.gridx = 0;
		c.gridy = 3;
		tablePanel2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		tablePanel2.setMinimumSize(new Dimension(500, 100));
		tablePanel2.setPreferredSize(new Dimension(500, 166));
		currentReqsPanel.add(tablePanel2, c);
		
		/**
		 * Creates and adds a remove requirement button
		 * CURRENTLY NOT IMPLEMENTED IN GUI
		 */
		/*JButton removeReqButton = new JButton("Remove Selected Requirement");
		c.gridx = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridy = 2;
		c.ipady = 0;
		c.ipadx = 0;
		//currentReqsPanel.add(removeReqButton, c);
		*/
		
		/**
		 * Creates and adds a remove all requirements button
		 * CURRENTLY NOT IMPLEMENTED IN GUI
		 */
		/*JButton removeAllReqButton = new JButton("Remove All Requirements");
		c.gridx = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridy = 2;
		//currentReqsPanel.add(removeAllReqButton, c);*/
		
		/**
		 * Blank panel for formatting
		 */
		JPanel blankPanel3 = new JPanel();
		blankPanel3.setMinimumSize(new Dimension(400, 25));
		c.gridx = 0;
		c.gridy = 8;
		c.gridwidth = 8;
		reqPanel.add(blankPanel3, c);
				
		this.add(reqPanel);

	}
	
	public JPanel getCreateReqPanel() {
		return createReqPanel;
	}
	
	public JPanel getImportReqPanel() {
		return importReqPanel;
	}
	
	public JPanel getCurrentReqsPanel() {
		return currentReqsPanel;
	}

	public void addRequirement(Requirement requirement){
		table2.getTableModel().addRow(new Object[]{requirement.getName(), requirement.getDescription()});
	}
}