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
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.DescriptionJTextArea;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.NameJTextField;

/**
 * The panel for the deck creation process
 * Used to allow the user to create a new deck by filling out the indicated fields
 */
public class CreateDeckPanel extends JScrollPane {
	
	private NameJTextField nameTextField;
	private DescriptionJTextArea descriptionTextField;
	
	private NameJTextField numCards;
	private NameJTextField cardValue;
	
	private JRadioButton singleSelection;
	private JRadioButton multipleSelection;
	
	private JButton saveButton;
	
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
		
		/* Create components */
		JLabel nameLabel = new JLabel("Name * ");
		nameTextField = new NameJTextField(20);
		
		JLabel descriptionLabel = new JLabel("Description");
		descriptionTextField = new DescriptionJTextArea();
		descriptionTextField.setLineWrap(true);
		
		JScrollPane descriptionScroll = new JScrollPane(descriptionTextField);
		descriptionScroll.setPreferredSize(new Dimension(400, 20));		
		
		JPanel numCardsAndColorAndSelectedTypePanel = new JPanel();
		
		JLabel selectionTypeLabel = new JLabel("Selection Type * ");
		JPanel selectionLabelPanel = new JPanel();
		selectionLabelPanel.add(selectionTypeLabel);
		singleSelection = new JRadioButton("Single Card Selection");
		multipleSelection = new JRadioButton("Multiple Card Selection");
		multipleSelection.setSelected(true);
		ButtonGroup radioGroup = new ButtonGroup();						//Creates a radio button group to make it so that you can only select one of the radio buttons in the group
		radioGroup.add(singleSelection);									//Adds the card option radio button to the group
		radioGroup.add(multipleSelection);		
		JPanel radioButtonsPanel = new JPanel();
		radioButtonsPanel.setLayout(new BorderLayout());
		radioButtonsPanel.add(selectionLabelPanel, BorderLayout.PAGE_START);
		radioButtonsPanel.add(singleSelection, BorderLayout.LINE_START);
		radioButtonsPanel.add(multipleSelection, BorderLayout.LINE_END);
		
		JPanel blankPanel1 = new JPanel();
		blankPanel1.setPreferredSize(new Dimension(85, 5));
		
		JLabel numCardsLabel = new JLabel("Number of Cards * ");
		JPanel numLabelPanel = new JPanel();
		numLabelPanel.add(numCardsLabel);
		numCards = new NameJTextField(5);
		numCards.setText("1");
		JButton submitNumCards = new JButton("Submit");
		JPanel numPanel = new JPanel();
		numPanel.setLayout(new BorderLayout());
		numPanel.add(numLabelPanel, BorderLayout.PAGE_START);
		numPanel.add(numCards, BorderLayout.LINE_START);
		numPanel.add(submitNumCards, BorderLayout.LINE_END);
		
		JPanel blankPanel2 = new JPanel();
		blankPanel2.setPreferredSize(new Dimension(95, 5));
		
		JLabel colorLabel = new JLabel("Select Card Color * ");
		JPanel colorLabelPanel = new JPanel();
		colorLabelPanel.add(colorLabel);
		JComboBox<String> colorDropDown = new JComboBox<String>();	
		colorDropDown.addItem("Red (Default)");
		colorDropDown.addItem("Blue");
		colorDropDown.addItem("Green");
		colorDropDown.addItem("Purple");
		colorDropDown.addItem("Yellow");
		JPanel colorPanel = new JPanel();
		colorPanel.setLayout(new BorderLayout());
		colorPanel.add(colorLabelPanel, BorderLayout.PAGE_START);
		colorPanel.add(colorDropDown, BorderLayout.PAGE_END);
		
		numCardsAndColorAndSelectedTypePanel.add(radioButtonsPanel);
		numCardsAndColorAndSelectedTypePanel.add(blankPanel1);
		numCardsAndColorAndSelectedTypePanel.add(numPanel);
		/*numCardsAndColorAndSelectedTypePanel.add(numCardsLabel);
		numCardsAndColorAndSelectedTypePanel.add(numCards);
		numCardsAndColorAndSelectedTypePanel.add(submitNumCards);*/
		numCardsAndColorAndSelectedTypePanel.add(blankPanel2);
		/*numCardsAndColorAndSelectedTypePanel.add(colorLabel);
		numCardsAndColorAndSelectedTypePanel.add(colorDropDown);*/
		numCardsAndColorAndSelectedTypePanel.add(colorPanel);
		
		JPanel cardsPanel = new JPanel();
		cardsPanel.setPreferredSize(new Dimension(10, 400));
		cardsPanel.setBorder(nameTextField.getBorder());
		
		JPanel valuePanel = new JPanel();
		JButton setCardValue = new JButton("Set Card Value");
		cardValue = new NameJTextField(5);
		valuePanel.add(cardValue);
		valuePanel.add(setCardValue);
		
		saveButton = new JButton("Save Deck");
		
