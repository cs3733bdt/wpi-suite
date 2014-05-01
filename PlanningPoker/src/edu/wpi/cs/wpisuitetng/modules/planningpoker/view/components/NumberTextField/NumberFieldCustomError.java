package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.NumberTextField;

public class NumberFieldCustomError {
	/** The error shown when the number is too long */
	public final String STRING_TOO_LONG;
	/** The error shown when field is empty */
	public final String STRING_NOT_EMPTY;
	/** The error shown when you attempt to enter something that isn't an error */
	public final String STRING_NOT_NUMBER;

	/**
	 * Constructs the NumberFieldCustomError.
	 * This can be passed to the the NumberJTextField in order to use custom errors.
	 * @param too_long
	 * 			The error if the number is too long
	 * @param not_empty
	 * 			The error if the field is empty
	 * @param not_number
	 * 			The try to enter something that isn't a number
	 */
	public NumberFieldCustomError(String too_long, String not_empty, String not_number) {
		STRING_TOO_LONG = too_long;
		STRING_NOT_EMPTY = not_empty;
		STRING_NOT_NUMBER = not_number;
	}
	
	
	
	/**
	 * Default constructs the NumberFieldCustomError.
	 * This uses has the errors use the default errors for this field.
	 * If no NumberFieldCustomError is passed to the NumberJTextField
	 * then this version of the object is used.
	 */
	public NumberFieldCustomError(){
		STRING_TOO_LONG = "You can not enter a number greater than ";
		STRING_NOT_EMPTY = "The Field can not be empty";
		STRING_NOT_NUMBER= "You can only enter numbers here";
	}

}
