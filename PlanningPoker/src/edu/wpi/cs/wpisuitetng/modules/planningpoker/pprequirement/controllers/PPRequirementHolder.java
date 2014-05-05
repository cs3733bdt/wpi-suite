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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.controllers;

import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.exceptions.DBModelNotInstantiatedException;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.models.PPRequirement;

public class PPRequirementHolder {
	private static PPRequirementHolder instance = null;
	
	private List<PPRequirement> list = null;
	
	/**
	 * 
	 * @return an instance of PPRequirementHolder
	 */
	public static PPRequirementHolder getInstance(){
		if(instance == null){
			instance = new PPRequirementHolder();
		}
		return instance;
	}
	
	/**
	 * 
	 * @return the list of the PPRequirement
	 * @throws DBModelNotInstantiatedException
	 */
	public List<PPRequirement> getRequirements() throws DBModelNotInstantiatedException{
		if(list != null){
			return list;
		}
		throw new DBModelNotInstantiatedException();
	}
	
	public void setRequirements(List<PPRequirement> list){
		this.list = list;
	}
}
