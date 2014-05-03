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

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.models.PPRequirement;

public class RequirementTableTest {

	private List<PPRequirement> reqList;
	private RequirementTableMode activeMode;
	private RequirementTable activeTable;
	private RequirementTable createTable;
	private RequirementTableMode createMode;
	private PPRequirement req1;
	private PPRequirement req2;
	private PPRequirement req3;
	
	@Before
	public void setUp() { // $codepro.audit.disable accessorMethodNamingConvention
		reqList = new ArrayList<PPRequirement>();
		req1 = new PPRequirement("Req1","Desc1");
		req2 = new PPRequirement("Req2","Desc2");
		req3 = new PPRequirement("Req3","Desc3");
		reqList.add(req1);
		reqList.add(req2);
		reqList.add(req3);
		
		activeMode = RequirementTableMode.ACTIVE;
		createMode = RequirementTableMode.CREATE;
	
		activeTable = new RequirementTable(reqList, activeMode);
		createTable = new RequirementTable(reqList, createMode);	
	}
	
	@Test
	public void RequirementTableActiveTest() {
		assertEquals(req1, activeTable.getSelectedReq());
		activeTable.setRowSelectionInterval(0, 1);
		
		List <PPRequirement> req2Array = new ArrayList<PPRequirement>();
		req2Array.add(req2);
		
		assertEquals(req2Array, activeTable.getSelectedReqs());
	}
	
	@Test
	public void RequirementTableCreateTest() {
		assertEquals(req1, createTable.getSelectedReq());
		createTable.setRowSelectionInterval(0, 2);
		
		List <PPRequirement> req2Array = new ArrayList<PPRequirement>();
		req2Array.add(req2);
		
		assertEquals(reqList, createTable.getSelectedReqs());
	}
	
	@Test
	public void RequirementTableEditableTest() {
		assertFalse(activeTable.isCellEditable(0, 0));
		assertFalse(createTable.isCellEditable(0, 0));
	}
}
