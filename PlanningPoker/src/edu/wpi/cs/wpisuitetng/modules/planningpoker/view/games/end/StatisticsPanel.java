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
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirement.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.vote.models.Vote;
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
	private double mean;
	private double stDev;
	private int median;
	
	private ActiveStatisticsTable statTable;
	
	private ActiveVotesTable voteTable;
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
		
		JLabel descLabel = new JLabel("Description");
		JLabel statLabel = new JLabel("Statistics");
		JLabel votesLabel = new JLabel("Votes by User");
		
		initStats();
		
		statTable = initializeStatTable();
		voteTable = initializeVoteTable();
		statTable.getTableModel().addRow(new Object[]{mean, stDev, "0", maxEstimate, minEstimate});
		
		JScrollPane statsPanel = new JScrollPane(statTable);
		JScrollPane votePanel = new JScrollPane(voteTable);
		JScrollPane descPanel = new JScrollPane(userStoryDesc);
		
		overviewPanel.add(descLabel);
		overviewPanel.add(statLabel);
		overviewPanel.add(votesLabel);
		
		overviewPanel.add(descPanel);
		overviewPanel.add(statsPanel);
		overviewPanel.add(votePanel);
		
		/**
		 * Creates and adds the user story text area to the view.
		 */
		userStoryDesc.setText("W\nW\nW\nW\nW\nW\nW\nW\nW\nW\nW\nW\nW\nW\nW\nW\nW\nW\nW\nW\nW\nW\nW"); //game.getRequirements().get(0).getDescription());
		userStoryDesc.setEditable(false);
		userStoryDesc.setLineWrap(true);
		
		
		userStoryDesc.setPreferredSize(new Dimension(580, 150));
		descPanel.setPreferredSize(new Dimension(580, 100));
		statsPanel.setPreferredSize(new Dimension(580, 60));
		
		//Label for Desc
		layout.putConstraint(SpringLayout.NORTH, descLabel, 5, SpringLayout.NORTH, overviewPanel); //Anchor user Story to the top of panel
		layout.putConstraint(SpringLayout.WEST, descLabel, 5, SpringLayout.WEST, overviewPanel);
		
		//Constraints on the userStory Desc
		layout.putConstraint(SpringLayout.NORTH, descPanel, 5, SpringLayout.SOUTH, descLabel); 
		layout.putConstraint(SpringLayout.WEST, descPanel, 5, SpringLayout.WEST, overviewPanel);
		layout.putConstraint(SpringLayout.EAST, descPanel, -5, SpringLayout.EAST, overviewPanel); 
		
		//Constraints on the stats Label
		layout.putConstraint(SpringLayout.NORTH, statLabel, 5, SpringLayout.SOUTH, descPanel); 
		layout.putConstraint(SpringLayout.WEST, statLabel, 5, SpringLayout.WEST, overviewPanel);
		layout.putConstraint(SpringLayout.EAST, statLabel, -5, SpringLayout.EAST, overviewPanel); 
	
		
		//Constraints on the statsPanel
		layout.putConstraint(SpringLayout.NORTH, statsPanel, 5, SpringLayout.SOUTH, statLabel); //anchor top of stats panel to bottom of user story
		layout.putConstraint(SpringLayout.WEST, statsPanel, 5, SpringLayout.WEST, overviewPanel);  //Anchor stats Panel to the left side of panel
		layout.putConstraint(SpringLayout.EAST, statsPanel, -5, SpringLayout.EAST, overviewPanel); //Anchor user Story to the left side of panel
		
		//Constraints on the vote Label
		layout.putConstraint(SpringLayout.NORTH, votesLabel, 5, SpringLayout.SOUTH, statsPanel); 
		layout.putConstraint(SpringLayout.WEST, votesLabel, 5, SpringLayout.WEST, overviewPanel);
		layout.putConstraint(SpringLayout.EAST, votesLabel, -5, SpringLayout.EAST, overviewPanel); 
		
		//Constraints on the votePanel
		layout.putConstraint(SpringLayout.NORTH, votePanel, 5, SpringLayout.SOUTH, votesLabel); 
		layout.putConstraint(SpringLayout.WEST, votePanel, 5, SpringLayout.WEST, overviewPanel);  
		layout.putConstraint(SpringLayout.EAST, votePanel, -5, SpringLayout.EAST, overviewPanel); 
		layout.putConstraint(SpringLayout.SOUTH, votePanel, -10, SpringLayout.SOUTH, overviewPanel);
		
		repaint();
		invalidate();
		revalidate();
		
		setViewportView(overviewPanel);
	}
	
	private void initStats() {
		ArrayList<Integer> voteData = requirementToVotes(activeRequirement); 
		minEstimate = min(voteData);
		maxEstimate = max(voteData);
		mean = mean(voteData);
		stDev = stDev(voteData);
	}

	/**
	 * Instantiates the active stats table
	 * @return the table containing the statistics
	 */
	private ActiveStatisticsTable initializeStatTable() {
		String[] columnNames2 = {"Mean", "Standard Deviation", "Median", "Max", "Min" };
		Object[][] data2 = {};
		return new ActiveStatisticsTable(data2, columnNames2);
	}
	
	private ActiveVotesTable initializeVoteTable() {
		String[] columnNames2 = {"User Name", "Estimate"};
		Object[][] data2 = {};
		return new ActiveVotesTable(data2, columnNames2);
	}
	
	/**
	 * @param requirement
	 * @return an arrayList of the vote numbers from the passed requirement
	 */
	private ArrayList<Integer> requirementToVotes(Requirement requirement) {
		List<Vote> Votes = requirement.getVotes();
		if (Votes.size() == 0) {
			return new ArrayList<Integer>();
		}
		ArrayList<Integer> voteArray = new ArrayList<Integer>();
		for (int i = 0; i < Votes.size(); i++) {
			voteArray.add(Votes.get(i).getVoteNumber());
		}
		return voteArray;
	}
	
	/**
	 * @param Votes a list of the integer values for the votes for a given requirement
	 * @return the minimum of the array. will return -1 if the array is empty
	 */
	private int min(ArrayList<Integer> Votes) {
		int min = -1;
		
		for (int i = 0; i < Votes.size(); i++) {
			if (Votes.get(i) < min || min == -1) {
				min = Votes.get(i);
			}
		}
	
		return min;
	}
	
	/**
	 * @param Votes a list of the integer values for the votes for a given requirement
	 * @return the maximum of the array. will return -1 if the array is empty
	 */
	private int max(ArrayList<Integer> Votes) {
		int max = -1;
		
		for (int i = 0; i < Votes.size(); i++) {
			if (Votes.get(i) > max) {
				max = Votes.get(i);
			}
		}
	
		return max;
	}
	
	static double mean(ArrayList<Integer> a) {
		double sum = 0;
		int i;
		
		for(i = 0; i < a.size(); i++) {
			sum += a.get(i);
		}
		
		return sum/a.size();
	}
	
	private static double stDev(ArrayList<Integer> a) {
		double mean = mean(a);
		ArrayList<Double> numMinusMeanSquared = new ArrayList<Double>();
	//	double[] numMinusMeanSquared = new double[a.length]; 
		
		for (int i = 0; i < a.size(); i++) {
			numMinusMeanSquared.add(Math.pow((a.get(i)-mean), 2)); 
		}
		
		double sum = 0;
		
		for(int j = 0; j < numMinusMeanSquared.size(); j++) {
			sum += numMinusMeanSquared.get(j);
		}
				
		double variance = (sum / (double) numMinusMeanSquared.size());
		
		return Math.sqrt(variance);
	}
	
}
