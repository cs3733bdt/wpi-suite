package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.AddGameController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons.AddGameButtonPanel;

public class AddRequirementsPanel extends JPanel {
	public AddRequirementsPanel(){
		
		super(new GridBagLayout());
		
		Container reqPanel = new Container();
		reqPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		
		JLabel CreateReq = new JLabel("Create Requirement");
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.CENTER;
		
		reqPanel.add(CreateReq, c);
		
		JLabel ImportReq = new JLabel("Import Requirement");
		c.gridx = 2;
		c.gridy = 0;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.CENTER;
		
		reqPanel.add(ImportReq, c);
		
		/*Panel createReqPanel = new Panel();
		createReqPanel.setLayout(new GridBagLayout());
		
		c.gridx = 0;
		c.gridy = 1;
		reqPanel.add(createReqPanel, c);*/
		
		JLabel reqName = new JLabel("Name:");
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.CENTER;
		
		reqPanel.add(reqName, c);
		
		JTextArea nameArea = new JTextArea();
		c.gridx = 1;
		c.gridy = 0;
		
		reqPanel.add(nameArea, c);
		
		JLabel reqDesc = new JLabel("Description:");
		c.gridx = 0;
		c.gridy = 2;
		c.anchor = GridBagConstraints.CENTER;
		
		reqPanel.add(reqDesc, c);
		
		JTextArea descArea = new JTextArea();
		c.gridx = 0;
		c.gridy = 3;
		
		reqPanel.add(descArea, c);
		
		JButton addReqButton = new JButton();
		c.gridx = 0;
		c.gridwidth = 1;
		c.gridy = 4;
		
		reqPanel.add(addReqButton, c);
		
		
		//reqPanel.add
		
		Container importReqPanel = new Container();
		importReqPanel.setLayout(new GridBagLayout());
		
	}
}