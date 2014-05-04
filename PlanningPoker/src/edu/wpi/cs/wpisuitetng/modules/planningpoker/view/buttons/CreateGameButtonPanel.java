/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Bobby Drop Tables
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
 * creates the button that allows the user to create a game
 * @author TomPaolillo
 * @version $Revision: 1.0 $
 */
public class CreateGameButtonPanel extends ToolbarGroupView{
	
	// initialize the main view toolbar buttons
	private JButton createGameIcon = new JButton("Create Game");
	
	private JButton createDeckIcon = new JButton("Create Deck");

	private final JPanel contentPanel = new JPanel();
	
	public CreateGameButtonPanel(){
		super("");
		
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		
		if (System.getProperty("os.name").contains("Linux") || System.getProperty("os.name").contains("Mac"))
			setPreferredSize(new Dimension(370 ,50));
		else
			setPreferredSize(new Dimension(336 ,50));
		
		
	   try {
	        Image img = ImageIO.read(getClass().getResource("Start_game_button.png"));
	        createGameIcon.setIcon(new ImageIcon(img));
		    
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	   
	   createDeckIcon.setHorizontalAlignment(SwingConstants.CENTER);
	   
	   try {
	        Image img = ImageIO.read(getClass().getResource("create_deck.png"));
	        createDeckIcon.setIcon(new ImageIcon(img));
		    
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
		
		createDeckIcon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// bring up a create requirement pane if not in Multiple Requirement Editing Mode
				//if (!ViewEventController.getInstance().getOverviewTable().getEditFlag()) {
					ViewEventController.getInstance().createDeck();
			//	}
			}
		});	
		
		contentPanel.add(createGameIcon);
		contentPanel.add(createDeckIcon);
		//contentPanel.setOpaque(false);
		
		contentPanel.setOpaque(false);
		
		add(contentPanel);
	}
	/**
	 * getter for the create game icon
	 * @return the create game icon 
	 */
	public JButton getCreateGameIcon() {
		return createGameIcon;
	}
	
}
