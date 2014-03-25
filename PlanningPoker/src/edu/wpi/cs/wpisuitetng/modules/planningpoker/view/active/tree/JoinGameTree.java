/**
 * @author TomPaolillo
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.tree;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;

public class JoinGameTree extends JPanel{
		
	public JoinGameTree(){
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Active Games");
		
		this.createNodes(top);
	    JTree gamesTree = new JTree(top);
	    JScrollPane gameTreeScroll = new JScrollPane(gamesTree);
		
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
