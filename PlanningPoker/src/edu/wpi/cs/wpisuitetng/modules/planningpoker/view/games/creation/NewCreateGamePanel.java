package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation;

import javax.swing.JSplitPane;



/**
 * Used to create a new Planning Poker game using the input of the user.
 */

public class NewCreateGamePanel extends JSplitPane {
	NewLeftHalfCreateGamePanel leftHalf = new NewLeftHalfCreateGamePanel();
	NewRightHalfCreateGamePanel rightHalf = new NewRightHalfCreateGamePanel();
	
	public NewCreateGamePanel() {

		this.setRightComponent(leftHalf);
		this.setLeftComponent(rightHalf);
		this.setDividerLocation(400);
		
	}
}