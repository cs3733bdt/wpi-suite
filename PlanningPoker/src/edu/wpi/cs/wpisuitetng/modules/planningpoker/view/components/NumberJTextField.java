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

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.NumberTextField.NumberFieldCustomErrorMessage;

/**
 * @author jonathanleitschuh
 * 
 *         The NumberJTextField is designed to prevent the user from entering
 *         anything besides numbers
 * 
 */
public class NumberJTextField extends CustomJTextField implements IDataField {

	/** The default border when there aren't errors in this field */
	public static final Border BORDER_DEFAULT = (new JTextField()).getBorder();

	/** The default border when there are errors in this field */
	public static final Border BORDER_ERROR = BorderFactory
			.createLineBorder(Color.RED);

	private String initialText;
	private IErrorView warningField;
	private Integer maxValue = Integer.MAX_VALUE;
	private Integer minValue = null;
	private NumberFieldCustomErrorMessage errorFields;
	private static final Logger logger = Logger.getLogger(NumberJTextField.class
			.getName());
	/**
	 * The Default constructor for the NumberJTextField
	 */
	public NumberJTextField() {
		initialText = "";
		setup();
	}

	/**
	 * Constructor for the NumberJTextField
	 * 
	 * @param text
	 *            the initial value of this field
	 * @see CustomJTextField#CustomJTextField(String)
	 */
	public NumberJTextField(int text) {
		super(Integer.toString(text));

		setup(text);
	}

	/**
	 * Constructor for the NumberJTextField
	 * 
	 * @param text
	 *            the initial value of this field
	 * @param columns
	 *            the size of this field
	 * @see CustomJTextField#CustomJTextField(String, int)
	 */
	public NumberJTextField(int text, int columns) {
		super(Integer.toString(text), columns);
		setup(text);
	}

	IErrorView getIErrorView() { // The scope of this method is intentionally
									// set to package only
		return warningField;
	}

	/**
	 * Does the initial setup for the NumberJTextField
	 * 
	 * @param text
	 *            the initial text
	 */
	private void setup(int text) {
		initialText = Integer.toString(text);
		setup();
	}

	/**
	 * Sets up the Document for this class
	 */
	private void setup() {
		((AbstractDocument) this.getDocument())
				.setDocumentFilter(new MyDocumentFilter(this));
		errorFields = new NumberFieldCustomErrorMessage();
		enableSelectAllTextOnMouseListener();
	}

	@Override
	public void setText(String text) {
		text = text.replaceAll("\n", "");
		if (text.length() > 1) { // Checks to see if there is more than just one
									// "0" at the begining
			text = text.replaceFirst("^0+(?!$)", ""); // Replaces leading zeros
		}
		initialText = text;
		super.setText(text);
	}

