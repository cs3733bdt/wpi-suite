/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.AddGameController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirementmodels.Requirement;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.ActiveGamesTable;
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
	
	private JTextField nameTextField;
	private JTextField descriptionTextField;
	//TODO add an implemenation of the game
	Game displayGame;
	
	private JRadioButton cardsButton = new JRadioButton("Estimate With Cards");
	
	private JRadioButton textEntryButton = new JRadioButton("Estimate With Text Entry");
	
	private ArrayList<Requirement> requirements = new ArrayList<Requirement>();
	
	
	/**
	 * Constructor for creating a game
	 */
	public CreateGamePanel(){
		super(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		nameTextField = new JTextField(30);	
		descriptionTextField = new JTextField(30);		
		
		Container rightPanel = new Container();
		rightPanel.setLayout(new GridBagLayout());
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTH;
		
		JPanel nameAndDescPanel = new JPanel();
		//nameAndDescPanel.setLayout(new GridLayout(2,2));
		nameAndDescPanel.setLayout(new GridBagLayout());
		c.weighty = .5;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 6;
		//c.insets = new Insets(0, 125, 0, 0);
		rightPanel.add(nameAndDescPanel, c);
		
		JLabel gameName = new JLabel("Game Name:");
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		nameAndDescPanel.add(gameName, c);
		
		nameTextField.setMinimumSize(new Dimension(175, 20));
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 1;
		
		nameAndDescPanel.add(nameTextField, c);
		
		JLabel gameDesc = new JLabel("Game Description:");
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		nameAndDescPanel.add(gameDesc, c);
		
		descriptionTextField.setMinimumSize(new Dimension(175, 20));
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		nameAndDescPanel.add(descriptionTextField, c);
		
		JPanel estimateTypePanel = new JPanel();
		estimateTypePanel.setLayout(new BoxLayout(estimateTypePanel, BoxLayout.X_AXIS));
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 6;
		rightPanel.add(estimateTypePanel, c);
		
		
		estimateTypePanel.add(cardsButton);
		estimateTypePanel.add(textEntryButton);
		
		ButtonGroup radioGroup = new ButtonGroup();
        radioGroup.add(cardsButton);
        radioGroup.add(textEntryButton);
		
        c.insets = new Insets(0, 0, 0 ,0);
		c.ipadx = 0;
		c.gridwidth = 6;
		c.gridheight = 6;
		c.gridx = 0;
		c.gridy = 3;
		c.weighty = .5;
		
		add(new AddRequirementsPanel(this), c);
		
		c.gridwidth = 6;
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 0;
		c.weighty = .5;
		add(rightPanel, c);
		
		
		JTextField errorLog = new JTextField("Any errors shown here.");
		errorLog.setMinimumSize(new Dimension(150, 25));
		c.gridx = 2;
		c.gridwidth = 1;
		c.gridy = 9;
		c.insets = new Insets(0, 100, 0, 0);
		add(errorLog, c);
		
		c.gridx = 1;
		c.gridwidth = 1;
		c.gridy = 9;
		c.insets = new Insets(0, 100, 0, 0);
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
	
	/**
	 * Getter for the Game Name text entry
	 * @return nameTextField
	 */
	public String getNameText(){
		return this.nameTextField.getText();
	}
	
	/**
	 * Getter for the Game Description text entry
	 * @return descriptionTextField
	 */
	public String getDescText(){
		return this.descriptionTextField.getText();
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
	
	public void addRequirement(Requirement newReq){
		this.requirements.add(newReq);
	}
	
	
	/**
	 * Getter for the Requirement Name text entry
	 * @return nameArea
	 */
	public JTextArea getReqNameArea() {
		return nameArea;
	}
	
	/**
	 * Getter for the Requirement Description text entry
	 * @return descArea
	 */
	public JTextArea getReqDescArea() {
		return descArea;
	}
	
	public ArrayList<Requirement> getRequirements(){
		return this.requirements;
	}
	
	public void setRequirements(ArrayList<Requirement> newReqs){
		this.requirements = newReqs;
	}

}
