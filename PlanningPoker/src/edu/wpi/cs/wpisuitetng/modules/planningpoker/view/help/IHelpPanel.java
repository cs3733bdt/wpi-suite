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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.help;

import javax.swing.JLabel;

public interface IHelpPanel {

	boolean readyToRemove();
	
	/**
	 * Gets the identifier index for easy finding of the panel inside the list
	 *  int preferenceIndex = 0;
	 *	int endGameIndex = 1;
	 *	int activeGameIndex = 2;
     *  int createGameIndex = 3;
	 *	int createDeckIndex = 4;
	 * @return
	 */
	int getIdentifierIndex();
	
	JLabel addImage(String image);
}
