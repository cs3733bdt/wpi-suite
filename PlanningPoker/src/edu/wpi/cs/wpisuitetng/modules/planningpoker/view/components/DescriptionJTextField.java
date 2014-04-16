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
import javax.swing.JTextArea;
import javax.swing.border.Border;

/**
 * TODO: add documentation
 * @author Bobby Drop Tables
 *
 */
public class DescriptionJTextField extends JTextField implements IDataField {
	private final Border defaultBorder = (new JTextField()).getBorder();
	private final Border errorBorder = BorderFactory
			.createLineBorder(Color.RED);
	
	private String startingText;

	public DescriptionJTextField(String text) {
		super(text);
		startingText = text;
	}

	public DescriptionJTextField() {
		startingText = "";
	}
	
	public void displayStandardBorder(){
		setBorder(BorderFactory.createTitledBorder("Description"));
	}
	
	@Override
	public void setText(String text){
		startingText = text;
		super.setText(text);
	}
	

	@Override
	public boolean validateField(IErrorView errorField) {
		boolean isDescriptionValid = false;
		if (getText().length() <= 0) {
			isDescriptionValid = false;
			// getErrorDescription().setText("** Description is REQUIRED");
			setBorder(errorBorder);
			// getErrorDescription().setForeground(Color.RED);
			// TODO add a way to display error descriptions
			errorField.setText("Description is required");
		} else {
			// getErrorDescription().setText("");
			setBorder(defaultBorder);
			isDescriptionValid = true;
		}
		return isDescriptionValid;
	}

	@Override
	public boolean hasChanges() {
		return getText().equals(startingText);
	}
}
