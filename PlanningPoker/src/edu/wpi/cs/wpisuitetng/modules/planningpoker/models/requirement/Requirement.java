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
package edu.wpi.cs.wpisuitetng.modules.planningpoker.models.requirement;


import java.util.ArrayList;
import java.util.UUID;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.game.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.observers.ObservableModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.vote.Vote;


/**
 * Basic Requirement class that contains the data to be stored for a Requirement
 * @author tianchanggu & jmwetzel
 *
 */
public class Requirement extends ObservableModel {
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

	/** boolean for whether the requirement has been voted on by all users */
	private boolean complete = false;
	
	
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
	public Requirement(String name, String description) {
		this();
		//this.id = id;
		this.name = name;
		this.description = description;
	}

	/**
	 * Getter for the id
	 * @return the id 
	 */
	public int getId() {
		return id;
	}
	
	public UUID getIdentity(){
		return identity;
	}
	
	/**
	 * 
	 * @param id
	 */
	public void setId(int id){
		this.delayChange();
		fromRequirementModule = true;
		this.id = id;
	}
	
	public void setIdentity(UUID identity){
		this.delayChange();
		fromRequirementModule = false;
		this.identity = identity;
	}


	/**
	 * getter for the name
	 * @return the name 
	 */
	public String getName() {
		return name;
	}

	/**
	 * Getter for the description
	 * @return the description 
	 */
	public String getDescription() {
		return description;
	}

	
	/**
	 * getter for the votes
	 * @return the votes 
	 */
	public ArrayList<Vote> getVotes() {
		return votes;
	}

	/**
	 * adds a vote to the votes ArrayList
	 * @param votes the votes to set          
	 */
	public void addVote(Vote vote) {
		this.delayChange();
		for(int i = 0; i < votes.size(); i++) {
			if(vote.getUsername().equals(votes.get(i).getUsername())) {		//Check to see if this person has voted
				votes.get(i).setVoteNumber(vote.getVoteNumber());			//If they have update their vote to the new number
				this.setChanged();											//Says that the requirement has changed
				this.notifyObservers(votes.get(i));							//Run update in the game class
				return;														//Exit this class
			}
		}
		this.votes.add(vote);
		if(this.getProject() != null){
			if(votes.size() == this.getProject().getTeam().length) {
				this.setComplete();
			}
		} else {
			System.err.println("THE PROJECT IN THE REQUIREMENT WAS NULL: ADD VOTE METHOD");
		}
		
		this.setChanged();
		this.notifyObservers(vote);
	}
	
	/**
	 * Method save.
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#save()
	 */
	@Override
	public void save() {
		// TODO Auto-generated method stub

	}
	
	public void setComplete() {
		this.delayChange();
		this.complete = true;
		this.setChanged();
		this.notifyObservers();
	}

	public String displayComplete(){
		if(this.complete){
			return "*";
		}
		else{
			return Integer.toString(this.getVotes().size());
		}
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
		if(o == null){
			return false;
		}
		if(o.getClass() != this.getClass()){
			return false;
		}
		Requirement comp = (Requirement)o;
		
		if(fromRequirementModule){
			if(this.id != comp.id){
				return false;
			}
		} else {
			if(!this.identity.equals(comp.identity)){
				return false;
			}
		}
		return true;
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
	
	public int getVoteCount(){
		return votes.size();
	}


	/**
	 * Copies all of the values from the given requirement to this requirement.
	 * 
	 * @param toCopyFrom
	 *            the requirement to copy from.
	 */
	public boolean copyFrom(Requirement toCopyFrom) {
		boolean wasChanged = false;
		if(!this.description.equals(toCopyFrom.description)){
			this.description = toCopyFrom.description;
			wasChanged = true;
		}
		if(!this.name.equals(toCopyFrom.name)){
			this.name = toCopyFrom.name;
			wasChanged = true;
		}
		if(!this.votes.equals(toCopyFrom.votes)){
			this.votes = toCopyFrom.votes;
			wasChanged = true;
		}
		return wasChanged; //TODO THIS IS A PLACE HOLDER! THIS MUST CHECK TO MAKE SURE THAT CHANGES ARE ACTUALLY MADE
	}
	
	/**
	 * hold the code while the game model is updating
	 * prevent race-time condition for fields setting/overriding
	 */
	private void delayChange(){
		while(GameModel.getInstance().serverUpdating()){}
	}
}
