/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Bobby Drop Tables
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.deck.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.deck.models.Deck;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.deck.models.DeckModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.deck.observers.GetDeckRequestObserver;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Used in order to get the deckss off of the database. This is used to fill the
 * DeckModel from the database on build The first call of this method is inside
 * of the DeckTree on paint method This is done in order to prevent trying get
 * the data before the network has been instantiated.
 * 
 * @author Andrew Busch
 */
public class GetDeckController implements ActionListener {
	private final GetDeckRequestObserver observer;
	private static GetDeckController instance = null;
	private Timer timer;
	private boolean isRunning = false;

	/**
	 * Gets the singleton instance of the GetDeckController
	 * 
	 * @return the current instance of the GetDeckController
	 */
	public static GetDeckController getInstance() {
		if (instance == null) {
			instance = new GetDeckController();
		}
		return instance;
	}

	/**
	 * The constructor for the GetDeckController Private in order to prevent
	 * multiple instantiations
	 */
	private GetDeckController() {
		observer = new GetDeckRequestObserver(this);
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public synchronized void actionPerformed(ActionEvent e) {
		// Send a request to the core to read/get this Deck
		final Request request = Network.getInstance().makeRequest(
				"planningpoker/deck", HttpMethod.GET);
		// add an observer to process the response
		request.addObserver(observer);
		// send the request
		request.send();
	}

	/**
	 * Sends an HTTP request to retrieve all decks from the server
	 */
	public synchronized void retrieveDecks() {
		if (!isRunning) {
			timer = new Timer(25000, this);
			timer.setInitialDelay(25000);
			timer.setCoalesce(true);
			timer.start();
			isRunning = true;
		}
		// Send a request to the core to get the decks
		final Request request = Network.getInstance().makeRequest(
				"planningpoker/deck", HttpMethod.GET);
		// add an observer to process the response
		request.addObserver(observer);
		// send the request
		request.send();
	}

	/**
	 * Add the given Decks to the local model (they were received from the
	 * core). This method is called by the GetDeckRequestObserver
	 * 
	 * @param decks
	 *            an array of Decks received from the server
	 */
	public synchronized void receivedDecks(Deck[] decks) {
		System.out.println("The size of the list returned from the server is: "
				+ decks.length);
		for (Deck deck : decks) {
			System.out
					.println("\t" + deck.getName() + " " + deck.getIdentity());
		}
		// Make sure the response was not null
		if (decks != null) {
			// add the Decks to the local model
			DeckModel.getInstance().updateDecks(decks);
		}
	}
}
