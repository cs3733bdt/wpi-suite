/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.game.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.game.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.requirement.Requirement;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons.LaunchGameButtonPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons.SaveGameButtonPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.ErrorLabel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.NameJTextField;

/**
 * Used to create a new Planning Poker game using the input of a user Allows the
 * user to select a set of requirements from a list and create a game out of
 * these.
 * 
 * @author jonathanleitschuh
 * 
 */
public class CreateGamePanel extends JScrollPane {

	/** Shows the names of the errors */
	private ErrorLabel errorField;

	/** Field Border Definitions */
	private final Border defaultBorder = (new JTextField()).getBorder();
	private final Border errorBorder = BorderFactory
			.createLineBorder(Color.RED);
	/* End field Border Definitions */

	private boolean readyToClose = false;
	private boolean readyToRemove = true; // The window starts off ready to
											// remove because no changes have
											// happened

	private NameJTextField nameTextField;
	private JTextArea descriptionTextField;
	private AddEndDatePanel endDateField;

	private Game currentGame;

	private JRadioButton cardsButton = new JRadioButton("Estimate With Cards");

	private JRadioButton textEntryButton = new JRadioButton(
			"Estimate With Text Entry");

	private ArrayList<Requirement> requirements = new ArrayList<Requirement>();
	
	private Date endDate;

	AddRequirementsPanel addReqPan;
	
	/**
	 * Constructor for creating a game
	 */
	public CreateGamePanel() {
		build();
	}
	
	public CreateGamePanel(Game game){
		build();
		this.currentGame = game;
	}

	private void build() {
		// super(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		nameTextField = new NameJTextField(30);
		descriptionTextField = new JTextArea();
		descriptionTextField.setLineWrap(true);
		endDateField = new AddEndDatePanel(this);

		/**
		 * The ScrollPane that contains everything
		 */
		// JScrollPane createGameScrollPane;

		/**
		 * The Container that contains everything, which goes in the ScrollPane
		 */
		Container rightPanel = new Container();
		rightPanel.setLayout(new GridBagLayout());

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTH;

		/**
		 * Panel for name and description
		 */
		JPanel nameAndDescPanel = new JPanel();
		nameAndDescPanel.setLayout(new GridBagLayout());
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 6;
		rightPanel.add(nameAndDescPanel, c);

		/**
		 * Blank Panel for formatting purposes
		 */
		JPanel blankPanel0 = new JPanel();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.weighty = 0;
		blankPanel0.setPreferredSize(new Dimension(310, 10));
		nameAndDescPanel.add(blankPanel0, c);

		/**
		 * Game name label
		 */
		JLabel gameName = new JLabel("* Game Name:");
		c.weightx = 0;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		nameAndDescPanel.add(gameName, c);

		/**
		 * Game name text field
		 */
		c.weightx = 1;
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		nameTextField.setPreferredSize(new Dimension(440, 25));
		nameAndDescPanel.add(nameTextField, c);

		/**
		 * Blank Panel for formatting purposes
		 */
		JPanel blankPanel = new JPanel();
		blankPanel.setPreferredSize(new Dimension(440, 25));
		c.weightx = 0;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		nameAndDescPanel.add(blankPanel, c);

		/**
		 * Game description Label
		 */
		JLabel gameDesc = new JLabel("* Game Description:  ");
		c.weightx = 0;
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		nameAndDescPanel.add(gameDesc, c);

		/**
		 * ScrollPane for the description field
		 */
		JScrollPane descPane = new JScrollPane(descriptionTextField);

		/**
		 * Adding and configuring the description
		 */
		c.weightx = 1;
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 1;
		descPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		descPane.setMinimumSize(new Dimension(440, 80));
		descPane.setPreferredSize(new Dimension(440, 80));
		nameAndDescPanel.add(descPane, c);

		/**
		 * Panel for estimate radio buttons
		 */
		JPanel estimateTypePanel = new JPanel();
		// estimateTypePanel.setLayout(new BoxLayout(estimateTypePanel,
		// BoxLayout.X_AXIS));
		c.anchor = GridBagConstraints.CENTER;
		// c.weightx = 0;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 6;
		c.weighty = 0;
		// c.insets = new Insets(10, 100, 0, 0);
		// estimateTypePanel.setPreferredSize(new Dimension(125,25));
		rightPanel.add(estimateTypePanel, c);

		/**
		 * Sets the default radio button to be selected (cardsButton)
		 */
		cardsButton.setSelected(true);
		/**
		 * Adds the radio buttons
		 */
		estimateTypePanel.add(cardsButton);
		estimateTypePanel.add(textEntryButton);

		/**
		 * Radio Buttongroup to make only 1 radio button selectable
		 */
		ButtonGroup radioGroup = new ButtonGroup();
		radioGroup.add(cardsButton);
		radioGroup.add(textEntryButton);

		/**
		 * Blank Panel for formatting
		 */
		JPanel blankPanel2 = new JPanel();
		blankPanel2.setMinimumSize(new Dimension(100,25));
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 6;
		c.insets = new Insets(10,0,0,0);
		blankPanel2.setPreferredSize(new Dimension(100, 50));
		endDateField = new AddEndDatePanel(this);
		rightPanel.add(blankPanel2, c);
		blankPanel2.add(endDateField,c);

		/**
		 * Formats and add the AddRequirementPanel
		 */
		c.gridwidth = 6;
		// c.gridheight = 5;
		c.gridx = 0;
		c.gridy = 4;
		// c.weightx = 1;
		addReqPan = new AddRequirementsPanel(this);
		rightPanel.add(addReqPan, c);

		/**
		 * Formats and adds the AddReqImportReqPanel
		 */
		c.gridwidth = 6;
		c.gridx = 0;
		c.gridy = 5;
		// c.gridheight = 1;
		// c.weightx = 1;
		rightPanel.add(new AddReqImportReqPanel(addReqPan), c);

		/**
		 * Blank Panel for formatting
		 */
		JPanel blankPanel5 = new JPanel();
		c.gridx = 0;
		c.gridwidth = 6;
		// c.gridheight = 1;
		c.weightx = 0;
		blankPanel5.setMinimumSize(new Dimension(100, 25));
		rightPanel.add(blankPanel5, c);
		
		/**
		 * Formats and adds the SaveGameButtonPanel
		 */
		c.gridheight = 1;
		c.gridx = 0;
		c.gridwidth = 1;
		c.gridy = 11;
		c.insets = new Insets(0, 150, 0, 0);
		rightPanel.add(new SaveGameButtonPanel(this), c);	
		
		
		/**
		 * Formats and adds the LaunchGameButtonPanel
		 */
		c.gridheight = 1;
		c.gridx = 1;
		c.gridwidth = 1;
		c.gridy = 11;
		c.insets = new Insets(0, 150, 0, 0);
		rightPanel.add(new LaunchGameButtonPanel(this), c);

		/**
		 * Panel to add error label
		 */
		JPanel errorPanel = new JPanel();
		c.gridx = 0;
		c.gridy = 8;
		c.gridwidth = 6;
		c.weightx = 1;
		errorPanel.setMinimumSize(new Dimension(440, 50));
		rightPanel.add(errorPanel, c);

		/**
		 * label for displaying errors
		 */
		errorField = new ErrorLabel();
		errorField.setMinimumSize(new Dimension(150, 25));
		errorField.setForeground(Color.RED);
		/*
		 * c.anchor = GridBagConstraints.CENTER; c.weightx = 1; c.gridx = 0;
		 * c.gridwidth = 2; //c.gridheight = 1; c.gridy = 8; c.insets = new
		 * Insets(0, 0, 10, 0);
		 */
		errorPanel.add(errorField);

		/**
		 * initializes and adds the scroll pane
		 */
		/*
		 * createGameScrollPane = new JScrollPane(rightPanel);
		 * createGameScrollPane.setMinimumSize(new Dimension(600, 550));
		 * createGameScrollPane.repaint(); c.insets= new Insets(0, 0, 0, 0);
		 * c.gridwidth = 6; c.gridheight = 2; c.gridx = 0; c.gridy = 0;
		 * c.weightx = 1; //this.add(rightPanel, c);
		 */
		

		this.getViewport().add(rightPanel);
	}

