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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.swing.AbstractListModel;

import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.abstractmodel.AbstractStorageModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.abstractmodel.IModelObserver;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.abstractmodel.ObservableModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.controllers.AddGameController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.controllers.UpdateGameController;
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
public class GameModel extends AbstractStorageModel<Game> {

	/** Stores the singleton instance of this model */
	private static GameModel instance;

	/** Stores the next ID */
	private final int nextID;


	/**
	 * Constructs an empty list of games for the project. This is private in
	 * order to prevent multiple instantiations of this.
	 */
	private GameModel() {
		super(new ArrayList<Game>());
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
	 * Adds a Game to the data model games
	 * 
	 * @param newGame
	 *            game to be added to the games ArrayList
	 */
	public synchronized void addGame(Game newGame) {
		add(newGame);
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
	}

	/**
	 * Empties the model of games and resets the model back to the default
	 * state.
	 */
	@Override
	public synchronized void emptyModel() {
		super.emptyModel();
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
		removeFromModel(toRemove);
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
	 *            the list of games already in the model
	 */
	public synchronized void updateGames(Game[] allGames) {
		boolean changes = updateModels(allGames);

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
		return list;
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
				ViewEventController.getInstance().refreshGameTable();
				ViewEventController.getInstance().refreshGameTree();
			} catch (Exception e) {
				System.err.println("The network has not been instantiated");
			}
		} else {
			System.err
					.println("GAME MODEL ATTEMPTED TO UPDATE SOMETHING NOT A GAME");
		}

	}

	/**
	 * Gets the game with the UUID passed.
	 * 
	 * @param id
	 *            the UUID of the game to get
	 * @return return game with UUID
	 * @throws NotFoundException 
	 */
	public Game getGameById(UUID id) throws NotFoundException {
		return getModelById(id);
	}

	/**
	 * checks to see if the server is updating the game
	 * 
	 * @return true if the server is updating the game model
	 */
	public boolean isServerUpdating() {
		return serverUpdating;
	}
}
