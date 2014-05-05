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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.models.PPRequirement;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.vote.models.Vote;

/**
 * Creates the left half of the active games panel
 */
public class LeftHalfActiveGamePanel extends JScrollPane{
	
	private static final Logger logger = Logger.getLogger(UserProgress.class.getName());
	
	Game active;
	
	private Container newLeftView;
	private SpringLayout layout;
	
	private JLabel gameNameLabel;
	private JTextField gameName;
	private JLabel gameDescLabel;
	private JTextArea gameDesc;
	private JLabel gameCreatorLabel;
	private JLabel gameEndDateLabel;
	private JLabel gameCreatorName;
	private JLabel gameEndDate;
	private JButton endGameManuallyButton;
	private final Border etchedBorder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
	
	private JLabel overallProgressLabel = new JLabel("Overall Team Voting Progress: ");
	private final JProgressBar overallProgress = new JProgressBar(0, 1000);
	private JLabel individualUserProgressLabel;
	private UserProgressList usersProgressPanel;
	
	private boolean endManually;
	private ActiveGamePanel parentPanel;
	/**
	 * Constructor for NewLeftHalfActiveGamePanel
	 * @param game the current planning poker game session
	 * @param activeGamePanel the open game
	 */
	public LeftHalfActiveGamePanel(final Game game, final ActiveGamePanel activeGamePanel) {
		active = game;
		build();
		isUserCreator();
		parentPanel = activeGamePanel;
	}
	
	/**
	 * Method to notify observers and set game complete if end game is pressed
	 */
	public void endGameManuallyButtonPressed(){
		endManually=true;
		parentPanel.endGameManually();
	}
	
	/**
	 * Method to check if the creator of the game is the user in the current session
	 * 
	 */
	public void isUserCreator() {
		if(ConfigManager.getConfig().getUserName().equals(active.getCreator())){
			endGameManuallyButton.setVisible(true);
		} else {
			endGameManuallyButton.setVisible(false);
		}
	}
	
