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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.models;

import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.NotImplementedException;
import edu.wpi.cs.wpisuitetng.exceptions.UnauthorizedException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.EntityManager;
import edu.wpi.cs.wpisuitetng.modules.Model;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * This is the entity manager for the Requirement in the
 * RequirementManager module.
 * @author tianchanggu
 *
 */
public class PPRequirementEntityManager implements EntityManager<PPRequirement> {

	/** The database */
	Data db;
	private static final Logger logger = Logger.getLogger(PPRequirementEntityManager.class
			.getName());
	/**
	 * Constructs the entity manager. This constructor is called by
	 * {@link edu.wpi.cs.wpisuitetng.ManagerLayer#ManagerLayer()}. To make sure
	 * this happens, be sure to place add this entity manager to the map in
	 * the ManagerLayer file.
	 * 
	 * @param db a reference to the persistent database
	 */
	public PPRequirementEntityManager(Data db) {
		this.db = db; 
	}

	/**
	 * Saves a Requirement when it is received from a client
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#makeEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public PPRequirement makeEntity(Session s, String content) throws WPISuiteException {
		final PPRequirement newRequirement = PPRequirement.fromJSON(content);
		if(!db.save(newRequirement, s.getProject())) {
			throw new WPISuiteException();
		}
		return newRequirement;
	}
	
	/**
	 * Retrieves a single requirement from the database
	 * @param s the session
	 * @param id the id number of the requirement to retrieve
	 * @return the requirement matching the given id * 
	 * @throws NotFoundException * @throws NotFoundException * @throws NotFoundException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getEntity(Session, String)
	 */
	@Override
	public PPRequirement[] getEntity(Session s, String id) throws NotFoundException {
		final UUID intId;
		try{
			intId = UUID.fromString(id);
		} catch (IllegalArgumentException e){
			throw new NotFoundException();
		}
		
		if (id.equals("")) {
			throw new NotFoundException();
		}
		
		PPRequirement[] requirements = null;
		try {
			requirements = db.retrieve(PPRequirement.class, 
					"identity", intId, s.getProject()).toArray(new PPRequirement[0]);
		} catch (WPISuiteException e) {
			e.printStackTrace();
		}
		if(requirements.length < 1 || requirements[0] == null) {
			throw new NotFoundException();
		}
		return requirements;
	}

	/**
	 * Retrieves all requirements from the database
	 * @param s the session
	 * @return array of all stored requirements * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getAll(Session) * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getAll(Session) * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getAll(Session)
	 */
	@Override
	public PPRequirement[] getAll(Session s) {
		return db.retrieveAll(new PPRequirement(), s.getProject()).toArray(new PPRequirement[0]);
	}

	/**
	 * Saves a data model to the database
	 * @param s the session
	 * @param model the model to be saved
	 */
	@Override
	public void save(Session s, PPRequirement model) {
		db.save(model, s.getProject());
	}
	
	/**
	 * Ensures that a user is of the specified role
	 * @param session the session
	 * @param role the role being verified
	 * @throws WPISuiteException user isn't authorized for the given role
	 */
	private void ensureRole(Session session, Role role) throws WPISuiteException {
		User user = (User) db.retrieve(User.class, "username", session.getUsername()).get(0);
		if(!user.getRole().equals(role)) {
			throw new UnauthorizedException();
		}
	}
	
	/**
	 * Deletes a requirement from the database
	 * @param s the session
	 * @param id the id of the requirement to delete
	 * @return true if the deletion was successful * 
	 * @throws WPISuiteException * @throws WPISuiteException * @throws WPISuiteException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteEntity(Session, String)
	 */
	@Override
	public boolean deleteEntity(Session s, String id) throws WPISuiteException {
		ensureRole(s, Role.ADMIN);
		return (db.delete(getEntity(s, id)[0]) != null) ? true : false;
	}
	
	/**
	 * Deletes all requirements from the database
	 * @throws WPISuiteException * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteAll(Session) * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteAll(Session)
	 */
	@Override
	public void deleteAll(Session s) throws WPISuiteException {
		ensureRole(s, Role.ADMIN);
		db.deleteAll(new PPRequirement(), s.getProject());
	}
	
	/**
	 * Returns the number of requirements in the database
	 * @return number of requirements stored * 
	 * @throws WPISuiteException * @throws WPISuiteException * @throws WPISuiteException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#Count()
	 */
	@Override
	public int Count() throws WPISuiteException {
		return db.retrieveAll(new PPRequirement()).size();
	}

	/**
	 * Method update.
	 * @param session Session
	 * @param content String
	 * @return Requirement * @throws WPISuiteException * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#update(Session, String) * 
	 * @throws WPISuiteException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#update(Session, String)
	 */
	@Override
	public PPRequirement update(Session session, String content) throws WPISuiteException {
		
		PPRequirement updatedRequirement = PPRequirement.fromJSON(content);
		/*
		 * Because of the disconnected objects problem in db4o, we can't just save Requirements.
		 * We have to get the original defect from db4o, copy properties from updatedRequirement,
		 * then save the original Requirement again.
		 */
		List<Model> oldRequirements = db.retrieve(PPRequirement.class, 
				"identity", updatedRequirement.getIdentity(), session.getProject());
		if(oldRequirements.size() < 1 || oldRequirements.get(0) == null) {
			throw new BadRequestException("Requirement with ID does not exist.");
		} else if (oldRequirements.size() > 1){
			logger.log(Level.WARNING,"There are multiple requirements with this id on the server");
		} else{
			logger.log(Level.INFO,"Update should be sucsessful");
		}
				
		PPRequirement existingRequirement = (PPRequirement)oldRequirements.get(0);		

		// copy values to old requirement and fill in our changeset appropriately
		existingRequirement.copyFrom(updatedRequirement);
		
		if(!db.save(existingRequirement, session.getProject())) {
			throw new WPISuiteException();
		}
		
		return existingRequirement;
	}

	/**
	 * Method advancedGet.
	 * @param arg0 Session
	 * @param arg1 String[]
	 * @return String * @throws NotImplementedException * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedGet(Session, String[]) * 
	 * @throws NotImplementedException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedGet(Session, String[])
	 */
	@Override
	public String advancedGet(Session arg0, String[] arg1) throws NotImplementedException {
		throw new NotImplementedException();
	}

	/**
	 * Method advancedPost.
	 * @param arg0 Session
	 * @param arg1 String
	 * @param arg2 String
	 * @return String * @throws NotImplementedException * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedPost(Session, String, String) * 
	 * @throws NotImplementedException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedPost(Session, String, String)
	 */
	@Override
	public String advancedPost(Session arg0, String arg1, String arg2) 
			throws NotImplementedException {
		throw new NotImplementedException();
	}

	/**
	 * Method advancedPut.
	 * @param arg0 Session
	 * @param arg1 String[]
	 * @param arg2 String
	 * @return String * @throws NotImplementedException * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedPut(Session, String[], String) * 
	 * @throws NotImplementedException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedPut(Session, String[], String)
	 */
	@Override
	public String advancedPut(Session arg0, String[] arg1, String arg2) 
			throws NotImplementedException {
		throw new NotImplementedException();
	}

}
