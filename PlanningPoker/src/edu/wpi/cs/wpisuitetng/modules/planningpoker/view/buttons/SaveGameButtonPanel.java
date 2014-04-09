package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation.CreateGamePanel;

/**
 * 
 * @author TomPaolillo
 *
 */

public class SaveGameButtonPanel extends ToolbarGroupView{
	private final CreateGamePanel parentPanel;

	
	JPanel contentPanel = new JPanel();
	JButton saveGameButton = new JButton("Save Game");	
	
	public SaveGameButtonPanel(CreateGamePanel panel){
		super("");
		
		this.contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		this.setPreferredSize(new Dimension(150,50));
		this.saveGameButton.setHorizontalAlignment(SwingConstants.CENTER);
		this.saveGameButton.setPreferredSize(new Dimension(150, 50));
		//this.contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		this.setPreferredSize(new Dimension(200,50));
		this.saveGameButton.setHorizontalAlignment(SwingConstants.CENTER);
		this.saveGameButton.setPreferredSize(new Dimension(125, 25));
		
		// the action listener for the Create Game Button
		setupActionListeners();
		
		contentPanel.add(saveGameButton);
		contentPanel.setOpaque(false);
		

		this.add(contentPanel);
		parentPanel = panel;
	}
	/**
	 * Method getSaveGameButton.
	
	 * @return JButton */
	public JButton getSaveGameButton() {
		return saveGameButton;
	}
	
	void setupActionListeners(){
		saveGameButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				parentPanel.SaveGamePressed();
			}
		});
	}
	
}
