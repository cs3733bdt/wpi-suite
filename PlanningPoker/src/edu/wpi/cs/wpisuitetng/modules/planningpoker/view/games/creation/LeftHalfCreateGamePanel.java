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
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;

import org.jdesktop.swingx.JXDatePicker;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.deck.models.Deck;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.deck.models.DeckModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons.CancelButton;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons.LaunchGameButtonPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons.SaveGameButtonPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.DescriptionJTextArea;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.ErrorLabel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.IDataField;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.IErrorView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.NameJTextField;

/**
 * The Left Half panel for the NewCreateGamePanel Used to input the Games name,
 * description, end date, whether it uses cards
 */
public class LeftHalfCreateGamePanel extends JScrollPane implements IDataField {

	private Container leftView;
	private SpringLayout layout;
	private CreateGamePanel parent;

	private NameJTextField nameTextField;
	private DescriptionJTextArea descriptionTextField;

	private JPanel estimateSelectionPanel;

	private JRadioButton cardsButton = new JRadioButton("Estimate With Cards");
	private JRadioButton textEntryButton = new JRadioButton(
			"Estimate With Text Entry");

	private AddEndDatePanel endDateField;

	private JPanel deckDropDownPanel;

	private JComboBox<Deck> deckDropDown;

	private SaveGameButtonPanel saveGameButton;
	private LaunchGameButtonPanel launchGameButton;
	private CancelButton cancelGameButton;

	/** Shows the names of the errors */
	private ErrorLabel errorField;

	private Game game;

	private boolean usesCardsInitial;
	
	private Date savedDate;

	private static final Logger logger = Logger.getLogger(LeftHalfCreateGamePanel.class
			.getName());
	
	/**
	 * Builds the left half of the CreateGamePanel
	 * 
	 * @param mainPanel
	 *            the main panel this half is located on
	 */
	public LeftHalfCreateGamePanel(CreateGamePanel mainPanel) {
		parent = mainPanel;
		game = mainPanel.getGame();
		build();
		buildFields();

		usesCardsInitial = doesUseCards();

		nameTextField.requestFocus();
		if (game != null) {
			nameTextField.select(game.getName().length(), game.getName()
					.length());
			nameTextField.setStartingText(game.getName());
			descriptionTextField.setStartingText(game.getDescription());
			savedDate = game.getEndDate();
		} else {
			savedDate = endDateField.getEndDate();
		}
	}

