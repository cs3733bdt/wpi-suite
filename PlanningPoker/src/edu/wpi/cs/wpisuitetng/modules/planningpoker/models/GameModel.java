/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractListModel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.AddGameController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.UpdateGameController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;

//import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.Game;

/**
 * A singleton model for all for the Game data on the server.
 * This Model should always reflect the data stored on the database and be updated whenever the model requires updating
 * 
 * @author dstapply jonathanleitschuh
 */
@SuppressWarnings({"serial"})
public class GameModel extends AbstractListModel<Game> implements Observer{
	
	/** Stores the singleton instance of this model */
	private static GameModel instance;
	
	/** Stores the next ID */
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
	
	/**
	 * Gets the current model of this class
	 * @return The singleton instance of this object
	 */
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
		try{ //Prevents a null pointer exception when the running tests (the JPanel's aren't instantiated)
			AddGameController.getInstance().addGame(newGame);
			ViewEventController.getInstance().refreshGameTable();
			ViewEventController.getInstance().refreshGameTree();
		} catch (Exception e){
			System.err.println("WARNING: FAILED TO ADD GAME TO SERVER: " + newGame.getName());
		}
		newGame.addObserver(this);
		this.fireIntervalAdded(this, 0, 0);
	}

	/**
	 * Empties the model of games and resets the model back to the default state.
	 */
	public void emptyModel() {
		int oldSize = getSize();
		Iterator<Game> iterator = games.iterator();
		while (iterator.hasNext()) {
			iterator.next();
			iterator.remove();
		}
		this.fireIntervalRemoved(this, 0, Math.max(oldSize -1, 0));	
		try{ //Prevents a null pointer exception when the running tests (the JPanel's aren't instantiated)
			ViewEventController.getInstance().refreshGameTable();
			ViewEventController.getInstance().refreshGameTree();
		} catch (Exception e) {}
	}
	
	/**
	 * Removes a game from the model without removing the game from the database
	 * This is used when a database add fails so that the model reflects the database properly
	 * @param toRemove The game to remove from the model
	 */
	public void removeGameFromModel(Game toRemove){
		int index = 0;
		Iterator<Game> iterator = games.iterator();
		while(iterator.hasNext()) {
			Game game = iterator.next();
			if(game.equals(toRemove)){
				iterator.remove();
				this.fireIntervalRemoved(this, index, index);
			}
			index ++;
		}
	}

	/**
	 * Adds all of the list of games to the model
	 * Used by the database controller construct the game model initially.
	 * @param newGames The games to be added to the model
	 */
	public void addGames(Game[] newGames) {
		updateGames(newGames);
	}
	
	/**
	 * Updates the list of games in the model. If the there is any matching UUID's between the model and the list of
	 * games the values for the games will be updated using the server's values.
	 * @param allGames
	 */
	public void updateGames(Game[] allGames){
		int startingSize = getSize();
		for(Game aGame : allGames){ 			//Iterates over the new model
			boolean found = false;				//Has this Game been found in the list
			for(Game modelGame : games){		//Iterates over the existing model
				if(aGame.identify(modelGame)){	//Compares the UUID's of the two objects to see if they should be the same
					found = true;				//This game has been found in the list
				}
			}
			if(!found){							//If the game is not found then 
				startingSize ++;				//This Game will be added to the model so increase the starting size
				aGame.addObserver(this);		//Add an observer on this game
				this.games.add(aGame);			//Adds this game to the list of games in this list
			}
		}
		this.fireIntervalAdded(this, startingSize-1, getSize()-1); 	//Fires the event listeners on this list.
		try{ //This is used to prevent the a null pointer exception when running test cases (the JPanel's aren't instantiated)
			ViewEventController.getInstance().refreshGameTable();		//Currently serves no purpose
			ViewEventController.getInstance().refreshGameTree();		//Refreshes the active table
		} catch(Exception e) {
			
		}
	}
	
	
	
	/**
	 * Gets the list of games stored in the model
	 * @return the list of games
	 */
	public List<Game> getGames() {
		return games;
	}

	/**
     * This method is called whenever any of the models game objects is changed. An
     * application calls an <tt>Observable</tt> object's
     * <code>notifyObservers</code> method to have all the object's
     * observers notified of the change.
     *
     * @param   o     the observable object.
     * @param   arg   an argument passed to the <code>notifyObservers</code>
     *                 method.
     */
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof Game){
			UpdateGameController.getInstance().updateGame((Game)o);
		}
		
	}

}
