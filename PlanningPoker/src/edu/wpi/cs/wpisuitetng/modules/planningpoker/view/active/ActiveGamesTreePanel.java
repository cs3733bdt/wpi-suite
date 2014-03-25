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
public class ActiveGamesTreePanel extends JPanel {

	private JPanel gamesPanel = new JPanel();
	/**
	 * Sets up the left hand panel of the overview
	 */
	public ActiveGamesTreePanel()
	{
		this.gamesPanel.setPreferredSize(new Dimension(150,350));		
		this.add(new JoinGameTree());
		this.add(new CreateGameButtonPanel());     
	}
}