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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation.NewCreateGamePanel;

/**
 * 
 * @author TomPaolillo
 *
 */

public class NewLaunchGameButtonPanel extends JPanel{
	
	private final NewCreateGamePanel parentPanel;
	
	private JButton launchGameButton = new JButton("Launch Game");	
	
	/**
	 * Creates the Launch GameButton and attaches it's respective action listener
	 * @param panel the parent panel to call launch from
	 */
	public NewLaunchGameButtonPanel(NewCreateGamePanel panel){
		SpringLayout layout = new SpringLayout(); 
		setLayout(layout);
		
		setupActionListeners();
		
		add(launchGameButton);
		
		layout.putConstraint(SpringLayout.NORTH, launchGameButton, 0, SpringLayout.NORTH, this);	//Adds the end date field underneath the radio buttons panel
		layout.putConstraint(SpringLayout.SOUTH, launchGameButton, 0, SpringLayout.SOUTH, this);	
		layout.putConstraint(SpringLayout.WEST, launchGameButton, 0, SpringLayout.WEST, this);					//Makes sure the left side of the panel stretches with the left side of the container
		layout.putConstraint(SpringLayout.EAST, launchGameButton, 0, SpringLayout.EAST, this);
		
		setPreferredSize(launchGameButton.getPreferredSize());
		
		parentPanel = panel;
	}
	/**
	 * Method getLaunchGameButton.
	
	 * @return JButton */
	public JButton getLaunchGameButton() {
		return launchGameButton;
	}
	
	void setupActionListeners(){
		launchGameButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				parentPanel.LaunchGamePressed();
			}
		});
	}
	
}
