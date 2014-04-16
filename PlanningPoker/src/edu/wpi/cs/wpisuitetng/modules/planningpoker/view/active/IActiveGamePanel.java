package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;

/**
 * If this panel is a way to view an active game then
 * implement this interface on your class
 * @author jonathanleitschuh
 *
 */
public interface IActiveGamePanel{
	
	Game getGame();
	
	boolean readyToRemove();
}
