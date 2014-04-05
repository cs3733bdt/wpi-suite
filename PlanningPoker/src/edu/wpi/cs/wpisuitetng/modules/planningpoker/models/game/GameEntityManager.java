/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.models.game;

import java.util.List;
import java.util.UUID;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.ConflictException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.UnauthorizedException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.EntityManager;
import edu.wpi.cs.wpisuitetng.modules.Model;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * 
 * Most of the code is referenced and refactored directly from the Requirement
 * Entity Manager module
 * 
 * @author dstapply
 * 
 */
public class GameEntityManager implements EntityManager<Game> {

	private Data db;

	public GameEntityManager(Data db) {
		this.db = db;
	}

	@Override
	public Game makeEntity(Session s, String content)
			throws BadRequestException, ConflictException, WPISuiteException {

		// Parse game from JSON
		final Game newGame = Game.fromJSON(content);

		// Save the game in the database if possible, otherwise, throw an
		// exception
		// We want the game to be associated with the project the user logged in
		// to
		if (!db.save(newGame, s.getProject())) {
			throw new WPISuiteException();
		}

		// Return the newly created game
		return newGame;
	}

	@Override
	public Game[] getEntity(Session s, String id) throws NotFoundException {
		final UUID intId;
		try{
			intId = UUID.fromString(id);
		} catch (IllegalArgumentException e){
			throw new NotFoundException();
		}
		
		if (id == "") {
			throw new NotFoundException();
		}
		Game[] games = null;
		try {
			games = db.retrieve(Game.class, "identity", intId, s.getProject())
					.toArray(new Game[0]);
		} catch (WPISuiteException e) {
			e.printStackTrace();
		}
		
		if (games.length < 1 || games[0] == null) {
			throw new NotFoundException();
		}
		return games;

	}

	@Override
	public Game[] getAll(Session s) throws WPISuiteException {

		// Ask the database to retrieve all objects of the type
		// Game.
		// Passing a dummy Game lets the db know what type of object
		// to retrieve
		// Passing the project makes it only get messages from that project
		List<Model> messages = db.retrieveAll(new Game(), s.getProject());

		// Return the list of Games as an array
		return messages.toArray(new Game[0]);
	}

	@Override
	public Game update(Session s, String content) throws WPISuiteException {
		// This module does not allow Games to be modified, so throw an
		// exception
		Game updatedGame = Game.fromJSON(content);

		List<Model> oldGame = db.retrieve(Game.class, "identity",
				updatedGame.getIdentity(), s.getProject());
		if (oldGame.size() < 1 || oldGame.get(0) == null) {
			throw new BadRequestException("Requirement with ID does not exist.");
		}

		Game existingGame = (Game) oldGame.get(0);
		existingGame.copyFrom(updatedGame);

		if (!db.save(existingGame, s.getProject())) {
			throw new WPISuiteException();
		}

		return existingGame;
	}

	/**
	 * Saves the game into the database for the current session
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#save(edu.wpi.cs.wpisuitetng.Session,
	 *      edu.wpi.cs.wpisuitetng.modules.Model)
	 */
	@Override
	public void save(Session s, Game model) {
		// Save the given game in the database
		db.save(model, s.getProject());
	}

	/**
	 * Deletes a requirement from the database
	 * 
	 * @param s
	 *            the session
	 * @param id
	 *            the id of the requirement to delete
	 * 
	 * @return true if the deletion was successful * @throws WPISuiteException * @throws
	 *         WPISuiteException * @throws WPISuiteException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteEntity(Session,
	 *      String)
	 */

	@Override
	public boolean deleteEntity(Session s, String id) throws WPISuiteException {
		ensureRole(s, Role.ADMIN);
		try{
			return (db.delete(getEntity(s, id)[0]) != null) ? true : false;
		} catch (IllegalArgumentException e){
			throw new NotFoundException();
		}
	}

	@Override
	public String advancedGet(Session s, String[] args)
			throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAll(Session s) throws WPISuiteException {
		ensureRole(s, Role.ADMIN);
		db.deleteAll(new Game(), s.getProject());
	}

	@Override
	public int Count() throws WPISuiteException {
		// Return the number of Games currently in the database.
		return db.retrieveAll(new Game()).size();
	}

	@Override
	public String advancedPut(Session s, String[] args, String content)
			throws WPISuiteException {
		// TODO Auto-generated method stub
		throw new WPISuiteException();
	}

	@Override
	public String advancedPost(Session s, String string, String content)
			throws WPISuiteException {
		// TODO Auto-generated method stub
		throw new WPISuiteException();
	}

	/**
	 * Ensures that a user is of the specified role
	 * 
	 * @param session
	 *            the session
	 * @param role
	 *            the role being verified
	 * 
	 * @throws WPISuiteException
	 *             user isn't authorized for the given role
	 */
	private void ensureRole(Session session, Role role)
			throws WPISuiteException {
		User user = (User) db.retrieve(User.class, "username",
				session.getUsername()).get(0);
		if (!user.getRole().equals(role)) {
			throw new UnauthorizedException();
		}
	}

}
