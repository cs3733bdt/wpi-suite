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

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.Border;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;

/**
 * Creates the left half of the active games panel
 */
public class LeftHalfActiveGamePanel extends JScrollPane{
	
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
	private JLabel endGameManuallyNoteLabel;
	private JLabel endGameManuallyNoteLabel1;
	private JTextArea endGameManuallyNote;
	private JButton endGameManuallyButton;
	JScrollPane notePane;
	private final Border defaultBorder = (new JTextField()).getBorder();
	
	private JLabel overallProgressLabel = new JLabel("Team's overall voting progress: ");
	private final JProgressBar overallProgress = new JProgressBar(0, 1000);
	private JButton testButton = new JButton("testYo");
	private JLabel individualUserProgressLabel;
	private UserProgressList usersProgressPanel;
	private int userPanelHeight;
	
	private boolean endManually;
	private ActiveGamePanel parentPanel;
	
	private LeftHalfActiveGamePanel instance = this;
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
			endGameManuallyNoteLabel.setVisible(true);
			endGameManuallyNoteLabel1.setVisible(true);
			endGameManuallyNote.setBorder(defaultBorder);
			endGameManuallyNote.setVisible(true);
			endGameManuallyButton.setVisible(true);
		} else {
			endGameManuallyNoteLabel.setVisible(false);
			endGameManuallyNoteLabel1.setVisible(false);
			notePane.setVisible(false);
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
		gameName.setBorder(defaultBorder);						

		// Initializes and sets game description label
		gameDescLabel = new JLabel("Description");				

		// Initializes and sets game description display area
		gameDesc = new JTextArea();
		gameDesc.setText(active.getDescription());
		gameDesc.setEditable(false);
		gameDesc.setBorder(defaultBorder);
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
		
		// Initializes end game manually note labels
		endGameManuallyNoteLabel = new JLabel("If you wish to end the game manually,");
		endGameManuallyNoteLabel1 = new JLabel("you have the option to enter your reason for doing so:");

		// Initializes text area to enter end game note
		endGameManuallyNote = new JTextArea();
		endGameManuallyNote.setBorder(defaultBorder);
		endGameManuallyNote.setLineWrap(true);

		// Creates a scroll pane to hold the text area for end game note
		notePane = new JScrollPane(endGameManuallyNote);
		notePane.setPreferredSize(new Dimension(200, 110));

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
		individualUserProgressLabel = new JLabel("Individual Users Progress (click to view)");
		newLeftView.add(individualUserProgressLabel);
		addMouseListenerToProgressLabel(individualUserProgressLabel);
		
		usersProgressPanel = new UserProgressList(this);
		
		newLeftView.add(overallProgressLabel);
		
		//updateOverallProgress();
		overallProgress.setPreferredSize(new Dimension(320,25));//make the bar higher
		overallProgress.setStringPainted(true);
		newLeftView.add(overallProgress);
		newLeftView.add(testButton);
		
		testButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				submitButtonPressed();
			}
		});
		
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
		newLeftView.add(endGameManuallyNoteLabel);
		newLeftView.add(endGameManuallyNoteLabel1);
		newLeftView.add(notePane);
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
		
		layout.putConstraint(SpringLayout.NORTH, endGameManuallyNoteLabel, 20, SpringLayout.SOUTH, individualUserProgressLabel);	
		layout.putConstraint(SpringLayout.WEST, endGameManuallyNoteLabel, 5, SpringLayout.WEST, newLeftView);

		layout.putConstraint(SpringLayout.NORTH, endGameManuallyNoteLabel1, 1, SpringLayout.SOUTH, endGameManuallyNoteLabel);	
		layout.putConstraint(SpringLayout.WEST, endGameManuallyNoteLabel1, 5, SpringLayout.WEST, newLeftView);

		layout.putConstraint(SpringLayout.NORTH, notePane, 5, SpringLayout.SOUTH, endGameManuallyNoteLabel1);	
		layout.putConstraint(SpringLayout.WEST, notePane, 5, SpringLayout.WEST, newLeftView);
		layout.putConstraint(SpringLayout.EAST, notePane, -5, SpringLayout.EAST, newLeftView);
		layout.putConstraint(SpringLayout.SOUTH, notePane, -10, SpringLayout.NORTH, endGameManuallyButton);
		
		layout.putConstraint(SpringLayout.WEST, endGameManuallyButton, 5, SpringLayout.WEST, newLeftView);
		layout.putConstraint(SpringLayout.SOUTH, endGameManuallyButton, -10, SpringLayout.SOUTH, newLeftView);

		layout.putConstraint(SpringLayout.WEST, testButton, 10, SpringLayout.EAST, endGameManuallyButton);
		layout.putConstraint(SpringLayout.SOUTH, testButton, -10, SpringLayout.SOUTH, newLeftView);

		setMinimumSize(new Dimension(325, 150));			//Sets the minimum size of the left half view
		newLeftView.setPreferredSize(new Dimension(305, 485));		//Sets the size of the view
		
		setViewportView(newLeftView);
		
		usersProgressPanel.setVisible(false);
		revalidate();
		repaint();
		
		userPanelHeight = usersProgressPanel.getHeight();
		
		revalidate();
		repaint();
		
	}
	
	
	
	/**
	 * Updates the overall progress bar.
	 *
	 */
	private void updateOverallProgress() {
		/*int votes = 0;
		for (RequirementEstimate requirement : requirements) {
			if (requirement.getVotes().containsKey(user)) {
				votes++;
			}
		}
		overallProgress.setValue(votes * 1000 / requirements.size());
		overallProgress.setString("Personal voting progress: "+
				Double.toString(votes*100 / requirements.size()) + "%");*/
		/*int i = 0;
		for(i = 0; i <= overallProgress.getMaximum(); i = i+20){
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			overallProgress.setValue(20);
		}
		*/
	}
	
	public void submitButtonPressed() {
		UpdateOverallProgress();
	}

	public boolean endManually(){
		if (endManually){
			return true;
		}else{
			return false;
		}
	}
	
	public Game getGame(){
		return active;
	}
	
	private void addMouseListenerToProgressLabel(JLabel component) {
		component.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(!usersProgressPanel.isVisible()){
					newLeftView.setPreferredSize(new Dimension(305, 485 + userPanelHeight));		//Sets the size of the view
					layout.putConstraint(SpringLayout.NORTH, endGameManuallyNoteLabel, 10, SpringLayout.SOUTH, usersProgressPanel);
					usersProgressPanel.setVisible(true);
				}
				else {
					newLeftView.setPreferredSize(new Dimension(305, 485));
					layout.putConstraint(SpringLayout.NORTH, endGameManuallyNoteLabel, 20, SpringLayout.SOUTH, individualUserProgressLabel);
					usersProgressPanel.setVisible(false);
				}
				
				instance.revalidate();
				instance.repaint();
			}
		});
	}
	
	public void UpdateOverallProgress(){
		int i = overallProgress.getValue();
		int numReqs;
		if(parentPanel.getGame().getRequirements() != null){
			numReqs = parentPanel.getGame().getRequirements().size();
		}
		else {
			return;
		}
		int numUsers = usersProgressPanel.getNumUsers();
		int j = (overallProgress.getMaximum())/(numReqs * numUsers);
		overallProgress.setValue(i + j);
	}
}
