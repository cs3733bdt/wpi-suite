package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.AddGameController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation.CreateGamePanel;

/**
 * 
 * @author TomPaolillo
 *
 */

public class AddGameButtonPanel extends ToolbarGroupView{

	
	JPanel contentPanel = new JPanel();
	JButton addGameButton = new JButton("Add Game");	
	
	public AddGameButtonPanel(CreateGamePanel panel){
		super("");
		
		this.contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		this.setPreferredSize(new Dimension(110,35));
		this.addGameButton.setHorizontalAlignment(SwingConstants.CENTER);
		
		// the action listener for the Create Game Button
		addGameButton.addActionListener(new AddGameController(GameModel.getInstance(), panel) {
		});
		
		contentPanel.add(addGameButton);
		contentPanel.setOpaque(false);
		

		this.add(contentPanel);
	}
	/**
	 * Method getCreateGameButton.
	
	 * @return JButton */
	public JButton getAddGameButton() {
		return addGameButton;
	}
	
}
