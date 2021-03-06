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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.decks.creation;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.deck.models.Deck;

public interface ICreateDeckPanel {

	/**
	 * save the deck
	 * @param deck
	 */
	void saveDeck(Deck deck);
	
	/**
	 * 
	 * @return the deck
	 */
	Deck getDeck();
	
	/**
	 * disable the fields
	 */
	void disableFields();
	
}
