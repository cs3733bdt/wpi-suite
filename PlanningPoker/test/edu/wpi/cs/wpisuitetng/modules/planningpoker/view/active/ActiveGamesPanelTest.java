package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.game.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.requirement.Requirement;

public class ActiveGamesPanelTest {
	Game gameView;
	ActiveGamesPanel panel;

	@Before
	public void setUp() throws Exception {
		gameView = new Game("A Game", "A Description", new ArrayList<Requirement>(), true, false);
		panel = new ActiveGamesPanel(gameView);
	}

	@Test
	public void test() {
		
	}

}
