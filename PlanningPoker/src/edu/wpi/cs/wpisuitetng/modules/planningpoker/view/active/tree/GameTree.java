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
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.Spring;
import javax.swing.SpringLayout;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.controllers.GetGameController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.controllers.RetrievePPRequirementController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.models.PPRequirement;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.models.PPRequirementModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;

/**
 * Displays and lists the game nodes and stores games in them which
 * is dependant on the status of the game
 * @author Bobby Drop Tables
 *
 */
@SuppressWarnings("serial")
public class GameTree extends JPanel implements MouseListener{
	private static GameTree instance = null;
	
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
	
	boolean isInactiveCollapsed = true;
	boolean isActiveCollapsed = true;
	boolean isHistoryCollapsed = true;

	private List<PPRequirement> reqList = new ArrayList<PPRequirement>();
	
	/**
	 * Constructor for a GameTree
	 */
	private GameTree(){
		super(new GridBagLayout());
		ViewEventController.getInstance().setGameOverviewTree(this);
		refresh();
	}
	
	/**
	 * Instantiates the game tree if it does not already exist.
	 * Otherwise it returns the current version of the GameTree
	 * @return the singleton Game Tree
	 */
	public static GameTree getInstance(){
		if(instance == null){
			instance = new GameTree();
		}
		return instance;
	}

	/**
	 * Regenerates the table's components whenever called.
	 * Used when the list of games is updated or changed.
	 */
	public void refresh(){
		if(getComponentCount() != 0){			
			isInactiveCollapsed = gameTree.isCollapsed(new TreePath(inactive.getPath()));
			isActiveCollapsed = gameTree.isCollapsed(new TreePath(active.getPath()));			
			isHistoryCollapsed = gameTree.isCollapsed(new TreePath(history.getPath()));
			remove(0);
		}
		
		active.removeAllChildren();
		inactive.removeAllChildren();
		history.removeAllChildren();
		
		List<Game> gameList = 
				sortGames(GameModel.getInstance().getGames());//retrieve list of all games		

		System.out.println("Numb Games: " + gameList.size());
		for (Game game: gameList){
			DefaultMutableTreeNode newGameNode = new DefaultMutableTreeNode(game);
			
			if(!game.isComplete()){ //If the game is not complete and it is active, then add it to the active game dropdown

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
		System.out.println("Numb Games: " + gameList.size());

		
		gameTree = new JTree(gameNode);
		
		gameTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		gameTree.setToggleClickCount(0);
		gameTree.addMouseListener(this);
		
		gameTreeScroll = new JScrollPane(gameTree);
		gameTreeScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		gameTreeScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);		
		gameTreeScroll.setPreferredSize(new Dimension(190, 500));
		
		gameTree.setCellRenderer(new CustomTreeCellRenderer());

		SpringLayout layout = new SpringLayout();
		setLayout(layout);
	  
		SpringLayout.Constraints  cons;
        cons = layout.getConstraints(gameTreeScroll);
        cons.setX(Spring.constant(0));
        cons.setY(Spring.constant(0));
        cons.setWidth(Spring.scale(layout.getConstraint(SpringLayout.EAST, this), .995f)); // must be as close as possible to 1f without being 1f. 1f breaks it for some reason
        cons.setHeight(layout.getConstraint(SpringLayout.SOUTH, this));
		
		add(gameTreeScroll);
	    ViewEventController.getInstance().setGameOverviewTree(this);
	    
	    
	    if(isInactiveCollapsed){
	    	gameTree.collapsePath(new TreePath(inactive.getPath()));
	    }
	    else{
	    	gameTree.expandPath(new TreePath(inactive.getPath()));
	    }
	   
	    if(isActiveCollapsed){
	    	gameTree.collapsePath(new TreePath(active.getPath()));
	    }
	    else{
	    	gameTree.expandPath(new TreePath(active.getPath()));
	    }
	    	    
	    if(isHistoryCollapsed){
	    	gameTree.collapsePath(new TreePath(history.getPath()));
	    }
	    else{
	    	gameTree.expandPath(new TreePath(history.getPath()));
	    }
	    
	    
	    revalidate();
	    gameTree.revalidate();
	    gameTreeScroll.revalidate();
	    repaint();
	    gameTree.repaint();
		gameTreeScroll.repaint();
	    validate();
		
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 * 
	 * This method is overridden in order to allow us to instantiated the games model when the network has been instantiated
	 * 
	 */
	@Override
	public void paintComponent(Graphics g){
		if(!initialized){
			try{
				GetGameController.getInstance().retrieveGames();
				RetrievePPRequirementController.getInstance().retrievePPRequirements();
				List<PPRequirement> addingReqList = PPRequirementModel.getInstance().getRequirements();
				List<Game> addingGameList = GameModel.getInstance().getGames();
				System.out.println("List of Requirements: " + addingReqList);
				System.out.println("List of Games: " + addingGameList);
				for(Game emptyGame : addingGameList) {
					List<PPRequirement> emptyGameReqs = new ArrayList<PPRequirement>();
					for(PPRequirement r : addingReqList) {
						if(r.getIdentity().equals(emptyGame.getIdentity())) {
							emptyGameReqs.add(r);
						}
					}
					emptyGame.setRequirements(emptyGameReqs);
				}
				
				
				
				GameModel.getInstance().updateGames(addingGameList.toArray(new Game[1]));
				
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

	/**
	 * Gets the current game tree
	 * @return
	 */
	private JTree getGameTree(){
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
						Game selectedGame = (Game)node.getUserObject();
						System.out.println("Setting view to game: " + 
								selectedGame.toString());
						if(selectedGame.isActive() &&
								(!selectedGame.isComplete())){
							ViewEventController.getInstance().joinGame(selectedGame);
						}
						else if(!selectedGame.isComplete()){
							ViewEventController.getInstance().editGame(selectedGame);
						}
						else{
							ViewEventController.getInstance().viewEndGame(selectedGame);
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
