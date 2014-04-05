/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.models;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirementmodels.Requirement;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;

/**
 * @author jonathanleitschuh
 *
 */
public class GameModelTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		ViewEventController viewCon = ViewEventController.getInstance();
	}

	@Test
	public void testAddGame() {
		GameModel model = GameModel.getInstance();
		model.emptyModel();
		assertEquals(model.getSize(), 0);
		Game object = new Game(4, "Test Game", "Steve", false);
		model.addGame(object);
		assertEquals(model.getSize(), 1);
		Game objectReturned = model.getElementAt(0);
		assertEquals(objectReturned.getName(), "Test Game");
		assertEquals(objectReturned.getId(), 4);
	}
	
	@Test
	public void testAddGameList(){
		GameModel model = GameModel.getInstance();
		model.emptyModel();
		assertEquals(0, model.getSize());
		Game game1 = new Game("Game", "With a name", "Steve", new ArrayList<Requirement>(), false, true);
		Game game2 = new Game("Game2", "With a name2", "Steve2", new ArrayList<Requirement>(), true, true);
		Game game3 = new Game("Game3", "With a name3", "Steve3", new ArrayList<Requirement>(), false, false);
		Game gameList[] = new Game[3];
		gameList[0] = game1;
		gameList[1] = game2;
		gameList[2] = game3;
		
		model.addGames(gameList);
		
		Game returned1 = model.getElementAt(0);
		Game returned2 = model.getElementAt(1);
		Game returned3 = model.getElementAt(2);
		assertEquals(returned1.getName(), "Game");
		assertEquals(returned2.getName(), "Game2");
		assertEquals(returned3.getName(), "Game3");
		
		Game game4 = new Game("An updated game", "Overwriting an existing", "Steve", new ArrayList<Requirement>(), false, true);
		game4.setIdentifier(game1.getIdentifier());
		gameList[0] = game4;
		model.addGames(gameList);
		
		assertEquals(model.getElementAt(0).getName(), "An updated game");
	}

}
