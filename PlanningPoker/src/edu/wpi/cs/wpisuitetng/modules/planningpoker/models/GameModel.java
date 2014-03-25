/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.models;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;

/**
 * @author dstapply
 *
 */
@SuppressWarnings({"serial"})
public class GameModel extends AbstractListModel{
	
	/** List of available games */
	private List<Game> games;
	
	/**
	 * Constructs an empty list of games
	 */
	public GameModel() {
		games = new ArrayList<Game>();
	}

	@Override
	public Object getElementAt(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * adds a Game to the data model games
	 * @param newGame
	 * 			game to be added to the games ArrayList
	 */
	public void addGame(Game newGame) {
		games.add(newGame);
		
		this.fireIntervalAdded(this, 0, 0);
	}

}
