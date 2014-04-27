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

import java.awt.Image;
import java.io.IOException;
import java.util.List;

import javax.accessibility.Accessible;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.deck.models.Card;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.NewRightHalfActiveGamePanel;

/**
 * creates all of the buttons to be used for the estimation
 * 
 * @author Justin Canas
 * 
 */
public class CardButton extends JToggleButton implements Accessible {
	JToggleButton button;
	String cardText;
	ActiveCardsPanel panel;

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
		// Initialize the Button and the number on the button
		String buttonNum = card.getText();
		cardText = card.getText();

		// button = new JToggleButton(buttonNum);
		try {
			Image frontImg = ImageIO.read(getClass().getResource(
					"card_front.png"));
			setIcon(new ImageIcon(frontImg));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		// Set the Button's tooltiptext and position it correctly
		setText(cardText);
		setToolTipText("Add " + buttonNum + " to the total");

		setHorizontalTextPosition(SwingConstants.CENTER);
		setVerticalAlignment(SwingConstants.CENTER);

		// Add the action listener to the button
		addActionListener(new CardActionListenerRefactor(card, this,
				passedCardsPanel));
		panel.add(this);
	}

	/**
	 * getter for the value of the card
	 * 
	 * @return the value of the card
	 */
	public String getValue() {
		return getText();
	}
}
