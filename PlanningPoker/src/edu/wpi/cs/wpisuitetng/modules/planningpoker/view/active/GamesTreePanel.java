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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.tree.GameTree;
/**
 * @author TomPaolillo
 *
 */
public class GamesTreePanel extends JPanel {

	private JPanel gamesPanel = new JPanel();
	/**
	 * Sets up the left hand panel of the overview
	 */
	public GamesTreePanel()
	{
		this.gamesPanel.setLayout(new BoxLayout(gamesPanel, BoxLayout.Y_AXIS));		
		this.gamesPanel.add(new GameTree());
		this.add(gamesPanel);
	}
}