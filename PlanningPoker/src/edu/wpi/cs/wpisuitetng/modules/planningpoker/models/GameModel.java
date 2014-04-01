/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractListModel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.AddGameController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;

//import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.Game;

/**
 * @author dstapply
 *
 */
@SuppressWarnings({"serial"})
public class GameModel extends AbstractListModel<Game>{
	
	private static GameModel instance;
	private int nextID;
	
	/** List of available games */
	private List<Game> games;
	
	/**
	 * Constructs an empty list of games for the project.
	 * This is private in order to prevent multiple instantiations of this.
	 */
	private GameModel() {
		games = new ArrayList<Game>();
		nextID = 0;
	}
	
	public static GameModel getInstance(){
		if(instance == null){
			instance = new GameModel();
		}
		return instance;
	}

	/**
	 * Gets a game at the specified location
	 * @param the index to retrive
	 */
	@Override
	public Game getElementAt(int arg0) {
		return games.get(arg0);
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
		try{
			AddGameController.getInstance().addGame(newGame);
			ViewEventController.getInstance().refreshGameTable();
			ViewEventController.getInstance().refreshGameTree();
		} catch (Exception e){
			System.err.println("WARNING: FAILED TO ADD GAME TO SERVER: " + newGame.getName());
		}
		this.fireIntervalAdded(this, 0, 0);
	}

	public void emptyModel() {
		int oldSize = getSize();
		Iterator<Game> iterator = games.iterator();
		while (iterator.hasNext()) {
			iterator.next();
			iterator.remove();
		}
		this.fireIntervalRemoved(this, 0, Math.max(oldSize -1, 0));	
		try{
			ViewEventController.getInstance().refreshGameTable();
			ViewEventController.getInstance().refreshGameTree();
		} catch (Exception e) {}
	}

	public void addGames(Game[] newGames) {
		for (int i = 0; i < newGames.length; i++) {
			this.games.add(newGames[i]);
			if(newGames[i].getId() >= nextID) nextID = newGames[i].getId() + 1;
		}
		this.fireIntervalAdded(this, 0, Math.max(getSize() - 1, 0));
		ViewEventController.getInstance().refreshGameTable();
		ViewEventController.getInstance().refreshGameTree();
	}

	public List<Game> getGames() {
		return games;
	}

}
