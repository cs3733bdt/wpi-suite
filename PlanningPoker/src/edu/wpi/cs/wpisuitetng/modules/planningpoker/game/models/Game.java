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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.abstractmodel.IModelObserver;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.abstractmodel.IStorageModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.abstractmodel.ObservableModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.deck.models.Deck;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.exceptions.DBModelNotInstantiatedException;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.notifications.EmailNotification;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.notifications.FacebookNotification;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.notifications.SMSNotification;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.controllers.PPRequirementHolder;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.controllers.UpdatePPRequirementController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.models.PPRequirement;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;

/**
 * Basic Game class that contains the data to be store for a Game
 * 
 * @author jonathanleitschuh
 */
public class Game extends ObservableModel implements IModelObserver, IStorageModel<Game>{
	
	/** This is the best way to keep games unique so 
	 *  that you are not relying upon data that can change */
	private UUID identity;
	/** The name of the game */
	private String name;
	/** The description of the game */
	private String description;
	/** True if the game does of a time limit */
	private boolean hasTimeLimit;
	/** True if the game uses cards, false if it uses text input */
	private boolean usesCards;
	/** The date and time of the game creation */
	private Date creationTime;
	/** The username of the game creator */
	private String creator;
	/** The list of requirements that need to be estimated */
	private List<PPRequirement> requirements = new ArrayList<>();
	/** True if the game is complete, false otherwise */
	private boolean complete;
	/** True if the game is active and people can vote, false if people can't vote */
	private boolean active;
	/** The date and time that the game ended/completed */
	private Date endDate;
	/** True if the users of the game have been notified of game creation */
	private boolean notifiedOfCreation;
	/** True if the users of the game have been notified of game complete */
	private boolean notifiedOfCompletion;
	/** Holds the deck for this game */
	private Deck deck;
	/** Holds the logger for this class */
	private static Logger logger = Logger.getLogger(Game.class.getName());

	/**
	 * Copies all of the values from the given Game to this Game.
	 * @param toCopyFrom the Game to copy from
	 * @return true if any modifications are made to the class
	 */
	public boolean copyFrom(Game toCopyFrom) {
		boolean needsUpdate = false;		//Triggers if there was a change to to the UUID
		boolean wasChanged = false;			//True if there were any changes made

		if(!name.equals(toCopyFrom.name)){
			name = toCopyFrom.name;
			needsUpdate = true;
			wasChanged = true;
			logger.log(Level.FINEST, "Name coppied: " + name);
		}

		if(!description.equals(toCopyFrom.description)){
			description = toCopyFrom.description;
			needsUpdate = true;
			wasChanged = true;
			logger.log(Level.FINEST, "Description coppied: " + description);
		}

		if(hasTimeLimit != toCopyFrom.hasTimeLimit){
			hasTimeLimit = toCopyFrom.hasTimeLimit;
			needsUpdate = true;
			wasChanged = true;
			logger.log(Level.FINEST, "Has Time Limit coppied: " + hasTimeLimit);
		}

		if(usesCards != toCopyFrom.usesCards){
			usesCards = toCopyFrom.usesCards;
			needsUpdate = true;
			wasChanged = true;
			logger.log(Level.FINEST, "Uses Cards coppied: " + usesCards);
		}

		if(!creationTime.equals(toCopyFrom.creationTime)){
			creationTime = toCopyFrom.creationTime;
			needsUpdate = true;
			wasChanged = true;
			logger.log(Level.FINEST, "Creation Time coppied " + creationTime);
		}

		if(!creator.equals(toCopyFrom.creator)){
			creator = toCopyFrom.creator;
			needsUpdate = true;
			wasChanged = true;
			logger.log(Level.FINEST, "Creator coppied " + creator);
		}

		if(!endDate.equals(toCopyFrom.endDate)){
			endDate = toCopyFrom.endDate;
			needsUpdate = true;
			wasChanged = true;
			logger.log(Level.FINEST, "End Date coppied " + endDate);
		}

		if(active != toCopyFrom.active) {
			active = toCopyFrom.active;
			needsUpdate = true;
			wasChanged = true;
			logger.log(Level.FINEST, "Active coppied " + active);
		}
		
		if(!requirements.equals(toCopyFrom.requirements)){
			boolean changes = false;

			//REMOVES REQUIREMENTS THAT HAVE BEEN REMOVED FROM THIS GAME
			Iterator<PPRequirement> existingReq = requirements.iterator();
			while(existingReq.hasNext()){
				boolean found = false;
				PPRequirement comp = existingReq.next();
				for(PPRequirement serverReq : toCopyFrom.requirements){
					if(serverReq.identify(comp)){
						found = true;
					}
				}
				if(!found){
					existingReq.remove();
					changes = true;
				}
			}
			//END REMOVE REQUIREMENTS
			
			List<PPRequirement> fromDB = getRequirementsFromDB(toCopyFrom.requirements);

			for(PPRequirement serverReq: fromDB){//Iterate over the new requirements
				boolean found = false;
				
				for(PPRequirement req : requirements){//Iterate over the existing requirements list
					if(serverReq.identify(req)){	//If this requirement is found
						found = true;
						if(req.copyFrom(serverReq)){//Copy the data over
							changes = true;			//If there are changes then make those changes
							req.addObserver(this);
						}
					}
				}
				if(!found){						//If this requirement does not exist in the list
					changes = true;				//Indicates that this game has changes
					serverReq.addObserver(this);//Add this game as an observer for this requirement
					requirements.add(serverReq);//Add to the list of server requirements
				}
			}

			if(changes){											
				wasChanged = true;
			}
			needsUpdate = true;
			logger.log(Level.FINEST, "Requirements coppied " + requirements);
		}

		if(complete != toCopyFrom.complete){
			if (!complete){
				complete = toCopyFrom.complete;
			}
			needsUpdate = true;
			wasChanged = true;
			logger.log(Level.FINEST, "Complete coppied " + complete);
		}

		if(notifiedOfCreation != toCopyFrom.notifiedOfCreation) {
			notifiedOfCreation = toCopyFrom.notifiedOfCreation;
			needsUpdate = true;
			wasChanged = true;
			logger.log(Level.FINEST, "Notified Of Creation coppied " + notifiedOfCompletion);
		}
		
		if(!deck.equals(toCopyFrom.deck)){
			if(deck.copyFrom(toCopyFrom.deck)){
				wasChanged = true;
				needsUpdate = true;
			}
			logger.log(Level.FINEST, "Deck coppied " + deck);
		}

		if(identity.equals(toCopyFrom.identity)){
			needsUpdate = false;
		} else {
			identity = toCopyFrom.identity;
			needsUpdate = true;
		}

		if(needsUpdate){
			logger.log(Level.SEVERE, "WARNING! THERE WAS A COPY OVER FOR TWO NON MATCHING UUID GAMES!");
			makeChanged();
		}

		return wasChanged;
	}

