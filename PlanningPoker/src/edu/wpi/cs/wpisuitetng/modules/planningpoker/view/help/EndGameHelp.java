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

public class EndGameHelp extends JScrollPane implements IHelpPanel {
	JLabel headingLabel;
	JLabel endGame;
	JLabel endGameLabel;
	JTextArea endGameExplanation;
	JLabel statsHelp;
	JLabel statsLabel;
	JTextArea statsExplanation;
	
	public EndGameHelp() {
		build();
	}
	
	private void build() {
		/**
		 *  Set up initial container with spring layout */
		Container view = new Container();
		SpringLayout layout = new SpringLayout();
		view.setLayout(layout);
		view.setPreferredSize(new Dimension(1000, 775));
		setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		//Add the heading label to the Panel
		headingLabel = new JLabel("End Game Help");
		headingLabel.setFont(makeFont(8));
		
		//Add the image for this panel
		endGame = addImage("ended_game_info.png");
		
		//Add label for end game explanation
		endGameLabel = new JLabel("Game Information");
		endGameLabel.setFont(makeFont(5));
		
		//Add the explanation for the end game image
		endGameExplanation = new JTextArea();
		endGameExplanation.setText("The left half displays information about the game and a list of the requirements "
				+ "that were voted on in the game. Clicking one of the requirements will display the game statistics "
				+ "on the right side of the panel.");
		
		endGameExplanation.setEditable(false);
		endGameExplanation.setBackground(null);
		endGameExplanation.setWrapStyleWord(true);
		endGameExplanation.setLineWrap(true);
		
		//Add statistics image
		statsHelp = addImage("Stats.png");
		
		//Add label for statistics explanation
		statsLabel = new JLabel("Requirement Statistics");
		statsLabel.setFont(makeFont(5));
		
		//Add the explanation for the statistics image
		statsExplanation = new JTextArea();
		statsExplanation.setText("The right half displays the statistics and description of the selected requirement. "
				+ "The mean, median, standard deviation, min and max of all votes, the total votes and number of "
				+ "\"I don't know\" votes are displayed. "
				+ " \"I don't know\" counts as a 0 for statistics calculations. Each user's votes are also displayed. "
				+ "The creator of the game is able to submit a final estimate for the requirement at the bottom. "
				+ "This will get saved in the requirement manager.");
		
		statsExplanation.setEditable(false);
		statsExplanation.setBackground(null);
		statsExplanation.setWrapStyleWord(true);
		statsExplanation.setLineWrap(true);
		
		view.add(headingLabel);
		view.add(endGame);
		view.add(endGameLabel);
		view.add(endGameExplanation);
		view.add(statsHelp);
		view.add(statsLabel);
		view.add(statsExplanation);
		
		/**
		 * Constraints for the overall panel layout
		 */
		layout.putConstraint(SpringLayout.NORTH, headingLabel, 5, SpringLayout.NORTH, view);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, headingLabel, 0, SpringLayout.HORIZONTAL_CENTER, view);
		
		layout.putConstraint(SpringLayout.NORTH, endGame, 20, SpringLayout.SOUTH, endGameLabel);
		layout.putConstraint(SpringLayout.WEST, endGame, 5, SpringLayout.WEST, view);
		
		layout.putConstraint(SpringLayout.NORTH, endGameLabel, 20, SpringLayout.SOUTH, headingLabel); 
		layout.putConstraint(SpringLayout.WEST, endGameLabel, 20, SpringLayout.WEST, view);
		
		layout.putConstraint(SpringLayout.NORTH, endGameExplanation, 20, SpringLayout.SOUTH, endGameLabel); 
		layout.putConstraint(SpringLayout.EAST, endGameExplanation, 0, SpringLayout.EAST, view);
		layout.putConstraint(SpringLayout.WEST, endGameExplanation, 20, SpringLayout.EAST, endGame);
		
		layout.putConstraint(SpringLayout.NORTH, statsHelp, 20, SpringLayout.SOUTH, statsLabel);
		layout.putConstraint(SpringLayout.WEST, statsHelp, 5, SpringLayout.WEST, view);
		
		layout.putConstraint(SpringLayout.NORTH, statsLabel, 20, SpringLayout.SOUTH, endGame); 
		layout.putConstraint(SpringLayout.WEST, statsLabel, 20, SpringLayout.WEST, view);
		
		layout.putConstraint(SpringLayout.NORTH, statsExplanation, 20, SpringLayout.SOUTH, statsLabel); 
		layout.putConstraint(SpringLayout.EAST, statsExplanation, 0, SpringLayout.EAST, view);
		layout.putConstraint(SpringLayout.WEST, statsExplanation, 20, SpringLayout.EAST, statsHelp);		
		
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
		return 1;
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
