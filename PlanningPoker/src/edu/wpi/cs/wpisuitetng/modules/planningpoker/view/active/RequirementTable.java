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
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirement.models.Requirement;

public class RequirementTable extends JTable{
	
	List<Requirement> reqList;
	RequirementTableMode mode;
	String[] columnNames;
	
	private DefaultTableModel tableModel = null;
	private boolean initialized;
	private boolean isInEditMode;
	private final boolean changedByRefresh = false;	
	private final Border paddingBorder = BorderFactory.createEmptyBorder(0, 4, 0, 0);
	private final Border defaultBorder = (new JTextField()).getBorder();
	Object[][] data = {};
	
	public RequirementTable(List<Requirement> reqList, RequirementTableMode mode) {
		this.reqList = reqList;
		this.mode = mode;
		build();
	}
	
	/**
	 * Build one of three different tables depending on the mode 
	 * parameter passed to RequirementTable.
	 */
	
	public void build() {
		
		switch(mode) {
		case ACTIVE:
			columnNames = new String[4];
			columnNames[0] = "Requirement";
			columnNames[1] = "Description";
			columnNames[2] = "My Estimate";
			columnNames[3] = "Complete";
			
			setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			setTableModel(new DefaultTableModel(data, columnNames));
			setModel(getTableModel());
					
			setBorder(defaultBorder);
			
			getColumnModel().getColumn(0).setMinWidth(100);
			getColumnModel().getColumn(0).setMaxWidth(400);
			getColumnModel().getColumn(1).setMinWidth(100);
			getColumnModel().getColumn(1).setMaxWidth(800);
			getColumnModel().getColumn(2).setMinWidth(100);
			getColumnModel().getColumn(2).setMaxWidth(150);
			getColumnModel().getColumn(3).setMinWidth(100);
			getColumnModel().getColumn(3).setMaxWidth(150);
			
			for(Requirement r: reqList) {
				getTableModel().addRow(new Object[]{r.getName(), r.getDescription(),
						r.userVote(), r.displayComplete()});
			}
			
			break;
		case ENDED:
			
			// fall through to the next case
			
		case CREATE:
			
			columnNames = new String[2];
			columnNames[0] = "Requirement";
			columnNames[1] = "Description";
			
			setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // This might need to be changed
			setTableModel(new DefaultTableModel(data, columnNames));
			setModel(getTableModel());
			
			getColumnModel().getColumn(0).setMinWidth(100);
			getColumnModel().getColumn(0).setMaxWidth(400);
			getColumnModel().getColumn(1).setMinWidth(100);
			getColumnModel().getColumn(1).setMaxWidth(800);
			
			for(int i=0; i < reqList.size(); i++) {
				getTableModel().addRow(new Object[]{reqList.get(i).getName(), reqList.get(i).getDescription()});
			}
			
			break;
		}
		
		setRowSelectionInterval(0,0);
		
	}
	
	
	
	//table.getTableModel().addRow(new Object[]{"Requirement1", "Description1"});
	
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
	
	/** 
	 * 
	 * @return multiple requirements that are selected by the user
	 */
	public List<Requirement> getSelectedReqs() {
		
		List<Requirement> listOfReqs = new ArrayList<Requirement>();
		
		for(Integer i: this.getSelectedRows()) {
			listOfReqs.add(reqList.get(i));
		}
		return listOfReqs;
	}
	
	public Requirement getSelectedReq() throws IndexOutOfBoundsException{
		
		return reqList.get(getSelectedRow());
		
	}

}
