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
import javax.swing.ScrollPaneConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.controllers.GetGameController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;

/**
 * TODO: add documentation
 * @author Bobby Drop Tables
 *
 */
@SuppressWarnings("serial")
public class GameTree extends JPanel implements MouseListener{
	private boolean initialized = false; //Check if GameModel should be generated from the server
	JTree gameTree; // JTree to hold the hierarchy of games
	JScrollPane gameTreeScroll; // scrollPane to put the tree in
	DefaultMutableTreeNode gameNode = 
			new DefaultMutableTreeNode("Games"); //Make master node hold the other 3
	DefaultMutableTreeNode inactive = 
			new DefaultMutableTreeNode("Pending Games"); //Make pending games node
	DefaultMutableTreeNode active = 
			new DefaultMutableTreeNode("Active Games"); //Make active games node
	DefaultMutableTreeNode history = 
			new DefaultMutableTreeNode("Game History"); //Make games history node
	
	
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
		if(this.getComponentCount() != 0){
			this.remove(0);
		}
		
		active.removeAllChildren();
		inactive.removeAllChildren();
		history.removeAllChildren();
		
		List<Game> gameList = 
				sortGames(GameModel.getInstance().getGames());//retrieve list of all games
		System.out.println("Numb Games: " + gameList.size());
		for (Game game: gameList){
			DefaultMutableTreeNode newGameNode = new DefaultMutableTreeNode(game);
			
			if(!game.isComplete()){ //If game is not complete, add it to the active game drop down
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
	
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		//c.weighty = 1;
		c.weightx = 1;
		c.gridx = 1;
		c.gridy = 1;
		gameTreeScroll = new JScrollPane(gameTree);
		gameTreeScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		gameTreeScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		gameTreeScroll.setPreferredSize(new Dimension(190, 500));
		
		
	    this.add(gameTreeScroll, c);
	    ViewEventController.getInstance().setGameOverviewTree(this);
	   
	    this.repaint();
	    gameTree.repaint();
		gameTreeScroll.repaint();
	    this.validate();
		
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
				DefaultMutableTreeNode node = 
						(DefaultMutableTreeNode)clicked.getLastSelectedPathComponent();
				if(node != null) {
					if(node.getUserObject() instanceof Game){ //Confirm that this is a game
						System.out.println("Setting view to game: " + 
								((Game)node.getUserObject()).toString());
						if(((Game)node.getUserObject()).isActive() &&
								(!((Game)node.getUserObject()).isComplete())){
							ViewEventController.getInstance().joinGame((Game)node.getUserObject());
						}
						else if(!((Game)node.getUserObject()).isComplete()){
							ViewEventController.getInstance().editGame((Game)node.getUserObject());
						}
						else{
							ViewEventController.getInstance().viewEndGame((Game)node.getUserObject());
						}
					}
					else if(node.getUserObject() instanceof String){
						if(gameTree.isCollapsed(gameTree.getPathForLocation(x, y))){
							gameTree.expandPath(gameTree.getPathForLocation(x, y));
						}
						else {
							gameTree.collapsePath(gameTree.getPathForLocation(x, y));
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
