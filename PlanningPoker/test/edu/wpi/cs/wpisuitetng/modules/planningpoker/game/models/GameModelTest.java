/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.models.PPRequirement;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.vote.models.Vote;

/**
 * @author jonathanleitschuh
 *
 */
public class GameModelTest {


	@SuppressWarnings("static-access")
	@Test
	public void testAddGame() {
		GameModel model = GameModel.getInstance();
		Game object = new Game("Test Game", 
				"Test Description", 
				new ArrayList<PPRequirement>(), 
				true, 
				false);
		
		model.emptyModel();
		assertEquals(model.getSize(), 0);
		
		model.addGame(object);
		assertEquals(model.getSize(), 1);
		
		Game objectReturned = model.getElementAt(0);
		assertEquals(objectReturned.getName(), "Test Game");
		assertEquals(objectReturned.getDescription(), "Test Description");
		assertEquals(objectReturned.getCreator(), 
				ConfigManager.getInstance().getConfig().getUserName());
	}
	
	@Test
	public void testAddGameList(){
		GameModel model = GameModel.getInstance();
		model.emptyModel();
		assertEquals(0, model.getSize());
		Game game1 = new Game("Game", "With a name", new ArrayList<PPRequirement>(), false, true);
		Game game2 = new Game("Game2", "With a name2", new ArrayList<PPRequirement>(), true, true);
		Game game3 = new Game("Game3", "With a name3", new ArrayList<PPRequirement>(), false, false);
		Game[] gameList = new Game[3];
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
		
		Game game4 = new Game("An updated game", 
				"Overwriting an existing",
				new ArrayList<PPRequirement>(), 
				false, 
				true);
		game4.setIdentifier(game1.getIdentity());
		gameList[0] = game4;
		model.addGames(gameList);
		
		assertEquals(model.getElementAt(0).getName(), "An updated game");
		model.emptyModel();
		assertEquals(0, model.getSize());
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testUpdateGameListWithAllFields(){
		GameModel model = GameModel.getInstance();
		model.emptyModel();
		assertEquals(0, model.getSize());
		
		model.emptyModel();
		
		List<PPRequirement> reqs = new ArrayList<PPRequirement>();
		
		PPRequirement req = new PPRequirement("Requirement", "Description");
		
		req.addVote(new Vote("Steve", 10));
		req.addVote(new Vote("Paul", 27));
		req.addVote(new Vote("Erin", 10));
		
		reqs.add(req);
		Game game1 = new Game("Game", "With a name", reqs, false, true);
		for(PPRequirement r : game1.getRequirements()) {
			r.setGameID(game1.getIdentity());
		}
		
		
		assertEquals(3, req.getVoteCount());
		
		Game game2 = new Game("Game2", "With a name2", new ArrayList<PPRequirement>(), true, true);
		Game game3 = new Game("Game3", "With a name3", new ArrayList<PPRequirement>(), true, false);
		
		
		Game[] gameList = new Game[3];
		gameList[0] = game1;
		gameList[1] = game2;
		gameList[2] = game3;
		
		//FINISHED CONSTRUCTING DATA PRIOR TO PUSHING TO SERVER
		//IMITATES A INITIAL 'DATA' FROM THE SERVER
	
		List<PPRequirement> reqs2 = new ArrayList<PPRequirement>();
		
		reqs2.add(new PPRequirement("RequirementChanged", "DescriptionChanged"));
		
		PPRequirement req2 = new PPRequirement("Requirement With New Votes",
										"Description With New Votes");
		req2.setIdentity(req.getIdentity());
		
		req2.addVote(new Vote("Steve", 1));
		req2.addVote(new Vote("Paul", 2));
		req2.addVote(new Vote("Erin", 3));
		req2.addVote(new Vote("Steve", 4));
		
		reqs2.add(req2);
		
		assertEquals(2, reqs2.size());
		assertEquals(3, req2.getVoteCount());
		
		Game game1Changed = new Game("Game1 Changed", "DescriptionChanged", reqs2, true, false);
		for(PPRequirement r : game1Changed.getRequirements()) {
			r.setGameID(game1Changed.getIdentity());
		}
		game1Changed.setIdentifier(game1.getIdentity()); //Make this game 'equivalent' to the other
		game1Changed.makeComplete();
		
		assertEquals("Requirement With New Votes", game1Changed.getRequirements().get(1).getName());
		assertEquals(3, game1Changed.getRequirements().get(1).getVoteCount());
		
		model.updateGames(gameList); //Post the array to the model
		//MIMIC THE 'BUILD' FROM THE SERVER
		
		
		assertEquals(3, model.getSize());
		gameList[0] = game1Changed;			//Changes game1 to game1changed
		
		//SHOULD BE NO CHANGES TO THE MODEL AT THIS POINT!
		assertEquals(3, model.getSize());
		assertEquals(1, model.getElementAt(0).getRequirements().size());
		assertEquals(ConfigManager.getInstance().getConfig().getUserName(), model.getElementAt(0).getCreator());
		assertEquals("Game", model.getElementAt(0).getName());
		PPRequirement reqFromGame = model.getElementAt(0).getRequirements().get(0);
		assertEquals(3, reqFromGame.getVoteCount());
		
		model.updateGames(gameList);  //CHANGES TO THE MODEL
		//MIMICS CHANGES COMING FROM THE SERVER
		
		assertEquals(3, model.getSize());
		
		assertEquals("Game1 Changed", model.getElementAt(0).getName());
		assertEquals(ConfigManager.getInstance().getConfig().getUserName(), model.getElementAt(0).getCreator());
		assertTrue(model.getElementAt(0).isComplete());
		
		
		PPRequirement fromModel = model.getElementAt(0).getRequirements().get(1);
		assertEquals("RequirementChanged", fromModel.getName());
		assertEquals("DescriptionChanged", fromModel.getDescription());
		assertEquals(0, fromModel.getVoteCount());
		
		
		PPRequirement fromModel2 = model.getElementAt(0).getRequirements().get(0);
		assertEquals("Requirement With New Votes", fromModel2.getName());
		assertEquals("Description With New Votes", fromModel2.getDescription());
		assertEquals(3, fromModel2.getVoteCount());
		
		fromModel2.addVote(new Vote("Sally", 10));
		model.getElementAt(0).getRequirements().get(0).addVote(new Vote("Annie", 10));
		
		model.getElementAt(1).setDescription("A Description");
		assertTrue(model.getElementAt(1).hasChanged());
		model.getElementAt(1).notifyObservers();
		assertFalse(model.getElementAt(1).hasChanged());
		
		model.emptyModel();
		assertEquals(0, model.getSize());
	}

}
