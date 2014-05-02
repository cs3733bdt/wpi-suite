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
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.abstractmodel.AbstractStorageModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.models.PPRequirement;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.models.PPRequirementModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirement.controllers.AddRequirementController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirement.controllers.GetRequirementsController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirement.controllers.UpdateRequirementController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirement.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirement.models.RequirementModel;
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
	private static Logger logger = Logger.getLogger(AbstractStorageModel.class.getName());
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
		activeRequirement = game.getRequirements().get(0); //default to first requirement //TODO dependent on the click
		
		JLabel descLabel = new JLabel("Description");
		JLabel statLabel = new JLabel("Statistics");
		JLabel votesLabel = new JLabel("Votes by User");
		
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
			finalEstimateDisplay.setText("Your Current Final Estimate is: " + (int)mean);
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
		userStoryDesc.setText(game.getRequirements().get(0).getDescription());
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
		
		repaint();
		invalidate();
		revalidate();
		
		setViewportView(overviewPanel);
	}
	
	/**
	 * 
	 * @param voteData list of user's votes
	 * @return the number of user votes
	 */
	public int numVotes(List<Integer> voteData) {
		numVotes = voteData.size();
		return numVotes;
	}
	/**
	 * 
	 * @param requirement whose statistics are to be displayed
	 * @return a row object to be displayed in the statistics table
	 */
	public Object[] makeStatRow(PPRequirement requirement) {
		List<Integer> rawVoteData = requirementToVotes(requirement);
		List<Integer> voteData = removeIDKs(rawVoteData);
		
		int idks = rawVoteData.size() - voteData.size();
		
		Object[] row = new Object[] {(float)mean(voteData), (float)stDev(voteData), median(voteData), max(voteData), min(voteData), numVotes(rawVoteData), idks};
		return row;
	}
	/**
	 * @param stat String input that determines what statistic to return
	 * @return statistic based on the input
	 */
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
	public List<Integer> requirementToVotes(PPRequirement requirement) {
		List<Vote> Votes = requirement.getVotes();
		ArrayList<Integer> voteArray = new ArrayList<Integer>();
		for (int i = 0; i < Votes.size(); i++) {
			voteArray.add(Votes.get(i).getVoteNumber());
		}
		return voteArray;
	}

	/**
	 * 
	 * @param requirement
	 * @return an arrayList of usernames of users who have voted
	 */
	public List<String> requirementToNames(PPRequirement requirement) {
		List<Vote> Votes = requirement.getVotes();
		List<String> nameArray = new ArrayList<String>();
		for (int i = 0; i < Votes.size(); i++) {
			nameArray.add(Votes.get(i).getUsername());
		}
		return nameArray;
	}
	
	/**
	 * @param votes a list of the user's votes
	 * @return a new array of votes that does not contain "I don't know" votes (zero votes)
	 */
	private List<Integer> removeIDKs(List<Integer> votes) {
		
		List<Integer> newVotes = new ArrayList<Integer>();
		
		for (int i = 0; i < votes.size(); i++) {
			if (votes.get(i) != 0) {
				newVotes.add(votes.get(i));
			}
		}
		return newVotes;
	}
	
	/**
	 * @param votes a list of the integer values for the votes for a given requirement
	 * @return the minimum of the array. will return -1 if the array is empty
	 */
	private int min(List<Integer> votes) {
		int min = -1;
		
		for (int i = 0; i < votes.size(); i++) {
			if (votes.get(i) < min || min == -1) {
				min = votes.get(i);
			}
		}
		minEstimate = min;
		return min;
	}
	
	/**
	 * @param votes a list of the integer values for the votes for a given requirement
	 * @return the maximum of the array. will return -1 if the array is empty
	 */
	private int max(List<Integer> votes) {
		int max = -1;
		
		for (int i = 0; i < votes.size(); i++) {
			if (votes.get(i) > max) {
				max = votes.get(i);
			}
		}
		maxEstimate = max;
		return max;
	}
	
	private double mean(List<Integer> a) {
		double sum = 0;
		int i;
		
		for(i = 0; i < a.size(); i++) {
			sum += a.get(i);
		}
		mean = sum/ ((double)a.size());
		return mean;
	}
	
	private double stDev(List<Integer> a) {
		double mean = mean(a);
		List<Double> numMinusMeanSquared = new ArrayList<Double>();
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

	private double median(List<Integer> votes) {
		if (votes.size() == 0) {
			median = 0;
		}
		else if (votes.size() == 1) {
			median = votes.get(0);
		}
		else {
			double[] a = new double[votes.size()];
			for (int i = 0; i < votes.size(); i++) {
				a[i] = votes.get(i);
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
	/**
	 * Fills the statistics table with the statistics associated with that requirement
	 * @param requirement
	 */
	public void fillVoteTable(PPRequirement requirement) {
		List<String> nameArray = requirementToNames(requirement);
		List<Integer> voteArray = requirementToVotes(requirement);
				for (int i = 0; i < nameArray.size(); i++) {
					if (voteArray.get(i) == 0) {
						voteTable.getTableModel().addRow(new Object[]{nameArray.get(i),"I don't know"});
					} else {
						voteTable.getTableModel().addRow(new Object[]{nameArray.get(i),voteArray.get(i)});
					}
				}
	}
	/**
	 * Displays a final estimate if you are the creator of a game upon a requirement being clicked
	 * @param req requirement which is clicked
	 */
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
		currFinalEstimate = activeRequirement.getFinalEstimate();
		
		if (currFinalEstimate == -1) {
			finalEstimateDisplay.setText("Your Current Final Estimate is: " + mean);
		}
		else {
			finalEstimateDisplay.setText("Your Current Final Estimate is: " + currFinalEstimate);
		}
		
		finalEstimateMessage.setText("");
		finalEstimateBox.setText("");
	}
	
	/**
	 * determines if the current user is the creator of the game and displays components related to the final estimate
	 * if the user is the creator.
	 */
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
		for (PPRequirement ppr: activeGame.getRequirements()) {
			if (ppr.identify(activeRequirement)) {
				ppr.setFinalEstimate(newEstimate);
				ppr.notifyObservers();
				if (ppr.getFromRequirementModule()) {
					sendEstimateToManager(ppr, newEstimate);
				} else {
					sendRequirementToManager(ppr, newEstimate);
				}
				
				finalEstimateMessage.setForeground(Color.BLUE);
				finalEstimateMessage.setText("Final estimate submitted successfully!");
			}
		}
		ViewEventController.getInstance().refreshGameTable();
		ViewEventController.getInstance().refreshGameTree();
		finalEstimateDisplay.setText("Your Current Final Estimate is: " + newEstimate);
	}
	
	private void sendEstimateToManager(PPRequirement req, int estimate) {
		// Get requirement from requirement manager with that requirement id
		RequirementModel rModel = RequirementModel.getInstance();
		GetRequirementsController.getInstance().retrieveRequirements();
		// Sleep to wait for retrieve to finish
		try {
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		try {
			// Set new estimate (the ppr.getid() - 1 is for translating between
			// our requirement id's and the requirement manager's)
			rModel.getRequirement((req.getId() - 1)).setEstimate(estimate);
			// Send updated requirement to server
			UpdateRequirementController.getInstance().updateRequirement(rModel.getRequirement((req.getId() - 1)));
		} catch(NullPointerException e) {
			// The requirement doesn't exist
		}
	}
	
	private void sendRequirementToManager(PPRequirement req, int estimate) {
		int nextId = 0;
		RequirementModel rModel = RequirementModel.getInstance();
		GetRequirementsController.getInstance().retrieveRequirements();
		// Sleep to wait for retrieve to finish
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// Find next id that can be added
		// and check to see if a requirement exists
		// with the same name and description
		for (Requirement r: rModel.getRequirements()) {
			if (r.getId() > nextId)
				nextId = r.getId();
			// Break out if already exists in manager
			if (r.getName().equals(req.getName()) && r.getDescription().equals(req.getDescription())) {
				// TODO: Do Logger Message
				return;
			}
		}
		// nextId = current highest id, set it to next
		nextId += 1;
		// Get new requirement to be added ready
		Requirement newReq = new Requirement(nextId, req.getName(), req.getDescription());
		newReq.setEstimate(estimate);
		// Send Requirement to requirement manager
		AddRequirementController.getInstance().addRequirement(newReq);
		// Now exists in the requirement module/manager
		req.setFromRequirementModule(true);
		req.notifyObservers();
	}
	
	private boolean verifyFinalEstimateField() {
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

	private Font makeFont(int fontSize) {
		return new Font("Serif", Font.BOLD, fontSize);
	}
	
	
}
