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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.abstractmodel.AbstractStorageModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.abstractmodel.ObservableModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.controllers.AddPPRequirementController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.controllers.UpdatePPRequirementController;


//import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.ViewEventController;

/**
 * List of Requirements being pulled from the server
 * 
 * @author tianchanggu
 *
 */
@SuppressWarnings("serial")
public class PPRequirementModel extends AbstractStorageModel<PPRequirement>{
	
	protected PPRequirementModel() {
		super(new ArrayList<PPRequirement>());
		nextID = 0;
		// TODO Auto-generated constructor stub
	}

	/**
	 * The list in which all the requirements for a single project are contained
	 */
	private int nextID; // the next available ID number for the requirements that are added.
	
	//the static object to allow the requirement model to be 
	private static PPRequirementModel instance; 

	/**
	 * Constructs an empty list of requirements for the project
	 */
	/*private PPRequirementModel (){
		requirements = new ArrayList<PPRequirement>();
		nextID = 0;
	}*/
	
	/**
	 * @return the instance of the requirement model singleton.
	 */
	public static PPRequirementModel getInstance()
	{
		if(instance == null)
		{
			instance = new PPRequirementModel();
		}
		return instance;
	}
	
	/**
	 * Adds a single requirement to the requirements of the project
	 * 
	 * @param newReq The requirement to be added to the list of requirements in the project
	 */
	public void addRequirement(PPRequirement newReq){
		// add the requirement
		add(newReq);
		try{
			AddPPRequirementController.getInstance().addRequirement(newReq);
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
	/*public PPRequirement getRequirement(int id)
	{
		PPRequirement temp = null;
		// iterate through list of requirements until id is found
		for (int i=0; i < requirements.size(); i++){
			temp = requirements.get(i);
			if (temp.getId() == id){
				break;
			}
		}
		return temp;
	}*/
	
	/**
	 * Gets a requirement from the list of requirements
	 * by the name of the requirement
	 * @param name the name of the requirement
	 * @return the requirement with given name
	 */
	/*public PPRequirement getRequirement(String name) {
		PPRequirement req = null;
		for (PPRequirement r: requirements) {
			if (r.getName().equals(name))
				req = r;
		}
		return req;
	}*/
	
	/**
	 * Removes the requirement with the given ID
	 * 
	 * @param removeId The ID number of the requirement to be removed 
	 * from the list of requirements in the project
	 */
	public void removeRequirementFromModel(PPRequirement toRemove){
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
	 * This function takes an index and finds the requirement in the list of requirements
	 * for the project. Used internally by the JList in NewRequirementModel.
	 * 
	 * @param index The index of the requirement to be returned
	
	
	
	 * @return the requirement associated with the provided index * 
	 * @see javax.swing.ListModel#getElementAt(int) * 
	 * @see javax.swing.ListModel#getElementAt(int) * 
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	/*@Override
	public PPRequirement getElementAt(int index) {
		return requirements.get(requirements.size() - 1 - index);
	}*/

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
		Iterator<PPRequirement> iterator = requirements.iterator();
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
	public void addRequirements(PPRequirement[] requirements, UUID gameID) {
		for(PPRequirement r : requirements) {
			r.setGameID(gameID);
		}
		updateRequirements(requirements);
		/*for (int i = 0; i < requirements.length; i++) {
		for (int i = 0; i < requirements.length; i++) {
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
	
	private synchronized void updateRequirements(PPRequirement[] allRequirements) {
		boolean changes = updateModels(allRequirements);

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
	/*public boolean contains(int id) {
		for (PPRequirement r: requirements) {
			if (r.getId() == id)
				return true;
		}
		return false;
	}*/

	/**
	 * Returns the list of the requirements
	
	 * @return the requirements held within the requirementmodel.
	 */
	public List<PPRequirement> getRequirements() {
		return list;
	}

	public PPRequirement getRequirementById(UUID id) throws NotFoundException {
		return getModelById(id);
	}

	@Override
	public void update(ObservableModel o, Object arg) {
		System.out.println("I'm here with: " + o.toString());
		if (o instanceof PPRequirement) {
			try {
				UpdatePPRequirementController.getInstance().updatePPRequirement((PPRequirement) o);
				System.out.println("A game is being updated: "
						+ ((PPRequirement) o).getName());
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
