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

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.abstractmodel.ObservableModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;

/**
 * Holds all of the information for a deck.
 * Decks are custom ways for users to vote upon requirements.
 * @author jonathanleitschuh
 *
 */
public class Deck extends ObservableModel {
	private final UUID identity;
	private String name;
	private String description;
	private String owner;
	private List<Integer> cards;
	//private Color cardColor;
	
	/**
	 * Constructor for a Deck
	 * @param name the name of the deck
	 * @param description a description of this deck
	 * @param owner the user who created this deck
	 * @param cards the numbers on this deck
	 */
	public Deck(String name, String description, List<Integer> cards){
		identity = UUID.randomUUID();
		this.name = name;
		this.description = description;
		this.owner = ConfigManager.getInstance().getConfig().getUserName();
		this.cards = cards;
	}
	
	private Deck(String name, String description, List<Integer> cards, UUID identity){
		this.identity = identity;
		this.name = name;
		this.description = description;
		this.owner = ConfigManager.getInstance().getConfig().getUserName();
		this.cards = cards;
	}
	
	/**
	 * Gets the identity of this Deck
	 * @return the unique UUID for this deck
	 */
	public UUID getIdentity(){
		return identity;
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub

	}

	@Override
	public String toJSON() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean identify(Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Gets the name of this deck
	 * @return the decks name
	 */
	public String getName() {
		return name;
	}

	public static Deck fromJSON(String body) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Returns an array of Game parsed from the given JSON-encoded
	 * string.
	 * 
	 * @param json a string containing a JSON-encoded array of Game
	 * @return an array of Game deserialzied from the given json string
	 */
	public static Deck[] fromJsonArray(String json) {
	    final Gson parser = new Gson();
	    return parser.fromJson(json, Deck[].class);
	}

	public String getDescription() {
		return description;
	}

	public String getOwner() {
		return owner;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public static Deck makeDeckSameID(String name, String description, List<Integer> cards, Deck identifyingDeck){
		return new Deck(name, description, cards, identifyingDeck.identity);	
	}

}
