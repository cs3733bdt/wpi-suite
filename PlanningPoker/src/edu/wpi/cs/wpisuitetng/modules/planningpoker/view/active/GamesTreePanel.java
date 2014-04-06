package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.tree.GameTree;
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
		this.gamesPanel.setLayout(new BoxLayout(gamesPanel, BoxLayout.Y_AXIS));		
		this.gamesPanel.add(new GameTree());
		this.add(gamesPanel);
	}
}