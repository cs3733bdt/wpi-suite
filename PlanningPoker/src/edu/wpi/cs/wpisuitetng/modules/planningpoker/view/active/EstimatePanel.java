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
import java.util.ArrayList;

import javax.swing.BorderFactory;
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
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.cards.ActiveCardsPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.cards.CardButton;

public class EstimatePanel extends JPanel{
	Game activeGame;
	Requirement activeRequirement;	
	
	private ArrayList<String> deck = new ArrayList<String>();
	ActiveCardsPanel cardsPanel;
	
	//Label and accumlated sum
	JLabel counterLabel = new JLabel("Your current estimate total: " + 0);
	private int sum = 0;
	
	//initializes the JToggleButton
    private ArrayList<CardButton> JToggleButtonList = new ArrayList<CardButton>();
	
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
    
    /*
	 * If the ArrayList passed in is empty it will use the default deck
	 */
	public EstimatePanel(Game game, Requirement requirement, ArrayList<String> customDeck){ //add a deck of cards as a parameter

		super(new GridBagLayout());

		this.setMinimumSize(new Dimension(580, 200));
		this.repaint();
		this.invalidate();
		this.revalidate();

		this.activeGame = game;
		this.activeRequirement = requirement;
		
		//this.overviewPanel =  new Container();
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
		
		final Font largeFont = new Font("Serif",Font.BOLD,20);
		
		JScrollPane userStoryPane = new JScrollPane(userStoryDesc);
		userStoryDesc.setFont(bigFont);
		userStoryDesc.setLineWrap(true);
		userStoryDesc.setEditable(false);
		c.anchor = GridBagConstraints.CENTER;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 4;
		c.weightx = 1;
		c.weighty = 1;
		userStoryPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		userStoryPane.setMinimumSize(new Dimension(580, 150));
		userStoryPane.setPreferredSize(new Dimension(580, 150));
		overviewPanel.add(userStoryPane, c);

		/**
		 * Blank Panel for formatting purposes
		 */
		JPanel blankPanel0 = new JPanel();
		c.weightx = 0;
		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 2;
		blankPanel0.setMinimumSize(new Dimension(450, 10));
		blankPanel0.setPreferredSize(new Dimension(450, 10));
		overviewPanel.add(blankPanel0, c);
		

		/**
		 * Formatting purposes
		 */
		//cardPanel.add(Box.createRigidArea(new Dimension(75,0)));
		
		//This branch will be run if the default deck is to be used
		boolean useDefaultDeck;
		if (customDeck.size() == 0) {
			//generate fibonachi sequence
			int firstnum = 0;
			int secondnum = 1;
			int currnum;
			deck.add(Integer.toString(secondnum));
			//Default value is 6.
			int Fibcount = 6; //if this is 6, the highest number generated will be 13
			for (int i = 0; i < Fibcount; i++) {
				currnum = firstnum + secondnum;
				deck.add("" + currnum + "");
				firstnum = secondnum;
				secondnum = currnum; 
			}
			useDefaultDeck = true;
		}
		
		//This branch will be run if a custom deck is to be used
		else {
			deck = customDeck; 
			useDefaultDeck = false;
		}
		
		if (useDefaultDeck) {
			deck.add("0?");
		}
		
		/**
		 * Creates a panel for all the cards
		 */
		//cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.X_AXIS));
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 2;
		cardsPanel = new ActiveCardsPanel(deck, this);
		overviewPanel.add(cardsPanel, c);
		this.JToggleButtonList = cardsPanel.getCardButtonArray();
		System.out.println("Estimate Panel output: card button item 3:" + JToggleButtonList.get(2));
		/**
		 * The text area where the user types their estimate
		 */
		estText.setText("Estimate Here");
		estText.setMinimumSize(new Dimension(100, 50));
		estText.setPreferredSize(new Dimension(100, 50));
		c.anchor = GridBagConstraints.CENTER;
		c.weightx = 1;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 6;
		estText.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				estText.setFont(largeFont);
				estText.setText("");
			}
		});
		
		overviewPanel.add(estText, c);
		
		/**
		 * The label for the counter
		 */
		counterLabel = new JLabel("Your current estimate total: " + 0);
		//c.anchor = GridBagConstraints.LINE_END;
		c.insets = new Insets(0, 77, 0, 0);

		c.gridx = 0;
		c.gridwidth = 1;
		c.gridy = 7;
		overviewPanel.add(counterLabel, c);

		if (game.doesUseCards()) {
			estText.setVisible(false);
		} else {
			cardsPanel.setVerifyInputWhenFocusTarget(false);
			for (int i = 0; i < JToggleButtonList.size(); i++) {
				JToggleButtonList.get(i).setVisible(false);
			}
			counter.setVisible(false);
			counterLabel.setVisible(false);
		}
		
		/**
		 * Blank Panel for formatting purposes
		 */
		JPanel blankPanel1 = new JPanel();
		c.weightx = 0;
		c.gridx = 0;
		c.gridy = 8;
		c.gridwidth = 2;
		blankPanel1.setMinimumSize(new Dimension(450, 15));
		blankPanel1.setPreferredSize(new Dimension(450, 15));
		overviewPanel.add(blankPanel1, c);
		
		/**
		 * The submit button for when the user is ready to submit the estimate
		 */
		JButton submitEstimate = new JButton("Submit Estimate");
		submitEstimate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				submitEstimatePressed();
			}
		});
		
		c.weightx = 0;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 8; //changed 8 to 9
	    //c.insets = new Insets(50, 0, 0, 0); ///uncomment to make submit estimate 
		overviewPanel.add(submitEstimate, c);

		/**
		 * label for displaying errors
		 */
		errorField = new JLabel();
		errorField.setMinimumSize(new Dimension(150, 25));
		errorField.setPreferredSize(new Dimension(300, 25));
		errorField.setForeground(Color.RED);
		/*
		 * remove if problems
		 */
		c.weightx = 0;
		c.gridx = 0;
		c.gridy = 10;
		overviewPanel.add(errorField, c);
		
		c.insets= new Insets(0, 0, 0, 0);
		c.gridwidth = 10;
		c.gridheight = 2;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		this.add(overviewPanel, c);
		
		this.setMinimumSize(new Dimension(580, 200));
		this.repaint();
		this.invalidate();
		this.revalidate();

	}
	
