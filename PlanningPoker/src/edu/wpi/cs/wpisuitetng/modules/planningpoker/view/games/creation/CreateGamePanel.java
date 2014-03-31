/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.AddGameController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons.AddGameButtonPanel;

/**
 * Used to create a new Planning Poker game using the input of a user
 * Allows the user to select a set of requirements from a list and create a game out of these.
 * @author jonathanleitschuh
 *
 */
public class CreateGamePanel extends JPanel {
	
	//THIS IS THE REQUIREMENT NAME FIELD THAT WILL BE NEEDED FOR CONTROLLER
	private JTextArea nameArea = new JTextArea();
	
	//THIS IS THE REQUIREMENT DESCRIPTION FIELD THAT WILL BE NEEDED FOR CONTROLLER
	private JTextArea descArea = new JTextArea();
	
	private boolean readyToClose = false;
	private boolean readyToRemove = true; //The window starts off ready to remove because no changes have happened
	
	JTextField nameTextField;
	JTextField descriptionTextField;
	//TODO add an implemenation of the game
	Game displayGame;
	
	
	/**
	 * Constructor for creating a game
	 */
	public CreateGamePanel(){
		super(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		nameTextField = new JTextField(30);	
		descriptionTextField = new JTextField(30);
		
		//displayGame = new Game();
		
		Container rightPanel = new Container();
		rightPanel.setLayout(new GridBagLayout());
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTH;
		
		//JLabel gameName = new JLabel("Game Name:");
		
		JLabel createReq = new JLabel("Create Requirement");
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.CENTER;
		
		rightPanel.add(createReq, c);
		
		JLabel importReq = new JLabel("Import Requirement");
		c.gridx = 2;
		c.gridy = 0;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.CENTER;
		
		rightPanel.add(importReq, c);
		
		/*Panel createReqPanel = new Panel();
		createReqPanel.setLayout(new GridBagLayout());
		
		c.gridx = 0;
		c.gridy = 1;
		reqPanel.add(createReqPanel, c);*/
		
		JLabel reqName = new JLabel("Name:");
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 1;
		c.anchor = GridBagConstraints.CENTER;
		
		rightPanel.add(reqName, c);
		
		nameArea.setText("test name");
		c.gridx = 1;
		c.gridy = 1;
		
		rightPanel.add(nameArea, c);
		
		JLabel reqDesc = new JLabel("Description:");
		c.gridx = 0;
		c.gridy = 2;
		
		c.anchor = GridBagConstraints.CENTER;
		
		rightPanel.add(reqDesc, c);
		
		descArea.setText("As a ______________ \nI would like to _______________________ \nso that ________________________________.");
		c.gridwidth = 2;
		c.gridheight = 3;
		c.gridx = 0;
		c.gridy = 3;
		c.ipady = 50;
		
		rightPanel.add(descArea, c);
		
		JButton addReqButton = new JButton("Add Requirement");
		c.gridx = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.gridy = 6;
		c.ipady = 0;
		
		rightPanel.add(addReqButton, c);
		
		add(rightPanel, c);
		
		c.gridx = 0;
		c.gridwidth = 1;
		c.gridy = 7;
		c.insets = new Insets(100, 0, 0, 0);
		add(new AddGameButtonPanel(this), c);		
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
	
	public String getNameText(){
		System.out.println(this.nameTextField.getText());
		return this.nameTextField.getText();
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
	
	public JTextArea getNameArea() {
		return nameArea;
	}
	
	public JTextArea getDescArea() {
		return descArea;
	}

}
