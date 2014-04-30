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
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.preferences.creation;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.MockNetwork;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

public class PreferencesPanelTest {

	PreferencesPanel prefPanel;
	User currentUser;
	Session session;

	@Before
	public void setUp() {

		// Mock network
		Network.initNetwork(new MockNetwork());
		Network.getInstance().setDefaultNetworkConfiguration(
				new NetworkConfiguration("http://wpisuitetng"));

		currentUser = new User("doruk", "doruk1", "123", "email@email.com", "fb.username", 12);
		currentUser.setPhoneNumber("5555555555");
		currentUser.setCarrier("AT&T");
		prefPanel = new PreferencesPanel(currentUser);

	}

	@Test
	public void getEmailFieldTest() {
		assertEquals("email@email.com", prefPanel.getEmailField().getText());
	}
	
	@Test
	public void isReceivingEmailTest() {
		assertFalse(prefPanel.receivingEmail());
		prefPanel.getEmailCheckBox().doClick();
		assertTrue(prefPanel.receivingEmail());
	}
	
	@Test
	public void getFacebookTest() {
		assertEquals("fb.username", prefPanel.getFacebookField().getText());
	}
	
	@Test
	public void isReceivingFacebookTest() {
		assertFalse(prefPanel.receivingFacebook());
		prefPanel.getFacebookCheckBox().doClick();
		assertTrue(prefPanel.receivingFacebook());
	}
	
	@Test
	public void getMobileFieldTest() {
		assertEquals("5555555555", prefPanel.getMobileField().getText());
	}
	
	@Test
	public void isReceivingMobileTest() {
		assertFalse(prefPanel.receivingMobile());
		prefPanel.getMobileCheckBox().doClick();
		assertTrue(prefPanel.receivingMobile());
	}
}
