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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.border.Border;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons.CancelButton;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.DescriptionJTextArea;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.ErrorLabel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.IDataField;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.IErrorView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.NameJTextField;

/**
 * The panel for the deck creation process
 * Used to allow the user to create a new deck by filling out the indicated fields
 */
public class CreateDeckPanel extends JScrollPane implements IDataField{
	
	private NameJTextField nameTextField;
	private DescriptionJTextArea descriptionTextField;
	
	private JTextField numCards;
	private JButton submitNumCards;
	
	private JComboBox<String> colorDropDown;
	
	private JTextField cardValue;
	
	private JRadioButton singleSelection;
	private JRadioButton multipleSelection;
	
	private final JPanel cardsPanel = new JPanel();
	
	private CancelButton cancelDeckButton;
	
	private JButton saveButton;
	
	private ErrorLabel errorField;
	
	private final Border defaultTextFieldBorder = (new JTextField()).getBorder();
	
	private final Border errorBorder = BorderFactory.createLineBorder(Color.RED);
	
	private final CardImage cardRed = new CardImage(ColorEnum.RED);
	
	private final CardImage cardBlue = new CardImage(ColorEnum.BLUE);
	
	private final CardImage cardGreen = new CardImage(ColorEnum.GREEN);
	
	private final CardImage cardPurple = new CardImage(ColorEnum.PURPLE);
	
	private final CardImage cardYellow = new CardImage(ColorEnum.YELLOW);
	
	private ArrayList<CardImage> cards = new ArrayList<CardImage>();
	
	public CreateDeckPanel(){
		
		build();
	}
	
