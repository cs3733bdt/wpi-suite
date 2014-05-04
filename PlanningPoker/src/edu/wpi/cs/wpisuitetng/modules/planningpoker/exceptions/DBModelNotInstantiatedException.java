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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.exceptions;
/**
 * Exception to be thrown if DB model cannot be instantiated
 *
 */
public class DBModelNotInstantiatedException extends Exception {

	public DBModelNotInstantiatedException() {
	}

	public DBModelNotInstantiatedException(String message) {
		super(message);
	}

	public DBModelNotInstantiatedException(Throwable cause) {
		super(cause);
	}

	public DBModelNotInstantiatedException(String message, Throwable cause) {
		super(message, cause);
	}

	public DBModelNotInstantiatedException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
