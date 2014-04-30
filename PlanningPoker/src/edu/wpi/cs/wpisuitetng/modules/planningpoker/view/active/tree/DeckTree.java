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

import edu.wpi.cs.wpisuitetng.modules.planningpoker.deck.controllers.GetDeckController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.deck.models.Deck;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.deck.models.DeckModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;

/**
 * Displays and lists the game nodes and stores games in them which
 * is dependant on the status of the game
 * @author Bobby Drop Tables
 *
 */
public class DeckTree extends JPanel implements MouseListener{
	private static DeckTree instance = null;
	
	private boolean initialized = false; //Check if GameModel should be generated from the server
	JTree deckTree; // JTree to hold the hierarchy of games
	JScrollPane gameTreeScroll; // scrollPane to put the tree in
	DefaultMutableTreeNode deckNode = 
			new DefaultMutableTreeNode("Decks"); //Make master node hold the other 3
	DefaultMutableTreeNode currentDecks = 
			new DefaultMutableTreeNode("Your Decks"); //Make pending games node
	DefaultMutableTreeNode active = 
			new DefaultMutableTreeNode("Blue Decks"); //Make active games node
	DefaultMutableTreeNode history = 
			new DefaultMutableTreeNode("Green Decks"); //Make games history node
	
	boolean isInactiveCollapsed = true;
	boolean isActiveCollapsed = true;
	boolean isHistoryCollapsed = true;
	
	/**
	 * Constructor for a GameTree
	 */
	public DeckTree(){
		super(new GridBagLayout());
		ViewEventController.getInstance().setDeckOverviewTree(this);
		refresh();
	}

	/**
	 * Instantiates the game tree if it does not already exist.
	 * Otherwise it returns the current version of the GameTree
	 * @return the singleton Game Tree
	 */
	public static DeckTree getInstance(){
		if(instance == null){
			instance = new DeckTree();
		}
		return instance;
	}
	
	/**
	 * Regenerates the table's components whenever called.
	 * Used when the list of games is updated or changed.
	 */
	public void refresh(){
		if(getComponentCount() != 0){			
			isInactiveCollapsed = deckTree.isCollapsed(new TreePath(currentDecks.getPath()));
			isActiveCollapsed = deckTree.isCollapsed(new TreePath(active.getPath()));			
			isHistoryCollapsed = deckTree.isCollapsed(new TreePath(history.getPath()));
			remove(0);
		}
		
		active.removeAllChildren();
		currentDecks.removeAllChildren();
		history.removeAllChildren();
		
		List<Deck> deckList = DeckModel.getInstance().getDecks();//retrieve list of all games
		System.out.println("Numb Decks: " + deckList.size());
		for (Deck deck: deckList){
			DefaultMutableTreeNode newDeckNode = new DefaultMutableTreeNode(deck);
			currentDecks.add(newDeckNode);
		}
		
		deckNode.add(currentDecks);
		deckNode.add(active);
		deckNode.add(history);
		System.out.println("Numb Games: " + deckList.size());

		
		deckTree = new JTree(deckNode);
		
		deckTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		deckTree.setToggleClickCount(0);
		deckTree.addMouseListener(this);
		
		gameTreeScroll = new JScrollPane(deckTree);
		gameTreeScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		gameTreeScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);		
		gameTreeScroll.setPreferredSize(new Dimension(190, 500));
		

		SpringLayout layout = new SpringLayout();
		setLayout(layout);
	  
		SpringLayout.Constraints  cons;
        cons = layout.getConstraints(gameTreeScroll);
        cons.setX(Spring.constant(0));
        cons.setY(Spring.constant(0));
        cons.setWidth(Spring.scale(layout.getConstraint(SpringLayout.EAST, this), .995f)); // must be as close as possible to 1f without being 1f. 1f breaks it for some reason
        cons.setHeight(layout.getConstraint(SpringLayout.SOUTH, this));
		
		add(gameTreeScroll);
	    //ViewEventController.getInstance().setGameOverviewTree(this);
	    
	    
	    if(isInactiveCollapsed){
	    	deckTree.collapsePath(new TreePath(currentDecks.getPath()));
	    }
	    else{
	    	deckTree.expandPath(new TreePath(currentDecks.getPath()));
	    }
	   
	    if(isActiveCollapsed){
	    	deckTree.collapsePath(new TreePath(active.getPath()));
	    }
	    else{
	    	deckTree.expandPath(new TreePath(active.getPath()));
	    }
	    	    
	    if(isHistoryCollapsed){
	    	deckTree.collapsePath(new TreePath(history.getPath()));
	    }
	    else{
	    	deckTree.expandPath(new TreePath(history.getPath()));
	    }
	    
	    
	    revalidate();
	    deckTree.revalidate();
	    gameTreeScroll.revalidate();
	    repaint();
	    deckTree.repaint();
		gameTreeScroll.repaint();
	    validate();
		
	}
	
	@Override
	public void paintComponent(Graphics g){
		if(!initialized){
			try{
				GetDeckController.getInstance().retrieveDecks();
				initialized = true;
			} catch (Exception e){
				System.err.println("Problem instantiating the Game Model. " + e);
			}
		}
		super.paintComponent(g);
	}
	
	
	/**
	 * @param list the list of decks to be sorted
	 * @return the same list sorted by start date
	 */
	public List<Deck> sortDecks(List<Deck> list) {
		
		//Collections.sort(list, new DeckComparator());
		return list;
	}

	public JTree getDeckTree(){
		return deckTree;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		//System.out.println("Single Click Detected");
		
		if(e.getClickCount() == 2){
			System.out.println("Double Click Detected");
			TreePath treePath = deckTree.getPathForLocation(x, y);
			JTree clicked = deckTree;
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
						if(deckTree.isCollapsed(deckTree.getPathForLocation(x, y))){
							deckTree.expandPath(deckTree.getPathForLocation(x, y));
						}
						else {
							deckTree.collapsePath(deckTree.getPathForLocation(x, y));
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
 * Used to sort decks by their creation time.
 *
 */
class DeckComparator implements Comparator<Deck>{
	@Override
	public int compare(Deck D1, Deck D2){
		return -(D1.getName().compareTo(D2.getName()));
	}
}