	/**
	 * Builds the layout for this panel
	 * Sets up all of the elements in their respective locations
	 */
	public void build(){
		/* Set up initial container with spring layout */
		Container view = new Container();
		SpringLayout layout = new SpringLayout();
		view.setLayout(layout);
		
		/**
		 *  Create components
		 */
		/* name and description */
		JLabel nameLabel = new JLabel("Name * ");
		nameTextField = new NameJTextField(20);
		addKeyListenerTo(nameTextField);
		JLabel descriptionLabel = new JLabel("Description");
		descriptionTextField = new DescriptionJTextArea();
		descriptionTextField.setLineWrap(true);
		JScrollPane descriptionScroll = new JScrollPane(descriptionTextField);
		descriptionScroll.setPreferredSize(new Dimension(400, 20));		
		
		/* panel underneath name and description, holds the radio buttons, the number of cards entry, and the color dropdown */
		JPanel numCardsAndColorAndSelectedTypePanel = new JPanel();
		
		/* radio buttons for type of card selection - single or multiple */
		JLabel selectionTypeLabel = new JLabel("Selection Type * ");
		JPanel selectionLabelPanel = new JPanel();
		selectionLabelPanel.add(selectionTypeLabel);
		singleSelection = new JRadioButton("Single Card Selection");
		multipleSelection = new JRadioButton("Multiple Card Selection");
		multipleSelection.setSelected(true);
		ButtonGroup radioGroup = new ButtonGroup();
		radioGroup.add(singleSelection);
		radioGroup.add(multipleSelection);		
		JPanel radioButtonsPanel = new JPanel();
		radioButtonsPanel.setLayout(new BorderLayout());
		radioButtonsPanel.add(selectionLabelPanel, BorderLayout.PAGE_START);
		radioButtonsPanel.add(singleSelection, BorderLayout.LINE_START);
		radioButtonsPanel.add(multipleSelection, BorderLayout.LINE_END);
		
		/* blank panel for formatting */
		JPanel blankPanel1 = new JPanel();
		blankPanel1.setPreferredSize(new Dimension(85, 5));
		
		/* number of cards desired by user */
		JLabel numCardsLabel = new JLabel("Number of Cards * ");
		JPanel numLabelPanel = new JPanel();
		numLabelPanel.add(numCardsLabel);
		numCards = new NameJTextField(5);
		numCards.setText("1");
		addKeyListenerTo(numCards);
		addMouseListenerToNumberOfCardsTextEntry(numCards);
		submitNumCards = new JButton("Submit");
		submitNumCards.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	displayNumCards();
		        cardsPanel.revalidate();
		        cardsPanel.repaint();
		    }
		});
		addMouseListenerToNumberOfCardsSubmitButton(submitNumCards);
		
		JPanel numPanel = new JPanel();
		numPanel.setLayout(new BorderLayout());
		numPanel.add(numLabelPanel, BorderLayout.PAGE_START);
		numPanel.add(numCards, BorderLayout.LINE_START);
		numPanel.add(submitNumCards, BorderLayout.LINE_END);
		
		/* blank panel for formatting */
		JPanel blankPanel2 = new JPanel();
		blankPanel2.setPreferredSize(new Dimension(95, 5));
		
		/* color desired by user */
		JLabel colorLabel = new JLabel("Select Card Color * ");
		JPanel colorLabelPanel = new JPanel();
		colorLabelPanel.add(colorLabel);
		colorDropDown = new JComboBox<String>();	
		colorDropDown.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        chooseCardColor();
		    }
		});
		colorDropDown.addItem("Red (Default)");
		colorDropDown.addItem("Blue");
		colorDropDown.addItem("Green");
		colorDropDown.addItem("Purple");
		colorDropDown.addItem("Yellow");
		JPanel colorPanel = new JPanel();
		colorPanel.setLayout(new BorderLayout());
		colorPanel.add(colorLabelPanel, BorderLayout.PAGE_START);
		colorPanel.add(colorDropDown, BorderLayout.PAGE_END);
		
		/* adds the 5 previous components (including formatting panels) to a single panel so the items can appear horizontally.
		 * Initially they were all on the same panel because they needed to all stretch together, but now it is easier to just leave
		 * them instead of re-doing each spring constraint. */
		numCardsAndColorAndSelectedTypePanel.add(radioButtonsPanel);
		numCardsAndColorAndSelectedTypePanel.add(blankPanel1);
		numCardsAndColorAndSelectedTypePanel.add(numPanel);
		numCardsAndColorAndSelectedTypePanel.add(blankPanel2);
		numCardsAndColorAndSelectedTypePanel.add(colorPanel);
		
		/* Card panel for the cards to appear in. Get rid of border when something actually appears */
		JScrollPane cardScrollPane = new JScrollPane(cardsPanel);
		
		cardsPanel.setPreferredSize(new Dimension(10, 520));
		cardsPanel.add(cardRed);
		cards.add(cardRed);
		cardRed.setVisible(true);
		
		/* Not currently in use. Re-add this if the card-value setting method is desired below the card panel */
		JPanel valuePanel = new JPanel();
		JButton setCardValue = new JButton("Set Card Value");
		cardValue = new NameJTextField(5);
		valuePanel.add(cardValue);
		valuePanel.add(setCardValue);
		
		/* save button */
		saveButton = new JButton("Save Deck");
		saveButton.setEnabled(false);
		
		/*cancel button */
		cancelDeckButton = new CancelButton("Cancel Game", this);
		
		errorField = new ErrorLabel();
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
		//view.add(valuePanel);
		view.add(saveButton);
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
		
		layout.putConstraint(SpringLayout.WEST, numCardsAndColorAndSelectedTypePanel, 0, SpringLayout.WEST, view);
		layout.putConstraint(SpringLayout.NORTH, numCardsAndColorAndSelectedTypePanel, 10, SpringLayout.SOUTH, nameLabel);
		
		layout.putConstraint(SpringLayout.WEST, cardScrollPane, 10, SpringLayout.WEST, view);
		layout.putConstraint(SpringLayout.EAST, cardScrollPane, -10, SpringLayout.EAST, view);
		layout.putConstraint(SpringLayout.NORTH, cardScrollPane, 10, SpringLayout.SOUTH, numCardsAndColorAndSelectedTypePanel);
		layout.putConstraint(SpringLayout.SOUTH, cardScrollPane, -45, SpringLayout.SOUTH, view);
			
		/*layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, valuePanel, 5, SpringLayout.HORIZONTAL_CENTER, view);
		layout.putConstraint(SpringLayout.NORTH, valuePanel, 10, SpringLayout.SOUTH, cardsPanel);
		*/
		
		layout.putConstraint(SpringLayout.WEST, saveButton, 10, SpringLayout.WEST, view);
		layout.putConstraint(SpringLayout.NORTH, saveButton, 10, SpringLayout.SOUTH, cardScrollPane);
		
		layout.putConstraint(SpringLayout.WEST, cancelDeckButton, 10, SpringLayout.EAST, saveButton);
		layout.putConstraint(SpringLayout.NORTH, cancelDeckButton, 10, SpringLayout.SOUTH, cardScrollPane);
		
		layout.putConstraint(SpringLayout.WEST, errorField, 10, SpringLayout.EAST, cancelDeckButton);
		layout.putConstraint(SpringLayout.NORTH, errorField, 10, SpringLayout.SOUTH, cardScrollPane);
		
		revalidate();
		repaint();
		
		setViewportView(view);
	}
	
	/**
	 * Build a new font based on specified size
	 * @param size is how much larger you want the font to be than a default font in a JTextArea
	 * Can have a negative value for size to decrease the font size
	 * @return the font of the designated size
	 */
	public Font makeFont(int size) {
		/**
		 * Creates a new font for use later
		 */
		//create a dummy JTextArea
		JTextArea editingArea = new JTextArea();
		// get the current font
		Font f = editingArea.getFont();
		// create a new, larger font from the current font
		Font newFont = new Font(f.getFontName(), f.getStyle(), f.getSize()+size);		
		//set the bigger font for userStoryDesc
		Font bigFont = newFont;
		return bigFont;
	}
	
	/**
	 * Gets the nameTextField for this deck panel
	 * @return the nameTextField
	 */
	public NameJTextField getBoxName(){
		return nameTextField;
	}
	/**
	 * Gets the descriptionTextField for this deck panel
	 * @return the descriptionTextField
	 */
	public DescriptionJTextArea getBoxDescription() {
		return descriptionTextField;
	}
	/**
	 * Gets the errorField for this deck panel
	 * @return the errorField
	 */
	public ErrorLabel getErrorField(){
		return errorField;
	}
	/**
	 * Gets the numCards for this deck panel
	 * @return the numCards
	 */
	public JTextField getNumCards(){
		return numCards;
	}
	
	public JComboBox<String> getColorDropDown(){
		return colorDropDown;
	}
	
	public JPanel getCardsPanel(){
		return cardsPanel;
	}
	
	/**
	 * Checks all fields to determine if they are prepared to be removed.
	 * If a field is invalid the it warns the user with a notification and by highlighting
	 * the offending box on the GUI.
	 * @param warningField the field to output the errors to
	 * @return true If all fields are valid and the window is ready to be removed
	 */
	@Override
	public boolean validateField(IErrorView warningField, boolean showLabel, boolean showBox) {
		boolean isNameValid = false;		
		boolean isNumCardsValid = false;
		
		isNumCardsValid = verifyNumberOfCards();
		if(!isNumCardsValid) {
			errorField.setText("Number of cards must be a 1-or-2-digit integer between 1 and 25");
			//getNumCards().setBorder(errorBorder);
			submitNumCards.setEnabled(false);
			saveButton.setEnabled(false);
		}
		else {
			getNumCards().setBorder(defaultTextFieldBorder);
			errorField.setText("");
			submitNumCards.setEnabled(true);
		}
		
		isNameValid = getBoxName().validateField(errorField, showLabel, showBox);
		if (!isNameValid) {
			getBoxName().setBorder(defaultTextFieldBorder);
			errorField.setText("Name is required");
			saveButton.setEnabled(false);
		}
		else if(isNameValid && isNumCardsValid){
			errorField.setText("");
			saveButton.setEnabled(true);
		}
		
		return (isNameValid && isNumCardsValid);
	}
	
	@Override
	public boolean hasChanges() {
		// TODO Auto-generated method stub
		return false;
	}
	
	private void addKeyListenerTo(JComponent component){
		component.addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent arg0) {	
				validateField(errorField, true, true);
			}
		});
	}
	
	/**
	 * Checks to make sure the number of cards inputted is 1-24
	 * @return
	 */
	public boolean verifyNumberOfCards() {
		String text = getNumCards().getText();
		String allowedChars = "123456789";
		String allowedChars1 = "01234";
		String allowedChars2 = "0123456789";
		if (text.length() == 0) {
			return false;
		}
		/* Checks to see if the number is 1-9 */
		if (text.length() == 1){
			if(allowedChars.contains(Character.toString(text.charAt(0)))){
				return true;
			}
		}
		/* Checks to see if the number is 10-24 */
		if (text.length() == 2){
			if(text.charAt(0) == '2'){
				if(allowedChars1.contains(Character.toString(text.charAt(1)))){
					return true;
				}
			}
			if(text.charAt(0) == '1'){
				if(allowedChars2.contains(Character.toString(text.charAt(1)))){
					return true;
				}
			}
			if(text.charAt(0) == '0'){
				if(allowedChars.contains(Character.toString(text.charAt(1)))){
					return true;
				}
			}
		}
		return false;
	}
	
	/** 
	 * This method makes sure that the color selected in the dropdown is the color being display on all the cards
	 * It does this by checking how many components are in the card panel, then once it has stored this number,
	 * it removes all the components in the card panel. It then generates the number of cards of the correct color
	 * (since it now knows the color) that was equal to the previously recorded number of components in the panel.
	 * @return
	 */
	public void chooseCardColor(){
		String color = (String)getColorDropDown().getSelectedItem();
		int numCardsPresent = cardsPanel.getComponentCount();
		cardsPanel.removeAll();
		cards.removeAll(cards);
		/* Later, when real functionality occurs, we will need a way of storing the cards 
		 * being generated, so we can later tell if one is selected or not.
		 * This will be necessary when assigning values to each card. */
		if(color == "Red (Default)"){
			 for(int i=0; i < numCardsPresent; i++){
				 cardsPanel.add(new CardImage(ColorEnum.RED));
				 cards.add(new CardImage(ColorEnum.RED));
			 }
		}
		if(color == "Blue"){
			for(int i=0; i < numCardsPresent; i++){
				cardsPanel.add(new CardImage(ColorEnum.BLUE));
				cards.add(new CardImage(ColorEnum.BLUE));
			 }
		}
		if(color == "Green"){
			for(int i=0; i < numCardsPresent; i++){
				cardsPanel.add(new CardImage(ColorEnum.GREEN));
				cards.add(new CardImage(ColorEnum.GREEN));
			 }
		}
		if(color == "Purple"){
			for(int i=0; i < numCardsPresent; i++){
				cardsPanel.add(new CardImage(ColorEnum.PURPLE));
				cards.add(new CardImage(ColorEnum.PURPLE));
			 }
		}
		if(color == "Yellow"){
			for(int i=0; i < numCardsPresent; i++){
				 cardsPanel.add(new CardImage(ColorEnum.YELLOW));
				 cards.add(new CardImage(ColorEnum.YELLOW));
			 }
		}
		System.out.print(cards.size());
		cardsPanel.revalidate();
        cardsPanel.repaint();
	}
	
	public void displayNumCards(){
		int numCardsSubmitted = Integer.parseInt(getNumCards().getText());
		cardsPanel.removeAll();
        for(int i=0; i < numCardsSubmitted; i++){		// Here, we are creating the correct number of components in the cards panel,
			cardsPanel.add(new JLabel("countLabel"));   // so that when chooseCardColor() is called it creates the correct number of
		}												// the correct color of cards.
        chooseCardColor();
        cardsPanel.revalidate();
        cardsPanel.repaint();
	}
	
	private void addMouseListenerToNumberOfCardsSubmitButton(JComponent component){
		component.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent arg0) {
					if(!submitNumCards.isEnabled()){
						errorField.setText("Number of cards must be a 1-or-2-digit integer between 1 and 24");
					}
					else{}//it will perform the action listener
			}
		});
	}
	
	private void addMouseListenerToNumberOfCardsTextEntry(JComponent component){
		component.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent arg0) {
				getNumCards().selectAll();
			}
		});
	}
	
}