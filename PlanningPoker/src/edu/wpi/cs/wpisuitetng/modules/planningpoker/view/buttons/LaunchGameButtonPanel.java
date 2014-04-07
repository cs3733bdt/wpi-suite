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

public class LaunchGameButtonPanel extends ToolbarGroupView{
	private final CreateGamePanel parentPanel;

	
	JPanel contentPanel = new JPanel();
	JButton launchGameButton = new JButton("Launch Game");	
	
	public LaunchGameButtonPanel(CreateGamePanel panel){
		super("");
		
		this.contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		this.setPreferredSize(new Dimension(150,50));
		this.launchGameButton.setHorizontalAlignment(SwingConstants.CENTER);
		this.launchGameButton.setPreferredSize(new Dimension(150, 50));
		
		// the action listener for the Create Game Button
		setupActionListeners();
		
		contentPanel.add(launchGameButton);
		contentPanel.setOpaque(false);
		

		this.add(contentPanel);
		parentPanel = panel;
	}
	/**
	 * Method getLaunchGameButton.
	
	 * @return JButton */
	public JButton getLaunchGameButton() {
		return launchGameButton;
	}
	
	void setupActionListeners(){
		launchGameButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				parentPanel.LaunchGamePressed();
			}
		});
	}
	
}
