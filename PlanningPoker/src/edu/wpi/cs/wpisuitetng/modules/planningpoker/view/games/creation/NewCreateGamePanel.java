package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.requirement.Requirement;



/**
 * Used to create a new Planning Poker game using the input of the user.
 */

public class NewCreateGamePanel extends JSplitPane {
	NewLeftHalfCreateGamePanel leftHalf = new NewLeftHalfCreateGamePanel();
	NewRightHalfCreateGamePanel rightHalf = new NewRightHalfCreateGamePanel(this);
	
	public NewCreateGamePanel() {
		this.setRightComponent(rightHalf);
		this.setLeftComponent(leftHalf);
		this.setDividerLocation(400);
		
	}
	
	public static void main(String args[]){
		JFrame frame = new JFrame("GridBagLayoutDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up the content pane.
        frame.add(new NewCreateGamePanel());
        frame.setSize(400, 400);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
	}

	public void addRequirement(Requirement requirement) {
		//this.requirements.add(requirement);
	}
	
	
	
	
	
}