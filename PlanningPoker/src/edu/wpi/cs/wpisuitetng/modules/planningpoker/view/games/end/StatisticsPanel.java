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

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.models.PPRequirement;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.IDataField;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.IErrorView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.vote.models.Vote;
/**
 * used to display the end game statistics upon ending a game
 */
public class StatisticsPanel extends JScrollPane implements IDataField {
	Game activeGame;
	PPRequirement activeRequirement;
	
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
	private JLabel finalEstimateDisplay;
	private JLabel finalEstimateMessage = new JLabel("");
	
	private int minEstimate;
	private int maxEstimate;
	private double mean;
	private double stDev;
	private double median;
	private int numVotes;
	int currFinalEstimate;
	
	private EndGameTable statTable;	
	
	private EndGameTable voteTable;
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
		//activeRequirement = game.getRequirements().get(0); //default to first requirement //TODO dependent on the click
		
		JLabel descLabel = new JLabel("Description");
		JLabel statLabel = new JLabel("Statistics");
		JLabel votesLabel = new JLabel("Votes by User");
		
		if(activeRequirement == null) {
			System.out.println("shits busted");
			activeRequirement = activeGame.getRequirements().get(0);
		}
		if(activeGame.getRequirements().get(0) == null) {
			System.out.println("shits very busted");
		}
		
		Object[] row = makeStatRow(activeRequirement);
		
		statTable = new EndGameTable(EndGameTableMode.STATISTIC);
		voteTable = new EndGameTable(EndGameTableMode.VOTE);
		statTable.getTableModel().addRow(row);
		fillVoteTable(activeRequirement);
		
		JScrollPane statsPanel = new JScrollPane(statTable);
		JScrollPane votePanel = new JScrollPane(voteTable);
		JScrollPane descPanel = new JScrollPane(userStoryDesc);
		
