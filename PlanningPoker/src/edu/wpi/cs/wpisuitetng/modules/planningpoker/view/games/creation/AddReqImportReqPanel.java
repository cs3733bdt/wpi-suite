package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

//import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation.AddRequirementsPanel;

public class AddReqImportReqPanel extends JPanel {
	
	private JButton addReq;
	
	private JButton importReq;
		
	public AddReqImportReqPanel(final AddRequirementsPanel view2){
		
		//this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		/**
		 * Initializes Add Requirement button and sets its action listener
		 */
		addReq = new JButton("Add Requirement");
		addReq.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				 /*view.addRequirement(new Requirement(nameArea.getText(), descArea.getText()));
				 nameArea.setText("");
				 descArea.setText("");*/
				 view2.getCreateReqPanel().setVisible(true);
				 view2.getImportReqPanel().setVisible(false);
				 view2.getCurrentReqsPanel().setVisible(false);
			 }
		});
		
		importReq = new JButton("Import Requirement");
		importReq.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				 /*view.addRequirement(new Requirement(nameArea.getText(), descArea.getText()));
				 nameArea.setText("");
				 descArea.setText("");*/
				 view2.getCreateReqPanel().setVisible(false);
				 view2.getImportReqPanel().setVisible(true);
				 view2.getCurrentReqsPanel().setVisible(false);
			 }
		});
		/**
		 * adds both buttons to the panel
		 */
		this.add(addReq);
		this.add(Box.createRigidArea(new Dimension(220, 0)));
		//this.add(importReq);
		
	}
	
	public void setCreateReqPanelVisible(AddRequirementsPanel panel) {
		this.setVisible(true);
	}
	
}