package edu.wpi.cs.wpisuitetng.modules.planningpoker.deck.models;

/**
 * Card object
 * Holds data for its parent deck.
 * This class is never handled by the database so there is no need to manage it
 * @author jonathanleitschuh
 *
 */
public class Card {
	private Deck parentDeck;
	private String text;
	
	public Card(String text, Deck parentDeck) {
		this.text = text;
		this.parentDeck = parentDeck;
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

}
