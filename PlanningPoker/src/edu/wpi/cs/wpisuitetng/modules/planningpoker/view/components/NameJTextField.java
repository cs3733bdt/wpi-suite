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
 * creates a textbox which allows a user to input a name for a game.
 * the textbox cannot be empty when creating a new game and makes sure to check it
 * @author Bobby Drop Tables
 *
 */
public class NameJTextField extends JTextField implements IDataField {
	private final Border defaultBorder = (new JTextField()).getBorder();
	private final Border errorBorder = BorderFactory
			.createLineBorder(Color.RED);
	private String startingText;

	/**
	 * initializes the textbox with a specific string
	 * @param text the initialized text for the textbox
	 */
	public NameJTextField(String text) {
		super(text);
		startingText = text;
	}

	/**
	 * initializes the textbox to have a specific number of columns
	 * @param size the number of columns the textbox has
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
	
	@Override
	public void setText(String text){
		startingText = text;
		super.setText(text);
	}

	public void displayStandardBorder(){
		setBorder(BorderFactory.createTitledBorder("Name"));
	}

	/**
	 * adds a key listener that will update buttons based on this data field
	 */
	public void addKeyListener(final IValidateButtons parent){
		this.addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent arg0) {	
				parent.updateButtons();
			}
		});
	}
	
	@Override
	public boolean validateField(IErrorView errorField, boolean showLabel, boolean showBox) {
		boolean isNameValid = false;
		
		if (getText().length() >= 100) {
			isNameValid = false;
			if(showLabel){
				if(errorField != null) errorField.setText("Name can be no more than 100 chars.");
			}
			if(showBox){
				this.setBorder(errorBorder);
			}
			
		} else if (getText().length() <= 0) {
			isNameValid = false;
			if(errorField != null){
				if(showLabel){
					errorField.setText("Name is required");
				}
				if(showBox){
					this.setBorder(errorBorder);
				}
			}
		} else {
			// getErrorName().setText("");
			isNameValid = true;
			this.setBorder(defaultBorder);
		}
		return isNameValid;
	}

	@Override
	public boolean hasChanges() {
		return !getText().equals(startingText);
	}

}
