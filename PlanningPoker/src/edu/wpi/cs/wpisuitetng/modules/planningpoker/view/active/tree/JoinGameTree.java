/**
 * @author TomPaolillo
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.tree;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class JoinGameTree extends JPanel{
		
	public JoinGameTree(){
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Active Games");
		
		this.createNodes(top);
	    JTree gamesTree = new JTree(top);
	    JScrollPane gameTreeScroll = new JScrollPane(gamesTree);
	    gameTreeScroll.setPreferredSize(new Dimension(175,400));
		
	    this.add(gameTreeScroll);
		}
	
	
	/**
	 * Takes in a root tree node and creates a hierarchy of nodes under it
	 * @param top 
	 */
	private void createNodes(DefaultMutableTreeNode top) {
	    DefaultMutableTreeNode game = null;
	    DefaultMutableTreeNode requirement = null;
	    
	    // game 1
	    game = new DefaultMutableTreeNode("Game 1");
	    top.add(game);
	    
	    // requirements for game 1
	    requirement = new DefaultMutableTreeNode("requirement 1");
	    game.add(requirement);
	    
	    requirement = new DefaultMutableTreeNode("requirement 2");
	    game.add(requirement);
	    
	    requirement = new DefaultMutableTreeNode("requirement 3");
	    game.add(requirement);

	    
	    // game 2
	    game = new DefaultMutableTreeNode("Game 2");
	    top.add(game);

	    // requirements for game 2
	    requirement = new DefaultMutableTreeNode("requirement 1");
	    game.add(requirement);

	    requirement = new DefaultMutableTreeNode("requirement 2");
	    game.add(requirement);
	    
	    
	    // game 3
	    game = new DefaultMutableTreeNode("Game 3");
	    top.add(game);

	    // requirements for game 3
	    requirement = new DefaultMutableTreeNode("requirement 1");
	    game.add(requirement);

	    requirement = new DefaultMutableTreeNode("requirement 2");
	    game.add(requirement);
	    
	    requirement = new DefaultMutableTreeNode("requirement 3");
	    game.add(requirement);
	    
	    requirement = new DefaultMutableTreeNode("requirement 4");
	    game.add(requirement);
	    
	    
	}
	
}
