package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * @author Jeffrey Signore
 * Sets up the panel for the active games screen, which has the list of all active games in which the user is playing.
 * When you click on a game, the bottom section of the screen will display more details about that specific game.
 */
public class ActiveGamesPanel extends JPanel {
	
	private JTextField estText = new JTextField();
	



	public ActiveGamesPanel() {
		
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
		 * creates the variable for the game name, which is determined by whichever game was clicked in the list.
		 * the variable gameNameVar will be set as the correct game name
		 */
		String gameNameVar = "Game 1";
		JTextArea gameName = new JTextArea("" + gameNameVar);
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
		 * creates the variable for the description, which is determined by whichever game was clicked in the list.
		 * the variable descVar will be set as the correct game description
		 */
		String descVar = "This is the very important and detailed description of the awesomely cool game.";
		JTextArea desc = new JTextArea("" + descVar);
		desc.setEditable(false);
		
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.weightx = 0.75;
		c.gridwidth = 8;
		c.gridx = 1;
		c.gridy = 1;
		
		rightPanel.add(desc, c);
			
		
		/*
		 * Creates the left half of the active games view, which has the list of active games.
		 */
		GamesTreePanel filterPanel = new GamesTreePanel();
		
		
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
		c.weightx = 0.75;
		c.gridx = 1;
		c.gridy = 2;
		c.ipady = 150;
		
		rightPanel.add(tablePanel, c);
		
		
		/*
		 * These strings are to be set dynamically as the three inputs for the user story.
		 */
		String input1 = "_________________TEST_________________";
		String input2 = "_________________TEST_________________";
		String input3 = "_________________TEST_________________";
		
		
		/*
		 * Creates and adds the user story text area to the view.
		 */
		JTextArea userstorydesc = new JTextArea("As a " + input1 + "\nI would like to " + input2 + "\nso that " + input3 + ".");
		userstorydesc.setEditable(false);		
		
		c.gridwidth = 8;
		c.weightx = 0.75;
		c.gridx = 1;
		c.gridy = 3;
		c.ipady = 50;
		
		rightPanel.add(userstorydesc, c);
		
		/*
		
		DELETE THE FOLLOWING IF NO DROP DOWN MENU (COMBOBOX)
		ALTERNATIVE	TO CARDS IS TO BE USED FOR ESTIMATES!!!!!!!!!!!
		
		Integer[] estimateList = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		
		JComboBox<int[]> estimateDropDown = new JComboBox(estimateList);
		
		c.gridwidth = 8;
		c.weightx = 0.25;
		c.gridx = 1;
		c.gridy = 3;
		c.ipady = 0;
		
		rightPanel.add(estimateDropDown, c);*/
		
		
		/*
		 * Creates and adds the 1st card to the view.
		 */
		JButton but0 = new JButton("1");
		
		c.gridwidth = 1;
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 4;
		c.ipadx = 20;
		c.ipady = 100;
		
		rightPanel.add(but0, c);
		
		/*
		 * Creates and adds the 2nd card to the view.
		 */
		JButton but1 = new JButton("1");
		
		c.gridx = 2;
		c.gridy = 4;
		c.ipadx = 20;
		c.ipady = 100;
		
		rightPanel.add(but1, c);
		
		/*
		 * Creates and adds the 3rd card to the view.
		 */
		JButton but2 = new JButton("2");
		
		c.gridx = 3;
		c.gridy = 4;
		c.ipadx = 20;
		c.ipady = 100;
		
		rightPanel.add(but2, c);
		
		/*
		 * Creates and adds the 4th card to the view.
		 */
		JButton but3 = new JButton("3");
		
		c.gridx = 4;
		c.gridy = 4;
		c.ipadx = 20;
		c.ipady = 100;
		
		rightPanel.add(but3, c);
		
		/*
		 * Creates and adds the 5th card to the view.
		 */
		JButton but4 = new JButton("5");
		
		c.gridx = 5;
		c.gridy = 4;
		c.ipadx = 20;
		c.ipady = 100;
		
		rightPanel.add(but4, c);
		
		/*
		 * Creates and adds the 6th card to the view.
		 */
		JButton but5 = new JButton("8");
		
		c.gridx = 6;
		c.gridy = 4;
		c.ipadx = 20;
		c.ipady = 100;
		
		rightPanel.add(but5, c);
		
		/*
		 * Creates and adds the 7th card to the view.
		 */
		JButton but6 = new JButton("13");
		
		c.gridx = 7;
		c.gridy = 4;
		c.ipadx = 20;
		c.ipady = 100;
		
		rightPanel.add(but6, c);
		
		/*
		 * Creates and adds the 8th card to the view.
		 */
		JButton but7 = new JButton("0?");
		
		c.gridx = 8;
		c.gridy = 4;
		c.ipadx = 20;
		c.ipady = 100;
		
		rightPanel.add(but7, c);
		
		/*
		 * Creates and adds the label for the estimate text entry option.
		 */
		JLabel estLabel = new JLabel("Estimate: ");
		
		c.gridx = 4;
		c.gridy = 5;
		c.ipadx = 0;
		c.ipady = 50;
		//c.insets = new Insets(0,25,0,0);
		//c.anchor = GridBagConstraints.EAST;
		
		rightPanel.add(estLabel, c);
		
		/*
		 * Creates and adds the text are for the estimate text entry option.
		 */
		
		
		//JTextField estText = new JTextField();
		
		estText.setText("Type Estimate: ");
		
		//c.insets = new Insets(0,0,0,0);
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.gridwidth = 2;
		c.gridx = 4;
		c.gridy = 5;
		c.ipadx = 0;
		c.ipady = 50;
		c.insets = new Insets(0,60,0,0);
		
		rightPanel.add(estText, c);
		
		/*
		 * A blank panel for formatting purposes.
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
		 * A temporary component to try and help fix the layout.
		 * DELETE WHEN LAYOUT IS FIXED!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		 */
		/*JButton butnull = new JButton("Layout fixer #2");
		
		c.weightx = .01;
		c.gridx = 9;
		c.gridy = 4;
		c.ipadx = 0;
		c.ipady = 0;
		
		rightPanel.add(butnull, c);
		*/
		
		
		/*
		 * Adds the right and left half of the view to the entire split pane view.
		 * Also creates the divider.
		 */
		this.add(rightPanel);
		
	}
	
	public JTextField getEstimateText() {
		return estText;
	}
	
}