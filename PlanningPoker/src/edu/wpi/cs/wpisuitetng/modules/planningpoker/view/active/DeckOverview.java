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
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;

import org.jdesktop.swingx.JXHyperlink;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.tree.DeckTree;

/**
 * The default view for the GUI
 * @author Bobby Drop Tables
 *
 */
public class DeckOverview extends JSplitPane {
	private static final Logger logger = Logger.getLogger(DeckOverview.class.getName());
	JLabel ppIntroLabel;
	Font bigFont;
	JTextArea ppExplanation;
	JLabel ppWhyLabel;
	JTextArea ppWhyExp;
	JLabel createDeckLabel;
	JTextArea createDeckExp;
	
	JXHyperlink videoTutorial;
	
	DeckTree filterPanel = DeckTree.getInstance();
	
	/**
	 * The constructor for the overview panel
	 */
	public DeckOverview() {
		
		Container panel = new Container();
		SpringLayout layout = new SpringLayout();
		panel.setLayout(layout);
		JScrollPane scrollPanel = new JScrollPane(panel);
		panel.setPreferredSize(new Dimension(610, 510));
		
		
		// Adds introduction label
		ppIntroLabel = new JLabel("What is a Deck?");
		
		// Sets a large font to be used for headings
		bigFont = new Font(ppIntroLabel.getFont().getFontName(), ppIntroLabel.getFont().getStyle(),
				ppIntroLabel.getFont().getSize() + 8);
		// Sets the font of introduction label
		ppIntroLabel.setFont(bigFont);
		
		// Adds text area describing planning poker
		ppExplanation = new JTextArea();
		ppExplanation.setText("A Deck in Planning Poker is a set of cards that is "
				+ "used when voting on requirements. On each card is a number "
				+ "that when selected, is added to a total. The user can either select "
				+ "a single card or "
				+ "add cards to a total and then submit that total as their estimation"
				+ "for the requirement.");
		
		ppExplanation.setEditable(false);
		ppExplanation.setBackground(null);
		ppExplanation.setWrapStyleWord(true);
		ppExplanation.setLineWrap(true);

		// Creates a scroll pane to hold the planning poker description area
		JScrollPane expPane = new JScrollPane(ppExplanation);
		expPane.setPreferredSize(new Dimension(600, 120));
		expPane.setBorder(null);
		
		// Adds label
		ppWhyLabel = new JLabel("Why use a custom deck?");
		ppWhyLabel.setFont(bigFont);
		
		// Adds text area describing the main reasons of using planning poker
		ppWhyExp = new JTextArea();
		ppWhyExp.setText("By creating a custom deck, you can have any "
				+ "number of cards for the estimation. You can also assign "
				+ "any integer value to each card, giving voters more variety "
				+ "when voting on requirements. You can also choose the color "
				+ "of the back of the cards for more variety.");
		
		ppWhyExp.setEditable(false);
		ppWhyExp.setBackground(null);
		ppWhyExp.setWrapStyleWord(true);
		ppWhyExp.setLineWrap(true);

		// Creates a scroll pane to hold the planning poker description area
		JScrollPane whyPane = new JScrollPane(ppWhyExp);
		whyPane.setPreferredSize(new Dimension(600, 90));
		whyPane.setBorder(null);
		
		// Adds label
		createDeckLabel = new JLabel("Creating a deck");
		createDeckLabel.setFont(bigFont);
		
		// Adds text area describing how to create a planning poker deck
		createDeckExp = new JTextArea();
		createDeckExp.setText("To create a custom deck, click the Create Deck button in the upper toolbar. "
				+ "Decks must have a name, though a description is optional. You can choose whether or not "
				+ "the deck is single selection or multiple selection. Single selection means that when "
				+ "voting, users will only be able to choose one card tto submit their vote. Multiple "
				+ "selection means that multiple cards can be slected to create a total. That total can "
				+ "be submitted as the user's vote. You can have up to 15 cards in a single deck. To set "
				+ "a value for a card in the deck, just click on the card and type an integer into the "
				+ "given textbox. You may change card's value by clicking on the card and re-typing a "
				+ "number. You can also have the "
				+ "option to include a ? in the deck. The ? represents I Don't Know. If a user is not sure "
				+ "how long a requirement will take, they can set that card as their submission. "
				+ "You may also choose one of five colors for the back of cards, red is the default, blue "
				+ "green, yellow and purple. Once "
				+ "all of the cards have values associated with them, the deck can be saved.");
		

		createDeckExp.setEditable(false);
		createDeckExp.setBackground(null);
		createDeckExp.setWrapStyleWord(true);
		createDeckExp.setLineWrap(true);

		// Creates a scroll pane to hold the planning poker description area
		JScrollPane createPane = new JScrollPane(createDeckExp);
		createPane.setPreferredSize(new Dimension(600, 150));
		createPane.setBorder(null);
		
		//Creates a hyperlink for the video tutorial
		videoTutorial = new JXHyperlink();
		videoTutorial.setText("Here is a video tutorial showing how to create a new Planning Poker deck.");
		videoTutorial.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Desktop.getDesktop().browse(new URI("https://www.youtube.com/watch?v=okqEVeNqBhc"));
				} catch (URISyntaxException | IOException ex) {
					//It looks like there's a problem
					logger.log(Level.WARNING, "The link cannot be opened", ex);
				}
			}
		});
		
		// Adds components to the panel
		panel.add(ppIntroLabel);
		//panel.add(expPane);
		panel.add(ppExplanation);
		panel.add(ppWhyLabel);
		//panel.add(whyPane);
		panel.add(ppWhyExp);
		panel.add(createDeckLabel);
		//panel.add(createPane);
		panel.add(createDeckExp);
		panel.add(videoTutorial);
		
		// Adjusts constraints on components
		layout.putConstraint(SpringLayout.NORTH, ppIntroLabel, 10, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST, ppIntroLabel, 5, SpringLayout.WEST, panel);
		
		layout.putConstraint(SpringLayout.NORTH, ppExplanation, 10, SpringLayout.SOUTH, ppIntroLabel);
		layout.putConstraint(SpringLayout.WEST, ppExplanation, 5, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.EAST, ppExplanation, 600, SpringLayout.WEST, panel);
		
		layout.putConstraint(SpringLayout.NORTH, ppWhyLabel, 40, SpringLayout.SOUTH, ppExplanation);
		layout.putConstraint(SpringLayout.WEST, ppWhyLabel, 5, SpringLayout.WEST, panel);
		
		layout.putConstraint(SpringLayout.NORTH, ppWhyExp, 10, SpringLayout.SOUTH, ppWhyLabel);
		layout.putConstraint(SpringLayout.WEST, ppWhyExp, 5, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.EAST, ppWhyExp, 600, SpringLayout.WEST, panel);
		
		layout.putConstraint(SpringLayout.NORTH, createDeckLabel, 40, SpringLayout.SOUTH, ppWhyExp);
		layout.putConstraint(SpringLayout.WEST, createDeckLabel, 5, SpringLayout.WEST, panel);
		
		layout.putConstraint(SpringLayout.NORTH, videoTutorial, 8, SpringLayout.SOUTH, createDeckLabel);
		layout.putConstraint(SpringLayout.WEST, videoTutorial, 5, SpringLayout.WEST, panel);
		
		layout.putConstraint(SpringLayout.NORTH, createDeckExp, 8, SpringLayout.SOUTH, videoTutorial);
		layout.putConstraint(SpringLayout.WEST, createDeckExp, 5, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.EAST, createDeckExp, 600, SpringLayout.WEST, panel);
		
		this.setRightComponent(scrollPanel);
		this.setLeftComponent(filterPanel);
		this.setDividerLocation(200);
	}
}