	public void build(){
		endManually=false;
		// Creates the container to hold all the components
		// and sets the container's layout to be SpringLayout
		newLeftView = new Container();
		layout = new SpringLayout();
		newLeftView.setLayout(layout);

		/**
		 * Create and/or initialize components
		 */

		// Initializes and sets game name label
		gameNameLabel = new JLabel("Game Name");				

		// Initializes and sets properties of game name label
		gameName = new JTextField(30);						
		gameName.setText(active.getName());
		gameName.setEditable(false);
		gameName.setBackground(new Color(230,230,230));
		gameName.setBorder(etchedBorder);

		// Initializes and sets game description label
		gameDescLabel = new JLabel("Description");				

		// Initializes and sets game description display area
		gameDesc = new JTextArea();
		gameDesc.setText(active.getDescription());
		gameDesc.setEditable(false);
		gameDesc.setBorder(etchedBorder);
		gameDesc.setBackground(new Color(230,230,230));
		gameDesc.setLineWrap(true);									

		// Creates a scroll pane to hold the area which to display game description
		JScrollPane descPane = new JScrollPane(gameDesc);
		descPane.setPreferredSize(new Dimension(200, 100));

		// Initializes and sets game creator label
		gameCreatorLabel = new JLabel("Creator: ");				

		// Initializes and sets game end date label
		gameEndDateLabel = new JLabel("End date: ");			

		// Initializes and sets game creator name
		gameCreatorName = new JLabel(active.getCreator());		
		
		// Initializes and sets game end date
		gameEndDate = new JLabel(active.getEndDate().toString());	

		// Creates end game button
		endGameManuallyButton = new JButton();
		endGameManuallyButton.setSize(10, 5);
		endGameManuallyButton.setText("End Game");

		// If end game button is pressed, set the game complete
		endGameManuallyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				endGameManuallyButtonPressed();
				}
		});
		
		// Initializes and sets the individualUserProgressLabel
		individualUserProgressLabel = new JLabel("Individual Users Voting Progress: ");
		newLeftView.add(individualUserProgressLabel);
		
		usersProgressPanel = new UserProgressList(this);
		
		newLeftView.add(overallProgressLabel);
		
		overallProgress.setPreferredSize(new Dimension(320,25));//make the bar higher
		overallProgress.setBackground(Color.WHITE);
		overallProgress.setStringPainted(true);
		newLeftView.add(overallProgress);
		
		/**
		 * Add components to the container
		 */
		newLeftView.add(gameNameLabel);
		newLeftView.add(gameName);
		newLeftView.add(gameDescLabel);
		newLeftView.add(descPane);
		newLeftView.add(gameCreatorLabel);
		newLeftView.add(gameEndDateLabel);
		newLeftView.add(gameCreatorName);
		newLeftView.add(gameEndDate);
		newLeftView.add(endGameManuallyButton);
		
		newLeftView.add(usersProgressPanel);
		/**
		 * Adjust constraints on components
		 */
		layout.putConstraint(SpringLayout.NORTH, gameNameLabel, 10, SpringLayout.NORTH, newLeftView);
		layout.putConstraint(SpringLayout.WEST, gameNameLabel, 5, SpringLayout.WEST, newLeftView);

		layout.putConstraint(SpringLayout.NORTH, gameName, 5, SpringLayout.SOUTH , gameNameLabel);
		layout.putConstraint(SpringLayout.WEST, gameName, 5, SpringLayout.WEST, newLeftView);
		layout.putConstraint(SpringLayout.EAST, gameName, -5, SpringLayout.EAST, newLeftView);

		layout.putConstraint(SpringLayout.NORTH, gameDescLabel, 5, SpringLayout.SOUTH, gameName);
		layout.putConstraint(SpringLayout.WEST, gameDescLabel, 5, SpringLayout.WEST, newLeftView);

		layout.putConstraint(SpringLayout.NORTH, descPane, 5, SpringLayout.SOUTH, gameDescLabel);
		layout.putConstraint(SpringLayout.WEST, descPane, 5, SpringLayout.WEST, newLeftView);
		layout.putConstraint(SpringLayout.EAST, descPane, -5, SpringLayout.EAST, newLeftView);

		layout.putConstraint(SpringLayout.NORTH, gameCreatorName, 10, SpringLayout.SOUTH, descPane);
		layout.putConstraint(SpringLayout.WEST, gameCreatorName, 14, SpringLayout.EAST, gameCreatorLabel);

		layout.putConstraint(SpringLayout.NORTH, gameCreatorLabel, 10, SpringLayout.SOUTH, descPane);
		layout.putConstraint(SpringLayout.WEST, gameCreatorLabel, 5, SpringLayout.WEST, newLeftView);

		layout.putConstraint(SpringLayout.NORTH, gameEndDateLabel, 5, SpringLayout.SOUTH, gameCreatorLabel);
		layout.putConstraint(SpringLayout.WEST, gameEndDateLabel, 5, SpringLayout.WEST, newLeftView);

		layout.putConstraint(SpringLayout.NORTH, gameEndDate, 5, SpringLayout.SOUTH, gameCreatorLabel);	
		layout.putConstraint(SpringLayout.WEST, gameEndDate, 5, SpringLayout.EAST, gameEndDateLabel);

		layout.putConstraint(SpringLayout.NORTH, overallProgressLabel, 15, SpringLayout.SOUTH, gameEndDate);	
		layout.putConstraint(SpringLayout.WEST, overallProgressLabel, 5, SpringLayout.WEST, newLeftView);
		
		layout.putConstraint(SpringLayout.NORTH, overallProgress, 5, SpringLayout.SOUTH, overallProgressLabel);	
		layout.putConstraint(SpringLayout.WEST, overallProgress, 5, SpringLayout.WEST, newLeftView);
		layout.putConstraint(SpringLayout.EAST, overallProgress, -5, SpringLayout.EAST, newLeftView);
		
		layout.putConstraint(SpringLayout.NORTH, individualUserProgressLabel, 10, SpringLayout.SOUTH, overallProgress);	
		layout.putConstraint(SpringLayout.WEST, individualUserProgressLabel, 5, SpringLayout.WEST, newLeftView);
		
		layout.putConstraint(SpringLayout.NORTH, usersProgressPanel, 5, SpringLayout.SOUTH, individualUserProgressLabel);	
		layout.putConstraint(SpringLayout.WEST, usersProgressPanel, 5, SpringLayout.WEST, newLeftView);
		layout.putConstraint(SpringLayout.EAST, usersProgressPanel, -5, SpringLayout.EAST, newLeftView);
		layout.putConstraint(SpringLayout.SOUTH, usersProgressPanel, -10, SpringLayout.NORTH, endGameManuallyButton);	

		layout.putConstraint(SpringLayout.WEST, endGameManuallyButton, 5, SpringLayout.WEST, newLeftView);
		layout.putConstraint(SpringLayout.SOUTH, endGameManuallyButton, -10, SpringLayout.SOUTH, newLeftView);

		setMinimumSize(new Dimension(325, 150));			//Sets the minimum size of the left half view
		newLeftView.setPreferredSize(new Dimension(305, 485));		//Sets the size of the view
		
		setViewportView(newLeftView);
		
		usersProgressPanel.setVisible(true);
		revalidate();
		repaint();
		
		revalidate();
		repaint();
		
	}

	public boolean endManually(){
		return endManually;
	}
	
	public Game getGame(){
		return active;
	}
	
	/**
	 * Updates the overall progress bar.
	 *
	 */
	public void updateOverallProgress(){
		
		List<PPRequirement> reqList;
		List<Vote> voteList;
		if(active.getRequirements() != null){	//check if reqList isn't null
			reqList = active.getRequirements();
		}
		else {
			logger.log(Level.SEVERE, "Requirement list is null");
			return;
		}		
		overallProgress.setValue(0);
		for(PPRequirement r : reqList){
			if(r.getVotes() != null){				//check if voteList isn't null
				voteList = r.getVotes();
				for(Vote v : voteList){		
					int i = overallProgress.getValue();
					int numReqs;
					if(active.getRequirements() != null){
						numReqs = active.getRequirements().size();
					}
					else {
						return;
					}
					int numUsers = usersProgressPanel.getNumUsers();
					int j = (overallProgress.getMaximum())/(numReqs * numUsers);
					overallProgress.setValue(i + j);
				}
			}
		}
	}
	
	public UserProgressList getUsersProgressPanel(){
		return usersProgressPanel;
	}
}
