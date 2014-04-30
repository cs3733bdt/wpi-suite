/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Rolling Thunder
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.requirement;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirement.characteristics.AcceptanceTest;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirement.characteristics.Attachment;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirement.characteristics.DevelopmentTask;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirement.characteristics.NoteList;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirement.characteristics.RequirementPriority;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirement.characteristics.RequirementStatus;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirement.characteristics.RequirementType;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirement.characteristics.TestStatus;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirement.characteristics.TransactionHistory;

/**
 * Basic Requirement class that contains the data to be stored for a Requirement
 * 
 * 
 * @version $Revision: 1.0 $
 * @author justinhess
 */
public class Requirement extends AbstractModel {
	/** the ID of the requirement */
	private int id; // TODO: move ID stuff to server side?

	/** the name of the requirement */
	private String name;

	/** the release number of the requirement */
	private String release;

	/** the project status of the requirement */
	private RequirementStatus status;

	/** the priority of the requirement */
	private RequirementPriority priority;

	/** a short description of the requirement */
	private String description;

	/** the estimated amount of effort to complete the requirement */
	private int estimate;
	
	/** flag to indicate when the requirement estimate was just edited */
	private boolean estimateEdited;
	
	/** flag to indicate whether the requirement was just created or not */
	private boolean wasCreated;

	/** the actual effort of completing the requirement */
	private int actualEffort;

	/** history of transactions of the requirement */
	private TransactionHistory history;

	/** the type of the requirement */
	private RequirementType type;

	/** notes associated with the requirement */
	private NoteList notes;

	/** iteration the requirement is assigned to */
	private String iteration;
	
	/** the ID of the requirement that this requirement is a sub-requirement of */
	private int parentID = -1;

	/**
	 * team members the requirement is assigned to need to figure out the class
	 * of a user name, then use that instead of TeamMember
	 */
	private List<String> assignedTo;

	/** development tasks associated with the requirement */
	private List<DevelopmentTask> tasks;

	/** acceptance tests associated with the requirement */
	private ArrayList<AcceptanceTest> tests;

	/** attachments associated with the requirement */
	private List<Attachment> attachments;

	/**
	 * Constructs a Requirement with default characteristics
	 */
	public Requirement() {
		super();
		name = description = "";
		release = "";
		status = RequirementStatus.NEW;
		priority = RequirementPriority.BLANK;
		estimate = actualEffort = 0;
		history = new TransactionHistory();
		iteration = "Backlog";
		type = RequirementType.BLANK;
		this.parentID = -1;
		notes = new NoteList();
		tasks = new ArrayList<DevelopmentTask>();
		tests = new ArrayList<AcceptanceTest>();
		attachments = new ArrayList<Attachment>();
	}

	/**
	 * Construct a Requirement with required properties provided and others set
	 * to default
	 * 
	 * @param id
	 *            The ID number of the requirement
	 * @param name
	 *            The name of the requirement
	 * @param description
	 *            A short description of the requirement
	 */
	// need to phase out supplying the ID
	public Requirement(int id, String name, String description) {
		this();
		this.id = id;
		this.name = name;
		this.description = description;
		this.parentID = -1;
	}

	/**
	 * Constructs a requirement from the provided characteristics
	 * 
	 * @param id
	 *            The ID number of the requirement
	 * @param name
	 *            The name of the requirement
	 * @param release
	 *            The release number of the requirement
	 * @param status
	 *            The status of the requirement
	 * @param priority
	 * 			The priorty of the requirement
	 * @param description
	 *            A short description of the requirement
	 * @param estimate
	 *            The estimated time required by the task. This is in a point
	 *            system decided by the user
	 * @param effort
	 *            The estimated amount of work required by the requirement.
	 * @deprecated Should not be used anymore.
	 */
	@Deprecated
	public Requirement(int id, String name, String release,
			RequirementStatus status, RequirementPriority priority,
			String description, int estimate, int effort) {
		this.id = id;
		this.name = name;
		this.release = release;
		this.status = status;
		this.priority = priority;
		this.description = description;
		this.estimate = estimate;
		this.actualEffort = effort;
		this.parentID = -1;
	}

	/**
	 * Method save.
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#save()
	 */
	@Override
	public void save() {
		// TODO Auto-generated method stub

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

	@Override
	public Boolean identify(Object o) {
		// TODO Auto-generated method stub
		return null;
	}

}
