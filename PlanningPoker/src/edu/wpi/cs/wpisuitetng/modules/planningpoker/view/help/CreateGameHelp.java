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

/**
 * Designs the create game help menu
 * @author Kevin
 *
 */
public class CreateGameHelp extends JScrollPane implements IHelpPanel {
	JLabel headingLabel;
	JLabel gameInformationHeading;
	JLabel gameInformationpic;
	JTextArea gameInformationExplanation;
	JLabel noRequirementsHeading;
	JLabel noRequirementspic;
	JTextArea noRequirementsExplanation;
	JLabel addRequirementsHeading;
	JLabel addRequirementspic;
	JTextArea addRequirementsExplanation;
	JLabel importRequirementsHeading;
	JLabel importRequirementspic;
	JTextArea importRequirementsExplanation;
	JLabel addedRequirementsHeading;
	JLabel addedRequirementspic;
	JTextArea addedRequirementsExplanation;
	JLabel updateRequirementsHeading; 
	JLabel updateRequirementspic;
	JTextArea updateRequirementsExplanation;
	
	public CreateGameHelp() {
		build();
	}
	
	private void build() {
		/**
		 *  Set up initial container with spring layout */
		Container view = new Container();
		SpringLayout layout = new SpringLayout();
		view.setLayout(layout);
		view.setPreferredSize(new Dimension(1100, 1780));
		setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		//Add the heading label to the Panel
		headingLabel = new JLabel("Create and Edit Game Help");
		headingLabel.setFont(makeFont(8));

		//Add heading label for gameInformationHeading	
		gameInformationHeading = new JLabel("Initializing a Game");
		gameInformationHeading.setFont(makeFont(5)); 
		
		//Add game information image
		gameInformationpic = addImage("create_game_help.png");
		
		//Add explanation to first image, the game information
		gameInformationExplanation = new JTextArea();
		gameInformationExplanation.setText("When creating a new game, there must be a name and "
				+ "description for the game, an end date and time set after the current date and "
				+ "time, at least one requirement, and whether or not the votes will be by text "
				+ "entry or by selecting cards. If votes will be by cards, then you can choose "
				+ "to use the default card deck or your own custom deck. If the required fields "
				+ "are incomplete or invalid, an error message will be displayed showing what "
				+ "field is incomplete and you cannot start the game, you can only save it for "
				+ "future editing.");
		
		gameInformationExplanation.setEditable(false);
		gameInformationExplanation.setBackground(null);
		gameInformationExplanation.setWrapStyleWord(true);
		gameInformationExplanation.setLineWrap(true);
		
		//Add heading label for no requirements in the table	
		noRequirementsHeading = new JLabel("Initial View of the Requirements Table");
		noRequirementsHeading.setFont(makeFont(5)); 
		
		//Add no requirements image
		noRequirementspic = addImage("no_requirements.png");
		
		//Add explanation to the second image, no requirements
		noRequirementsExplanation = new JTextArea();
		noRequirementsExplanation.setText("When a game is first created, there are no requirements "
				+ "associated with it. You can then manually create a new requirement or import a "
				+ "requirement from the Requirement Manager.");
		
		noRequirementsExplanation.setEditable(false);
		noRequirementsExplanation.setBackground(null);
		noRequirementsExplanation.setWrapStyleWord(true);
		noRequirementsExplanation.setLineWrap(true);
		
		//Add heading label for adding a requirement 	
		addRequirementsHeading = new JLabel("Adding a Requirement to a Game");
		addRequirementsHeading.setFont(makeFont(5));
				
		//Add create requirement image
		addRequirementspic = addImage("create_requirement_help.png");
		
		//Add explanation to the third image, adding a requirement
		addRequirementsExplanation = new JTextArea();
		addRequirementsExplanation.setText("This is the panel for manually creating and adding a "
				+ "requirement. Every requirement must have a name or a description before it can "
				+ "be added to the game. When both those fields are completed, the requirement can "
				+ "then be added by hitting the create button. Hitting Cancel will return you to the "
				+ "list of Current Requirements in the game. The Update button is not functional when "
				+ "initially creating a requirement.");
		
		addRequirementsExplanation.setEditable(false);
		addRequirementsExplanation.setBackground(null);
		addRequirementsExplanation.setWrapStyleWord(true);
		addRequirementsExplanation.setLineWrap(true);
		
		//Add heading label for importing requirements 	
		importRequirementsHeading = new JLabel("Importing a Requirement to the Game");
		importRequirementsHeading.setFont(makeFont(5));

		//Add import requirement image
		importRequirementspic = addImage("import_requirements.png");
		
		//Add explanation to fourth image, importing a requirement
		importRequirementsExplanation = new JTextArea();
		importRequirementsExplanation.setText("You can import requirements from the requirements "
				+ "manager into the Planning Poker game. Hitting Cancel will bring you "
				+ "back to the list of Current Requirements in the game.  Ctrl + Click, Shift + Arrow Keys, "
				+ "and clicking and dragging will all allow you to select multiple requirements. "
				+ "to import and you will be forced to either exit the game or hit the cancel button.");
		
		importRequirementsExplanation.setEditable(false);
		importRequirementsExplanation.setBackground(null);
		importRequirementsExplanation.setWrapStyleWord(true);
		importRequirementsExplanation.setLineWrap(true);
		
		//Add heading label for table with requirements added 	
		addedRequirementsHeading = new JLabel("Requirements Table with Added Requirements");
		addedRequirementsHeading.setFont(makeFont(5));

		//Add added requirements image
		addedRequirementspic = addImage("added_requirements.png");
		
		//Add explanation to fifth image, added requirements
		addedRequirementsExplanation = new JTextArea();
		addedRequirementsExplanation.setText("After adding any requirements they "
				+ "will show up in the list of Current Requirements. Selecting a requirement will "
				+ "allow you to edit the requirement or remove it from the list. Only manually created "
				+ "requirements can be edited.");
		
		addedRequirementsExplanation.setEditable(false);
		addedRequirementsExplanation.setBackground(null);
		addedRequirementsExplanation.setWrapStyleWord(true);
		addedRequirementsExplanation.setLineWrap(true);
		
		//Add heading label for table with requirements added 	
		updateRequirementsHeading = new JLabel("Updating a Requirement");
		updateRequirementsHeading.setFont(makeFont(5));

		//Add edit requirements image
		updateRequirementspic = addImage("updated_requirement.png");
		
		//Add explanation to sixth image, editing requirements
		updateRequirementsExplanation = new JTextArea();
		updateRequirementsExplanation.setText("Manually created requirements can be edited "
				+ "after selecting it from the Current Requirements list and clicking the edit "
				+ "button. You can change both the name and description of the requirement. "
				+ "To save your changes, click the Update button. "
				+ "If you want to discard the changes you made, hitting the Cancel button will bring "
				+ "you back to the Current Requirements list without any of your changes saved.");
		
		updateRequirementsExplanation.setEditable(false);
		updateRequirementsExplanation.setBackground(null);
		updateRequirementsExplanation.setWrapStyleWord(true);
		updateRequirementsExplanation.setLineWrap(true);
		
		view.add(headingLabel);
		//view.add(createGameExplanation);
		view.add(gameInformationHeading);
		view.add(gameInformationpic);
		view.add(gameInformationExplanation);
		view.add(noRequirementsHeading);
		view.add(noRequirementspic);
		view.add(noRequirementsExplanation);
		view.add(addRequirementsHeading);
		view.add(addRequirementspic);
		view.add(addRequirementsExplanation);
		view.add(importRequirementsHeading);
		view.add(importRequirementspic);
		view.add(importRequirementsExplanation);
		view.add(addedRequirementsHeading);
		view.add(addedRequirementspic);
		view.add(addedRequirementsExplanation);
		view.add(updateRequirementsHeading);
		view.add(updateRequirementspic);
		view.add(updateRequirementsExplanation);
		
		/**
		 * Constraints for the overall panel layout
		 */
		layout.putConstraint(SpringLayout.NORTH, headingLabel, 5, SpringLayout.NORTH, view);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, headingLabel, 0, SpringLayout.HORIZONTAL_CENTER, view);
		
