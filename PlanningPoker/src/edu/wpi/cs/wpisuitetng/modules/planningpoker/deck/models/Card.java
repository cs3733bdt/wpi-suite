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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.deck.models;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Logger;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.decks.creation.ColorCardImage;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.decks.creation.ColorEnum;

/**
 * Card object
 * Holds data for its parent deck.
 * 
 * NOTE: This class is never handled by the database so there is no need to manage it
 * @author jonathanleitschuh
 */
public class Card {
	private Deck parentDeck;
	private String text;
	private BufferedImage imageBack = null;
	private BufferedImage imageFront = null;
	private static final Logger logger = Logger.getLogger(Card.class.getName());
	
	/**
	 * 
	 * @param a passing text
	 * @param a passing deck
	 */
	public Card(String text, Deck parentDeck) {
		this.text = text;
		this.parentDeck = parentDeck;
		try {
			imageBack = ColorCardImage.getColorCardImage(parentDeck.getColor());
			imageFront = ColorCardImage.getColorCardImage(ColorEnum.FRONT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString(){
		return text;
	}
	
	/**
	 * Gets the text of the 
	 * @return
	 */
	public String getText(){
		return text;
	}
	
	public int getDeckSize(){
		return parentDeck.getSize();
	}
	
	/**
	 * 
	 * @return back image of the card
	 * @throws IOException
	 */
	public BufferedImage getBackImage() throws IOException{
		if(imageBack == null){
			throw new IOException();
		}
		return imageBack;
	}
	
	/**
	 * 
	 * @return front image of the card
	 * @throws IOException
	 */
	public BufferedImage getFrontImage() throws IOException{
		if(imageFront == null){
			throw new IOException();
		}
		return imageFront;
	}
	
	/**
	 * 
	 * @return true if the text is an integer
	 */
	public boolean isInteger(){
		
		for(int i=0; i< text.length(); i++){
			if(Character.digit(text.charAt(i), 10)<0) return false;
		}
		return true;
	}
	
	public boolean isMultipleSelection() {
		return parentDeck.isMultipleSelection();
	}

}
