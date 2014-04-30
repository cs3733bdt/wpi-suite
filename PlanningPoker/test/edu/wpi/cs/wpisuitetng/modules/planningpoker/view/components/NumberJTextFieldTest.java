package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components;

import static org.junit.Assert.*;

import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class NumberJTextFieldTest {
	
	private NumberJTextField testerField;
	
	private ErrorLabel label;
	
	private JFrame frame;
	
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
		testerField.setIErrorView(label);
		testerField.setText("27");
		sendKey(testerField,"21C\n");
		assertEquals("27", testerField.getText());
		assertEquals(NumberJTextField.STRING_NOT_NUMBER, label.getText());
		assertFalse(testerField.hasChanges());
		assertTrue(testerField.validateField(label, true, true));
		assertEquals("", label.getText());
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
		assertEquals(NumberJTextField.STRING_TOO_LONG + maxValue, label.getText());
		assertSame(NumberJTextField.BORDER_ERROR, testerField.getBorder());
		
		
		testerField.setMaxValue(-1); //Reset this back to default
	}
	
	@Test
	public void testNoText(){
		testerField.setText("");
		assertEquals("", testerField.getText());
		assertFalse(testerField.validateField(label, true, true));
		assertEquals(NumberJTextField.STRING_NOT_EMPTY, label.getText());
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
