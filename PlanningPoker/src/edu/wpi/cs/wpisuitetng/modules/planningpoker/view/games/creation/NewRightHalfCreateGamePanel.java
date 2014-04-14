package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.requirement.Requirement;

/**
 * TODO DOCUMENTATION
 */
public class NewRightHalfCreateGamePanel extends JScrollPane {
	NewAddRequirementsPanel reqPanel;    //initialize new add requirements panel
	NewAddReqImportReqPanel importPanel;    //initialize the panel with the buttons "Add Requirement" and "Import Requirements"
	NewCreateGamePanel createGamePanel;  //initialize variable to hold panel above this panel
	
	public NewRightHalfCreateGamePanel(NewCreateGamePanel createGamePanel){
		this.createGamePanel = createGamePanel;
		
		//Initializes a container with SpringLayout and adds it to this panel 
		Container rightView = new Container();
		SpringLayout layout = new SpringLayout();
		rightView.setLayout(layout);
		
		reqPanel =  new NewAddRequirementsPanel(this);
		importPanel = new NewAddReqImportReqPanel(reqPanel);
		
		//Anchor reqPanel to left side of container
		layout.putConstraint(SpringLayout.WEST, reqPanel,
		                     5, //add -5 if issues with the bar
		                     SpringLayout.WEST, rightView);
		layout.putConstraint(SpringLayout.NORTH, reqPanel,
		                     5,
		                     SpringLayout.NORTH, rightView);
		
		layout.putConstraint(SpringLayout.EAST, reqPanel, 
							-5, 
							SpringLayout.EAST, rightView);
	
		//Adjust constraints for the text field so it's at
		layout.putConstraint(SpringLayout.NORTH, importPanel,
		                     5,
		                     SpringLayout.SOUTH, reqPanel);
		layout.putConstraint(SpringLayout.WEST, importPanel,
		                     5,
		                     SpringLayout.WEST, rightView);
		
		

		rightView.add(reqPanel);
		rightView.add(importPanel);
		this.getViewport().add(rightView);
	}

	public void addRequirement(Requirement requirement) {
		createGamePanel.addRequirement(requirement);
	}
	
}