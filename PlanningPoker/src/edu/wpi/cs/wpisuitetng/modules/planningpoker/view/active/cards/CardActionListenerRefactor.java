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

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

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

	/**
	 * sets the action listener for each button
	 * 
	 * @param index
	 *            of the button to be created
	 * @param deckUsed
	 *            to create the button
	 * @param passedButton
	 *            the button to be given an action
	 * @param passedCardsPanel
	 *            cards that have been selected
	 * @param passedEstimatePanel
	 *            the estimate before the action occurs
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
				System.out.println("Button is not integer");
				if (button.isSelected()) { // if the IDK button is toggled on
					System.out.println("Button is selected");
					for (CardButton c : cardsPanel.getCardButtonArray()) {
						if(!button.equals(c)){	//If this is not the "?" button
							if (c.isSelected()) { //If it is selected
								cardsPanel.memoryArrayAddElt(c);
								c.doClick();
							}
						}
					}
					button.setIcon(new ImageIcon(backImg)); //Swich image states
					cardsPanel.memoryArrayAddElt(button); //Add this card to the memory array
				} else { // if the IDK button is toggled off
					System.out.println("Button is not selected");
					for (CardButton c: cardsPanel.memoryArray()) {
						if(!button.equals(c)){
							c.doClick();
						} else {
							button.setIcon(new ImageIcon(frontImg));
						}
					}
					button.setIcon(new ImageIcon(frontImg));
					cardsPanel.memoryArrayClear();
				}
				
			} else { // otherwise, if it is a button other than the I don't know
						// button
				if (button.isSelected()) { // if button is toggled on
					if (cardsPanel.getCardButtonArray().get(size)
							.isSelected()) {
						cardsPanel.memoryArrayClear();
						cardsPanel.getCardButtonArray().get(size).doClick();
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
