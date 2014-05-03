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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.NumberTextField;

/**
 * Allows the NumberJTextField to display custom errors you can set manually.
 * This is passed as a parameter in a setter on the NumberJTextField class
 * 
 * @author jonathanleitschuh
 * @see NumberJTextField
 * 
 */
public class NumberFieldCustomErrorMessage {
	/** The error shown when the number is too long */
	public final String STRING_TOO_HIGH;

	public final String STRING_TOO_LOW;
	/** The error shown when field is empty */
	public final String STRING_NOT_EMPTY;
	/** The error shown when you attempt to enter something that isn't an error */
	public final String STRING_NOT_NUMBER;

	/**
	 * Constructs the NumberFieldCustomError. This can be passed to the the
	 * NumberJTextField in order to use custom errors.
	 * 
	 * @param too_long
	 *            The error if the number is too long
	 * @param too_short
	 *            TODO
	 * @param not_empty
	 *            The error if the field is empty
	 * @param not_number
	 *            The try to enter something that isn't a number
	 * @param too_low
	 *            The number is too low
	 */
	public NumberFieldCustomErrorMessage(String too_long, String too_low,
			String not_empty, String not_number) {
		STRING_TOO_HIGH = too_long;
		STRING_TOO_LOW = too_low;
		STRING_NOT_EMPTY = not_empty;
		STRING_NOT_NUMBER = not_number;
	}

	/**
	 * Default constructs the NumberFieldCustomError. This uses has the errors
	 * use the default errors for this field. If no NumberFieldCustomError is
	 * passed to the NumberJTextField then this version of the object is used.
	 */
	public NumberFieldCustomErrorMessage() {
		STRING_TOO_HIGH = "You can not enter a number greater than ";
		STRING_TOO_LOW = "You can not enter a number less than ";
		STRING_NOT_EMPTY = "The number field can not be empty";
		STRING_NOT_NUMBER = "You can only enter numbers here";
	}

}
