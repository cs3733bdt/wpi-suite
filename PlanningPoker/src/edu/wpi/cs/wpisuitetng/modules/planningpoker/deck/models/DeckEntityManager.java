/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Team Bobby Drop Tables
 *******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.deck.models;

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
 * This is the entity manager for the Game in the PlanningPoker module
 * 
 * @author dstapply
 * 
 */
public class DeckEntityManager implements EntityManager<Deck> {

	private final Data db;

	/**
	 * constructor the the GameEntityManager class
	 * @param db the data to be added to the database
	 */
	public DeckEntityManager(Data db) {
		this.db = db;
	}

	@Override
	public Deck makeEntity(Session s, String content)
			throws BadRequestException, ConflictException, WPISuiteException {

		// Parse game from JSON
		final Deck newDeck = Deck.fromJSON(content);

		// Save the game in the database if possible, otherwise, throw an
		// exception
		// We want the game to be associated with the project the user logged in
		// to
		if (!db.save(newDeck, s.getProject())) {
			throw new WPISuiteException();
		}

		// Return the newly created game
		return newDeck;
	}

	@Override
	public Deck[] getEntity(Session s, String id) throws NotFoundException {
		final UUID intId;
		try{
			intId = UUID.fromString(id);
		} catch (IllegalArgumentException e){
			throw new NotFoundException();
		}
		
		if (id.equals("")) {
			throw new NotFoundException();
		}
		Deck[] decks = null;
		try {
			decks = db.retrieve(Deck.class, "identity", intId, s.getProject())
					.toArray(new Deck[0]);
		} catch (WPISuiteException e) {
			e.printStackTrace();
		}
		
		if (decks.length < 1 || decks[0] == null) {
			throw new NotFoundException();
		}
		return decks;

	}

	@Override
	public Deck[] getAll(Session s) throws WPISuiteException {

		// Ask the database to retrieve all objects of the type
		// Game.
		// Passing a dummy Game lets the db know what type of object
		// to retrieve
		// Passing the project makes it only get messages from that project
		List<Model> messages = db.retrieveAll(new Deck(), s.getProject());

		// Return the list of Games as an array
		return messages.toArray(new Deck[0]);
	}

	@Override
	public Deck update(Session s, String content) throws WPISuiteException {
		// This module does not allow Games to be modified, so throw an
		// exception
		Deck updatedDeck = Deck.fromJSON(content);

		List<Model> oldDeck = db.retrieve(Deck.class, "identity",
				updatedDeck.getIdentity(), s.getProject());
		if (oldDeck.size() < 1 || oldDeck.get(0) == null) {
			throw new BadRequestException("Requirement with ID does not exist.");
		}

		Deck existingDeck = (Deck) oldDeck.get(0);
		existingDeck.copyFrom(updatedDeck);

		if (!db.save(existingDeck, s.getProject())) {
			throw new WPISuiteException();
		}

		return existingDeck;
	}

	/**
	 * Saves the game into the database for the current session
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#save(edu.wpi.cs.wpisuitetng.Session,
	 *      edu.wpi.cs.wpisuitetng.modules.Model)
	 */
	@Override
	public void save(Session s, Deck model) {
		// Save the given game in the database
		db.save(model, s.getProject());
	}

	/**
	 * Deletes a requirement from the database
	 * 
	 * @param s the session
	 * @param id the id of the requirement to delete 
	 * @return true if the deletion was successful 
	 * @throws WPISuiteException 
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
		return null;
	}

	@Override
	public void deleteAll(Session s) throws WPISuiteException {
		ensureRole(s, Role.ADMIN);
		db.deleteAll(new Deck(), s.getProject());
	}

	@Override
	public int Count() throws WPISuiteException {
		// Return the number of Games currently in the database.
		return db.retrieveAll(new Deck()).size();
	}

	@Override
	public String advancedPut(Session s, String[] args, String content)
			throws WPISuiteException {
		throw new WPISuiteException();
	}

	@Override
	public String advancedPost(Session s, String string, String content)
			throws WPISuiteException {
		throw new WPISuiteException();
	}

	/**
	 * Ensures that a user is of the specified role
	 * 
	 * @param session the session
	 * @param role the role being verified
	 * 
	 * @throws WPISuiteException user isn't authorized for the given role
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
