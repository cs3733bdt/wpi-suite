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

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.tree.DeckTree;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.tree.GameTree;

/**
 * The default view for the GUI
 * @author Bobby Drop Tables
 *
 */
public class DeckOverview extends JSplitPane {
	
	JLabel ppIntroLabel;
	Font bigFont;
	JTextArea ppExplanation;
	JLabel ppWhyLabel;
	JTextArea ppWhyExp;
	JLabel createDeckLabel;
	JTextArea createDeckExp;
	
	JXHyperlink videoTutorial;
	
	DeckTree filterPanel = new DeckTree();
	
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
		ppExplanation.setText("Are you serious? A deck of cards is literally just a deck of cards. Have you never seen a deck of cards before??? Learn to play. ya dingus!");
		
		ppExplanation.setEditable(false);
		ppExplanation.setBackground(null);
		ppExplanation.setWrapStyleWord(true);
		ppExplanation.setLineWrap(true);

		// Creates a scroll pane to hold the planning poker description area
		JScrollPane expPane = new JScrollPane(ppExplanation);
		expPane.setPreferredSize(new Dimension(600, 120));
		expPane.setBorder(null);
		
		// Adds label
		ppWhyLabel = new JLabel("Why use a new deck?");
		ppWhyLabel.setFont(bigFont);
		
		// Adds text area describing the main reasons of using planning poker
		ppWhyExp = new JTextArea();
		ppWhyExp.setText("BECAUSE IT LOOKS SO MUCH COOLER WHEN YOU MAKE IT YOURSELF!!!!");
		
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
		
		// Adds text area describing how to create a planning poker game
		createDeckExp = new JTextArea();
		createDeckExp.setText("This is probably where actually useful information would go.");
		

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
		panel.add(videoTutorial);
		panel.add(createDeckExp);
		
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