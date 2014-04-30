package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.NumberTextField;

public class NumberFieldCustomError {
	public final String STRING_TOO_LONG;
	public final String STRING_NOT_EMPTY;
	public final String STRING_NOT_NUMBER;

	public NumberFieldCustomError(String too_long, String not_empty, String not_number) {
		STRING_TOO_LONG = too_long;
		STRING_NOT_EMPTY = not_empty;
		STRING_NOT_NUMBER = not_number;
	}
	
	public NumberFieldCustomError(){
		STRING_TOO_LONG = "You can not enter a number greater than ";
		STRING_NOT_EMPTY = "The Field can not be empty";
		STRING_NOT_NUMBER= "You can only enter numbers here";
	}

}
