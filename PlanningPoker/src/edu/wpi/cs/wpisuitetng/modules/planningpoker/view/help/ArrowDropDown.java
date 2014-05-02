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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.help;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;

/**
 * This class's constructor creates an JLabel containing a Unicode arrow to be used in various Help Panels. 
 * This class supports a method on the Label called <code>addComponentToGroup(JComponent comp)</code> which adds
 * the drop down functionality to <code>comp</code>.
 * @author Justin
 *
 */
public class ArrowDropDown extends JLabel {
	
  /**
   * List of components associated with this ArrowDropDown object	
   */
  ArrayList<JComponent> associatedComponents;
  /**
   * State variable for the toggle
   */
  boolean isExpanded;
  
  static char expandedArrow = 9660;
  static String expandedArrowString = Character.toString(expandedArrow);
  static char hiddenArrow = 9654;
  static String hiddenArrowString = Character.toString(hiddenArrow);
	
  static Font hiddenArrowFont = makeFont(11);
  static Font expandedArrowFont = makeFont(3);
  
  public ArrowDropDown() {
	  super(); //get the base functionality of a normal JLabel 
	  setText(expandedArrowString);
	  setFont(expandedArrowFont);
	  associatedComponents = new ArrayList<JComponent>();
	  isExpanded = false;
	  addMouseListenerToArrow();
  }
  
  /**
   * This method adds a component to the group of components associated with this arrow label. 
   * This should be run on the components that you want to disappear when the arrow is toggled
   * @param comp is the component to be added to the group
   */
  public void addComponentToGroup(JComponent comp) {
	  associatedComponents.add(comp);
  }
  
  /**
   * Adds the mouse listener to the arrow label. 
   * This method is not designed for use other than in a constructor for a JLabel.
   */
  public void addMouseListenerToArrow() {
	  this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent arg0) {
				switchComponentState();
			}
		});
  }
  
  	/**
  	 * called to swap the visibility state of the components
     * also updates isExpanded boolean
  	 */
	private void switchComponentState() {
		System.out.println("switching component state from:" + isExpanded + " (true means it should have been expanded prior to clicking)");
		
		for (JComponent c: associatedComponents) {
			c.setVisible(isExpanded); //this flips the state of the component visibilities
     	}
		isExpanded = !isExpanded;
		
		changeArrow();
	}
	
	/**
	 * Changes the icon for the arrow. 
	 * This method is called subsequently to switchComponentState()
	 */
	private void changeArrow() {
		if (getText().equals(hiddenArrowString)) {
			setText(expandedArrowString);
			setFont(expandedArrowFont);
		}
		else {
			setText(hiddenArrowString);
			setFont(hiddenArrowFont);
		}
		
	}
	
	/**
	 * Creates a font to be used for later
	 * @param size The size of the font
	 * @return font to be used 
	 */
	public static Font makeFont(int size) {
		//create a dummy JTextArea
		JTextArea editingArea = new JTextArea();
		// get the current font
		Font f = editingArea.getFont();
		// create a new, larger font from the current font
		Font newFont = new Font(f.getFontName(), f.getStyle(), f.getSize()+size);		
		//set the bigger font for userStoryDesc
		Font bigFont = newFont;
		return bigFont;
	}
  
}