	public CreateGamePanel(Game game, boolean withError) {
		this();
		nameTextField.setText(game.getName());
		descriptionTextField.setText(game.getDescription());
		if (withError) {
			JOptionPane
					.showMessageDialog(
							null,
							"\tYour connection to the server has been lost.\n"
									+ "\tYour changes have been resored but no further changes to the server can be made.\n"
									+ "\tPlease save your changes to a text file and restart Janeway.",
							"Network Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Checks to see if the panel has unsaved changes
	 * 
	 * @return whether the CreateGamePanel as a whole is ready to be removed.
	 */
	public boolean readyToRemove() {
		if (readyToClose)
			return true;

		// TODO Check fields to see if this window has unsaved changes

		if (readyToRemove) {
			return true;
		} else {
			int result = JOptionPane.showConfirmDialog(this,
					"Discard unsaved changes and close tab?",
					"Discard Changes?", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			return result == 0;
		}

	}

	/**
	 * Getter for the Game Name text entry
	 * 
	 * @return nameTextField
	 */
	public String getNameText() {
		System.out.println(this.nameTextField.getText());
		return this.nameTextField.getText();
	}

	/**
	 * Getter for the Game Description text entry
	 * 
	 * @return descriptionTextField
	 */
	public String getDescText() {
		System.out.println(this.descriptionTextField.getText());
		return this.descriptionTextField.getText();
	}

	public void addRequirement(Requirement newReq) {
		this.requirements.add(newReq);
	}

	public ArrayList<Requirement> getRequirements() {
		return this.requirements;
	}


	
	/**
	 * Triggered when the save game button is pressed using the mouse listener
	 * @return true when a game is sucsessfully added
	 */
	public boolean SaveGamePressed() {
		if(this.validateField(true)){
			this.saveGame();
			readyToClose = true;
			ViewEventController.getInstance().removeTab(this);
			System.out.println("Add Game Pressed Passed.");
			return true;
		} else {
			System.out.println("Add Game Pressed Failed.");
			return false;
		}
		
	}
	
	public boolean LaunchGamePressed() {
		if(this.validateField(true)){
			this.launchGame();
			readyToClose = true;
			ViewEventController.getInstance().removeTab(this);
			System.out.println("Launch Game Pressed Passed.");
			return true;
		} else {
			System.out.println("Launch Game Pressed Failed.");
			return false;
		}
		
	}

	/**
	 * Checks all fields to determine if they are prepared to be removed.
	 * If a field is invalid the it warns the user with a notification and by highlighting
	 * the offending box on the GUI.
	 * @param warn Whether to warn the user via coloring texboxes and warning labels
	 * @return true If all fields are valid and the window is ready to be removed
	 */
	private boolean validateField(boolean warn) {
		boolean isNameValid = false;
		boolean isDescriptionValid = false;
		boolean areRequirementsSelected = false;
		boolean isEndDateValid = false;
		
		isNameValid = getBoxName().verifyField(errorField);
		
		//BEGIN DESCRIPTION BOX VALDATION
		if(getBoxDescription().getText().length() <= 0){
			isDescriptionValid = false;
			if(warn){
				//getErrorDescription().setText("** Description is REQUIRED");
				getBoxDescription().setBorder(errorBorder);
				//getErrorDescription().setForeground(Color.RED);
			}
			//TODO add a way to display error descriptions
			displayError("Description is required");
		} else {
			if (warn){
				//getErrorDescription().setText("");
				getBoxDescription().setBorder(defaultBorder);
			}
			isDescriptionValid = true;
		}
		
		//TODO check if a valid game(s) are selected here
		areRequirementsSelected = true;
		
		//BEGIN END DATE VALIDATION
		endDate = endDateField.getEndDate();
		if(endDate.compareTo(new Date()) >= 0) {
			isEndDateValid = true;
		} else {
			isEndDateValid = false;
			if (warn){
				getBoxDescription().setBorder(errorBorder);
			}
			displayError("End Date is before right now");
		}

		return (isNameValid && isDescriptionValid && areRequirementsSelected && isEndDateValid);
	}
	
	/**
	 * Adds the game to the model and to the server and sets it to inactive
	 * @param endDate 
	 */
	public void  saveGame(){	
		if(currentGame == null){
			setCurrentGame(false);
			GameModel.getInstance().addGame(currentGame);		//New Game gets added to the server
		} else{
			setCurrentGame(false);
		}
		ViewEventController.getInstance().refreshGameTable();
		ViewEventController.getInstance().refreshGameTree();
		
	}
	
	/**
	 * Adds the game to the model and to the server and sets it to active
	 */
	public void  launchGame(){
		if(currentGame == null){
			setCurrentGame(true);
			GameModel.getInstance().addGame(currentGame);		//New Game gets added to the server
		} else{
			setCurrentGame(true);
		}
		ViewEventController.getInstance().refreshGameTable();
		ViewEventController.getInstance().refreshGameTree();
	}

	public NameJTextField getBoxName(){
		return nameTextField;
	}
	
	public void setBoxName(String newName){
		this.nameTextField.setText(newName);
	}
	
	public JTextArea getBoxDescription() {
		return descriptionTextField;
	}
	
	public void setBoxDescription(String newDescription){
		this.descriptionTextField.setText(newDescription);
	}
	
	public JLabel getErrorName(){
		//TODO add errors to the indivitdual fields
		//WHEN FIXED UNCOMMENT THE LINES THAT USE THIS METHOD IN VALIDATE
		return null;
	}
	
	public AddRequirementsPanel getAddReqPan(){
		return this.addReqPan;
	}
	
	public boolean doesUseCards(){
		return cardsButton.isSelected();
	}
	
	public void displayError(String error){
		errorField.setText(error);
	}
	
	public Game getGame(){
		return this.currentGame;
	}
	
	public void setUsesCards(boolean usesCards){
		if(usesCards){
			this.cardsButton.setSelected(true);
			this.textEntryButton.setSelected(false);
		}
		else{
			this.cardsButton.setSelected(false);
			this.textEntryButton.setSelected(true);
		}
	}
	
	
	public AddEndDatePanel getEndDateField(){
		return endDateField;
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
	
	private void setCurrentGame(boolean active){
		currentGame.setName(this.getBoxName().getText());
		currentGame.setDescription(this.getBoxDescription().getText());
		currentGame.setActive(active);
		currentGame.setUsesCards(doesUseCards());
		currentGame.setRequirements(getRequirements());
		currentGame.setEndDate(endDateField.getEndDate());
		currentGame.notifyObservers();
	}

}
