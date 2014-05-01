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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;

/**
 * creates a help button that the user can use if they need help with Planning Poker
 * @author Kevin Zhao
 *
 */
public class HelpButtonPanel extends ToolbarGroupView {

	//initialize the buttons
	private JButton helpIcon = new JButton("Help");
	
	private final JPanel contentPanel = new JPanel();
	
	public HelpButtonPanel(){
		super("");
		
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		
		setPreferredSize(new Dimension(120,50));
		
		helpIcon.setHorizontalAlignment(SwingConstants.CENTER);
		try {
	        Image img = ImageIO.read(getClass().getResource("help.png"));
	        helpIcon.setIcon(new ImageIcon(img));
		    
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		// the action listener for the Create Deck Icon
		helpIcon.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			//JOptionPane.showMessageDialog(null, "This is not functional yet.", "Dummy Button", 1);
			ViewEventController.getInstance().openHelp();
		}
		});
				
		contentPanel.add(helpIcon);
		//contentPanel.setOpaque(false);
		
		contentPanel.setOpaque(false);
		
		add(contentPanel);
		helpIcon.setEnabled(false);
	}
	
	/**
	 * getter for the help icon
	 * 
	 * @return the help icon
	 */
	
	public JButton gethelpIcon() {
		return helpIcon;
	}

	public void disableHelpButton() {
		helpIcon.setEnabled(false);
	}
	
	public void enableHelpButton() {
		helpIcon.setEnabled(true);
	}

}
