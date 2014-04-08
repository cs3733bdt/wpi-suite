package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components;

public interface IDataField {
	
	
	/**
	 * Runs over all of this DataFields subcomponents and validates all of the fields.
	 * This method throws/displays any errors if there are any problems with the users input.
	 * 
	 * @param warningField The external warning field that you want to indicate the error to
	 * @return true when all of the fields in this component are valid
	 */
	public boolean verifyField(IErrorView warningField);

}
