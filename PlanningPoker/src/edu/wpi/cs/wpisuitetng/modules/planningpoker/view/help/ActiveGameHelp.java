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

public class ActiveGameHelp extends JScrollPane implements IHelpPanel {
	JLabel headingLabel;
	JLabel gameInfo;
	JLabel gameInfoLabel;
	JTextArea gameInfoExplanation;
	JLabel cardVote;
	JLabel cardVoteLabel;
	JTextArea cardVoteExplanation;
	JLabel textVote;
	JLabel textVoteLabel;
	JTextArea textVoteExplanation;	
	
	public ActiveGameHelp() {
		build();
	}
	
	private void build() {
		/**
		 *  Set up initial container with spring layout */
		Container view = new Container();
		SpringLayout layout = new SpringLayout();
		view.setLayout(layout);
		view.setPreferredSize(new Dimension(610, 1450));
		
		//Add the heading label to the Panel
		headingLabel = new JLabel("Active Games Help");
		headingLabel.setFont(makeFont(8));
		
		//Add label for game info
		gameInfoLabel = new JLabel("Game Info");
		gameInfoLabel.setFont(makeFont(5));
		
		//Add image for game info
		gameInfo = addImage("game_info.png");
		
		//Add explanation for game info
		gameInfoExplanation = new JTextArea();
		gameInfoExplanation.setText("This displays the name, description, creator and end date of "
				+ "the game. A progress bar displays the voting progress on the current game "
				+ "displayed requirement. The game's creator also has the option to end the game "
				+ "before the end date and time. The game's creator can provide a reason why "
				+ "they ended the game early in the textbox provided. If you are not the game's "
				+ "creator, this option is not available to you");
		
		gameInfoExplanation.setEditable(false);
		gameInfoExplanation.setBackground(null);
		gameInfoExplanation.setWrapStyleWord(true);
		gameInfoExplanation.setLineWrap(true);
		
		//Add label for card voting
		cardVoteLabel = new JLabel("Card Voting");
		cardVoteLabel.setFont(makeFont(5));
		
		//Add image for card voting
		cardVote = addImage("card_vote.png");
		
		//Add explanation for card info
		cardVoteExplanation = new JTextArea();
		cardVoteExplanation.setText("This is displayed when cards are being used for voting. "
				+ "The requirements for the game are displayed on the list. If you have "
				+ "submitted a vote for a requirement, your esitmate for that requirement "
				+ "is recorded and the requirement is set as complete for you. You can change your vote for "
				+ "that requirement while the game is still active. If the deck is single selection, "
				+ "then only one card can be selected for the estimation. If the deck is multiple "
				+ "selection, multiple cards can be selected can be added together and the sum can "
				+ "be submitted as the estimation. The ? card is the 'I don't know' card. If it is "
				+ "available, selecting it will deselct all other cards and the submitting it as "
				+ "an esitmate will set the estimate as 'I don't know'. Deseleting the ? button will "
				+ "reselect the cards that were selecting before selecting the ? card. Hitting the "
				+ "Clear Vote button will clear all currently selected cards. Hitting the Submit"
				+ "Vote button will submit your estimation for that requirement.");
		
		cardVoteExplanation.setEditable(false);
		cardVoteExplanation.setBackground(null);
		cardVoteExplanation.setWrapStyleWord(true);
		cardVoteExplanation.setLineWrap(true);
		
		//Add label for text voting
		textVoteLabel = new JLabel("Text Voting");
		textVoteLabel.setFont(makeFont(5));
		
		//Add image for text voting
		textVote = addImage("text_vote.png");
		
		//Add explanation for text voting
		textVoteExplanation = new JTextArea();
		textVoteExplanation.setText("This is displated when votes are to be manually entered. The "
				+ "requirements for the game are displayed on the list. If you have submitted a "
				+ "vote for a requirement, your estimate for that requirement is recorded and the "
				+ "requirement is set as complete for you. You can change your vote for that requirement "
				+ "while the game is still active. The text box will check for an integer. Upon entering an "
				+ "integer, you cna then submit your estimate with the Submit Vote button.");
		
		textVoteExplanation.setEditable(false);
		textVoteExplanation.setBackground(null);
		textVoteExplanation.setWrapStyleWord(true);
		textVoteExplanation.setLineWrap(true);
		
		view.add(headingLabel);
		view.add(gameInfo);
		view.add(gameInfoLabel);
		view.add(gameInfoExplanation);
		view.add(cardVote);
		view.add(cardVoteLabel);
		view.add(cardVoteExplanation);
		view.add(textVote);
		view.add(textVoteLabel);
		view.add(textVoteExplanation);
		
		/**
		 * Constraints for the overall panel layout
		 */
		layout.putConstraint(SpringLayout.NORTH, headingLabel, 5, SpringLayout.NORTH, view);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, headingLabel, 0, SpringLayout.HORIZONTAL_CENTER, view);
		
