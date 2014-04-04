/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Rolling Thunder
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.requirementmodels;


import java.util.ArrayList;
import java.util.UUID;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.vote.Vote;


/**
 * Basic Requirement class that contains the data to be stored for a Requirement
 * @author tianchanggu & jmwetzel
 *
 */
public class Requirement extends AbstractModel {
	/** the ID of the requirement */
	private int id;
	
	/** More secure version of identity. Guaranteed to be unique. This is used if
	 * the Requirement was generated in the planning poker module
	 */
	private UUID identity;

	/** the name of the requirement */
	private String name;

	/** a short description of the requirement */
	private String description;
	
	/** If this requirement came from the requirement manager module*/
	private boolean fromRequirementModule;
	
	/** list of votes for this requirement */
	private ArrayList<Vote> votes = new ArrayList<Vote>();

	
	
	/**
	 * The basic constructor for a game
	 * Sets all of the default values for a game class
	 * 
	 */
	public Requirement() {
		super();
		name = description = "";
		identity = UUID.randomUUID();
		
	}

	/**
	 * Construct a Requirement with required properties provided
	 * 
	 * @param id
	 *            The ID number of the requirement
	 * @param name
	 *            The name of the requirement
	 * @param description
	 *            A short description of the requirement
	 */
	// need to phase out supplying the ID
	//We took out int id -- Jeff, Tom, Jordan
	public Requirement(String name, String description, ArrayList<Vote> votes) {
		this();
		//this.id = id;
		this.name = name;
		this.description = description;
		this.votes = votes;
	}

	/**
	 * Getter for the id
	 * @return the id 
	 */
	public int getId() {
		return id;
	}

	/**
	 * Setter for the id
	 * @param id the id to set         
	 */
	public void setId(int id) {
		this.id = id;
		
	}

	/**
	 * getter for the name
	 * @return the name 
	 */
	public String getName() {
		return name;
	}

	/**
	 * setter for the name
	 * @param name the name to set          
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter for the description
	 * @return the description 
	 */
	public String getDescription() {
		return description;
	}
	

	/**
	 * Setter for the description
	 * @param description the description to set           
	 */
	public void setDescription(String description) {
		this.description=description;
	}

	
	/**
	 * getter for the votes
	 * @return the votes 
	 */
	public ArrayList<Vote> getVotes() {
		return votes;
	}

	/**
	 * setter for the votes
	 * @param votes the votes to set          
	 */
	public void setVotes(ArrayList<Vote> votes) {
		this.votes = votes;
	}
	
	/**
	 * Method save.
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#save()
	 */
	@Override
	public void save() {
		// TODO Auto-generated method stub

	}

	/**
	 * Method delete.
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#delete()
	 */
	@Override
	public void delete() {
		// TODO Auto-generated method stub

	}

	/**
	 * Method toJSON.
	
	
	 * @return String * @see edu.wpi.cs.wpisuitetng.modules.Model#toJSON() * @see edu.wpi.cs.wpisuitetng.modules.Model#toJSON()
	 */
	@Override
	/**This returns a Json encoded String representation of this requirement object.
	 * 
	 * @return a Json encoded String representation of this requirement
	 * 
	 */
	public String toJSON() {
		return new Gson().toJson(this, Requirement.class);
	}
	

	/**
	 * Returns an instance of Requirement constructed using the given
	 * Requirement encoded as a JSON string.
	 * 
	 * @param json
	 *            JSON-encoded Requirement to deserialize
	
	 * @return the Requirement contained in the given JSON */
	public static Requirement fromJson(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, Requirement.class);
	}
	
	/**
	 * Returns an array of Requirements parsed from the given JSON-encoded
	 * string.
	 * 
	 * @param json
	 *            string containing a JSON-encoded array of Requirement
	
	 * @return an array of Requirement deserialized from the given JSON string */
	public static Requirement[] fromJsonArray(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, Requirement[].class);
	}

	/**
	 * Method identify.
	 * @param o Object
	 * @return Boolean * @see edu.wpi.cs.wpisuitetng.modules.Model#identify(Object) * @see edu.wpi.cs.wpisuitetng.modules.Model#identify(Object)
	 */
	@Override
	public Boolean identify(Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method toString.
	
	
	 * @return String * @see edu.wpi.cs.wpisuitetng.modules.Model#toString() * @see edu.wpi.cs.wpisuitetng.modules.Model#toString()
	 */
	@Override
	public String toString() {
		String n = "\nName: " + name + 
				"\nDescription: " + description + '\n';
		return n;
	}


	/**
	 * Copies all of the values from the given requirement to this requirement.
	 * 
	 * @param toCopyFrom
	 *            the requirement to copy from.
	 */
	public void copyFrom(Requirement toCopyFrom) {
		this.description = toCopyFrom.description;
		this.name = toCopyFrom.name;
	}
}
