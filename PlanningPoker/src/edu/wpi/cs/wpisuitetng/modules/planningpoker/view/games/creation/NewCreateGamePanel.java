package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation;

import javax.swing.JFrame;
import javax.swing.JSplitPane;



/**
 * Used to create a new Planning Poker game using the input of the user.
 */

public class NewCreateGamePanel extends JSplitPane {
	NewLeftHalfCreateGamePanel leftHalf = new NewLeftHalfCreateGamePanel();
	NewRightHalfCreateGamePanel rightHalf = new NewRightHalfCreateGamePanel();
	
	public NewCreateGamePanel() {

		this.setRightComponent(rightHalf);
		this.setLeftComponent(leftHalf);
		this.setDividerLocation(700);
		
	}
	
	public static void main(String args[]){
		JFrame frame = new JFrame("Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up the content pane.
        frame.add(new NewCreateGamePanel());
        frame.setSize(800, 800);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
	}
}