package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.NameJTextField;

/**
 * TODO DOCUMENTATION
 */
public class NewLeftHalfCreateGamePanel extends JScrollPane {
	
	private NameJTextField nameTextField;
	private JTextArea descriptionTextField;
	
	public NewLeftHalfCreateGamePanel() {
		JScrollPane rightScrollPane = new JScrollPane();
		
		JLabel nameLabel = new JLabel("Name * ");
		JLabel descLabel = new JLabel("Description * ");
	}
}