package edu.wpi.cs.wpisuitetng.modules.planningpoker.models;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
//import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

/**
 * Basic Game class that contains the data to be store for a Game
 * 
 * @author jonathanleitschuh
 */
public class Game extends AbstractModel{
	
	private int id;
	
	private boolean hasTimeLimit;
	
	private Date creationTime;
	
	//TODO find out how to implement existing module classes
	//private List<Requirement> requirements;
	
	/*
	 * dstapply
	 * 
	 * We probably want to keep track of:
	 * 	game creator
	 * 	list of game players
	 * 	moderator
	 * 	list of estimates players can choose
	 * 	player-estimate pairs
	 * 	active?
	 * 
	 */
	
	/**
	 * Constructs a Game with given id
	 * @param id
	 * @param hasTimeLimit checks if game has a time limit
	 * 
	 * @author dstapply
	 */
	public Game(int id, boolean hasTimeLimit) {
		this.id = id;
		this.hasTimeLimit = hasTimeLimit;
		creationTime = new Date();
	}
	
	/**
	 * Gets the creating time and date of the game
	 * @return a Formated Date String
	 * 
	 * @author dstapply
	 */
	public String getCreationTime() {
		// Format the date-time stamp
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy hh:mm a");
		
		return dateFormat.format(creationTime);
	}

	/**
	 * Method toJSON.
	 * @return String * @see edu.wpi.cs.wpisuitetng.modules.Model#toJSON() * @see edu.wpi.cs.wpisuitetng.modules.Model#toJSON()
	 */
	@Override
	public String toJSON() {
		return new Gson().toJson(this, Game.class);
	}
	
	/**
	 * Returns an instance of Game constructed using the given
	 * Game encoded as a JSON string.
	 * 
	 * @param json the json-encoded Game to deserialize
	 * @return the Game contained in the given JSON
	 */
	public static Game fromJSON(String json) {
	    final Gson parser = new Gson();
	    return parser.fromJson(json, Game.class);
	}

	/**
	 * Returns an array of Game parsed from the given JSON-encoded
	 * string.
	 * 
	 * @param json a string containing a JSON-encoded array of Game
	 * @return an array of Game deserialzied from the given json string
	 */
	public static Game[] fromJsonArray(String json) {
	    final Gson parser = new Gson();
	    return parser.fromJson(json, Game[].class);
	}
	
	@Override
	public void save() {}

	@Override
	public void delete() {}

	@Override
	public Boolean identify(Object o) {
		if(o == null){
			return false;
		}
		if(o.getClass() != this.getClass()){
			return false;
		}
		Game comp = (Game)o;
		if(id != comp.id){
			return false;
		}
		return true;
	}

}