	private List<PPRequirement> getRequirementsFromDB(List<PPRequirement> match) {
		try {
			List<PPRequirement> newReq = new ArrayList<PPRequirement>();
			for(PPRequirement req : PPRequirementHolder.getInstance().getRequirements()){
				for(PPRequirement reqMatch : match){
					if(req.identify(reqMatch)){
						newReq.add(req);
					}
				}
			}
			return newReq;
		} catch (DBModelNotInstantiatedException e) {
			logger.log(Level.SEVERE, "GetPPRequirementController was null.", e);
			return match;
		}
	}

	/**
	 * The basic constructor for a game
	 * Sets all of the default values for a game class
	 * 
	 */
	public Game(){
		name = "";
		description = "";
		creator = "";
		creationTime = new Date();
		endDate = new Date();
		hasTimeLimit = false;
		complete = false;
		notifiedOfCreation = false;
		notifiedOfCompletion = false;
		identity = UUID.randomUUID();
		deck = new Deck();
	}

	/**
	 * Constructs a Game without a creation time
	 * @param name the name of the game
	 * @param description the description of the game
	 * @param requirements the list of requirements for the game
	 * @param hasTimeLimit checks if game has a time limit
	 * @param usesCards checks if the game uses cards or text entry
	 * 
	 */
	public Game(String name, 
			String description, 
			List<PPRequirement> requirements,
			boolean hasTimeLimit, 
			boolean usesCards) {
		this(); //Calls the default constructor
		this.name = name;
		this.description = description;
		this.hasTimeLimit = hasTimeLimit;
		this.requirements = requirements;
		for(PPRequirement req : this.requirements){
			req.addObserver(this);
			req.setProject(getProject());
		}
		this.usesCards = usesCards;

	}