		layout.putConstraint(SpringLayout.NORTH, gameInfoLabel, 20, SpringLayout.SOUTH, headingLabel);
		layout.putConstraint(SpringLayout.WEST, gameInfoLabel, 20, SpringLayout.WEST, view);
		
		layout.putConstraint(SpringLayout.NORTH, gameInfo, 20, SpringLayout.SOUTH, gameInfoLabel);
		layout.putConstraint(SpringLayout.WEST, gameInfo, 5, SpringLayout.WEST, view);
		
		layout.putConstraint(SpringLayout.NORTH, gameInfoExplanation, 20, SpringLayout.SOUTH, gameInfoLabel); 
		layout.putConstraint(SpringLayout.EAST, gameInfoExplanation, 0, SpringLayout.EAST, view);
		layout.putConstraint(SpringLayout.WEST, gameInfoExplanation, 20, SpringLayout.EAST, gameInfo);
		
		layout.putConstraint(SpringLayout.NORTH, cardVoteLabel, 20, SpringLayout.SOUTH, gameInfo);
		layout.putConstraint(SpringLayout.WEST, cardVoteLabel, 20, SpringLayout.WEST, view);
		
		layout.putConstraint(SpringLayout.NORTH, cardVote, 20, SpringLayout.SOUTH, cardVoteLabel);
		layout.putConstraint(SpringLayout.WEST, cardVote, 5, SpringLayout.WEST, view);
		
		layout.putConstraint(SpringLayout.NORTH, cardVoteExplanation, 20, SpringLayout.SOUTH, cardVoteLabel); 
		layout.putConstraint(SpringLayout.EAST, cardVoteExplanation, 0, SpringLayout.EAST, view);
		layout.putConstraint(SpringLayout.WEST, cardVoteExplanation, 20, SpringLayout.EAST, cardVote);
		
		layout.putConstraint(SpringLayout.NORTH, textVoteLabel, 20, SpringLayout.SOUTH, cardVote);
		layout.putConstraint(SpringLayout.WEST, textVoteLabel, 20, SpringLayout.WEST, view);
		
		layout.putConstraint(SpringLayout.NORTH, textVote, 20, SpringLayout.SOUTH, textVoteLabel);
		layout.putConstraint(SpringLayout.WEST, textVote, 5, SpringLayout.WEST, view);
		
		layout.putConstraint(SpringLayout.NORTH, textVoteExplanation, 20, SpringLayout.SOUTH, textVoteLabel); 
		layout.putConstraint(SpringLayout.EAST, textVoteExplanation, 0, SpringLayout.EAST, view);
		layout.putConstraint(SpringLayout.WEST, textVoteExplanation, 20, SpringLayout.EAST, textVote);
		
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


	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.planningpoker.view.help.IHelpPanel#getIdentifierIndex()
	 */
	@Override
	public int getIdentifierIndex() {
		return 2;
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
