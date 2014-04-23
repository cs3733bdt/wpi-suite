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
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;

import org.jdesktop.swingx.JXHyperlink;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.tree.GameTree;

/**
 * The default view for the GUI
 * @author Bobby Drop Tables
 *
 */
public class GameOverview extends JSplitPane {
	
	JLabel ppIntroLabel;
	Font bigFont;
	JTextArea ppExplanation;
	JLabel ppWhyLabel;
	JTextArea ppWhyExp;
	JLabel createGameLabel;
	JTextArea createGameExp;
	JLabel playPlanPokerLabel;
	JTextArea playPlanPokerExp;
	
	JXHyperlink videoTutorial;
	
	GameTree filterPanel = GameTree.getInstance();
	
	/**
	 * The constructor for the overview panel
	 */
	public GameOverview() {
		
		Container panel = new Container();
		SpringLayout layout = new SpringLayout();
		panel.setLayout(layout);
		JScrollPane scrollPanel = new JScrollPane(panel);
		//scrollPanel.setMinimumSize(new Dimension(600, 450));
		panel.setPreferredSize(new Dimension(610, 675)); //ADD HEIGHT AS 510
		
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
				+ "first number spoken aloud sets a precedent for subsequent estimates.");
		
		ppExplanation.setEditable(false);
		ppExplanation.setBackground(null);
		ppExplanation.setWrapStyleWord(true);
		ppExplanation.setLineWrap(true);

