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
 * Implemented by any class that can display errors on it.
 * This allows a class to be passed to an IDataField as a parameter to show errors
 * 
 * @author Bobby Drop Tables
 */
public interface IErrorView {
	
	/**
	 * set text
	 * @param string text
	 */
	void setText(String text);

}
