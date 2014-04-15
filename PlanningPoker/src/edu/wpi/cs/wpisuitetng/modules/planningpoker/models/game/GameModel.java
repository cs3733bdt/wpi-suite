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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.models.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.swing.AbstractListModel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.AddGameController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.UpdateGameController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.observers.AbstractModelObserver;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.observers.ObservableModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;

//import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.Game;

/**
 * A singleton model for all for the Game data on the server. This Model should
 * always reflect the data stored on the database and be updated whenever the
 * model requires updating
 * 
 * @author dstapply jonathanleitschuh
 */
@SuppressWarnings({ "serial" })
public class GameModel extends AbstractListModel<Game> implements
		AbstractModelObserver {

	/** Stores the singleton instance of this model */
	private static GameModel instance;

	/** Stores the next ID */
	private int nextID;

	/** List of available games */
	private List<Game> games;

	private boolean serverUpdating = false;

	/**
	 * Constructs an empty list of games for the project. This is private in
	 * order to prevent multiple instantiations of this.
	 */
	private GameModel() {
		games = new ArrayList<Game>();
		nextID = 0;
	}

	/**
	 * Gets the current model of this class
	 * 
	 * @return The singleton instance of this object
	 */
	public static GameModel getInstance() {
		if (instance == null) {
			instance = new GameModel();
		}
		return instance;
	}

	/**
	 * Gets a game at the specified location
	 * 
	 * @param the
	 *            index to retrive
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
	 * 
	 * @param newGame
	 *            game to be added to the games ArrayList
	 */
	public synchronized void addGame(Game newGame) {
		while (serverUpdating()) {}
		games.add(newGame);
		try {
			AddGameController.getInstance().addGame(newGame);
		} catch (Exception e) {
			System.err.println("WARNING: FAILED TO ADD GAME TO SERVER: "
					+ newGame.getName());
		}
		try { // Prevents a null pointer exception when the running tests (the
				// JPanel's aren't instantiated)
			ViewEventController.getInstance().refreshGameTable();
			ViewEventController.getInstance().refreshGameTree();
		} catch (Exception e) {
			System.err.println("No view output attached");
		}
		newGame.addObserver(this);
		this.fireIntervalAdded(this, 0, 0);
	}

	/**
	 * Empties the model of games and resets the model back to the default
	 * state.
	 */
	public synchronized void emptyModel() {
		int oldSize = getSize();
		Iterator<Game> iterator = games.iterator();
		while (iterator.hasNext()) {
			iterator.next();
			iterator.remove();
		}
		this.fireIntervalRemoved(this, 0, Math.max(oldSize - 1, 0));
		try { // Prevents a null pointer exception when the running tests (the
				// JPanel's aren't instantiated)
			ViewEventController.getInstance().refreshGameTable();
			ViewEventController.getInstance().refreshGameTree();
		} catch (Exception e) {
			System.err.println("ViewEventController not fully initiallized");
		}
	}

	/**
	 * Removes a game from the model without removing the game from the database
	 * This is used when a database add fails so that the model reflects the
	 * database properly
	 * 
	 * @param toRemove
	 *            The game to remove from the model
	 */
	public void removeGameFromModel(Game toRemove) {
		int index = 0;
		Iterator<Game> iterator = games.iterator();
		while (iterator.hasNext()) {
			Game game = iterator.next();
			if (game.equals(toRemove)) {
				game.deleteObservers();
				iterator.remove();
				this.fireIntervalRemoved(this, index, index);
			}
			index++;
		}
	}

	/**
	 * Adds all of the list of games to the model Used by the database
	 * controller construct the game model initially.
	 * 
	 * @param newGames
	 *            The games to be added to the model
	 */
	public synchronized void addGames(Game[] newGames) {
		updateGames(newGames);
	}

	/**
	 * Updates the list of games in the model. If the there is any matching
	 * UUID's between the model and the list of games the values for the games
	 * will be updated using the server's values.
	 * 
	 * @param allGames
	 */
	public synchronized void updateGames(Game[] allGames) {
		boolean changes = false;

		if (!isUpdating()) {
			serverUpdating = true;

			int startingSize = getSize();
			for (Game aGame : allGames) { // Iterates over the new model
				boolean found = false; // Has this Game been found in the list
				// GAME EXIST IN THE MODEL
				for (Game modelGame : games) { // Iterates over the existing
												// model
					if (aGame.identify(modelGame)) { // Compares the UUID's of
														// the two objects to
														// see if they should be
														// the same
						found = true; // This game has been found in the list
						// aGame.deleteObservers();
						aGame.addObserver(this);
						modelGame.copyFrom(aGame);
						changes = true;
					}
				}

				// GAME DOES NOT EXIST YET ADD TO MODEL
				if (!found) { // If the game is not found then
					changes = true; // There were changes to the model
					startingSize++; // This Game will be added to the model so
									// increase the starting size
					// aGame.deleteObservers();
					aGame.addObserver(this); // Add an observer on this game
					games.add(aGame); // Adds this game to the list of games in
										// this list
					System.out.print("Updating the model");
					System.out
							.println("\tNEW GAME FOUND BEING ADDED TO MODEL: "
									+ aGame.getName());
				}
			}
			this.fireIntervalAdded(this, startingSize - 1, getSize() - 1); // Fires
																			// the
																			// event
																			// listeners
																			// on
																			// this
																			// list.

			if (changes) { // Only repaint game tree if the model has changed
				try { // This is used to prevent the a null pointer exception
						// when running test cases (the JPanel's aren't
						// instantiated)
					ViewEventController.getInstance().refreshGameTable(); // Currently
																			// serves
																			// no
																			// purpose
					ViewEventController.getInstance().refreshGameTree(); // Refreshes
																			// the
																			// active
																			// table
				} catch (Exception e) {
					System.err
							.println("ViewEventController not fully initiallized");
				}
			} else {
			}
		}
		serverUpdating = false;
	}

	@Override
	public String toString() {
		return ("You got the singleton of the Game Model");
	}

	/**
	 * Gets the list of games stored in the model
	 * 
	 * @return the list of games
	 */
	public List<Game> getGames() {
		return games;
	}

	/**
	 * This method is called whenever any of the models game objects is changed.
	 * An application calls an <tt>Observable</tt> object's
	 * <code>notifyObservers</code> method to have all the object's observers
	 * notified of the change.
	 * 
	 * @param o
	 *            the observable object.
	 * @param arg
	 *            an argument passed to the <code>notifyObservers</code> method.
	 */
	@Override
	public void update(ObservableModel o, Object arg) {
		System.out.println("I'm here with: " + o.toString());
		if (o instanceof Game) {
			try {
				UpdateGameController.getInstance().updateGame((Game) o);
				System.out.println("A game is being updated: "
						+ ((Game) o).getName());
			} catch (Exception e) {
				System.err.println("The network has not been instantiated");
			}
		} else {
			System.err
					.println("GAME MODEL ATTEMPTED TO UPDATE SOMETHING NOT A GAME");
		}

	}

	/**
	 * @return true if this game model is updating
	 * 
	 */
	public boolean isUpdating() {
		for (Game g : games) {
			if (g.hasChanged()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Gets the game with the UUID passed.
	 * @param id the UUID of the game to get
	 * @return return game with UUID
	 */
	public Game getGameById(UUID id) {
		for (Game g : games) {
			if (g.getIdentity().equals(id)) {
				return g;
			}
		}
		System.err.println("Could not fine a game with idenity: " + id);
		
		return null;
	}

	/**
	 * 
	 * @return true if the server is updating the game model
	 */
	public boolean serverUpdating() {
		return serverUpdating;
	}
}