		// Creates a scroll pane to hold the planning poker description area
		JScrollPane expPane = new JScrollPane(ppExplanation);
		//expPane.setPreferredSize(new Dimension(600, 120));
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
				+ "at the same time.");
		
		ppWhyExp.setEditable(false);
		ppWhyExp.setBackground(null);
		ppWhyExp.setWrapStyleWord(true);
		ppWhyExp.setLineWrap(true);

		// Creates a scroll pane to hold the planning poker description area
		JScrollPane whyPane = new JScrollPane(ppWhyExp);
		//whyPane.setPreferredSize(new Dimension(600, 90));
		whyPane.setBorder(null);
		
		// Adds label
		createGameLabel = new JLabel("Creating a game");
		createGameLabel.setFont(bigFont);
		
		// Adds text area describing how to create a planning poker game
		createGameExp = new JTextArea();
		createGameExp.setText("In order to start creating a game, the Create Game button on the toolbar "
				+ "should be clicked. Every game should have a name, a description, a valid end date, and "
				+ "at least one requirement associated with it. A valid end date is any date and time in "
				+ "the future. A valid requirement should have a name and description associated with it. "
				+ "A requirement can be added by clicking the Add Requirement button and clicking Submit "
				+ "after filling out the name and description fields. After all the required fields are "
				+ "filled in, the game can be saved and be launched later by clicking the Save Game button. If "
				+ "Save Game is clicked, the game can be later accessed in the Pending Games folder which "
				+ "can be found on the tree to the left. If a game is launched by clicking the Start Game "
				+ "button, it can be accessed in the Active Games folder in the tree to the left.");
		

		createGameExp.setEditable(false);
		createGameExp.setBackground(null);
		createGameExp.setWrapStyleWord(true);
		createGameExp.setLineWrap(true);

		// Creates a scroll pane to hold the planning poker description area
		JScrollPane createPane = new JScrollPane(createGameExp);
		//createPane.setPreferredSize(new Dimension(600, 150));
		createPane.setBorder(null);
		
		//Creates a hyperlink for the video tutorial
		videoTutorial = new JXHyperlink();
		videoTutorial.setText("Here is a video tutorial showing how to create a new Planning Poker game.");
		videoTutorial.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://www.youtube.com/watch?v=okqEVeNqBhc"));
                } catch (URISyntaxException | IOException ex) {
                    //It looks like there's a problem
                }
            }
        });
		
		// Adds label
		playPlanPokerLabel = new JLabel("Playing Planning Poker");
		playPlanPokerLabel.setFont(bigFont);
				
		// Adds text area describing how to create a planning poker game
		playPlanPokerExp = new JTextArea();
		playPlanPokerExp.setText("In order to start a Planning Poker Session, select a game from the Active "
				+ "Games folder. The game will display the list of requirements to be voted on. Depending on "
				+ "the voting method used, the user will either select cards to create a total that they will "
				+ "submit as their estimate or manually type in an estimation for the requirement. Users can "
				+ "change their estimations before the game ends. The creator of the game can end the game before "
				+ "the set end date and add a reson for ending the game early. When a game ends, participants can view "
				+ "the game's statistics after opening the game from the Game History folder. The max, min, mean "
				+ "median, standard deviation, and number of votes of each requirement will be displayed. The final "
				+ "estimate can then be decided");
				

		playPlanPokerExp.setEditable(false);
		playPlanPokerExp.setBackground(null);
		playPlanPokerExp.setWrapStyleWord(true);
		playPlanPokerExp.setLineWrap(true);

		// Creates a scroll pane to hold the planning poker description area
		JScrollPane playPane = new JScrollPane(playPlanPokerExp);
		//createPane.setPreferredSize(new Dimension(600, 150));
		playPane.setBorder(null);
		
		// Adds components to the panel
		panel.add(ppIntroLabel);
		//panel.add(expPane);
		panel.add(ppExplanation);
		panel.add(ppWhyLabel);
		//panel.add(whyPane);
		panel.add(ppWhyExp);
		panel.add(createGameLabel);
		//panel.add(createPane);
		panel.add(createGameExp);
		//panel.add(playPane);
		panel.add(playPlanPokerLabel);
		panel.add(playPlanPokerExp);
		panel.add(videoTutorial);
		
		// Adjusts constraints on components
		layout.putConstraint(SpringLayout.NORTH, ppIntroLabel, 10, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST, ppIntroLabel, 5, SpringLayout.WEST, panel);
		
		layout.putConstraint(SpringLayout.NORTH, ppExplanation, 10, SpringLayout.SOUTH, ppIntroLabel);
		layout.putConstraint(SpringLayout.WEST, ppExplanation, 5, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.EAST, ppExplanation, 600, SpringLayout.WEST, panel);
		
		layout.putConstraint(SpringLayout.NORTH, ppWhyLabel, 28, SpringLayout.SOUTH, ppExplanation); //CHANGE BACK TO 40
		layout.putConstraint(SpringLayout.WEST, ppWhyLabel, 5, SpringLayout.WEST, panel);
		
		layout.putConstraint(SpringLayout.NORTH, ppWhyExp, 10, SpringLayout.SOUTH, ppWhyLabel);
		layout.putConstraint(SpringLayout.WEST, ppWhyExp, 5, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.EAST, ppWhyExp, 600, SpringLayout.WEST, panel);
		
		layout.putConstraint(SpringLayout.NORTH, createGameLabel, 28, SpringLayout.SOUTH, ppWhyExp); //CHANGE BACK TO 40
		layout.putConstraint(SpringLayout.WEST, createGameLabel, 5, SpringLayout.WEST, panel);
		
		layout.putConstraint(SpringLayout.NORTH, videoTutorial, 8, SpringLayout.SOUTH, createGameLabel);
		layout.putConstraint(SpringLayout.WEST, videoTutorial, 5, SpringLayout.WEST, panel);
		
		layout.putConstraint(SpringLayout.NORTH, createGameExp, 10, SpringLayout.SOUTH, createGameLabel);
		layout.putConstraint(SpringLayout.NORTH, createGameExp, 8, SpringLayout.SOUTH, videoTutorial);
		layout.putConstraint(SpringLayout.WEST, createGameExp, 5, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.EAST, createGameExp, 600, SpringLayout.WEST, panel);
		
		layout.putConstraint(SpringLayout.NORTH, playPlanPokerLabel, 28, SpringLayout.SOUTH, createGameExp);
		layout.putConstraint(SpringLayout.WEST, playPlanPokerLabel, 5, SpringLayout.WEST, panel);
		
		layout.putConstraint(SpringLayout.NORTH, playPlanPokerExp, 10, SpringLayout.SOUTH, playPlanPokerLabel);
		layout.putConstraint(SpringLayout.WEST, playPlanPokerExp, 5, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.EAST, playPlanPokerExp, 600, SpringLayout.WEST, panel);
		
		setRightComponent(scrollPanel);
		setLeftComponent(filterPanel);
		setDividerLocation(200);
		
	}
}
	
	
	
	