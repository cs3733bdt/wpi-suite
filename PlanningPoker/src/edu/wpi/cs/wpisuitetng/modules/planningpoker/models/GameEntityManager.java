/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.models;

import java.util.List;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.ConflictException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.EntityManager;
import edu.wpi.cs.wpisuitetng.modules.Model;

/**
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
		
		// Save the game in the database if possible, otherwise, throw an exception
		// We want the game to be associated with the project the user logged in to
		if (!db.save(newGame, s.getProject())) {
			throw new WPISuiteException();
		}
		
		// Return the newly created game
		return newGame;
	}

	@Override
	public Game[] getEntity(Session s, String id) throws NotFoundException,
			WPISuiteException {
		// The module does not support retrieving a specific game right now. If that feature
		// is implemented in the future and the user is allowed to retrieve specific games,
		// this method has to be updated
	    throw new WPISuiteException();
	}

	@Override
	public Game[] getAll(Session s) throws WPISuiteException {

		// Ask the database to retrieve all objects of the type
		// Game.
		// Passing a dummy Game lets the db know what type of object
		// to retrieve
		// Passing the project makes it only get messages from that project
		List<Model> messages = db.retrieveAll(new Game(-1, false), s.getProject());

		// Return the list of Games as an array
		return messages.toArray(new Game[0]);
	}

	@Override
	public Game update(Session s, String content) throws WPISuiteException {
		// This module does not allow Games to be modified, so throw an exception
	    throw new WPISuiteException();
	}

	@Override
	public void save(Session s, Game model) throws WPISuiteException {
	    // Save the given defect in the database
	    db.save(model);
	}

	@Override
	public boolean deleteEntity(Session s, String id) throws WPISuiteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String advancedGet(Session s, String[] args)
			throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAll(Session s) throws WPISuiteException {
		// TODO Auto-generated method stub

	}

	@Override
	public int Count() throws WPISuiteException {
		// Return the number of Games currently in the database.
	    return db.retrieveAll(new Game(-1, true)).size();
	}

	@Override
	public String advancedPut(Session s, String[] args, String content)
			throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String advancedPost(Session s, String string, String content)
			throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}

}
