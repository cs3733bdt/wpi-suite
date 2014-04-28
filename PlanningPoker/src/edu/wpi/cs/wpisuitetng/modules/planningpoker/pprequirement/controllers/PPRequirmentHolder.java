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
