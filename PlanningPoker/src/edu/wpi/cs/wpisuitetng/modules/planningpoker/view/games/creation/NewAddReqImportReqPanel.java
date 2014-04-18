package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

//import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation.AddRequirementsPanel;

public class NewAddReqImportReqPanel extends JPanel {
	
	private JButton addReq;
	
	private JButton importReq;
		
	public NewAddReqImportReqPanel(final NewAddRequirementsPanel reqPanel) {
		
		SpringLayout layout = new SpringLayout();
		setLayout(layout);
		
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
		add(addReq);
		add(importReq);
		
		layout.putConstraint(SpringLayout.WEST, addReq, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, importReq, -5, SpringLayout.EAST, this);
		
	}
	
	public void setCreateReqPanelVisible(AddRequirementsPanel panel) {
		this.setVisible(true);
	}
	
}