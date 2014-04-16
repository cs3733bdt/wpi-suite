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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.border.Border;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons.NewLaunchGameButtonPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons.NewSaveGameButtonPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.DescriptionJTextArea;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.ErrorLabel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.IDataField;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.IErrorView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.NameJTextField;

/**
 * TODO DOCUMENTATION
 */
public class NewLeftHalfCreateGamePanel extends JScrollPane implements IDataField{
	
	private final Border defaultBorder = (new JTextField()).getBorder();
	
	private NameJTextField nameTextField;
	private DescriptionJTextArea descriptionTextField;
	
	private JRadioButton cardsButton = new JRadioButton("Estimate With Cards");
	private JRadioButton textEntryButton = new JRadioButton("Estimate With Text Entry");
	
	private NewAddEndDatePanel endDateField;
	
	private NewSaveGameButtonPanel saveGameButton;
	private NewLaunchGameButtonPanel launchGameButton;
	//TODO: IMPLEMENT A CANCELGAMEBUTTONPANEL CLASS
	//private NewCancelGameButton cancelGameButton;
	private JButton cancelGameButton = new JButton("Cancel Game");
	
	/** Shows the names of the errors */
	private ErrorLabel errorField;
	
	private Date endDate;
	
	private Game game;
	
	private NewCreateGamePanel parent;
	
	public NewLeftHalfCreateGamePanel(NewCreateGamePanel mainPanel) {
		parent = mainPanel;
		this.game = mainPanel.getGame();
		build();
		buildFields();
	}
	
	public void build(){
		setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		Container leftView = new Container(); 				//Creates the container for everything in the view
		SpringLayout layout = new SpringLayout(); 			//Creates the layout to be used: Spring Layout
		leftView.setLayout(layout);							//Sets the container to have the spring layout
		
		/**
		 * Create and/or initialize components and/or set component properties
		 */
		JLabel nameLabel = new JLabel("Name * ");						//Creates the Label for the Name
		nameTextField = new NameJTextField(30);							//Initializes the text field for the game name and sets the size to 30
		
		JLabel descLabel = new JLabel("Description * ");				//Creates the label for the Description
		
		descriptionTextField = new DescriptionJTextArea();				//Initializes the text area for the game description
		descriptionTextField.setBorder(defaultBorder);					//Sets the default border to the description text area
		
		JScrollPane descPane = new JScrollPane(descriptionTextField);	//Creates the scrollPane for the description field
		descPane.setPreferredSize(new Dimension(200, 200));				//Sets the preferred(which works as minimum for some reason) size of the description scroll pane
		
		JPanel estimateSelectionPanel = new JPanel();					//Creates the panel for the estimate radio buttons
		estimateSelectionPanel.add(cardsButton);						//Adds the card option radio button to the panel
		estimateSelectionPanel.add(textEntryButton);					//Adds the text option radio button to the panel
		
		cardsButton.setSelected(true);									//Sets the default radio button selection to card selection
		
		ButtonGroup radioGroup = new ButtonGroup();						//Creates a radio button group to make it so that you can only select one of the radio buttons in the group
		radioGroup.add(cardsButton);									//Adds the card option radio button to the group
		radioGroup.add(textEntryButton);								//Adds the text option radio button to the group
		
		endDateField = new NewAddEndDatePanel(this);					//Creates an end date panel
		
		saveGameButton = new NewSaveGameButtonPanel(parent);				//Creates a save game button
		launchGameButton = new NewLaunchGameButtonPanel(parent);		//Creates a launch game button
		//cancelGameButton = new NewCancelGameButtonPanel(this);		//TODO implement this
		
		JPanel buttonPanel = new JPanel();								//Creates a panel for the buttons
		buttonPanel.add(saveGameButton);								//Adds the save button to the panel
		buttonPanel.add(launchGameButton);								//Adds the launch button to the panel
		//buttonPanel.add(cancelGameButton);								//Adds the cancel button to the panel
		
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
		leftView.add(buttonPanel);						//Adds the panel with the buttons to the container
		leftView.add(errorField);						//Adds stuff
		
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
        
		//layout.putConstraint(SpringLayout.SOUTH, endDateField, 50, SpringLayout.NORTH, buttonPanel);
		layout.putConstraint(SpringLayout.NORTH, endDateField, 10, SpringLayout.SOUTH, estimateSelectionPanel);	//Adds the end date field underneath the radio buttons panel
		//layout.putConstraint(SpringLayout.SOUTH, endDateField, 50, SpringLayout.NORTH, buttonPanel);	
		layout.putConstraint(SpringLayout.WEST, endDateField, 5, SpringLayout.WEST, leftView);					//Makes sure the left side of the panel stretches with the left side of the container
		layout.putConstraint(SpringLayout.EAST, endDateField, -5, SpringLayout.EAST, leftView);					//Makes sure the right side of the panel stretches with the right side of the container
        
		layout.putConstraint(SpringLayout.SOUTH, buttonPanel, -20, SpringLayout.SOUTH, leftView);				//Adds the button panel to the bottom of the container
		layout.putConstraint(SpringLayout.WEST, buttonPanel, 5, SpringLayout.WEST, leftView);					//Makes sure the left side of the panel stretches with the left side of the container
		layout.putConstraint(SpringLayout.EAST, buttonPanel, 5, SpringLayout.EAST, leftView);					//Makes sure the right side of the panel stretches with the right side of the container
		
		layout.putConstraint(SpringLayout.NORTH, errorField, 3, SpringLayout.SOUTH, buttonPanel);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, errorField, 5, SpringLayout.HORIZONTAL_CENTER, leftView);
		
