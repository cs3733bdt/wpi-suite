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
 * TODO: add documentation
 * @author Bobby Drop Tables
 *
 */
public interface IDataField {
	
	
	/**
	 * Runs over all of this DataFields subcomponents and validates all of the fields.
	 * This method throws/displays any errors if there are any problems with the users input.
	 * 
	 * @param warningField The external warning field that you want to indicate the error to
	 * @return true when all of the fields in this component are valid
	 */
	boolean validateField(IErrorView warningField);

}
