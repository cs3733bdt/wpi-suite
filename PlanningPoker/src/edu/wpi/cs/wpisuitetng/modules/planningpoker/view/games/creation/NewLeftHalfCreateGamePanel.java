package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation;

import java.awt.Container;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.Border;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.NameJTextField;

/**
 * TODO DOCUMENTATION
 */
public class NewLeftHalfCreateGamePanel extends JScrollPane {
	
	private NameJTextField nameTextField;
	private JTextArea descriptionTextField;
	
	private JRadioButton cardsButton = new JRadioButton("Estimate With Cards");
	private JRadioButton textEntryButton = new JRadioButton("Estimate With Text Entry");
	
	private final Border defaultBorder = (new JTextField()).getBorder();
	
	public NewLeftHalfCreateGamePanel() {
		Container leftView = new Container(); 				//Creates the container for everything in the view
		SpringLayout layout = new SpringLayout(); 			//Creates the layout to be used: Spring Layout
		leftView.setLayout(layout);							//Sets the container to have the spring layout

		/**
		 * Create and/or initialize components
		 */
		JLabel nameLabel = new JLabel("Name * ");			//Creates the Label for the Name
		nameTextField = new NameJTextField(30);				//Initializes the textfield for the game name and sets the size to 30
		JLabel descLabel = new JLabel("Description * ");	//Creates the label for the Description
		descriptionTextField = new JTextArea(5, 30);		//Initializes the textarea for the game description
		descriptionTextField.setBorder(defaultBorder);		//Sets the default border to the description text area
		
		/**
		 * Add components to container
		 */
		leftView.add(nameLabel);							//Adds name label to the container
		leftView.add(nameTextField);						//Adds name text field to the container
		leftView.add(descLabel);							//Adds description label to the container
		leftView.add(descriptionTextField);					//Adds description field to the container
		
		/**
		 * Sets the default radio button to be selected (cardsButton)
		 */
		cardsButton.setSelected(true);
		/**
		 * Adds the radio buttons
		 */
		leftView.add(cardsButton);
		leftView.add(textEntryButton);
		/**
		 * Radio Buttongroup to make only 1 radio button selectable
		 */
		ButtonGroup radioGroup = new ButtonGroup();
		radioGroup.add(cardsButton);
		radioGroup.add(textEntryButton);
		
		
		
		/**
		 * Adjust layout constraints to correctly setup the layout of each component
		 */
		layout.putConstraint(SpringLayout.WEST, nameLabel, 5, SpringLayout.WEST, leftView); 					//Adds the name label to the far left
		layout.putConstraint(SpringLayout.NORTH, nameLabel, 5, SpringLayout.NORTH, leftView); 					//Adds the name label to the far top
		layout.putConstraint(SpringLayout.WEST, nameTextField, 37, SpringLayout.EAST, nameLabel); 				//Adds the name textfield to the right of the name label
        layout.putConstraint(SpringLayout.NORTH, nameTextField, 5, SpringLayout.NORTH, leftView); 				//Adds the name textfield to the far top
        layout.putConstraint(SpringLayout.EAST, nameTextField, -5, SpringLayout.EAST, leftView); 				//Makes sure the right side of the textfield stretches with the right side of the container
        layout.putConstraint(SpringLayout.WEST, descLabel, 5, SpringLayout.WEST, leftView); 					//Adds the description label to the far left
        layout.putConstraint(SpringLayout.NORTH, descLabel, 5, SpringLayout.SOUTH, nameLabel); 					//Adds the name description label underneath the name label
        layout.putConstraint(SpringLayout.WEST, descriptionTextField, 5, SpringLayout.EAST, descLabel); 		//Adds the name textfield to the right of the name label
        layout.putConstraint(SpringLayout.NORTH, descriptionTextField, 5, SpringLayout.SOUTH, nameTextField); 	//Adds the name textfield to the far top
        layout.putConstraint(SpringLayout.EAST, descriptionTextField, -5, SpringLayout.EAST, leftView);			//Makes sure the right side of the textfield stretches with the right side of the container

        
        //RADIO BUTTONS
        
        
		this.getViewport().add(leftView);			//Sets the view of the scrollpane to be the entire container which has everything contained within it
		
	}
	
}