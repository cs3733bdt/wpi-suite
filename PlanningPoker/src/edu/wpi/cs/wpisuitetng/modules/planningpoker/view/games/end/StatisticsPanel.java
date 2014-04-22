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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirement.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.IDataField;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.IErrorView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.vote.models.Vote;
/**
 * used to display the end game statistics upon ending a game
 */
public class StatisticsPanel extends JScrollPane implements IDataField {
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
	
	private JLabel finalEstimateLabel;
	private JTextField finalEstimateBox;
	private JButton finalEstimateButton;
	
	private int minEstimate;
	private int maxEstimate;
	private double mean;
	private double stDev;
	private double median;
	
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
		
		
		Object[] row = makeStatRow(activeRequirement);
		
		statTable = initializeStatTable();
		voteTable = initializeVoteTable();
		statTable.getTableModel().addRow(row);
		fillVoteTable(activeRequirement);
		
		
		JScrollPane statsPanel = new JScrollPane(statTable);
		JScrollPane votePanel = new JScrollPane(voteTable);
		JScrollPane descPanel = new JScrollPane(userStoryDesc);
		
		
		finalEstimateLabel = new JLabel("Enter a final estimate here:");
		finalEstimateBox = new JTextField(4);
		finalEstimateButton = new JButton("Submit Final Estimate");
		addKeyListenerTo(finalEstimateBox);
		validateSubmitButton();
		//finalEstimateDisplay = new JLabel("Your current final Estimate is:");
		
			
		
		overviewPanel.add(descLabel);
		overviewPanel.add(statLabel);
		overviewPanel.add(votesLabel);
		
		overviewPanel.add(finalEstimateLabel);
		overviewPanel.add(finalEstimateBox);
		overviewPanel.add(finalEstimateButton);
		isUserCreator(); //sets visibility for the above 3 components
		
		overviewPanel.add(descPanel);
		overviewPanel.add(statsPanel);
		overviewPanel.add(votePanel);
		
		/**
		 * Creates and adds the user story text area to the view.
		 */
		userStoryDesc.setText(game.getRequirements().get(0).getDescription());
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
		layout.putConstraint(SpringLayout.SOUTH, votePanel, -30, SpringLayout.SOUTH, overviewPanel);
		
		//Constraints on the final estimate box
		layout.putConstraint(SpringLayout.NORTH, finalEstimateBox, 5, SpringLayout.SOUTH, votePanel); 
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, finalEstimateBox, 5, SpringLayout.HORIZONTAL_CENTER, overviewPanel); 
		
		//Constraints on the final estimate label
		layout.putConstraint(SpringLayout.EAST, finalEstimateLabel, -5, SpringLayout.WEST, finalEstimateBox); 
		layout.putConstraint(SpringLayout.NORTH, finalEstimateLabel, 5, SpringLayout.SOUTH, votePanel); 
		
		//Constraints on the final estimate Button
		layout.putConstraint(SpringLayout.WEST, finalEstimateButton, 5, SpringLayout.EAST, finalEstimateBox); 
		layout.putConstraint(SpringLayout.NORTH, finalEstimateButton, 5, SpringLayout.SOUTH, votePanel); 
		
		
		int[] test = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }; //5.5 //TODO fix
		int[] test2 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11}; //6
		
		System.out.println("Median test1 should be 5.5:" + median(test));
		System.out.println("Median test2:" + median(test2));
		
		repaint();
		invalidate();
		revalidate();
		
		setViewportView(overviewPanel);
	}
	
