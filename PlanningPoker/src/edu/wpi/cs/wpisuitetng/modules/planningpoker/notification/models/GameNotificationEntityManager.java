/*******************************************************************************
* Copyright (c) 2012-2014 -- WPI Suite
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.notification.models;

import java.util.List;
import java.util.UUID;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.ConflictException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.EntityManager;
import edu.wpi.cs.wpisuitetng.modules.Model;

public class GameNotificationEntityManager implements EntityManager<GameNotification> {
	
	private final Data db;
	
	public GameNotificationEntityManager(Data db) {
		this.db = db;
	}

	@Override
	public GameNotification makeEntity(Session s, String content)
			throws BadRequestException, ConflictException, WPISuiteException {
		// Parse GameNotification from JSON
		final GameNotification newGN = GameNotification.fromJSON(content);

		// Save the GameNotification in the database if possible, otherwise, throw an
		// exception
		// We want the GameNotification to be associated with the project the user logged in
		// to
		if (!db.save(newGN, s.getProject())) {
			throw new WPISuiteException();
		}

		// Return the newly created GameNotification
		return newGN;
	}

	@Override
	public GameNotification[] getEntity(Session s, String id) throws NotFoundException,
			WPISuiteException {
		final UUID intId;
		try {
			intId = UUID.fromString(id);
		} catch (IllegalArgumentException e){
			throw new NotFoundException();
		}
		
		if (id.equals("")) {
			throw new NotFoundException();
		}
		GameNotification[] gns = null;
		try {
			gns = db.retrieve(GameNotification.class, "gameIdentity", intId, s.getProject())
					.toArray(new GameNotification[0]);
		} catch (WPISuiteException e) {
			e.printStackTrace();
		}
		
		if (gns.length < 1 || gns[0] == null) {
			throw new NotFoundException();
		}
		return gns;

	}

	@Override
	public GameNotification[] getAll(Session s) throws WPISuiteException {
		// Ask the database to retrieve all objects of the type
		// GameNotification.
		// Passing a dummy GameNotification lets the db know what type of object
		// to retrieve
		// Passing the project makes it only get messages from that project
		List<Model> messages = db.retrieveAll(new GameNotification(new UUID(0,0)), s.getProject());

		// Return the list of GameNotification as an array
		return messages.toArray(new GameNotification[0]);
	}

	@Override
	public GameNotification update(Session s, String content) throws WPISuiteException {
		// This module does not allow GameNotification to be modified, so throw an
		// exception
		GameNotification updatedGN = GameNotification.fromJSON(content);

		List<Model> oldGN = db.retrieve(GameNotification.class, "gameIdentity",
				updatedGN.getIdentity(), s.getProject());
		if (oldGN.size() < 1 || oldGN.get(0) == null) {
			throw new BadRequestException("GameNotification with ID does not exist.");
		}
		
		GameNotification existingGN = (GameNotification) oldGN.get(0);
		
		existingGN.copyFrom(updatedGN);

		if (!db.save(existingGN, s.getProject())) {
			throw new WPISuiteException();
		}

		return existingGN;
	}

	@Override
	public void save(Session s, GameNotification model) throws WPISuiteException {
		// Save the given GameNotification in the database
		db.save(model, s.getProject());
	}

	@Override
	public boolean deleteEntity(Session s, String id) throws WPISuiteException {
		try {
			return (db.delete(getEntity(s, id)[0]) != null) ? true : false;
		} catch (IllegalArgumentException e) {
			throw new NotFoundException();
		}
	}

	@Override
	public String advancedGet(Session s, String[] args)
			throws WPISuiteException {
		return null;
	}

	@Override
	public void deleteAll(Session s) throws WPISuiteException {
		db.deleteAll(new GameNotification(new UUID(0,0)), s.getProject());
	}

	@Override
	public int Count() throws WPISuiteException {
		// Return the number of GameNotifications currently in the database.
		return db.retrieveAll(new GameNotification(new UUID(0,0))).size();
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

}
