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
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.border.Border;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.deck.models.Deck;
import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.models.PPRequirement;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.cards.ActiveCardsPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.cards.CardButton;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.vote.models.Vote;

public class RightHalfActiveGamePanel extends JScrollPane {
	private Game currentGame;
	private PPRequirement activeRequirement;
	private JTextArea nameTextField;
	private JScrollPane nameTextFieldPanel;
	private JTextArea descriptionTextField;
	private JButton submitButton;
	private final Border defaultBorder = (new JTextField()).getBorder();
	private final Border errorBorder = BorderFactory
			.createLineBorder(Color.RED);
	private Deck deck;
	private List<CardButton> JToggleButtonList = new ArrayList<CardButton>();
	private ActiveCardsPanel cardsPanel;
	private int sum;
	private JLabel previousEst;
	private JLabel counterLabel;
	private JPanel estimateWithTextPanel;
	private JTextField estText = new JTextField(6);
	private JTextArea counter = new JTextArea();
	private JLabel errorField = new JLabel();
	private JScrollPane descriptionPanel;
	private JLabel reqLabel;
	private JLabel desLabel;
	private JLabel nameLabel;
	private JScrollPane cardScrollPanel;
	private RequirementTable table;
	private Font largeFont;
	private JButton clearButton;
	private ActiveGamePanel parentPanel;

	RightHalfActiveGamePanel(final Game game, final ActiveGamePanel activeGamePanel) {
		currentGame = game;
		build();
		parentPanel = activeGamePanel;
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
		nameLabel = new JLabel("Select a Requirement to Vote:");
		reqLabel = new JLabel("Requirement Name");
		desLabel = new JLabel("Requirement Description");

		/*
		 * Initializes a table's columns and rows and the table
		 */
		table = new RequirementTable(currentGame.getRequirements(),
				RequirementTableMode.ACTIVE);

		/**
		 * mouse listener
		 */
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {

					activeRequirement = table.getSelectedReq();
					nameTextField.setText(activeRequirement.getName());
					descriptionTextField.setText(activeRequirement
							.getDescription());
					
					estText.setText("");
					estText.setBorder(new JTextField().getBorder());
					errorField.setText("");
					estText.requestFocus();
					estText.select(0, 0);

					previousEst.setText("Your saved estimate is: "
							+ activeRequirement.userVote());
					previousEst.setFont(largeFont);

					try {
						cardsPanel.clearCards();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					setFieldsVisible(true);
					displaySuccess("");

				}
			}
		});
		
		table.setRowSelectionInterval(0, 0); //start table at beginning

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
		nameTextField.setLineWrap(true);
		
		nameTextFieldPanel = new JScrollPane(nameTextField);

		descriptionTextField = new JTextArea(3, 30); // Initializes the textarea
		// for the game
		// description
		descriptionTextField.setText("");
		// description
		descriptionTextField.setBorder(defaultBorder);
		descriptionTextField.setEditable(false);
		descriptionTextField.setLineWrap(true);

		descriptionPanel = new JScrollPane(descriptionTextField);
		descriptionPanel
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		descriptionPanel.setPreferredSize(new Dimension(200, 100));

		/**
		 * Set up the cards panel
		 */
		// Label and accumulate sum

		largeFont = new Font("Serif", Font.BOLD, 20);

		counterLabel = new JLabel("Your current selected estimate is: " + 0);
		counterLabel.setFont(largeFont);

		previousEst = new JLabel();

		PPRequirement firstRequirement = table.getSelectedReq();
		
		previousEst.setText("Your saved estimate is: " + firstRequirement.userVote());
		
		previousEst.setFont(largeFont);

		sum = 0;

		// This branch will be run if the default deck is to be used
		boolean useDefaultDeck;
		// if (customDeck.size() == 0) {
		// generate fibonachi sequence
//		int firstnum = 0;
//		int secondnum = 1;
//		int currnum;
		deck = new Deck();
		// Default value is 6.
