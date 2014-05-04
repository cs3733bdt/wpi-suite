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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.border.Border;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;

public class UserProgressList extends JScrollPane {
	
	private Container view = new Container();
	
	private SpringLayout layout;

	private LeftHalfActiveGamePanel parent;
	
	private Game game;
	
	private List<UserProgress> userProgressList;
	
	private UserProgressList instance = this;
	
	private User[] userList;
	
	private int heightBasedOnUserNumber;
	
	private final Border defaultBorder = (new JTextField()).getBorder();
	
	public UserProgressList(LeftHalfActiveGamePanel parent){
		layout = new SpringLayout();
		view.setLayout(layout);
		
		this.parent = parent;
		userProgressList = new ArrayList<UserProgress>(){ 
			public boolean add(UserProgress o){
				view.add(o);
				return super.add(o);
			}
			
			@Override
			public void clear(){
				view.removeAll();
				super.clear();
			}
		};
		
		build();
		
	}
	
	public void build(){
		
		game = parent.getGame();
		
		userList = game.getProject().getTeam();
		
		if(userList == null){
			return;
		}
		
		//create all User Progress's using the usernames from the project, and add them all to the user Progress List
		for(User user : userList){
			String userName = user.getUsername();
			UserProgress userProgress = new UserProgress(userName);
			userProgressList.add(userProgress);
		}
		int prevIndex = 0;
		for(UserProgress userP : userProgressList){
			if(userProgressList.indexOf(userP) == 0){
				layout.putConstraint(SpringLayout.NORTH, userP, 5, SpringLayout.NORTH, view);	
				layout.putConstraint(SpringLayout.WEST, userP, 0, SpringLayout.WEST, view);
				layout.putConstraint(SpringLayout.EAST, userP, 0, SpringLayout.EAST, view);
				prevIndex = 0;
			}
			else{
				UserProgress prevUserProgress = userProgressList.get(prevIndex);
				layout.putConstraint(SpringLayout.NORTH, userP, 5, SpringLayout.SOUTH, prevUserProgress);	
				layout.putConstraint(SpringLayout.WEST, userP, 0, SpringLayout.WEST, view);
				layout.putConstraint(SpringLayout.EAST, userP, 0, SpringLayout.EAST, view);
				prevIndex++;
			}
		}
		
		int users = userProgressList.size();
		heightBasedOnUserNumber = 33*users;
		
		setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		setPreferredSize(new Dimension(50, 125));
		view.setPreferredSize(new Dimension(150, heightBasedOnUserNumber));

		setBorder(defaultBorder);
		
		setViewportView(view);
		
		revalidate();
		repaint();
		
	}
	
	public int getHeight(){
		return heightBasedOnUserNumber;
	}
	
	public void updateProgress(){
		userProgressList.clear();
		revalidate();
		repaint();
		build();
	}
	
}