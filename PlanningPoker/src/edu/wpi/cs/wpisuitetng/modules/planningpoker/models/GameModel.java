/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractListModel;

//import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.Game;

/**
 * @author dstapply
 *
 */
@SuppressWarnings({"serial"})
public class GameModel extends AbstractListModel<Game>{
	
	/** List of available games */
	private List<Game> games;
	
	/**
	 * Constructs an empty list of games
	 */
	public GameModel() {
		games = new ArrayList<Game>();
	}

	/**
	 * Gets a game at the specified location
	 * @param the index to retrive
	 */
	@Override
	public Game getElementAt(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getSize() {
		return games.size();
	}

	/**
	 * Adds a Game to the data model games
	 * @param newGame
	 * 			game to be added to the games ArrayList
	 */
	public void addGame(Game newGame) {
		games.add(newGame);
		
		this.fireIntervalAdded(this, 0, 0);
	}

	public void emptyModel() {
		int oldSize = getSize();
		Iterator<Game> iterator = games.iterator();
		while (iterator.hasNext()) {
			iterator.next();
			iterator.remove();
		}
		this.fireIntervalRemoved(this, 0, Math.max(oldSize - 1, 0));		
	}

	public void addGames(Game[] newGames) {
		for (int i = 0; i < newGames.length; i++) {
			this.games.add(newGames[i]);
		}
		this.fireIntervalAdded(this, 0, Math.max(getSize() - 1, 0));
	}

}
