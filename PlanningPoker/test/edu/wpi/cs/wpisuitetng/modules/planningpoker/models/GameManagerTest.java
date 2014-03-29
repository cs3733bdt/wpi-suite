/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.models;

import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.ConflictException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.MockData;

/**
 * @author jonathanleitschuh
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
		User admin = new User("admin", "admin", "1234", 27);
		admin.setRole(Role.ADMIN);
		testProject = new Project("test", "1");
		otherProject = new Project("other", "2");
		mockSsid = "abc123";
		adminSession = new Session(admin, testProject, mockSsid);
		
		existingUser = new User("steve", "steve", "1234", 2);
		game1 = new Game(1, "game 1", admin.getName(), false);
		
		game2 = new Game(2, "game 2", existingUser.getName(), true);
		
		defaultSession = new Session(existingUser, testProject,  mockSsid);
		game3 = new Game(3, "game 3", existingUser.getName(), false);
		
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
		assertEquals("game 3", created.getName());
		assertSame(db.retrieve(Game.class, "name", "game 3").get(0), created);
	}
	
	/**
	 * Ensures that a game can be retrived from the database
	 * @throws NotFoundException
	 */
	@Test
	public void testGetEntity() throws NotFoundException {
		Game[] gotten = manager.getEntity(defaultSession, "1");
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
	}
	
	@Test
	public void testDelete() throws WPISuiteException{
		assertSame(game1, db.retrieve(Game.class, "id", 1).get(0));
		assertTrue(manager.deleteEntity(adminSession, "1"));
		assertEquals(0, db.retrieve(Game.class, "id", 1).size());
	}

}
