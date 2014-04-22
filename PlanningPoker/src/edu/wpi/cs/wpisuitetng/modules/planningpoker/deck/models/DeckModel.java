package edu.wpi.cs.wpisuitetng.modules.planningpoker.deck.models;

import javax.swing.AbstractListModel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.abstractmodel.AbstractModelObserver;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.abstractmodel.ObservableModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;

public class DeckModel extends AbstractListModel<Deck> implements AbstractModelObserver {
	private static DeckModel instance = null;

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Deck getElementAt(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(ObservableModel o, Object arg) {
		// TODO Auto-generated method stub
		
	}

	public static DeckModel getInstance() {
		if(instance == null){
			instance = new DeckModel();
		}
		return instance;
	}
	
	public void update(Deck[] decks){
		//TODO add functionality
	}

}
