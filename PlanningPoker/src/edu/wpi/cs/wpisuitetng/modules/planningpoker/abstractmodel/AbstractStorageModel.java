package edu.wpi.cs.wpisuitetng.modules.planningpoker.abstractmodel;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.swing.AbstractListModel;

import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;

public abstract class AbstractStorageModel<T extends ObservableModel & IStorageModel<T>>
		extends AbstractListModel<T> implements IModelObserver {

	protected final List<T> list;
	protected boolean serverUpdating = false;

	protected AbstractStorageModel(List<T> list) {
		this.list = list;
	}
	
	/**
	 * Adds a Model to the data model
	 * 
	 * @param object
	 *            object to be added to the games ArrayList
	 */
	protected synchronized void add(T object) {
		while (serverUpdating) {
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		list.add(object);
		object.addObserver(this);
		this.fireIntervalAdded(this, 0, 0);
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
	}

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
	}
	
	/**
	 * Removes a model from the model without removing the model from the database
	 * This is used when a database add fails so that the model reflects the
	 * database properly
	 * 
	 * @param toRemove
	 *            The model to remove from the model
	 */
	public T getModelById(UUID id) throws NotFoundException{
		for(T o : list){
			if(o.getIdentity().equals(id)){
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
	 * @param allGames
	 *            the list of games already in the model
	 */
	protected synchronized boolean updateModels(T[] allModels) {
		boolean changes = false;

		if (!isUpdating()) {
			serverUpdating = true;

			int startingSize = getSize();
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
						modelList.copyFrom(aModel);
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
					System.out.print("Updating the model");
					System.out
							.println("\tNEW MODEL FOUND BEING ADDED TO MODEL: "
									+ aModel.getName());
				}
			}
			this.fireIntervalAdded(this, startingSize - 1, getSize() - 1); // Fires
			// the event listeners on this list.
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
				return true;
			}
		}
		return false;
	}

}
