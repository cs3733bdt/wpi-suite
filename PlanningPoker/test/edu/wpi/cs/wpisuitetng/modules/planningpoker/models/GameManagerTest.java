/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.util.HashSet;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.UnauthorizedException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.MockData;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.MockNetwork;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.game.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.game.GameEntityManager;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

/**
 * Used to test the Game Manager's functionality
 * 
 * @author jonathanleitschuh
 * @author phbaumann
 *
 */
public class GameManagerTest {
	MockData db;
	User existingUser;
	Game game1;
	Session defaultSession;
	String mockSsid;
	GameEntityManager manager;
	Game game2;
	User paul;
	Game goodUpdatedGame;
	Session adminSession;
	Project testProject;
	Project otherProject;
	Game game3;
	

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		//Set up the network
		Network.initNetwork(new MockNetwork());
		Network.getInstance().setDefaultNetworkConfiguration(
				new NetworkConfiguration("http://wpisuitetng"));
		
		//Set up the data
		User admin = new User("admin", "admin", "1234", "", 27);
		admin.setRole(Role.ADMIN);
		testProject = new Project("test", "1");
		otherProject = new Project("other", "2");
		mockSsid = "abc123";
		adminSession = new Session(admin, testProject, mockSsid);
		
		existingUser = new User("steve", "steve", "1234", "", 2);
		
		game1 = new Game("Game 1", "First", admin.getName(), null, false, false);
		game2 = new Game("Game 2", "Second", existingUser.getName(), null, true, false);
		game3 = new Game("Game 3", "Third", existingUser.getName(), null, false, false);
		
		defaultSession = new Session(existingUser, testProject,  mockSsid);

		db = new MockData(new HashSet<Object>());
		
		db.save(game1, testProject);
		db.save(existingUser);
		db.save(game2, otherProject);
		db.save(admin);
		manager = new GameEntityManager(db);
		
	}

	/**
	 * Stores a new game and ensure that the correct data was stored
	 * @throws WPISuiteException
	 */
	@Test
	public void testMakeEntity() throws WPISuiteException {
		Game created = manager.makeEntity(defaultSession, game3.toJSON());
		assertEquals("Game 3", created.getName());
		assertSame(db.retrieve(Game.class, "name", "Game 3").get(0), created);
	}
	

	/**
	 * Ensures that a game can be retrived from the database
	 * @throws NotFoundException
	 */
	@Test
	public void testGetEntity() throws NotFoundException {
		Game[] gotten = manager.getEntity(defaultSession, game1.getIdentity().toString());
		assertSame(game1, gotten[0]);
		
	}
	
	/**
	 * Ensures a NotFoundException is thrown when trying to retrive an invalid requirement
	 * 
	 * @throws NotFoundException
	 */
	@Test(expected = NotFoundException.class)
	public void testGetBadId() throws NotFoundException {
		manager.getEntity(defaultSession, "-1");
		fail("Should have thrown an exception");
	}
	
	/**
	 * Ensures that games can be deleted
	 * 
	 * @throws WPISuiteException
	 */
	@Test
	public void testDelete() throws WPISuiteException{
		UUID id = game1.getIdentity();
		assertSame(game1, db.retrieve(Game.class, "identity", id).get(0));
		assertTrue(manager.deleteEntity(adminSession, id.toString()));
		assertEquals(0, db.retrieve(Game.class, "identity", id).size());
	}
	
	/**
	 * Ensures that an exception is thrown when trying to delete an invalid game
	 * 
	 * @throws WPISuiteException
	 */
	@Test(expected = NotFoundException.class)
	public void testDeleteMissing() throws WPISuiteException{
		manager.deleteEntity(adminSession, "404");
		fail("The code should have thrown an exception");
	}
	
	/**
	 * Ensures an UnauthorizedException is thrown when trying to delete an entity while not authorized
	 * @throws WPISuiteException
	 */
	@Test(expected=UnauthorizedException.class)
	public void testDeleteNotAllowed() throws WPISuiteException{
		manager.deleteEntity(defaultSession, Integer.toString(game1.getId()));
		fail("This should have thrown an Unauthorized access exception");
	}
	
	/**
	 * Ensures that the deletion of all games works properly
	 * @throws WPISuiteException
	 */
	@Test
	public void testDeleteAll() throws WPISuiteException{
		Game anotherGame = new Game();
		manager.makeEntity(defaultSession, anotherGame.toJSON());
		assertEquals(2, db.retrieveAll(new Game(), testProject).size());
		manager.deleteAll(adminSession);
		assertEquals(0, db.retrieveAll(new Game(), testProject).size());
		//Other games should still be around
		assertEquals(1, db.retrieveAll(new Game(), otherProject).size());
	}
	
	
	/**
	 * Tests that no exceptions are thrown when you remove all games from an empty session
	 * @throws WPISuiteException
	 */
	@Test
	public void testDeleteAllWhenEmpty() throws WPISuiteException{
		manager.deleteAll(adminSession);
		manager.deleteAll(adminSession);
		// no exceptions get thrown
	}
	
	@Test
	public void testCount() throws WPISuiteException{
		assertEquals(2, manager.Count());
	}
	
	@Test
	public void testGetAll() throws WPISuiteException{
		manager.save(defaultSession, game2);
		Game returnedGameList [] = manager.getAll(defaultSession);
		assertEquals(2, returnedGameList.length);
	}
	


}
