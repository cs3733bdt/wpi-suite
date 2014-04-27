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


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.abstractmodel.ObservableModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.vote.models.Vote;


/**
 * Basic Requirement class that contains the data to be stored for a Requirement
 * @author tianchanggu & jmwetzel
 *
 */
public class PPRequirement extends ObservableModel {
	/**
	 * The ID of the requirement from the Requirement Manager
	 * Only used if the requirement is from the Requirement Manager
	 */
	private int id;
	
	/** 
	 * More secure version of identity. Guaranteed to be unique. This is used if
	 * the Requirement was generated in the planning poker module
	 */
	private UUID identity;

	/** the name of the requirement */
	private String name;

	/** a short description of the requirement */
	private String description;

	
	/** If this requirement came from the requirement manager module*/
	private boolean fromRequirementModule = false;
	
	/** list of votes for this requirement */
	private List<Vote> votes = new ArrayList<Vote>(){
		public boolean equals(Object o){
			if(this == o){
				return true;
			}
			if(o == null){
				return false;
			}
			if(getClass() != o.getClass()){
				return false;
			}
			@SuppressWarnings("unchecked")
			ArrayList<Vote> comp = (ArrayList<Vote>)o;
			for(Vote v : this){
				if(!comp.contains(v)){
					return false;
				}
			}
			for(Vote v : comp){
				if(!this.contains(v)){
					return false;
				}
			}
			return true;
		}
	};

	/** boolean for whether the requirement has been voted on by all users */
	private boolean complete = false;
	/** The final estimate for this requirement. This is -1 if a final estimate has not been submitted yet. */
	private int finalEstimate = -1;
	/**
	 * The basic constructor for a game
	 * Sets all of the default values for a game class
	 * 
	 */
	public PPRequirement() {
		name = description = "";
		identity = UUID.randomUUID();
	}

