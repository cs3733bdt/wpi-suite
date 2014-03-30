package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

/**
 * @author Jeffrey Signore
 * Sets up the panel for the active games screen, which has the list of all active games in which the user is playing.
 * When you click on a game, the bottom section of the screen will display more details about that specific game.
 */
public class ActiveGamesPanel extends JSplitPane {
	



	public ActiveGamesPanel() {
		
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		
		JLabel desc = new JLabel("This is the very important and detailed description of the awesomely cool game.");
		
		c.gridx = 1;
		c.gridy = 1;
		
		rightPanel.add(desc, c);
		
		/*JPanel reqpanel = new JPanel();
		
		reqpanel.setPreferredSize(new Dimension(300, 200));
		JLabel req1 = new JLabel("Requirement 1: ___________________________");
		reqpanel.add(req1);
		JLabel req2 = new JLabel("Requirement 2: ___________________________");
		reqpanel.add(req2);
		JLabel req3 = new JLabel("Requirement 3: ___________________________");
		reqpanel.add(req3);
		JLabel req4 = new JLabel("Requirement 4: ___________________________");
		reqpanel.add(req4);
		JLabel req5 = new JLabel("Requirement 5: ___________________________");
		reqpanel.add(req5);
		JLabel req6 = new JLabel("Requirement 6: ___________________________");
		reqpanel.add(req6);*/
		
		ActiveGamesTreePanel filterPanel = new ActiveGamesTreePanel();
		
		String[] columnNames = {"Requirement", "Description"};
		
		Object[][] data = {};
		
		ActiveGamesTable table = new ActiveGamesTable(data, columnNames);
		
		table.tableModel.addRow(new Object[]{"Requirement1", "Description1"});
		table.tableModel.addRow(new Object[]{"Requirement2", "Description2"});
		table.tableModel.addRow(new Object[]{"Requirement3", "Description3"});
		table.tableModel.addRow(new Object[]{"Requirement4", "Description4"});
		table.tableModel.addRow(new Object[]{"Requirement5", "Description5"});
		table.tableModel.addRow(new Object[]{"Requirement6", "Description6"});
		
		JScrollPane tablePanel = new JScrollPane(table);
		
		c.gridx = 1;
		c.gridy = 3;
		c.ipady = 200;
		
		rightPanel.add(tablePanel, c);
		
		JLabel reqdesc = new JLabel("This is the description of the requirement");
		
		c.gridx = 1;
		c.gridy = 4;
		c.ipady = 125;
		
		rightPanel.add(reqdesc, c);
		
		JLabel userstorydesc = new JLabel("This is the description of the user story of that requirement");
		
		c.gridx = 1;
		c.gridy = 5;
		c.ipady = 50;
		//c.anchor = GridBagConstraints.CENTER;
		
		rightPanel.add(userstorydesc, c);
		
		/*String[] columnNames = {"Game ID", "Name", "Number of Requirements", "Game Admin", "Number of Participants", "Status", "Priority"};
		
		Object[][] data = {};
		
		ActiveGamesTable table = new ActiveGamesTable(data, columnNames);
		
		JScrollPane tablePanel = new JScrollPane(table);
		
		table.getColumnModel().getColumn(0).setMinWidth(70); // Game ID
		table.getColumnModel().getColumn(0).setMaxWidth(70); // Game ID
		
		table.getColumnModel().getColumn(1).setMinWidth(50); // Name
	
		table.getColumnModel().getColumn(2).setMinWidth(155); // Number of Requirements
		table.getColumnModel().getColumn(4).setMaxWidth(155); // Number of Requirements
		
		table.getColumnModel().getColumn(3).setMinWidth(90); // Game Admin
		
		table.getColumnModel().getColumn(4).setMinWidth(150); // Number of Participants
		table.getColumnModel().getColumn(4).setMaxWidth(150); // Number of Participants
		
		table.getColumnModel().getColumn(5).setMinWidth(75); // Status
		//table.getColumnModel().getColumn(5).setMaxWidth(75); // Status
		
		table.getColumnModel().getColumn(6).setMinWidth(60); // Priority
		table.getColumnModel().getColumn(6).setMaxWidth(60); // Priority
		
		//	table.getColumnModel().getColumn(0).
		
		/*table.getColumnModel().getColumn(7).setMinWidth(75); // Estimate
		table.getColumnModel().getColumn(7).setMaxWidth(75); // Estimate*/
		
		//rightPanel.add(reqpanel);
		/*rightFrame.add(desc);
		//this.add(desc);
		
		this.setRightComponent(rightFrame);
		this.setDividerLocation(180);*/
		
		
		
	
		
		
		this.setRightComponent(rightPanel);
		this.setLeftComponent(filterPanel);
		this.setDividerLocation(180);
		
	}
	
	
}