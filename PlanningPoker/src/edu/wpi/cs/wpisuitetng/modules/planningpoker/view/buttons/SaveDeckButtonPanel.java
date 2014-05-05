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

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.decks.creation.CreateDeckPanel;

/**
 * 
 * @author Jordan Wetzel
 *
 */

public class SaveDeckButtonPanel extends JPanel{
	
	private JButton saveDeckButton = new JButton("Save Deck");
	CreateDeckPanel parent;
	
	/**
	 * 
	 * @param CreateDeckPanel panel
	 */
	public SaveDeckButtonPanel(CreateDeckPanel panel){
		parent = panel;
		
		SpringLayout layout = new SpringLayout(); 
		setLayout(layout);
		
		setupActionListeners();
		
		add(saveDeckButton);
		
		layout.putConstraint(SpringLayout.NORTH, saveDeckButton, 0, SpringLayout.NORTH, this);	//Adds the end date field underneath the radio buttons panel
		layout.putConstraint(SpringLayout.SOUTH, saveDeckButton, 0, SpringLayout.SOUTH, this);	
		layout.putConstraint(SpringLayout.WEST, saveDeckButton, 0, SpringLayout.WEST, this);					//Makes sure the left side of the panel stretches with the left side of the container
		layout.putConstraint(SpringLayout.EAST, saveDeckButton, 0, SpringLayout.EAST, this);
		
		setPreferredSize(saveDeckButton.getPreferredSize());
	}
	
	/**
	 * Method getSaveDeckButton.
	 * @return JButton
	 */
	
	public JButton getSaveDeckButton() {
		return saveDeckButton;
	}
	
	/**
	 * set up Action Listeners
	 */
	void setupActionListeners(){
		saveDeckButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				parent.SaveDeckPressed();
			}
		});
	}
	
}
