/**
 * @author TomPaolillo
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.tree;

import java.awt.Dimension;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;

public class JoinGameTree extends JScrollPane{
	private JPanel viewPort;
	
	public JoinGameTree(){
		DefaultMutableTreeNode active;
		DefaultMutableTreeNode history;
		active = new DefaultMutableTreeNode("Active Games");
		history = new DefaultMutableTreeNode("Past Games");
		
		this.createNodes(active, history);
	    JTree activeTree = new JTree(active);
	    JTree historyTree = new JTree(history);
	    
	    int width = 175;
		activeTree.setPreferredSize(new Dimension(width , 200));
		historyTree.setPreferredSize(new Dimension(width, 200));
		viewPort = new JPanel();
	    viewPort.add(activeTree);
	    viewPort.add(historyTree);
	    
	    viewPort.setPreferredSize(new Dimension(176,400));
		
	    this.setViewportView(viewPort);
	}
	
	
	/**
	 * Takes in a root tree node and creates a hierarchy of nodes under it
	 * @param active 
	 */
	private void createNodes(DefaultMutableTreeNode active, DefaultMutableTreeNode history) {
	    DefaultMutableTreeNode game = null;
	    DefaultMutableTreeNode requirement = null;
	    
	    // game 1
	    game = new DefaultMutableTreeNode("Game 1");
	    active.add(game);
	    
	    // requirements for game 1
	    requirement = new DefaultMutableTreeNode("requirement 1");
	    game.add(requirement);
	    
	    requirement = new DefaultMutableTreeNode("requirement 2");
	    game.add(requirement);
	    
	    requirement = new DefaultMutableTreeNode("requirement 3");
	    game.add(requirement);

	    
	    // game 2
	    game = new DefaultMutableTreeNode("Game 2");
	    active.add(game);

	    // requirements for game 2
	    requirement = new DefaultMutableTreeNode("requirement 1");
	    game.add(requirement);

	    requirement = new DefaultMutableTreeNode("requirement 2");
	    game.add(requirement);
	    
	    
	    // game 3
	    game = new DefaultMutableTreeNode("Game 3");
	    active.add(game);

	    // requirements for game 3
	    requirement = new DefaultMutableTreeNode("requirement 1");
	    game.add(requirement);

	    requirement = new DefaultMutableTreeNode("requirement 2");
	    game.add(requirement);
	    
	    requirement = new DefaultMutableTreeNode("requirement 3");
	    game.add(requirement);
	    
	    requirement = new DefaultMutableTreeNode("requirement 4");
	    game.add(requirement);
	    
	    game = new DefaultMutableTreeNode("Game 20");
	    history.add(game);
	    
	}
	
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
		

		int width = 175;
		activeTree.setPreferredSize(new Dimension(width , 200));
		historyTree.setPreferredSize(new Dimension(width, 200));
		viewPort = new JPanel();
	    viewPort.add(activeTree);
	    viewPort.add(historyTree);
	    
	    viewPort.setPreferredSize(new Dimension(176,400));
		
	    this.setViewportView(viewPort);
	    
	    ViewEventController.getInstance().setGameOverviewTree(this);
	    
	    System.out.println("Finished refresshing the tree");
		
	}
	
	/**
	 * @param list the list of iterations to be sorted
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
