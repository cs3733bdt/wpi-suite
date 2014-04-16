package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;

public interface IGamePanel {
	Game getGame();
	
	boolean readyToRemove();
}
