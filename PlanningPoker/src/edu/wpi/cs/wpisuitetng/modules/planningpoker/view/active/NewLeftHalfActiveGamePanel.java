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
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.Border;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.NameJTextField;

/**
 * TODO Documentation
 * @author Doruk Uzunoglu
 *
 */
public class NewLeftHalfActiveGamePanel extends JScrollPane{
	
	Game active;
	private JLabel gameNameLabel;
	private NameJTextField gameName;
	private JLabel gameDescLabel;
	private JTextArea gameDesc;
	private JLabel gameCreatorLabel;
	private JLabel gameEndDateLabel;
	private JLabel gameCreatorName;
	private JLabel gameEndDate;
	private JLabel endGameManuallyNoteLabel;
	private JLabel endGameManuallyNoteLabel1;
	private JTextArea endGameManuallyNote;
	private JButton endGameManuallyButton;
	private JLabel endGameErrorLabel;
	JScrollPane notePane;
	private final Border defaultBorder = (new JTextField()).getBorder();
	
	private NewActiveGamePanel parentPanel;
	/**
	 * Constructor for NewLeftHalfActiveGamePanel
	 * @param game the current planning poker game session
	 */
	public NewLeftHalfActiveGamePanel(final Game game, final NewActiveGamePanel activeGamePanel) {
		active = game;
		build();
		isUserCreator();
		parentPanel = activeGamePanel;
	}
	
	/**
	 * Method to notify observers and set game complete if end game is pressed
	 */
	public void endGameManuallyButtonPressed(){
		parentPanel.endGame();
	}
	
	/**
	 * Method to check if the creator of the game is the user in the current session
	 * 
	 */
	public void isUserCreator() {
		if(ConfigManager.getConfig().getUserName().equals(active.getCreator())){
			endGameManuallyNoteLabel.setVisible(true);
			endGameManuallyNoteLabel1.setVisible(true);
			endGameManuallyNote.setBorder(defaultBorder);
			endGameManuallyNote.setVisible(true);
			endGameManuallyButton.setVisible(true);
		} else {
			endGameManuallyNoteLabel.setVisible(false);
			endGameManuallyNoteLabel1.setVisible(false);
			notePane.setVisible(false);
			endGameManuallyButton.setVisible(false);
		}
	}
	
	public void build(){
		// Creates the container to hold all the components
		// and sets the container's layout to be SpringLayout
		Container newLeftView = new Container();
		SpringLayout layout = new SpringLayout();
		newLeftView.setLayout(layout);
		setMinimumSize(new Dimension(300,300));

		/**
		 * Create and/or initialize components
		 */

		// Initializes and sets game name label
		gameNameLabel = new JLabel("Game Name");				

		// Initializes and sets properties of game name label
		gameName = new NameJTextField(30);						
		gameName.setText(active.getName());				
		gameName.setBorder(defaultBorder);						
		gameName.setEditable(false); 
		gameName.setBackground(Color.WHITE);

		// Initializes and sets game description label
		gameDescLabel = new JLabel("Description");				

		// Initializes and sets game description display area
		gameDesc = new JTextArea();
		gameDesc.setText(active.getDescription());
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
		gameCreatorName = new JLabel(active.getCreator());		
		
		
		// Initializes and sets game end date
		gameEndDate = new JLabel(active.getEndDate().toString());	
		
		// Initializes end game manually note labels
		endGameManuallyNoteLabel = new JLabel("If you are ending the game manually,");
		endGameManuallyNoteLabel1 = new JLabel("please enter your reason of doing so:");

		// Initializes text area to enter end game note
		endGameManuallyNote = new JTextArea();
		endGameManuallyNote.setBorder(defaultBorder);
		endGameManuallyNote.setLineWrap(true);

		// Creates a scroll pane to hold the text area for end game note
		notePane = new JScrollPane(endGameManuallyNote);
		notePane.setPreferredSize(new Dimension(200, 200));

		// Creates end game button
		endGameManuallyButton = new JButton();
		endGameManuallyButton.setSize(10, 5);
		endGameManuallyButton.setText("End Game");

		// If end game button is pressed, set the game complete
		endGameManuallyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				// Checks whether if the note is empty, if it is, doesn't end the game
				if(endGameManuallyNote.getText().isEmpty())
					endGameErrorLabel.setVisible(true);
				else {
					endGameManuallyButtonPressed();
					endGameErrorLabel.setVisible(false);
				}
			}
		});
		
		endGameErrorLabel = new JLabel("*A note is required for ending games manually");
		endGameErrorLabel.setForeground(Color.RED);
		endGameErrorLabel.setVisible(false);
		
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
		newLeftView.add(endGameManuallyNoteLabel);
		newLeftView.add(endGameManuallyNoteLabel1);
		newLeftView.add(notePane);
		newLeftView.add(endGameManuallyButton);
		newLeftView.add(endGameErrorLabel);

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

		layout.putConstraint(SpringLayout.NORTH, endGameManuallyNoteLabel, 50, SpringLayout.SOUTH, gameEndDateLabel);	
		layout.putConstraint(SpringLayout.WEST, endGameManuallyNoteLabel, 5, SpringLayout.WEST, newLeftView);

		layout.putConstraint(SpringLayout.NORTH, endGameManuallyNoteLabel1, 1, SpringLayout.SOUTH, endGameManuallyNoteLabel);	
		layout.putConstraint(SpringLayout.WEST, endGameManuallyNoteLabel1, 5, SpringLayout.WEST, newLeftView);

		layout.putConstraint(SpringLayout.NORTH, notePane, 5, SpringLayout.SOUTH, endGameManuallyNoteLabel1);	
		layout.putConstraint(SpringLayout.WEST, notePane, 5, SpringLayout.WEST, newLeftView);
		layout.putConstraint(SpringLayout.EAST, notePane, -5, SpringLayout.EAST, newLeftView);

		layout.putConstraint(SpringLayout.NORTH, endGameManuallyButton, 5, SpringLayout.SOUTH, notePane);	
		layout.putConstraint(SpringLayout.WEST, endGameManuallyButton, 5, SpringLayout.WEST, newLeftView);

		layout.putConstraint(SpringLayout.NORTH, endGameErrorLabel, 5, SpringLayout.SOUTH, endGameManuallyButton);	
		layout.putConstraint(SpringLayout.WEST, endGameErrorLabel, 5, SpringLayout.WEST, newLeftView);
		
		this.getViewport().add(newLeftView);
	}

}
