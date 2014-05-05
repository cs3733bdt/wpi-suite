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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.help;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;

public class CreateDeckHelp extends JScrollPane implements IHelpPanel {
	JLabel headingLabel;
	JLabel setUpCardsHeading;
	JLabel setUpCardspic;
	JTextArea setUpCardsExplanation;
	JLabel addingValuesToTheCardsHeading;
	JLabel addingValuesToTheCardspic;
	JTextArea addingValuesToTheCardsExplanation;
	
	public CreateDeckHelp() {
		build();
	}
	
	private void build() {
		/**
		 *  Set up initial container with spring layout */
		Container view = new Container();
		SpringLayout layout = new SpringLayout();
		view.setLayout(layout);
		view.setPreferredSize(new Dimension(1100, 600));
		setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		
		//Add the heading label to the Panel
		headingLabel = new JLabel("Create Deck Help");
		headingLabel.setFont(makeFont(8));
			
		
		//Add label to explanation for initalizing the cards
		setUpCardsHeading = new JLabel("Initialize the Deck");
		setUpCardsHeading.setFont(makeFont(5));

		//Add deck set up pic to the image panel;
		setUpCardspic = addImage("set_up_cards.png");

		//Add explanation for setting up cards
		setUpCardsExplanation = new JTextArea();
		setUpCardsExplanation.setText("To start making a deck you have to enter the Name of the deck "
				+ "you want created, and optionally, you can enter a Description of that deck. The Selection Type allows "
				+ "you to be able to select either multiple cards or just a single card when voting. Selecting multiple cards "
				+ "adds up all of the cards that you select and puts that as your total. The next step is to select the number "
				+ "of cards that you want in your deck, and then you have the option of changing the color of your cards. "
				+ "Finally you can choose whether or not to have an 'I dont know' button as an option when voting.");

		setUpCardsExplanation.setEditable(false);
		setUpCardsExplanation.setBackground(null);
		setUpCardsExplanation.setWrapStyleWord(true);
		setUpCardsExplanation.setLineWrap(true);

		//Add label to explanation for adding values to cards
		addingValuesToTheCardsHeading = new JLabel("Adding Values");
		addingValuesToTheCardsHeading.setFont(makeFont(5));

		//Add value pic to the image panel;
		addingValuesToTheCardspic = addImage("adding_Values_To_The_Cards.png");

		//Add explanation for adding values to the cards
		addingValuesToTheCardsExplanation = new JTextArea();
		addingValuesToTheCardsExplanation.setText("After the initialization of the deck you will see the cards appear. "
				+ "To add a value to a card select the text field that is on the card and enter the desired value."
				+ "If you press enter this value will be recorded and you be prompted to enter a value for the next card if"
				+ "it is appropriate.");

		addingValuesToTheCardsExplanation.setEditable(false);
		addingValuesToTheCardsExplanation.setBackground(null);
		addingValuesToTheCardsExplanation.setWrapStyleWord(true);
		addingValuesToTheCardsExplanation.setLineWrap(true);
		
		view.add(headingLabel);
		view.add(setUpCardsHeading);
		view.add(setUpCardspic);
		view.add(setUpCardsExplanation);
		view.add(addingValuesToTheCardsHeading);
		view.add(addingValuesToTheCardspic);
		view.add(addingValuesToTheCardsExplanation);

		/**
		 * Constraints for the overall panel layout
		 */
		layout.putConstraint(SpringLayout.NORTH, headingLabel, 5, SpringLayout.NORTH, view);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, headingLabel, 0, SpringLayout.HORIZONTAL_CENTER, view);
		
		// initialize the deck
		layout.putConstraint(SpringLayout.NORTH, setUpCardsHeading, 5, SpringLayout.SOUTH, headingLabel);
		layout.putConstraint(SpringLayout.WEST, setUpCardsHeading, 20, SpringLayout.WEST, view);
		
		layout.putConstraint(SpringLayout.NORTH, setUpCardspic, 20, SpringLayout.SOUTH, setUpCardsHeading);
		layout.putConstraint(SpringLayout.WEST, setUpCardspic, 5, SpringLayout.WEST, view);
		
		layout.putConstraint(SpringLayout.NORTH, setUpCardsExplanation, 20, SpringLayout.SOUTH, setUpCardsHeading);
		layout.putConstraint(SpringLayout.EAST, setUpCardsExplanation, 0, SpringLayout.EAST, view);
		layout.putConstraint(SpringLayout.WEST, setUpCardsExplanation, 20, SpringLayout.EAST, setUpCardspic);
		layout.putConstraint(SpringLayout.SOUTH, setUpCardsExplanation, 20, SpringLayout.NORTH, addingValuesToTheCardsHeading);


		// adding values to the cards
		layout.putConstraint(SpringLayout.NORTH, addingValuesToTheCardsHeading, 20, SpringLayout.SOUTH, setUpCardspic);
		layout.putConstraint(SpringLayout.WEST, addingValuesToTheCardsHeading, 20, SpringLayout.WEST, view);
		
		layout.putConstraint(SpringLayout.NORTH, addingValuesToTheCardspic, 20, SpringLayout.SOUTH, addingValuesToTheCardsHeading);
		layout.putConstraint(SpringLayout.WEST, addingValuesToTheCardspic, 5, SpringLayout.WEST, view);
		
		layout.putConstraint(SpringLayout.NORTH, addingValuesToTheCardsExplanation, 20, SpringLayout.SOUTH, addingValuesToTheCardsHeading);
		layout.putConstraint(SpringLayout.EAST, addingValuesToTheCardsExplanation, 0, SpringLayout.EAST, view);
		layout.putConstraint(SpringLayout.WEST, addingValuesToTheCardsExplanation, 20, SpringLayout.EAST, addingValuesToTheCardspic);

		setViewportView(view);
		revalidate();
		repaint();
	}
	
	/**
	 * Creates a font to be used for later
	 * @param size The size of the font
	 * @return font to be used 
	 */
	public Font makeFont(int size) {
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

	@Override
	public boolean readyToRemove() {
		return true;
	}

	@Override
	public int getIdentifierIndex() {
		return 4;
	}
	
	@Override
	public JLabel addImage(String image){
		JLabel helpLabel = new JLabel();
		
		try {
			Image imageToBeAdded = ImageIO.read(getClass().getResource(image));
			ImageIcon helpImage = new ImageIcon(imageToBeAdded);
			helpLabel = new JLabel(helpImage);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return helpLabel;
		
	}
	}

