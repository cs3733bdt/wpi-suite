/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation;

import javax.swing.JPanel;
import java.util.Date;
import org.jdesktop.swingx.JXDatePicker;

/**
 *
 */
public class AddEndDatePanel extends JPanel {
	
	private final JXDatePicker datePicker = new JXDatePicker(new Date());
	
	public AddEndDatePanel(final CreateGamePanel view){
		this.add(datePicker);	
	}
}