		setMinimumSize(new Dimension(333, 115));			//Sets the minimum size of the left half view
		leftView.setPreferredSize(new Dimension(410, 420));		//Sets the size of the view
		
		revalidate();
		repaint();
		
		setViewportView(leftView);						//Sets the view of the scrollpane to be the entire container which has everything contained within it

	}
	
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
	
	public NewLeftHalfCreateGamePanel(Game game, boolean withError) {
		this(new NewCreateGamePanel(game));
		buildFields();
		if (withError) {
			JOptionPane
					.showMessageDialog(
							null,
							"\tYour connection to the server has been lost.\n"
									+ "\tYour changes have been resored but no further changes to the server can be made.\n"
									+ "\tPlease save your changes to a text file and restart Janeway.",
							"Network Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public NewAddEndDatePanel getEndDateField(){
		return endDateField;
	}
	
	/**
	 * Getter for the Game Name text entry
	 * @return nameTextField
	 */
	public String getNameText() {
		System.out.println(this.nameTextField.getText());
		return this.nameTextField.getText();
	}
	
	/**
	 * Getter for the Game Description text entry
	 * @return descriptionTextField
	 */
	public String getDescText() {
		System.out.println(this.descriptionTextField.getText());
		return this.descriptionTextField.getText();
	}

	public NameJTextField getBoxName(){
		return nameTextField;
	}
	
	public void setBoxName(String newName){
		this.nameTextField.setText(newName);
	}
	
	public DescriptionJTextArea getBoxDescription() {
		return descriptionTextField;
	}
	
	public void setBoxDescription(String newDescription){
		this.descriptionTextField.setText(newDescription);
	}
	
	public JLabel getErrorName(){
		//TODO add errors to the indivitdual fields
		//WHEN FIXED UNCOMMENT THE LINES THAT USE THIS METHOD IN VALIDATE
		return null;
	}
	
	public void displayError(String error){
		errorField.setText(error);
	}
	
	/**
	 * Checks all fields to determine if they are prepared to be removed.
	 * If a field is invalid the it warns the user with a notification and by highlighting
	 * the offending box on the GUI.
	 * @param warningField the field to output the errors to
	 * @return true If all fields are valid and the window is ready to be removed
	 */
	@Override
	public boolean validateField(IErrorView warningField) {
		boolean isNameValid = false;
		boolean isDescriptionValid = false;
		boolean isEndDateValid = false;
		
		isEndDateValid = getEndDateField().validateField(errorField);
		
		isDescriptionValid = getBoxDescription().validateField(errorField);

		isNameValid = getBoxName().validateField(errorField);
		
		return (isNameValid && isDescriptionValid && isEndDateValid);
	}
	
	

	@Override
	public boolean hasChanges() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean doesUseCards(){
		return cardsButton.isSelected();
	}
	
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
	
	
}