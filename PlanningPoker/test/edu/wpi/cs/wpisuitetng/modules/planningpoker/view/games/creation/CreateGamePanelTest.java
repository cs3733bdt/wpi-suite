package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.game.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.requirement.Requirement;

public class CreateGamePanelTest {
	Game gameView;
	CreateGamePanel panel;
	
	@Before
	public void setUp() throws Exception {
		gameView = new Game("A Game", "A Description", new ArrayList<Requirement>(), true, false);
		panel = new CreateGamePanel(gameView, false);
	}
	
	@Test
	public void test() {
		assertEquals(gameView.getName(), panel.getBoxName().getText());
		assertEquals(gameView.getDescription(), panel.getDescText());
	}

}
