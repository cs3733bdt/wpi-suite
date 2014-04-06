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
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.game.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.requirement.Requirement;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.vote.Vote;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;

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
	
	JTextArea counter = new JTextArea();
	
	
	public EstimatePanel(Game game, Requirement requirement){
		this.activeGame = game;
		this.activeRequirement = requirement;
		
		this.overviewPanel =  new Container();
		overviewPanel.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();

		/**
		 * Creates and adds the user story text area to the view.
		 */
		userStoryDesc.setText(requirement.getDescription());

		// create a dummy JTextArea
		JTextArea editingArea = new JTextArea();
		// get the current font
		Font f = editingArea.getFont();

		// create a new, smaller font from the current font
		Font newFont = new Font(f.getFontName(), f.getStyle(), f.getSize() + 5);

		// set the bigger font for userStoryDesc
		Font bigFont = newFont;
		
		JScrollPane userStoryPane = new JScrollPane(userStoryDesc);
		userStoryDesc.setFont(bigFont);
		userStoryDesc.setLineWrap(true);
		userStoryDesc.setEditable(false);
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 4;
		userStoryPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		userStoryPane.setPreferredSize(new Dimension(500, 150));
		userStoryPane.setMaximumSize(new Dimension(500, 150));
		overviewPanel.add(userStoryPane, c);

		/**
		 * Blank Panel for formatting purposes
		 */
		JPanel blankPanel0 = new JPanel();
		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 2;
		blankPanel0.setPreferredSize(new Dimension(500, 10));
		overviewPanel.add(blankPanel0, c);
		
		/**
		 * Creates a panel for all the cards
		 */
		JPanel cardPanel = new JPanel();
		cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.X_AXIS));
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 2;
		cardPanel.setPreferredSize(new Dimension(500, 50));		
		overviewPanel.add(cardPanel, c);
		
		/**
		 * Formatting purposes
		 */
		cardPanel.add(Box.createRigidArea(new Dimension(75,0)));
		
		/**
		 * Creates and adds the 1st card to the view.
		 */
		JButton but0 = new JButton("1");
		cardPanel.add(but0);

		/**
		 * Creates and adds the 2nd card to the view.
		 */
		JButton but1 = new JButton("1");
		cardPanel.add(but1);

		/**
		 * Creates and adds the 3rd card to the view.
		 */
		JButton but2 = new JButton("2");
		cardPanel.add(but2);

		/**
		 * Creates and adds the 4th card to the view.
		 */
		JButton but3 = new JButton("3");
		cardPanel.add(but3);

		/**
		 * Creates and adds the 5th card to the view.
		 */
		JButton but4 = new JButton("5");
		cardPanel.add(but4);

		/**
		 * Creates and adds the 6th card to the view.
		 */
		JButton but5 = new JButton("8");
		cardPanel.add(but5);

		/**
		 * Creates and adds the 7th card to the view.
		 */
		JButton but6 = new JButton("13");
		cardPanel.add(but6);

		/**
		 * Creates and adds the 8th card to the view.
		 */
		JButton but7 = new JButton("0?");
		cardPanel.add(but7);
		
		/**
		 * The text area where the user types their estimate
		 */
		estText.setText("Estimate Here");
		estText.setPreferredSize(new Dimension(100, 50));
		c.anchor = GridBagConstraints.CENTER;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 6;
		estText.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				estText.setText("");
			}
		});
		
		overviewPanel.add(estText, c);
		
		overviewPanel.validate();
		overviewPanel.revalidate();
		overviewPanel.invalidate();
		overviewPanel.repaint();
		
		/**
		 * The label for the counter
		 */
		JLabel counterLabel = new JLabel("Your current estimate total: ");
		c.anchor = GridBagConstraints.LINE_END;
		c.insets = new Insets(0, 77, 0, 0);
		c.gridx = 0;
		c.gridwidth = 1;
		c.gridy = 7;
		overviewPanel.add(counterLabel, c);
		
		/**
		 * Formats and adds the counter for the card estimates
		 */
		counter.setText("0");
		counter.setPreferredSize(new Dimension(25, 25));
		counter.setLineWrap(true);
		counter.setEditable(false);
		c.insets = new Insets(0, 0, 0, 0);
		c.anchor = GridBagConstraints.CENTER;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 7;
		
		overviewPanel.add(counter, c);

		if (game.doesUseCards()) {
			estText.setVisible(false);
		} else {
			cardPanel.setVerifyInputWhenFocusTarget(false);
			counter.setVisible(false);
			counterLabel.setVisible(false);
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
		 * The submit button for when the user is ready to submit the estimate
		 */
		JButton submitEstimate = new JButton("Submit Estimate");
		submitEstimate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				submitEstimatePressed();
			}
		});

		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 8;
		overviewPanel.add(submitEstimate, c);

		/**
		 * label for displaying errors
		 */
		errorField = new JLabel();
		errorField.setMinimumSize(new Dimension(150, 25));
		errorField.setForeground(Color.RED);
		c.gridx = 0;
		c.gridy = 9;
		overviewPanel.add(errorField, c);
		
		this.add(overviewPanel);

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
