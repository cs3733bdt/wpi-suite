package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.end;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class EndGameTable extends JTable
{
	public DefaultTableModel tableModel = null;	
	/**
	 * Sets initial table view
	 * 
	 * @param data	Initial data to fill OverviewTable
	 * @param columnNames	Column headers of OverviewTable
	 */
	public EndGameTable(Object[][] data, String[] columnNames)
	{
		this.tableModel = new DefaultTableModel(data, columnNames);
		this.setModel(tableModel);
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);		
	}
	
	@Override
	public boolean isCellEditable(int row, int column){
		return false;
	}
}