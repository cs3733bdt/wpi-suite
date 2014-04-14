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