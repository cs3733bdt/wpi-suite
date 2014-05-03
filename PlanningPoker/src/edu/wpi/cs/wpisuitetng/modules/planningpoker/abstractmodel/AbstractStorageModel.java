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
package edu.wpi.cs.wpisuitetng.modules.planningpoker.abstractmodel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractListModel;

import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;

/**
 * @author jonathanleitschuh
 * 
 *         The Abstract Storage model This Abstract Model's goal is to stores a
 *         client side copy of all of the objects of this type on the database.
 * 
 * @param <T>
 *            The model that this is storing.
 *            <code> <T extends ObservableModel & IStorageModel<T>> </code>
 */
public abstract class AbstractStorageModel<T extends ObservableModel & IStorageModel<T>>
		extends AbstractListModel<T> implements IModelObserver {
	/** The list that holds all of the elements in this database */
	protected final List<T> list;

	/** If the server is updating this model */
	protected boolean serverUpdating = false;

	private static final Logger logger = Logger.getLogger(AbstractStorageModel.class
			.getName());

	/**
	 * Constructs the AbstractStorageModel with the given list
	 * 
	 * @param list
	 *            the list to store the data in
	 */
	protected AbstractStorageModel(List<T> list) {
		this.list = list;
	}

	/**
	 * Adds a Model to the data model Also attaches this model as an observer to
	 * the the passed object
	 * 
	 * @param object
	 *            object to be added and stored to the model
	 */
	protected synchronized void add(T object) {
		while (serverUpdating) {
			try {
				Thread.sleep(5);
				logger.log(Level.WARNING,
						"Waiting for the server to finish updating before adding the object");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		list.add(object);
		object.addObserver(this);
		this.fireIntervalAdded(this, 0, 0);
		logger.log(Level.FINEST, object.getClass().getName()
				+ "added to Abstract Storage Model");
	}

	/**
	 * Empties the model of objects and resets the model back to the default
	 * state.
	 */
	protected synchronized void emptyModel() {
		int oldSize = getSize();
		Iterator<T> iterator = list.iterator();
		while (iterator.hasNext()) {
			iterator.next();
			iterator.remove();
		}
		this.fireIntervalRemoved(this, 0, Math.max(oldSize - 1, 0));
		logger.log(Level.FINEST, "Finished emptying the model");
	}

	/**
	 * Removes a given model from the model without trying to remove it from the
	 * database This is used when an object is not properly added to the model
	 * 
	 * @param toRemove
	 *            the object to remove from the model
	 */
	protected void removeFromModel(T toRemove) {
		int index = 0;
		Iterator<T> iterator = list.iterator();
		while (iterator.hasNext()) {
			T object = iterator.next();
			if (object.equals(toRemove)) {
				object.deleteObservers();
				iterator.remove();
				this.fireIntervalRemoved(this, index, index);
				return;
			}
			index++;
		}
		logger.log(Level.FINEST, "Removed " + toRemove.getName()
				+ " from the AbstractStorageModel");
	}

	/**
	 * Retrieves a model from the model by using the UUID of the object
	 * @param id the UUID of the object
	 * @throws NotFoundException
	 *             when the UUID is not found in the storage model
	 * @return the model with the matching UUID
	 */
	public T getModelById(UUID id) throws NotFoundException {
		for (T o : list) {
			if (o.getIdentity().equals(id)) {
				return o;
			}
		}
		throw new NotFoundException();
	}

	/**
	 * Updates the list of models in this model. If the there is any matching
	 * UUID's between the model and the list of games the values for the games
	 * will be updated using the server's values.
	 * 
	 * This method will not update the model if any object stored in this model
	 * is currently in the state of "isUpdating"
	 * 
	 * @param allModels
	 *            the list of objects in the model
	 * @return true if the model changes during the method
	 */
	protected synchronized boolean updateModels(T[] allModels) {
		boolean changes = false;

		if (!isUpdating()) {
			serverUpdating = true;

			int startingSize = getSize();
			List<T> newModels = new ArrayList<T>();// Stores any new models for
													// logging purposes
			List<T> updatedModels = new ArrayList<T>(); // Stores any changed
														// models for logging
														// purposes

			for (T aModel : allModels) { // Iterates over the new model
				boolean found = false; // Has this Game been found in the list
				// GAME EXIST IN THE MODEL
				for (T modelList : list) { // Iterates over the existing
					// model
					if (aModel.identify(modelList)) { // Compares the UUID's of
						// the two objects to
						// see if they should be
						// the same
						found = true; // This game has been found in the list
						// aGame.deleteObservers();
						aModel.addObserver(this);
						if (modelList.copyFrom(aModel)) {
							updatedModels.add(aModel); // Stores this for
														// logging purposes
						}
						changes = true;
					}
				}

				// GAME DOES NOT EXIST YET ADD TO MODEL
				if (!found) { // If the game is not found then
					changes = true; // There were changes to the model
					startingSize++; // This Game will be added to the model so
					// increase the starting size
					// aGame.deleteObservers();
					aModel.addObserver(this); // Add an observer on this game
					list.add(aModel); // Adds this game to the list of games in
					// this list
					newModels.add(aModel); // Adds to the system logger output
				}
			}
			this.fireIntervalAdded(this, startingSize - 1, getSize() - 1); // Fires
			// the event listeners on this list.

			// Output the log of this interaction
			StringBuilder log = new StringBuilder();
			log.append("Model Updated\nAdditions:\n");
			for (T a : newModels) {
				log.append("\t" + a.getName() + "\n");
			}
			log.append("Changes\n");
			for (T a : updatedModels) {
				log.append("\t" + a.getName() + "\n");
			}
			logger.log(Level.INFO, log.toString());
			
			if(isUpdating()){
				logger.log(Level.WARNING, getClass().getName().trim() +"'s isUpdating() may have been set durring the copy over. Please check your logic.");
			}

		}
		serverUpdating = false;
		return changes;
	}

	@Override
	public int getSize() {
		return list.size();
	}

	/**
	 * Gets a game at the specified location
	 * 
	 * @param the
	 *            index to retrieve
	 */
	@Override
	public T getElementAt(int index) {
		return list.get(index);
	}

	/**
	 * checks to see if any element in the model is updating
	 * 
	 * @return true if this game model is updating
	 * 
	 */
	public boolean isUpdating() {
		for (T o : list) {
			if (o.hasChanged()) {
				logger.log(Level.FINEST, "The model was updating itself");
				return true;
			}
		}
		logger.log(Level.FINEST, "The model was not updating itself");
		return false;
	}

}