	/**
	 * Construct a Requirement with required properties provided
	 * 
	 * @param name The name of the requirement
	 * @param description A short description of the requirement
	 */
	public PPRequirement(String name, String description) {
		this();
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
	
	/**
	 * Getter for fromRequirementModule
	 * @return True if the requirement is from the
	 * Requirement Manager
	 */
	public boolean getFromRequirementModule() {
		return fromRequirementModule;
	}
	
	/**
	 * Checks if this requirement exists in list of requirements passed
	 * **Specifically for requirements from requirement manager
	 * @param requirements List of requirements to check
	 * @return True if the requirement exists in the list
	 */
	public boolean existsIn(List<PPRequirement> requirements) {
		for (PPRequirement r: requirements) {
			if (r.getFromRequirementModule() && id == r.getId())
				return true;
		}
		return false;
	}
	
	/**
	 * getter for the UUID of the requirement
	 * @return UUID of the requirement
	 */
	public UUID getIdentity(){
		return identity;
	}
	
	/**
	 * sets the id of the requirement
	 * @param id value to set the id to
	 */
	public void setIdPlusOne(int id){
		delayChange("setIdPlusOne");
		makeChanged();
		fromRequirementModule = true;
		// Make Id one more than the id in the 
		// Requirement Manager
		this.id = id + 1;
	}
	
	public void setId(int id){
		delayChange("setId");
		fromRequirementModule = true;
		makeChanged();
		// Make Id one more than the id in the 
		// Requirement Manager
		this.id = id;
	}
	
	/**
	 * sets the identity of the requirement
	 * @param identity value to set the identity to
	 */
	public void setIdentity(UUID identity){
		delayChange("setUUID");
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
	 * setter for the name
	 * @param name
	 */
	public void setName(String name){
		this.name = name;
	}

	/**
	 * getter for the description
	 * @return the description 
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * setter for the description
	 * @param description
	 */
	public void setDescription(String description){
		this.description = description;
	}

	/**
	 * getter for the votes
	 * @return the votes 
	 */
	public List<Vote> getVotes() {
		return votes;
	}


	/**
	 * adds a vote to the votes ArrayList
	 * @param vote the votes to set          
	 */
	public void addVote(Vote vote) {
		delayChange("addVote");	//Holds the code until the server is finished re-populating the model
		addNoDelay(vote);
		notifyObservers(vote);
	}
	
	private void addNoDelay(Vote vote){
		boolean found = false;
		for(Vote v : votes) {
			if(vote.getUsername().equals(v.getUsername())) {	//Has person voted?
				v.setVoteNumber(vote.getVoteNumber());		//Update their vote
				makeChanged();
				found = true;												//Exit this class
			}
		}
		if(!found){
			votes.add(vote);
			makeChanged();
		}
		if(getProject() != null){
			if(votes.size() == getProject().getTeam().length) {
				makeComplete();
				makeChanged();
			}
		} else {
			System.err.println("THE PROJECT IN THE REQUIREMENT WAS NULL: ADD VOTE METHOD");
		}
	}
	
	/**
	 * Getter for the final estimate
	 */
	public int getFinalEstimate() {
		return finalEstimate;
	}
	
	/**
	 * Setter for the final estimate
	 */
	public void setFinalEstimate(int newEstimate) {
		finalEstimate = newEstimate;
	}
	
	/**
	 * Method save.
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#save()
	 */
	@Override
	public void save() {}
	
	/**
	 * Sets the requirement to completed
	 */
	private void makeComplete() {
		//DO NOT PUT DELAYCHANGE IN THIS METHOD
		//IT WILL CAUSE AN INFINITE LOOP!
		complete = true;
		makeChanged();
	}

	/**
	 * displays that the progress of the requirement
	 * @return the number of votes, or a star if the game is complete
	 */
	public String displayComplete() {
		if(complete) {
			return "*";
		}
		else{
			return Integer.toString(votes.size());
		}
	}
	
	/**
	 * Method delete.
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#delete()
	 */
	@Override
	public void delete() {}

	/**This returns a Json encoded String representation of this requirement object.
	 * 
	 * @return a Json encoded String representation of this requirement
	 * 
	 */
	@Override
	public String toJSON() {
		return new Gson().toJson(this, PPRequirement.class);
	}
	
	/**
	 * Returns an instance of Requirement constructed using the given
	 * Requirement encoded as a JSON string.
	 * 
	 * @param json JSON-encoded Requirement to deserialize
	 * @return the Requirement contained in the given JSON */
	public static PPRequirement fromRequirmentsManagerJSON(String json) {
		Gson gson;
		GsonBuilder builder = new GsonBuilder();
		// Use our custom deserializer
		builder.registerTypeAdapter(PPRequirement.class, new PPRequirementDeserializer());
		gson = builder.create();
		
		return gson.fromJson(json, PPRequirement.class);
	}
	
	public static PPRequirement fromJSON(String json){
		final Gson parser = new Gson();
		return parser.fromJson(json, PPRequirement.class);
	}
	
	/**
	 * Returns an array of Requirements parsed from the given JSON-encoded
	 * string.
	 * 
	 * @param json string containing a JSON-encoded array of Requirement
	 * @return an array of Requirement deserialized from the given JSON string */
	public static PPRequirement[] fromJsonArray(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, PPRequirement[].class);
	}
	
	
	/**
	 * Returns an array of Requirements parsed from the given JSON-encoded
	 * string.
	 * 
	 * @param json string containing a JSON-encoded array of Requirement
	 * @return an array of Requirement deserialized from the given JSON string */
	public static PPRequirement[] fromRequirmentsManagerJsonArray(String json) {
		Gson gson;
		GsonBuilder builder = new GsonBuilder();
		// Use our custom deserializer
		builder.registerTypeAdapter(PPRequirement.class, new PPRequirementDeserializer());
		gson = builder.create();
		
		return gson.fromJson(json, PPRequirement[].class);
	}
	

	/**
	 * Method identify.
	 * @param o Object
	 * @return Boolean * @see edu.wpi.cs.wpisuitetng.modules.Model#identify(Object) * 
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#identify(Object)
	 */
	@Override
	public Boolean identify(Object o) {
		if(o == null) {
			return false;
		}
		if(o.getClass() != this.getClass()) {
			return false;
		}
		PPRequirement comp = (PPRequirement)o;
		
		if(fromRequirementModule) {
			if(id != comp.id) {
				return false;
			}
		} else {
			if(!identity.equals(comp.identity)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Method toString.
	 * @return String * @see edu.wpi.cs.wpisuitetng.modules.Model#toString() * 
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#toString()
	 */
	@Override
	public String toString() {
		String n = "[Name: " + name + 
				" Description: " + description + " Votes: " + getVoteCount() + "]";
		return n;
	}
	
	/**
	 * @return the number of votes submitted
	 */
	public int getVoteCount() {
		return votes.size();
	}

	/**
	 * Copies all of the values from the given requirement to this requirement.
	 * 
	 * @param toCopyFrom the requirement to copy from.
	 * @return true if copyFrom succeeded, false if it did not
	 */
	public boolean copyFrom(PPRequirement toCopyFrom) {
		boolean wasChanged = false;
		if(id != toCopyFrom.id){
			id = toCopyFrom.id;
			wasChanged = true;
		}
		
		if(!identity.equals(toCopyFrom.identity)) {
			identity = toCopyFrom.identity;
			wasChanged = true;
		}
		
		if(fromRequirementModule != toCopyFrom.fromRequirementModule) {
			fromRequirementModule = toCopyFrom.fromRequirementModule;
			wasChanged = true;
		}
		
		if (finalEstimate != toCopyFrom.finalEstimate) {
			finalEstimate = toCopyFrom.finalEstimate;
			wasChanged = true;
		}
		
		if(!description.equals(toCopyFrom.description)) {
			description = toCopyFrom.description;
			wasChanged = true;
		}
		
		if(!name.equals(toCopyFrom.name)) {
			name = toCopyFrom.name;
			wasChanged = true;
		}
		
		if(!votes.equals(toCopyFrom.votes)) {
			for(Vote vote : toCopyFrom.votes){
				addNoDelay(vote);
			}
			wasChanged = true;
		}
		
		if(complete != toCopyFrom.complete) {
			complete = toCopyFrom.complete;
			wasChanged = true;
		}
		
		if(finalEstimate != toCopyFrom.finalEstimate) {
			finalEstimate = toCopyFrom.finalEstimate;
			wasChanged = true;
		}
		
		return wasChanged;
	}
	
	/**
	 * hold the code while the game model is updating
	 * prevent race-time condition for fields setting/overriding
	 */
	private void delayChange(String methodCalled) {
		while(GameModel.getInstance().isServerUpdating()) {
			try {
				Thread.sleep(5);
				System.out.println("Looping in the reqirement: " + methodCalled);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 
	 * @return the number of vote if the user already voted, otherwise return 0;
	 */
	public int userVote() {
		String currentUser = ConfigManager.getConfig().getUserName();
		for (Vote v : getVotes()) {
			if (currentUser.equals(v.getUsername())) {
				System.out.println("name matches");
				return v.getVoteNumber();
			}
		}
		System.out.println("name does not match");
		return 0;
	}

}
