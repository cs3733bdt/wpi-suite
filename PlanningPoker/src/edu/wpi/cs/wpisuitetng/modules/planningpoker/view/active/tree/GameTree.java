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

/**
 * @author Tom Paolillo, Jonathan Leitschuh, Jeff Signore
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.tree;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetGameController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.game.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.game.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;

/**
 * TODO: add documentation
 * @author Bobby Drop Tables
 *
 */
@SuppressWarnings("serial")
public class GameTree extends JPanel implements MouseListener{
	private boolean initialized = false; //Used to check if the GameModel should be generated from the server.
	JTree gameTree;
	JScrollPane gameTreeScroll;
	DefaultMutableTreeNode gameNode = new DefaultMutableTreeNode("Games");
	DefaultMutableTreeNode inactive = new DefaultMutableTreeNode("Pending Games");
	DefaultMutableTreeNode active = new DefaultMutableTreeNode("Active Games"); //Makes the starting node
	DefaultMutableTreeNode history = new DefaultMutableTreeNode("Game History"); //Makes the starting node
	
	/**
	 * Constructor for a GameTree
	 */
	public GameTree(){
		super(new GridBagLayout());
		ViewEventController.getInstance().setGameOverviewTree(this);
		this.refresh();
	}

	/**
	 * Regenerates the table's components whenever called.
	 * Used when the list of games is updated or changed.
	 */
	public void refresh(){
		active.removeAllChildren();
		inactive.removeAllChildren();
		history.removeAllChildren();
		
		List<Game> gameList = sortGames(GameModel.getInstance().getGames()); //retrive the list of all of the games
		System.out.println("Numb Games: " + gameList.size());
		for (Game game: gameList){
			DefaultMutableTreeNode newGameNode = new DefaultMutableTreeNode(game);
			
			//if
			if(!game.isComplete()){ //If the game is not complete then add it to the active game dropdown
				if(game.isActive()){
					active.add(newGameNode);
				}
				else if(game.isCreator(ConfigManager.getConfig().getUserName())){
					inactive.add(newGameNode);
				}
			} else { //If the game is complete then put it in the history
				history.add(newGameNode);
			}
		}
		
		gameNode.add(inactive);
		gameNode.add(active);
		gameNode.add(history);
		gameTree = new JTree(gameNode);
		
		gameTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		gameTree.setToggleClickCount(0);
		gameTree.addMouseListener(this);
		
		gameTree.setPreferredSize(new Dimension(500 , 10 + gameList.size()*21));

		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 1;
		c.weightx = 1;
		c.gridx = 1;
		c.gridy = 1;
		gameTreeScroll = new JScrollPane(gameTree);
		
		if(this.getComponentCount() != 0){
			this.remove(0);
		}
	    this.add(gameTreeScroll, c);
	    ViewEventController.getInstance().setGameOverviewTree(this);
	    this.repaint();
	    this.validate();
	    System.out.println("Finished refresshing the tree");
		
	}
	
	@Override
	public void paintComponent(Graphics g){
		if(!initialized){
			try{
				GetGameController.getInstance().retrieveGames();
				initialized = true;
			} catch (Exception e){
				System.err.println("Problem instantiating the Game Model. " + e);
			}
		}
		super.paintComponent(g);
	}
	
	
	/**
	 * @param list the list of games to be sorted
	 * @return the same list sorted by start date
	 */
	public List<Game> sortGames(List<Game> list) {
		
		Collections.sort(list, new GameComparator());
		return list;
	}

	public JTree getGameTree(){
		return gameTree;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		//System.out.println("Single Click Detected");
		
		if(e.getClickCount() == 2){
			System.out.println("Double Click Detected");
			TreePath treePath = gameTree.getPathForLocation(x, y);
			JTree clicked = gameTree;
			if(treePath == null){
				System.out.println("Not on gameTree");
			}
			if(treePath != null){
				System.out.println("Tree Path valid");
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)clicked.getLastSelectedPathComponent();
				if(node != null) {
					if(node.getUserObject() instanceof Game){ 		//Make sure that this is actually a game
						System.out.println("Setting view to game: " + ((Game)node.getUserObject()).toString());
						if(((Game)node.getUserObject()).isActive() && (!((Game)node.getUserObject()).isComplete())){
							ViewEventController.getInstance().joinGame((Game)node.getUserObject());
						}
						else if(!((Game)node.getUserObject()).isComplete()){
							ViewEventController.getInstance().editGame((Game)node.getUserObject());
						}
						else{
							ViewEventController.getInstance().viewEndGame((Game)node.getUserObject());
						}
					}
				}
			}
		}
		
		//TODO add functionality for right clicking possibly
		
	}



	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}



/**
 * Used to sort games by their creation time.
 * @author jonathanleitschuh
 *
 */
class GameComparator implements Comparator<Game>{
	@Override
	public int compare(Game G1, Game G2){
		return -(G1.getCreationTime().compareTo(G2.getCreationTime()));
	}
}
