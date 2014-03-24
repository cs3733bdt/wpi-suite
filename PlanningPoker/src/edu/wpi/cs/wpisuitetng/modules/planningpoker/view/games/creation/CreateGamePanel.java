/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Used to create a new Planning Poker game using the input of a user
 * Allows the user to select a set of requirements from a list and create a game out of these.
 * @author jonathanleitschuh
 *
 */
public class CreateGamePanel extends JPanel {
	
	private boolean readyToClose = false;
	private boolean readyToRemove = true; //The window starts off ready to remove because no changes have happened
	
	//TODO add an implemenation of the game
	//Game displayGame;
	
	/**
	 * Constructor for creating a game
	 */
	public CreateGamePanel(){
		super(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		//TODO add the implementation of a game
				//displaygame = new Game();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		//c.anchor = GridBagConstraints.PAGE_START;
		c.gridx = 1;
		c.gridy = 1;
		add(new JLabel("Game Name:"),c);
		c.gridx = 3;
		add(new JTextField(30), c);
		
		
		
	}
	
	/**
	 * Checks to see if the panel has unsaved changes
	 * @return whether the CreateGamePanel as a whole is ready to be removed.
	 */
	public boolean readyToRemove(){
		if(readyToClose) return true;
		
		//TODO Check fields to see if this window has unsaved changes
		
		if(readyToRemove){
			return true;
		} else {
			int result = JOptionPane.showConfirmDialog(this, "Discard unsaved changes and close tab?", "Discard Changes?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			return result == 0;
		}
		
		
	}
	
	
	
	public static void main(String args[]){
		JFrame frame = new JFrame("GridBagLayoutDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up the content pane.
        frame.add(new CreateGamePanel());
        frame.setSize(400, 400);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
	}

}