//		int Fibcount = 6; // if this is 6, the highest number generated will be
//							// 13
//		for (int i = 0; i < Fibcount; i++) {
//			currnum = firstnum + secondnum;
//			deck.add("" + currnum + "");
//			firstnum = secondnum;
//			secondnum = currnum;
//		}
		useDefaultDeck = true;
		/*
		 * } This branch will be run if a custom deck is to be used else { deck
		 * = customDeck; useDefaultDeck = false; }
		 */
//		if (useDefaultDeck) {
////			deck.add("?");
//		}

		cardsPanel = new ActiveCardsPanel(deck, this);
		
		// adds the button to clear all entered estimates
		clearButton = new JButton("Clear Vote");
		clearButton.setToolTipText("Clear all Estimates");
		
		rightView.add(clearButton);

		// action Listener for the clear button
		clearButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cardsPanel.memoryArrayClear();
				try {
					cardsPanel.clearCards();
					cardsPanel.clearSum();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		});

		// added below
		if (getGame().doesUseCards()) {
			cardScrollPanel = new JScrollPane(cardsPanel);
			// cardPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			cardScrollPanel.setPreferredSize(new Dimension(400, 120));
			rightView.add(cardScrollPanel);
			layout.putConstraint(SpringLayout.WEST, cardScrollPanel, 5,
					SpringLayout.WEST, rightView);
			layout.putConstraint(SpringLayout.EAST, cardScrollPanel, -5,
					SpringLayout.EAST, rightView);
			layout.putConstraint(SpringLayout.NORTH, cardScrollPanel, 20,
					SpringLayout.SOUTH, descriptionPanel);
			cardScrollPanel.getHorizontalScrollBar().setValue((cardScrollPanel.getHorizontalScrollBar().getMaximum())/2);
			layout.putConstraint(SpringLayout.HORIZONTAL_CENTER,
					cardScrollPanel, 0, SpringLayout.HORIZONTAL_CENTER,
					rightView);
			
			layout.putConstraint(SpringLayout.NORTH, clearButton, 5, SpringLayout.SOUTH, cardScrollPanel);
			layout.putConstraint(SpringLayout.WEST, clearButton, 115, SpringLayout.WEST, rightView);
			

			cardScrollPanel.setVisible(false);

		} else {
			rightView.add(cardsPanel);
			layout.putConstraint(SpringLayout.WEST, cardsPanel, 5,
					SpringLayout.WEST, rightView);
			layout.putConstraint(SpringLayout.EAST, cardsPanel, -5,
					SpringLayout.EAST, rightView);
			layout.putConstraint(SpringLayout.NORTH, cardsPanel, 20,
					SpringLayout.SOUTH, descriptionPanel);
			layout.putConstraint(SpringLayout.NORTH, clearButton, 5, SpringLayout.SOUTH, cardsPanel);
			layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, clearButton, 0, SpringLayout.HORIZONTAL_CENTER, rightView);
			
		}
		cardsPanel.setVisible(false);

		// added above

		JToggleButtonList = cardsPanel.getCardButtonArray();

		/**
		 * The text area where the user types their estimate
		 */
		//estText.setText("Estimate Here");
		//estText.setMinimumSize(new Dimension(100, 50));
		//estText.setPreferredSize(new Dimension(100, 50));
		estimateWithTextPanel = new JPanel();
		JLabel estLabel = new JLabel("Enter your estimate: ");
		estimateWithTextPanel.add(estLabel);
		estimateWithTextPanel.add(estText);
		/*estText.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				estText.setText("");
			}
		});*/
		addKeyListenerTo(estText);

		rightView.add(estimateWithTextPanel);
		layout.putConstraint(SpringLayout.WEST, estimateWithTextPanel, 5, SpringLayout.WEST,
				rightView);
		/*layout.putConstraint(SpringLayout.EAST, estText, -5, SpringLayout.EAST,
				rightView);*/
		layout.putConstraint(SpringLayout.NORTH, estimateWithTextPanel, 10,
				SpringLayout.SOUTH, descriptionPanel);

		rightView.add(counterLabel);

		submitButton = new JButton();
		submitButton.setSize(10, 5);
		submitButton.setText("Submit Vote");
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				submitButtonPressed();
			}
		});
		addMouseListenerTo(submitButton);

		if (currentGame.doesUseCards()) {
			estimateWithTextPanel.setVisible(false);
			submitButton.setEnabled(true);
		} else {
			cardsPanel.setVerifyInputWhenFocusTarget(false);
			for (int i = 0; i < JToggleButtonList.size(); i++) {
				JToggleButtonList.get(i).setVisible(false);
			}
			submitButton.setEnabled(false);
			counter.setVisible(false);
			counterLabel.setVisible(false);
		}

		/**
		 * Add components to container
		 */
		rightView.add(nameLabel); // Adds name label to the container
		rightView.add(tablePanel); // Adds description field to the container
		rightView.add(reqLabel);
		rightView.add(nameTextFieldPanel);
		rightView.add(desLabel);
		rightView.add(descriptionPanel);
		rightView.add(previousEst);
		rightView.add(counterLabel);
		rightView.add(submitButton);
		rightView.add(errorField);

		reqLabel.setVisible(false);
		nameTextFieldPanel.setVisible(false);
		desLabel.setVisible(false);
		descriptionPanel.setVisible(false);
		counterLabel.setVisible(false);
		clearButton.setVisible(false);
		previousEst.setVisible(false);
		submitButton.setVisible(false);
		estimateWithTextPanel.setVisible(false);

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

		layout.putConstraint(SpringLayout.WEST, nameTextFieldPanel, 5,
				SpringLayout.WEST, rightView);
		layout.putConstraint(SpringLayout.EAST, nameTextFieldPanel, -5,
				SpringLayout.EAST, rightView);
		layout.putConstraint(SpringLayout.NORTH, nameTextFieldPanel, 0,
				SpringLayout.SOUTH, reqLabel);

		layout.putConstraint(SpringLayout.WEST, desLabel, 5, SpringLayout.WEST,
				rightView);
		layout.putConstraint(SpringLayout.NORTH, desLabel, 5,
				SpringLayout.SOUTH, nameTextFieldPanel);

		layout.putConstraint(SpringLayout.WEST, descriptionPanel, 5,
				SpringLayout.WEST, rightView);
		layout.putConstraint(SpringLayout.EAST, descriptionPanel, -5,
				SpringLayout.EAST, rightView);
		layout.putConstraint(SpringLayout.NORTH, descriptionPanel, 0,
				SpringLayout.SOUTH, desLabel);
		
		layout.putConstraint(SpringLayout.WEST, previousEst, 10,
				SpringLayout.WEST, rightView);
		layout.putConstraint(SpringLayout.NORTH, previousEst, 5,
				SpringLayout.SOUTH, clearButton);
		
		
		
		layout.putConstraint(SpringLayout.WEST, counterLabel, 10,
				SpringLayout.WEST, rightView);
		layout.putConstraint(SpringLayout.EAST, counterLabel, -10,
				SpringLayout.EAST, rightView);
		layout.putConstraint(SpringLayout.NORTH, counterLabel, 5,
				SpringLayout.SOUTH, previousEst);

		layout.putConstraint(SpringLayout.WEST, submitButton, 5,
				SpringLayout.WEST, rightView);

		layout.putConstraint(SpringLayout.WEST, errorField, 120,
				SpringLayout.WEST, rightView);
		
		if(!currentGame.doesUseCards()) {
			layout.putConstraint(SpringLayout.NORTH, previousEst, 10,
					SpringLayout.SOUTH, estimateWithTextPanel);
			
			layout.putConstraint(SpringLayout.SOUTH, submitButton, -10,
					SpringLayout.SOUTH, rightView);
			
			layout.putConstraint(SpringLayout.SOUTH, errorField, -15,
					SpringLayout.SOUTH, rightView);
			
			
			rightView.setPreferredSize(new Dimension(300, 430)); // Sets the size of
			// the view

			revalidate();
			repaint();
		}
		else {	
			layout.putConstraint(SpringLayout.NORTH, submitButton, 5,
					SpringLayout.SOUTH, cardScrollPanel);
			
			layout.putConstraint(SpringLayout.NORTH, errorField, 8,
					SpringLayout.SOUTH, cardScrollPanel);
		}

		// TODO: make this into a method
		activeRequirement = table.getSelectedReq();
		nameTextField.setText(activeRequirement.getName());
		descriptionTextField.setText(activeRequirement.getDescription());

		setFieldsVisible(true);
		
		this.getViewport().add(rightView); // Sets the rightview to be the
											// entire container which has
											// everything contained within it

	}

	private Font makeFont(int i) {
		// TODO Auto-generated method stub
		return null;
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
		counterLabel.setText("Your current selected estimate is: " + sum);
	}

	/**
	 * Returns the sum of all the cards
	 */
	public int getMaxSum() {
		int sum = 0;
		for (int i = 0; i < deck.getSize()-1; i++) {
			sum += Integer.parseInt(deck.getCards().get(i).getText());
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
	public PPRequirement getRequirement() {
		return activeRequirement;
	}

	/**
	 * system out print message when the button is pressed
	 */
	public void submitButtonPressed() {
		if (getGame().doesUseCards()) {
			submitButton();
			System.out.println("Submit Vote Pressed Passed");
		} else {
			if (validateField(true)) {
				submitButton();
				System.out.println("Submit Vote Pressed Passed.");
				estText.requestFocus();
				estText.select(estText.getText().length(), estText.getText().length());
			} else {
				System.out.println("Submit Vote Pressed Failed.");
			}
		}
		
		if (allUsersVoted()){
			parentPanel.endGame();
		}
	}

	/**
	 * 
	 * @param warn
	 * @return true if fields are validate
	 */
	private boolean validateField(boolean warn) {
		boolean isEstimateValid = false;
		getEstimateText().setBorder(defaultBorder);
		errorField.setText("");

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
				displayError("An estimation is required before submission");
			}
		} else if (parsable) {
			if (Integer.parseInt(getEstimateText().getText()) < 0) {
				isEstimateValid = false;
				if (warn) {
					getEstimateText().setBorder(errorBorder);
					displayError("An estimate must be at least 0");
				}
			} else {
				getEstimateText().setBorder(defaultBorder);
				isEstimateValid = true;
			}
		} else {
			isEstimateValid = false;
			if (warn) {
				getEstimateText().setBorder(errorBorder);
				displayError("An estimation must contain only numbers");
			}
		}

		return isEstimateValid;
	}

	/**
	 * add vote and display success message when the button is pressed
	 */
	public void submitButton() {
		//gets the the currently selected table index
		//getNextRow();
		
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
		displaySuccess("Vote Successful!");

		previousEst.setText("Your saved estimate is: "
				+ activeRequirement.userVote());
		table.setValueAt(activeRequirement.userVote(), table.getSelectedRow(),
				2);
		table.setValueAt(activeRequirement.displayComplete(), table.getSelectedRow(),
				3);
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
		nameTextFieldPanel.setVisible(visible);
		desLabel.setVisible(visible);
		previousEst.setVisible(visible);
		descriptionPanel.setVisible(visible);
		submitButton.setVisible(visible);
		if (!getGame().doesUseCards()) {
			estimateWithTextPanel.setVisible(visible);
		} else {
			counterLabel.setVisible(visible);
			clearButton.setVisible(visible);
			cardScrollPanel.setVisible(visible);
			cardsPanel.setVisible(visible);
		}
	}

	private void addKeyListenerTo(JComponent component) {
		component.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent arg0) {
				updateButton();
			}
		});
	}

	private void addMouseListenerTo(JComponent component) {
		component.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				if (!submitButton.isEnabled()) {
					validateField(true);
				}
			}
		});
	}

	private void updateButton() {
		if (validateField(true)) {
			submitButton.setEnabled(true);
		} else {
			submitButton.setEnabled(false);
		}
	}
	
	public RequirementTable getReqTable(){
		return table;
	}
	
	public void getNextRow() {
		table.setRowSelectionInterval(0, 0);
	}
	
	private boolean allUsersVoted(){
		for (PPRequirement p:currentGame.getRequirements()){
			if (p.getVoteCount() != currentGame.getUsers()){
				return false;
			}	
		}
		return true;
	}
}