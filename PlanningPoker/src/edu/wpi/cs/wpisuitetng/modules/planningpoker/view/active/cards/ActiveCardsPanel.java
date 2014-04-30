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
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.deck.models.Card;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.deck.models.Deck;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.RightHalfActiveGamePanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.IDataField;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.IErrorView;

/**
 * creates the panel that displays all of the buttons and adds a clear button
 * 
 * @author Bobby Drop Tables
 * 
 */
public class ActiveCardsPanel extends JPanel implements IDataField {

	private int sum = 0;
	private final Deck deck;
	private final List<CardButton> JToggleButtonList = new ArrayList<CardButton>();
	private JLabel counterLabel = new JLabel("Your current estimate total: " + 0);
	private List<CardButton> memoryArray = new ArrayList<CardButton>();

	// initialized array to remember what buttons were pressed if "0?" button is
	// pressed

	private RightHalfActiveGamePanel parent;

	public ActiveCardsPanel(Deck passedDeck,
			RightHalfActiveGamePanel passedPanel) {
		parent = passedPanel;
		deck = passedDeck;

		int cardsPerRow = 10;

		this.setPreferredSize(new Dimension(525, (68 * (Math.round(deck.getSize()
				/ cardsPerRow)))));
		for (Card c : deck.getCards()) {
			System.out.println("card value: " + c.getText());
			JToggleButtonList.add(new CardButton(c, this));
		}// idk button is part of array

	
	}

	public int getCount() {
		return 0;
	}

	public void clearCards() throws IOException {
		Image frontImg = ImageIO.read(getClass().getResource("card_front.png"));
		for (int i = 0; i < (deck.getSize() - 1); i++) {
			if (JToggleButtonList.get(i).isSelected()) {
				JToggleButtonList.get(i).doClick();
				JToggleButtonList.get(i).setIcon(new ImageIcon(frontImg));
			}
		}
		if (JToggleButtonList.get(deck.getSize() - 1).isSelected()) {
			JToggleButtonList.get(deck.getSize() - 1).doClick();
		}
	}

	/**
	 * Increase total sum by amount entered
	 * 
	 * @param cardValue
	 *            the amount to be added
	 */
	public void addToCardSum(int cardValue) {
		sum += cardValue;
		System.out.println(sum);
	}
	
	public void addIDK() {
		sum = 0;
		parent.selectedIDK();
		System.out.println("I don't know is selected.");
	}
	
	/**
	 * Decrease total sum by amount entered
	 * 
	 * @param cardValue
	 *            the amount to be subtracted
	 */
	public void decToCardSum(int cardValue) {
		sum -= cardValue;
		System.out.println(sum);
	}


	/**
	 * gets the total of all the cards in the deck
	 * 
	 * @return sum of all the cards in deck
	 */
	public int getMaxSum() {
		int sum = 0;
		for (int i = 0; i < deck.getSize(); i++) {
			sum += Integer.parseInt(deck.getCards().get(i).getText());
		}
		return sum;
	}

	/**
	 * getter for the sum
	 * 
	 * @return the sum of the cards
	 */
	public int getSum() {
		return sum;
	}
	
	public void clearSum() {
		sum = 0;
	}

	/**
	 * getter for all of the buttons
	 * 
	 * @return a list of buttons in the panel
	 */
	public List<CardButton> getCardButtonArray() {
		return JToggleButtonList;
	}

	/**
	 * adds an element to the array of buttons to be remembered when the "0?"
	 * button is unpress
	 * 
	 * @param elt
	 *            element to be added
	 **/
	public void memoryArrayAddElt(CardButton elt) {
		memoryArray.add(elt);
	}

	/**
	 * getter for size of the memory array
	 * 
	 * @return size of the memory array
	 **/
	public int memoryArrayGetSize() {
		return memoryArray.size();
	}

	/**
	 * clears memory array; use after the values stored in the array are
	 * restored
	 **/
	public void memoryArrayClear() {
		memoryArray.clear();
	}

	/**
	 * adds index of the button to be remembered to the memory array
	 * 
	 * @param elt
	 *            element to be searched for in the memory array
	 * @return the index of the element in the memory array
	 **/
	public List<CardButton> memoryArray() {
		return memoryArray;
	}

	@Override
	public boolean validateField(IErrorView warningField, boolean showLabel,
			boolean showBox) {
		// TODO Auto-generated method stub
		return false;
	}

	
	@Override
	public boolean hasChanges() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public RightHalfActiveGamePanel getParentPanel(){
		return parent;
	}

}