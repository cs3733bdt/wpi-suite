package edu.wpi.cs.wpisuitetng.modules.planningpoker.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirementmodels.Requirement;

/**
 * Basic Game class that contains the data to be store for a Game
 * 
 * @author jonathanleitschuh
 */
public class Game extends AbstractModel{
	
	//This is the best way to keep games unique so that you are not relying upon data that can change
	private UUID identity;
	
	private int id;
	
	private String name;
	
	private String description;
	
	private boolean hasTimeLimit;
	
	private boolean usesCards;
	
	private Date creationTime;
	
	private String creator;
	
	private ArrayList<Requirement> requirements = new ArrayList<Requirement>();
	
	private boolean complete;
	
	private Calendar endTime;

	
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
	 * Copies all of the values from the given requirement to this requirement.
	 * @param toCopyFrom the requirement to copy from
	 */
	public void copyFrom(Game toCopyFrom) {
		this.complete = toCopyFrom.complete;
		this.creationTime = toCopyFrom.creationTime;
		this.creator = toCopyFrom.creator;
		this.id = toCopyFrom.id;
		this.identity = toCopyFrom.identity;
		this.name = toCopyFrom.name;
		this.requirements = toCopyFrom.requirements;
		this.description = toCopyFrom.description;
		this.endTime = toCopyFrom.endTime;
	}
	
	
	
	/**
	 * The basic constructor for a game
	 * Sets all of the default values for a game class
	 * 
	 */
	public Game(){
		super();
		name = "";
		description = "";
		creationTime = new Date();
		hasTimeLimit = false;
		complete = false;
		identity = UUID.randomUUID();
	}
	
	
	/**
	 * Constructs a Game with given id
	 * @param id the id number of the game
	 * @param name the name of the game
	 * @param hasTimeLimit checks if game has a time limit
	 * 
	 * @author dstapply
	 */
	public Game(String name, String description, String creator, ArrayList<Requirement> requirements, boolean hasTimeLimit, boolean usesCards) {
		this(); //Calls the default constructor
		this.name = name;
		this.description = description;
		this.creator = creator;
		this.hasTimeLimit = hasTimeLimit;
		this.requirements = requirements;
		this.usesCards = usesCards;
	}
	
	/**
	 * Constructs a Game with given id, and end time
	 * @param id the id number of the game
	 * @param name the name of the game
	 * @param hasTimeLimit checks if game has a time limit
	 * @param endTime Calendar object containing the end time for the game
	 */
	public Game(String name, String description, String creator, ArrayList<Requirement> requirements, boolean hasTimeLimit, boolean usesCards, Calendar endTime) {
		this(); //Calls the default constructor
		this.name = name;
		this.description = description;
		this.creator = creator;
		this.hasTimeLimit = hasTimeLimit;
		this.requirements = requirements;
		this.usesCards = usesCards;
		this.endTime = endTime;
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
		this(name, "", creator, null, hasTimeLimit, true);
		this.id = id;
	}

	/**
	 * Gets the id for this game
	 * @return the id of the game
	 */
	public int getId(){
		return id;
	}
	
	/**
	 * Gets the Identifier for this game
	 * @return the UUID for this class
	 */
	public UUID getIdentifier(){
		return identity;
	}
	
	
	/**
	 * Gets the name of this game
	 * @return the name of the game
	 */
	public String getName(){
		return name;
	}
	
	public boolean isComplete(){
		return complete;
	}
	
	public boolean doesUseCards(){
		return usesCards;
	}
	
	public String getDescription() {
		return description;
	}
	
	public ArrayList<Requirement> getRequirements(){
		return requirements;
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
	 * Converts the game into a string.
	 * @return Returns the name of the game
	 */
	public String toString(){
		return name;
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
		/* Changed so that this comparison does not run off of the name because the
		 * name of the game may be changed by the user at a later time.
		 */
		if(!identity.equals(comp.identity)){
			return false;
		}
		return true;
	}


}
