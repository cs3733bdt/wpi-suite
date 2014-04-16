package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.border.Border;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.requirement.Requirement;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.ActiveGamesTable;

/**
 * TODO DOCUMENTATION
 */
public class NewRightHalfCreateGamePanel extends JScrollPane {
    NewAddRequirementsPanel reqPanel;    //initialize new add requirements panel
    NewAddReqImportReqPanel importPanel;    //initialize the panel with the buttons "Add Requirement" and "Import Requirements"
    NewCreateGamePanel createGamePanel;  //initialize variable to hold panel above this panel
   
    private final Border defaultBorder = (new JTextField()).getBorder();
   
    private ActiveGamesTable table2;
   
    public NewRightHalfCreateGamePanel(NewCreateGamePanel createGamePanel){
       
        Container rightView = new Container();                 //Creates the container for everything in the view
        SpringLayout layout = new SpringLayout();             //Creates the layout to be used: Spring Layout
        rightView.setLayout(layout);                        //Sets the container to have the spring layout       
       
        /**
		 * Initializes objects for use in table
		 */
		table2 = initializeTable();
		Font labelFont = makeFont();
		
		Border defaultBorder = BorderFactory.createLineBorder(Color.black);
    
       
        JPanel currentReqsPanel = new JPanel();
        SpringLayout currentLayout = new SpringLayout();
        currentReqsPanel.setLayout(currentLayout);
        currentReqsPanel.setBorder(defaultBorder);
       
        JLabel currentReqs = new JLabel("Current Requirements");
        currentReqs.setFont(labelFont);
              
        JScrollPane tablePanel2 = new JScrollPane(table2);
        tablePanel2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
       
        currentReqsPanel.add(currentReqs);
        currentReqsPanel.add(tablePanel2);
       
        currentLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, currentReqs, 5, SpringLayout.HORIZONTAL_CENTER, currentReqsPanel);
        //currentLayout.putConstraint(SpringLayout.WEST, currentReqs, 5, SpringLayout.WEST, currentReqsPanel);
        //currentLayout.putConstraint(SpringLayout.EAST, currentReqs, -5, SpringLayout.EAST, currentReqsPanel);
       
        currentLayout.putConstraint(SpringLayout.NORTH, tablePanel2, 5, SpringLayout.SOUTH, currentReqs);
        currentLayout.putConstraint(SpringLayout.WEST, tablePanel2, 5, SpringLayout.WEST, currentReqsPanel);
        currentLayout.putConstraint(SpringLayout.EAST, tablePanel2, -5, SpringLayout.EAST, currentReqsPanel);
        currentLayout.putConstraint(SpringLayout.SOUTH, tablePanel2, -5, SpringLayout.SOUTH, currentReqsPanel);
       
        rightView.add(currentReqsPanel);
       
        layout.putConstraint(SpringLayout.NORTH, currentReqsPanel, 5, SpringLayout.NORTH, rightView);
        layout.putConstraint(SpringLayout.SOUTH, currentReqsPanel, -5, SpringLayout.SOUTH, rightView);
        layout.putConstraint(SpringLayout.WEST, currentReqsPanel, 5, SpringLayout.WEST, rightView);
        layout.putConstraint(SpringLayout.EAST, currentReqsPanel, -5, SpringLayout.EAST, rightView);
       
        JPanel createReqPanel = new JPanel();
        createReqPanel.setBorder(defaultBorder);
       
        setMinimumSize(new Dimension(350, 350));
        getViewport().add(rightView);
    }
    
	public Font makeFont() {
		/**
		 * Creates a new font for use later
		 */
		//create a dummy JTextArea
		JTextArea editingArea = new JTextArea();
		// get the current font
		Font f = editingArea.getFont();
		// create a new, larger font from the current font
		Font newFont = new Font(f.getFontName(), f.getStyle(), f.getSize()+8);		
		//set the bigger font for userStoryDesc
		Font bigFont = newFont;
		return bigFont;
	}
	
	private ActiveGamesTable initializeTable() {
		String[] columnNames2 = {"Requirement", "Description"};
		Object[][] data2 = {};
		return new ActiveGamesTable(data2, columnNames2);
	}
}