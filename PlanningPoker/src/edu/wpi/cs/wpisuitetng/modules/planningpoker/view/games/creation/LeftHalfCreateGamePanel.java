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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.GregorianCalendar;

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

import org.jdesktop.swingx.JXDatePicker;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.EstimatePanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons.CancelButton;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons.LaunchGameButtonPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons.SaveGameButtonPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.DescriptionJTextArea;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.ErrorLabel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.IDataField;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.IErrorView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.NameJTextField;

/**
 * The Left Half panel for the NewCreateGamePanel
 * Used to input the Games name, description, end date, whether it uses cards
 */
public class LeftHalfCreateGamePanel extends JScrollPane implements IDataField{
		
	private Container leftView;
	private SpringLayout layout;
	private CreateGamePanel parent;
	
	private NameJTextField nameTextField;
	private DescriptionJTextArea descriptionTextField;
	
	private JPanel estimateSelectionPanel;
	
	private JRadioButton cardsButton = new JRadioButton("Estimate With Cards");
	private JRadioButton textEntryButton = new JRadioButton("Estimate With Text Entry");
	
	private AddEndDatePanel endDateField;
	
	private JPanel deckDropDownPanel;
	
	private JComboBox<String> deckDropDown;	
	
	private SaveGameButtonPanel saveGameButton;
	private LaunchGameButtonPanel launchGameButton;
	private CancelButton cancelGameButton;
	
	/** Shows the names of the errors */
	private ErrorLabel errorField;
		
	private Game game;
	
	private final Border defaultTextFieldBorder = (new JTextField()).getBorder();
	 
	private final Border defaultTextAreaBorder = (new JTextArea()).getBorder();
	    
	private final Border defaultDateBorder = (new JXDatePicker()).getBorder();	
	
	
	/**
	 * Builds the left half of the CreateGamePanel
	 * @param mainPanel the main panel this half is located on
	 */
	public LeftHalfCreateGamePanel(CreateGamePanel mainPanel) {
		parent = mainPanel;
		game = mainPanel.getGame();
		build();
		buildFields();
		
		nameTextField.requestFocus();
		if(game != null){
			nameTextField.select(game.getName().length(), game.getName().length());
		}
	}
	
