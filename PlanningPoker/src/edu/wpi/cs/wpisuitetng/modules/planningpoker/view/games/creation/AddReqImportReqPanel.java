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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

//import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation.AddRequirementsPanel;

public class AddReqImportReqPanel extends JPanel {
	
	private JButton addReq;
	
	private JButton importReq;
	
	/**
	 * creates the panel for the Import and Add Requirements button
	 * @param reqPanel the requirements panel that the buttons will be displayed on
	 */
	public AddReqImportReqPanel(final AddRequirementsPanel reqPanel) {
		
		SpringLayout layout = new SpringLayout();
		setLayout(layout);
		
		/**
		 * Initializes Add Requirement button and sets its action listener
		 */
		addReq = new JButton("Add Requirement");
		addReq.addActionListener(new ActionListener() {
			 @Override
			public void actionPerformed(ActionEvent e) {
				 /*view.addRequirement(new Requirement(nameArea.getText(), descArea.getText()));
				 nameArea.setText("");
				 descArea.setText("");*/
				 reqPanel.getCreateReqPanel().setVisible(true);
				 reqPanel.getImportReqPanel().setVisible(true);
				 reqPanel.getCurrentReqsTable().setVisible(false);
			 }
		});
		
		importReq = new JButton("Import Requirement");
		importReq.addActionListener(new ActionListener() {
			 @Override
			public void actionPerformed(ActionEvent e) {
				 /*view.addRequirement(new Requirement(nameArea.getText(), descArea.getText()));
				 nameArea.setText("");
				 descArea.setText("");*/
				 reqPanel.getCreateReqPanel().setVisible(false);
				 reqPanel.getImportReqPanel().setVisible(true);
				 reqPanel.getCurrentReqsTable().setVisible(false);
			 }
		});
		/**
		 * adds both buttons to the panel
		 */
		add(addReq);
		add(importReq);
		
		layout.putConstraint(SpringLayout.WEST, addReq, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, importReq, -5, SpringLayout.EAST, this);
		
	}
	
	/**
	 * Sets the panel to be visible or not
	 * @param panel the panel which these buttons are located on 
	 */
	public void setCreateReqPanelVisible(AddRequirementsPanel panel) {
		this.setVisible(true);
	}
	
}