		//Game Information 
		layout.putConstraint(SpringLayout.NORTH, gameInformationHeading, 5, SpringLayout.SOUTH, headingLabel); // will change to createGameExplanation when working
		layout.putConstraint(SpringLayout.EAST, gameInformationHeading, 0, SpringLayout.EAST, view);
		layout.putConstraint(SpringLayout.WEST, gameInformationHeading, 20, SpringLayout.WEST, view);
		
		layout.putConstraint(SpringLayout.NORTH, gameInformationpic, 20, SpringLayout.SOUTH, gameInformationHeading); // will change to createGameExplanation when working
		layout.putConstraint(SpringLayout.WEST, gameInformationpic, 5, SpringLayout.WEST, view);
		
		layout.putConstraint(SpringLayout.NORTH, gameInformationExplanation, 20, SpringLayout.SOUTH, gameInformationHeading); // will change to createGameExplanation when working
		layout.putConstraint(SpringLayout.EAST, gameInformationExplanation, 0, SpringLayout.EAST, view);
		layout.putConstraint(SpringLayout.WEST, gameInformationExplanation, 20, SpringLayout.EAST, gameInformationpic);
		
		//No Requirements
		layout.putConstraint(SpringLayout.NORTH, noRequirementsHeading, 20, SpringLayout.SOUTH, gameInformationpic);
		layout.putConstraint(SpringLayout.EAST, noRequirementsHeading, 0, SpringLayout.EAST, view);
		layout.putConstraint(SpringLayout.WEST, noRequirementsHeading, 20, SpringLayout.WEST, view);
		
		layout.putConstraint(SpringLayout.NORTH, noRequirementspic, 20, SpringLayout.SOUTH, noRequirementsHeading);
		layout.putConstraint(SpringLayout.WEST, noRequirementspic, 5, SpringLayout.WEST, view);
		