	/**
	 * Builds the layout for this panel Sets up all of the elements in their
	 * respective locations
	 */
	private void build() {
		setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		leftView = new Container(); // Creates the container for everything in
									// the view
		layout = new SpringLayout(); // Creates the layout to be used: Spring
										// Layout
		leftView.setLayout(layout); // Sets the container to have the spring
									// layout

		/**
		 * Create and/or initialize components and/or set component properties
		 */
		JLabel nameLabel = new JLabel("Name * "); // Creates the Label for the
													// Name
		nameTextField = new NameJTextField(30,GameModel.getInstance()); // Initializes the text field
												// for the game name and sets
												// the size to 30
		nameTextField.setFocusable(true);

		JLabel descLabel = new JLabel("Description * "); // Creates the label
															// for the
															// Description

		descriptionTextField = new DescriptionJTextArea(); // Initializes the
		descriptionTextField.setLineWrap(true);												// text area for the
															// game description
		nameTextField.addKeyListener(parent);
		// Adds KeyListener to update on key press
		descriptionTextField.addKeyListener(parent); // Adds KeyListener to
														// update on key press

		JScrollPane descPane = new JScrollPane(descriptionTextField); // Creates
																		// the
																		// scrollPane
																		// for
																		// the
																		// description
																		// field
		descPane.setPreferredSize(new Dimension(200, 200)); // Sets the
															// preferred(which
															// works as minimum
															// for some reason)
															// size of the
															// description
															// scroll pane

		estimateSelectionPanel = new JPanel(); // Creates the panel for the
												// estimate radio buttons
		estimateSelectionPanel.add(cardsButton); // Adds the card option radio
													// button to the panel
		estimateSelectionPanel.add(textEntryButton); // Adds the text option
														// radio button to the
														// panel

		cardsButton.setSelected(true); // Sets the default radio button
										// selection to card selection

		ButtonGroup radioGroup = new ButtonGroup(); // Creates a radio button
													// group to make it so that
													// you can only select one
													// of the radio buttons in
													// the group
		radioGroup.add(cardsButton); // Adds the card option radio button to the
										// group
		radioGroup.add(textEntryButton); // Adds the text option radio button to
											// the group

		cardsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deckOptionsVisibility();
			}
		});
		textEntryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deckOptionsVisibility();
			}
		});

		endDateField = new AddEndDatePanel(this); // Creates an end date panel

		endDateField.addKeyListener(parent);
		addActionListenerTo(endDateField.getDatePicker()); // Adds
															// ActionListener to
															// update when a
															// selection is made
		addActionListenerTo(endDateField.getHourSelection()); // Adds
																// ActionListener
																// to update
																// when a
																// selection is
																// made
		addActionListenerTo(endDateField.getMinuteSelection()); // Adds
																// ActionListener
																// to update
																// when a
																// selection is
																// made
		addActionListenerTo(endDateField.getAmPmSelection()); // Adds
																// ActionListener
																// to update
																// when a
																// selection is
																// made

		deckDropDownPanel = new JPanel();
		JLabel deckDropDownLabel = new JLabel("Deck");

		deckDropDown = new JComboBox<Deck>();
		List<Deck> deckList = DeckModel.getInstance().getDecks();
		
		deckDropDown.addItem(new Deck());
		for (Deck d : deckList) {
			deckDropDown.addItem(d);
		}
		
		if (game != null) {
			deckDropDown.setSelectedItem(game.getDeck());
		}

		deckDropDownPanel.add(deckDropDownLabel);
		deckDropDownPanel.add(deckDropDown);

		saveGameButton = new SaveGameButtonPanel(parent); // Creates a save game
															// button
		launchGameButton = new LaunchGameButtonPanel(parent); // Creates a
																// launch game
																// button
		cancelGameButton = new CancelButton("Cancel Game", parent); 
		
		JPanel buttonPanel = new JPanel(); // Creates a panel for the buttons
		buttonPanel.add(saveGameButton); // Adds the save button to the panel
		buttonPanel.add(launchGameButton); // Adds the launch button to the
											// panel
		buttonPanel.add(cancelGameButton); // Adds the cancel button to the
											// panel

		addMouseListenerTo(buttonPanel); // Adds MouseListener to validate on
											// mouse click
		addMouseListenerTo(saveGameButton.getSaveGameButton()); // Adds
																// MouseListener
																// to validate
																// on mouse
																// click
		addMouseListenerTo(launchGameButton.getLaunchGameButton()); // Adds
																	// MouseListener
																	// to
																	// validate
																	// on mouse
																	// click

		errorField = new ErrorLabel();
		errorField.setMinimumSize(new Dimension(150, 25));
		errorField.setForeground(Color.RED);

		/**
		 * Add components to container
		 */
		leftView.add(nameLabel); // Adds name label to the container
		leftView.add(nameTextField); // Adds name text field to the container
		leftView.add(descLabel); // Adds description label to the container
		leftView.add(descPane); // Adds description field to the container
		leftView.add(estimateSelectionPanel); // Adds the panel with the radio
												// buttons to the container
		leftView.add(endDateField); // Adds the end date field to the container
		leftView.add(deckDropDownPanel); // Adds the panel with the drop down
											// menu of decks and label to the
											// container
		leftView.add(buttonPanel); // Adds the panel with the buttons to the
									// container
		leftView.add(errorField); // Adds the error field to the container

		/**
		 * Adjust layout constraints to correctly setup the layout of each
		 * component
		 */
		layout.putConstraint(SpringLayout.WEST, nameLabel, 5,
				SpringLayout.WEST, leftView); // Adds the name label to the far
												// left
		layout.putConstraint(SpringLayout.NORTH, nameLabel, 5,
				SpringLayout.NORTH, leftView); // Adds the name label to the far
												// top

		layout.putConstraint(SpringLayout.WEST, nameTextField, 5,
				SpringLayout.WEST, leftView); // Adds the name text field to the
												// far left
		layout.putConstraint(SpringLayout.NORTH, nameTextField, 5,
				SpringLayout.SOUTH, nameLabel); // Adds the name text field just
												// underneath the name label
		layout.putConstraint(SpringLayout.EAST, nameTextField, -5,
				SpringLayout.EAST, leftView); // Makes sure the right side of
												// the text field stretches with
												// the right side of the
												// container

		layout.putConstraint(SpringLayout.WEST, descLabel, 5,
				SpringLayout.WEST, leftView); // Adds the description label to
												// the far left
		layout.putConstraint(SpringLayout.NORTH, descLabel, 5,
				SpringLayout.SOUTH, nameTextField); // Adds the description
													// label underneath the name
													// label

		layout.putConstraint(SpringLayout.WEST, descPane, 5, SpringLayout.WEST,
				leftView); // Adds the description text area to the right of the
							// name label
		layout.putConstraint(SpringLayout.NORTH, descPane, 5,
				SpringLayout.SOUTH, descLabel); // Adds the description text
												// area to the far top
		layout.putConstraint(SpringLayout.EAST, descPane, -5,
				SpringLayout.EAST, leftView); // Makes sure the right side of
												// the text area stretches with
												// the right side of the
												// container

		layout.putConstraint(SpringLayout.NORTH, estimateSelectionPanel, 10,
				SpringLayout.SOUTH, descPane); // Adds the panel with the radio
												// buttons underneath the
												// description text area
		layout.putConstraint(SpringLayout.WEST, estimateSelectionPanel, 5,
				SpringLayout.WEST, leftView); // Makes sure the left side of the
												// panel stretches with the left
												// side of the container
		layout.putConstraint(SpringLayout.EAST, estimateSelectionPanel, 5,
				SpringLayout.EAST, leftView); // Makes sure the right side of
												// the panel stretches with the
												// right side of the container

		layout.putConstraint(SpringLayout.NORTH, deckDropDownPanel, -2,
				SpringLayout.SOUTH, estimateSelectionPanel); // Adds the deck
																// dropdown and
																// label panel
																// underneath
																// the end date
																// field
		layout.putConstraint(SpringLayout.WEST, deckDropDownPanel, 5,
				SpringLayout.WEST, leftView); // Makes sure the left side of the
												// panel stretches with the left
												// side of the container
		layout.putConstraint(SpringLayout.EAST, deckDropDownPanel, -5,
				SpringLayout.EAST, leftView); // Makes sure the right side of
												// the panel stretches with the
												// right side of the container

		layout.putConstraint(SpringLayout.NORTH, endDateField, 5,
				SpringLayout.SOUTH, deckDropDownPanel); // Adds the end date
														// field underneath the
														// radio buttons panel
		layout.putConstraint(SpringLayout.WEST, endDateField, 5,
				SpringLayout.WEST, leftView); // Makes sure the left side of the
												// panel stretches with the left
												// side of the container
		layout.putConstraint(SpringLayout.EAST, endDateField, -5,
				SpringLayout.EAST, leftView); // Makes sure the right side of
												// the panel stretches with the
												// right side of the container

		layout.putConstraint(SpringLayout.SOUTH, buttonPanel, -20,
				SpringLayout.SOUTH, leftView); // Adds the button panel to the
												// bottom of the container
		layout.putConstraint(SpringLayout.WEST, buttonPanel, 5,
				SpringLayout.WEST, leftView); // Makes sure the left side of the
												// panel stretches with the left
												// side of the container
		layout.putConstraint(SpringLayout.EAST, buttonPanel, 5,
				SpringLayout.EAST, leftView); // Makes sure the right side of
												// the panel stretches with the
												// right side of the container

		layout.putConstraint(SpringLayout.NORTH, errorField, 1,
				SpringLayout.SOUTH, buttonPanel);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, errorField, 5,
				SpringLayout.HORIZONTAL_CENTER, leftView);

		setMinimumSize(new Dimension(333, 115)); // Sets the minimum size of the
													// left half view
		leftView.setPreferredSize(new Dimension(360, 445)); // Sets the size of
															// the view

		revalidate();
		repaint();

		setViewportView(leftView); // Sets the view of the scrollpane to be the
									// entire container which has everything
									// contained within it

	}

	/**
	 * Sets the fields for the current panel using the current game if the
	 * current game is not null
	 */
	private void buildFields() {
		if (game != null) {
			nameTextField.setText(game.getName());
			descriptionTextField.setText(game.getDescription());
			setUsesCards(game.doesUseCards());

			// CALENDAR SETUP
			Calendar dateMaker = new GregorianCalendar();
			dateMaker.setTime(game.getEndDate());
			String hour = Integer.toString(dateMaker.get(Calendar.HOUR));
			String minute = Integer.toString(dateMaker.get(Calendar.MINUTE));
			String AM_PM = "If this doesn't change, something is wrong";

			if (dateMaker.get(Calendar.AM_PM) == Calendar.AM) {
				AM_PM = "AM";
			}
			if (dateMaker.get(Calendar.AM_PM) == Calendar.PM) {
				AM_PM = "PM";
			}

			getEndDateField().setDateAndTime(dateMaker.getTime(), hour, minute,
					AM_PM);
			// >>END CALENDAR SETUP
		}
	}

	/**
	 * Gets the end date field for this panel
	 * 
	 * @return the current end date field
	 */
	public AddEndDatePanel getEndDateField() {
		return endDateField;
	}

	/**
	 * Getter for the Game Name text entry
	 * 
	 * @return nameTextField
	 */
	public String getNameText() {
		return nameTextField.getText();
	}

	/**
	 * Getter for the Game Description text entry
	 * 
	 * @return descriptionTextField
	 */
	public String getDescText() {
		logger.log(Level.INFO, "Description text retrieved");
		return descriptionTextField.getText();
	}

	/**
	 * Gets the NameJTextField for this game panel
	 * 
	 * @return the NameJTextField
	 */
	public NameJTextField getBoxName() {
		return nameTextField;
	}

	public DescriptionJTextArea getBoxDescription() {
		return descriptionTextField;
	}

	public ErrorLabel getErrorField() {
		return errorField;
	}

	/**
	 * Displays text when an error is encountered
	 * 
	 * @param error
	 *            The error text
	 */
	public void displayError(String error) {
		errorField.setText(error);
	}

	public SaveGameButtonPanel getSaveGameButtonPanel() {
		return saveGameButton;
	}

	public LaunchGameButtonPanel getLaunchGameButtonPanel() {
		return launchGameButton;
	}

	/**
	 * Checks all fields to determine if they are prepared to be removed. If a
	 * field is invalid the it warns the user with a notification and by
	 * highlighting the offending box on the GUI.
	 * 
	 * @param warningField
	 *            the field to output the errors to
	 * @return true If all fields are valid and the window is ready to be
	 *         removed
	 */
	@Override
	public boolean validateField(IErrorView warningField, boolean showLabel, boolean showBox) {
		boolean isNameValid = false;
		boolean isDescriptionValid = false;
		boolean isEndDateValid = false;
		boolean returnBoolean = false;
		
		isEndDateValid = getEndDateField().validateField(warningField, showLabel,showBox);
		isDescriptionValid = getBoxDescription().validateField(warningField,showLabel, showBox);
		isNameValid = getBoxName().validateField(warningField, showLabel, showBox);
		
		
		returnBoolean = isNameValid && isDescriptionValid && isEndDateValid;

		if (returnBoolean) {
			warningField.setText("");
		}

		if (!showLabel) {
			warningField.setText("");
		}		
		return (isNameValid && isDescriptionValid && isEndDateValid);
	}

	@Override
	public boolean hasChanges() {

		return nameTextField.hasChanges() 
				|| descriptionTextField.hasChanges()
				|| (getEndDateField().getEndDate().compareTo(savedDate) != 0)
				|| usesCardsInitial != doesUseCards();
				
	}

	/**
	 * Determines if a game uses cards or not
	 * 
	 * @return true if the cards buttons is selected
	 */
	public boolean doesUseCards() {
		return cardsButton.isSelected();
	}

	/**
	 * Sets the toggle panel to indicate if this game is supposed to display a
	 * use of cards
	 * 
	 * @param usesCards
	 *            true if this game uses cards
	 */
	public void setUsesCards(boolean usesCards) {
		if (usesCards) {
			cardsButton.setSelected(true);
			textEntryButton.setSelected(false);
			deckDropDownPanel.setVisible(true);
		} else {
			cardsButton.setSelected(false);
			textEntryButton.setSelected(true);
			deckDropDownPanel.setVisible(false);
		}
	}

	private void addActionListenerTo(JComponent component) {
		if (component instanceof JComboBox) {
			((JComboBox) component).addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					parent.updateButtons();
					getEndDateField().validateField(errorField, false, false);
				}
			});
		}
		if (component instanceof JXDatePicker) {
			((JXDatePicker) component).addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					parent.updateButtons();
					getEndDateField().validateField(errorField, false, false);
				}
			});
		}

	}

	private void addMouseListenerTo(JComponent component) {
		component.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				parent.validateField(true, true);
			}
		});
	}

	public Deck getDeck() {
		return (Deck) deckDropDown.getSelectedItem();
	}

	private void deckOptionsVisibility() {
		if (cardsButton.isSelected()) {
			leftView.setPreferredSize(new Dimension(360, 445));
			deckDropDownPanel.setVisible(true);
			layout.putConstraint(SpringLayout.NORTH, endDateField, 5,
					SpringLayout.SOUTH, deckDropDownPanel);
			revalidate();
			repaint();
		} else {
			leftView.setPreferredSize(new Dimension(360, 410));
			deckDropDownPanel.setVisible(false);
			layout.putConstraint(SpringLayout.NORTH, endDateField, 5,
					SpringLayout.SOUTH, estimateSelectionPanel);
			revalidate();
			repaint();
		}
	}

	/**
	 * 
	 * @return a string of date
	 */
	public String dateToString() {
		return endDateField.toString();
	}

}