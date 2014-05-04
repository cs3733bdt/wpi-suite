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

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SpringLayout;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.models.PPRequirement;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.vote.models.Vote;

public class UserProgress extends JPanel {
	
	private static final Logger logger = Logger.getLogger(UserProgress.class.getName());
	
	private final JProgressBar userProgress = new JProgressBar(0, 1000);
	
	private JLabel userNameLabel;
	
	private String username;
	
	private UserProgressList parentPanel;
	
	public UserProgress(String name, UserProgressList parent){
		this.username = name;
		this.parentPanel = parent;
		build();
	}
	
	public void build(){
		SpringLayout layout = new SpringLayout();
		setLayout(layout);
		
		userNameLabel = new JLabel(username);
		userNameLabel.setPreferredSize(new Dimension(80, 20));
		addMouseListenerToProgressLabel(userNameLabel);
		
		userProgress.setStringPainted(true);
		
		add(userNameLabel);
		add(userProgress);
		
		layout.putConstraint(SpringLayout.NORTH, userNameLabel, 3, SpringLayout.NORTH, this);	
		layout.putConstraint(SpringLayout.WEST, userNameLabel, 2, SpringLayout.WEST, this);
		
		layout.putConstraint(SpringLayout.NORTH, userProgress, 0, SpringLayout.NORTH, this);	
		layout.putConstraint(SpringLayout.WEST, userProgress, 15, SpringLayout.EAST, userNameLabel);
		layout.putConstraint(SpringLayout.EAST, userProgress, -2, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, userProgress, 0, SpringLayout.SOUTH, this);
		
		setPreferredSize(new Dimension(50, 25));
	}
	
	public void setProgressBarValue(){
		List<PPRequirement> reqList;
		List<Vote> voteList;
		if(parentPanel.getGame().getRequirements() != null){	//check if reqList isn't null
			reqList = parentPanel.getGame().getRequirements();
		}
		else {
			logger.log(Level.SEVERE, "Requirement list is null");
			return;
		}		
		userProgress.setValue(0);
		for(PPRequirement r : reqList){
			if(r.getVotes() != null){				//check if voteList isn't null
				voteList = r.getVotes();
				for(Vote v : voteList){
					if(v.getUsername().equals(username)){		//if the voter has the same username as the username associated with this progress bar
						int i = userProgress.getValue();
						int numReqs;
						if(parentPanel.getGame().getRequirements() != null){
							numReqs = parentPanel.getGame().getRequirements().size();
						}
						else {
							return;
						}
						int j = (userProgress.getMaximum())/(numReqs);
						userProgress.setValue(i + j);
					}
				}
			}
		}
	}
	
	private void addMouseListenerToProgressLabel(JLabel component) {
		component.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				setProgressBarValue();
			}
		});
	}
}