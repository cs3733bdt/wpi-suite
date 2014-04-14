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

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation.CreateGamePanel;

/**
 * 
 * @author TomPaolillo
 *
 */

public class SaveGameButtonPanel extends JPanel{
	private final CreateGamePanel parentPanel;

	
	JPanel contentPanel = new JPanel();
	JButton saveGameButton = new JButton("Save Game");	
	
	public SaveGameButtonPanel(CreateGamePanel panel){
		//super("");
		
		/*this.contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		this.setPreferredSize(new Dimension(150,50));
		this.saveGameButton.setHorizontalAlignment(SwingConstants.CENTER);
		this.saveGameButton.setPreferredSize(new Dimension(150, 50));
		//this.contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		this.setPreferredSize(new Dimension(200,50));
		this.saveGameButton.setHorizontalAlignment(SwingConstants.CENTER);
		this.saveGameButton.setPreferredSize(new Dimension(125, 25));*/
		
		// the action listener for the Create Game Button
		setupActionListeners();
		
		//contentPanel.add(saveGameButton);
		//contentPanel.setOpaque(false);
		

		//this.add(contentPanel);
		this.add(saveGameButton);
		saveGameButton.setVisible(true);
		parentPanel = panel;
	}
	/**
	 * Method getSaveGameButton.
	
	 * @return JButton */
	public JButton getSaveGameButton() {
		return saveGameButton;
	}
	
	void setupActionListeners(){
		saveGameButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				parentPanel.SaveGamePressed();
			}
		});
	}
	
}
