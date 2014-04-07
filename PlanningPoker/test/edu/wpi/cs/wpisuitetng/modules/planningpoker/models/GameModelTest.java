/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.models;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.game.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.game.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.requirement.Requirement;
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
		Game object = new Game("Test Game", "Test Description", "Steve", new ArrayList<Requirement>(), true, false);
		
		model.emptyModel();
		assertEquals(model.getSize(), 0);
		
		model.addGame(object);
		assertEquals(model.getSize(), 1);
		
		Game objectReturned = model.getElementAt(0);
		assertEquals(objectReturned.getName(), "Test Game");
		assertEquals(objectReturned.getDescription(), "Test Description");
		assertEquals(objectReturned.getCreator(), "Steve");
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
		game4.setIdentifier(game1.getIdentity());
		gameList[0] = game4;
		model.addGames(gameList);
		
		assertEquals(model.getElementAt(0).getName(), "An updated game");
		model.emptyModel();
		assertEquals(0, model.getSize());
	}
	
	@Test
	public void testUpdateGameListWithAllFields(){
		GameModel model = GameModel.getInstance();
		
		ArrayList<Requirement> reqs = new ArrayList<Requirement>();
		reqs.add(new Requirement("Requirement", "Description"));
		Game game1 = new Game("Game", "With a name", "Steve", reqs, false, true);
		Game game2 = new Game("Game2", "With a name2", "Steve2", new ArrayList<Requirement>(), true, true);
		Game game3 = new Game("Game3", "With a name3", "Steve3", new ArrayList<Requirement>(), true, true);
		
		
		Game gameList[] = new Game[3];
		gameList[0] = game1;
		gameList[1] = game2;
		gameList[2] = game3;
		
		model.addGames(gameList);
		
		
		ArrayList<Requirement> reqs2 = new ArrayList<Requirement>();
		reqs2.add(new Requirement("RequirementChanged", "DescriptionChanged"));
		Game game1Changed = new Game("Game1 Changed", "DescriptionChanged", "DifferentName", reqs2, true, false);
		game1Changed.setIdentifier(game1.getIdentity()); //Makes this game 'the equivalent' to the other game
		
		model.updateGames(gameList);
		
		assertEquals(3, model.getSize());
		gameList[0] = game1Changed;
		
		//Check to make sure that changes are not reflected in the model
		assertEquals("Steve", model.getElementAt(0).getCreator());
		assertEquals("Game", model.getElementAt(0).getName());
		
		//Changes should now be reflected after updateing the model
		model.updateGames(gameList);
		
		assertEquals(3, model.getSize());
		
		assertEquals("Game1 Changed", model.getElementAt(0).getName());
		assertEquals("DifferentName", model.getElementAt(0).getCreator());
		assertEquals("RequirementChanged", model.getElementAt(0).getRequirements().get(0).getName());
		//assertEquals("DescriptionChanged", model.getElementAt(0));
		
	}

}
