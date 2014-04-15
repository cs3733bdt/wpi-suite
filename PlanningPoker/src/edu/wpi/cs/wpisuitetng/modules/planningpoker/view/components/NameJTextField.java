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

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 * TODO: add documentation
 * @author Bobby Drop Tables
 *
 */
public class NameJTextField extends JTextField implements IDataField {
	private final Border defaultBorder = (new JTextField()).getBorder();
	private final Border errorBorder = BorderFactory
			.createLineBorder(Color.RED);
	

	public NameJTextField(String text) {
		super(text);
	}

	public NameJTextField(int size) {
		super(size);
	}

	public NameJTextField() {
	}

	@Override
	public boolean validateField(IErrorView errorField) {
		boolean isNameValid = false;
		
		if (this.getText().length() >= 100) {
			isNameValid = false;
			this.setBorder(errorBorder);
			// getErrorName().setForeground(Color.RED);
			if(errorField != null) errorField.setText("Name can be no more than 100 chars.");
		} else if (this.getText().length() <= 0) {
			isNameValid = false;
			if (errorField != null) {
				// getErrorName().setText("** Name is REQUIRED");
				this.setBorder(errorBorder);
				// getErrorName().setForeground(Color.RED);
			}

			if(errorField != null) errorField.setText("Name is required");
		} else {
			// getErrorName().setText("");
			this.setBorder(defaultBorder);
			isNameValid = true;
		}
		return isNameValid;
	}

}
