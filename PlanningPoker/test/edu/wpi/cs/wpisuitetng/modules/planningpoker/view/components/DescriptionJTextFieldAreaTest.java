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
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DescriptionJTextFieldAreaTest {
	private DescriptionJTextArea textArea;
	private DescriptionJTextArea blankTextArea;
	private ErrorLabel errorLabel;
	
	@Before
	public void setUp() {
		textArea = new DescriptionJTextArea("Test Text Area");
		textArea.displayStandardBorder();
		blankTextArea = new DescriptionJTextArea();
		errorLabel = new ErrorLabel();
	}
	
	@Test
	public void setTextTest() {
		textArea.setText("Bobby Drop Tables");
		assertEquals("Bobby Drop Tables", textArea.getText());
	}
	
	@Test
	public void hasChangesTest() {
		textArea.setText("Change text");
		assertTrue(textArea.hasChanges());
	}
	
	@Test
	public void validateFieldTest() {
		assertTrue(textArea.validateField(errorLabel, false, false));
		assertFalse(blankTextArea.validateField(errorLabel, true, false));
		assertFalse(blankTextArea.validateField(errorLabel, false, true));
		assertFalse(blankTextArea.validateField(errorLabel, true, true));
		
	}
	
}
