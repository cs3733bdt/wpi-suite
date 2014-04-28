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
import javax.swing.SpringLayout;

/**
 * Designs the create game help menu
 * @author Kevin
 *
 */
public class CreateGameHelp extends JScrollPane implements IHelpPanel {
	JLabel headingLabel;
	JTextArea createGameExplanation;
	JLabel gameInformation;
	JTextArea gameInformationExplanation;
	JLabel noRequirements;
	JTextArea noRequirementsExplanation;
	JLabel addRequirements;
	JTextArea addRequirementsExplanation;
	JLabel importRequirements;
	JTextArea importRequirementsExplanation;
	JLabel addedRequirements;
	JTextArea addedRequirementsExplanation;
	JLabel updateRequirements;
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
		view.setPreferredSize(new Dimension(610, 2500));
		
		//Add the heading label to the Panel
		headingLabel = new JLabel("Create Game Help");
		headingLabel.setFont(makeFont(8));
		
		//Add explanation for creating games
		createGameExplanation = new JTextArea();
		createGameExplanation.setText("The Create Game menu allows you to create a Planning "
				+ "Poker game which you can save for further editing or start immediately. "
				+ "Games that have been saved can be found in Pending Games in the Game Overview"
				+ " tab and games that have been started can be found in Active GAmes in the Game "
				+ "Overview tab.");
		
		createGameExplanation.setEditable(false);
		createGameExplanation.setBackground(null);
		createGameExplanation.setWrapStyleWord(true);
		createGameExplanation.setLineWrap(true);
		
		//Add game information image
		gameInformation = addImage("create_game_help.png");
		
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
		
		//Add no requirements image
		noRequirements = addImage("no_requirements.png");
		
		//Add explanation to the second image, no requirements
		noRequirementsExplanation = new JTextArea();
		noRequirementsExplanation.setText("When a game is first created, there are no requirements "
				+ "associated with it. You can then manually create a new requirement or import a "
				+ "requirement from the Requirement Manager.");
		
		noRequirementsExplanation.setEditable(false);
		noRequirementsExplanation.setBackground(null);
		noRequirementsExplanation.setWrapStyleWord(true);
		noRequirementsExplanation.setLineWrap(true);
		
		//Add create requirement image
		addRequirements = addImage("create_requirement_help.png");
		
		//Add explanation to the third image, adding a requirement
		addRequirementsExplanation = new JTextArea();
		addRequirementsExplanation.setText("This is the panel for manually creating and adding a "
				+ "requirement. Every requirement must have a name or a description before it can "
				+ "be added to the game. When both those fields are completed, the requirement can "
				+ "then be added by hitting the create button. Hitting Cancel will return you to the "
				+ "list of Current Requirements in the game. The Update button is functional when adding a "
				+ "requirement.");
		
		addRequirementsExplanation.setEditable(false);
		addRequirementsExplanation.setBackground(null);
		addRequirementsExplanation.setWrapStyleWord(true);
		addRequirementsExplanation.setLineWrap(true);
		
		//Add import requirement image
		importRequirements = addImage("import_requirements.png");
		
		//Add explanation to fourth image, importing a requirement
		importRequirementsExplanation = new JTextArea();
		importRequirementsExplanation.setText("You can import requirements from the requirements "
				+ "manger into the Planning Poker game. You can select one or more requirements by "
				+ "using Ctrl + left mouse click and add all of the selected requirements to the "
				+ "game all at once by clicking the Import button. Hitting Cancel will bring you "
				+ "back to the list of Current Requirements in the game. You can import more requirements "
				+ "from the requirements manager afterwards, but if there are no more unique "
				+ "requirements, then an error message will display saying there are no requirements "
				+ "to import and you will be forced to either exit the game or hit the cancel button.");
		
		importRequirementsExplanation.setEditable(false);
		importRequirementsExplanation.setBackground(null);
		importRequirementsExplanation.setWrapStyleWord(true);
		importRequirementsExplanation.setLineWrap(true);
		
		//Add added requirements image
		addedRequirements = addImage("added_requirements.png");
		
		//Add explanation to fifth image, added requirements
		addedRequirementsExplanation = new JTextArea();
		addedRequirementsExplanation.setText("After adding and/or importing a requirement, the "
				+ "requirements show up in a list of Current Requirements. Selecting a requirement will"
				+ "allow you to edit the requirement or remove it from the list. Only manually created "
				+ "requirements can be edited.");
		
		addedRequirementsExplanation.setEditable(false);
		addedRequirementsExplanation.setBackground(null);
		addedRequirementsExplanation.setWrapStyleWord(true);
		addedRequirementsExplanation.setLineWrap(true);
		
		//Add edit requirements image
		updateRequirements = addImage("updated_requirement.png");
		
		//Add explanation to sixth image, editing requirements
		updateRequirementsExplanation = new JTextArea();
		updateRequirementsExplanation.setText("Manually created requirements can be edited "
				+ "after selecting it from the Current Requirements list and clicking the edit "
				+ "button. You can change both the name and description of the requirement and to "
				+ "save the changes, click the Update button. The Create button is disabled in this "
				+ "mode. The Update button can only be used if there are changes to the requirement. "
				+ "If you want to discard the changes you made, hitting the Cancel button will bring "
				+ "you back to the Current Requirements list without any of your changes saved.");
		
