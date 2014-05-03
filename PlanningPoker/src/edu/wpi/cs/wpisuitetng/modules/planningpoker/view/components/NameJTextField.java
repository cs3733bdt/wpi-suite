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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 * creates a textbox which allows a user to input a name for a game. the textbox
 * cannot be empty when creating a new game and makes sure to check it
 * 
 * @author Bobby Drop Tables
 * 
 */
public class NameJTextField extends CustomJTextField implements IDataField {
	// These can be accessed externally in order to allow the user to debug and
	// check for errors easier
	/**
	 * The text error placed on an IErrorField when the name is longer than 100
	 * characters
	 */
	public static final String STRING_ERROR_NAMELONG = "Name can be no more than 100 chars.";

	/** The text error placed on an IErrorField when the name empty */
	public static final String STRING_ERROR_BLANK = "Highlighted fields are required";

	/** The */
	public static final Border DEFAULTBORDER = (new JTextField()).getBorder();
	public static final Border ERRORBORDER = BorderFactory
			.createLineBorder(Color.RED);

	private String startingText;

	/**
	 * initializes the textbox with a specific string
	 * 
	 * @param text
	 *            the initialized text for the textbox
	 */
	public NameJTextField(String text) {
		super(text);
		startingText = text;
		enableSelectAllTextOnMouseListener();
	}

	/**
	 * initializes the textbox to have a specific number of columns
	 * 
	 * @param size
	 *            the number of columns the textbox has
	 */
	public NameJTextField(int size) {
		super(size);
		startingText = "";
	}

	/**
	 * initializes a textbox with no initial string or columns
	 */
	public NameJTextField() {
		startingText = "";
	}

	/**
	 * adds a key listener that will update buttons based on this data field
	 */
	public void addKeyListener(final IValidateButtons parent) {
		this.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent arg0) {
				parent.updateButtons();
			}
		});
	}

	@Override
	public boolean validateField(IErrorView errorField, boolean showLabel,
			boolean showBox) {
		boolean isNameValid = false;

		if (getText().length() >= 100) {
			isNameValid = false;
			if (showLabel) {
				if (errorField != null) {
					errorField.setText(STRING_ERROR_NAMELONG);
				}
			}
			if (showBox) {
				this.setBorder(ERRORBORDER);
			}

		} else if (getText().length() <= 0) {
			isNameValid = false;
			if (errorField != null) {
				if (showLabel) {
					errorField.setText(STRING_ERROR_BLANK);
				}
				if (showBox) {
					this.setBorder(ERRORBORDER);
				}
			}
		} else {
			//errorField.setText("");
			isNameValid = true;
			this.setBorder(DEFAULTBORDER);
		}
		return isNameValid;
	}

	@Override
	public boolean hasChanges() {
		return !getText().equals(startingText);
	}

	public void setStartingText(String newStarting) {
		startingText = newStarting;
	}

}
