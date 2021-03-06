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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.deck.models.Card;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.RightHalfActiveGamePanel;

/**
 * sets the ActionListener for each of the estimates. The "I don't know" button
 * clears all selected buttons when selected and if deselected, previously
 * selected buttons are reselected. "I don't know" button is deselected when an
 * estimate button is selected.
 * 
 * @author Bobby Drop Tables
 * 
 */
public class CardActionListenerRefactor implements ActionListener {

	// Size is the size of the button array including the "IDK" button
	private int size;
	private CardButton button;
	private ActiveCardsPanel cardsPanel;
	private Card card;
	private RightHalfActiveGamePanel estimatePanel;
	private static final Logger logger = Logger.getLogger(CardActionListenerRefactor.class
			.getName());
	/**
	 * sets the action listener for each button
	 * 
	 * @param card the card being pressed
	 * 
	 * @param passedButton
	 *            the button to be given an action
	 * @param passedCardsPanel
	 *            cards that have been selected
	 */
	public CardActionListenerRefactor(Card card,
			CardButton passedButton, ActiveCardsPanel passedCardsPanel) {
		size = card.getDeckSize();
		this.card = card;
		cardsPanel = passedCardsPanel;
		estimatePanel = passedCardsPanel.getParentPanel();
		button = passedButton;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			Image frontImg = card.getFrontImage();
			Image backImg = card.getBackImage();
			// if the button is the I don't know button
			if (!button.isInteger()) {
				if (button.isSelected()) { // if the IDK button is toggled on
					for (CardButton c : cardsPanel.getCardButtonArray()) {
						if(!button.equals(c)){	//If this is not the "?" button
							if (c.isSelected()) { //If it is selected
								cardsPanel.memoryArrayAddElt(c);
								c.doClick();
							}
							cardsPanel.addIDK();
						}
					}
					button.setIcon(new ImageIcon(backImg)); //Swich image states
					cardsPanel.memoryArrayAddElt(button); //Add this card to the memory array
				} else { // if the IDK button is toggled off
					if (!card.isMultipleSelection()) {
						button.setIcon(new ImageIcon(frontImg));
						return;
					}
					for (CardButton c: cardsPanel.memoryArray()) {
						if(!button.equals(c)){
							c.doClick();
						} else {
							button.setIcon(new ImageIcon(frontImg));
						}
					}
					cardsPanel.subIDK();
					estimatePanel.updateSum();
					button.setIcon(new ImageIcon(frontImg));
					cardsPanel.memoryArrayClear();
				}

			} else { // otherwise, if it is a button other than the I don't know
				// button
				if (button.isSelected()) { // if button is toggled on
					if (!card.isMultipleSelection()) {
							for (CardButton c: cardsPanel.getCardButtonArray()) {
								if(!button.equals(c)){	//If this is not the "?" button
									if (c.isSelected()) { //If it is selected
										cardsPanel.memoryArrayAddElt(c);
										c.doClick();
									}
								}
							}
							logger.log(Level.INFO,"is SingleSelection");
						}
					if (estimatePanel.getGame().getDeck().hasIDontKnowCard()) {
						if (cardsPanel.getCardButtonArray().get(size-1)
								.isSelected()) {
							cardsPanel.memoryArrayClear();
							cardsPanel.getCardButtonArray().get(size-1).doClick();
						}
					}
					button.setIcon(new ImageIcon(backImg));

					addToCardSum(Integer.parseInt(card.getText()));
				} else { // if button is toggled off
					button.setIcon(new ImageIcon(frontImg));
					decToCardSum(Integer.parseInt(card.getText()));
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Increase total sum by amount entered
	 * 
	 * @param cardValue
	 */
	public void addToCardSum(int cardValue) {
		cardsPanel.addToCardSum(cardValue);
		estimatePanel.updateSum();
	}

	/**
	 * Decrease total sum by amount entered
	 * 
	 * @param cardValue
	 */
	public void decToCardSum(int cardValue) {
		cardsPanel.decToCardSum(cardValue);
		estimatePanel.updateSum();
	}
}
