package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

//import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation.AddRequirementsPanel;

public class NewAddReqImportReqPanel extends JPanel {
	
	private JButton addReq;
	
	private JButton importReq;
		
	public NewAddReqImportReqPanel(final NewAddRequirementsPanel reqPanel) {
		
		//this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		/**
		 * Initializes Add Requirement button and sets its action listener
		 */
		addReq = new JButton("Add Requirement");
		addReq.addActionListener(new ActionListener() {
			 @Override
			public void actionPerformed(ActionEvent e) {
				 /*view.addRequirement(new Requirement(nameArea.getText(), descArea.getText()));
				 nameArea.setText("");
				 descArea.setText("");*/
				 reqPanel.getCreateReqPanel().setVisible(true);
				 reqPanel.getImportReqPanel().setVisible(true);
				 reqPanel.getCurrentReqsTable().setVisible(false);
			 }
		});
		
		importReq = new JButton("Import Requirement");
		importReq.addActionListener(new ActionListener() {
			 @Override
			public void actionPerformed(ActionEvent e) {
				 /*view.addRequirement(new Requirement(nameArea.getText(), descArea.getText()));
				 nameArea.setText("");
				 descArea.setText("");*/
				 reqPanel.getCreateReqPanel().setVisible(false);
				 reqPanel.getImportReqPanel().setVisible(true);
				 reqPanel.getCurrentReqsTable().setVisible(false);
			 }
		});
		/**
		 * adds both buttons to the panel
		 */
		this.add(addReq);
		//this.add(Box.createRigidArea(new Dimension(220, 0)));
		//this.add(importReq);
		
	}
	
	public void setCreateReqPanelVisible(AddRequirementsPanel panel) {
		this.setVisible(true);
	}
	
}