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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.decks.creation;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.DescriptionJTextArea;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.NameJTextField;

public class CreateDeckPanel extends JScrollPane {
	
	private NameJTextField nameTextField;
	private DescriptionJTextArea descriptionTextField;
	
	public CreateDeckPanel(){
		
		build();
		
	}
	
	public void build(){
		/* Set up initial container with spring layout */
		Container view = new Container();
		SpringLayout layout = new SpringLayout();
		view.setLayout(layout);
		
		/* Create components */
		JLabel nameLabel = new JLabel("Name * ");
		nameTextField = new NameJTextField(20);
		
		JLabel descriptionLabel = new JLabel("Description");
		descriptionTextField = new DescriptionJTextArea();
		descriptionTextField.setLineWrap(true);
		
		JScrollPane descriptionScroll = new JScrollPane(descriptionTextField);
		descriptionScroll.setPreferredSize(new Dimension(400, 50));		
		
		JLabel colorLabel = new JLabel("Select Card Color * ");
		colorLabel.setFont(makeFont(10));
		
		/* new panel for selecting card color */
		JPanel colorsPanel = new JPanel();
		//GridLayout colorsLayout = new GridLayout(0,5);
		SpringLayout colorsLayout = new SpringLayout();
		colorsPanel.setLayout(colorsLayout);
		
		/* Make components for colorsPanel */
		JLabel color1Label = new JLabel("Color 1");
		color1Label.setFont(makeFont(6));
		JLabel color2Label = new JLabel("Color 2");
		color2Label.setFont(makeFont(6));
		JLabel color3Label = new JLabel("Color 3");
		color3Label.setFont(makeFont(6));
		JLabel color4Label = new JLabel("Color 4");
		color4Label.setFont(makeFont(6));
		JLabel color5Label = new JLabel("Color 5");
		color5Label.setFont(makeFont(6));
		
		/* Add components to colorsPanel */
		colorsPanel.add(color1Label);
		colorsPanel.add(color2Label);
		colorsPanel.add(color3Label);
		colorsPanel.add(color4Label);
		colorsPanel.add(color5Label);
		
		SpringUtilities.makeCompactGrid(colorsPanel, //parent
                1, 5,
                3, 3,  //initX, initY
                3, 3); //xPad, yPad
		
		/* Sets the layout constraints for each component in colorsPanel */
		
		/*colorsLayout.putConstraint(SpringLayout.WEST, colorLabel, 0, SpringLayout.WEST, colorsPanel);
		colorsLayout.putConstraint(SpringLayout.NORTH, colorLabel, 0, SpringLayout.NORTH, colorsPanel);
		
		colorsLayout.putConstraint(SpringLayout.WEST, color1Label, 5, SpringLayout.WEST, colorsPanel);
		colorsLayout.putConstraint(SpringLayout.NORTH, color1Label, 10, SpringLayout.SOUTH, colorLabel);
		
		colorsLayout.putConstraint(SpringLayout.WEST, color2Label, 5, SpringLayout.EAST, color1Label);
		colorsLayout.putConstraint(SpringLayout.NORTH, color2Label, 10, SpringLayout.SOUTH, colorLabel);
		
		colorsLayout.putConstraint(SpringLayout.WEST, color3Label, 5, SpringLayout.EAST, color2Label);
		colorsLayout.putConstraint(SpringLayout.NORTH, color3Label, 10, SpringLayout.SOUTH, colorLabel);

		colorsLayout.putConstraint(SpringLayout.WEST, color4Label, 5, SpringLayout.EAST, color3Label);
		colorsLayout.putConstraint(SpringLayout.NORTH, color4Label, 10, SpringLayout.SOUTH, colorLabel);

		colorsLayout.putConstraint(SpringLayout.WEST, color5Label, 5, SpringLayout.EAST, color4Label);
		colorsLayout.putConstraint(SpringLayout.NORTH, color5Label, 10, SpringLayout.SOUTH, colorLabel);
		*/
		
		/* Add components to the container */
		view.add(nameLabel);
		view.add(nameTextField);
		view.add(descriptionLabel);
		view.add(descriptionScroll);
		view.add(colorLabel);
		view.add(colorsPanel);
		
		/* Sets the layout constraints for each component */
		layout.putConstraint(SpringLayout.WEST, nameLabel, 10, SpringLayout.WEST, view);
		layout.putConstraint(SpringLayout.NORTH, nameLabel, 10, SpringLayout.NORTH, view);
		
		layout.putConstraint(SpringLayout.WEST, nameTextField, 5, SpringLayout.EAST, nameLabel);
		layout.putConstraint(SpringLayout.NORTH, nameTextField, 10, SpringLayout.NORTH, view);
		
		layout.putConstraint(SpringLayout.WEST, descriptionLabel, 20, SpringLayout.EAST, nameTextField);
		layout.putConstraint(SpringLayout.NORTH, descriptionLabel, 10, SpringLayout.NORTH, view);
		
		layout.putConstraint(SpringLayout.WEST, descriptionScroll, 5, SpringLayout.EAST, descriptionLabel);
		layout.putConstraint(SpringLayout.NORTH, descriptionScroll, 10, SpringLayout.NORTH, view);
		layout.putConstraint(SpringLayout.EAST, descriptionScroll, -10, SpringLayout.EAST, view);
		
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, colorLabel, 5, SpringLayout.HORIZONTAL_CENTER, view);
		layout.putConstraint(SpringLayout.NORTH, colorLabel, 75, SpringLayout.SOUTH, nameLabel);
		
		layout.putConstraint(SpringLayout.WEST, colorsPanel, 5, SpringLayout.WEST, view);
		layout.putConstraint(SpringLayout.NORTH, colorsPanel, 10, SpringLayout.SOUTH, colorLabel);
		layout.putConstraint(SpringLayout.SOUTH, colorsPanel, -100, SpringLayout.SOUTH, view);
		layout.putConstraint(SpringLayout.EAST, colorsPanel, -10, SpringLayout.EAST, view);

		setViewportView(view);
	}
	
	public Font makeFont(int size) {
		/**
		 * Creates a new font for use later
		 */
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