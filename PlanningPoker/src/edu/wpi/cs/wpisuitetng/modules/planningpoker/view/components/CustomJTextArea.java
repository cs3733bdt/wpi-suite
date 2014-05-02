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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextArea;

public class CustomJTextArea extends JTextArea implements IDataField {

	/** @see JTextArea#JTextArea() */
	protected CustomJTextArea() {
	}

	/** @see JTextArea#JTextArea(String) */
	protected CustomJTextArea(String text) {
		super(text);
	}

	/**
	 * Mouse listener for number of cards textfield. When the text field is
	 * clicked, it selects all of the text so that the user can easily overwrite
	 * their previous number.
	 */
	public void enableSelectAllTextOnMouseListener() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				selectAll();
			}
		});
	}

	@Override
	public boolean validateField(IErrorView warningField, boolean showLabel,
			boolean showBox) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasChanges() {
		// TODO Auto-generated method stub
		return false;
	}

}
