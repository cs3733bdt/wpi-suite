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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.requirement.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.abstractmodel.AbstractStorageModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.abstractmodel.ObservableModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirement.controllers.AddRequirementController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirement.controllers.UpdateRequirementController;


//import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.ViewEventController;

/**
 * List of Requirements being pulled from the server
 * 
 * @author tianchanggu
 *
 */
@SuppressWarnings("serial")
public class RequirementModel extends AbstractStorageModel<Requirement>{
	
	protected RequirementModel() {
		super(new ArrayList<Requirement>());
		nextID = 0;
		// TODO Auto-generated constructor stub
	}

	/**
	 * The list in which all the requirements for a single project are contained
	 */
	private int nextID; // the next available ID number for the requirements that are added.
	
	//the static object to allow the requirement model to be 
	private static RequirementModel instance; 

	/**
	 * Constructs an empty list of requirements for the project
	 */
	/*private RequirementModel (){
		requirements = new ArrayList<Requirement>();
		nextID = 0;
	}*/
	
	/**
	 * @return the instance of the requirement model singleton.
	 */
	public static RequirementModel getInstance()
	{
		if(instance == null)
		{
			instance = new RequirementModel();
		}
		return instance;
	}
	
	/**
	 * Adds a single requirement to the requirements of the project
	 * 
	 * @param newReq The requirement to be added to the list of requirements in the project
	 */
	public void addRequirement(Requirement newReq){
		// add the requirement
		add(newReq);
		try{
			AddRequirementController.getInstance().addRequirement(newReq);
			/*
			ViewEventController.getInstance().refreshTable();
			ViewEventController.getInstance().refreshTree();
			*/
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Removes the requirement with the given ID
	 * 
	 * @param removeId The ID number of the requirement to be removed 
	 * from the list of requirements in the project
	 */
	public void removeRequirementFromModel(Requirement toRemove){
		removeFromModel(toRemove);
	}
	
	/**
	 * 
	 * Provides the next ID number that should be used for a new requirement that is created.
	 * 
	
	 * @return the next open id number
	 */
	public int getNextID()
	{
		return nextID++;
	}

	/**
	 * Removes all requirements from this model
	 * 
	 * NOTE: One cannot simply construct a new instance of
	 * the model, because other classes in this module have
	 * references to it. Hence, we manually remove each requirement
	 * from the model.
	 */
	/*public void emptyModel() {
		int oldSize = getSize();
		Iterator<Requirement> iterator = requirements.iterator();
		while (iterator.hasNext()) {
			iterator.next();
			iterator.remove();
		}
		fireIntervalRemoved(this, 0, Math.max(oldSize - 1, 0));
	}*/
	
	/**
	 * Adds the given array of requirements to the list
	 * 
	 * @param requirements the array of requirements to add
	 */
	public void addRequirements(Requirement[] requirements) {
		updateRequirements(requirements);
		/*for (int i = 0; i < requirements.length; i++) {
			if (!this.requirements.contains(requirements[i].getId())) {
				this.requirements.add(requirements[i]);
				if(requirements[i].getId() >= nextID) nextID = requirements[i].getId() + 1;
			}
		}
		fireIntervalAdded(this, 0, Math.max(getSize() - 1, 0));*/
		/*
		ViewEventController.getInstance().refreshTable();
		ViewEventController.getInstance().refreshTree();
		*/
	}
	
	private synchronized void updateRequirements(Requirement[] requirements) {
		boolean changes = updateModels(requirements);

		if (changes) { // Only repaint game tree if the model has changed
		} else {
		}
	}

	/*public void addRequirements(List<Requirement> requirements, UUID gameID) {
		for (Requirement r : requirements) {
			if (!this.requirements.contains(r.getId())) {
				r.setGameID(gameID);
				this.requirements.add(r);
				if(r.getId() >= nextID) nextID = r.getId() + 1;
				add(r);
				try {
					AddRequirementController.getInstance().addRequirement(r);
				} catch (Exception e) {
					System.err.println("WARNING: FAILED TO ADD GAME TO SERVER: "
							+ r.getName());
				}

			}
		}
		fireIntervalAdded(this, 0, Math.max(getSize() - 1, 0));
	}*/
	
	/**
	 * Checks if the list of requirements has a requirement with the id passed
	 * @param id the id that is being checked
	 * @return true if a requirement with id exists in the list of requirements
	 */
	public boolean contains(int id) {
		/*for (Requirement r: requirements) {
			if (r.getId() == id)
				return true;
		}*/
		return false;
	}

	/**
	 * Returns the list of the requirements
	
	 * @return the requirements held within the requirementmodel.
	 */
	public List<Requirement> getRequirements() {
		return list;
	}

	public Requirement getRequirementById(UUID id) throws NotFoundException {
		return getModelById(id);
	}

	@Override
	public void update(ObservableModel o, Object arg) {
		System.out.println("I'm here with: " + o.toString());
		if (o instanceof Requirement) {
			try {
				UpdateRequirementController.getInstance().updateRequirement((Requirement) o);
				System.out.println("A game is being updated: "
						+ ((Requirement) o).getName());
			} catch (Exception e) {
				System.err.println("The network has not been instantiated");
			}
		} else {
			System.err
					.println("GAME MODEL ATTEMPTED TO UPDATE SOMETHING NOT A GAME");
		}
	}
	
	public boolean isServerUpdating() {
		return serverUpdating;
	}
}
