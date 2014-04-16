package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;

/**
 * Interface IGamePanel
 * This interface is implemented in order to simplify transitions between GUI layouts for games.
 * This interface provides the methods needed by the ViewEventController to add/remove these panels from the active view
 * @author jonathanleitschuh
 *
 */
public interface IGamePanel {
	
	/**
	 * Gets the game for the panel.
	 * This is used for comparisons to make sure that we are re-displaying the correct game when ask to show a panel in the
	 * ViewEventController
	 * @return the game for this panel.
	 */
	Game getGame();
	
	/**
	 * Checks to see if the fields have changed.
	 * If they haven't change then the panel can be removed
	 * @return true if this panel is ready to be removed from the view
	 */
	boolean readyToRemove();
}
