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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.Border;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation.NewLeftHalfCreateGamePanel;

/**
 * 
 * @author TomPaolillo
 *
 */

public class NewSaveGameButtonPanel extends JPanel{
	
	private JButton saveGameButton = new JButton("Save Game");	
	
	public NewSaveGameButtonPanel(NewLeftHalfCreateGamePanel panel){
		
		SpringLayout layout = new SpringLayout(); 
		setLayout(layout);
		
		/*setupActionListeners();*/
		
		add(saveGameButton);
		
		layout.putConstraint(SpringLayout.NORTH, saveGameButton, 0, SpringLayout.NORTH, this);	//Adds the end date field underneath the radio buttons panel
		layout.putConstraint(SpringLayout.SOUTH, saveGameButton, 0, SpringLayout.SOUTH, this);	
		layout.putConstraint(SpringLayout.WEST, saveGameButton, 0, SpringLayout.WEST, this);					//Makes sure the left side of the panel stretches with the left side of the container
		layout.putConstraint(SpringLayout.EAST, saveGameButton, 0, SpringLayout.EAST, this);
		
		setPreferredSize(saveGameButton.getPreferredSize());
	}
	
	/**
	 * Method getSaveGameButton.
	 * @return JButton
	 */
	
	public JButton getSaveGameButton() {
		return saveGameButton;
	}
	
	/*void setupActionListeners(){
		saveGameButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				parentPanel.SaveGamePressed();
			}
		});
	}*/
	
}
