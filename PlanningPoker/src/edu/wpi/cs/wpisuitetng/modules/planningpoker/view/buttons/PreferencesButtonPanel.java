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
 * creates a preferences button so the user can change their preferences 
 * @author Kevin Zhao
 *
 */

public class PreferencesButtonPanel extends ToolbarGroupView{

	private JButton preferencesIcon = new JButton("Preferences");

	private final JPanel contentPanel = new JPanel();

	public PreferencesButtonPanel(){
		super("");

		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));

		setPreferredSize(new Dimension(159,50));

		preferencesIcon.setHorizontalAlignment(SwingConstants.CENTER);
		try {
			Image img = ImageIO.read(getClass().getResource("preferences.png"));
			preferencesIcon.setIcon(new ImageIcon(img));

		} catch (IOException ex) {
			ex.printStackTrace();
		}

		// the action listener for the Create Deck Icon
		preferencesIcon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ViewEventController.getInstance().editPreferences();
			}
		});

		contentPanel.add(preferencesIcon);
		//contentPanel.setOpaque(false);

		contentPanel.setOpaque(false);

		add(contentPanel);
	}

	/**
	 * getter for the help icon
	 * 
	 * @return the help icon
	 */

	public JButton getpreferencesIcon() {
		return preferencesIcon;
	}
}