		layout.putConstraint(SpringLayout.NORTH, noRequirementsExplanation, 20, SpringLayout.SOUTH, noRequirementsHeading);
		layout.putConstraint(SpringLayout.EAST, noRequirementsExplanation, 0, SpringLayout.EAST, view);
		layout.putConstraint(SpringLayout.WEST, noRequirementsExplanation, 20, SpringLayout.EAST, noRequirementspic);
		
		//Add Requirements
		layout.putConstraint(SpringLayout.NORTH, addRequirementsHeading, 20, SpringLayout.SOUTH, noRequirementspic);
		layout.putConstraint(SpringLayout.EAST, addRequirementsHeading, 0, SpringLayout.EAST, view);
		layout.putConstraint(SpringLayout.WEST, addRequirementsHeading, 20, SpringLayout.WEST, view);
		
		layout.putConstraint(SpringLayout.NORTH, addRequirementspic, 20, SpringLayout.SOUTH, addRequirementsHeading);
		layout.putConstraint(SpringLayout.WEST, addRequirementspic, 5, SpringLayout.WEST, view);
		
		layout.putConstraint(SpringLayout.NORTH, addRequirementsExplanation, 20, SpringLayout.SOUTH, addRequirementsHeading);
		layout.putConstraint(SpringLayout.EAST, addRequirementsExplanation, 0, SpringLayout.EAST, view);
		layout.putConstraint(SpringLayout.WEST, addRequirementsExplanation, 20, SpringLayout.EAST, addRequirementspic);
		
		//Import Requirements 
		layout.putConstraint(SpringLayout.NORTH, importRequirementsHeading, 20, SpringLayout.SOUTH, addRequirementspic);
		layout.putConstraint(SpringLayout.EAST, importRequirementsHeading, 0, SpringLayout.EAST, view);
		layout.putConstraint(SpringLayout.WEST, importRequirementsHeading, 20, SpringLayout.WEST, view);
		
		layout.putConstraint(SpringLayout.NORTH, importRequirementspic, 20, SpringLayout.SOUTH, importRequirementsHeading);
		layout.putConstraint(SpringLayout.WEST, importRequirementspic, 5, SpringLayout.WEST, view);
		
		layout.putConstraint(SpringLayout.NORTH, importRequirementsExplanation, 20, SpringLayout.SOUTH, importRequirementsHeading);
		layout.putConstraint(SpringLayout.EAST, importRequirementsExplanation, 0, SpringLayout.EAST, view);
		layout.putConstraint(SpringLayout.WEST, importRequirementsExplanation, 20, SpringLayout.EAST, importRequirementspic);
		
		//Added Requirements 
		layout.putConstraint(SpringLayout.NORTH, addedRequirementsHeading, 20, SpringLayout.SOUTH, importRequirementspic);
		layout.putConstraint(SpringLayout.EAST, addedRequirementsHeading, 0, SpringLayout.EAST, view);
		layout.putConstraint(SpringLayout.WEST, addedRequirementsHeading, 20, SpringLayout.WEST, view);
		
		layout.putConstraint(SpringLayout.NORTH, addedRequirementspic, 20, SpringLayout.SOUTH, addedRequirementsHeading);
		layout.putConstraint(SpringLayout.WEST, addedRequirementspic, 5, SpringLayout.WEST, view);
		
		layout.putConstraint(SpringLayout.NORTH, addedRequirementsExplanation, 20, SpringLayout.SOUTH, addedRequirementsHeading);
		layout.putConstraint(SpringLayout.EAST, addedRequirementsExplanation, 0, SpringLayout.EAST, view);
		layout.putConstraint(SpringLayout.WEST, addedRequirementsExplanation, 20, SpringLayout.EAST, addedRequirementspic);
		
		//Update Requirements
		layout.putConstraint(SpringLayout.NORTH, updateRequirementsHeading, 20, SpringLayout.SOUTH, addedRequirementspic);
		layout.putConstraint(SpringLayout.EAST, updateRequirementsHeading, 0, SpringLayout.EAST, view);
		layout.putConstraint(SpringLayout.WEST, updateRequirementsHeading, 20, SpringLayout.WEST, view);
		
		layout.putConstraint(SpringLayout.NORTH, updateRequirementspic, 20, SpringLayout.SOUTH, updateRequirementsHeading);
		layout.putConstraint(SpringLayout.WEST, updateRequirementspic, 5, SpringLayout.WEST, view);
		//layout.putConstraint(SpringLayout.SOUTH, updateRequirementspic, 10, SpringLayout.SOUTH, view);

		layout.putConstraint(SpringLayout.NORTH, updateRequirementsExplanation, 20, SpringLayout.SOUTH, updateRequirementsHeading);
		layout.putConstraint(SpringLayout.EAST, updateRequirementsExplanation, 0, SpringLayout.EAST, view);
		layout.putConstraint(SpringLayout.WEST, updateRequirementsExplanation, 20, SpringLayout.EAST, updateRequirementspic);
		
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
		return 3;
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
