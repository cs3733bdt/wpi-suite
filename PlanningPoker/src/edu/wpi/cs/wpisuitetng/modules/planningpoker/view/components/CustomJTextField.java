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

import javax.swing.JTextField;

public abstract class CustomJTextField extends JTextField implements IDataField{
	/** @see JTextField#JTextField() */
	protected CustomJTextField(){};
	/** @see JTextField#JTextField(String) */
	protected CustomJTextField(String string){super(string);}
	/** @see JTextField#JTextField(String, int) */
	protected CustomJTextField(String string, int columns) {super(string, columns);}
	/** @see JTextField#JTextField(int) */
	public CustomJTextField(int size) {super(size);}
	
	
	/**
	 * Mouse listener for number of cards textfield. When the text field is
	 * clicked, it selects all of the text so that the user can easily overwrite
	 * their previous number.
	 */
	public void enableSelectAllTextOnMouseListener(){
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				selectAll();
			}
		});
	}

}
