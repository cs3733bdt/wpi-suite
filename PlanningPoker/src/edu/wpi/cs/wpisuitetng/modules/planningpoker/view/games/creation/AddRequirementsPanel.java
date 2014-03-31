package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.AddGameController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.ActiveGamesTable;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons.AddGameButtonPanel;

public class AddRequirementsPanel extends JPanel {
	
	//THIS IS THE REQUIREMENT NAME FIELD THAT WILL BE NEEDED FOR CONTROLLER
	private JTextArea nameArea = new JTextArea();
		
	//THIS IS THE REQUIREMENT DESCRIPTION FIELD THAT WILL BE NEEDED FOR CONTROLLER
	private JTextArea descArea = new JTextArea();
	
	public AddRequirementsPanel(){
		
		super(new GridBagLayout());
		
		Container reqPanel = new Container();
		reqPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		
		//JLabel gameName = new JLabel("Game Name:");
		
		JLabel createReq = new JLabel("Create Requirement");
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.CENTER;
		
		reqPanel.add(createReq, c);
		
		JLabel importReq = new JLabel("Import Requirement");
		c.gridx = 2;
		c.gridy = 0;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.CENTER;
		
		reqPanel.add(importReq, c);
		
		Panel createReqPanel = new Panel();
		createReqPanel.setLayout(new GridBagLayout());
		
		c.gridx = 0;
		c.gridy = 1;
		reqPanel.add(createReqPanel, c);
		
		JLabel reqName = new JLabel("Name:");
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 1;
		c.anchor = GridBagConstraints.CENTER;
		
		createReqPanel.add(reqName, c);
		
		nameArea.setText("test name");
		c.gridx = 1;
		c.gridy = 1;
		
		createReqPanel.add(nameArea, c);
		
		JLabel reqDesc = new JLabel("Description:");
		c.gridx = 0;
		c.gridy = 2;
		
		c.anchor = GridBagConstraints.CENTER;
		
		createReqPanel.add(reqDesc, c);
		
		descArea.setText("As a ______________ \nI would like to _______________________ \nso that ________________________________.");
		c.gridwidth = 2;
		c.gridheight = 3;
		c.gridx = 0;
		c.gridy = 3;
		c.ipady = 50;
		
		createReqPanel.add(descArea, c);
		
		JButton addReqButton = new JButton("Add Requirement");
		c.gridx = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.gridy = 6;
		c.ipady = 0;
		
		createReqPanel.add(addReqButton, c);
		
		reqPanel.add(createReqPanel, c);
		
		Panel importReqPanel = new Panel();
		importReqPanel.setLayout(new GridBagLayout());
		
		c.gridx = 2;
		c.gridy = 1;
		reqPanel.add(importReqPanel, c);
		
		
		/*
		 * Initializes a table's columns and rows and the table
		 */
		String[] columnNames = {"Requirement"};
		
		Object[][] data = {};
		
		ActiveGamesTable table = new ActiveGamesTable(data, columnNames);
		
		/*
		 * Adds temporary data into the table. 
		 * DELETE THIS ONCE DATA IS SUCCESSFULLY IMPORTED FROM REQUIREMENT MANAGER!!!!!!!!!!!!
		 */
		table.tableModel.addRow(new Object[]{"Requirement1"});
		table.tableModel.addRow(new Object[]{"Requirement2"});
		table.tableModel.addRow(new Object[]{"Requirement3"});
		table.tableModel.addRow(new Object[]{"Requirement4"});
		table.tableModel.addRow(new Object[]{"Requirement5"});
		table.tableModel.addRow(new Object[]{"Requirement6"});
		table.tableModel.addRow(new Object[]{"Requirement7"});
		table.tableModel.addRow(new Object[]{"Requirement8"});
		table.tableModel.addRow(new Object[]{"Requirement9"});
		table.tableModel.addRow(new Object[]{"Requirement10"});		
		
		/*
		 * Puts the table within a scroll pane, and adds to the view.
		 */
		JScrollPane tablePanel = new JScrollPane(table);
		
		c.gridwidth = 2;
		//c.weightx = 0.5;
		c.gridx = 2;
		c.gridy = 1;
		c.ipady = 50;
		
		importReqPanel.add(tablePanel, c);
		
		JButton addGameButton = new JButton("Add Game");
		
		c.gridx = 0;
		c.gridwidth = 1;
		c.gridy = 7;
		c.insets = new Insets(100, 0, 0, 0);
		
		reqPanel.add(addGameButton, c);
		//add(new AddGameButtonPanel(this), c);
		
		//Container importReqPanel = new Container();
		//importReqPanel.setLayout(new GridBagLayout());
		
	}
}