	@Override
	public String getText() {
		String text = super.getText();
		if (text.length() > 1) { // Checks to see if there is more than just one
									// "0" at the begining
			text = text.replaceFirst("^0+(?!$)", ""); // Replaces leading zeros
			super.setText(text); // Set the field to the correct version
		}
		return text;
	}

	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}

	public void setMinValue(int minValue) {
		this.minValue = minValue;
	}

	public void setCustomErrorFields(NumberFieldCustomErrorMessage errorFields) {
		this.errorFields = errorFields;
	}

	public void setIErrorView(IErrorView warningField) {
		this.warningField = warningField;
	}

	public int getValue() {
		return Integer.parseInt(getText());
	}

	@Override
	public boolean validateField(IErrorView warningField, boolean showLabel, boolean showBox) {
		this.warningField = warningField;
		boolean isValid = false;

			if (getText().equals("")) {
				isValid = false;
				showInvalid(errorFields.STRING_NOT_EMPTY, showLabel, showBox);
			} else if (!hasChanges()) { // If this has not changed
				isValid = true;
				showValid(showLabel, showBox);
			} else if (maxValue != null) {
				logger.log(Level.INFO,"maxValue true");
				if (Integer.parseInt(getText()) > (Integer) maxValue) {
					isValid = false;
					showInvalid(errorFields.STRING_TOO_HIGH + maxValue,	showLabel, showBox);
				} else {
					isValid = true;
					showValid(showLabel, showBox);
				}
			} else if (minValue != null) {
				logger.log(Level.INFO,"minValue true");
				if (Integer.parseInt(getText()) < (Integer) minValue) {
					isValid = false;
					showInvalid(errorFields.STRING_TOO_LOW + minValue, showLabel, showBox);
				} else {
					isValid = true;
					showValid(showLabel, showBox);
				}
			} else {
				isValid = true;
				showValid(showLabel, showBox);
			} // Should not need to handle checking to see if there not numbers
				// because this should have already been caught
		return isValid;
	}

	private void showValid(boolean showLabel, boolean showBox) {
		if (showLabel) {
			// NOTHING SHOULD HAPPEN HERE BECAUSE WE DONT WANT TO OVERWRITE A
			// DIFFERENT ERROR
		}
		setBorder(BORDER_DEFAULT);
	}

	private void showInvalid(String text, boolean showLabel, boolean showBox) {
		if (showLabel) {
			warningField.setText(text);
		}
		if (showBox) {
			setBorder(BORDER_ERROR);
		}
	}

	/**
	 * adds a key listener that will update buttons based on this data field
	 */
	public void addKeyListener(final IValidateButtons parent) {
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				parent.updateButtons();
			}
		});
	}

	@Override
	public boolean hasChanges() {
		return !initialText.equals(getText());
	}

	/**
	 * set Text without Update
	 * @param string text
	 */
	public void setTextNoUpdate(String text) {
		text = text.replaceAll("\n", "");
		super.setText(text);

	}

	/**
	 * 
	 * @param string args
	 */
	public static void main(String... args) {
		JFrame frame = new JFrame("Input Integer Example");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		NumberJTextField text = new NumberJTextField(0, 20);

		contentPane.add(text);

		frame.setContentPane(contentPane);
		frame.pack();
		frame.setLocationByPlatform(true);
		frame.setVisible(true);
	}

	public NumberFieldCustomErrorMessage getErrorFields() {
		return errorFields;
	}
}

/**
 * @author jonathanleitschuh This class is a direct copy from:
 *         http://stackoverflow
 *         .com/questions/9477354/how-to-allow-introducing-only
 *         -digits-in-jtextfield
 * 
 *         This document filter prevents anything besides integers entered into
 *         the field
 * 
 */
class MyDocumentFilter extends DocumentFilter {
	private NumberJTextField parent;

	MyDocumentFilter(NumberJTextField parent) {
		this.parent = parent;
	}

	@Override
	public void insertString(DocumentFilter.FilterBypass fp, int offset,
			String string, AttributeSet aset) throws BadLocationException {
		string = string.replaceAll("\n", ""); // Parse out newline character
		int len = string.length();
		boolean isValidInteger = true;

		for (int i = 0; i < len; i++) {
			if (!Character.isDigit(string.charAt(i))) {
				isValidInteger = false;
				break;
			}
		}
		if (isValidInteger) {
			super.insertString(fp, offset, string, aset);
			numberValid();
		} else {
			Toolkit.getDefaultToolkit().beep();
			numberInvalid();
		}
	}

	@Override
	public void replace(DocumentFilter.FilterBypass fp, int offset, int length,
			String string, AttributeSet aset) throws BadLocationException {
		string = string.replaceAll("\n", ""); // Parse out newline character
		int len = string.length();
		boolean isValidInteger = true;

		for (int i = 0; i < len; i++) {
			if (!Character.isDigit(string.charAt(i))) {
				isValidInteger = false;
				break;
			}
		}
		if (isValidInteger) {
			super.replace(fp, offset, length, string, aset);
			numberValid();
		} else {
			Toolkit.getDefaultToolkit().beep();
			numberInvalid();
		}
	}

	private void numberInvalid() {
		if (parent.getIErrorView() != null) {
			parent.getIErrorView().setText(parent.getErrorFields().STRING_NOT_NUMBER);
		}
	}

	private void numberValid() {
		if (parent.getIErrorView() != null) {
			parent.getIErrorView().setText("");
		}
	}
}