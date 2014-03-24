package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active;

import java.awt.event.MouseListener;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionListener;

public class ActiveGamesTreePanel extends JScrollPane /*implements MouseListener, TreeSelectionListener*/{

	private JTree tree;
	/**
	 * Sets up the left hand panel of the overview
	 */
	public ActiveGamesTreePanel()
	{
        this.setViewportView(tree);
        //ViewEventController.getInstance().setOverviewTree(this);
		//this.refresh();  
	}
}