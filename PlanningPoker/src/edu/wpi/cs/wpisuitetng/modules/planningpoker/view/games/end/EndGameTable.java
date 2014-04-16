/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Team Bobby Drop Tables
 *******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.end;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 * TODO: add documentation
 * @author Bobby Drop Tables
 *
 */
@SuppressWarnings("serial")
public class EndGameTable extends JTable
{
	private DefaultTableModel tableModel = null;	
	/**
	 * Sets initial table view
	 * 
	 * @param data	Initial data to fill OverviewTable
	 * @param columnNames	Column headers of OverviewTable
	 */
	public EndGameTable(Object[][] data, String[] columnNames)
	{
		setTableModel(new DefaultTableModel(data, columnNames));
		this.setModel(getTableModel());
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);		
	}
	
	@Override
	public boolean isCellEditable(int row, int column){
		return false;
	}

	public DefaultTableModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(DefaultTableModel tableModel) {
		this.tableModel = tableModel;
	}
}