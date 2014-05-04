package edu.wpi.cs.wpisuitetng.modules.planningpoker.abstractmodel;

public interface IModelValidate {
    /**
     * 
     * @param text name of entry to check 
     * @return true if the model has an entry with the same name
     */
	boolean hasName(String text);
}
