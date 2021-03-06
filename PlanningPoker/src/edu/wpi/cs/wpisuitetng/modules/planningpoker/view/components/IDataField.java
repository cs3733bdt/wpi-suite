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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components;

/**
 * This is meant to be implemented on GUI fields to make sure that data can be validated properly
 * Please only apply this to GUI fields
 * @author Bobby Drop Tables
 */
public interface IDataField {
	
	/**
	 * Runs over all of this DataFields subcomponents and validates all of the fields.
	 * This method throws/displays any errors if there are any problems with the users input.
	 * <p>
	 * WARNING:
	 * Be sure that all implementations of this method EXCEPT for the highest level call on this
	 * method NEVER set the warningField to the empty string
	 * 
	 * @param warningField
	 * 				The external warning field that you want to indicate the error to.
	 * See the warning above on the use of this warningField
	 * @param showLabel
	 * 				to show a label if the text is invalid
	 * @param showBox
	 * 				to show a box around this field if the field is invalid
	 * @return true when all of the fields in this component are valid
	 */
	boolean validateField(IErrorView warningField, boolean showLabel, boolean showBox);
	
	
	/**
	 * Runs over all of this DataFields subcomponents and checks to see if there are any
	 * changes since this panel was instantiated.
	 * @return true when the user has made changes to the field since it was created or set
	 */
	boolean hasChanges();
}
