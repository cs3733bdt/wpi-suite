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

/*import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;*/

import javax.swing.JSplitPane;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.TabbedView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.tree.GameTree;

@SuppressWarnings("serial")
public class MainView extends JSplitPane {
	TabbedView tabView = new TabbedView();
	GameTree filterPanel = new GameTree();
	
	/**
	 * constructs a MainView
	 */
	public MainView() {
		
		
		/*Container rightPanel = new Container();
		rightPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;*/
		
		this.setRightComponent(tabView);
		this.setLeftComponent(filterPanel);
		this.setDividerLocation(180);
		
	}
	
	/**
	 * @return current TabbedView
	 */
	public TabbedView getTabbedView(){
		return tabView;
	}
}
