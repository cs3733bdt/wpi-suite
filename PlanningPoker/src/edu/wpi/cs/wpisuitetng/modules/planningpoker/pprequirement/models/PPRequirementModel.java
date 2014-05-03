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
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractListModel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.controllers.AddPPRequirementController;

/**
 * List of Requirements being pulled from the server
 * 
 * @author tianchanggu
 * 
 */
@SuppressWarnings("serial")
public class PPRequirementModel extends AbstractListModel<PPRequirement> {

	/**
	 * The list in which all the requirements for a single project are contained
	 */
	private List<PPRequirement> requirements;
	private int nextID; // the next available ID number for the requirements
						// that are added.

	// the static object to allow the requirement model to be
	private static PPRequirementModel instance = null;

	/**
	 * Constructs an empty list of requirements for the project
	 */
	private PPRequirementModel() {
		requirements = new ArrayList<PPRequirement>();
		nextID = 0;
	}

	/**
	 * @return the instance of the requirement model singleton.
	 */
	public static PPRequirementModel getInstance() {
		if (instance == null) {
			instance = new PPRequirementModel();
		}

		return instance;
	}

	/**
	 * Adds a single requirement to the requirements of the project
	 * 
	 * @param newReq
	 *            The requirement to be added to the list of requirements in the
	 *            project
	 */
	public void addRequirement(PPRequirement newReq) {
		// add the requirement
		requirements.add(newReq);
		try {
			AddPPRequirementController.getInstance().addRequirement(newReq);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the Requirement with the given ID
	 * 
	 * @param id
	 *            The ID number of the requirement to be returned
	 * @throws NullPointerException if the requirement is not found
	 * @return the requirement for the id 
	 */
	public PPRequirement getRequirement(int id) throws NullPointerException {
		// iterate through list of requirements until id is found
		for (PPRequirement r : requirements) {
			if (r.getId() == id) {
				return r;
			}
		}
		throw new NullPointerException(
				"Requirement with the given ID does not exist.");
	}

	/**
	 * Gets a requirement from the list of requirements by the name of the
	 * requirement
	 * 
	 * @param name
	 *            the name of the requirement
	 * @param description the description of the requirement
	 * @return the requirement with given name
	 */
	public PPRequirement getRequirement(String name, String description) {
		PPRequirement req = null;
		for (PPRequirement r : requirements) {
			if (r.getName().equals(name)
					&& r.getDescription().equals(description)) {
				req = r;
			}
		}
		return req;
	}

	/**
	 * Removes the requirement with the given ID
	 * 
	 * @param removeId
	 *            The ID number of the requirement to be removed from the list
	 *            of requirements in the project
	 */
	public void removeRequirement(int removeId) {
		// iterate through list of requirements until id of project is found
		for (int i = 0; i < requirements.size(); i++) {
			if (requirements.get(i).getId() == removeId) {
				// remove the id
				requirements.remove(i);
				break;
			}
		}
	}

	/**
	 * Provides the number of elements in the list of requirements for the
	 * project. This function is called internally by the JList in
	 * NewRequirementPanel. Returns elements in reverse order, so the newest
	 * requirement is returned first.
	 * 
	 * @return the number of requirements in the project *
	 * @see javax.swing.ListModel#getSize() * @see
	 *      javax.swing.ListModel#getSize() *
	 * @see javax.swing.ListModel#getSize()
	 */
	@Override
	public int getSize() {
		return requirements.size();
	}

	/**
	 * This function takes an index and finds the requirement in the list of
	 * requirements for the project. Used internally by the JList in
	 * NewRequirementModel.
	 * 
	 * @param index
	 *            The index of the requirement to be returned
	 * 
	 * 
	 * 
	 * @return the requirement associated with the provided index *
	 * @see javax.swing.ListModel#getElementAt(int) *
	 * @see javax.swing.ListModel#getElementAt(int) *
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	@Override
	public PPRequirement getElementAt(int index) {
		return requirements.get(requirements.size() - 1 - index);
	}

	/**
	 * Removes all requirements from this model
	 * 
	 * NOTE: One cannot simply construct a new instance of the model, because
	 * other classes in this module have references to it. Hence, we manually
	 * remove each requirement from the model.
	 */
	public void emptyModel() {
		int oldSize = getSize();
		Iterator<PPRequirement> iterator = requirements.iterator();
		while (iterator.hasNext()) {
			iterator.next();
			iterator.remove();
		}
		fireIntervalRemoved(this, 0, Math.max(oldSize - 1, 0));
	}

	/**
	 * Adds the given array of requirements to the list
	 * 
	 * @param requirements
	 *            the array of requirements to add
	 */
	public void addRequirements(PPRequirement[] requirements) {
		for (int i = 0; i < requirements.length; i++) {
			if (!this.requirements.contains(requirements[i].getId())
					&& !requirements[i].getFromRequirementModule()) {
				this.requirements.add(requirements[i]);
				if (requirements[i].getId() >= nextID)
					nextID = requirements[i].getId() + 1;
			} else if (requirements[i].getFromRequirementModule()
					&& !requirements[i].existsIn(this.requirements)) {
				this.requirements.add(requirements[i]);
			}
		}
		fireIntervalAdded(this, 0, Math.max(getSize() - 1, 0));
	}

	/**
	 * Checks if the list of requirements has a requirement with the id passed
	 * 
	 * @param id
	 *            the id that is being checked
	 * @return true if a requirement with id exists in the list of requirements
	 */
	public boolean contains(int id) {
		for (PPRequirement r : requirements) {
			if (r.getId() == id) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns the list of the requirements
	 * 
	 * @return the requirements held within the requirementmodel.
	 */
	public List<PPRequirement> getRequirements() {
		return requirements;
	}
}
