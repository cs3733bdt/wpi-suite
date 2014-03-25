/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.models;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.ConflictException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.EntityManager;

/**
 * @author dstapply
 *
 */
public class GameEntityManager implements EntityManager<Game>{

	private Data db;

	public GameEntityManager(Data db) {
		this.db = db;
	}
	
	@Override
	public Game makeEntity(Session s, String content)
			throws BadRequestException, ConflictException, WPISuiteException {
		// TODO Auto-generated method stub
		
		final Game newGame = Game.fromJSON(content);

		if (!db.save(newGame, s.getProject())) {
	        throw new WPISuiteException();
	    }
		
		return newGame;
	}

	@Override
	public Game[] getEntity(Session s, String id) throws NotFoundException,
			WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Game[] getAll(Session s) throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Game update(Session s, String content) throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Session s, Game model) throws WPISuiteException {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		return 0;
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
