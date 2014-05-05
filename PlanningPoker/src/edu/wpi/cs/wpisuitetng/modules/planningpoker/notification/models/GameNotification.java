package edu.wpi.cs.wpisuitetng.modules.planningpoker.notification.models;

import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.abstractmodel.IModelObserver;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.abstractmodel.IStorageModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.abstractmodel.ObservableModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.notification.models.GameNotificationModel;

public class GameNotification extends ObservableModel implements IModelObserver, IStorageModel<GameNotification>{
	
	private UUID gameIdentity;
	private boolean gameCreationNotified;
	private boolean gameCompletionNotified;
	private static Logger logger = Logger.getLogger(GameNotification.class.getName());
	
	public GameNotification(UUID gameIdentity) {
		this.gameIdentity = gameIdentity;
		gameCreationNotified = false;
		gameCompletionNotified = false;
	}
	
	public UUID getGameIdentity() {
		return gameIdentity;
	}
	
	public void setGameIdentity(UUID gameIdentity) {
		if (!this.gameIdentity.equals(gameIdentity)) {
			makeChanged();
			delayChange("setGameIdentity");
			this.gameIdentity = gameIdentity;
			notifyObservers();
		}
	}
	
	public boolean getGameCreationNotified() {
		return gameCreationNotified;
	}
	
	public void setGameCreationNotified(boolean value) {
		if (gameCreationNotified != value) {
			makeChanged();
			delayChange("setGameCreationNotified");
			gameCreationNotified = value;
			notifyObservers();
		}
	}
	
	public boolean getGameCompletionNotified() {
		return gameCompletionNotified;
	}
	
	public void setGameCompletionNotified(boolean value) {
		if (gameCompletionNotified != value) {
			makeChanged();
			delayChange("setGameCompletionNotified");
			gameCompletionNotified = value;
			notifyObservers();
		}
	}
	
	/**
	 * hold the code while the game model is updating prevent race-time
	 * condition for fields setting/overriding
	 */
	private void delayChange(String source) {
		while (GameNotificationModel.getInstance().isServerUpdating()) {
			try {
				Thread.sleep(5);
				logger.log(Level.WARNING, "Looping in the game");
			} catch (InterruptedException e) {
				logger.log(Level.WARNING, "The thread was interrupted.", e);
			}
		}
	}

	@Override
	public String toJSON() {
		return new Gson().toJson(this, GameNotification.class);
	}
	
	public static GameNotification fromJSON(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, GameNotification.class);
	}
	
	public static GameNotification[] fromJsonArray(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, GameNotification[].class);
	}

	@Override
	public Boolean identify(Object o) {
		if (o == null) {
			return false;
		}
		if (o.getClass() != this.getClass()) {
			return false;
		}
		GameNotification comp = (GameNotification) o;
		/*
		 * Changed so that this comparison does not run off of the name because
		 * the name of the game may be changed by the user at a later time.
		 */
		if (!gameIdentity.equals(comp.getGameIdentity())) {
			return false;
		}
		return true;
	}

	@Override
	public boolean copyFrom(GameNotification toCopyFrom) {
		boolean needsUpdate = false; // Triggers if there was a change to to the UUID
		boolean wasChanged = false; // True if there were any changes made
		
		if (gameCreationNotified != toCopyFrom.getGameCreationNotified()) {
			gameCreationNotified = toCopyFrom.getGameCreationNotified();
			needsUpdate = true;
			wasChanged = true;
			logger.log(Level.INFO, "gameCreationNotified copied " + gameCreationNotified);
		}
		
		if (gameCompletionNotified != toCopyFrom.getGameCompletionNotified()) {
			gameCompletionNotified = toCopyFrom.getGameCompletionNotified();
			needsUpdate = true;
			wasChanged = true;
			logger.log(Level.INFO, "gameCompletionNotified copied " + gameCompletionNotified);
		}

		if (gameIdentity.equals(toCopyFrom.getGameIdentity())) {
			needsUpdate = false;
		} else {
			gameIdentity = toCopyFrom.getGameIdentity();
			needsUpdate = true;
		}

		if (needsUpdate) {
			logger.log(Level.SEVERE,
					"WARNING! THERE WAS A COPY OVER FOR TWO NON MATCHING UUID GAMES!");
		}
		if (hasChanged()) {
			logger.log(Level.WARNING, "hasChanges may have been set in "
					+ getName().trim()
					+ " durring the copy over. Please check your logic.");
		}

		return wasChanged;
	}
	

	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(ObservableModel o, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UUID getIdentity() {
		// TODO Auto-generated method stub
		return gameIdentity;
	}

	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
