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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.decks.creation;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.deck.models.Card;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.deck.models.Deck;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.IDataField;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.IErrorView;

public class CardPanel extends JPanel implements IDataField {
	
	private static final Logger logger = Logger.getLogger(CardPanel.class.getName());
	
	private CardPanel instance;
	
	private final boolean isReopen;
	
	//The current color image
	private ColorEnum color = ColorEnum.RED;
	private CreateDeckPanel parent;
	private CardImage cardRed;
	
	private List<CardImage> cards;

	/**
	 * 
	 * @param CreateDeckPanel parent
	 */
	public CardPanel(CreateDeckPanel parent) {
		instance = this;
		this.parent = parent;
		isReopen = parent.isReopen();
		cards = new ArrayList<CardImage>(){
			@Override
			public boolean add(CardImage o){
				instance.add(o);
				return super.add(o);
			}
			
			@Override
			public CardImage remove(int index){
				CardImage removed = super.remove(index);
				instance.remove(removed);
				return removed;
			}
			
			@Override
			public boolean remove(Object o){
				instance.remove((CardImage)o);
				return super.remove(o);
			}
		};
		
		build();
		
		revalidate();
		repaint();
	}
	
	private void build(){
		setPreferredSize(new Dimension(10, 450));
		//add(cardRed); // adds initial card to panel //Not needed done in the list
		if(!isReopen){
			cardRed = new CardImage(color, this);
			cards.add(cardRed); // adds initial card to card list
			cardRed.setVisible(true);
		}
	}
	
	/**
	 * set a deck
	 * @param deck
	 */
	public void setDeck(Deck deck){
		for(Card c : deck.getCards()){
			if(c.isInteger()){ //Make sure this card dosen't have the IDK card
				CardImage newCard = new CardImage(deck.getColor(), this);
				newCard.setValueLabel(c.getText());
				//add(newCard); //Now done in the override
				cards.add(newCard);
			}
		}
	}
	
	
	/**
	 * Changes every card in the deck to have the new color
	 * @param color the color you want the deck to be
	 */
	public void setColor(ColorEnum color){
		for(CardImage c: cards){
			c.setColor(color);
		}
		this.color = color;
	}

	/**
	 * Retrieves all of the values from the cards currently in the panel
	 * @return a list of the integers for all of the cards
	 */
	public List<Integer> getCardValues(){
		List<Integer> values = new ArrayList<Integer>();
		for(CardImage c : cards){
			try{
				values.add(c.getCardValue());
			} catch (NumberFormatException e){
				logger.log(Level.WARNING, "One of the cards was not formatted properly", e);
			}
		}
		return values;
	}
	
	public CreateDeckPanel getCardPanelParent(){
		return parent;
	}
	
	@Override
	public boolean validateField(IErrorView warningField, boolean showLabel, boolean showBox) {
		boolean areCardsValid = true;
		for(CardImage c : cards){
			//If there are any cards that return false then this should fail
			areCardsValid &= c.validateField(warningField, showLabel, showBox);
		}
		return areCardsValid;
	}

	@Override
	public boolean hasChanges() {
		for (CardImage c: cards) {
			if(c.hasChanges()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * set size of cards
	 * @param int value
	 */
	public void setNumberCards(int value) {
		int oldSize = cards.size();
		int difference = value - oldSize;
		
		if(difference > 0){
			for(int i = 0; i < difference; i++){
				CardImage newCard = new CardImage(color, this);
				cards.add(newCard);
				//add(newCard); // This is done in the list now
			}
		}
		
		if(difference < 0){ //Remove the difference
			for (int i = 0; i < difference * (-1); i++) { //Remember that difference is negative
				cards.remove(cards.size()-1); //This removes the component from the list and from the GUI in one call
			}
		}
		
		revalidate();
		repaint();
		
	}
	
	public int getNumberCards(){
		return cards.size();
	}

	public List<CardImage> getCards() {
		return cards;
	}
	
	/**
	 * disable Cards and mouseListener
	 */
	public void disableCards(){
		for(CardImage c: cards){
			c.disableCardMouseListener();
		}
	}

}
