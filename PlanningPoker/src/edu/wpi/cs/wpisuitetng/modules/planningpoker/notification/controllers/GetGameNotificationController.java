/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Bobby Drop Tables
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.notification.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Timer;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.notification.models.GameNotification;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.notification.models.GameNotificationModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.notification.observers.GetGameNotificationRequestObserver;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Used in order to get the games off of the database. This is used to fill the
 * GameModel from the database on build The first call of this method is inside
 * of the GameTree on paint method This is done in order to prevent trying get
 * the data before the network has been instantiated.
 * 
 */

public class GetGameNotificationController implements ActionListener {
	private final GetGameNotificationRequestObserver observer;
	private static GetGameNotificationController instance = null;
	private static final Logger logger = Logger.getLogger(GetGameNotificationController.class
			.getName());
	/**
	 * Gets the singleton instance of the GetGameController
	 * 
	 * @return the current instance of the GetGameController
	 */
	public static GetGameNotificationController getInstance() {
		if (instance == null) {
			instance = new GetGameNotificationController();
		}
		return instance;
	}

	/**
	 * The constructor for the GetGameController Private in order to prevent
	 * multiple instantiations
	 */
	private GetGameNotificationController() {
		observer = new GetGameNotificationRequestObserver(this);
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public synchronized void actionPerformed(ActionEvent e) {
		// Send a request to the core to read/get this GameNotification
		final Request request = Network.getInstance().makeRequest(
				"planningpoker/gamenotification", HttpMethod.GET);
		// add an observer to process the response
		request.addObserver(observer);
		// send the request
		request.send();
	}

	/**
	 * Sends an HTTP request to retrieve all requirements
	 */
	public synchronized void retrieveGameNotifications() {
		// Send a request to the core to read/get this GameNotification
		final Request request = Network.getInstance().makeRequest(
				"planningpoker/gamenotification", HttpMethod.GET);
		// add an observer to process the response
		request.addObserver(observer);
		// send the request
		request.send();
	}

	/**
	 * Add the given GamesNotification to the local model (they were received from the
	 * core). This method is called by the GetGamesNotificationRequestObserver
	 * 
	 * @param gns
	 *            an array of GameNotifications received from the server
	 */
	public synchronized void receivedGameNotifications(GameNotification[] gns) {
		logger.log(Level.INFO,"The size of the list returned from the server is: "
				+ gns.length);
		for (GameNotification gn : gns) {
			logger.log(Level.INFO,"\t" + gn.getName() + " " + gn.getIdentity());
		}
		// Make sure the response was not null
		if (gns != null) {
			// add the Games to the local model
			GameNotificationModel.getInstance().updateGameNotifications(gns);
		}
	}
}
