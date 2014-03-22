package edu.wpi.cs.wpisuitetng.modules.planningpoker.models;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

/**
 * Basic Game class that contains the data to be store for a Game
 * 
 * @author jonathanleitschuh
 */
public class Game extends AbstractModel{
	
	private int id;
	
	private boolean hasTimeLimit;
	
	//TODO find out how to implement existing module classes
	//private List<Requirement> requirements;

	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toJSON() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean identify(Object o) {
		// TODO Auto-generated method stub
		return null;
	}

}
