package edu.wpi.cs.wpisuitetng.modules.planningpoker.deck.models;

import java.util.List;
import java.util.UUID;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;

public class Deck extends AbstractModel {
	private final UUID identity;
	private String name;
	private String description;
	private String owner;
	private List<Integer> cards;
	//private Color cardColor;
	
	public Deck(String name, String description, String owner, List<Integer> cards){
		identity = UUID.randomUUID();
		this.name = name;
		this.description = description;
		this.owner = owner;
		this.cards = cards;
	}
	
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

}
