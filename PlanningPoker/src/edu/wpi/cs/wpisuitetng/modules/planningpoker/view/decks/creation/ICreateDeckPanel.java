package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.decks.creation;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.deck.models.Deck;

public interface ICreateDeckPanel {

	public void saveDeck(Deck deck);
	
	public Deck getDeck();
	
}
