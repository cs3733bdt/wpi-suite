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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.MockData;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.MockNetwork;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.abstractmodel.AbstractStorageModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.models.PPRequirement;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.models.PPRequirementEntityManager;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

public class PPRequirementEntityManagerTest {

	MockData db;
	User existingUser;
	Session defaultSession;
	String mockSsid;
	Project testProject;
	Project otherProject;
	Session adminSession;
	PPRequirementEntityManager manager;
	PPRequirement req1;
	PPRequirement req2;
	PPRequirement req3;
	PPRequirement[] reqArray;
	PPRequirement[] allReqs;
	
	private static final Logger logger = Logger.getLogger(PPRequirementEntityManagerTest.class
			.getName());
	@Before
	public void setUp() throws Exception { // $codepro.audit.disable accessorMethodNamingConvention
		// Set up the network
		Network.initNetwork(new MockNetwork());
		Network.getInstance().setDefaultNetworkConfiguration(
				new NetworkConfiguration("http://wpisuitetng"));

		// Set up the data
		User admin = new User("admin", "admin", "1234", "", "fbTest", 27);
		admin.setRole(Role.ADMIN);
		req1 = new PPRequirement();
		req1.setName("Requirement1");
		req1.setDescription("Description1");
		req2 = new PPRequirement();
		req2.setName("Requirement2");
		req2.setDescription("Description2");
		req3 = new PPRequirement();
		req3.setName("Requirement3");
		req3.setDescription("Description3");
		reqArray = new PPRequirement[1];
		reqArray[0] = req1;
		allReqs = new PPRequirement[3];
		allReqs[0] = req1;
		allReqs[1] = req2;
		allReqs[2] = req3;
		
		testProject = new Project("test", "1");
		otherProject = new Project("other", "2");
		mockSsid = "abc123";
		adminSession = new Session(admin, testProject, mockSsid);
		existingUser = new User("steve", "steve", "1234", "", "fbTest", 2);
		defaultSession = new Session(existingUser, testProject, mockSsid);
		db = new MockData(new HashSet<Object>());
		manager = new PPRequirementEntityManager(db);
		
		db.save(existingUser);
		db.save(admin);
		manager.save(defaultSession, req1);
		manager.save(defaultSession, req2);
		manager.save(defaultSession, req3);
		manager.save(adminSession, req1);
		manager.save(adminSession, req2);
		manager.save(adminSession, req3);
	
	}
	
	@Test
	public void retrieveRequirementTest() {
		try {
			PPRequirement[] reqArrayDatabase = manager.
					getEntity(defaultSession, req1.getIdentity().toString());
			assertEquals(reqArray[0], reqArrayDatabase[0]);
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void retrieveAllRequirementsTest() {
		PPRequirement[] reqArrayDatabase = manager.getAll(defaultSession);
		ArrayList<String> identitiesArrayDatabase = new ArrayList<String>();
		ArrayList<String> identitiesArrayTest = new ArrayList<String>();
		
		ArrayList<PPRequirement> reqsArrayListDatabase = 
				new ArrayList<PPRequirement>(Arrays.asList(reqArrayDatabase));
		
		for(PPRequirement r: reqsArrayListDatabase) {
			logger.log(Level.INFO,"Database : " + r.getName());
			logger.log(Level.INFO,"Database : " + r.getIdentity());
			identitiesArrayDatabase.add(r.getIdentity().toString());
		}
		
		ArrayList<PPRequirement> allReqsArrayList = 
				new ArrayList<PPRequirement>(Arrays.asList(allReqs));
		
		for(PPRequirement req: allReqsArrayList) {
			logger.log(Level.INFO,"Test : " + req.getName());
			logger.log(Level.INFO,"Database : " + req.getIdentity());
			identitiesArrayTest.add(req.getIdentity().toString());
		}
		
		assertTrue(identitiesArrayTest.contains(identitiesArrayDatabase.get(0)));
		assertTrue(identitiesArrayTest.contains(identitiesArrayDatabase.get(1)));
		assertTrue(identitiesArrayTest.contains(identitiesArrayDatabase.get(2)));
	}
	
	@Test
	public void updateTest() {
		req1.setName("Cute puppies");
		String req1Json = req1.toJSON();
		
		try {
			assertEquals(req1,manager.update(defaultSession, req1Json));
		} catch (WPISuiteException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void deleteTest() {
		ArrayList<PPRequirement> allReqsArrayList = 
				new ArrayList<PPRequirement>(Arrays.asList(allReqs));
		ArrayList<PPRequirement> reqsArrayListDatabase;

		try {
			manager.deleteEntity(adminSession, req1.getIdentity().toString());
			PPRequirement[] reqArray2 = manager.getAll(adminSession);
			
			reqsArrayListDatabase = new ArrayList<PPRequirement>(Arrays.asList(reqArray2));
			
			assertEquals(2, reqsArrayListDatabase.size());
			assertEquals(3, allReqsArrayList.size());

		} catch (WPISuiteException e) {
			e.printStackTrace();
		}
	}
	
	@Test 
	public void deleteAllTest() {
		ArrayList<PPRequirement> reqsArrayListDatabase;
		
		try {
			manager.deleteAll(adminSession);
			PPRequirement[] reqArray2 = manager.getAll(adminSession);
			
			reqsArrayListDatabase = new ArrayList<PPRequirement>(Arrays.asList(reqArray2));
			
			assertEquals(0, reqsArrayListDatabase.size());

		} catch (WPISuiteException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void countTest() {
		try {
			assertEquals(3, manager.Count());
		} catch (WPISuiteException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void makeEntityTest() {
		PPRequirement req4 = new PPRequirement();
		req4.setName("Requirement4");
		req4.setDescription("Description4");
		String req4Json = req4.toJSON();
		
		try {	
			manager.makeEntity(defaultSession, req4Json);
			
			PPRequirement[] reqArrayDatabase = manager.
					getEntity(defaultSession, req4.getIdentity().toString());
			
			assertEquals(req4.getName(),reqArrayDatabase[0].getName());
			assertEquals(req4.getDescription(),reqArrayDatabase[0].getDescription());
			assertEquals(req4.getIdentity(),reqArrayDatabase[0].getIdentity());
			
			assertEquals(4, manager.Count());
			
			
		} catch (WPISuiteException e) {
			e.printStackTrace();
		}
		
	}

}
