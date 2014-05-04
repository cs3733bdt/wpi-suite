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
 * Implemented on any panel that needs to have buttons that get enabled when the fields are valid
 * 
 * @author jonathanleitschuh
 *
 */
public interface IValidateButtons {
	
	/**
	 * This method is used to enable the buttons when all fields on the panel are completely valdid
	 */
	void updateButtons();
	
}