		/* Add components to the container */
		view.add(nameLabel);
		view.add(nameTextField);
		view.add(descriptionLabel);
		view.add(descriptionScroll);
		view.add(numCardsAndColorAndSelectedTypePanel);
		/*view.add(numCardsLabel);
		view.add(numCards);
		view.add(submitNumCards);
		view.add(colorLabel);
		view.add(colorDropDown);*/
		view.add(cardsPanel);
		//view.add(valuePanel);
		view.add(saveButton);
		
		/* Sets the layout constraints for each component */
		layout.putConstraint(SpringLayout.WEST, nameLabel, 10, SpringLayout.WEST, view);
		layout.putConstraint(SpringLayout.NORTH, nameLabel, 10, SpringLayout.NORTH, view);
		
		layout.putConstraint(SpringLayout.WEST, nameTextField, 5, SpringLayout.EAST, nameLabel);
		layout.putConstraint(SpringLayout.NORTH, nameTextField, 10, SpringLayout.NORTH, view);
		
		layout.putConstraint(SpringLayout.WEST, descriptionLabel, 10, SpringLayout.EAST, nameTextField);
		layout.putConstraint(SpringLayout.NORTH, descriptionLabel, 10, SpringLayout.NORTH, view);
		
		layout.putConstraint(SpringLayout.WEST, descriptionScroll, 5, SpringLayout.EAST, descriptionLabel);
		layout.putConstraint(SpringLayout.NORTH, descriptionScroll, 10, SpringLayout.NORTH, view);
		//layout.putConstraint(SpringLayout.EAST, descriptionScroll, -10, SpringLayout.EAST, view);
		
		layout.putConstraint(SpringLayout.WEST, numCardsAndColorAndSelectedTypePanel, 0, SpringLayout.WEST, view);
		layout.putConstraint(SpringLayout.NORTH, numCardsAndColorAndSelectedTypePanel, 10, SpringLayout.SOUTH, nameLabel);
		
		/*layout.putConstraint(SpringLayout.WEST, numCardsLabel, 10, SpringLayout.WEST, view);
		layout.putConstraint(SpringLayout.NORTH, numCardsLabel, 12, SpringLayout.SOUTH, nameTextField);
	
		layout.putConstraint(SpringLayout.WEST, numCards, 5, SpringLayout.EAST, numCardsLabel);
		layout.putConstraint(SpringLayout.NORTH, numCards, 12, SpringLayout.SOUTH, nameTextField);
		
		layout.putConstraint(SpringLayout.WEST, submitNumCards, 5, SpringLayout.EAST, numCards);
		layout.putConstraint(SpringLayout.NORTH, submitNumCards, 10, SpringLayout.SOUTH, nameTextField);
		
		layout.putConstraint(SpringLayout.WEST, colorLabel, 10, SpringLayout.EAST, nameTextField);
		layout.putConstraint(SpringLayout.NORTH, colorLabel, 12, SpringLayout.SOUTH, descriptionScroll);
		
		layout.putConstraint(SpringLayout.WEST, colorDropDown, 5, SpringLayout.EAST, colorLabel);
		layout.putConstraint(SpringLayout.NORTH, colorDropDown, 10, SpringLayout.SOUTH, descriptionScroll);
		*/
		layout.putConstraint(SpringLayout.WEST, cardsPanel, 10, SpringLayout.WEST, view);
		layout.putConstraint(SpringLayout.EAST, cardsPanel, -10, SpringLayout.EAST, view);
		layout.putConstraint(SpringLayout.NORTH, cardsPanel, 10, SpringLayout.SOUTH, numCardsAndColorAndSelectedTypePanel);
		layout.putConstraint(SpringLayout.SOUTH, cardsPanel, -45, SpringLayout.SOUTH, view);
		
		layout.putConstraint(SpringLayout.WEST, saveButton, 10, SpringLayout.WEST, view);
		layout.putConstraint(SpringLayout.NORTH, saveButton, 10, SpringLayout.SOUTH, cardsPanel);
				
		/*layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, valuePanel, 5, SpringLayout.HORIZONTAL_CENTER, view);
		layout.putConstraint(SpringLayout.NORTH, valuePanel, 10, SpringLayout.SOUTH, cardsPanel);
		*/
		
		/*layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, colorLabel, 5, SpringLayout.HORIZONTAL_CENTER, view);
		layout.putConstraint(SpringLayout.NORTH, colorLabel, 75, SpringLayout.SOUTH, nameLabel);
		
		layout.putConstraint(SpringLayout.WEST, colorsPanel, 10, SpringLayout.WEST, view);
		layout.putConstraint(SpringLayout.NORTH, colorsPanel, 10, SpringLayout.SOUTH, colorLabel);
		layout.putConstraint(SpringLayout.SOUTH, colorsPanel, -100, SpringLayout.SOUTH, view);
		layout.putConstraint(SpringLayout.EAST, colorsPanel, -10, SpringLayout.EAST, view);
*/
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
}