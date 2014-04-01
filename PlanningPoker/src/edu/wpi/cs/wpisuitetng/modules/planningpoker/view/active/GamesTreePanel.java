package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active;

import java.awt.Dimension;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.tree.JoinGameTree;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons.CreateGameButtonPanel;

/**
 * @author TomPaolillo
 *
 */
public class GamesTreePanel extends JPanel {

	private JPanel gamesPanel = new JPanel();
	/**
	 * Sets up the left hand panel of the overview
	 */
	public GamesTreePanel()
	{
		this.gamesPanel.setPreferredSize(new Dimension(200,600));		
		this.gamesPanel.add(new JoinGameTree());
		this.gamesPanel.add(new CreateGameButtonPanel());     
		this.add(gamesPanel);
	}
}