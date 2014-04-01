package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;

/**
 * @author Jeffrey Signore
 * Sets up the panel for the active games screen, which has the list of all active games in which the user is playing.
 * When you click on a game, the bottom section of the screen will display more details about that specific game.
 */
public class ActiveGamesPanel extends JPanel {
	
	/**
	 * Set the gameName equal to the name of the game that was selected from the active games list
	 */
	private JTextArea gameName = new JTextArea();
	
	/**
	 * Set the gameDesc equal to the description of the game that was selected from the active games list
	 */
	private JTextArea gameDesc = new JTextArea();
	
	/**
	 * Set the userStoryDesc equal to the description of the requirement being selected in the table
	 */
	private JTextArea userStoryDesc = new JTextArea();
	
	/**
	 * The estText is needed when the user inputs their estimate, since it must be added to the server
	 */
	private JTextField estText = new JTextField();

	public ActiveGamesPanel(Game game) {
		
		Container rightPanel = new Container();
		rightPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		
		/*
		 * creates and adds the label "Name"
		 */
		JLabel nameLabel = new JLabel("Name: ");
		c.weightx = 0.5;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.gridx = 0;
		c.gridy = 0;
		c.ipadx = 0;
		c.ipady = 0;
		rightPanel.add(nameLabel, c);
		
		
		/*
		 * the game name is determined by whichever game was clicked in the list.
		 */
		gameName.setText(game.getName());
		gameName.setEditable(false);
		c.weightx = 0.75;
		c.gridwidth = 8;
		c.gridx = 1;
		c.gridy = 0;
		rightPanel.add(gameName, c);
		
		
		/*
		 * creates and adds the label "Description"
		 */
		JLabel descLabel = new JLabel("Description: ");
		c.gridwidth = 1;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 1;
		c.ipadx = 0;
		c.ipady = 0;
		rightPanel.add(descLabel, c);
		
		
		/*
		 * the description is determined by whichever game was clicked in the list.
		 */
		gameDesc.setText(game.getDescription());
		gameDesc.setEditable(false);
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.weightx = 0.75;
		c.gridwidth = 8;
		c.gridx = 1;
		c.gridy = 1;
		rightPanel.add(gameDesc, c);
		
		
		/*
		 * Initializes the two columns for the table of requirements.
		 */
		String[] columnNames = {"Requirement", "Description"};
		Object[][] data = {};
		ActiveGamesTable table = new ActiveGamesTable(data, columnNames);
		
		/*
		 * Adds temporary data into the table. 
		 * DELETE THIS ONCE DATA IS SUCCESSFULLY IMPORTED FROM REQUIREMENT MANAGER!!!!!!!!!!!!
		 */
		table.tableModel.addRow(new Object[]{"Requirement1", "Description1"});
		table.tableModel.addRow(new Object[]{"Requirement2", "Description2"});
		table.tableModel.addRow(new Object[]{"Requirement3", "Description3"});
		table.tableModel.addRow(new Object[]{"Requirement4", "Description4"});
		table.tableModel.addRow(new Object[]{"Requirement5", "Description5"});
		table.tableModel.addRow(new Object[]{"Requirement6", "Description6"});
		table.tableModel.addRow(new Object[]{"Requirement7", "Description7"});
		table.tableModel.addRow(new Object[]{"Requirement8", "Description8"});
		table.tableModel.addRow(new Object[]{"Requirement9", "Description9"});
		table.tableModel.addRow(new Object[]{"Requirement10", "Description10"});
		table.tableModel.addRow(new Object[]{"Requirement11", "Description11"});
		table.tableModel.addRow(new Object[]{"Requirement12", "Description12"});
		
		/*
		 * Puts the table within a scroll pane, and adds to the view.
		 */
		JScrollPane tablePanel = new JScrollPane(table);
		c.gridwidth = 8;
		c.gridheight = 1;
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 2;
		c.ipady = -300;
		rightPanel.add(tablePanel, c);
	
		
		/*
		 * Creates and adds the user story text area to the view.
		 */
		userStoryDesc.setText("As a BLANK, I would like to BLANK \nso that BLANK.");
		
		//create a dummy JTextArea
		JTextArea editingArea = new JTextArea();
		// get the current font
		Font f = editingArea.getFont();

		// create a new, smaller font from the current font
		Font newFont = new Font(f.getFontName(), f.getStyle(), f.getSize()+5);
		
		//set the bigger font for userStoryDesc
		Font bigFont = newFont;
		userStoryDesc.setFont(bigFont);
		userStoryDesc.setEditable(false);		
		c.gridwidth = 8;
		c.weightx = 0.75;
		c.gridx = 1;
		c.gridy = 3;
		c.ipady = 50;
		rightPanel.add(userStoryDesc, c);
		
		
		/*
		 * Creates and adds the 1st card to the view.
		 */
		JButton but0 = new JButton("1");
		c.gridwidth = 1;
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 4;
		c.ipadx = 20;
		c.ipady = 75;
		rightPanel.add(but0, c);
		
		/*
		 * Creates and adds the 2nd card to the view.
		 */
		JButton but1 = new JButton("1");
		c.gridx = 2;
		c.gridy = 4;
		c.ipadx = 20;
		c.ipady = 75;
		rightPanel.add(but1, c);
		
		/*
		 * Creates and adds the 3rd card to the view.
		 */
		JButton but2 = new JButton("2");
		c.gridx = 3;
		c.gridy = 4;
		c.ipadx = 20;
		c.ipady = 75;
		rightPanel.add(but2, c);
		
		/*
		 * Creates and adds the 4th card to the view.
		 */
		JButton but3 = new JButton("3");
		c.gridx = 4;
		c.gridy = 4;
		c.ipadx = 20;
		c.ipady = 75;
		rightPanel.add(but3, c);
		
		/*
		 * Creates and adds the 5th card to the view.
		 */
		JButton but4 = new JButton("5");
		c.gridx = 5;
		c.gridy = 4;
		c.ipadx = 20;
		c.ipady = 75;
		rightPanel.add(but4, c);
		
		/*
		 * Creates and adds the 6th card to the view.
		 */
		JButton but5 = new JButton("8");
		c.gridx = 6;
		c.gridy = 4;
		c.ipadx = 20;
		c.ipady = 75;
		rightPanel.add(but5, c);
		
		/*
		 * Creates and adds the 7th card to the view.
		 */
		JButton but6 = new JButton("13");
		c.gridx = 7;
		c.gridy = 4;
		c.ipadx = 20;
		c.ipady = 75;
		rightPanel.add(but6, c);
		
		/*
		 * Creates and adds the 8th card to the view.
		 */
		JButton but7 = new JButton("0?");
		c.gridx = 8;
		c.gridy = 4;
		c.ipadx = 20;
		c.ipady = 75;
		rightPanel.add(but7, c);
		
		/*
		 * Creates and adds the label for the estimate text entry option.
		 */
		JLabel estLabel = new JLabel("Estimate: ");
		c.gridwidth = 2;
		c.gridx = 4;
		c.gridy = 5;
		c.ipadx = 0;
		c.ipady = 50;
		rightPanel.add(estLabel, c);
		
		/*
		 * The text area where the user types their estimate
		 */
		estText.setText("Type Here:");		
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.gridwidth = 1;
		c.gridx = 5;
		c.gridy = 5;
		rightPanel.add(estText, c);
		
		/*
		 * A blank panel for formatting purposes
		 */
		JPanel invisPanel = new JPanel();
		c.gridwidth = 1;
		c.weightx = 0.5;
		c.gridx = 9;
		c.gridy = 4;
		c.ipadx = 0;
		c.ipady = 0;		
		rightPanel.add(invisPanel, c);
		
		/*
		 * The submit button for when the user is ready to submit the estimate
		 */
		JButton submitEstimate = new JButton("Submit Estimate");
		c.gridwidth = 2;
		c.gridx = 4;
		c.gridy = 6;
		rightPanel.add(submitEstimate, c);
		
		this.add(rightPanel);
		
	}
	
	public JTextField getEstimateText() {
		return estText;
	}
	
	public void setGameName(String newGameName) {
		gameName.setText(newGameName);
	}
	
	public void setGameDesc(String newGameDesc) {
		gameDesc.setText(newGameDesc);
	}
	
	public void setUserStoryDesc(String newUserStoryDesc) {
		userStoryDesc.setText(newUserStoryDesc);
	}
}