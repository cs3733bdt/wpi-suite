/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This class sets the main view when the user goes to the PlanningPoker tab 
 * @author jonathanleitschuh
 *
 */
public class MainView extends JPanel {
	
	public MainView(){
		this.add(new JLabel("PlanningPoker placeholder"));
	    this.setBorder(BorderFactory.createLineBorder(Color.green, 2));
	}

}