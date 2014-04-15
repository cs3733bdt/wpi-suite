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
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.border.Border;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.game.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.requirement.Requirement;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.cards.ActiveCardsPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.cards.CardButton;

public class NewRightHalfActiveGamePanel extends JScrollPane {
	Game currentGame;
	private JTextArea nameTextField;
	private JTextArea descriptionTextField;
	private JButton submitButton;
	private final Border defaultBorder = (new JTextField()).getBorder();
	private ArrayList<String> deck = new ArrayList<String>();
	//initializes the JToggleButton
    private ArrayList<CardButton> JToggleButtonList = new ArrayList<CardButton>();
	private ActiveCardsPanel cardsPanel;
	private int sum;
	private JLabel counterLabel;
	
	NewRightHalfActiveGamePanel(final Game game) {
		currentGame = game;
		build();
	}

	public void build() {
		Container rightView = new Container(); // Creates the container for
												// everything in the view
		SpringLayout layout = new SpringLayout(); // Creates the layout to be
													// used: Spring Layout
		rightView.setLayout(layout); // Sets the container to have the spring
										// layout

		//Label and accumlated sum
		counterLabel = new JLabel("Your current estimate total: " + 0);
		sum = 0;
		JToggleButtonList = cardsPanel.getCardButtonArray();
		
		/**
		 * Create and/or initialize components
		 */
		JLabel nameLabel = new JLabel("Requirements"); // Creates the Label for
														// the Name
		JLabel reqLabel = new JLabel("Requirement Name"); // Creates the Label
															// for the Name
		JLabel desLabel = new JLabel("Requirement Description"); // Creates the
																	// Label for
																	// the
																	// Description
		submitButton = new JButton();
		submitButton.setSize(10, 5);
		submitButton.setText("SUBMIT");

		nameTextField = new JTextArea(1, 30); // Initializes the textfield for
												// the game name and sets the
												// size to 30
		nameTextField.setText("Requirement1"); // dummy Requirement
		nameTextField.setBorder(defaultBorder);
		nameTextField.setEditable(false);

		descriptionTextField = new JTextArea(3, 30); // Initializes the textarea
														// for the game
														// description
		descriptionTextField.setText("Sleep Sleep Sleep Sleep Sleep Sleep Sleep "); // dummy
																				// description
		descriptionTextField.setBorder(defaultBorder);
		descriptionTextField.setEditable(false);
		
		JScrollPane descriptionPanel = new JScrollPane(descriptionTextField);
		descriptionPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		descriptionPanel.setPreferredSize(new Dimension(200, 100));

		/**
		 * Initializes a table's columns and rows and the table
		 */
		String[] columnNames = { "Requirement", "Description" };
		Object[][] data = {};
		ActiveGamesTable table = new ActiveGamesTable(data, columnNames);
		table.setBorder(defaultBorder);

		/**
		 * Display the requirement list in the table
		 */
		for (Requirement r : currentGame.getRequirements()) {
			table.tableModel.addRow(new Object[] { r.getName(),
					r.getDescription() });
		}

		JScrollPane tablePanel = new JScrollPane(table);
		tablePanel
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		tablePanel.setPreferredSize(new Dimension(200, 100));

		/**
		 * Add components to container
		 */
		rightView.add(nameLabel); // Adds name label to the container
		rightView.add(tablePanel); // Adds description field to the container
		rightView.add(reqLabel);
		rightView.add(nameTextField);
		rightView.add(desLabel);
		rightView.add(descriptionPanel);
		rightView.add(cardsPanel);
		rightView.add(counterLabel);
		rightView.add(submitButton);

		layout.putConstraint(SpringLayout.WEST, nameLabel, 40,
				SpringLayout.WEST, rightView);
		layout.putConstraint(SpringLayout.EAST, nameLabel, -40,
				SpringLayout.EAST, rightView);// Adds the name label to the far
												// left
		layout.putConstraint(SpringLayout.NORTH, nameLabel, 10,
				SpringLayout.NORTH, rightView);

		layout.putConstraint(SpringLayout.WEST, tablePanel, 40,
				SpringLayout.WEST, rightView);
		layout.putConstraint(SpringLayout.EAST, tablePanel, -40,
				SpringLayout.EAST, rightView);
		layout.putConstraint(SpringLayout.NORTH, tablePanel, 5,
				SpringLayout.SOUTH, nameLabel);

		layout.putConstraint(SpringLayout.WEST, reqLabel, 40,
				SpringLayout.WEST, rightView);
		layout.putConstraint(SpringLayout.NORTH, reqLabel, 10,
				SpringLayout.SOUTH, tablePanel);

		layout.putConstraint(SpringLayout.WEST, nameTextField, 40,
				SpringLayout.WEST, rightView);
		layout.putConstraint(SpringLayout.EAST, nameTextField, -40,
				SpringLayout.EAST, rightView);
		layout.putConstraint(SpringLayout.NORTH, nameTextField, 0,
				SpringLayout.SOUTH, reqLabel);

		layout.putConstraint(SpringLayout.WEST, desLabel, 40,
				SpringLayout.WEST, rightView);
		layout.putConstraint(SpringLayout.NORTH, desLabel, 5,
				SpringLayout.SOUTH, nameTextField);

		layout.putConstraint(SpringLayout.WEST, descriptionPanel, 40,
				SpringLayout.WEST, rightView);
		layout.putConstraint(SpringLayout.EAST, descriptionPanel, -40,
				SpringLayout.EAST, rightView);
		layout.putConstraint(SpringLayout.NORTH, descriptionPanel, 0,
				SpringLayout.SOUTH, desLabel);

		layout.putConstraint(SpringLayout.WEST, cardsPanel, 40,
				SpringLayout.WEST, rightView);
		layout.putConstraint(SpringLayout.EAST, cardsPanel, 40,
				SpringLayout.EAST, rightView);
		layout.putConstraint(SpringLayout.NORTH, cardsPanel, 20,
				SpringLayout.SOUTH, desLabel);
		
		layout.putConstraint(SpringLayout.WEST, counterLabel, 40,
				SpringLayout.WEST, rightView);
		layout.putConstraint(SpringLayout.EAST, counterLabel, 40,
				SpringLayout.EAST, rightView);
		layout.putConstraint(SpringLayout.NORTH, counterLabel, 10,
				SpringLayout.SOUTH, cardsPanel);
		
		layout.putConstraint(SpringLayout.WEST, submitButton, 40,
				SpringLayout.WEST, rightView);
		layout.putConstraint(SpringLayout.SOUTH, submitButton, -20,
				SpringLayout.SOUTH, rightView);

		this.getViewport().add(rightView); // Sets the rightview to be the
											// entire container which has
											// everything contained within it
	}
	
	/**
	 * Update the sum of cards and display it
	 */
	public void updateSum() {
		sum = cardsPanel.getSum();
		counterLabel.setText("Your current estimate total: " + sum);
	}
	
	/**
	 * Returns the sum of all the cards
	 */
	public int getMaxSum() {
		int sum = 0;
		for (int i = 0; i < deck.size(); i++) {
			sum += Integer.parseInt(deck.get(i));
		}
		return sum;
	}
	
}
