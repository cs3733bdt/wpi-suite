package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components;

import static org.junit.Assert.*;

import java.awt.event.KeyEvent;

import org.junit.Before;
import org.junit.Test;

public class NumberJTextFieldTest {
	
	private NumberJTextField testerField;
	
	/**
	 * Sends a key pressed event to the number field
	 * As if a user had actually pressed a key
	 * @param tester
	 * @param key
	 */
	private void sendKey(NumberJTextField tester, char key){
		tester.dispatchEvent(new KeyEvent(tester,
			    KeyEvent.KEY_TYPED, System.currentTimeMillis(),
			    0, KeyEvent.VK_UNDEFINED, key));
	}
	
	@Before
	public void setUp() throws Exception {
		testerField = new NumberJTextField(27);
	}
	
	@Test
	public void testGoodKey() {
		char key = '1';
		sendKey(testerField, key);
		assertTrue(testerField.hasChanges());
		assertTrue(testerField.validateField(null, false, false));
	}

	@Test
	public void testBadKey() {
		char key = 'C';
		sendKey(testerField, key);
		assertFalse(testerField.hasChanges());
		assertTrue(testerField.validateField(null, false, false));
	}

}
