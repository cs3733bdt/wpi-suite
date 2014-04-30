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

public class EndGameHelp extends JScrollPane implements IHelpPanel {
	JLabel headingLabel;
	JLabel endGameImage;
	JTextArea endGameExplanation;
	
	public EndGameHelp() {
		build();
	}
	
	private void build() {
		/**
		 *  Set up initial container with spring layout */
		Container view = new Container();
		SpringLayout layout = new SpringLayout();
		view.setLayout(layout);
		view.setPreferredSize(new Dimension(610, 750));
		
		//Add the heading label to the Panel
		headingLabel = new JLabel("End Game Help");
		headingLabel.setFont(makeFont(8));
		
		//Add the image for this panel
		endGameImage = addImage("end_game.png");
		
		//Add the explanation for the end game image
		endGameExplanation = new JTextArea();
		endGameExplanation.setText("1. The name of the ended game.\n");
		endGameExplanation.append("2. The description of the ended game.\n");
		endGameExplanation.append("3. The game's creator.\n");
		endGameExplanation.append("4. The date and time the game ends.\n");
		endGameExplanation.append("5. The list of requirements voted on during the game.\n");
		endGameExplanation.append("6. The description of the requirement that is being displayed. \n");
		endGameExplanation.append("7. The mean, standard deviation, median, max, min of all the "
				+ "votes in the requirement and the number of people who voted on the requirement.\n");
		endGameExplanation.append("8. A list of each user's votes on the requirement.\n");
		endGameExplanation.append("9. The final estimate that the game creator can input.\n");
		endGameExplanation.append("10. Displays the current final estimate which is the mean of "
				+ "all the votes in the requirement.\n");
		endGameExplanation.append("11. Sets the final estimate for the requirement as entered in"
				+ " 9.\n");
		endGameExplanation.setFont(makeFont(5));
		
		endGameExplanation.setEditable(false);
		endGameExplanation.setBackground(null);
		endGameExplanation.setWrapStyleWord(true);
		endGameExplanation.setLineWrap(true);
		
		view.add(headingLabel);
		view.add(endGameImage);
		view.add(endGameExplanation);
		
		/**
		 * Constraints for the overall panel layout
		 */
		layout.putConstraint(SpringLayout.NORTH, headingLabel, 5, SpringLayout.NORTH, view);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, headingLabel, 0, SpringLayout.HORIZONTAL_CENTER, view);
		
		layout.putConstraint(SpringLayout.NORTH, endGameImage, 5, SpringLayout.SOUTH, headingLabel);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, endGameImage, 0, SpringLayout.HORIZONTAL_CENTER, headingLabel);
		
		layout.putConstraint(SpringLayout.NORTH, endGameExplanation, 20, SpringLayout.SOUTH, endGameImage);
		layout.putConstraint(SpringLayout.EAST, endGameExplanation, 0, SpringLayout.EAST, view);
		layout.putConstraint(SpringLayout.WEST, endGameExplanation, 0, SpringLayout.WEST, view);
		
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