		finalEstimateLabel = new JLabel("Enter a Final Estimate here:");
		finalEstimateBox = new JTextField(4);
		addKeyListenerTo(finalEstimateBox);
		finalEstimateButton = new JButton("Submit Final Estimate");
		finalEstimateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				finalEstimateButtonPressed();		
			}
		});
		
		validateSubmitButton();
		finalEstimateDisplay = new JLabel();
		currFinalEstimate = activeRequirement.getFinalEstimate();
		if (currFinalEstimate == -1) {
			finalEstimateDisplay.setText("Your Current Final Estimate is: " + mean);
		}
		else {
			finalEstimateDisplay.setText("Your Current Final Estimate is: " + currFinalEstimate);
		}
		finalEstimateDisplay.setFont(makeFont(12));
		
		overviewPanel.add(descLabel);
		overviewPanel.add(statLabel);
		overviewPanel.add(votesLabel);
		
		overviewPanel.add(finalEstimateLabel);
		overviewPanel.add(finalEstimateBox);
		overviewPanel.add(finalEstimateButton);
		overviewPanel.add(finalEstimateDisplay);
		isUserCreator(); //sets visibility for the above 4 components
		
		overviewPanel.add(descPanel);
		overviewPanel.add(statsPanel);
		overviewPanel.add(votePanel);
		overviewPanel.add(finalEstimateMessage);
		
		/**
		 * Creates and adds the user story text area to the view.
		 */
		//userStoryDesc.setText(game.getRequirements().get(0).getDescription());
		userStoryDesc.setEditable(false);
		userStoryDesc.setLineWrap(true);
		
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
		layout.putConstraint(SpringLayout.SOUTH, votePanel, -60, SpringLayout.SOUTH, overviewPanel);

		//Constraints on the final estimate label
		layout.putConstraint(SpringLayout.WEST, finalEstimateLabel, 5, SpringLayout.WEST, overviewPanel); 
		layout.putConstraint(SpringLayout.NORTH, finalEstimateLabel, 5, SpringLayout.SOUTH, votePanel); 

		//Constraints on the final estimate box
		layout.putConstraint(SpringLayout.NORTH, finalEstimateBox, 5, SpringLayout.SOUTH, votePanel);
		layout.putConstraint(SpringLayout.WEST, finalEstimateBox, 5, SpringLayout.EAST, finalEstimateLabel);
		
		//Constraints on the final estimate Button
		layout.putConstraint(SpringLayout.WEST, finalEstimateButton, 5, SpringLayout.WEST, overviewPanel); 
		layout.putConstraint(SpringLayout.NORTH, finalEstimateButton, 5, SpringLayout.SOUTH, finalEstimateLabel); 
		
		//Constraints on the final estimate message
		layout.putConstraint(SpringLayout.WEST, finalEstimateMessage, 5, SpringLayout.EAST, finalEstimateButton); 
		layout.putConstraint(SpringLayout.NORTH, finalEstimateMessage, 8, SpringLayout.SOUTH, finalEstimateBox); 
		
		//Constraints on the final estimate display
		layout.putConstraint(SpringLayout.EAST, finalEstimateDisplay, -20, SpringLayout.EAST, overviewPanel); 
		layout.putConstraint(SpringLayout.NORTH, finalEstimateDisplay, 5, SpringLayout.SOUTH, votePanel); 
			
		
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
	
	public int numVotes(ArrayList<Integer> voteData) {
		numVotes = voteData.size();
		return numVotes;
	}
	
	public Object[] makeStatRow(PPRequirement requirement) {
		ArrayList<Integer> voteData = requirementToVotes(requirement); 
		Object[] row = new Object[] {mean(voteData), stDev(voteData), median(voteData), max(voteData), min(voteData), numVotes(voteData)};
		return row;
	}
	/**Pass it the name of the stat you want in string form (mean, stDev, min, max, numVotes, median) */
	public double getStat(String stat) {
		switch (stat) {
		case "mean":
			return mean;
		case "stDev":
			return stDev;
		case "min":
			return (double)minEstimate;
		case "max":
			return (double)maxEstimate;
		case "numVotes":
			return (double)numVotes;
		case "median":
			return median;
		default:
			return -1.0;
		}
	}

	/**
	 * @param requirement
	 * @return an arrayList of the vote numbers from the passed requirement
	 */
	public ArrayList<Integer> requirementToVotes(PPRequirement requirement) {
		List<Vote> Votes = requirement.getVotes();
		ArrayList<Integer> voteArray = new ArrayList<Integer>();
		for (int i = 0; i < Votes.size(); i++) {
			voteArray.add(Votes.get(i).getVoteNumber());
		}
		return voteArray;
	}
	
	public ArrayList<String> requirementToNames(PPRequirement requirement) {
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
		minEstimate = min;
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
		maxEstimate = max;
		return max;
	}
	
	double mean(ArrayList<Integer> a) {
		double sum = 0;
		int i;
		
		for(i = 0; i < a.size(); i++) {
			sum += a.get(i);
		}
		mean = sum/ ((double)a.size());
		return mean;
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
		stDev = Math.sqrt(variance) ;
		return stDev;
	}

	public double median(ArrayList<Integer> Votes) {
		if (Votes.size() == 0) {
			median = 0;
		}
		else if (Votes.size() == 1) {
			median = Votes.get(0);
		}
		else {
			double[] a = new double[Votes.size()];
			for (int i = 0; i < Votes.size(); i++) {
				a[i] = Votes.get(i);
			}
			Arrays.sort(a);
			int mid = a.length/2;
		
			if (a.length % 2 == 0) {
				median = (a[mid] + a[mid - 1])/2.0;
			}
			else {
				median = a[mid];
			}		
		}
		return median;
	}
	
	
	public double median(int[] a) {
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
	
	
	public void fillVoteTable(PPRequirement requirement) {
		ArrayList<String> nameArray = requirementToNames(requirement);
		ArrayList<Integer> voteArray = requirementToVotes(requirement);
				for (int i = 0; i < nameArray.size(); i++) {
					voteTable.getTableModel().addRow(new Object[]{nameArray.get(i),voteArray.get(i)});
				}
	}
	
	public void reqClicked(PPRequirement req) {
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
		
		if (currFinalEstimate == -1) {
			finalEstimateDisplay.setText("Your Current Final Estimate is: " + mean);
		}
		else {
			finalEstimateDisplay.setText("Your Current Final Estimate is: " + currFinalEstimate);
		}
		
		finalEstimateMessage.setText("");
		finalEstimateBox.setText("");
		}
	
	public void isUserCreator() {
		if(ConfigManager.getConfig().getUserName().equals(activeGame.getCreator())){
			finalEstimateBox.setVisible(true);
			finalEstimateLabel.setVisible(true);
			finalEstimateButton.setVisible(true);
			finalEstimateDisplay.setVisible(true);
		} else {
			finalEstimateBox.setVisible(false);
			finalEstimateLabel.setVisible(false);
			finalEstimateButton.setVisible(false);
			finalEstimateDisplay.setVisible(false);
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
		if (verifyFinalEstimateField()) {
			finalEstimateButton.setEnabled(true);
		}
		else {
			finalEstimateButton.setEnabled(false);
		}
	}
	
	private void finalEstimateButtonPressed() {
		int newEstimate = Integer.parseInt(finalEstimateBox.getText());
		List<PPRequirement> selectedReqs = activeGame.getRequirements();
		for (int i = 0; i < selectedReqs.size(); i++) {
			if (selectedReqs.get(i).identify(activeRequirement)) {
				selectedReqs.get(i).setFinalEstimate(newEstimate);
				finalEstimateMessage.setForeground(Color.BLUE);
				finalEstimateMessage.setText("Final estimate submitted successfully!");
			}
		}
		ViewEventController.getInstance().refreshGameTable();
		ViewEventController.getInstance().refreshGameTree();
		finalEstimateDisplay.setText("Your Current Final Estimate is: " + newEstimate);
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

	public Font makeFont(int fontSize) {
		return new Font("Serif", Font.BOLD, fontSize);
	}
	
	
}
