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

import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.GameModel;


public class NameJTextFieldTest {

	private NameJTextField textField;
	private NameJTextField blankTextField;
	private NameJTextField blankTextField1;
	private NameJTextField textFieldInt;
	private ErrorLabel errorLabel;
	
	
	@Before
	public void setUp() { // $codepro.audit.disable accessorMethodNamingConvention
		textField = new NameJTextField("Test Field", GameModel.getInstance());
		blankTextField = new NameJTextField(GameModel.getInstance());
		blankTextField1 = new NameJTextField(GameModel.getInstance());
		textFieldInt = new NameJTextField(10, GameModel.getInstance());
		errorLabel = new ErrorLabel();
		
	}
	
	@Test
	public void setTextBlankTextFieldTest() { // $codepro.audit.disable accessorMethodNamingConvention
		blankTextField.setText("Bobby Drop Tables");
		assertEquals("Bobby Drop Tables", blankTextField.getText());
		textFieldInt.setText("aaa");
		assertEquals("aaa", textFieldInt.getText());
	}
	
	@Test
	public void hasChangesTextFieldTest() {
		
		blankTextField.setText("Change text");
		
		assertTrue(blankTextField.hasChanges());
		assertFalse(textField.hasChanges());
		
	}
	
	@Test
	public void validateFieldTest() {
		assertTrue(textField.validateField(errorLabel, false, false));
		assertTrue(textField.validateField(errorLabel, false, true));
		assertTrue(textField.validateField(errorLabel, true, false));
		assertTrue(textField.validateField(errorLabel, true, true));
		assertFalse(blankTextField1.validateField(errorLabel, false, false));
		assertFalse(blankTextField1.validateField(errorLabel, true, false));
		assertFalse(blankTextField1.validateField(errorLabel, false, true));
		assertFalse(blankTextField1.validateField(errorLabel, true, true));
		assertFalse(blankTextField1.validateField(null, true, true));
		
		textField.setText("Bobby Drop TablesBobby Drop TablesBobby Drop Tables"
				+ "Bobby Drop Tables"
				+ "Bobby Drop Tables"
				+ "Bobby Drop TablesBobby Drop TablesBobby Drop TablesBobby Drop Tables"
				+ "Bobby Drop Tables"
				+ "Bobby Drop TablesBobby Drop TablesBobby Drop TablesBobby Drop Tables"
				+ "Bobby Drop TablesBobby Drop Tables"
				+ "Bobby Drop TablesBobby Drop TablesBobby Drop TablesBobby Drop Tables"
				+ "Bobby Drop TablesBobby Drop Tables"
				+ "Bobby Drop TablesBobby Drop TablesBobby Drop TablesBobby Drop Tables"
				+ "Bobby Drop TablesBobby Drop Tables");
		
		assertFalse(textField.validateField(errorLabel, true, true));
		assertFalse(textField.validateField(errorLabel, false, true));
		assertFalse(textField.validateField(errorLabel, true, false));
		assertFalse(textField.validateField(null, true, true));
	}
	
	@Test
	public void testValid(){ // $codepro.audit.disable missingAssertInTestMethod
		NameJTextField testerField = new NameJTextField("Name", GameModel.getInstance());
		//TODO: Add testing for this method
	}
}
