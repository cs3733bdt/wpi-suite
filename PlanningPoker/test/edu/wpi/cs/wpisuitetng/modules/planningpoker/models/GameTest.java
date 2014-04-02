package edu.wpi.cs.wpisuitetng.modules.planningpoker.models;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.MockNetwork;
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
		Game object = new Game(4, "Game A", "Steve", false);
		//Test existing object
		assertEquals(object.getName(), "Game A");
		assertEquals(object.getId(), 4);
		
		String jsonMessage = object.toJSON(); //Test conversion to jSON
		Game newObject = Game.fromJSON(jsonMessage); //Test conversion back from jSON
		
		assertEquals(newObject.getId(), 4);
		assertEquals(newObject.getName(), "Game A");
	}

}