		updateRequirementsExplanation.setEditable(false);
		updateRequirementsExplanation.setBackground(null);
		updateRequirementsExplanation.setWrapStyleWord(true);
		updateRequirementsExplanation.setLineWrap(true);
		
		view.add(headingLabel);
		//view.add(createGameExplanation);
		view.add(gameInformation);
		view.add(gameInformationExplanation);
		view.add(noRequirements);
		view.add(noRequirementsExplanation);
		view.add(addRequirements);
		view.add(addRequirementsExplanation);
		view.add(importRequirements);
		view.add(importRequirementsExplanation);
		view.add(addedRequirements);
		view.add(addedRequirementsExplanation);
		view.add(updateRequirements);
		view.add(updateRequirementsExplanation);
		
		/**
		 * Constraints for the overall panel layout
		 */
		layout.putConstraint(SpringLayout.NORTH, headingLabel, 5, SpringLayout.NORTH, view);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, headingLabel, 0, SpringLayout.HORIZONTAL_CENTER, view);
		
		//layout.putConstraint(SpringLayout.NORTH, createGameExplanation, 5, SpringLayout.SOUTH, headingLabel);
		//layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, createGameExplanation, 0, SpringLayout.HORIZONTAL_CENTER, view);
		
		layout.putConstraint(SpringLayout.NORTH, gameInformation, 5, SpringLayout.SOUTH, headingLabel); // will change to createGameExplanation when working
		layout.putConstraint(SpringLayout.WEST, gameInformation, 5, SpringLayout.WEST, view);
		
		layout.putConstraint(SpringLayout.NORTH, gameInformationExplanation, 5, SpringLayout.SOUTH, headingLabel); // will change to createGameExplanation when working
		layout.putConstraint(SpringLayout.EAST, gameInformationExplanation, 0, SpringLayout.EAST, view);
		layout.putConstraint(SpringLayout.WEST, gameInformationExplanation, 20, SpringLayout.EAST, gameInformation);
		
		layout.putConstraint(SpringLayout.NORTH, noRequirements, 20, SpringLayout.SOUTH, gameInformation);
		layout.putConstraint(SpringLayout.WEST, noRequirements, 5, SpringLayout.WEST, view);
		
		layout.putConstraint(SpringLayout.NORTH, noRequirementsExplanation, 20, SpringLayout.SOUTH, gameInformation);
		layout.putConstraint(SpringLayout.EAST, noRequirementsExplanation, 0, SpringLayout.EAST, view);
		layout.putConstraint(SpringLayout.WEST, noRequirementsExplanation, 20, SpringLayout.EAST, noRequirements);
		
		layout.putConstraint(SpringLayout.NORTH, addRequirements, 20, SpringLayout.SOUTH, noRequirements);
		layout.putConstraint(SpringLayout.WEST, addRequirements, 5, SpringLayout.WEST, view);
		
		layout.putConstraint(SpringLayout.NORTH, addRequirementsExplanation, 20, SpringLayout.SOUTH, noRequirements);
		layout.putConstraint(SpringLayout.EAST, addRequirementsExplanation, 0, SpringLayout.EAST, view);
		layout.putConstraint(SpringLayout.WEST, addRequirementsExplanation, 20, SpringLayout.EAST, addRequirements);
		
		layout.putConstraint(SpringLayout.NORTH, importRequirements, 20, SpringLayout.SOUTH, addRequirements);
		layout.putConstraint(SpringLayout.WEST, importRequirements, 5, SpringLayout.WEST, view);
		
		layout.putConstraint(SpringLayout.NORTH, importRequirementsExplanation, 20, SpringLayout.SOUTH, addRequirements);
		layout.putConstraint(SpringLayout.EAST, importRequirementsExplanation, 0, SpringLayout.EAST, view);
		layout.putConstraint(SpringLayout.WEST, importRequirementsExplanation, 20, SpringLayout.EAST, importRequirements);
		
		layout.putConstraint(SpringLayout.NORTH, addedRequirements, 20, SpringLayout.SOUTH, importRequirements);
		layout.putConstraint(SpringLayout.WEST, addedRequirements, 5, SpringLayout.WEST, view);
		
		layout.putConstraint(SpringLayout.NORTH, addedRequirementsExplanation, 20, SpringLayout.SOUTH, importRequirements);
		layout.putConstraint(SpringLayout.EAST, addedRequirementsExplanation, 0, SpringLayout.EAST, view);
		layout.putConstraint(SpringLayout.WEST, addedRequirementsExplanation, 20, SpringLayout.EAST, addedRequirements);
		
		layout.putConstraint(SpringLayout.NORTH, updateRequirements, 20, SpringLayout.SOUTH, addedRequirements);
		layout.putConstraint(SpringLayout.WEST, updateRequirements, 5, SpringLayout.WEST, view);
		
		layout.putConstraint(SpringLayout.NORTH, updateRequirementsExplanation, 20, SpringLayout.SOUTH, addedRequirements);
		layout.putConstraint(SpringLayout.EAST, updateRequirementsExplanation, 0, SpringLayout.EAST, view);
		layout.putConstraint(SpringLayout.WEST, updateRequirementsExplanation, 20, SpringLayout.EAST, updateRequirements);
		
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
