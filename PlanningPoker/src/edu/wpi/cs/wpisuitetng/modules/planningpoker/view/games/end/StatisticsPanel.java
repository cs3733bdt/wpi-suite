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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.end;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirement.models.Requirement;

/**
 * @author TomPaolillo
 */
public class StatisticsPanel extends JPanel{
	Game activeGame;
	Requirement activeRequirement;
	
	/**
	 * Set the userStoryDesc equal to the description of the requirement being
	 * selected in the table
	 */
	private JTextArea userStoryDesc = new JTextArea();
	/**
	 * The estText is needed when the user inputs their estimate, since it must
	 * be added to the server
	 */
	
	private int minEstimate;
	private int maxEstimate;
	private int averageEstimate;
	private int yourEstimate; 
	
	private JLabel minLabel = new JLabel("Minimum Estimate: 0");
	private JLabel maxLabel = new JLabel("Maximum Estimate: 0");
	private JLabel averageLabel = new JLabel("Average Estimate: 0");
	private JLabel yourLabel = new JLabel("Your Estimate: 0");
	
	Container overviewPanel = new Container();
 
	/**
	 * If the ArrayList passed in is empty it will use the default deck
	 * @param game 
	 * @param requirement 
	 */
	public StatisticsPanel(Game game, Requirement requirement){

		super(new GridBagLayout());

		this.setMinimumSize(new Dimension(580, 200));
		this.repaint();
		this.invalidate();
		this.revalidate();

		activeGame = game;
		activeRequirement = requirement;
		
		//this.overviewPanel =  new Container();
		overviewPanel.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();

		/**
		 * Creates and adds the user story text area to the view.
		 */
		userStoryDesc.setText(requirement.getDescription());
		
		JScrollPane userStoryPane = new JScrollPane(userStoryDesc);
		userStoryDesc.setLineWrap(true);
		userStoryDesc.setEditable(false);
		c.anchor = GridBagConstraints.CENTER;
		c.gridwidth = 4;
		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 1;
		c.weighty = 1;
		userStoryPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		userStoryPane.setMinimumSize(new Dimension(580, 150));
		userStoryPane.setPreferredSize(new Dimension(580, 150));
		overviewPanel.add(userStoryPane, c);
		
		c.anchor = GridBagConstraints.LINE_START;
		c.gridwidth = 1;
		c.weightx = 0;
		c.gridx = 0;
		c.gridy = 5;
		overviewPanel.add(yourLabel,c);
		
		c.gridx = 0;
		c.gridy = 6;
		overviewPanel.add(averageLabel,c);
		
		c.anchor = GridBagConstraints.LINE_END;
		c.gridx = 3;
		c.gridy = 5;
		overviewPanel.add(minLabel,c);
		
		c.gridx = 3;
		c.gridy = 6;
		overviewPanel.add(maxLabel,c);
		
		
		c.anchor = GridBagConstraints.CENTER;
		c.insets= new Insets(0, 0, 0, 0);
		c.gridwidth = 10;
		c.gridheight = 2;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		this.add(overviewPanel, c);
		
		this.setMinimumSize(new Dimension(580, 200));
		this.repaint();
		this.invalidate();
		this.revalidate();

	}
}
