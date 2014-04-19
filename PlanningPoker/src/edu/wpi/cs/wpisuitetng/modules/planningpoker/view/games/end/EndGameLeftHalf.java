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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.end;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.Border;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirement.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.ActiveGamesTable;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.NameJTextField;

public class EndGameLeftHalf extends JScrollPane {
	Game ended;
	
	private JTextArea descriptionTextField;
	
	
	
	private JLabel gameNameLabel;
	private NameJTextField gameName;
	private JLabel gameDescLabel;
	private JTextArea gameDesc;
	private JLabel gameCreatorLabel;
	private JLabel gameEndDateLabel;
	private JLabel gameCreatorName;
	private JLabel gameEndDate;
	
	private final Border defaultBorder = (new JTextField()).getBorder();
	
	public EndGameLeftHalf(final Game game){
		ended = game;
		build();
	}
	
	public void build(){
		// Creates the container to hold all the components
		// and sets the container's layout to be SpringLayout
		Container newLeftView = new Container();
		SpringLayout layout = new SpringLayout();
		newLeftView.setLayout(layout);
		
		revalidate();
		repaint();

		/**
		 * Create and/or initialize components
		 */

		// Initializes and sets game name label
		gameNameLabel = new JLabel("Game Name");				

		// Initializes and sets properties of game name label
		gameName = new NameJTextField(30);						
		gameName.setText(ended.getName());				
		gameName.setBorder(defaultBorder);						
		gameName.setEditable(false); 
		gameName.setBackground(Color.WHITE);

		// Initializes and sets game description label
		gameDescLabel = new JLabel("Description");				

		// Initializes and sets game description display area
		gameDesc = new JTextArea();
		gameDesc.setText(ended.getDescription());
		gameDesc.setEditable(false);
		gameDesc.setBorder(defaultBorder);
		gameDesc.setLineWrap(true);									

		// Creates a scroll pane to hold the area which to display game description
		JScrollPane descPane = new JScrollPane(gameDesc);
		descPane.setPreferredSize(new Dimension(200, 100));

		// Initializes and sets game creator label
		gameCreatorLabel = new JLabel("Creator: ");				

		// Initializes and sets game end date label
		gameEndDateLabel = new JLabel("End date: ");			

		// Initializes and sets game creator name
		gameCreatorName = new JLabel(ended.getCreator());		
		
		// Initializes and sets game end date
		gameEndDate = new JLabel(ended.getEndDate().toString());	
		
		/**
		 * Initializes a table's columns and rows and the table
		 */
		String[] columnNames = { "Requirement", "Description"};
		Object[][] data = {};
		ActiveGamesTable table = new ActiveGamesTable(data, columnNames);
		table.setBorder(defaultBorder);
		JScrollPane tablePanel = new JScrollPane(table);
		/**
		 * Display the requirement list in the table
		 */
		for (Requirement r : ended.getRequirements()) {
			table.getTableModel().addRow(new Object[] { r.getName(),
					r.getDescription() });
		}
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					JTable target = (JTable) e.getSource();
					int row = target.getSelectedRow();
					int column = target.getSelectedColumn();
					String selected = (String) target.getValueAt(row, column);
					for (Requirement r : ended.getRequirements()) {
						if (selected.equals(r.getName())
								|| selected.equals(r.getDescription())) {
							descriptionTextField.setText(r.getDescription());
							//statisticsRow = {mean, std, med, max, min};
							//TODO: GET AND SET OTHER FIELDS REQUIRED FOR RIGHTHALF OF END GAME PANEL
							//AlSO TODO: figure out how to actually use this listener on the right half..
						}
					}
				}
			}
		});
		
		/**
		 * Add components to the container
		 */
		newLeftView.add(gameNameLabel);
		newLeftView.add(gameName);
		newLeftView.add(gameDescLabel);
		newLeftView.add(descPane);
		newLeftView.add(gameCreatorLabel);
		newLeftView.add(gameEndDateLabel);
		newLeftView.add(gameCreatorName);
		newLeftView.add(gameEndDate);
		newLeftView.add(tablePanel);
		
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

		layout.putConstraint(SpringLayout.NORTH, descPane, 5, SpringLayout.SOUTH, gameDescLabel);
		layout.putConstraint(SpringLayout.WEST, descPane, 5, SpringLayout.WEST, newLeftView);
		layout.putConstraint(SpringLayout.EAST, descPane, -5, SpringLayout.EAST, newLeftView);

		layout.putConstraint(SpringLayout.NORTH, gameCreatorName, 15, SpringLayout.SOUTH, descPane);
		layout.putConstraint(SpringLayout.WEST, gameCreatorName, 14, SpringLayout.EAST, gameCreatorLabel);

		layout.putConstraint(SpringLayout.NORTH, gameCreatorLabel, 15, SpringLayout.SOUTH, descPane);
		layout.putConstraint(SpringLayout.WEST, gameCreatorLabel, 5, SpringLayout.WEST, newLeftView);

		layout.putConstraint(SpringLayout.NORTH, gameEndDateLabel, 5, SpringLayout.SOUTH, gameCreatorLabel);
		layout.putConstraint(SpringLayout.WEST, gameEndDateLabel, 5, SpringLayout.WEST, newLeftView);

		layout.putConstraint(SpringLayout.NORTH, gameEndDate, 5, SpringLayout.SOUTH, gameCreatorLabel);	
		layout.putConstraint(SpringLayout.WEST, gameEndDate, 5, SpringLayout.EAST, gameEndDateLabel);

		layout.putConstraint(SpringLayout.NORTH, tablePanel, 15, SpringLayout.SOUTH, gameEndDateLabel);
		layout.putConstraint(SpringLayout.WEST, tablePanel, 5, SpringLayout.WEST, newLeftView);
		layout.putConstraint(SpringLayout.EAST, tablePanel, -5, SpringLayout.EAST, newLeftView);
		layout.putConstraint(SpringLayout.SOUTH, tablePanel, -10, SpringLayout.SOUTH, newLeftView);
		
		setMinimumSize(new Dimension(310, 110));			//Sets the minimum size of the left half view
		newLeftView.setPreferredSize(new Dimension(315, 400));		//Sets the size of the view
		
		this.getViewport().add(newLeftView);
		
	}
}