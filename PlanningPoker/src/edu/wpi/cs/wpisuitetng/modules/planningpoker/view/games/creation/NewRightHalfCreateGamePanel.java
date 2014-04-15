package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

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
		final Container rightView = new Container();
		SpringLayout layout = new SpringLayout();
		rightView.setLayout(layout);
		rightView.setMinimumSize(new Dimension(250, 500)); //@TODO change the 500 to something more appropriate
		
		
		
		rightView.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				rightView.repaint();
			}
			
		});
		
		reqPanel =  new NewAddRequirementsPanel(this);
		//importPanel = new NewAddReqImportReqPanel(reqPanel);
		
		//Anchor AddReqPanel's left side to left side of container
		layout.putConstraint(SpringLayout.WEST, reqPanel,
				5, //add -5 if issues with the bar
				SpringLayout.WEST, rightView);

		
		//Anchor AddReqPanel top to the top of the container
		layout.putConstraint(SpringLayout.NORTH, reqPanel,
				5,
				SpringLayout.NORTH, rightView);

		//Anchor the AddReqPanel's right side to the right side of the container
		layout.putConstraint(SpringLayout.EAST, reqPanel, 
				-5, 
				SpringLayout.EAST, rightView);

//		//Anchor the top of the import panel to the bottom of the AddReqPanel
//		layout.putConstraint(SpringLayout.NORTH, importPanel,
//				5,
//				SpringLayout.SOUTH, reqPanel);
//
//		//Anchor the left side of the import panel to the left side of the container
//		layout.putConstraint(SpringLayout.WEST, importPanel,
//				5,
//				SpringLayout.WEST, rightView);
//
//		//Anchor the right side of the import panel to the right side of the container
//		layout.putConstraint(SpringLayout.EAST, importPanel,
//				5,
//				SpringLayout.EAST, rightView);
		
		

		rightView.add(reqPanel);
		//rightView.add(importPanel);
		this.getViewport().add(rightView);
	}

	public void addRequirement(Requirement requirement) {
		createGamePanel.addRequirement(requirement);
	}
	
}