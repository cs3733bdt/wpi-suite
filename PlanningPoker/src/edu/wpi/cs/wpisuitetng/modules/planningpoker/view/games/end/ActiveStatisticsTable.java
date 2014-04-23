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

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

/**
 * creates the table of active games
 * @author Bobby Drop Tables
 *
 */
@SuppressWarnings("serial")
public class ActiveStatisticsTable extends JTable
{
	private DefaultTableModel tableModel = null;
	private boolean initialized;
	private boolean isInEditMode;
	private final boolean changedByRefresh = false;	
	private final Border paddingBorder = BorderFactory.createEmptyBorder(0, 4, 0, 0);
	
	/**
	 * Sets initial table view
	 * 
	 * @param data	Initial data to fill OverviewTable
	 * @param columnNames	Column headers of OverviewTable
	 */
	public ActiveStatisticsTable(Object[][] data, String[] columnNames)
	{
		setTableModel(new DefaultTableModel(data, columnNames));
		setModel(getTableModel());
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		
		//TODO ALOT
		
	}
	
	@Override
	public boolean isCellEditable(int row, int column){
		return false;
	}

	/**
	 * getter for the table model of the current game
	 * @return the table model
	 */
	public DefaultTableModel getTableModel() {
		return tableModel;
	}

	/** 
	 * sets the table model specifically for that game
	 * @param tableModel the model to be set
	 */
	public void setTableModel(DefaultTableModel tableModel) {
		this.tableModel = tableModel;
	}
}