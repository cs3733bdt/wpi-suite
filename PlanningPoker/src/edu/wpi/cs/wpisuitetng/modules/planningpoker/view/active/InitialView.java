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

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;

/**
 * The default view for the GUI
 * @author Bobby Drop Tables
 *
 */
public class InitialView extends JScrollPane {
	
	JLabel ppIntroLabel;
	Font bigFont;
	JTextArea ppExplanation;
	JLabel ppWhyLabel;
	JTextArea ppWhyExp;
	JLabel createGameLabel;
	JTextArea createGameExp;
	
	/**
	 * The constructor for the overview panel
	 */
	public InitialView() {
		
		Container panel = new Container();
		SpringLayout layout = new SpringLayout();
		panel.setLayout(layout);
		panel.setPreferredSize(new Dimension(500, 500));
		
		// Adds introduction label
		ppIntroLabel = new JLabel("What is Planning Poker?");
		
		// Sets a large font to be used for headings
		bigFont = new Font(ppIntroLabel.getFont().getFontName(), ppIntroLabel.getFont().getStyle(),
				ppIntroLabel.getFont().getSize() + 8);
		// Sets the font of introduction label
		ppIntroLabel.setFont(bigFont);
		
		// Adds text area describing planning poker
		ppExplanation = new JTextArea();
		ppExplanation.setText("Planning poker is a "
				+ "consensus-based technique for estimating, mostly used to "
				+ "estimate effort or relative size of development goals in "
				+ "software development. In planning poker, members of the group "
				+ "make estimates by playing numbered cards face-down to the table, "
				+ "instead of speaking them aloud. The cards are revealed, and the "
				+ "estimates are then discussed. By hiding the figures in this way, "
				+ "the group can avoid the cognitive bias of anchoring, where the "
				+ "first number spoken aloud sets a precedent for subsequent estimates. [TAKEN FROM"
				+ " WIKIPEDIA]");
		
		ppExplanation.setEditable(false);
		ppExplanation.setBackground(null);
		ppExplanation.setWrapStyleWord(true);
		ppExplanation.setLineWrap(true);

		// Creates a scroll pane to hold the planning poker description area
		JScrollPane expPane = new JScrollPane(ppExplanation);
		expPane.setPreferredSize(new Dimension(600, 120));
		expPane.setBorder(null);
		
		// Adds label
		ppWhyLabel = new JLabel("Why use Planning Poker?");
		ppWhyLabel.setFont(bigFont);
		
		// Adds text area describing the main reasons of using planning poker
		ppWhyExp = new JTextArea();
		ppWhyExp.setText("The reason to use Planning poker is to avoid the influence of the "
				+ "other participants. If a number is spoken, it can sound like a suggestion "
				+ "and influence the other participants' sizing. Planning poker should force "
				+ "people to think independently and propose their numbers simultaneously. "
				+ "This is accomplished by requiring that all participants show their card "
				+ "at the same time. [TAKEN FROM WIKIPEDIA]");
		
		ppWhyExp.setEditable(false);
		ppWhyExp.setBackground(null);
		ppWhyExp.setWrapStyleWord(true);
		ppWhyExp.setLineWrap(true);

		// Creates a scroll pane to hold the planning poker description area
		JScrollPane whyPane = new JScrollPane(ppWhyExp);
		whyPane.setPreferredSize(new Dimension(600, 110));
		whyPane.setBorder(null);
		
		// Adds label
		createGameLabel = new JLabel("Creating a game");
		createGameLabel.setFont(bigFont);
		
		// Adds text area describing how to create a planning poker game
		createGameExp = new JTextArea();
		createGameExp.setText("In order to start creating a game, the Create Game button on the toolbar"
				+ "should be clicked. Every game should have a name, a description, a valid end date, and "
				+ "at least one requirement associated with it. A valid end date is any date and time in"
				+ "the future. A valid requirement should have a name and description associated with it."
				+ "A requirement can be added by clicking the Add Requirement button and clicking Submit"
				+ "after filling out the name and description fields. After all the required fields are"
				+ "filled in, the game can be saved and be launched later by clicking the Save Game button. If"
				+ "Save Game is clicked, the game can be later accessed in the Pending Games folder which"
				+ "can be found on the tree to the left. If a game is launched by clicking th Launch Game"
				+ "button, it can be accessed in the Active Games folder in the tree to the left.");
		

		createGameExp.setEditable(false);
		createGameExp.setBackground(null);
		createGameExp.setWrapStyleWord(true);
		createGameExp.setLineWrap(true);

		// Creates a scroll pane to hold the planning poker description area
		JScrollPane createPane = new JScrollPane(createGameExp);
		createPane.setPreferredSize(new Dimension(600, 150));
		createPane.setBorder(null);
		
		// Adds components to the panel
		panel.add(ppIntroLabel);
		panel.add(expPane);
		panel.add(ppWhyLabel);
		panel.add(whyPane);
		panel.add(createGameLabel);
		panel.add(createPane);
		
		// Adjusts constraints on components
		layout.putConstraint(SpringLayout.NORTH, ppIntroLabel, 10, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST, ppIntroLabel, 5, SpringLayout.WEST, panel);
		
		layout.putConstraint(SpringLayout.NORTH, expPane, 10, SpringLayout.SOUTH, ppIntroLabel);
		layout.putConstraint(SpringLayout.WEST, expPane, 5, SpringLayout.WEST, panel);
		
		layout.putConstraint(SpringLayout.NORTH, ppWhyLabel, 10, SpringLayout.SOUTH, expPane);
		layout.putConstraint(SpringLayout.WEST, ppWhyLabel, 5, SpringLayout.WEST, panel);
		
		layout.putConstraint(SpringLayout.NORTH, whyPane, 10, SpringLayout.SOUTH, ppWhyLabel);
		layout.putConstraint(SpringLayout.WEST, whyPane, 5, SpringLayout.WEST, panel);

		
		layout.putConstraint(SpringLayout.NORTH, createGameLabel, 10, SpringLayout.SOUTH, whyPane);
		layout.putConstraint(SpringLayout.WEST, createGameLabel, 5, SpringLayout.WEST, panel);
		
		layout.putConstraint(SpringLayout.NORTH, createPane, 10, SpringLayout.SOUTH, createGameLabel);
		layout.putConstraint(SpringLayout.WEST, createPane, 5, SpringLayout.WEST, panel);
		
		this.getViewport().add(panel);
	}
	/*
	public static void main(String args[]){
		JFrame frame = new JFrame("Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   
        //Set up the content pane.
        frame.add(new InitialView());
        frame.setMinimumSize(new Dimension(1000, 600));

        //Display the window.
        frame.pack();
        frame.setVisible(true);
	}
	*/
}
	
	
	
	