//	private void initStats() {
//		ArrayList<Integer> voteData = requirementToVotes(activeRequirement); 
//		minEstimate = min(voteData);
//		maxEstimate = max(voteData);
//		mean = mean(voteData);
//		stDev = stDev(voteData);
//		median = median(voteData);
//	}
	private void initStats() {
		System.out.println(activeRequirement.getVotes().size());
		System.out.println(activeGame.getName());
		
		ArrayList<Integer> voteData = requirementToVotes(activeRequirement); 
		minEstimate = min(voteData);
		maxEstimate = max(voteData);
		mean = mean(voteData);
		stDev = stDev(voteData);
		median = median(voteData);
	}
	
	private Object[] makeStatRow(Requirement requirement) {
		ArrayList<Integer> voteData = requirementToVotes(requirement); 
		Object[] row = new Object[] {mean(voteData), stDev(voteData), median(voteData), max(voteData), min(voteData), voteData.size()};
		return row;
	}

	/**
	 * Instantiates the active stats table
	 * @return the table containing the statistics
	 */
	private ActiveStatisticsTable initializeStatTable() {
		String[] columnNames2 = {"Mean", "Standard Deviation", "Median", "Max", "Min","Num Votes" };
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
		ArrayList<Integer> voteArray = new ArrayList<Integer>();
		for (int i = 0; i < Votes.size(); i++) {
			voteArray.add(Votes.get(i).getVoteNumber());
		}
		return voteArray;
	}
	
	public ArrayList<String> requirementToNames(Requirement requirement) {
		List<Vote> Votes = requirement.getVotes();
		ArrayList<String> nameArray = new ArrayList<String>();
		for (int i = 0; i < Votes.size(); i++) {
			nameArray.add(Votes.get(i).getUsername());
		}
		return nameArray;
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
	
	private double stDev(ArrayList<Integer> a) {
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

	public static double median(ArrayList<Integer> Votes) {
		if (Votes.size() == 0) {
			return 0;
		}
		if (Votes.size() == 1) {
			return Votes.get(0);
		}
		else {
			double[] a = new double[Votes.size()];
			for (int i = 0; i < Votes.size(); i++) {
				a[i] = Votes.get(i);
			}
			Arrays.sort(a);
			int mid = a.length/2;
			if (a.length % 2 == 0) {
				return (a[mid] + a[mid - 1])/2.0;
			}
			else {
				return a[mid];
			}		
		}
	}
	
	
	public static double median(int[] a) {
		double median;
		Arrays.sort(a);
		int mid = a.length/2;
		if (a.length % 2 == 0) {
			median = ((a[mid] + a[mid - 1])/2);
		}
		else {
			median = a[mid];
		}		
		return median;
	}
	
	
	public void fillVoteTable(Requirement requirement) {
		ArrayList<String> nameArray = requirementToNames(requirement);
		ArrayList<Integer> voteArray = requirementToVotes(requirement);
				for (int i = 0; i < nameArray.size(); i++) {
					System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@Name:" + nameArray.get(i) + "Vote:" + voteArray.get(i));
					voteTable.getTableModel().addRow(new Object[]{nameArray.get(i),voteArray.get(i)});
				}
	}
	
	public void reqClicked(Requirement req) {
		activeRequirement = req;
		userStoryDesc.setText(req.getDescription());
		while (voteTable.getTableModel().getRowCount() > 0) {
			voteTable.getTableModel().removeRow(0);
		}
		while (statTable.getTableModel().getRowCount() > 0) {
			statTable.getTableModel().removeRow(0);
		}
		Object[] row = makeStatRow(req);
		statTable.getTableModel().addRow(row);
		fillVoteTable(req);
		}
	
	public void isUserCreator() {
		if(ConfigManager.getConfig().getUserName().equals(activeGame.getCreator())){
			finalEstimateBox.setVisible(true);
			finalEstimateLabel.setVisible(true);
			finalEstimateButton.setVisible(true);
		} else {
			finalEstimateBox.setVisible(false);
			finalEstimateLabel.setVisible(false);
			finalEstimateButton.setVisible(false);
		}
	}
	
	private void addKeyListenerTo(JComponent component){
		component.addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent arg0) {
				if (finalEstimateBox.isFocusOwner()) {
					validateSubmitButton();
				}
				else {}
			}
		});
	}

	private void validateSubmitButton() {
		String text = finalEstimateBox.getText();
		if (verifyFinalEstimateField()) {
			finalEstimateButton.setEnabled(true);
		}
		else {
			finalEstimateButton.setEnabled(false);
		}
	}
	
	public boolean verifyFinalEstimateField() {
		String text = finalEstimateBox.getText();
		String allowedChars = "0123456789";
		String currChar;
		if (text.length() == 0) {
			return false;
		}
		for (int i = 0; i < text.length(); i++) {
			currChar = Character.toString(text.charAt(i));
			if (!allowedChars.contains(currChar)) {
				return false;
			}
		}
		return true;
	}


	@Override
	public boolean validateField(IErrorView warningField, boolean showLabel,
			boolean showBox) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasChanges() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
}
