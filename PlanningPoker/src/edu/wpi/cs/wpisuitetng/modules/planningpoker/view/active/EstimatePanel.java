package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirementmodels.Requirement;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.vote.Vote;

public class EstimatePanel extends JPanel{
	Game activeGame;
	Requirement activeRequirement;
	
	/**
	 * Set the userStoryDesc equal to the description of the requirement being
	 * selected in the table
	 */
	private JTextArea userStoryDesc = new JTextArea();

	/**
	 * The estText is needed when the user inputs their estimate, since it must
	 * be added to the server
	 */
	private JTextField estText = new JTextField();

	/** Shows the names of the errors */
	JLabel errorField = new JLabel();

	
	Container overviewPanel = new Container();
	/** Field Border Definitions */
	private final Border defaultBorder = (new JTextField()).getBorder();
	private final Border errorBorder = BorderFactory
			.createLineBorder(Color.RED);

	/* End field Border Definitions */
	
	
	public EstimatePanel(Game game, Requirement requirement){
		this.activeGame = game;
		this.activeRequirement = requirement;
		
		this.overviewPanel =  new Container();
		overviewPanel.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();

		/**
		 * Creates and adds the user story text area to the view.
		 */
		userStoryDesc.setText(activeRequirement.getDescription());
		
		// create a dummy JTextArea
		JTextArea editingArea = new JTextArea();
		// get the current font
		Font f = editingArea.getFont();

		// create a new, smaller font from the current font
		Font newFont = new Font(f.getFontName(), f.getStyle(), f.getSize() + 5);

		// set the bigger font for userStoryDesc
		Font bigFont = newFont;
		userStoryDesc.setFont(bigFont);
		userStoryDesc.setEditable(false);
		c.gridwidth = 8;
		c.weightx = 0.75;
		c.gridx = 1;
		c.gridy = 3;
		c.ipady = 50;
		overviewPanel.add(userStoryDesc, c);

		/**
		 * Creates and adds the 1st card to the view.
		 */
		JButton but0 = new JButton("1");
		c.gridwidth = 1;
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 4;
		c.ipadx = 20;
		c.ipady = 75;
		overviewPanel.add(but0, c);

		/**
		 * Creates and adds the 2nd card to the view.
		 */
		JButton but1 = new JButton("1");
		c.gridx = 2;
		c.gridy = 4;
		c.ipadx = 20;
		c.ipady = 75;
		overviewPanel.add(but1, c);

		/**
		 * Creates and adds the 3rd card to the view.
		 */
		JButton but2 = new JButton("2");
		c.gridx = 3;
		c.gridy = 4;
		c.ipadx = 20;
		c.ipady = 75;
		overviewPanel.add(but2, c);

		/**
		 * Creates and adds the 4th card to the view.
		 */
		JButton but3 = new JButton("3");
		c.gridx = 4;
		c.gridy = 4;
		c.ipadx = 20;
		c.ipady = 75;
		overviewPanel.add(but3, c);

		/**
		 * Creates and adds the 5th card to the view.
		 */
		JButton but4 = new JButton("5");
		c.gridx = 5;
		c.gridy = 4;
		c.ipadx = 20;
		c.ipady = 75;
		overviewPanel.add(but4, c);

		/**
		 * Creates and adds the 6th card to the view.
		 */
		JButton but5 = new JButton("8");
		c.gridx = 6;
		c.gridy = 4;
		c.ipadx = 20;
		c.ipady = 75;
		overviewPanel.add(but5, c);

		/**
		 * Creates and adds the 7th card to the view.
		 */
		JButton but6 = new JButton("13");
		c.gridx = 7;
		c.gridy = 4;
		c.ipadx = 20;
		c.ipady = 75;
		overviewPanel.add(but6, c);

		/**
		 * Creates and adds the 8th card to the view.
		 */
		JButton but7 = new JButton("0?");
		c.gridx = 8;
		c.gridy = 4;
		c.ipadx = 20;
		c.ipady = 75;
		overviewPanel.add(but7, c);

		/**
		 * The text area where the user types their estimate
		 */
		estText.setText("Estimate Here");
		estText.setPreferredSize(new Dimension(75, 40));
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.gridwidth = 1;
		c.gridx = 5;
		c.gridy = 5;
		estText.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				estText.setText("");
			}
		});

		overviewPanel.add(estText, c);

		if (game.doesUseCards()) {
			estText.setVisible(false);
		} else {
			but0.setVisible(false);
			but1.setVisible(false);
			but2.setVisible(false);
			but3.setVisible(false);
			but4.setVisible(false);
			but5.setVisible(false);
			but6.setVisible(false);
			but7.setVisible(false);
		}

		/**
		 * A blank panel for formatting purposes
		 */
		JPanel invisPanel = new JPanel();
		c.gridwidth = 1;
		c.weightx = 0.5;
		c.gridx = 9;
		c.gridy = 4;
		c.ipadx = 0;
		c.ipady = 0;
		overviewPanel.add(invisPanel, c);

		/**
		 * The submit button for when the user is ready to submit the estimate
		 */
		JButton submitEstimate = new JButton("Submit Estimate");
		submitEstimate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				submitEstimatePressed();
			}
		});

		c.gridwidth = 2;
		c.gridx = 4;
		c.gridy = 6;
		overviewPanel.add(submitEstimate, c);

		/**
		 * label for displaying errors
		 */
		errorField = new JLabel();
		errorField.setMinimumSize(new Dimension(150, 25));
		errorField.setForeground(Color.RED);
		c.gridx = 5;
		c.gridy = 12;
		c.insets = new Insets(0, 175, 0, 0);
		overviewPanel.add(errorField, c);

	}
	
	public JTextField getEstimateText() {
		return estText;
	}
	
	public Game getGame() {
		return activeGame;
	}
	
	public Requirement getRequirement(){
		return activeRequirement;
	}
	
	
	public void submitEstimatePressed() {
		if(getGame().doesUseCards()){
			this.submitEstimate();
			System.out.println("Submit Vote Pressed Passed");
		}
		else{
			if (this.validateField(true)) {
				this.submitEstimate();
				System.out.println("Submit Vote Pressed Passed.");
			} else {
				System.out.println("Submit Vote Pressed Failed.");
			}
		}
	}

	private boolean validateField(boolean warn) {
		boolean isEstimateValid = false;
		
		// check to see if estimate is parsable to int
		boolean parsable = true;
		try{
		Integer.parseInt(getEstimateText().getText());

		}catch(NumberFormatException e){
		parsable = false;
		}
		// end parsable check
		
				
		//BEGIN ESTIMATE BOX VALDATION
		if (getEstimateText().getText().length() <= 0) {
			isEstimateValid = false;
			if (warn) {
				getEstimateText().setBorder(errorBorder);
			}
			displayError("An estimation is required before submission");
		} else if(parsable == true){
			if(Integer.parseInt(getEstimateText().getText()) < 0){
				isEstimateValid = false;
				if (warn) {
					getEstimateText().setBorder(errorBorder);
				}
				displayError("An estimate must be at least 0");
			}
			else{ 
				if(warn) {
					getEstimateText().setBorder(defaultBorder);
				}
				isEstimateValid = true;
			}
		}
		else{
			isEstimateValid = false;
			if (warn) {
				getEstimateText().setBorder(errorBorder);
			}
			displayError("An estimation must contain only numbers");
		}
		
		
		return isEstimateValid;
	}

	public void submitEstimate() {
		String currentUser = ConfigManager.getConfig().getUserName(); // Gets the currently active user
		int voteNumber;
		if(getGame().doesUseCards()){
			voteNumber = 1;
		}
		else{
			voteNumber = Integer.parseInt(estText.getText());
		}
		Vote vote = new Vote(currentUser, voteNumber);
		
		

		// I am currently working on updating a game's requirements to
		// reflect the addition of the vote. Until then, I am printing out
		// the fields of the vote to ensure the information is getting through
		System.out.println("current user: " + vote.getUsername());
		System.out.println("vote number: " + vote.getVoteNumber());
		// these lines should be deleted when proper implementation is complete

		ViewEventController.getInstance().refreshGameTable();
		ViewEventController.getInstance().refreshGameTree();
	}

	public void displayError(String error) {
		errorField.setText(error);
	}
	
}
