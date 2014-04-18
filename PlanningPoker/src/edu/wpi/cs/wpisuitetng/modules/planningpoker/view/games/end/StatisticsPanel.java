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

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirement.models.Requirement;
/**
 * used to display the end game statistics upon ending a game
 * @author TomPaolillo
 */
public class StatisticsPanel extends JScrollPane{
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
	private int mean;
	private int stDev;
	private int median;
	
	private ActiveStatisticsPanel statTable;
//	
//	private JLabel minLabel = new JLabel("Minimum Estimate: 0");
//	private JLabel maxLabel = new JLabel("Maximum Estimate: 0");
//	private JLabel averageLabel = new JLabel("Average Estimate: 0");
//	private JLabel yourLabel = new JLabel("Your Estimate: 0");
	
	Container overviewPanel = new Container();
 
	/**
	 * If the ArrayList passed in is empty it will use the default deck
	 * @param game 
	 * @param requirement 
	 */
	public StatisticsPanel(Game game) {
		
		SpringLayout layout = new SpringLayout();
		
		overviewPanel.setLayout(layout);

		activeGame = game;
		
		activeRequirement = game.getRequirements().get(0); //default to first requirement //TODO dependent on the click
		
		statTable = initializeTable();
		JScrollPane statsPanel = new JScrollPane(statTable);
	 //   statsPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		

		/**
		 * Creates and adds the user story text area to the view.
		 */
		userStoryDesc.setText(game.getRequirements().get(0).getDescription());
		
		JScrollPane userStoryPane = new JScrollPane(userStoryDesc);

		layout.putConstraint(SpringLayout.NORTH, userStoryPane, 5, SpringLayout.NORTH, overviewPanel);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, userStoryPane, 0, SpringLayout.HORIZONTAL_CENTER, overviewPanel);
		
		
		userStoryDesc.setLineWrap(true);
		userStoryDesc.setEditable(false);
	//	userStoryPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		userStoryPane.setMinimumSize(new Dimension(580, 150));
		overviewPanel.add(userStoryPane);
		

		layout.putConstraint(SpringLayout.NORTH, statsPanel, 5, SpringLayout.SOUTH, userStoryPane);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, statsPanel, 5, SpringLayout.HORIZONTAL_CENTER, overviewPanel);
		
		overviewPanel.add(statsPanel);
		
	//	setMinimumSize(new Dimension(580, 200));
		repaint();
		invalidate();
		revalidate();
		
		add(overviewPanel);
		
		setViewportView(overviewPanel);
	}
	
	/**
	 * Instantiates this table
	 * @return the ActiveGamesTable
	 */
	private ActiveStatisticsPanel initializeTable() {
		String[] columnNames2 = {"Mean", "Standard Deviation", "Median", "Max", "Min" };
		Object[][] data2 = {};
		return new ActiveStatisticsPanel(data2, columnNames2);
	}
}
