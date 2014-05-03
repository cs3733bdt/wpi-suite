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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.decks.creation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.deck.models.Deck;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.deck.models.DeckModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons.CancelButton;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons.SaveDeckButtonPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.DescriptionJTextArea;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.ErrorLabel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.IDataField;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.IErrorView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.IValidateButtons;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.NameJTextField;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.NumberJTextField;

/**
 * The panel for the deck creation process Used to allow the user to create a
 * new deck by filling out the indicated fields
 */
public class CreateDeckPanel extends JScrollPane implements IDataField,
		IValidateButtons, ICreateDeckPanel {
	
	/** The Logger for this class */
	private static final Logger logger = Logger.getLogger(CreateDeckPanel.class.getName());

	/**
	 * textfield for the deck name
	 */
	private NameJTextField nameTextField;
	/**
	 * textarea for the deck description
	 */
	private DescriptionJTextArea descriptionTextField;

	/**
	 * textfield for the number of cards desired
	 */
	private NumberJTextField numCards;
	/**
	 * button to submit the number of cards desired and repaint the card panel
	 * with chosen number
	 */
	private JButton submitNumCards;

	/**
	 * dropdown to choose the card back color
	 */
	private JComboBox<String> colorDropDown;

	/**
	 * radio button to only be able to select one card at a time. TODO:
	 * IMPLEMENT THIS
	 */
	private JRadioButton singleSelection;
	/**
	 * radio button to be able to select multiple cards at a time. TODO:
	 * IMPLEMENT THIS
	 */
	private JRadioButton multipleSelection;

	/**
	 * checkbox for selecting whether or not the deck uses an I Don't Know
	 * button
	 */
	private JCheckBox iDontKnowCheck;

	/**
	 * panel to display the cards
	 */
	//private final JPanel cardsPanel = new JPanel();
	private final CardPanel cardsPanel2;

	/**
	 * cancel button to cancel the deck creation process. same as X in tab
	 */
	private CancelButton cancelDeckButton;

	private SaveDeckButtonPanel saveButtonPanel;	//save button to save deck to server


	/**
	 * errorfield to display validation errors
	 */
	private ErrorLabel errorField = new ErrorLabel();

	private final Border defaultTextFieldBorder = (new JTextField())
			.getBorder();

	private final Border errorBorder = BorderFactory
			.createLineBorder(Color.RED);
	
	private final boolean isReopen;


	private Deck deck;

	public CreateDeckPanel() {
		isReopen = false;
		cardsPanel2 = new CardPanel(this);
		build();
	}

	public CreateDeckPanel(Deck deck) {
		isReopen = true;
		cardsPanel2 = new CardPanel(this);
		this.deck = deck;
		build();
		buildFields(deck);
	}
	
	private void buildFields(Deck deck){
		this.deck = deck;
		
		int deckSize = deck.getSize(); //The size of the deck
		int numberOfCards = deck.hasIDontKnowCard() ? deckSize - 1 : deckSize; //correcting for the idk card
		numCards.setText(Integer.toString(numberOfCards)); //set the field
		
		nameTextField.setText(deck.getName());
		descriptionTextField.setText(deck.getDescription());
		iDontKnowCheck.setSelected(deck.hasIDontKnowCard());
		
		if(deck.isMultipleSelection()){
			multipleSelection.setSelected(true);
			singleSelection.setSelected(false);
		}
		else{
			multipleSelection.setSelected(false);
			singleSelection.setSelected(true);
		}
		
		
		colorDropDown.setSelectedIndex(deck.getColorIndex());
		
		cardsPanel2.setDeck(deck);
		
		cardsPanel2.revalidate();
		cardsPanel2.repaint();
	}

	/**
	 * Builds the layout for this panel Sets up all of the elements in their
	 * respective locations
	 */
	private void build() {
		/* Set up initial container with spring layout */
		Container view = new Container();
		SpringLayout layout = new SpringLayout();
		view.setLayout(layout);

		/* Create components */
		/* name and description */
		JLabel nameLabel = new JLabel("Name * ");
		nameTextField = new NameJTextField(20);
		nameTextField.addKeyListener(this);
		
		
		JLabel descriptionLabel = new JLabel("Description");
		descriptionTextField = new DescriptionJTextArea();
		descriptionTextField.setLineWrap(true);
		descriptionTextField.addKeyListener(this);
		JScrollPane descriptionScroll = new JScrollPane(descriptionTextField);
		descriptionScroll.setPreferredSize(new Dimension(400, 20));

		/*
		 * panel underneath name and description, holds the radio buttons, the
		 * number of cards entry, the color dropdown, and the i dont know check
		 * box
		 */
		JPanel numCardsAndColorAndSelectedTypePanel = new JPanel();

		/* radio buttons for type of card selection - single or multiple */
		JLabel selectionTypeLabel = new JLabel("Selection Type");
		JPanel selectionLabelPanel = new JPanel();
		selectionLabelPanel.add(selectionTypeLabel);

		singleSelection = new JRadioButton("Single Card Selection");
		multipleSelection = new JRadioButton("Multiple Card Selection");
		multipleSelection.setSelected(true);
		ButtonGroup radioGroup = new ButtonGroup();
		radioGroup.add(singleSelection);
		radioGroup.add(multipleSelection);

		/* this panel holds the label and the two radio buttons */
		JPanel radioButtonsPanel = new JPanel();
		radioButtonsPanel.setPreferredSize(new Dimension(170, 80));
		SpringLayout radioSpring = new SpringLayout();
		radioButtonsPanel.setLayout(radioSpring);
		radioButtonsPanel.add(selectionLabelPanel);
		radioButtonsPanel.add(singleSelection);
		radioButtonsPanel.add(multipleSelection);
		/* Layout changes of radio button label/fields in radioButtonsPanel */
		radioSpring.putConstraint(SpringLayout.HORIZONTAL_CENTER,
				selectionLabelPanel, 0, SpringLayout.HORIZONTAL_CENTER,
				radioButtonsPanel);
		radioSpring.putConstraint(SpringLayout.NORTH, selectionLabelPanel, 2,
				SpringLayout.NORTH, radioButtonsPanel);
		radioSpring.putConstraint(SpringLayout.WEST, singleSelection, 2,
				SpringLayout.WEST, radioButtonsPanel);
		radioSpring.putConstraint(SpringLayout.WEST, multipleSelection, 2,
				SpringLayout.WEST, radioButtonsPanel);
		radioSpring.putConstraint(SpringLayout.EAST, singleSelection, -2,
				SpringLayout.EAST, radioButtonsPanel);
		radioSpring.putConstraint(SpringLayout.EAST, multipleSelection, -2,
				SpringLayout.EAST, radioButtonsPanel);
		radioSpring.putConstraint(SpringLayout.NORTH, singleSelection, 2,
				SpringLayout.SOUTH, selectionLabelPanel);
		radioSpring.putConstraint(SpringLayout.NORTH, multipleSelection, 2,
				SpringLayout.SOUTH, singleSelection);

		/* blank panel for formatting */
		JPanel blankPanel1 = new JPanel();
		blankPanel1.setPreferredSize(new Dimension(70, 5));

		/* number of cards desired by user */
		JLabel numCardsLabel = new JLabel("Number of Cards * ");
		numCards = new NumberJTextField(10);
		numCards.setPreferredSize(new Dimension(40,22));
		numCards.setText("1");
		numCards.setMaxValue(15);
		numCards.setMinValue(1);
		numCards.addKeyListener(this);
		//initializeArrayList();
		//Selection mode is now enabled by default
		
		submitNumCards = new JButton("Submit");
		submitNumCards.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayNumCards();
				cardsPanel2.revalidate();
				cardsPanel2.repaint();
			}
		});
		addMouseListenerToNumberOfCardsSubmitButton(submitNumCards);
		
		
		/* color desired by user */
		JLabel colorLabel = new JLabel("Select Card Color * ");
		colorDropDown = new JComboBox<String>();
		colorDropDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooseCardColor();
			}
		});
		colorDropDown.addItem("Red (Default)");
		colorDropDown.addItem("Blue");
		colorDropDown.addItem("Green");
		colorDropDown.addItem("Purple");
		colorDropDown.addItem("Yellow");

		/* blank panel for formatting */
		JPanel blankPanel2 = new JPanel();
		blankPanel2.setPreferredSize(new Dimension(70, 5));

		/*
		 * this panel holds the labels for the number of cards and for the color
		 * selection, as well as the textfield and the submit button for the
		 * number of cards, and the dropdown for color selection
		 */
		JPanel numberAndColorOfCards = new JPanel();
		numberAndColorOfCards.setPreferredSize(new Dimension(260, 70));
		SpringLayout numberAndColorOfCardsSpring = new SpringLayout();
		numberAndColorOfCards.setLayout(numberAndColorOfCardsSpring);
		numberAndColorOfCards.add(numCardsLabel);
		numberAndColorOfCards.add(numCards);
		numberAndColorOfCards.add(submitNumCards);
		numberAndColorOfCards.add(colorLabel);
		numberAndColorOfCards.add(colorDropDown);
		/* Layout changes of number label/fields in numberAndColorOfCards */
		numberAndColorOfCardsSpring.putConstraint(SpringLayout.WEST,
				numCardsLabel, 2, SpringLayout.WEST, numberAndColorOfCards);
		numberAndColorOfCardsSpring.putConstraint(SpringLayout.NORTH,
				numCardsLabel, 5, SpringLayout.NORTH, numberAndColorOfCards);
		numberAndColorOfCardsSpring.putConstraint(SpringLayout.WEST, numCards,
				10, SpringLayout.EAST, numCardsLabel);
		numberAndColorOfCardsSpring.putConstraint(SpringLayout.NORTH, numCards,
				2, SpringLayout.NORTH, numberAndColorOfCards);
		numberAndColorOfCardsSpring.putConstraint(SpringLayout.SOUTH, numCards,
				0, SpringLayout.SOUTH, submitNumCards);
		numberAndColorOfCardsSpring.putConstraint(SpringLayout.WEST,
				submitNumCards, 5, SpringLayout.EAST, numCards);
		numberAndColorOfCardsSpring.putConstraint(SpringLayout.NORTH,
				submitNumCards, 2, SpringLayout.NORTH, numberAndColorOfCards);
		/* Layout changes of color label/fields in numberAndColorOfCards */
		numberAndColorOfCardsSpring.putConstraint(SpringLayout.WEST,
				colorLabel, 2, SpringLayout.WEST, numberAndColorOfCards);
		numberAndColorOfCardsSpring.putConstraint(SpringLayout.NORTH,
				colorLabel, 16, SpringLayout.SOUTH, submitNumCards);
		numberAndColorOfCardsSpring.putConstraint(SpringLayout.WEST,
				colorDropDown, 7, SpringLayout.EAST, colorLabel);
		numberAndColorOfCardsSpring.putConstraint(SpringLayout.NORTH,
				colorDropDown, 15, SpringLayout.SOUTH, submitNumCards);
		numberAndColorOfCardsSpring.putConstraint(SpringLayout.EAST,
				colorDropDown, 0, SpringLayout.EAST, submitNumCards);

		/* Checkbox for I Dont Know button */
		JLabel checkboxLabel = new JLabel("Have 'I Don't Know' Card?");
		iDontKnowCheck = new JCheckBox();
		iDontKnowCheck.setSelected(true);
		iDontKnowCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// deck.updateHasIdk(iDontKnowCheck.isSelected());
			}
		});
		JPanel checkPanel = new JPanel();
		checkPanel.add(iDontKnowCheck);
		JPanel checkboxPanel = new JPanel();
		checkboxPanel.setLayout(new BorderLayout());
		checkboxPanel.add(checkboxLabel, BorderLayout.PAGE_START);
		checkboxPanel.add(checkPanel, BorderLayout.PAGE_END);

		/*
		 * adds the 5 previous components (including formatting panels) to a
		 * single panel so the items can appear horizontally. Initially they
		 * were all on the same panel because they needed to all stretch
		 * together, but now it is easier to just leave them instead of re-doing
		 * each spring constraint.
		 */
		numCardsAndColorAndSelectedTypePanel.add(radioButtonsPanel);
		numCardsAndColorAndSelectedTypePanel.add(blankPanel1);
		numCardsAndColorAndSelectedTypePanel.add(numberAndColorOfCards);
		numCardsAndColorAndSelectedTypePanel.add(blankPanel2);
		numCardsAndColorAndSelectedTypePanel.add(checkboxPanel);

		/* Card panel and scrollPane for the cards to appear in */
		JScrollPane cardScrollPane = new JScrollPane(cardsPanel2);
		TitledBorder titleBorder = BorderFactory.createTitledBorder("Type a value for each card and hit enter");
		titleBorder.setTitleJustification(TitledBorder.CENTER);
		cardScrollPane.setBorder(titleBorder);
		cardsPanel2.setPreferredSize(new Dimension(10, 450));

		/* save button */
		saveButtonPanel = new SaveDeckButtonPanel(this);
		addMouseListenerTo(saveButtonPanel.getSaveDeckButton());
		saveButtonPanel.getSaveDeckButton().setEnabled(false);

		/* cancel button */
		cancelDeckButton = new CancelButton("Cancel Deck", this);

		/* error label */
		errorField.setMinimumSize(new Dimension(150, 25));
		errorField.setForeground(Color.RED);
		errorField.setText("Name is required");

		/* Add components to the container */
		view.add(nameLabel);
		view.add(nameTextField);
		view.add(descriptionLabel);
		view.add(descriptionScroll);
		view.add(numCardsAndColorAndSelectedTypePanel);
		view.add(cardScrollPane);
		view.add(saveButtonPanel);
		view.add(cancelDeckButton);
		view.add(errorField);

		/* Sets the layout constraints for each component */
		layout.putConstraint(SpringLayout.WEST, nameLabel, 10, SpringLayout.WEST, view);
		layout.putConstraint(SpringLayout.NORTH, nameLabel, 10, SpringLayout.NORTH, view);
		
		layout.putConstraint(SpringLayout.WEST, nameTextField, 5, SpringLayout.EAST, nameLabel);
		layout.putConstraint(SpringLayout.NORTH, nameTextField, 10, SpringLayout.NORTH, view);
		
		layout.putConstraint(SpringLayout.WEST, descriptionLabel, 10, SpringLayout.EAST, nameTextField);
		layout.putConstraint(SpringLayout.NORTH, descriptionLabel, 10, SpringLayout.NORTH, view);
		
		layout.putConstraint(SpringLayout.WEST, descriptionScroll, 5, SpringLayout.EAST, descriptionLabel);
		layout.putConstraint(SpringLayout.NORTH, descriptionScroll, 10, SpringLayout.NORTH, view);
		layout.putConstraint(SpringLayout.EAST, descriptionScroll, -10, SpringLayout.EAST, view);
		
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, numCardsAndColorAndSelectedTypePanel, 0, SpringLayout.HORIZONTAL_CENTER, view);
		layout.putConstraint(SpringLayout.NORTH, numCardsAndColorAndSelectedTypePanel, 10, SpringLayout.SOUTH, nameLabel);
		
		layout.putConstraint(SpringLayout.WEST, cardScrollPane, 10, SpringLayout.WEST, view);
		layout.putConstraint(SpringLayout.EAST, cardScrollPane, -10, SpringLayout.EAST, view);
		layout.putConstraint(SpringLayout.NORTH, cardScrollPane, 10, SpringLayout.SOUTH, numCardsAndColorAndSelectedTypePanel);
		layout.putConstraint(SpringLayout.SOUTH, cardScrollPane, -45, SpringLayout.SOUTH, view);
		
		layout.putConstraint(SpringLayout.WEST, saveButtonPanel, 10, SpringLayout.WEST, view);
		layout.putConstraint(SpringLayout.NORTH, saveButtonPanel, 10, SpringLayout.SOUTH, cardScrollPane);
		
		layout.putConstraint(SpringLayout.WEST, cancelDeckButton, 10, SpringLayout.EAST, saveButtonPanel);
		layout.putConstraint(SpringLayout.NORTH, cancelDeckButton, 10, SpringLayout.SOUTH, cardScrollPane);
		
		layout.putConstraint(SpringLayout.WEST, errorField, 10, SpringLayout.EAST, cancelDeckButton);
		layout.putConstraint(SpringLayout.NORTH, errorField, 10, SpringLayout.SOUTH, cardScrollPane);
		
		
		ViewEventController.getInstance().refreshDeckTree();
		revalidate();
		repaint();

		setViewportView(view);
	
		
	}


	/**
	 * Build a new font based on specified size
	 * 
	 * @param size
	 *            is how much larger you want the font to be than a default font
	 *            in a JTextArea Can have a negative value for size to decrease
	 *            the font size
	 * @return the font of the designated size
	 */
	public Font makeFont(int size) {
		/**
		 * Creates a new font for use later
		 */
		// create a dummy JTextArea
		JTextArea editingArea = new JTextArea();
		// get the current font
		Font f = editingArea.getFont();
		// create a new, larger font from the current font
		Font newFont = new Font(f.getFontName(), f.getStyle(), f.getSize()
				+ size);
		// set the bigger font for userStoryDesc
		Font bigFont = newFont;
		return bigFont;
	}

	/**
	 * Gets the nameTextField for this deck panel
	 * 
	 * @return the nameTextField
	 */
	public NameJTextField getBoxName() {
		return nameTextField;
	}

	/**
	 * Gets the descriptionTextField for this deck panel
	 * 
	 * @return the descriptionTextField
	 */
	public DescriptionJTextArea getBoxDescription() {
		return descriptionTextField;
	}

	/**
	 * Gets the errorField for this deck panel
	 * 
	 * @return the errorField
	 */
	public ErrorLabel getErrorField() {
		return errorField;
	}

	/**
	 * Gets the numCards for this deck panel
	 * 
	 * @return the numCards
	 */
	public JTextField getNumCards() {
		return numCards;
	}

	/**
	 * Gets the dropdown which displays the color
	 * 
	 * @return colorDropDown
	 */
	public JComboBox<String> getColorDropDown() {
		return colorDropDown;
	}

	/**
	 * Gets the panel which displays the card
	 * 
	 * @return cardsPanel
	 */
	public CardPanel getCardsPanel() {
		return cardsPanel2;
	}
	
	/**
	 * Gets the list of cards
	 * 
	 * @return cards
	 */
	public List<CardImage> getCards() {
		return cardsPanel2.getCards();
	}
	
	/**
	 * Checks all fields to determine if they are prepared to be removed.
	 * If a field is invalid then it warns the user with a notification and by highlighting
	 * the offending box on the GUI.
	 * @param warningField the field to output the errors to
	 * @return true If all fields are valid and the window is ready to be removed
	 */
	public boolean validateField(IErrorView warningField, boolean showLabel, boolean showBox) {
		boolean areCardsValid = cardsPanel2.validateField(warningField, showLabel, showBox);
		
		//Note from Police: We do not need to validate the description field. Descriptions are not required
		
		boolean isNumCardsValid = numCards.validateField(warningField, showLabel, showBox);
		
		boolean isNameValid = nameTextField.validateField(warningField, showLabel, showBox);
		
		
		//If the name is valid and the number of cards is valid and the card panel is valid
		return isNameValid && isNumCardsValid && areCardsValid;
	}

	public boolean hasChanges() {
		// TODO Auto-generated method stub
		return false;
	}



	/**
	 * This method makes sure that the color selected in the dropdown is the
	 * color being display on all the cards It does this by checking how many
	 * components are in the card panel, then once it has stored this number, it
	 * removes all the components in the card panel. It then generates the
	 * number of cards of the correct color (since it now knows the color) that
	 * was equal to the previously recorded number of components in the panel.
	 * TODO: if a card had a value label associated with it, this method should
	 * still associate the value with the new card. TODO: if the number of cards
	 * generated is less than the number of cards that had values, some values
	 * have to be lost in an organized manner.
	 */
	private void chooseCardColor() {
		cardsPanel2.setColor(determineDeckColor());
		
	}

	/**
	 * This is the first thing executed when a new value is entered, after the
	 * submit button action listener is invoked Displays the number of cards as
	 * selected by the user. TODO: store label values previously associated with
	 * cards and keep the saved for the new cards TODO: this would require
	 * changes made to chooseCardColor() since that is where the cards are
	 * really generated.
	 */
	private void displayNumCards() {
		cardsPanel2.setNumberCards(numCards.getValue());
	}

	/**
	 * Mouse listener for submit button for number of cards. If the submit
	 * button is disabled, the error field will tell the user why.
	 * 
	 * @param component
	 *            (this method should only be used with the submit button for
	 *            the number of cards)
	 */
	private void addMouseListenerToNumberOfCardsSubmitButton(JButton component) {
		component.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				logger.log(Level.FINER, "The mouse listener has been triggered on a button.");
				
				//Check for the submitNumbCards 
				if (!submitNumCards.isEnabled()) {
					numCards.validateField(errorField, true, true);
					//errorField.setText("Number of cards must be a 1-or-2-digit integer between 1 and 15");
				} else {
				}// it will perform the action listener
			}
		});
	}

	/**
	 * Adds the deck to the model and to the server
	 */
	public void saveDeck(Deck deck) {
		deck.updateMultipleSelection(!singleSelection.isSelected()); // updates multiple selection boolean
		deck.updateHasIdk(iDontKnowCheck.isSelected());

		DeckModel.getInstance().addDeck(deck); // New Deck gets added // to the
												// server

		ViewEventController.getInstance().refreshDeckTable();
		ViewEventController.getInstance().refreshDeckTree();
	}

	/**
	 * Triggered when the save deck button is pressed using the mouse listener
	 * 
	 */
	public void SaveDeckPressed() {
		if(validateField(errorField, true, true)){
			final Deck deck = new Deck(nameTextField.getText(), 
					descriptionTextField.getText(), cardsPanel2.getCardValues(), iDontKnowCheck.isSelected(), 
						determineDeckColor());
			saveDeck(deck);
			ViewEventController.getInstance().removeTab(this);
		}
	}

	/**
	 * Determines the color of the deck from the dropdown menu
	 * 
	 * @return the color of the deck
	 */
	private ColorEnum determineDeckColor() {
		ColorEnum colorEnum = ColorEnum.RED;
		String color = (String) getColorDropDown().getSelectedItem();
		switch (color) {
		case "Blue":
			colorEnum = ColorEnum.BLUE;
			break;
		case "Green":
			colorEnum = ColorEnum.GREEN;
			break;
		case "Purple":
			colorEnum = ColorEnum.PURPLE;
			break;
		case "Yellow":
			colorEnum = ColorEnum.YELLOW;
			break;
		default:
			colorEnum = ColorEnum.RED;
			break;
		}

		return colorEnum;
	}

	@Override
	public void updateButtons() {
		
		//FOR ENABLING THE SAVE GAME BUTTON
		if (validateField(errorField,true, false)) {
			saveButtonPanel.getSaveDeckButton().setEnabled(true);
		} 
		else {
			saveButtonPanel.getSaveDeckButton().setEnabled(false);
		}
		
		//FOR ENABLING THE SUBMIT NUMBER BUTTON
		if(numCards.validateField(errorField, false, false)){
			submitNumCards.setEnabled(true);
		}
		else {
			submitNumCards.setEnabled(false);
		}
		
	}
	
	/**
	 * Adds a mouse listener to a button to show errors borders when the fields are not set
	 * @param component the button to add the mouse listener on
	 */
	private void addMouseListenerTo(final JButton component){
		component.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(!component.isEnabled()){
					validateField(errorField,true, true);
				}
			}
		});
	}

	public Deck getDeck() {
		try{
			return deck;
		}catch(NullPointerException e){	
		}
		
		final Deck deck = new Deck(nameTextField.getText(),
				descriptionTextField.getText(), cardsPanel2.getCardValues(), iDontKnowCheck.isSelected(),
				determineDeckColor());
		deck.updateMultipleSelection(multipleSelection.isSelected());
		return deck;
	}
	
	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.planningpoker.view.decks.creation.ICreateDeckPanel#disableFields()
	 */
	public void disableFields() {
		
		//Disable the input fields
		descriptionTextField.setEnabled(false);
		nameTextField.setEnabled(false);
		singleSelection.setEnabled(false);
		multipleSelection.setEnabled(false);
		colorDropDown.setEnabled(false);
		submitNumCards.setEnabled(false);
		numCards.setEnabled(false);
		iDontKnowCheck.setEnabled(false);
		
		//Disables editing on each card in the view panel
		cardsPanel2.disableCards();
		
		//Hide the save and cancel buttons
		saveButtonPanel.setVisible(false);
		cancelDeckButton.setVisible(false);
		
		//Hide the error field
		errorField.setVisible(false);
}

	/**
	 * @return the isReopen
	 */
	public boolean isReopen() {
		return isReopen;
	}
}
