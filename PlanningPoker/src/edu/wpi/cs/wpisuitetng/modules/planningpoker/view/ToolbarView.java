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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.DefaultToolbarView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons.CreateGameButtonPanel;

/**
 * Sets up the upper toolbar of the PlanningPoker Window
 * 
 * 
 * @author jonathanleitschuh
 */
public class ToolbarView extends DefaultToolbarView {
	
	public CreateGameButtonPanel createGameButton = new CreateGameButtonPanel();
	
	/**
	 * Creates and positions the buttons/other information in the tool bar
	 */
	public ToolbarView(boolean visible){
		this.addGroup(createGameButton);
	    //this.setBorder(BorderFactory.createLineBorder(Color.blue, 2)); // add a border so you can see the panel
	    this.repaint();
	    
	}
}