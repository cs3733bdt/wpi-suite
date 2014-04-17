package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.cards;

import java.util.ArrayList;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.IDataField;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.IErrorView;

/**
 * 
 * @author Justin
 *This class is for building decks to use in planning poker and has 3 constructors to produce different decks as needed
 */
public class Deck implements IDataField {
	
	//The deck variable holds the list of Strings, each string corresponding to a card in the deck
	private ArrayList<String> Deck = new ArrayList<String>();
	
	//set to true if there is an error with the created deck
	private boolean errorBit = false;
	
	
	//boolean variable that states whether this deck object represents a default deck or not
	private boolean isDefaultDeck;

	//Integer that holds the number of cards in this deck object
	private int numCards = 0;
	
	/**
	 * Constructs a default deck
	 */
	public Deck() {
		isDefaultDeck = true;
		numCards = 7;
		buildDefaultDeck(numCards);
	}

	/**
	 * Constructs a default deck with numCards number of cards from the Fibonacci sequence
	 * this constructor sets the error bit 
	 */
	public Deck(int numCards) {
		isDefaultDeck = true;
		this.numCards = numCards;
		buildDefaultDeck(numCards);
	}

	/**
	 * Constructs a default deck from the passed Deck
	 * @param Deck an ArrayList of strings, each string corresponding to a card in the created Deck object
	 */
	public Deck(ArrayList<String> Deck) {
		isDefaultDeck = false;
		numCards = Deck.size();
		this.Deck = Deck;
	}
	
	
	/**
	 * @param numCards number of Cards to be added. 0 returns an error. 
	 */
	private void buildDefaultDeck(int numCards) {
		int numLoops;
		if (numCards == 0) {
			errorBit = true; 
			return;
		}
		else { 
			numLoops = numCards - 1;
		}
		int firstnum = 0;
		int secondnum = 1;
		int currnum;
		Deck.add(Integer.toString(secondnum));
		
		for (int i = 0; i < numLoops; i++) {
			currnum = firstnum + secondnum;
			Deck.add(Integer.toString(currnum));
			firstnum = secondnum;
			secondnum = currnum;
		}
	}

	/**
	 * returns true if the deck validates
	 */
	public boolean validateField(IErrorView warningField, boolean show) {
		return !errorBit;
	}

	@Override
	public boolean hasChanges() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public int get(int index) {
		return Integer.parseInt(Deck.get(index));
	}
	
	
	
	
	
	
	
	
}
