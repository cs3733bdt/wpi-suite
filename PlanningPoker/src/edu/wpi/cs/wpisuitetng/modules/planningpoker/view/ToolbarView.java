package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.DefaultToolbarView;

/**
 * Sets up the upper toolbar of the PlanningPoker Window
 * 
 * 
 * @author jonathanleitschuh
 */
public class ToolbarView extends DefaultToolbarView {
	
	/**
	 * Creates and positions the buttons/other information in the tool bar
	 */
	public ToolbarView(){
		this.add(new JLabel("PlanningPoker toolbar placeholder")); // add a label with some placeholder text
	    this.setBorder(BorderFactory.createLineBorder(Color.blue, 2)); // add a border so you can see the panel
	}
}
