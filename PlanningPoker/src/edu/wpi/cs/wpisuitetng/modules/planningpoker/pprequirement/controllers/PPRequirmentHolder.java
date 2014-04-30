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

public class PPRequirmentHolder {
	private static PPRequirmentHolder instance = null;
	
	private List<PPRequirement> list = null;
	
	public static PPRequirmentHolder getInstance(){
		if(instance == null){
			instance = new PPRequirmentHolder();
		}
		return instance;
	}
	
	public List<PPRequirement> getRequirments() throws DBModelNotInstantiatedException{
		if(list != null){
			return list;
		}
		throw new DBModelNotInstantiatedException();
	}
	
	public void setRequirments(List<PPRequirement> list){
		this.list = list;
	}
}
