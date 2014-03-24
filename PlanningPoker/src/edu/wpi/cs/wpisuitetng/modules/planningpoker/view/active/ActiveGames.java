package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * 
 * @author Jeff
 * This class serves as the view for the active games section.
 */
public class ActiveGames extends JTabbedPane {
	
	private ActiveGamesPanel activeview = new ActiveGamesPanel();
	
	
	public ActiveGames(){
		this.add(new JLabel("This is the active games panel"));
		this.add(activeview);
		
	}

}