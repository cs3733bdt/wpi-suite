/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: TomPaolillo
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.game.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.requirement.Requirement;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;

/**
 * @author TomPaolillo
 * @version $Revision: 1.0 $
 */
public class CreateGameButtonPanel extends ToolbarGroupView{
	
	// initialize the main view toolbar buttons
		private JButton createGameButton = new JButton("Create Game");
		/*
		 * Temporary join game button until tree has clickable functionality
		 */
		private JButton joinGameButton = new JButton("  Join Game  ");
		private final JPanel contentPanel = new JPanel();
	
	public CreateGameButtonPanel(){
		super("");
		
		this.contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		//35
		this.setPreferredSize(new Dimension(110,35));
		this.createGameButton.setVerticalAlignment(SwingConstants.TOP);
		this.createGameButton.setHorizontalAlignment(SwingConstants.CENTER);
		this.joinGameButton.setVerticalAlignment(SwingConstants.BOTTOM);
		this.joinGameButton.setHorizontalAlignment(SwingConstants.CENTER);
		
		// the action listener for the Create Game Button
		createGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// bring up a create game panel
					ViewEventController.getInstance().createGame();
			}
		});		
		
		/*
		 * The action listener for the Join Game Button
		 */
		joinGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// bring up an active game panel
				ViewEventController.getInstance().joinGame(new Game("An awesome game with test Data", "This should be in the description area", "Steve", new ArrayList<Requirement>(), false, true));
			}
		});	
			
		contentPanel.add(createGameButton);
		//contentPanel.setOpaque(false);
		
		//contentPanel.add(joinGameButton);
		contentPanel.setOpaque(false);
		
		this.add(contentPanel);
	}
	/**
	 * Method getCreateGameButton.
	
	 * @return JButton */
	public JButton getCreateGameButton() {
		return createGameButton;
	}
	
	/*
	 * Method getJoinGameButton
	 */
	public JButton getJoinGameButton() {
		return joinGameButton;
	}
	
}
