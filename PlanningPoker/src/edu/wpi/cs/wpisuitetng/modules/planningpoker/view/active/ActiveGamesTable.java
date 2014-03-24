package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

public class ActiveGamesTable extends JTable
{
	private DefaultTableModel tableModel = null;
	private boolean initialized;
	private boolean isInEditMode;
	private boolean changedByRefresh = false;	
	private Border paddingBorder = BorderFactory.createEmptyBorder(0, 4, 0, 0);
	
	/**
	 * Sets initial table view
	 * 
	 * @param data	Initial data to fill OverviewTable
	 * @param columnNames	Column headers of OverviewTable
	 */
	public ActiveGamesTable(Object[][] data, String[] columnNames)
	{
		this.tableModel = new DefaultTableModel(data, columnNames);
		this.setModel(tableModel);
		
		//TODO ALOT
		
	}
}