	/**
	 * Builds the layout for this panel
	 * Sets up all of the elements in their respective locations
	 */
	private void build(){
		setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		leftView = new Container(); 				//Creates the container for everything in the view
		layout = new SpringLayout(); 			//Creates the layout to be used: Spring Layout
		leftView.setLayout(layout);							//Sets the container to have the spring layout
		
		/**
		 * Create and/or initialize components and/or set component properties
		 */
		JLabel nameLabel = new JLabel("Name * ");						//Creates the Label for the Name
		nameTextField = new NameJTextField(30);							//Initializes the text field for the game name and sets the size to 30
		nameTextField.setFocusable(true);
		
		JLabel descLabel = new JLabel("Description * ");				//Creates the label for the Description
		
		descriptionTextField = new DescriptionJTextArea();				//Initializes the text area for the game description
		descriptionTextField.setBorder(defaultTextAreaBorder);					//Sets the default border to the description text area
		
		addKeyListenerTo(nameTextField);								//Adds KeyListener to update on key press
		addKeyListenerTo(descriptionTextField);							//Adds KeyListener to update on key press
		
		JScrollPane descPane = new JScrollPane(descriptionTextField);	//Creates the scrollPane for the description field
		descPane.setPreferredSize(new Dimension(200, 200));				//Sets the preferred(which works as minimum for some reason) size of the description scroll pane
		
		estimateSelectionPanel = new JPanel();					//Creates the panel for the estimate radio buttons
		estimateSelectionPanel.add(cardsButton);						//Adds the card option radio button to the panel
		estimateSelectionPanel.add(textEntryButton);					//Adds the text option radio button to the panel
		
		cardsButton.setSelected(true);									//Sets the default radio button selection to card selection
		
		ButtonGroup radioGroup = new ButtonGroup();						//Creates a radio button group to make it so that you can only select one of the radio buttons in the group
		radioGroup.add(cardsButton);									//Adds the card option radio button to the group
		radioGroup.add(textEntryButton);								//Adds the text option radio button to the group
		
		cardsButton.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	setDeckOptionsVisibility();
		    }
		});
		textEntryButton.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	setDeckOptionsVisibility();
		    }
		});
		
		endDateField = new AddEndDatePanel(this);					//Creates an end date panel
		
		addKeyListenerTo(endDateField.getDatePicker());
		addActionListenerTo(endDateField.getDatePicker());			//Adds ActionListener to update when a selection is made
		addActionListenerTo(endDateField.getHourSelection());			//Adds ActionListener to update when a selection is made
		addActionListenerTo(endDateField.getMinuteSelection());		//Adds ActionListener to update when a selection is made
		addActionListenerTo(endDateField.getAmPmSelection());			//Adds ActionListener to update when a selection is made
		
		deckDropDownPanel = new JPanel();
		JLabel deckDropDownLabel = new JLabel("Deck");
		
		deckDropDown = new JComboBox<String>();
		deckDropDown.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        chooseDeck();
		    }
		});
		/*
		 * TODO:Actually add the decks available to the user into the dropdown
		 * Will look something like this:
		 * for(each deck in ArrayOfDecksBelongingToUser){
		 * 		deckDropDown.addItem(deck.getName());
		 * }
		 */
		deckDropDown.addItem("Deck 1");
		deckDropDown.addItem("Deck 2");
		deckDropDown.addItem("Deck 3");
		deckDropDown.addItem("Deck 4");
		deckDropDown.addItem("Deck 5");
		
		deckDropDownPanel.add(deckDropDownLabel);
		deckDropDownPanel.add(deckDropDown);
		
		saveGameButton = new SaveGameButtonPanel(parent);			//Creates a save game button
		launchGameButton = new LaunchGameButtonPanel(parent);		//Creates a launch game button
		cancelGameButton = new CancelButton("Cancel Game", parent);		//TODO implement this
		
		JPanel buttonPanel = new JPanel();								//Creates a panel for the buttons
		buttonPanel.add(saveGameButton);								//Adds the save button to the panel
		buttonPanel.add(launchGameButton);								//Adds the launch button to the panel
		buttonPanel.add(cancelGameButton);								//Adds the cancel button to the panel		
		
		addMouseListenerTo(buttonPanel);							//Adds MouseListener to validate on mouse click
		addMouseListenerTo(saveGameButton.getSaveGameButton());		//Adds MouseListener to validate on mouse click
		addMouseListenerTo(launchGameButton.getLaunchGameButton());	//Adds MouseListener to validate on mouse click
		
		errorField = new ErrorLabel();
		errorField.setMinimumSize(new Dimension(150, 25));
		errorField.setForeground(Color.RED);
		
		/**
		 * Add components to container
		 */
		leftView.add(nameLabel);						//Adds name label to the container
		leftView.add(nameTextField);					//Adds name text field to the container
		leftView.add(descLabel);						//Adds description label to the container
		leftView.add(descPane);							//Adds description field to the container
		leftView.add(estimateSelectionPanel);			//Adds the panel with the radio buttons to the container
		leftView.add(endDateField);						//Adds the end date field to the container
		leftView.add(deckDropDownPanel);				//Adds the panel with the drop down menu of decks and label to the container
		leftView.add(buttonPanel);						//Adds the panel with the buttons to the container
		leftView.add(errorField);						//Adds the error field to the container
		
		/**
		 * Adjust layout constraints to correctly setup the layout of each component
		 */
		layout.putConstraint(SpringLayout.WEST, nameLabel, 5, SpringLayout.WEST, leftView); 					//Adds the name label to the far left
		layout.putConstraint(SpringLayout.NORTH, nameLabel, 5, SpringLayout.NORTH, leftView); 					//Adds the name label to the far top
		
		layout.putConstraint(SpringLayout.WEST, nameTextField, 5, SpringLayout.WEST, leftView); 				//Adds the name text field to the far left
        layout.putConstraint(SpringLayout.NORTH, nameTextField, 5, SpringLayout.SOUTH, nameLabel); 				//Adds the name text field just underneath the name label
        layout.putConstraint(SpringLayout.EAST, nameTextField, -5, SpringLayout.EAST, leftView); 				//Makes sure the right side of the text field stretches with the right side of the container
        
        layout.putConstraint(SpringLayout.WEST, descLabel, 5, SpringLayout.WEST, leftView); 					//Adds the description label to the far left
        layout.putConstraint(SpringLayout.NORTH, descLabel, 5, SpringLayout.SOUTH, nameTextField); 				//Adds the description label underneath the name label
        
        layout.putConstraint(SpringLayout.WEST, descPane, 5, SpringLayout.WEST, leftView); 						//Adds the description text area to the right of the name label
        layout.putConstraint(SpringLayout.NORTH, descPane, 5, SpringLayout.SOUTH, descLabel); 					//Adds the description text area to the far top
        layout.putConstraint(SpringLayout.EAST, descPane, -5, SpringLayout.EAST, leftView);						//Makes sure the right side of the text area stretches with the right side of the container
        
        layout.putConstraint(SpringLayout.NORTH, estimateSelectionPanel, 10, SpringLayout.SOUTH, descPane);		//Adds the panel with the radio buttons underneath the description text area
		layout.putConstraint(SpringLayout.WEST, estimateSelectionPanel, 5, SpringLayout.WEST, leftView);		//Makes sure the left side of the panel stretches with the left side of the container
		layout.putConstraint(SpringLayout.EAST, estimateSelectionPanel, 5, SpringLayout.EAST, leftView);		//Makes sure the right side of the panel stretches with the right side of the container
        
		layout.putConstraint(SpringLayout.NORTH, deckDropDownPanel, 0, SpringLayout.SOUTH, estimateSelectionPanel);		//Adds the deck dropdown and label panel underneath the end date field
		layout.putConstraint(SpringLayout.WEST, deckDropDownPanel, 5, SpringLayout.WEST, leftView);				//Makes sure the left side of the panel stretches with the left side of the container
		layout.putConstraint(SpringLayout.EAST, deckDropDownPanel, -5, SpringLayout.EAST, leftView);			//Makes sure the right side of the panel stretches with the right side of the container
		
		layout.putConstraint(SpringLayout.NORTH, endDateField, 10, SpringLayout.SOUTH, deckDropDownPanel);		//Adds the end date field underneath the radio buttons panel
		layout.putConstraint(SpringLayout.WEST, endDateField, 5, SpringLayout.WEST, leftView);					//Makes sure the left side of the panel stretches with the left side of the container
		layout.putConstraint(SpringLayout.EAST, endDateField, -5, SpringLayout.EAST, leftView);					//Makes sure the right side of the panel stretches with the right side of the container
		
		layout.putConstraint(SpringLayout.SOUTH, buttonPanel, -20, SpringLayout.SOUTH, leftView);				//Adds the button panel to the bottom of the container
		layout.putConstraint(SpringLayout.WEST, buttonPanel, 5, SpringLayout.WEST, leftView);					//Makes sure the left side of the panel stretches with the left side of the container
		layout.putConstraint(SpringLayout.EAST, buttonPanel, 5, SpringLayout.EAST, leftView);					//Makes sure the right side of the panel stretches with the right side of the container
		
		layout.putConstraint(SpringLayout.NORTH, errorField, 3, SpringLayout.SOUTH, buttonPanel);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, errorField, 5, SpringLayout.HORIZONTAL_CENTER, leftView);
		
		setMinimumSize(new Dimension(333, 115));			//Sets the minimum size of the left half view
		leftView.setPreferredSize(new Dimension(410, 450));		//Sets the size of the view
		
		revalidate();
		repaint();
		
		setViewportView(leftView);						//Sets the view of the scrollpane to be the entire container which has everything contained within it

	}
	
	/**
	 * Sets the fields for the current panel using the current game if the current game is not null
	 */
	private void buildFields(){
		if(game != null){
			nameTextField.setText(game.getName());
			descriptionTextField.setText(game.getDescription());
			setUsesCards(game.doesUseCards());
	
			//CALENDAR SETUP
			Calendar dateMaker = new GregorianCalendar();
			dateMaker.setTime(game.getEndDate());
			String hour = Integer.toString(dateMaker.get(Calendar.HOUR));
			String minute = Integer.toString(dateMaker.get(Calendar.MINUTE));		
			String AM_PM = "If this doesn't change, something is wrong";
			
			if(dateMaker.get(Calendar.AM_PM) == Calendar.AM){
				AM_PM = "AM";
			}
			if(dateMaker.get(Calendar.AM_PM) == Calendar.PM){
				AM_PM = "PM";
			}
			
			getEndDateField().setDateAndTime(dateMaker.getTime(), hour, minute, AM_PM);
			//>>END CALENDAR SETUP
		}
	}
	
	/**
	 * Gets the end date field for this panel
	 * @return the current end date field
	 */
	public AddEndDatePanel getEndDateField(){
		return endDateField;
	}
	
	/**
	 * Getter for the Game Name text entry
	 * @return nameTextField
	 */
	public String getNameText() {
		return nameTextField.getText();
	}
	
	/**
	 * Getter for the Game Description text entry
	 * @return descriptionTextField
	 */
	public String getDescText() {
		System.out.println(descriptionTextField.getText());
		return descriptionTextField.getText();
	}

	/**
	 * Gets the NameJTextField for this game panel
	 * @return the NameJTextField
	 */
	public NameJTextField getBoxName(){
		return nameTextField;
	}
	
	public DescriptionJTextArea getBoxDescription() {
		return descriptionTextField;
	}
	
	public ErrorLabel getErrorField(){
		return errorField;
	}
	
	/**
	 * Displays text when an error is encountered
	 * @param error The error text
	 */
	public void displayError(String error){
		errorField.setText(error);
	}
	
	
	
	public SaveGameButtonPanel getSaveGameButtonPanel() {
		return saveGameButton;
	}

	public LaunchGameButtonPanel getLaunchGameButtonPanel() {
		return launchGameButton;
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
		boolean isDescriptionValid = false;
		boolean isEndDateValid = false;
		
		isEndDateValid = getEndDateField().validateField(errorField, showLabel, showBox);
		if (!isEndDateValid) {
			
			getBoxDescription().setBorder(defaultTextAreaBorder);
			getBoxName().setBorder(defaultTextFieldBorder);
			parent.getRightHalf().getCurrentReqsPanel().setBorder((new JPanel().getBorder()));
		}

		isDescriptionValid = getBoxDescription().validateField(errorField, showLabel, showBox);
		if (!isDescriptionValid) {
			getEndDateField().setBorder(defaultDateBorder);
			getBoxName().setBorder(defaultTextFieldBorder);
			parent.getRightHalf().getCurrentReqsPanel().setBorder((new JPanel().getBorder()));
		}
		else{
			getBoxDescription().setBorder(defaultTextAreaBorder);
		}
		

		isNameValid = getBoxName().validateField(errorField, showLabel, showBox);
		if (!isNameValid) {
			getEndDateField().setBorder(defaultDateBorder);
			getBoxDescription().setBorder(defaultTextAreaBorder);
			parent.getRightHalf().getCurrentReqsPanel().setBorder((new JPanel().getBorder()));
		}
		
		return (isNameValid && isDescriptionValid && isEndDateValid);
	}
	
	

	@Override
	public boolean hasChanges() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Determines if a game uses cards or not
	 * @return true if the cards buttons is selected
	 */
	public boolean doesUseCards(){
		return cardsButton.isSelected();
	}
	
	/**
	 * Sets the toggle panel to indicate if this game is supposed to display a use of cards
	 * @param usesCards true if this game uses cards
	 */
	public void setUsesCards(boolean usesCards){
		if(usesCards){
			cardsButton.setSelected(true);
			textEntryButton.setSelected(false);
		}
		else{
			cardsButton.setSelected(false);
			textEntryButton.setSelected(true);
		}
	}
	
	private void addKeyListenerTo(JComponent component){
		component.addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent arg0) {	
				parent.updateButtons();
			}
		});
	}
	
	
	private void addActionListenerTo(JComponent component){
		if(component instanceof JComboBox){
			((JComboBox)component).addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					parent.updateButtons();
					getEndDateField().validateField(errorField, true, false);
				}		
			});
		}
		if(component instanceof JXDatePicker){
			((JXDatePicker)component).addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					parent.updateButtons();
					getEndDateField().validateField(errorField, true, false);
				}		
			});
		}
		
	}
	
	private void addMouseListenerTo(JComponent component){
		component.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent arg0) {
					parent.validateField(true, true);
			}
		});
	}
	
	public void chooseDeck(){
		String selectedDeckName = (String)deckDropDown.getSelectedItem();
		System.out.print("Deck Selected = " + selectedDeckName + "\n");
		//TODO: Actually then use the selected Deck...
	}
	
	private void setDeckOptionsVisibility(){
		if(cardsButton.isSelected()){
			leftView.setPreferredSize(new Dimension(410, 455));
			deckDropDownPanel.setVisible(true);
			layout.putConstraint(SpringLayout.NORTH, endDateField, 10, SpringLayout.SOUTH, deckDropDownPanel);
			revalidate();
			repaint();
		}
		else {
			leftView.setPreferredSize(new Dimension(410, 420));
			deckDropDownPanel.setVisible(false);
			layout.putConstraint(SpringLayout.NORTH, endDateField, 10, SpringLayout.SOUTH, estimateSelectionPanel);
			revalidate();
			repaint();
		}
	}
	
	public String dateToString(){
		return endDateField.toString();
	}
}