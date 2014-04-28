package edu.wpi.cs.wpisuitetng.modules.planningpoker.deck.models;

import java.awt.image.BufferedImage;
import java.io.IOException;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.decks.creation.CardImage;
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
	
	public String getText(){
		return text;
	}
	
	public int getDeckSize(){
		return parentDeck.getSize();
	}
	
	public BufferedImage getBackImage() throws IOException{
		if(imageBack == null){
			throw new IOException();
		}
		return imageBack;
	}
	
	public BufferedImage getFrontImage() throws IOException{
		if(imageFront == null){
			throw new IOException();
		}
		return imageFront;
	}
	

}
