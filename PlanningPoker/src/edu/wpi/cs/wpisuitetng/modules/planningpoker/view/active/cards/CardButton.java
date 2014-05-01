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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.cards;

import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;
import java.util.List;

import javax.accessibility.Accessible;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.deck.models.Card;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.RightHalfActiveGamePanel;

/**
 * creates all of the buttons to be used for the estimation
 * 
 * @author Justin Canas
 * 
 */
public class CardButton extends JToggleButton implements Accessible {
	JToggleButton button;
	String cardText;
	Card card;
	ActiveCardsPanel parent;

	/**
	 * creates the buttons based on the deck
	 * 
	 * @param cardNum
	 *            the index of the card
	 * @param passedDeck
	 *            the deck of cards to be used
	 * @param passedCardsPanel
	 *            the panel that displays the cards
	 * @param passedEstimatePanel
	 *            the panel that displays the estimates
	 */

	public CardButton(Card card, ActiveCardsPanel passedCardsPanel) {
		parent = passedCardsPanel;
		this.card = card;

		// Initialize the Button and the number on the button
		String buttonNum = card.getText();
		cardText = card.getText();

		// button = new JToggleButton(buttonNum);
		try {
			Image frontImg = card.getFrontImage();
			setIcon(new ImageIcon(frontImg));
			setBorder(new EmptyBorder(0,0,0,0));
//			setContentAreaFilled(false);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		// Set the Button's tooltiptext and position it correctly
		
		try{
			Integer.getInteger(card.getText());
			setToolTipText("Add " + buttonNum + " to the total");
		} catch (NumberFormatException e){
			setToolTipText("I don't know what to estimate");
		}
		
		setText(card.getText());

		setHorizontalTextPosition(SwingConstants.CENTER);
		setVerticalAlignment(SwingConstants.CENTER);

		// Add the action listener to the button
		addActionListener(new CardActionListenerRefactor(card, this,
				passedCardsPanel));
		parent.add(this);
	}

	/**
	 * getter for the value of the card
	 * 
	 * @return the value of the card
	 */
	public String getValue() {
		return getText();
	}
	
	public boolean isInteger(){
		try{
			Integer.parseInt(getText());
			return true;
		} catch (NumberFormatException e){
			return false;
		}
	}
}
