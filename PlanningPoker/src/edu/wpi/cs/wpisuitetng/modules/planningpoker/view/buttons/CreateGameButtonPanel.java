/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: TomPaolillo
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
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
		private JButton createGameIcon = new JButton("Create Game");
		/*
		 * Temporary join game button until tree has clickable functionality
		 */
		
		private final JPanel contentPanel = new JPanel();
	
	public CreateGameButtonPanel(){
		super("");
		
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		
		this.setPreferredSize(new Dimension(175,50)); 
		
		
		createGameIcon.setHorizontalAlignment(SwingConstants.CENTER);
	   try {
	        Image img = ImageIO.read(getClass().getResource("Start_game_button.png"));
	        createGameIcon.setIcon(new ImageIcon(img));
		    
		} catch (IOException ex) {
			ex.printStackTrace();
		}		
		
		// the action listener for the Create Game Icon
		createGameIcon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// bring up a create requirement pane if not in Multiple Requirement Editing Mode
				//if (!ViewEventController.getInstance().getOverviewTable().getEditFlag()) {
					ViewEventController.getInstance().createGame();
			//	}
			}
		});		
		
		contentPanel.add(createGameIcon);
		//contentPanel.setOpaque(false);
		
		contentPanel.setOpaque(false);
		
		this.add(contentPanel);
	}
	/**
	 * Method getCreateGameIcon.
	
	 * @return JButton */
	public JButton getCreateGameIcon() {
		return createGameIcon;
	}
	
}