///  *
//	 * if no errors are thrown when this is commented, it is deprecated
//	 */
//	public void updateAddToCardSum(int cardValue) {
//		sum += cardValue;
//		counterLabel.setText("Your current estimate total: " + sum);
//	}
	
	/*
	 * if no errors are thrown when this is commented, it is deprecated
	 */
//	/*
//	 * Decrease total sum by amount entered
//	 */
//	public void updateDecToCardSum(int cardValue) {
//		sum -= cardValue;
//		counterLabel.setText("Your current estimate total: " + sum);
//		System.out.println(sum);
//	}
	
	/*
	 * Clicks all the buttons. Used for testing
	 */
	public void doClicks() {
		for (int i = 0; i < JToggleButtonList.size(); i++) {
			JToggleButtonList.get(i).doClick();
		}
	}

	/**
	 * getter for the EstimateText text field
	 * @return estText
	 */
	public JTextField getEstimateText() {
		return estText;
	}
	
	public void updateSum() {
		sum = cardsPanel.getSum();
		counterLabel.setText("Your current estimate total: " + sum);
	}
	
	/*
	 * Returns the sum of all the cards
	 */
	public int getMaxSum() {
		int sum = 0;
		for (int i = 0; i < deck.size(); i++) {
			sum += Integer.parseInt(deck.get(i));
		}
		return sum;
	}
	
	public ArrayList<CardButton> getCardButtonArray() {
		return new ArrayList<CardButton>();
	}
	
	/**
	 * getter for the game field
	 * @return activeGame
	 */
	public Game getGame() {
		return activeGame;
	}
	
	/**
	 * getter for the requirement field
	 * @return activeRequirement
	 */
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
			voteNumber = cardsPanel.getSum();
		}
		else{
			voteNumber = Integer.parseInt(estText.getText());
		}
		Vote vote = new Vote(currentUser, voteNumber);
		getRequirement().addVote(vote);
		
		System.out.println("You voted: " + vote.getVoteNumber());
		
		ViewEventController.getInstance().refreshGameTable();
		ViewEventController.getInstance().refreshGameTree();
		
		getEstimateText().setBorder(defaultBorder);
		errorField.setForeground(Color.BLUE);
		displayError("   Vote Successful!");
	}

	public void displayError(String error) {
		errorField.setText(error);
	}
	
}
