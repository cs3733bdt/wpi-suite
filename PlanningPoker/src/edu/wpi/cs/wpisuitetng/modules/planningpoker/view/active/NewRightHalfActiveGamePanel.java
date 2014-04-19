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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.border.Border;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirement.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.cards.ActiveCardsPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.cards.CardButton;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.vote.models.Vote;

public class NewRightHalfActiveGamePanel extends JScrollPane {
	private Game currentGame;
	private Requirement activeRequirement;
	private JTextArea nameTextField;
	private JTextArea descriptionTextField;
	private JButton submitButton;
	private final Border defaultBorder = (new JTextField()).getBorder();
	private final Border errorBorder = BorderFactory
			.createLineBorder(Color.RED);
	private ArrayList<String> deck = new ArrayList<String>();
	private List<CardButton> JToggleButtonList = new ArrayList<CardButton>();
	private ActiveCardsPanel cardsPanel;
	private int sum;
	private JLabel previousEst;
	private JLabel counterLabel;
	private JTextField estText = new JTextField();
	private JTextArea counter = new JTextArea();
	private JLabel errorField = new JLabel();
	private JScrollPane descriptionPanel;
	private JLabel reqLabel;
	private JLabel desLabel;
	private JScrollPane cardPanel;

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

		setMinimumSize(new Dimension(310, 110)); // Sets the minimum size of the
													// left half view
		rightView.setPreferredSize(new Dimension(300, 518)); // Sets the size of
																// the view

		revalidate();
		repaint();

		/**
		 * Create and/or initialize components
		 */
		JLabel nameLabel = new JLabel("Requirements"); // Creates the label for
														// the Name
		reqLabel = new JLabel("Requirement Name"); // Creates the label for the
													// Name
		desLabel = new JLabel("Requirement Description"); // Creates the label
															// for the
															// description

		/**
		 * Initializes a table's columns and rows and the table
		 */
		String[] columnNames = { "Requirement", "Description", "Complete" };
		Object[][] data = {};
		ActiveGamesTable table = new ActiveGamesTable(data, columnNames);
		table.setBorder(defaultBorder);

		/**
		 * Display the requirement list in the table
		 */
		for (Requirement r : currentGame.getRequirements()) {
			table.getTableModel().addRow(
					new Object[] { r.getName(), r.getDescription(),
							r.displayComplete() });
		}

