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
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.models.PPRequirement;

/**
 * table that displays the end game results
 * @author Bobby Drop Tables
 *
 */
@SuppressWarnings("serial")
public class EndGameTable extends JTable
{
	private String[] columnNames;
	private DefaultTableModel tableModel = null;
	private EndGameTableMode mode;
	private final Border defaultBorder = (new JTextField()).getBorder();
	Object[][] data = {};
	
	public EndGameTable(EndGameTableMode mode)
	{
		this.mode = mode;
		build();
	}
	
	/**
	 * Build one of three different tables depending on the mode 
	 * parameter passed to EndGameTable.
	 */
	private void build() {
		
		switch(mode) {
		case VOTE:
			columnNames = new String[2];
			columnNames[0] = "User Name";
			columnNames[1] = "Estimate";
			
			setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			setTableModel(new DefaultTableModel(data, columnNames));
			setModel(getTableModel());
					
			setBorder(defaultBorder);
			
			break;
		case STATISTIC:
			columnNames = new String[6];
			columnNames[0] = "Mean";
			columnNames[1] = "Standard Deviation";
			columnNames[2] = "Median";
			columnNames[3] = "Max";
			columnNames[4] = "Min";
			columnNames[5] = "Num Votes";
			
			setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			setTableModel(new DefaultTableModel(data, columnNames));
			setModel(getTableModel());
					
			setBorder(defaultBorder);
			
		case GAME:
			
			/* There was originally a table class made specifically for a table for the end game panel
			 * but it was never used. This space is reserved in case it's needed in the future.
			 */
			
			break;
		}
		
		if(getRowCount() > 0) {
			setRowSelectionInterval(0,0);
		}
	}
	
	@Override
	public boolean isCellEditable(int row, int column){
		return false;
	}

	/**
	 * getter for the table model
	 * @return the table model
	 */
	public DefaultTableModel getTableModel() {
		return tableModel;
	}

	/**
	 * setter for the table model
	 * @param tableModel the table model that is being set
	 */
	public void setTableModel(DefaultTableModel tableModel) {
		this.tableModel = tableModel;
	}
}