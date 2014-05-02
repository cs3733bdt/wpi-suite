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
 * creates a textbox which allows the user to enter a description for the game
 * the description cannot be empty when creating a game
 * 
 * @author Bobby Drop Tables
 * 
 */
public class DescriptionJTextArea extends CustomJTextArea implements IDataField {
	private final Border defaultBorder = (new JTextField()).getBorder();
	private final Border errorBorder = BorderFactory
			.createLineBorder(Color.RED);

	private String startingText;

	public DescriptionJTextArea(String text) {
		super(text);
		startingText = text;
	}

	public DescriptionJTextArea() {
		startingText = "";
	}

	public void displayStandardBorder() {
		setBorder(BorderFactory.createTitledBorder("Description"));
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
		boolean isDescriptionValid = false;
		if (getText().length() <= 0) {
			isDescriptionValid = false;
			// TODO add a way to display error descriptions
			if (showLabel) {
				errorField.setText("Description is required");
			}
			if (showBox) {
				this.setBorder(errorBorder);
			}
		} else {
			isDescriptionValid = true;
			this.setBorder(defaultBorder);
		}
		return isDescriptionValid;
	}

	@Override
	public boolean hasChanges() {
		return !getText().equals(startingText);
	}

	public void setStartingText(String newStarting) {
		startingText = newStarting;
	}
}
