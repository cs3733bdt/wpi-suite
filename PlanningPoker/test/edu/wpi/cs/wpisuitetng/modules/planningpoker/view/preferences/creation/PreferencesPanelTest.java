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
	public void setUp() { // $codepro.audit.disable accessorMethodNamingConvention

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
	public void getEmailFieldTest() { // $codepro.audit.disable accessorMethodNamingConvention
		assertEquals("email@email.com", prefPanel.getEmailField().getText());
	}
	
	@Test
	public void isReceivingEmailTest() {
		assertFalse(prefPanel.receivingEmail());
		prefPanel.emailCheckBoxListener();
		assertTrue(prefPanel.getemailOffNotify().isVisible());
		prefPanel.getEmailCheckBox().doClick();
		assertTrue(prefPanel.receivingEmail());
		assertFalse(prefPanel.getemailOffNotify().isVisible());
	}
	
	@Test
	public void getFacebookTest() { // $codepro.audit.disable accessorMethodNamingConvention
		assertEquals("fb.username", prefPanel.getFacebookField().getText());
	}
	
	@Test
	public void isReceivingFacebookTest() {
		assertFalse(prefPanel.receivingFacebook());
		prefPanel.facebookCheckBoxListener();
		prefPanel.getFacebookCheckBox().doClick();
		assertTrue(prefPanel.receivingFacebook());
	}
	
	@Test
	public void getMobileFieldTest() { // $codepro.audit.disable accessorMethodNamingConvention
		assertEquals("5555555555", prefPanel.getMobileField().getText());
	}
	
	@Test
	public void isReceivingMobileTest() {
		assertFalse(prefPanel.receivingMobile());
		prefPanel.mobileCheckBoxListener();
		prefPanel.getMobileCheckBox().doClick();
		assertTrue(prefPanel.receivingMobile());
	}
	
	@Test
	public void updateEmailButtonTest() {
		prefPanel.updateEmailButtonPressed();
		assertEquals("newEmailaddress", currentUser.getEmail());
	}
	
	@Test
	public void updateFacebookButtonTest() {
		prefPanel.updateFacebookButtonPressed();
		assertEquals("newFacebook", currentUser.getFacebookUsername());
	}
	
	@Test
	public void updateMobileButtonTest() {
		prefPanel.updateMobileButtonPressed();
		assertEquals("1234567890", currentUser.getPhoneNumber());
	}
	
	@Test
	public void getCarrierFromIndexTest() { // $codepro.audit.disable accessorMethodNamingConvention
		prefPanel.getCarrierDropDown().setSelectedIndex(0);
		assertEquals("VERIZON", prefPanel.getCarrierFromIndex());
		
		prefPanel.getCarrierDropDown().setSelectedIndex(1);
		assertEquals("ATT", prefPanel.getCarrierFromIndex());
		
		prefPanel.getCarrierDropDown().setSelectedIndex(2);
		assertEquals("TMOBILE", prefPanel.getCarrierFromIndex());
		
		prefPanel.getCarrierDropDown().setSelectedIndex(3);
		assertEquals("SPRINT", prefPanel.getCarrierFromIndex());
		
		prefPanel.getCarrierDropDown().setSelectedIndex(4);
		assertEquals("USCELLULAR", prefPanel.getCarrierFromIndex());
		
		prefPanel.getCarrierDropDown().setSelectedIndex(5);
		assertEquals("--", prefPanel.getCarrierFromIndex());	
	}
}