		/**
		 * mouse listener
		 */
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					JTable target = (JTable) e.getSource();
					int row = target.getSelectedRow();
					int column = target.getSelectedColumn();
					String selected = (String) target.getValueAt(row, column);
					for (Requirement r : currentGame.getRequirements()) {
						if (selected.equals(r.getName())
								|| selected.equals(r.getDescription())
								|| selected.equals(r.displayComplete())) {
							activeRequirement = r;
							previousEst.setText("Previous estimate: "
									+ userVote(r));
							nameTextField.setText(r.getName());
							descriptionTextField.setText(r.getDescription());
							setFieldsVisible(true);
						}
					}
				}
			}
		});

		JScrollPane tablePanel = new JScrollPane(table);
		tablePanel
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		tablePanel.setPreferredSize(new Dimension(200, 100));

		nameTextField = new JTextArea(1, 30); // Initializes the textfield for
		// the game name and sets the
		// size to 30
		nameTextField.setText("");
		nameTextField.setBorder(defaultBorder);
		nameTextField.setEditable(false);

		descriptionTextField = new JTextArea(3, 30); // Initializes the textarea
		// for the game
		// description
		descriptionTextField.setText("");
		// description
		descriptionTextField.setBorder(defaultBorder);
		descriptionTextField.setEditable(false);

		descriptionPanel = new JScrollPane(descriptionTextField);
		descriptionPanel
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		descriptionPanel.setPreferredSize(new Dimension(200, 100));

		/**
		 * Set up the cards panel
		 */
		// Label and accumulate sum

		counterLabel = new JLabel("Your current estimate total: " + 0);
		previousEst = new JLabel();
		sum = 0;

		// This branch will be run if the default deck is to be used
		boolean useDefaultDeck;
		// if (customDeck.size() == 0) {
		// generate fibonachi sequence
		int firstnum = 0;
		int secondnum = 1;
		int currnum;
		deck.add(Integer.toString(secondnum));
		// Default value is 6.
		int Fibcount = 6; // if this is 6, the highest number generated will be
							// 13
		for (int i = 0; i < Fibcount; i++) {
			currnum = firstnum + secondnum;
			deck.add("" + currnum + "");
			firstnum = secondnum;
			secondnum = currnum;
		}
		useDefaultDeck = true;
		/*
		 * } This branch will be run if a custom deck is to be used else { deck
		 * = customDeck; useDefaultDeck = false; }
		 */
		if (useDefaultDeck) {
			deck.add("0?");
		}

		cardsPanel = new ActiveCardsPanel(deck, this);

		// added below
		if (this.getGame().doesUseCards()) {
			cardPanel = new JScrollPane(cardsPanel);
			// cardPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			cardPanel.setPreferredSize(new Dimension(100, 100));
			rightView.add(cardPanel);
			layout.putConstraint(SpringLayout.WEST, cardPanel, 5,
					SpringLayout.WEST, rightView);
			layout.putConstraint(SpringLayout.EAST, cardPanel, -5,
					SpringLayout.EAST, rightView);
			layout.putConstraint(SpringLayout.NORTH, cardPanel, 20,
					SpringLayout.SOUTH, descriptionPanel);
			layout.putConstraint(SpringLayout.NORTH, counterLabel, 5,
					SpringLayout.SOUTH, cardPanel);
			cardPanel.getHorizontalScrollBar().setValue(200);
			layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, cardPanel, 0,
					SpringLayout.HORIZONTAL_CENTER, rightView);

			cardPanel.setVisible(false);

		} else {
			rightView.add(cardsPanel);
			layout.putConstraint(SpringLayout.WEST, cardsPanel, 5,
					SpringLayout.WEST, rightView);
			layout.putConstraint(SpringLayout.EAST, cardsPanel, -5,
					SpringLayout.EAST, rightView);
			layout.putConstraint(SpringLayout.NORTH, cardsPanel, 20,
					SpringLayout.SOUTH, descriptionPanel);
		}
		cardsPanel.setVisible(false);

		// added above

		this.JToggleButtonList = cardsPanel.getCardButtonArray();
		
		/**
		 * The text area where the user types their estimate
		 */
		estText.setText("Estimate Here");
		estText.setMinimumSize(new Dimension(100, 50));
		estText.setPreferredSize(new Dimension(100, 50));
		estText.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				estText.setText("");
			}
		});
		rightView.add(estText);
		layout.putConstraint(SpringLayout.WEST, estText, 5, SpringLayout.WEST,
				rightView);
		layout.putConstraint(SpringLayout.EAST, estText, -5, SpringLayout.EAST,
				rightView);
		layout.putConstraint(SpringLayout.NORTH, estText, 20,
				SpringLayout.SOUTH, descriptionPanel);

		/**
		 * The label for the counter
		 */
		counterLabel = new JLabel("Your current estimate total: " + 0);

		rightView.add(counterLabel);

		if (currentGame.doesUseCards()) {
			estText.setVisible(false);
		} else {
			cardsPanel.setVerifyInputWhenFocusTarget(false);
			for (int i = 0; i < JToggleButtonList.size(); i++) {
				JToggleButtonList.get(i).setVisible(false);
			}
			counter.setVisible(false);
			counterLabel.setVisible(false);
		}

		submitButton = new JButton();
		submitButton.setSize(10, 5);
		submitButton.setText("SUBMIT");
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				submitButtonPressed();
			}
		});
		
		/**
		 * Add components to container
		 */
		rightView.add(nameLabel); // Adds name label to the container
		rightView.add(tablePanel); // Adds description field to the container
		rightView.add(reqLabel);
		rightView.add(nameTextField);
		rightView.add(desLabel);
		rightView.add(descriptionPanel);
		rightView.add(previousEst);
		rightView.add(counterLabel);
		rightView.add(submitButton);
		rightView.add(errorField);

		reqLabel.setVisible(false);
		nameTextField.setVisible(false);
		desLabel.setVisible(false);
		descriptionPanel.setVisible(false);
		counterLabel.setVisible(false);
		previousEst.setVisible(false);
		submitButton.setVisible(false);
		estText.setVisible(false);

		layout.putConstraint(SpringLayout.WEST, nameLabel, 5,
				SpringLayout.WEST, rightView);
		layout.putConstraint(SpringLayout.EAST, nameLabel, -5,
				SpringLayout.EAST, rightView);// Adds the name label to the far
												// left
		layout.putConstraint(SpringLayout.NORTH, nameLabel, 10,
				SpringLayout.NORTH, rightView);

		layout.putConstraint(SpringLayout.WEST, tablePanel, 5,
				SpringLayout.WEST, rightView);
		layout.putConstraint(SpringLayout.EAST, tablePanel, -5,
				SpringLayout.EAST, rightView);
		layout.putConstraint(SpringLayout.NORTH, tablePanel, 5,
				SpringLayout.SOUTH, nameLabel);

		layout.putConstraint(SpringLayout.WEST, reqLabel, 5, SpringLayout.WEST,
				rightView);
		layout.putConstraint(SpringLayout.NORTH, reqLabel, 10,
				SpringLayout.SOUTH, tablePanel);

		layout.putConstraint(SpringLayout.WEST, nameTextField, 5,
				SpringLayout.WEST, rightView);
		layout.putConstraint(SpringLayout.EAST, nameTextField, -5,
				SpringLayout.EAST, rightView);
		layout.putConstraint(SpringLayout.NORTH, nameTextField, 0,
				SpringLayout.SOUTH, reqLabel);

		layout.putConstraint(SpringLayout.WEST, desLabel, 5, SpringLayout.WEST,
				rightView);
		layout.putConstraint(SpringLayout.NORTH, desLabel, 5,
				SpringLayout.SOUTH, nameTextField);

		layout.putConstraint(SpringLayout.WEST, descriptionPanel, 5,
				SpringLayout.WEST, rightView);
		layout.putConstraint(SpringLayout.EAST, descriptionPanel, -5,
				SpringLayout.EAST, rightView);
		layout.putConstraint(SpringLayout.NORTH, descriptionPanel, 0,
				SpringLayout.SOUTH, desLabel);

		layout.putConstraint(SpringLayout.WEST, previousEst, 5,
				SpringLayout.WEST, rightView);
		layout.putConstraint(SpringLayout.EAST, previousEst, 5,
				SpringLayout.EAST, rightView);
		layout.putConstraint(SpringLayout.SOUTH, previousEst, -20,
				SpringLayout.NORTH, submitButton);

		layout.putConstraint(SpringLayout.WEST, counterLabel, 5,
				SpringLayout.WEST, rightView);
		layout.putConstraint(SpringLayout.EAST, counterLabel, 5,
				SpringLayout.EAST, rightView);
		layout.putConstraint(SpringLayout.SOUTH, counterLabel, -7,
				SpringLayout.NORTH, submitButton);

		layout.putConstraint(SpringLayout.WEST, submitButton, 5,
				SpringLayout.WEST, rightView);
		layout.putConstraint(SpringLayout.SOUTH, submitButton, -30,
				SpringLayout.SOUTH, rightView);

		layout.putConstraint(SpringLayout.WEST, errorField, 5,
				SpringLayout.WEST, rightView);
		layout.putConstraint(SpringLayout.SOUTH, errorField, -15,
				SpringLayout.SOUTH, rightView);

		this.getViewport().add(rightView); // Sets the rightview to be the
											// entire container which has
											// everything contained within it
	}

	/**
	 * getter for the EstimateText text field
	 * 
	 * @return estText
	 */
	public JTextField getEstimateText() {
		return estText;
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

	public ArrayList<CardButton> getCardButtonArray() {
		return new ArrayList<CardButton>();
	}

	/**
	 * getter for the game field
	 * 
	 * @return currentGame
	 */
	public Game getGame() {
		return currentGame;
	}

	/**
	 * getter for the requirement field
	 * 
	 * @return activeRequirement
	 */
	public Requirement getRequirement() {
		return activeRequirement;
	}

	/**
	 * system out print message when the button is pressed
	 */
	public void submitButtonPressed() {
		if (getGame().doesUseCards()) {
			this.submitButton();
			System.out.println("Submit Vote Pressed Passed");
		} else {
			if (this.validateField(true)) {
				this.submitButton();
				System.out.println("Submit Vote Pressed Passed.");
			} else {
				System.out.println("Submit Vote Pressed Failed.");
			}
		}
	}

	/**
	 * 
	 * @param warn
	 * @return true if fields are validate
	 */
	private boolean validateField(boolean warn) {
		boolean isEstimateValid = false;

		// check to see if estimate is parsable to int
		boolean parsable = true;
		try {
			Integer.parseInt(getEstimateText().getText());

		} catch (NumberFormatException e) {
			parsable = false;
		}
		// end parsable check

		// BEGIN ESTIMATE BOX VALDATION
		if (getEstimateText().getText().length() <= 0) {
			isEstimateValid = false;
			if (warn) {
				getEstimateText().setBorder(errorBorder);
			}
			displayError("An estimation is required before submission");
		} else if (parsable) {
			if (Integer.parseInt(getEstimateText().getText()) < 0) {
				isEstimateValid = false;
				if (warn) {
					getEstimateText().setBorder(errorBorder);
				}
				displayError("An estimate must be at least 0");
			} else {
				if (warn) {
					getEstimateText().setBorder(defaultBorder);
				}
				isEstimateValid = true;
			}
		} else {
			isEstimateValid = false;
			if (warn) {
				getEstimateText().setBorder(errorBorder);
			}
			displayError("An estimation must contain only numbers");
		}

		return isEstimateValid;
	}

	/**
	 * add vote and display success message when the button is pressed
	 */
	public void submitButton() {
		String currentUser = ConfigManager.getConfig().getUserName(); // Gets
																		// the
																		// currently
																		// active
																		// user
		int voteNumber;
		if (getGame().doesUseCards()) {
			voteNumber = cardsPanel.getSum();
		} else {
			voteNumber = Integer.parseInt(estText.getText());
		}
		Vote vote = new Vote(currentUser, voteNumber);
		activeRequirement.addVote(vote);

		System.out.println("You voted: " + vote.getVoteNumber());

		ViewEventController.getInstance().refreshGameTable();
		ViewEventController.getInstance().refreshGameTree();

		getEstimateText().setBorder(defaultBorder);
		displaySuccess("   Vote Successful!");
	}

	/**
	 * display if there is an error
	 * 
	 * @param errorString
	 */
	public void displayError(String errorString) {
		errorField.setForeground(Color.RED);
		errorField.setText(errorString);
	}

	/**
	 * display Success
	 * 
	 * @param successString
	 */
	public void displaySuccess(String successString) {
		errorField.setForeground(Color.BLUE);
		errorField.setText(successString);
	}

	/**
	 * set fields below to visible
	 * 
	 * @param boolean visible
	 */
	private void setFieldsVisible(boolean visible) {
		reqLabel.setVisible(visible);
		nameTextField.setVisible(visible);
		desLabel.setVisible(visible);
		previousEst.setVisible(visible);
		descriptionPanel.setVisible(visible);
		submitButton.setVisible(visible);
		if (getGame().doesUseCards() == false) {
			estText.setVisible(visible);
		} else {
			counterLabel.setVisible(visible);
			cardPanel.setVisible(true);
			cardsPanel.setVisible(true);
		}
	}

	/**
	 * 
	 * @return the number of vote if the user already voted, otherwise return 0;
	 */
	public int userVote(Requirement r) {
		String currentUser = ConfigManager.getConfig().getUserName();
		for (Vote v : (r.getVotes())) {
			if (currentUser.equals(v.getUsername())) {
				System.out.println("name matches");
				return v.getVoteNumber();
			}
		}
		System.out.println("name does not match");
		return 0;
	}
}