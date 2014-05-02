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

import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.NumberTextField.NumberFieldCustomError;

public class NumberJTextFieldTest {
	
	private NumberJTextField testerField;
	
	private ErrorLabel label;
	
	private JFrame frame;
	
	private NumberFieldCustomError defaultErrors;
	
	/**
	 * Sends a key pressed event to the number field
	 * As if a user had actually pressed a key
	 * @param tester
	 * @param key
	 */
	private void sendKey(NumberJTextField tester, String text){
		testerField.setTextNoUpdate(text);
		
	}
	
	@Before
	public void setUp() throws Exception {
		testerField = new NumberJTextField(27);
		label = new ErrorLabel();
		frame = new JFrame();
		frame.add(testerField);
		defaultErrors = new NumberFieldCustomError();
	}
	
	@Test
	public void testGoodKey() {
		testerField.setText("27");
		sendKey(testerField, "271\n");
		assertEquals("271", testerField.getText());
		assertTrue(testerField.hasChanges());
		assertTrue(testerField.validateField(label, true, true));
		assertEquals("", label.getText());
	}

	@Test
	public void testBadKey() {
		String initialError = label.getText();
		
		testerField.setIErrorView(label);
		testerField.setText("27");
		sendKey(testerField,"21C\n");
		assertEquals("27", testerField.getText());
		assertEquals(defaultErrors.STRING_NOT_NUMBER, label.getText());
		initialError = label.getText();
		assertFalse(testerField.hasChanges());
		assertTrue(testerField.validateField(label, true, true));
		assertEquals(initialError, label.getText());
		assertSame(NumberJTextField.BORDER_DEFAULT, testerField.getBorder());
	}
	
	@Test
	public void testLargeNumber(){
		//NumberJTextField testerField = new NumberJTextField(900);
		testerField.setText("990");
		int maxValue = 999;
		testerField.setMaxValue(maxValue);
		sendKey(testerField, "9909");
		assertEquals("9909", testerField.getText());
		assertTrue(testerField.hasChanges());
		assertFalse(testerField.validateField(label, true, true));
		assertEquals(defaultErrors.STRING_TOO_HIGH + maxValue, label.getText());
		assertSame(NumberJTextField.BORDER_ERROR, testerField.getBorder());
		
		
		testerField.setMaxValue(-1); //Reset this back to default
	}
	
	@Test
	public void testNoText(){
		testerField.setText("");
		assertEquals("", testerField.getText());
		assertFalse(testerField.validateField(label, true, true));
		assertEquals(defaultErrors.STRING_NOT_EMPTY, label.getText());
		assertSame(NumberJTextField.BORDER_ERROR, testerField.getBorder());
	}
	
	@Test
	public void testValidChange(){
		testerField.setText("27");
		testerField.setMaxValue(999);
		sendKey(testerField, "30");
		assertTrue(testerField.validateField(label, true, true));
		assertEquals("", label.getText());
		assertSame(NumberJTextField.BORDER_DEFAULT, testerField.getBorder());
	}
	

}
