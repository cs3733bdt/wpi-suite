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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.deck.models;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.UnauthorizedException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.MockData;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.MockNetwork;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.decks.creation.ColorEnum;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

public class DeckEntityManagerTest {
	MockData db;
	User existingUser;
	Session defaultSession;
	String mockSsid;
	User paul;
	Session adminSession;
	Project testProject;
	Project otherProject;
	DeckEntityManager manager;
	Deck deck1;
	Deck deck2;
	Deck deck3;
	
	@Before
	public void setUp() throws Exception { // $codepro.audit.disable accessorMethodNamingConvention
		//Set up the network
		Network.initNetwork(new MockNetwork());
		Network.getInstance().setDefaultNetworkConfiguration(
				new NetworkConfiguration("http://wpisuitetng"));

		// Set up the data
		User admin = new User("admin", "admin", "1234", "", "fbTest", 27);
		admin.setRole(Role.ADMIN);
		testProject = new Project("test", "1");
		otherProject = new Project("other", "2");
		mockSsid = "abc123";
		adminSession = new Session(admin, testProject, mockSsid);

		existingUser = new User("steve", "steve", "1234", "", "fbTest", 2);
		
		deck1 = new Deck("Deck1", "Description1", new ArrayList<Integer>(), true, ColorEnum.RED);
		deck2 = new Deck("Deck2", "Description2", new ArrayList<Integer>(), true, ColorEnum.GREEN);
		deck3 = new Deck("Deck3", "Description1", new ArrayList<Integer>(), true, ColorEnum.BLUE);
		
		defaultSession =  new Session(existingUser, testProject,  mockSsid);
		db = new MockData(new HashSet<Object>());
		
		db.save(deck1, testProject);
		db.save(existingUser);
		db.save(deck2, otherProject);
		db.save(admin);
		manager = new DeckEntityManager(db);
	}

	@Test
	public void testMakeEntity() throws WPISuiteException {
		Deck created = manager.makeEntity(defaultSession, deck3.toJSON());
		assertEquals("Deck3", created.getName());
		assertSame(created, db.retrieve(Deck.class, "name", deck3.getName()).get(0));
	}
	
	@Test
	public void tetsGetEntity() throws NotFoundException{
		Deck[] gotten = manager.getEntity(defaultSession, deck1.getIdentity().toString());
		assertSame(deck1, gotten[0]);
	}
	
	@Test(expected = NotFoundException.class)
	public void testGetBadId() throws NotFoundException {
		manager.getEntity(defaultSession, "-1");
		fail("Should have thrown an exception");
	}
	
	@Test
	public void testDelete() throws WPISuiteException {
		UUID id = deck1.getIdentity();
		assertSame(deck1, db.retrieve(Deck.class, "identity", id).get(0));
		assertTrue(manager.deleteEntity(adminSession, id.toString()));
		assertEquals(0, db.retrieve(Deck.class, "identity", id).size());
	}
	
	@Test (expected = NotFoundException.class)
	public void testDeleteMissing() throws WPISuiteException{
		manager.deleteEntity(adminSession, "404");
		fail("The code should have thrown an exception");
	}
	
	@Test (expected = UnauthorizedException.class)
	public void testDeleteNotAllowed() throws WPISuiteException{
		manager.deleteEntity(defaultSession, deck1.getIdentity().toString());
		fail("This should have thrown an Unauthorized Acess Exception");
	}
	
	@Test
	public void testDeleteAll() throws WPISuiteException {
		Deck anotherDeck = new Deck();
		manager.makeEntity(defaultSession, anotherDeck.toJSON());
		assertEquals(2, db.retrieveAll(new Deck(), testProject).size());
		manager.deleteAll(adminSession);
		assertEquals(0, db.retrieveAll(new Deck(), testProject).size());
		//Other Decks should be still around
		assertEquals(1, db.retrieveAll(new Deck(), otherProject).size());
	}
	
	@Test
	public void testDeleteAllWhenEmpty() throws WPISuiteException{
		manager.deleteAll(adminSession);
		manager.deleteAll(adminSession);
		assertEquals(adminSession, null);
		// no exceptions get thrown
	}
	
	@Test
	public void testCount() throws WPISuiteException{
		assertEquals(2, manager.Count());
	}
	
	@Test
	public void testGetAll() throws WPISuiteException{
		manager.save(defaultSession, deck2);
		Deck[] returnedGameList = manager.getAll(defaultSession);
		assertEquals(2, returnedGameList.length);
	}
}
