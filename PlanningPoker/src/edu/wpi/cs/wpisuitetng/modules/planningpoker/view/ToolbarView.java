package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.DefaultToolbarView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons.CreateGameButtonPanel;

/**
 * Sets up the upper toolbar of the PlanningPoker Window
 * 
 * 
 * @author jonathanleitschuh
 */
public class ToolbarView extends DefaultToolbarView {
	
	public CreateGameButtonPanel createGameButton = new CreateGameButtonPanel();
	
	/**
	 * Creates and positions the buttons/other information in the tool bar
	 */
	public ToolbarView(boolean visible){
		this.addGroup(createGameButton);
	    this.setBorder(BorderFactory.createLineBorder(Color.blue, 2)); // add a border so you can see the panel
	    this.repaint();
	    
	}
}