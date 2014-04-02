package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	private final CreateGamePanel parentPanel;

	
	JPanel contentPanel = new JPanel();
	JButton addGameButton = new JButton("Add Game");	
	
	public AddGameButtonPanel(CreateGamePanel panel){
		super("");
		
		this.contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		this.setPreferredSize(new Dimension(200,100));
		this.addGameButton.setHorizontalAlignment(SwingConstants.CENTER);
		this.addGameButton.setPreferredSize(new Dimension(200, 50));
		
		// the action listener for the Create Game Button
		setupActionListeners();
		
		contentPanel.add(addGameButton);
		contentPanel.setOpaque(false);
		

		this.add(contentPanel);
		parentPanel = panel;
	}
	/**
	 * Method getCreateGameButton.
	
	 * @return JButton */
	public JButton getAddGameButton() {
		return addGameButton;
	}
	
	void setupActionListeners(){
		addGameButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				parentPanel.AddGamePressed();
			}
		});
	}
	
}
