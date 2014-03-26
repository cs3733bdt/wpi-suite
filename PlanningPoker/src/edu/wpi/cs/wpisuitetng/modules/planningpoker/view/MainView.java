/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.overview.OverviewPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.ActiveGamesPanel;

/**
 * This class sets the main view when the user goes to the PlanningPoker tab 
 * @author jonathanleitschuh
 *
 */
public class MainView extends JTabbedPane {
	
	private ActiveGamesPanel activeGames = new ActiveGamesPanel();
	
	/**
	 * Adds Main View of the planning poker panel when the user goes to the planning poker tab
	 */
	public MainView(){
		this.addTab("Active Games", activeGames);
	    this.setBorder(BorderFactory.createLineBorder(Color.green, 2));
	}

	/**
	 * needed to get controller functioning
	 * TODO add purpose for this function
	 * @return boolean true always
	 */
	public boolean getNewGame(){
		return true;
	}
}
