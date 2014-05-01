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
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.help;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;



public class EndGameHelpTest {

	EndGameHelp panel = new EndGameHelp();
	
	
	@Test
	public void readyToRemoveTest(){
		assertTrue(panel.readyToRemove());
	}
	
	@Test
	public void getIdentifierIndexTest(){
		assertEquals(1, panel.getIdentifierIndex());
	}
	/*
	@Test
	public void addImageTest(){

	} */
}