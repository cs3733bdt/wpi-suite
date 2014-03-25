package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;

/*
 * @author Jeffrey Signore
 * Sets up the panel for the active games screen, which has the list of all active games in which the user is playing.
 * When you click on a game, the bottom section of the screen will display more details about that specfic game.
 */
public class ActiveGamesPanel extends JSplitPane {
	
	public ActiveGamesPanel() {
		
		ActiveGamesTreePanel filterPanel = new ActiveGamesTreePanel();
		
		String[] columnNames = {"Game ID", "Name", "Number of Requirements", "Game Admin", "Number of Participants", "Status", "Priority"};
	
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
		
		/*table.getColumnModel().getColumn(7).setMinWidth(75); // Estimate
		table.getColumnModel().getColumn(7).setMaxWidth(75); // Estimate*/
		
		
		this.setLeftComponent(filterPanel);
		this.setRightComponent(tablePanel);
		this.setDividerLocation(180);
	}
}