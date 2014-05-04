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

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SpringLayout;

public class UserProgress extends JPanel {
	
	private final JProgressBar userProgress = new JProgressBar(0, 1000);
	
	private JLabel userNameLabel;
	
	private String username;
	
	public UserProgress(String name){
		this.username = name;
		build();
	}
	
	public void build(){
		SpringLayout layout = new SpringLayout();
		setLayout(layout);
		
		userNameLabel = new JLabel(username);
		userNameLabel.setPreferredSize(new Dimension(80, 20));
		
		userProgress.setStringPainted(true);
		
		add(userNameLabel);
		add(userProgress);
		
		layout.putConstraint(SpringLayout.NORTH, userNameLabel, 3, SpringLayout.NORTH, this);	
		layout.putConstraint(SpringLayout.WEST, userNameLabel, 2, SpringLayout.WEST, this);
		
		layout.putConstraint(SpringLayout.NORTH, userProgress, 0, SpringLayout.NORTH, this);	
		layout.putConstraint(SpringLayout.WEST, userProgress, 15, SpringLayout.EAST, userNameLabel);
		layout.putConstraint(SpringLayout.EAST, userProgress, -2, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, userProgress, 0, SpringLayout.SOUTH, this);
		
		setPreferredSize(new Dimension(50, 25));
	}
}