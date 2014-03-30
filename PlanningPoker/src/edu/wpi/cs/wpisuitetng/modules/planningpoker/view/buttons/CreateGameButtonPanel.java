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

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;

/**
 * @author TomPaolillo
 * @version $Revision: 1.0 $
 */
public class CreateGameButtonPanel extends ToolbarGroupView{
	
	// initialize the main view toolbar buttons
		private JButton createGameButton = new JButton("Create Game");
		private final JPanel contentPanel = new JPanel();
	
	public CreateGameButtonPanel(){
		super("");
		
		this.contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		this.setPreferredSize(new Dimension(110,35));
		this.createGameButton.setHorizontalAlignment(SwingConstants.CENTER);
		
		// the action listener for the Create Game Button
		createGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// bring up a create game panel
					ViewEventController.getInstance().createGame();
			}
		});		
			
		contentPanel.add(createGameButton);
		contentPanel.setOpaque(false);
		

		this.add(contentPanel);
	}
	/**
	 * Method getCreateGameButton.
	
	 * @return JButton */
	public JButton getCreateGameButton() {
		return createGameButton;
	}
	
}
