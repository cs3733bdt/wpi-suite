/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.overview.OverviewPanel;

/**
 * This class sets the main view when the user goes to the PlanningPoker tab 
 * @author jonathanleitschuh
 *
 */
public class MainView extends JTabbedPane {
	
	
	private OverviewPanel overview = new OverviewPanel();
	
	/**
	 * Adds Main View of the planning poker panel when the user goes to the planning poker tab
	 */
	public MainView(){
		this.add(new JLabel("PlanningPoker placeholder"));
	    this.setBorder(BorderFactory.createLineBorder(Color.green, 2));
	}

}
