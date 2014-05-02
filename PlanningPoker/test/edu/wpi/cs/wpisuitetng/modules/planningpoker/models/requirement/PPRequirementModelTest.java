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
package edu.wpi.cs.wpisuitetng.modules.planningpoker.models.requirement;

import static org.junit.Assert.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.MockNetwork;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.abstractmodel.AbstractStorageModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.models.PPRequirement;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.models.PPRequirementModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

public class PPRequirementModelTest {
	private static Logger logger = Logger.getLogger(AbstractStorageModel.class.getName());
	PPRequirementModel model;
	PPRequirement req1, req2, req3, req4, req5;
	PPRequirement[] reqsArray;

	@Before
	public void setUp() {
		// Set up the network
				Network.initNetwork(new MockNetwork());
				Network.getInstance().setDefaultNetworkConfiguration(
						new NetworkConfiguration("http://wpisuitetng"));
		// Set up the contents		
		req1 = new PPRequirement("Requirement1", "Description1");
		req1.setId(1);
		req2 = new PPRequirement("Requirement2", "Description2");
		req2.setId(2);
		req3 = new PPRequirement("Requirement3", "Description3");
		req4 = new PPRequirement("Requirement4", "Description4");
		req5 = new PPRequirement("Requirement5", "Description5");
		reqsArray = new PPRequirement[5];
		reqsArray[0] = req1;
		reqsArray[1] = req2;
		reqsArray[2] = req3;
		reqsArray[3] = req4;
		reqsArray[4] = req5;
		
		model = PPRequirementModel.getInstance();
		model.emptyModel();
		model.addRequirement(req1);
		model.addRequirement(req2);
	}
	
	@Test
	public void emptyModelTest() {
		model.emptyModel();
		assertEquals(0, model.getSize());
	}
	
	@Test
	public void getRequirementTest() {
		assertEquals(req1, model.getRequirement(1));
		assertEquals(req2.getName(), model.getRequirement(2).getName());
		assertEquals(req1, model.getRequirement("Requirement1", "Description1"));
		assertEquals(req2, model.getRequirement("Requirement2", "Descripntion2"));
	}
	
	@Test
	public void removeRequirementTest(){
		model.emptyModel();
		model.addRequirement(req2);
		model.addRequirement(req1);
		model.removeRequirement(2);
		assertEquals(1, model.getSize());
		model.removeRequirement(1);
		assertEquals(0, model.getSize());
		try {
		model.getRequirement(1);
		} catch (NullPointerException e) {
			assertEquals(0, model.getSize());
			logger.log(Level.FINEST, "An exception is expected since the requirement does not exist.", e);
		}
	}
	
	@Test
	public void getElementAtTest() {
		model.addRequirement(req1);
		assertEquals(req1, model.getElementAt(2));
	}
	
	
	
	@Test
	public void addRequirementsTest() {
		model.emptyModel();
		assertEquals(0, model.getSize());
		model.addRequirements(reqsArray);
		assertEquals(5,model.getSize());
	}
	
	@Test
	public void containsTest() {
		assertTrue(model.contains(1));
		assertTrue(model.contains(2));
		assertFalse(model.contains(3));
		assertFalse(model.contains(4));
	}
	
	@Test
	public void getRequirementsTest() {
		assertTrue((model.getRequirements()).contains(req1));
		assertTrue((model.getRequirements()).contains(req2));
		assertFalse((model.getRequirements()).contains(req3));
		assertFalse((model.getRequirements()).contains(req4));
		
	}
}
