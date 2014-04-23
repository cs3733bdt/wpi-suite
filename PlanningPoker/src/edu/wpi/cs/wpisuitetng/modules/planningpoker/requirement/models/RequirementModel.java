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
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.swing.AbstractListModel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirement.controllers.AddRequirementController;


//import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.ViewEventController;

/**
 * List of Requirements being pulled from the server
 * 
 * @author tianchanggu
 *
 */
@SuppressWarnings("serial")
public class RequirementModel extends AbstractListModel<Requirement>{
	
	/**
	 * The list in which all the requirements for a single project are contained
	 */
	private List<Requirement> requirements;
	private int nextID; // the next available ID number for the requirements that are added.
	
	//the static object to allow the requirement model to be 
	private static RequirementModel instance; 

	/**
	 * Constructs an empty list of requirements for the project
	 */
	private RequirementModel (){
		requirements = new ArrayList<Requirement>();
		nextID = 0;
	}
	
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
		requirements.add(newReq);
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
	 * Returns the Requirement with the given ID
	 * 
	 * @param id The ID number of the requirement to be returned
	
	 * @return the requirement for the id or null if the requirement is not found
	 */
	public Requirement getRequirement(int id)
	{
		Requirement temp = null;
		// iterate through list of requirements until id is found
		for (int i=0; i < requirements.size(); i++){
			temp = requirements.get(i);
			if (temp.getId() == id){
				break;
			}
		}
		return temp;
	}
	
	/**
	 * Gets a requirement from the list of requirements
	 * by the name of the requirement
	 * @param name the name of the requirement
	 * @return the requirement with given name
	 */
	public Requirement getRequirement(String name) {
		Requirement req = null;
		for (Requirement r: requirements) {
			if (r.getName().equals(name))
				req = r;
		}
		return req;
	}
	
	/**
	 * Removes the requirement with the given ID
	 * 
	 * @param removeId The ID number of the requirement to be removed 
	 * from the list of requirements in the project
	 */
	public void removeRequirement(int removeId){
		// iterate through list of requirements until id of project is found
		for (int i=0; i < requirements.size(); i++){
			if (requirements.get(i).getId() == removeId){
				// remove the id
				requirements.remove(i);
				break;
			}
		}
	}

	/**
	 * Provides the number of elements in the list of requirements for the project. This
	 * function is called internally by the JList in NewRequirementPanel. Returns elements
	 * in reverse order, so the newest requirement is returned first.
	 * 
	
	
	
	 * @return the number of requirements in the project * 
	 * @see javax.swing.ListModel#getSize() * @see javax.swing.ListModel#getSize() * 
	 * @see javax.swing.ListModel#getSize()
	 */
	@Override
	public int getSize() {
		return requirements.size();
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
	 * This function takes an index and finds the requirement in the list of requirements
	 * for the project. Used internally by the JList in NewRequirementModel.
	 * 
	 * @param index The index of the requirement to be returned
	
	
	
	 * @return the requirement associated with the provided index * 
	 * @see javax.swing.ListModel#getElementAt(int) * 
	 * @see javax.swing.ListModel#getElementAt(int) * 
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	@Override
	public Requirement getElementAt(int index) {
		return requirements.get(requirements.size() - 1 - index);
	}

	/**
	 * Removes all requirements from this model
	 * 
	 * NOTE: One cannot simply construct a new instance of
	 * the model, because other classes in this module have
	 * references to it. Hence, we manually remove each requirement
	 * from the model.
	 */
	public void emptyModel() {
		int oldSize = getSize();
		Iterator<Requirement> iterator = requirements.iterator();
		while (iterator.hasNext()) {
			iterator.next();
			iterator.remove();
		}
		fireIntervalRemoved(this, 0, Math.max(oldSize - 1, 0));
	}
	
	/**
	 * Adds the given array of requirements to the list
	 * 
	 * @param requirements the array of requirements to add
	 */
	public void addRequirements(Requirement[] requirements) {
		for (int i = 0; i < requirements.length; i++) {
			if (!this.requirements.contains(requirements[i].getId())) {
				this.requirements.add(requirements[i]);
				if(requirements[i].getId() >= nextID) nextID = requirements[i].getId() + 1;
			}
		}
		fireIntervalAdded(this, 0, Math.max(getSize() - 1, 0));
		/*
		ViewEventController.getInstance().refreshTable();
		ViewEventController.getInstance().refreshTree();
		*/
	}
	
	/**
	 * Checks if the list of requirements has a requirement with the id passed
	 * @param id the id that is being checked
	 * @return true if a requirement with id exists in the list of requirements
	 */
	public boolean contains(int id) {
		for (Requirement r: requirements) {
			if (r.getId() == id)
				return true;
		}
		return false;
	}

	/**
	 * Returns the list of the requirements
	
	 * @return the requirements held within the requirementmodel.
	 */
	public List<Requirement> getRequirements() {
		return requirements;
	}

	public Requirement getRequirementById(UUID id) {
		for (Requirement r : requirements) {
			if (r.getIdentity().equals(id)) {
				return r;
			}
		}
		System.err.println("Could not fine a game with idenity: " + id);

		return null;
	}
}
