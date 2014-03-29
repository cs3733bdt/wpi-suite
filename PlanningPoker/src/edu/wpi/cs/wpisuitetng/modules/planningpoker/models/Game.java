package edu.wpi.cs.wpisuitetng.modules.planningpoker.models;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
//import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

/**
 * Basic Game class that contains the data to be store for a Game
 * 
 * @author jonathanleitschuh
 */
public class Game extends AbstractModel{
	
	int id;
	
	private String name;
	
	private boolean hasTimeLimit;
	
	private Date creationTime;
	
	private String creator;
	
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
	 * The basic constructor for a game
	 * Sets all of the default values for a game class
	 * 
	 */
	private Game(){
		super();
		name = "";
		creationTime = new Date();
		hasTimeLimit = false;
	}
	
	
	/**
	 * Constructs a Game with given id
	 * @param id the id number of the game
	 * @param name the name of the game
	 * @param hasTimeLimit checks if game has a time limit
	 * 
	 * @author dstapply
	 */
	public Game(String name, String creator, boolean hasTimeLimit) {
		this(); //Calls the default constructor
		this.name = name;
		this.creator = creator;
		this.hasTimeLimit = hasTimeLimit;
		
	}
	
	/**
	 * Used for junit testing
	 * Constructs a game from the provided characteristics
	 * @param id
	 * 			The ID number of the requirement
	 * @param name
	 * 			The name of the game
	 * @param creator
	 * 			The creator of the game
	 * @param hasTimeLimit
	 * 			Whether or not this game has a time limit or not.
	 */
	@Deprecated
	public Game(int id, String name, String creator, boolean hasTimeLimit){
		this(name, creator, hasTimeLimit);
		this.id = id;
	}
	
	public int getId(){
		return id;
	}
	
	/**
	 * Gets the name of this game
	 * @return the name of the game
	 */
	public String getName(){
		return name;
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
		if(!name.equals(comp.name)){
			return false;
		}
		return true;
	}

}
