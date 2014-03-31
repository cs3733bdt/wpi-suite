/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.models;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

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

}
