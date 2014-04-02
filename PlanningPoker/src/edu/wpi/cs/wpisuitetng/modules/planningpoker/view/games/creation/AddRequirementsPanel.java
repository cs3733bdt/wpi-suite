package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
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
import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirementmodels.Requirement;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.ActiveGamesTable;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons.AddGameButtonPanel;

public class AddRequirementsPanel extends JPanel {
	
	//THIS IS THE REQUIREMENT NAME FIELD THAT WILL BE NEEDED FOR CONTROLLER
	private JTextArea nameArea = new JTextArea();
		
	//THIS IS THE REQUIREMENT DESCRIPTION FIELD THAT WILL BE NEEDED FOR CONTROLLER
	private JTextArea descArea = new JTextArea();
	
	public AddRequirementsPanel(final CreateGamePanel view){
		
		super(new GridBagLayout());
		
		Container reqPanel = new Container();
		reqPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		
		JPanel createReqPanel = new JPanel();
		createReqPanel.setLayout(new GridBagLayout());
		c.insets = new Insets(0, 0, 0 ,0);
		c.ipadx = 0;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 2;
		reqPanel.add(createReqPanel, c);
		
		JPanel blankPanel = new JPanel();
		blankPanel.setMinimumSize(new Dimension(100, 100));
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 2;
		blankPanel.setPreferredSize(new Dimension(310, 200));
		//reqPanel.add(blankPanel, c);
		
		JPanel importReqPanel = new JPanel();
		importReqPanel.setLayout(new GridBagLayout());
		c.gridx = 4;
		c.gridy = 2;
		//reqPanel.add(importReqPanel, c);
		
		JPanel currentReqsPanel = new JPanel();
		currentReqsPanel.setLayout(new GridBagLayout());
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 6;
		//reqPanel.add(currentReqsPanel, c);
		
		JLabel createReq = new JLabel("Create Requirement");
		
		//create a dummy JTextArea
		JTextArea editingArea = new JTextArea();
		// get the current font
		Font f = editingArea.getFont();

		// create a new, smaller font from the current font
		Font newFont = new Font(f.getFontName(), f.getStyle(), f.getSize()+8);
				
		//set the bigger font for userStoryDesc
		Font bigFont = newFont;
		createReq.setFont(bigFont);
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.CENTER;
		createReqPanel.add(createReq, c);
		
		JLabel reqName = new JLabel("Name:");
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 1;
		c.anchor = GridBagConstraints.CENTER;
		createReqPanel.add(reqName, c);
		
		nameArea.setText("Requirement Name");
		c.gridx = 1;
		c.gridy = 1;
		nameArea.setPreferredSize(new Dimension(75, 25));
		createReqPanel.add(nameArea, c);
		
		JLabel reqDesc = new JLabel("Description:");
		c.gridx = 0;
		c.gridy = 2;
		c.anchor = GridBagConstraints.CENTER;
		createReqPanel.add(reqDesc, c);
		
		descArea.setText("Requirement Description");
		descArea.setLineWrap(true);
		c.gridwidth = 2;
		c.gridheight = 3;
		c.gridx = 0;
		c.gridy = 3;
		descArea.setPreferredSize(new Dimension(500,200));
		createReqPanel.add(descArea, c);
		
		JButton addReqButton = new JButton("Add Requirement");
		addReqButton.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				 view.addRequirement(new Requirement(nameArea.getText(), descArea.getText()));
				 nameArea.setText("");
				 descArea.setText("");
			 }
		});
		
		c.insets = new Insets(0, 175, 0, 175);
		c.gridx = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.gridy = 6;
		c.ipady = 0;
		addReqButton.setPreferredSize(new Dimension(150, 25));
		createReqPanel.add(addReqButton, c);
		
		JLabel importReq = new JLabel("Import Requirement");
		c.insets = new Insets(0, 0, 0, 0);
		c.gridwidth = 2;
		c.gridx = 2;
		c.gridy = 0;
		c.anchor = GridBagConstraints.CENTER;
		importReqPanel.add(importReq, c);
		
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
		tablePanel.setMinimumSize(new Dimension(200, 110));
		tablePanel.setMaximumSize(new Dimension(200, 110));
		c.gridwidth = 2;
		c.gridx = 2;
		c.gridy = 1;
		importReqPanel.add(tablePanel, c);
		
		JButton importReqButton = new JButton("Import Requirement");
		c.gridx = 2;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.gridy = 6;
		c.ipady = 0;
		c.ipadx = 0;
		importReqPanel.add(importReqButton, c);
		
		JLabel currentReqs = new JLabel("Current Requirements");
		c.gridx = 2;
		c.gridy = 0;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;
		currentReqsPanel.add(currentReqs, c);
		
		
		String[] columnNames2 = {"Requirement", "Description"};
		Object[][] data2 = {};
		ActiveGamesTable table2 = new ActiveGamesTable(data2, columnNames2);
		/*
		 * Adds temporary data into the table. 
		 * DELETE THIS ONCE DATA IS SUCCESSFULLY IMPORTED FROM REQUIREMENT MANAGER!!!!!!!!!!!!
		 */
		table2.tableModel.addRow(new Object[]{"Requirement1", "Description1"});
		table2.tableModel.addRow(new Object[]{"Requirement2", "Description2"});
		table2.tableModel.addRow(new Object[]{"Requirement3", "Description3"});
		table2.tableModel.addRow(new Object[]{"Requirement4", "Description4"});
		table2.tableModel.addRow(new Object[]{"Requirement5", "Description5"});
		table2.tableModel.addRow(new Object[]{"Requirement6", "Description6"});
		table2.tableModel.addRow(new Object[]{"Requirement7", "Description7"});
		table2.tableModel.addRow(new Object[]{"Requirement8", "Description8"});
		table2.tableModel.addRow(new Object[]{"Requirement9", "Description9"});
		table2.tableModel.addRow(new Object[]{"Requirement10", "Description10"});		
		/*
		 * Puts the table within a scroll pane, and adds to the view.
		 */
		JScrollPane tablePanel2 = new JScrollPane(table2);
		tablePanel2.setMinimumSize(new Dimension(400, 100));
		c.gridwidth = 8;
		c.gridx = 0;
		c.gridy = 1;
		currentReqsPanel.add(tablePanel2, c);
		
		JButton removeReqButton = new JButton("Remove Selected Requirement");
		c.gridx = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridy = 2;
		c.ipady = 0;
		c.ipadx = 0;
		currentReqsPanel.add(removeReqButton, c);
		
		JButton removeAllReqButton = new JButton("Remove All Requirements");
		c.gridx = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridy = 2;
		currentReqsPanel.add(removeAllReqButton, c);
	
		JPanel blankPanel3 = new JPanel();
		blankPanel3.setMinimumSize(new Dimension(400, 25));
		c.gridx = 0;
		c.gridy = 8;
		c.gridwidth = 8;
		reqPanel.add(blankPanel3, c);
		
		this.add(reqPanel);

	}
}