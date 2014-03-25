package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionListener;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons.CreateGameButtonPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.lists.JoinGameList;

/**
 * @author TomPaolillo
 *
 */
public class ActiveGamesTreePanel extends JPanel /*implements MouseListener, TreeSelectionListener*/{

	private JTree tree;
	private JPanel gamesPanel = new JPanel();
	/**
	 * Sets up the left hand panel of the overview
	 */
	public ActiveGamesTreePanel()
	{
		this.gamesPanel.setBackground(Color.RED);
		this.gamesPanel.setPreferredSize(new Dimension(150,350));		
		this.add(new JoinGameList());
		this.add(new CreateGameButtonPanel());
		
      //  this.setViewportView(tree);
        
        //ViewEventController.getInstance().setOverviewTree(this);
		//this.refresh();  
	}
}