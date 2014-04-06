package edu.wpi.cs.wpisuitetng.modules.planningpoker.models.game;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.observers.AbstractModelObserver;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.observers.ObservableModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.requirement.Requirement;

/**
 * Basic Game class that contains the data to be store for a Game
 * 
 * @author jonathanleitschuh
 */

public class Game extends ObservableModel implements AbstractModelObserver{
	
	/** This is the best way to keep games unique so that you are not relying upon data that can change */
	private UUID identity;
	
	private String name;
	
	private String description;
	
	private boolean hasTimeLimit;
	
	private boolean usesCards;
	
	private Date creationTime;
	
	private String creator;
	
	private ArrayList<Requirement> requirements = new ArrayList<Requirement>();
	
	private boolean complete;
	
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
		boolean needsUpdate = false;
		
		if(!this.name.equals(toCopyFrom.name)){
			this.name = toCopyFrom.name;
			needsUpdate = true;
		}
		
		if(!this.description.equals(toCopyFrom.description)){
			this.description = toCopyFrom.description;
			needsUpdate = true;
		}
		
		if(this.hasTimeLimit != toCopyFrom.hasTimeLimit){
			this.hasTimeLimit = toCopyFrom.hasTimeLimit;
			needsUpdate = true;
		}
		
		if(this.usesCards != toCopyFrom.usesCards){
			this.usesCards = toCopyFrom.usesCards;
			needsUpdate = true;
		}
		
		if(!this.creationTime.equals(toCopyFrom.creationTime)){
			this.creationTime = toCopyFrom.creationTime;
			needsUpdate = true;
		}
		
		if(!this.creator.equals(toCopyFrom.creator)){
			this.creator = toCopyFrom.creator;
			needsUpdate = true;
		}
		
		if(!this.requirements.equals(toCopyFrom.requirements)){
			this.requirements = toCopyFrom.requirements;
			for (Requirement req : this.requirements){ 
				req.deleteObservers(); //Removes any previous observers on this class. This may be wrong and may break things
				req.addObserver(this); //Adds this as an observer
			}
			needsUpdate = true;
		}
		
		if(this.complete != toCopyFrom.complete){
			this.complete = toCopyFrom.complete;
			needsUpdate = true;
		}
		
		if(!this.identity.equals(toCopyFrom.identity)){
			this.identity = toCopyFrom.identity;
			needsUpdate = false;
		}
		
		if(needsUpdate){
			this.setChanged();
			this.notifyObservers();
		}
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
		creator = "";
		creationTime = new Date();
		hasTimeLimit = false;
		complete = false;
		identity = UUID.randomUUID();
	}
	
	
	/**
	 * Constructs a Game without a creation time
	 * @param name the name of the game
	 * @param description the description of the game
	 * @param creator the name of the user who created the game
	 * @param hasTimeLimit checks if game has a time limit
	 * @param requirements the list of requirements for the game
	 * @param usesCards checks if the game uses cards or text entry
	 * 
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
	 * Constructs a Game without a creation time
	 * @param name the name of the game
	 * @param description the description of the game
	 * @param creator the name of the user who created the game
	 * @param hasTimeLimit checks if game has a time limit
	 * @param requirements the list of requirements for the game
	 * @param usesCards checks if the game uses cards or text entry
	 * @param creationTime the data and time a game is created on
	 * 
	 */
	public Game(String name, String description, String creator, ArrayList<Requirement> requirements, boolean hasTimeLimit, boolean usesCards, Date creationTime) {
		this(name, description, creator, requirements, hasTimeLimit, usesCards); //Calls the default constructor
		this.creationTime = creationTime;
	}
	
	/**
	 * Gets the universally unique identifier for this game
	 * @return the uuid of the game
	 */
	public void setIdentifier(UUID identifier){
		this.identity = identifier;
	}
	
	/**
	 * Gets the Identifier for this game
	 * @return the UUID for this class
	 */
	public UUID getIdentity(){
		return identity;
	}
	
	
	/**
	 * Gets the name of this game
	 * @return the name of the game
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Is this this game completed
	 * @return true if the game is complete
	 */
	public boolean isComplete(){
		return complete;
	}
	
	/**
	 * Change game status to complete
	 * @return true if the game is complete
	 */
	
	public void setComplete(){
		this.complete = true;
	}
	
	/**
	 * Does this game use cards to estimate
	 * @return true if this game uses cards
	 */
	public boolean doesUseCards(){
		return usesCards;
	}
	
	/**
	 * Gets the description of the game
	 * @return the description of the game
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Gets the list of requirements for this game
	 * @return the list of requirements for the game
	 */
	public ArrayList<Requirement> getRequirements(){
		return requirements;
	}
	
	/**
	 * Gets the username of the creator of the game
	 * @return the list of requirements for the game
	 */
	public String getCreator() {
		return creator;
	}
	
	/**
	 * Gets the creating time and date of the game
	 * @return a Formated Date String
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
		/* Changed so that this comparison does not run off of the name because the
		 * name of the game may be changed by the user at a later time.
		 */
		if(!identity.equals(comp.identity)){
			return false;
		}
		return true;
	}

	@Override
	public void update(ObservableModel o, Object arg) {
		if (o instanceof Requirement){
			this.hasChanged();
			this.notifyObservers(arg);
		}
	}


}
