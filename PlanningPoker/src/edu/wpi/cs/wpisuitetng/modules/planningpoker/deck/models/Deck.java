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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.abstractmodel.IModelObserver;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.abstractmodel.IStorageModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.abstractmodel.ObservableModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.decks.creation.ColorEnum;

/**
 * Holds all of the information for a deck.
 * Decks are custom ways for users to vote upon requirements.
 * @author jonathanleitschuh
 *
 */
public class Deck extends ObservableModel implements IModelObserver, IStorageModel<Deck>  {
	private final UUID identity;
	private String name;
	private String description;
	private String owner;
	private List<Integer> cards;
	private boolean hasIdontKnow = true;
	private boolean isDefault = false;
	private ColorEnum color;
	private boolean isMultipleSelection = true;
	
	
	@Override
	public boolean copyFrom(Deck toCopyFrom) {
		boolean changes = false;
		if(!name.equals(toCopyFrom.name)){
			name = toCopyFrom.name;
			changes = true;
		}
		
		if(!description.equals(toCopyFrom.description)){
			description = toCopyFrom.description;
			changes = true;
		}
		if(!cards.equals(toCopyFrom.cards)){
			cards = toCopyFrom.cards;
			changes = true;
		}
		
		if(isMultipleSelection != toCopyFrom.isMultipleSelection) {
			isMultipleSelection = toCopyFrom.isMultipleSelection;
			changes = true;
		}
		
		if (hasIdontKnow != toCopyFrom.hasIdontKnow) {
			hasIdontKnow = toCopyFrom.hasIdontKnow;
			changes = true;
		}
		
		
		return changes;
	}
	
	/**
	 * Constructor for a Deck
	 * @param name the name of the deck
	 * @param description a description of this deck
	 * @param cards the numbers on this deck
	 * @param hasIdontKnow whether the deck has the IdontKnow button or not
	 * @param color the color of the deck
	 */
	public Deck(String name, String description, List<Integer> cards, boolean hasIdontKnow, ColorEnum color){
		identity = UUID.randomUUID();
		this.name = name;
		this.description = description;
		owner = ConfigManager.getConfig().getUserName();
		this.cards = cards;
		this.color = color;
		this.hasIdontKnow = hasIdontKnow;
	}
	
	private Deck(String name, String description, List<Integer> cards, UUID identity, boolean hasIdontKnow, ColorEnum color){
		this.identity = identity;
		this.name = name;
		this.description = description;
		owner = ConfigManager.getConfig().getUserName();
		this.cards = cards;
		this.color = color;
		this.hasIdontKnow = hasIdontKnow;
	}
	
	public Deck(){
		identity = UUID.randomUUID();
		name = "Default";
		description = "Default Deck";
		owner = "admin";
		hasIdontKnow = true;
		color = ColorEnum.RED;
		cards = new ArrayList<Integer>();
		buildDefaultDeck(7);
	}
	
	
	/**
	 * @param numCards number of Cards to be added. 0 returns an error. 
	 */
	private void buildDefaultDeck(int numCards) {
		int numLoops;
		if (numCards == 0) {
//			throw new EmptyDeckException(); //TODO implement proper catch in call heirarchy before uncommenting
			return;
		}
		else { 
			numLoops = numCards - 1;
		}
		int firstnum = 0;
		int secondnum = 1;
		int currnum;
		cards.add(secondnum);
		
		for (int i = 0; i < numLoops; i++) {
			currnum = firstnum + secondnum;
			cards.add(currnum);
			firstnum = secondnum;
			secondnum = currnum;
		}
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
		return new Gson().toJson(this, Deck.class);
	}

	@Override
	public Boolean identify(Object o) {
		if(o == null){
			return false;
		}
		if(o.getClass() != this.getClass()){
			return false;
		}
		Deck comp = (Deck)o;
		if(!identity.equals(comp.identity)){
			return false;
		}
		return true;
	}

	/**
	 * Gets the name of this deck
	 * @return the decks name
	 */
	public String getName() {
		return name;
	}

	public static Deck fromJSON(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, Deck.class);
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

	/**
	 * Gets the owner of this deck
	 * @return the name of the deck's owner
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * Sets the name of the deck
	 * @param name the new deck name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * USED FOR TESTING EXCLUSIVELY
	 * @param name
	 * @param description
	 * @param cards
	 * @param identifyingDeck
	 * @return the deck
	 */
	public static Deck makeDeckSameID(String name, String description, List<Integer> cards, Deck identifyingDeck){
		return new Deck(name, description, cards, identifyingDeck.identity, true, identifyingDeck.color);	
	}

	@Override
	public void update(ObservableModel o, Object arg) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Gets the individual cards in this deck.
	 * Note:
	 * 		Cards are not saved as a part of this Deck
	 * 		They are only instantiated when this method is called
	 * 
	 * @return the list of cards in this deck
	 */
	public List<Card> getCards() {
		List<Card> returnedCards = new ArrayList<Card>();
		for(Integer i: cards){
			returnedCards.add(new Card(Integer.toString(i), this));
		}
		if(hasIdontKnow){
			returnedCards.add(new Card("?", this));
		}
		return returnedCards;
	}
	
	/**
	 * Gets the number of cards in this deck
	 * @return the size of this deck
	 */
	public int getSize(){
		return cards.size() + (hasIdontKnow ? 1 : 0);
	}

	public ColorEnum getColor() {
		return color;
	}
	
	/**
	 * @return index for the color drop down for the associated color.
	 */
	public int getColorIndex() {
		switch(color){
		case YELLOW:
			return 4;
		case RED:
			return 0;
		case PURPLE:
			return 3;
		case BLUE:
			return 1;
		case GREEN:
			return 2;
		default:
			return 0;
		}
		
	}
	
	public void updateMultipleSelection(boolean isMultipleSelection) {
		if (isMultipleSelection == this.isMultipleSelection) {
			return;
		}
		makeChanged();
		//delayChange(); //TODO This method is needed for changing
		this.isMultipleSelection = isMultipleSelection;
	}
	
	public boolean isMultipleSelection() {
		return isMultipleSelection;
	}
	
	public boolean hasIDontKnowCard(){
		return hasIdontKnow;
	}
	
	public void updateHasIdk(boolean hasIdontKnow) {
		if (hasIdontKnow == this.hasIdontKnow) {
			return;
		}
		makeChanged();
		//delayChange(); //TODO This method is needed for changing
		this.hasIdontKnow = hasIdontKnow;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
