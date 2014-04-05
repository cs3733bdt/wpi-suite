package edu.wpi.cs.wpisuitetng.modules.planningpoker.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.MockNetwork;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.game.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.game.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.requirement.Requirement;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.vote.Vote;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

public class GameTest {

	@Before
	public void setUp() throws Exception {
		Network.initNetwork(new MockNetwork());
		Network.getInstance().setDefaultNetworkConfiguration(
				new NetworkConfiguration("http://wpisuitetng"));
		GameModel.getInstance().emptyModel();
	}

	@Test
	public void testJSONConversion() {
		ArrayList<Requirement> reqList = new ArrayList<Requirement>();
		
		Game object = new Game("Game A", "Test description", "Steve", reqList, true, false);
		Requirement req = new Requirement("Test Requirement", "Test description");
		
		reqList.add(req);
		//Test existing object
		assertEquals(object.getName(), "Game A");
		assertEquals(object.getDescription(), "Test description");
		assertEquals(object.getCreator(), "Steve");
		assertFalse(object.getRequirements().isEmpty());
		assertEquals(object.getRequirements().get(0), req);
		assertEquals(object.isComplete(), false);
		assertEquals(object.doesUseCards(), false);
		
		String jsonMessage = object.toJSON(); //Test conversion to jSON
		Game newObject = Game.fromJSON(jsonMessage); //Test conversion back from jSON
		
		assertEquals(newObject.getName(), "Game A");
		assertEquals(newObject.getDescription(), "Test description");
		assertEquals(newObject.getCreator(), "Steve");
		assertFalse(newObject.getRequirements().isEmpty());
		//assertTrue(newObject.getRequirements().contains(req));
		assertEquals(newObject.isComplete(), false);
		assertEquals(newObject.doesUseCards(), false);
		
	}
	
	@Test
	public void testCopyContentsOfOneGameToAnother() {
		ArrayList<Requirement> reqList = new ArrayList<Requirement>();
		
		Game game1 = new Game("Game A", "Test description", "Steve", reqList, true, false);
		Game game2 = new Game();
		
		game2.copyFrom(game1);
		
		assertEquals(game2.getName(), "Game A");
		assertEquals(game2.getDescription(), "Test description");
		assertEquals(game2.getCreator(), "Steve");
		assertTrue(game2.getRequirements().isEmpty());
		assertEquals(game2.isComplete(), false);
		assertEquals(game2.doesUseCards(), false);
		
	}

}
