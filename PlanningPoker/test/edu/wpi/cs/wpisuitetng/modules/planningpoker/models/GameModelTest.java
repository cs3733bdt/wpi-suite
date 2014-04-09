/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.game.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.game.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.requirement.Requirement;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.vote.Vote;
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
		
		model.emptyModel();
		
		ArrayList<Requirement> reqs = new ArrayList<Requirement>();
		
		Requirement req = new Requirement("Requirement", "Description");
		
		req.addVote(new Vote("Steve", 10));
		req.addVote(new Vote("Paul", 27));
		req.addVote(new Vote("Erin", 10));
		
		reqs.add(req);
		Game game1 = new Game("Game", "With a name", "Steve", reqs, false, true);
		assertEquals(3, req.getVoteCount());
		
		Game game2 = new Game("Game2", "With a name2", "Steve2", new ArrayList<Requirement>(), true, true);
		Game game3 = new Game("Game3", "With a name3", "Steve3", new ArrayList<Requirement>(), true, false);
		
		
		Game gameList[] = new Game[3];
		gameList[0] = game1;
		gameList[1] = game2;
		gameList[2] = game3;
		
		//FINISHED CONSTRUCTING DATA PRIOR TO PUSHING TO SERVER
		//IMITATES A INITIAL 'DATA' FROM THE SERVER
		
		
		
		
		ArrayList<Requirement> reqs2 = new ArrayList<Requirement>();
		
		reqs2.add(new Requirement("RequirementChanged", "DescriptionChanged"));
		
		Requirement req2 = new Requirement("Requirement With New Votes", "Description With New Votes");
		req2.setIdentity(req.getIdentity());
		
		req2.addVote(new Vote("Steve", 1));
		req2.addVote(new Vote("Paul", 2));
		req2.addVote(new Vote("Erin", 3));
		req2.addVote(new Vote("Steve", 4));
		
		reqs2.add(req2);
		
		assertEquals(2, reqs2.size());
		assertEquals(4, req2.getVoteCount());
		
		Game game1Changed = new Game("Game1 Changed", "DescriptionChanged", "DifferentName", reqs2, true, false);
		game1Changed.setIdentifier(game1.getIdentity()); //Makes this game 'the equivalent' to the other game
		game1Changed.setComplete();
		
		assertEquals("Requirement With New Votes", game1Changed.getRequirements().get(1).getName());
		assertEquals(4, game1Changed.getRequirements().get(1).getVoteCount());
		
		model.updateGames(gameList); //Post the array to the model
		//MIMIC THE 'BUILD' FROM THE SERVER
		
		
		assertEquals(3, model.getSize());
		gameList[0] = game1Changed;			//Changes game1 to game1changed
		
		//SHOULD BE NO CHANGES TO THE MODEL AT THIS POINT!
		assertEquals(3, model.getSize());
		assertEquals(1, model.getElementAt(0).getRequirements().size());
		assertEquals("Steve", model.getElementAt(0).getCreator());
		assertEquals("Game", model.getElementAt(0).getName());
		Requirement reqFromGame = model.getElementAt(0).getRequirements().get(0);
		assertEquals(3, reqFromGame.getVoteCount());
		
		model.updateGames(gameList);  //CHANGES TO THE MODEL
		//MIMICS CHANGES COMING FROM THE SERVER
		
		assertEquals(3, model.getSize());
		
		assertEquals("Game1 Changed", model.getElementAt(0).getName());
		assertEquals("DifferentName", model.getElementAt(0).getCreator());
		assertTrue(model.getElementAt(0).isComplete());
		
		
		Requirement fromModel = model.getElementAt(0).getRequirements().get(1);
		assertEquals("RequirementChanged", fromModel.getName());
		assertEquals("DescriptionChanged", fromModel.getDescription());
		assertEquals(0, fromModel.getVoteCount());
		
		
		Requirement fromModel2 = model.getElementAt(0).getRequirements().get(0);
		assertEquals("Requirement With New Votes", fromModel2.getName());
		assertEquals("Description With New Votes", fromModel2.getDescription());
		assertEquals(4, fromModel2.getVoteCount());
		
		fromModel2.addVote(new Vote("Sally", 10));
		model.getElementAt(0).getRequirements().get(0).addVote(new Vote("Annie", 10));
		
		model.emptyModel();
		assertEquals(0, model.getSize());
	}

}
