/**
 * @author Tom Paolillo, Jonathan Leitschuh, Jeff Signore
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.tree;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetGameController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;

@SuppressWarnings("serial")
public class GameTree extends JScrollPane{
	private JPanel viewPort;
	private boolean initialized = false; //Used to check if the GameModel should be generated from the server.
	
	/**
	 * Constructor for a GameTree
	 */
	public GameTree(){
		this.setViewportView(viewPort);
		ViewEventController.getInstance().setGameOverviewTree(this);
		this.refresh();
	}
	
	

	/**
	 * Regenerates the table's components whenever called.
	 * Used when the list of games is updated or changed.
	 */
	public void refresh(){
		DefaultMutableTreeNode active = new DefaultMutableTreeNode("Active Games"); //Makes the starting node
		DefaultMutableTreeNode history = new DefaultMutableTreeNode("Game History"); //Makes the starting node
		
		List<Game> games = sortGames(GameModel.getInstance().getGames()); //retrive the list of all of the games
		System.out.println("Numb Games: " + games.size());
		for (int i = 0; i<games.size(); i++){
			DefaultMutableTreeNode newGameNode = new DefaultMutableTreeNode(games.get(i).toString());
			
			//TODO add the subrequirements for that game to this dropdown
			
			
			if(!games.get(i).isComplete()){ //If the game is not complete then add it to the active game dropdown
				active.add(newGameNode);
			} else { //If the game is complete then put it in the history
				history.add(newGameNode);
			}
		}
		JTree activeTree = new JTree(active);
		JTree historyTree = new JTree(history);
		

		int width = 150;
		activeTree.setPreferredSize(new Dimension(width , games.size()*21));
		historyTree.setPreferredSize(new Dimension(width, games.size()*21));
		
		JScrollPane activeTreeScroll = new JScrollPane(activeTree);
		activeTreeScroll.setPreferredSize(new Dimension(width + 25, 200));
		JScrollPane historyTreeScroll = new JScrollPane(historyTree);
		historyTreeScroll.setPreferredSize(new Dimension(width + 25, 200));
		
		viewPort = new JPanel();
	    viewPort.add(activeTreeScroll);
	    viewPort.add(historyTreeScroll);
	    
	    viewPort.setPreferredSize(new Dimension(176,410));
		
	    this.setViewportView(viewPort);
	    
	    ViewEventController.getInstance().setGameOverviewTree(this);
	    this.repaint();
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
	
}

class GameComparator implements Comparator<Game>{
	public int compare(Game G1, Game G2){
		return G1.getCreationTime().compareTo(G2.getCreationTime());
	}
}
