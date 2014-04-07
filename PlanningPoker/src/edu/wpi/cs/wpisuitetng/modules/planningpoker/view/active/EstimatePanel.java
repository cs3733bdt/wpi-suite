package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.JToggleButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sun.java.swing.plaf.motif.MotifBorders.ToggleButtonBorder;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.game.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.requirement.Requirement;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.vote.Vote;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons.CardActionListener;

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
	
	//initializes the sum
	private int sum = 0;
	
	//initializes the card Array
	private ArrayList<String> deck = new ArrayList<String>();
	
	//initializes the JToggleButton
	private ArrayList<JToggleButton> JToggleButtonList = new ArrayList<JToggleButton>();
	
	//initialized array to remember what buttons were pressed if "0?" button is pressed
	private ArrayList<Integer> memoryArray = new ArrayList<Integer>();
	
	/*
	 * If the ArrayList passed in is empty it will use the default deck
	 */
	public EstimatePanel(Game game, Requirement requirement, ArrayList<String> customDeck){ //add a deck of cards as a parameter
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
		//c.weightx = 0.75;
		c.gridx = 0;
		c.gridy = 4;
		//c.ipady = 50;
		userStoryPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		userStoryPane.setPreferredSize(new Dimension(400, 150));
		userStoryPane.setMaximumSize(new Dimension(400, 150));
		overviewPanel.add(userStoryPane, c);

		/**
		 * Blank Panel for formatting purposes
		 */
		JPanel blankPanel0 = new JPanel();
		//blankPanel0.setMinimumSize(new Dimension(310, 10));
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
		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 2;
		cardPanel.setPreferredSize(new Dimension(425, 50));
		
		
		//This branch will be run if the default deck is to be used
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
		}
		//This branch will be run if a custom deck is to be used
		else {
			deck = customDeck; 
		}
		//initializes all the buttons and add them to the panel
		for (int i = 0; i < deck.size(); i++) {
			JToggleButtonList.add(new JToggleButton(deck.get(i)));
			cardPanel.add(JToggleButtonList.get(i));
		}
		JToggleButtonList.add(new JToggleButton("0?")); //"I don't know" button hardcoded
		cardPanel.add(JToggleButtonList.get(deck.size()));
		
		//adds the button to clear all entered estimates
		JButton clearButton = new JButton("Clr");
		cardPanel.add(clearButton); 
		
		//action Listener for the clear button 
		clearButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				memoryArray.clear();
				for (int i = 0; i < (JToggleButtonList.size()); i++){
					if (JToggleButtonList.get(i).isSelected()){
						JToggleButtonList.get(i).doClick();
					}
				}
				sum = 0;
			}
		});	
		
		//creates action listeners for all other buttons
		for (int i = 0;  i < JToggleButtonList.size(); i++ ) { 
			JToggleButtonList.get(i).addActionListener(new CardActionListener(i, deck, JToggleButtonList, this));
		}
		
		overviewPanel.add(cardPanel, c);
		
		/**
		 * The text area where the user types their estimate
		 */
		estText.setText("Estimate Here");
		estText.setPreferredSize(new Dimension(100, 40));
		c.anchor = GridBagConstraints.FIRST_LINE_START;
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

		if (game.doesUseCards()) {
			estText.setVisible(false);
		} else {
			cardPanel.setVerifyInputWhenFocusTarget(false);
			for (int i = 0; i < JToggleButtonList.size(); i++) {
				JToggleButtonList.get(i).setVisible(false);
			}
			clearButton.setVisible(false);
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
	
	//Get the sum of all the cards and return it
	public void addToCardSum(int cardValue) {
		sum += cardValue;
		System.out.println(sum);
	}
	
	public void decToCardSum(int cardValue) {
		sum -= cardValue;
		System.out.println(sum);
	}
	
	public JTextField getEstimateText() {
		return estText;
	}
	
	//This is equivalent to a getter for sum. 
	public int updateSum() {
		sum = 0;
		for (int i = 0; i < deck.size(); i++) {
			if (JToggleButtonList.get(i).isSelected()){
				sum += Integer.parseInt(deck.get(i));   
			}
		}
		System.out.println("Sum:" + sum);
		return sum;
	}
	
	public Game getGame() {
		return activeGame;
	}
	
	public Requirement getRequirement(){
		return activeRequirement;
	}
	
	public void memoryArrayAddElt(int elt) {
		memoryArray.add(elt);
	}
	
	public int memoryArrayGetSize() {
		return memoryArray.size();
	}
	
	public void memoryArrayClear() {
		memoryArray.clear();
	}
	
	public int memoryArrayGetElt(int elt) {
		return memoryArray.get(elt);
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