	/**
	 * Constructs a Game with a creation time
	 * @param name the name of the game
	 * @param description the description of the game
	 * @param hasTimeLimit checks if game has a time limit
	 * @param requirements the list of requirements for the game
	 * @param usesCards checks if the game uses cards or text entry
	 * @param creationTime the data and time a game is created on
	 * 
	 */
	public Game(String name, 
			String description, 
			List<PPRequirement> requirements, 
			boolean hasTimeLimit, 
			boolean usesCards, 
			Date creationTime) {
		this(name, description, requirements, hasTimeLimit, usesCards);
		this.creationTime = creationTime;
		this.complete = false;
	}

	/**
	 * Gets the universally unique identifier for this game
	 * @return the uuid of the game
	 */
	public void setIdentifier(UUID identifier){
		delayChange("setIdentifier");
		makeChanged();
		identity = identifier;
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
	 * Sets the name of a game
	 * @param newName
	 */
	public void setName(String newName){
		if(!name.equals(newName)){
			makeChanged();
			delayChange("setName");
			name = newName;
		}
	}
	
	/**
	 * Gets the deck from the game
	 * @return the deck
	 */
	public Deck getDeck() {
		return deck;
	}
	
	/**
	 * Sets the game's deck to a new deck
	 * @param deck to be set
	 */
	public void setDeck(Deck deck) {
		if (!this.deck.equals(deck)) {
			makeChanged();
			delayChange("setDeck");
			this.deck = deck;
		}
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
	 */
	public void makeComplete(){
		if(!complete ){
			makeChanged();
			delayChange("makeComplete");
			complete = true;
		}
	}

	/**
	 * Does this game use cards to estimate
	 * @return true if this game uses cards
	 */
	public boolean doesUseCards(){
		return usesCards;
	}

	/**
	 * displays a given set of cards
	 * @param newUsesCards cards to be displayed
	 */
	public void setUsesCards(boolean newUsesCards){
		if(usesCards != newUsesCards){
			makeChanged();
			delayChange("setUsesCards");
			usesCards = newUsesCards;
		}
	}

	/**
	 * Gets the description of the game
	 * @return the description of the game
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * sets the description box to a certain value to be displayed
	 * @param newDescription new value for the description boxS
	 */
	public void setDescription(String newDescription){
		if(!description.equals(newDescription)){
			makeChanged();
			delayChange("setDescription");
			description = newDescription;
		}
	}

	/**
	 * Sets the project for the game and it's requirements
	 */
	@Override
	public void setProject(Project p) {
		// Set requirements' project
		for(PPRequirement r: requirements) {
			r.setProject(p);
		}
		// Set game's project
		super.setProject(p);
	}

	/**
	 * Gets the list of requirements for this game
	 * *WARNING* ADDING ELEMENTS TO THIS ARRAY WILL MAKE THEM UNTRACKABLE 
	 * TO THE GAME AND PREVENT THEM FROM BEING ADDED TO THE SERVER
	 * @return the list of requirements for the game
	 */
	final public List<PPRequirement> getRequirements(){
		return requirements;
	}

	/**
	 * TODO: add documentation
	 * @param newReqs
	 */
	public void setRequirements(List<PPRequirement> newReqs){
		if(!requirements.equals(newReqs)){
			makeChanged();
			delayChange("setRequiremets");
			requirements = newReqs;
			for(PPRequirement req : requirements){
				req.addObserver(this);
			}
		}
	}

	/**
	 * Gets the username of the creator of the game
	 * @return the list of requirements for the game
	 */
	public String getCreator() {
		return creator;
	}

	/**
	 * Sets the creator of the game.
	 * @param creator the creator's username
	 */
	public void setCreator(String creator) {
		if(!this.creator.equals(creator)){
			makeChanged();
			delayChange("setCreator");
			this.creator = creator;
		}
	}

	/**
	 * Get the number of all users
	 * @return the number of all users
	 */
	public int getUsers(){
		return getProject().getTeam().length;
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
	 * Gets the active boolean
	 * @return true if game is active, false otherwise
	 */
	public boolean isActive(){
		return active;
	}

	/**
	 * sets whether the game is active
	 * @param newActive true if the game is active, false if it is inactive
	 */
	public void setActive(boolean newActive){
		if(active != newActive){
			makeChanged();
			delayChange("setActive");
			active = newActive;
		}
	}

	@Override
	public String toString(){
		return name;
	}

	/**
	 * Method toJSON.
	 * @return String * @see edu.wpi.cs.wpisuitetng.modules.Model#toJSON() * 
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#toJSON()
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
		if (o instanceof PPRequirement){
			UpdatePPRequirementController.getInstance().updateRequirement((PPRequirement)o);
			makeChanged();
			notifyObservers(arg);
		}
		System.out.println("Game: " + name + " has " + countObservers() + " observers");
		if(countObservers()>0){
			System.out.println("\t" + this.getObserver(0));
		}
	}

	public Date getEndDate() {
		return endDate;
	}

	/**
	 * set the endDate of a game to a given Date
	 * @param endDate the new end date
	 */
	public void setEndDate(Date endDate) {
		if(this.endDate != endDate){
			makeChanged();
			delayChange("setEndDate");
			this.endDate = endDate;
		}
	}

	/**
	 * Checks if the user is the creator of the current game
	 * @param user user to check
	 * @return returns true if the user being checked is the creator, returns false otherwise
	 */
	public boolean isCreator(String user) {
		return creator.equals(user);
	}

	/**
	 * Checks if users have been notified after game creation
	 * @return True if users have been notified after game creation
	 */
	public boolean isNotifiedOfCreation() {
		return notifiedOfCreation;
	}

	/**
	 * Sets notifiedOfCreation to value of param
	 * @param notifiedOfCreation value set
	 */
	public void setNotifiedOfCreation(boolean notifiedOfCreation) {
		if (this.notifiedOfCreation != notifiedOfCreation) {
			makeChanged();
			delayChange("setNotifiedOfCreation");
			this.notifiedOfCreation = notifiedOfCreation;
		}
	}

	/**
	 * Checks if the users have been notified of a game completion
	 * @return True if the users have been notified of the game completion
	 */
	public boolean isNotifiedOfCompletion() {
		return notifiedOfCompletion;
	}

	/**
	 * Sets notifiedOfCompletion to value of param
	 * @param notifiedOfCompletion value set
	 */
	public void setNotifiedOfCompletion(boolean notifiedOfCompletion) {
		if (this.notifiedOfCompletion != notifiedOfCompletion) {
			makeChanged();
			delayChange("setNotifiedOfCompletion");
			this.notifiedOfCompletion = notifiedOfCompletion;
		}
	}

	/**
	 * hold the code while the game model is updating
	 * prevent race-time condition for fields setting/overriding
	 */
	private void delayChange(String source){
		while(GameModel.getInstance().isServerUpdating()){
			try {
				Thread.sleep(5);
				logger.log(Level.WARNING, "Looping in the game: From source " + source);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public synchronized void addObserver(IModelObserver o){
		for(PPRequirement r : requirements){
			r.addObserver(this);
		}
		super.addObserver(o);
	}


	/**
	 * hasChanged in the super class does not check if the 
	 * requirements has been changed. This method is to check
	 * whether if the requirements are also changed.
	 * @return true if the requirement is changed
	 * 
	 */
	@Override
	public synchronized boolean hasChanged()
	{
		if(super.hasChanged())
			return true;

		for(PPRequirement requirement: requirements)
		{
			if(requirement.hasChanged())
				return true;
		}
		return false;
	}

	/**
	 * Sends notifications via email, facebook, and text message
	 * to the team associated with this game.
	 */
	public void sendNotifications() {

		// Notify all team users via email
		EmailNotification en = new EmailNotification(this);
		en.sendEmails();

		// Notify all team users via text message
		SMSNotification sms = new SMSNotification(this);
		sms.sendSMSMessages();

		// Notify all team users via facebook message
		FacebookNotification fbn = new FacebookNotification(this);
		fbn.sendFacebookNotifications();

	}
	
	/**
	 * Checks if the current game has passed its deadline and sets it as
	 * complete if so
	 * 
	 * @return returns true if the game has passed its deadline, false otherwise
	 */
	public boolean hasEnded() {
		Calendar rightNow = Calendar.getInstance();
		Calendar gameEnd = new GregorianCalendar();
		gameEnd.setTime(getEndDate());
		
		if(isActive() && rightNow.after(gameEnd)) {
			if(ViewEventController.getInstance().getTabbedView().hasActiveGameOpen(this)) {
				JOptionPane.showMessageDialog(
					ViewEventController.getInstance().getTabbedView().getActiveGamePanel(this),
					"Current game has expired and will now close.");
				ViewEventController.getInstance().getTabbedView().removeTab(
							(JComponent) ViewEventController.getInstance().getTabbedView().getActiveGamePanel(
							this));
			}
			makeChanged();
			delayChange("hasEnded");
			makeComplete();
			setActive(false);
			notifyObservers();
			ViewEventController.getInstance().refreshGameTree();
			return true;
			}
		else {
			return false;
		}
	}
}
