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
package edu.wpi.cs.wpisuitetng.modules.planningpoker.abstractmodel;

import java.util.UUID;

/**
 * @author jonathanleitschuh
 * This interface allows this object to get stored in an instance of an AbstractStorageModel.
 *
 * @param <T> 
 * 		The class that you will be copying from.
 * 		Should be the same class that this interface is being applied to
 */
public interface IStorageModel <T>{
	/**
	 * @param toCopyFrom the object to copy the data from
	 * @return true if changes are made to the existing IStorageModel
	 */
	boolean copyFrom(T toCopyFrom);
	
	/**
	 * Retrieves the name of this Model
	 * @return the name of the object
	 */
	String getName();
	
	/**
	 * Retrieves the UUID of this model
	 * @return the UUID for this object
	 */
	UUID getIdentity();
}
