package edu.wpi.cs.wpisuitetng.modules.planningpoker.notification.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.abstractmodel.AbstractStorageModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.abstractmodel.ObservableModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.notification.controllers.AddGameNotificationController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.notification.controllers.UpdateGameNotificationController;

public class GameNotificationModel extends AbstractStorageModel<GameNotification> {

	/** Stores the singleton instance of this model */
	private static GameNotificationModel instance = null;

	/** Stores the logger for the GameModel */
	private static final Logger logger = Logger.getLogger(GameModel.class
			.getName());

	/**
	 * Constructs an empty list of games for the project. This is private in
	 * order to prevent multiple instantiations of this.
	 */
	private GameNotificationModel() {
		super(new ArrayList<GameNotification>());
	}

	/**
	 * Gets the current model of this class
	 * 
	 * @return The singleton instance of this object
	 */
	public static GameNotificationModel getInstance() {
		if (instance == null) {
			instance = new GameNotificationModel();
		}
		return instance;
	}

	public synchronized void addGameNotification(GameNotification newGN) {
		add(newGN);
		try {
			AddGameNotificationController.getInstance().addGameNotification(newGN);
		} catch (NullPointerException e) {
			logger.log(Level.WARNING, "GameNotification could not be added", e);
		} catch (Exception e) {
			logger.log(Level.WARNING, "GameNotification could not be added", e);
		}
	}

	/**
	 * Empties the model of games and resets the model back to the default
	 * state.
	 */
	@Override
	public synchronized void emptyModel() {
		super.emptyModel();
	}

	/**
	 * Removes a game from the model without removing the game from the database
	 * This is used when a database add fails so that the model reflects the
	 * database properly
	 * 
	 * @param toRemove
	 *            The game to remove from the model
	 */
	public void removeGameNotificationFromModel(GameNotification toRemove) {
		removeFromModel(toRemove);
	}

	/**
	 * Adds all of the list of games to the model Used by the database
	 * controller construct the game model initially.
	 * 
	 * @param newGames
	 *            The games to be added to the model
	 */
	public synchronized void addGameNotifications(GameNotification[] newGNs) {
		updateGameNotifications(newGNs);
	}

	/**
	 * Updates the list of games in the model. If the there is any matching
	 * UUID's between the model and the list of games the values for the games
	 * will be updated using the server's values.
	 * 
	 * @param allGames
	 *            the list of games already in the model
	 * @return true
	 * 			  when the game model was changed during this call
	 */
	public synchronized boolean updateGameNotifications(GameNotification[] allGNs) {
		boolean changes = updateModels(allGNs);
		
		serverUpdating = false; // Duplicate just because we want to be sure
								// that the lock disengages
		return changes;
	}

	@Override
	public String toString() {
		return ("You got the singleton of the GameNotificationModel");
	}

	/**
	 * Gets the list of games stored in the model
	 * 
	 * @return the list of games
	 */
	public List<GameNotification> getGameNotifications() {
		return list;
	}
	
	public GameNotification getGameNotification(UUID uuid) {
		if (list != null) {
			for (GameNotification gn: list) {
				if (gn.getGameIdentity().equals(uuid))
					return gn;
			}
		}
		return null;
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
		logger.log(Level.INFO,"I'm here with: " + o.toString());
		if (o instanceof GameNotification) {
			try {
				UpdateGameNotificationController.getInstance().updateGameNotification((GameNotification) o);
				logger.log(Level.INFO,"A gamenotification is being updated: "
						+ ((GameNotification) o).getName());
			} catch (Exception e) {
				logger.log(Level.WARNING,
						"The network has not been instantiated.", e);
			}
		} else {
			logger.log(Level.WARNING,"GAMENOTIFICATIONMODEL ATTEMPTED TO UPDATE SOMETHING NOT A GAMENOTIFICATION");
		}

	}

	public boolean isServerUpdating() {
		return serverUpdating;
	}
}
