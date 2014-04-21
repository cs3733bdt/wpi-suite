/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Team Bobby Drop Tables
 *******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.border.Border;

import org.jdesktop.swingx.JXDatePicker;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirement.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.ActiveGamesTable;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.ImportGamesTable;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.IDataField;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.IErrorView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.NameJTextField;

/**
 * TODO DOCUMENTATION
 */
public class NewRightHalfCreateGamePanel extends JScrollPane implements IDataField{
    private NewAddRequirementsPanel reqPanel;    //initialize new add requirements panel
    private NewAddReqImportReqPanel importPanel;    //initialize the panel with the buttons "Add Requirement" and "Import Requirements"
    private NewCreateGamePanel parent;  //initialize variable to hold panel above this panel
    
    private List<Requirement> requirements = new ArrayList<Requirement>();
   
    private final Border defaultTextFieldBorder = (new JTextField()).getBorder();
    
    private final Border defaultTextAreaBorder = (new JTextArea()).getBorder();
       
    private final Border defaultPanelBorder = (new JPanel()).getBorder();
    
    private int globalRow = -1; //this Variable is used to keep track of when the submit or update 
    							//button is grayed out as well as which row is being updated when the edit button is pressed
    
    private final Border errorBorder = BorderFactory
			.createLineBorder(Color.RED);

    //THIS IS THE REQUIREMENT NAME FIELD THAT WILL BE NEEDED FOR CONTROLLER
	private JTextField nameArea = new NameJTextField(30);	
		
	//THIS IS THE REQUIREMENT DESCRIPTION FIELD THAT WILL BE NEEDED FOR CONTROLLER
	private JTextArea descArea = new JTextArea();
    
    private JLabel errorLabel = new JLabel();
    
    private ImportGamesTable importTable;
    
    private JLabel createReqsLabel;
    private JLabel updateReqsLabel;
	
    private ActiveGamesTable currentTable;
   
    private JPanel createReqsPanel = new JPanel();
    
    final JPanel currentReqsPanel = new JPanel();

    private JPanel importReqsPanel = new JPanel();
    
    private JButton addReqButton = new JButton("Add Requirement");
    

    private JButton editReqButton = new JButton("Edit");

    private JButton updateAddReqButton = new JButton("Update");
    
    private JButton submitAddReqButton = new JButton("Submit");

    private JButton importReqButton = new JButton("Import");
    
    private JButton removeReqButton = new JButton("Remove");
   
    public NewRightHalfCreateGamePanel(NewCreateGamePanel createGamePanel){
    	parent = createGamePanel;
    	build();
    	buildFields();
    }
    
    
    private void build(){
        Container rightView = new Container();                 //Creates the container for everything in the view
        SpringLayout layout = new SpringLayout();             //Creates the layout to be used: Spring Layout
        rightView.setLayout(layout);                        //Sets the container to have the spring layout       
       
        /**
		 * Initializes objects for use in table
		 */
        importTable = initializeImportTable();
		currentTable = initializeTable();
		Font labelFont = makeFont();
						
		/**
		 * Code for Current Reqs Panel
		 */
        SpringLayout currentLayout = new SpringLayout();
        currentReqsPanel.setLayout(currentLayout);
        currentReqsPanel.setBorder(defaultPanelBorder);
       
        JLabel currentReqs = new JLabel("Current Requirements");
        currentReqs.setFont(labelFont);
              
        JScrollPane tablePanel2 = new JScrollPane(currentTable);
        tablePanel2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
      
        currentReqsPanel.add(currentReqs);
        currentReqsPanel.add(tablePanel2);
        
        /**
         * CODE FOR CREATE REQ PANEL
         */
        SpringLayout createLayout = new SpringLayout();
        createReqsPanel.setLayout(createLayout);
        createReqsPanel.setBorder(defaultPanelBorder);
        
        //initializes and set up the Create Requirement Label
        createReqsLabel = new JLabel("Create Requirement");
        createReqsLabel.setFont(labelFont);
        createReqsPanel.add(createReqsLabel);
        
        //initializes and set up the update requirement label
        updateReqsLabel = new JLabel("Update Requirement");
        updateReqsLabel.setFont(labelFont);
        createReqsPanel.add(updateReqsLabel);
   
        
        //Initializes the Name label and Area and adds them to the createReqPanel
		nameArea.setPreferredSize(new Dimension(75, 25));
        JLabel reqName = new JLabel("Requirement Name: *");
        createReqsPanel.add(reqName);
        createReqsPanel.add(nameArea);
        addKeyListenerTo(nameArea);
        
        //Adds error label to the createReqPanel
        createReqsPanel.add(errorLabel);
        
        //initializes the Desc Label and area and adds them to the createPanel
        /**
		 * Creates and adds the requirement description label
		 */
		JLabel reqDesc = new JLabel("Requirement Description: *");
		
		/**
		 * Creates a scroll pane for the description
		 */
		JScrollPane descPane = new JScrollPane(descArea);
		addKeyListenerTo(descArea);
		
		/**
		 * Creates and adds the description text area (descArea)
		 */
		descArea.setLineWrap(true);
		descPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		createReqsPanel.add(reqDesc);
		createReqsPanel.add(descPane);
		
		/**
		 * Creates a new button to add the requirements to the game
		 */
		submitAddReqButton.setEnabled(false);
		submitAddReqButton.addActionListener(new ActionListener() {
			 @Override
			public void actionPerformed(ActionEvent e) {
				submitButtonPressed();
			 }
		});
		addMouseListenerTo(submitAddReqButton);
		
		/**
		 * Format and Create Cancel Button
		 */
		JButton cancelRequirementButton = new JButton("Cancel");
		cancelRequirementButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nameArea.setText("");
				descArea.setText("");
				createReqsPanel.setVisible(false);
				currentReqsPanel.setVisible(true);
				importReqsPanel.setVisible(false);
				addReqButton.setEnabled(true);
				importReqButton.setEnabled(true);
				removeReqButton.setEnabled(true);
				globalRow = -1;
				submitAddReqButton.setEnabled(false);
				enableButtons();
			}
		});
		
		/**
		 * Add all components to the createReqsPanel and set it to not visible at first
		 */
		createReqsPanel.add(submitAddReqButton);
		createReqsPanel.add(cancelRequirementButton);
		createReqsPanel.add(updateAddReqButton);
		
		createReqsPanel.setVisible(false);
       
		
		/**
         * CODE FOR IMPORT REQ PANEL
         */
        SpringLayout importLayout = new SpringLayout();
        importReqsPanel.setLayout(importLayout);
		
		JLabel importReq = new JLabel("Import Requirements");	//Creates the label import requirement
		importReq.setFont(labelFont);							//Sets the label font
		
		JScrollPane tablePanel = new JScrollPane(importTable);
        tablePanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);        
		
        /**
		 * Creates a new button to import the requirements to the game
		 */
		JButton submitImportReqButton = new JButton("Submit");
		submitImportReqButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				submitImportButtonPressed();
			}
		});
		
		/**
		 * Action listener for the cancel button in the import req panel
		 */
		
		JButton cancelImportReqButton = new JButton("Cancel");
		cancelImportReqButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nameArea.setText("");
				descArea.setText("");
				createReqsPanel.setVisible(false);
				currentReqsPanel.setVisible(true);
				importReqsPanel.setVisible(false);
				addReqButton.setEnabled(true);
				importReqButton.setEnabled(true);
				removeReqButton.setEnabled(true);
				enableButtons();
			}
		});
		
		/**
		 * Add an action listener the remove Req Button
		 */
		removeReqButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int[] rows = currentTable.getSelectedRows();
				for (int i = 0; i < rows.length; i++) {
					currentTable.getTableModel().removeRow(rows[i]);
				}
			}
		});
		
		/**
		 * Add all components to the importReqsPanel and make it not visible to start
		 */
		importReqsPanel.add(importReq);
		importReqsPanel.add(tablePanel);
		importReqsPanel.add(submitImportReqButton);
		importReqsPanel.add(cancelImportReqButton);
		
		
		importReqsPanel.setVisible(false);
		editReqButton.setEnabled(false);
		removeReqButton.setEnabled(false);
		
		/**
		 * Formats the buttons underneath the panels
		 */
		JButton testButton = new JButton("tttttttttttttttttt"); //Don't change text, it determines button width...
		Dimension buttonD = testButton.getPreferredSize();
		importReqButton.setPreferredSize(buttonD);
        editReqButton.setPreferredSize(buttonD);
        removeReqButton.setPreferredSize(buttonD);
		
		/**
		 * Add all components to the container
		 */
        rightView.add(currentReqsPanel);
        rightView.add(createReqsPanel);
        rightView.add(importReqsPanel);
        rightView.add(addReqButton);
        rightView.add(removeReqButton);
        rightView.add(editReqButton);
        rightView.add(importReqButton);
        rightView.add(removeReqButton);
        
        //IN THE CURRENT REQ PANEL
        // the tile with respect to the panel
        currentLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, currentReqs, 5, SpringLayout.HORIZONTAL_CENTER, currentReqsPanel);
        // Table with respect to the title
        currentLayout.putConstraint(SpringLayout.NORTH, tablePanel2, 5, SpringLayout.SOUTH, currentReqs);
        //table with respect panel 
        currentLayout.putConstraint(SpringLayout.WEST, tablePanel2, 5, SpringLayout.WEST, currentReqsPanel);
        currentLayout.putConstraint(SpringLayout.EAST, tablePanel2, -5, SpringLayout.EAST, currentReqsPanel);
        currentLayout.putConstraint(SpringLayout.SOUTH, tablePanel2, -5, SpringLayout.SOUTH, currentReqsPanel);
        
        //IN RIGHT VEIW CONTAINER 
        //currentPanel with respect to the container 
        layout.putConstraint(SpringLayout.NORTH, currentReqsPanel, 5, SpringLayout.NORTH, rightView);
        layout.putConstraint(SpringLayout.SOUTH, currentReqsPanel, -50, SpringLayout.SOUTH, rightView);
        layout.putConstraint(SpringLayout.WEST, currentReqsPanel, 5, SpringLayout.WEST, rightView);
        layout.putConstraint(SpringLayout.EAST, currentReqsPanel, -5, SpringLayout.EAST, rightView);
        
        //addreq button
        layout.putConstraint(SpringLayout.SOUTH, addReqButton, -5, SpringLayout.SOUTH, rightView);
        layout.putConstraint(SpringLayout.SOUTH, currentReqsPanel, -5, SpringLayout.NORTH, addReqButton);
        layout.putConstraint(SpringLayout.WEST, addReqButton, 10, SpringLayout.WEST, rightView);
        //importreq button
        layout.putConstraint(SpringLayout.SOUTH, importReqButton, -5, SpringLayout.SOUTH, rightView);
        layout.putConstraint(SpringLayout.SOUTH, currentReqsPanel, -5, SpringLayout.NORTH, importReqButton);
        layout.putConstraint(SpringLayout.WEST, importReqButton, 5, SpringLayout.EAST, addReqButton);
        //removereq button
        layout.putConstraint(SpringLayout.SOUTH, removeReqButton, -5, SpringLayout.SOUTH, rightView);
        layout.putConstraint(SpringLayout.SOUTH, currentReqsPanel, -5, SpringLayout.NORTH, removeReqButton);
        layout.putConstraint(SpringLayout.EAST, removeReqButton, -10, SpringLayout.EAST, rightView);
        // currentPanel with respect to the button 
        layout.putConstraint(SpringLayout.SOUTH, addReqButton, -5, SpringLayout.SOUTH, rightView);
        layout.putConstraint(SpringLayout.SOUTH, currentReqsPanel, -5, SpringLayout.NORTH, addReqButton);
        layout.putConstraint(SpringLayout.SOUTH, createReqsPanel, -5, SpringLayout.NORTH, addReqButton);     
        //edit req button
        layout.putConstraint(SpringLayout.SOUTH, editReqButton, -5, SpringLayout.SOUTH, rightView);
        layout.putConstraint(SpringLayout.EAST, editReqButton, -5, SpringLayout.WEST, removeReqButton);
//        
//        //importReqButton
//        layout.putConstraint(SpringLayout.SOUTH, importReqButton, -5, SpringLayout.SOUTH, rightView);
//        layout.putConstraint(SpringLayout.EAST, importReqButton, -5, SpringLayout.EAST, rightView);
//        
//        //remove req button 
//        layout.putConstraint(SpringLayout.EAST, removeReqButton, 5, SpringLayout.WEST, importReqButton);
//        
        
        
        //CREATE REQS PANEL
        //createPanel with respect to the container 
        layout.putConstraint(SpringLayout.NORTH, createReqsPanel, 5, SpringLayout.NORTH, rightView);
        layout.putConstraint(SpringLayout.SOUTH, createReqsPanel, -50, SpringLayout.SOUTH, rightView);
        layout.putConstraint(SpringLayout.WEST, createReqsPanel, 5, SpringLayout.WEST, rightView);
        layout.putConstraint(SpringLayout.EAST, createReqsPanel, -5, SpringLayout.EAST, rightView);
        
        // create Reqs label
        createLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, createReqsLabel, 5, SpringLayout.HORIZONTAL_CENTER, createReqsPanel);
        createLayout.putConstraint(SpringLayout.NORTH, createReqsLabel, 5, SpringLayout.NORTH, parent);
        
        //update Reqs Label/
        createLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, updateReqsLabel, 5, SpringLayout.HORIZONTAL_CENTER, createReqsPanel);
        createLayout.putConstraint(SpringLayout.NORTH, updateReqsLabel, 5, SpringLayout.NORTH, parent);
       
        // req name label with respect to create panel
        createLayout.putConstraint(SpringLayout.WEST, reqName, 5, SpringLayout.WEST, createReqsPanel); 					
		createLayout.putConstraint(SpringLayout.NORTH, reqName, 5, SpringLayout.SOUTH, createReqsLabel); 					
		// req name label with respect to the text box
		createLayout.putConstraint(SpringLayout.NORTH, nameArea, 5, SpringLayout.SOUTH, reqName); 
		//name text box with respect to the create panel 
		createLayout.putConstraint(SpringLayout.WEST, nameArea, 5, SpringLayout.WEST, createReqsPanel); 				
        createLayout.putConstraint(SpringLayout.EAST, nameArea, -5, SpringLayout.EAST, createReqsPanel); 
        
        // desc label with respect to name area
    	createLayout.putConstraint(SpringLayout.NORTH, reqDesc, 5, SpringLayout.SOUTH, nameArea); 
        // desc label with respect to create panel
        createLayout.putConstraint(SpringLayout.WEST, reqDesc, 5, SpringLayout.WEST, createReqsPanel); 					
		// desc label with respect to the desc text box
		createLayout.putConstraint(SpringLayout.NORTH, descPane, 5, SpringLayout.SOUTH, reqDesc); 
		// desc text box with respect to the create panel 
		createLayout.putConstraint(SpringLayout.WEST, descPane, 5, SpringLayout.WEST, createReqsPanel); 				
        createLayout.putConstraint(SpringLayout.EAST, descPane, -5, SpringLayout.EAST, createReqsPanel); 
        
        //position submit button with respect to createReqPanel
        createLayout.putConstraint(SpringLayout.SOUTH, submitAddReqButton, -5, SpringLayout.SOUTH, createReqsPanel);
        createLayout.putConstraint(SpringLayout.WEST, submitAddReqButton, 5, SpringLayout.WEST, createReqsPanel);
        
        //position update button with respect to createReqPanel
        createLayout.putConstraint(SpringLayout.SOUTH, updateAddReqButton, -5, SpringLayout.SOUTH, createReqsPanel);
        createLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, updateAddReqButton, 5, SpringLayout.HORIZONTAL_CENTER, createReqsPanel);
        
       
        //postion error label with respect to creaReqPanel
        createLayout.putConstraint(SpringLayout.SOUTH, errorLabel, -1, SpringLayout.SOUTH, createReqsPanel);
        createLayout.putConstraint(SpringLayout.NORTH, errorLabel, 1, SpringLayout.SOUTH, updateAddReqButton);
        createLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, errorLabel, 1, SpringLayout.HORIZONTAL_CENTER, createReqsPanel);
       
        //position cancel button with respect to createReqPanel
        createLayout.putConstraint(SpringLayout.SOUTH, cancelRequirementButton, -5, SpringLayout.SOUTH, createReqsPanel);
        createLayout.putConstraint(SpringLayout.EAST, cancelRequirementButton, -5, SpringLayout.EAST, createReqsPanel);
        
        //anchor descPane to the top of the submit button
        createLayout.putConstraint(SpringLayout.SOUTH, descPane, -20, SpringLayout.NORTH, submitAddReqButton);
               
        //IMPORT REQS PANEL
        layout.putConstraint(SpringLayout.NORTH, importReqsPanel, 5, SpringLayout.NORTH, rightView);
        layout.putConstraint(SpringLayout.SOUTH, importReqsPanel, -36, SpringLayout.SOUTH, rightView);
        layout.putConstraint(SpringLayout.WEST, importReqsPanel, 5, SpringLayout.WEST, rightView);
        layout.putConstraint(SpringLayout.EAST, importReqsPanel, -5, SpringLayout.EAST, rightView);
        
        importLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, importReq, 5, SpringLayout.HORIZONTAL_CENTER, importReqsPanel);
        importLayout.putConstraint(SpringLayout.NORTH, importReq, 5, SpringLayout.NORTH, importReqsPanel);
        
        importLayout.putConstraint(SpringLayout.NORTH, tablePanel, 5, SpringLayout.SOUTH, importReq);
        importLayout.putConstraint(SpringLayout.WEST, tablePanel, 5, SpringLayout.WEST, importReqsPanel);
        importLayout.putConstraint(SpringLayout.EAST, tablePanel, -5, SpringLayout.EAST, importReqsPanel);
        importLayout.putConstraint(SpringLayout.SOUTH, tablePanel, 5, SpringLayout.NORTH, submitImportReqButton);
        
        importLayout.putConstraint(SpringLayout.SOUTH, submitImportReqButton, -5, SpringLayout.SOUTH, importReqsPanel);
        importLayout.putConstraint(SpringLayout.WEST, submitImportReqButton, 5, SpringLayout.WEST, importReqsPanel);
        
        importLayout.putConstraint(SpringLayout.SOUTH, cancelImportReqButton, -5, SpringLayout.SOUTH, importReqsPanel);
        importLayout.putConstraint(SpringLayout.EAST, cancelImportReqButton, -5, SpringLayout.EAST, importReqsPanel);      
        
        importLayout.putConstraint(SpringLayout.SOUTH, tablePanel, -20, SpringLayout.NORTH, submitImportReqButton);
        
        /**
         * Set the minimum size and add components to the viewport of the scrollpane
         */
        setMinimumSize(new Dimension(350, 350));
        rightView.setPreferredSize(new Dimension(485, 350));
        getViewport().add(rightView);
        
        /**
         * action listener for the edit button
         */
        editReqButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				editReqButtonAction();	
			}
        });
 
		
        /**
         * action listener for the edit button inside the view for updating requirements
         */
        updateAddReqButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				updateButtonPressed();
			}
		});
        
        /**
         * Action listener for the button to add a requirement
         */
		addReqButton.addActionListener(new ActionListener() {    
			@Override
			public void actionPerformed(ActionEvent e) {
				currentReqsPanel.setVisible(false);
				createReqsPanel.setVisible(true);
				importReqsPanel.setVisible(false);
				addReqButton.setEnabled(false);
				importReqButton.setEnabled(false);
				removeReqButton.setEnabled(false);
			//	submitAddReqButton.setEnabled(true);
				updateAddReqButton.setEnabled(false);
				updateReqsLabel.setVisible(false);
				createReqsLabel.setVisible(true);
				disableButtons();
			}
		});
		
		/**
		 * Action listener for the import requirement button
		 */
		importReqButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {			 
				currentReqsPanel.setVisible(false);
				createReqsPanel.setVisible(false);
				importReqsPanel.setVisible(true);
				addReqButton.setEnabled(false);
				importReqButton.setEnabled(false);
				removeReqButton.setEnabled(false);
				disableButtons();
			} 
		});
		
		/**
		 * Add action listener to the remove button
		 */
		removeReqButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(currentTable.getSelectedRowCount() == 0){
				}
				else {
					while(currentTable.getSelectedRowCount() > 0){
					int[] rows = currentTable.getSelectedRows();
					currentTable.getTableModel().removeRow(rows[0]);
					}
					if(currentTable.getTableModel().getRowCount() == 0){
						removeReqButton.setEnabled(false);
						editReqButton.setEnabled(false);
					}
				}
			}
		});
		
		currentTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (currentTable.getSelectedRowCount() > 1){
					editReqButton.setEnabled(false);
				}
				else {
					editReqButton.setEnabled(true);
				}
			}
		});
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
	
	/**
	 * Instantiates this table
	 * @return the ActiveGamesTable
	 */
	private ActiveGamesTable initializeTable() {
		String[] columnNames2 = {"Requirement", "Description"};
		Object[][] data2 = {};
		ActiveGamesTable table = new ActiveGamesTable(data2, columnNames2);
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table.getColumnModel().getColumn(0).setMinWidth(100);
		table.getColumnModel().getColumn(0).setMaxWidth(400);
		table.getColumnModel().getColumn(1).setMinWidth(100);
		table.getColumnModel().getColumn(1).setMaxWidth(800);
		return table;
	}
	
	private ImportGamesTable initializeImportTable() {
		String[] columnNames2 = {"Requirement", "Description,"};
		Object[][] data2 = {};
		ImportGamesTable table = new ImportGamesTable(data2, columnNames2);
		/**
		 * Adds temporary data into the table. 
		 * DELETE THIS ONCE DATA IS SUCCESSFULLY IMPORTED FROM REQUIREMENT MANAGER!!!!!!!!!!!!
		 */
		table.getColumnModel().getColumn(0).setMinWidth(100);
		table.getColumnModel().getColumn(0).setMaxWidth(400);
		table.getColumnModel().getColumn(1).setMinWidth(100);
		table.getColumnModel().getColumn(1).setMaxWidth(800);
		table.getTableModel().addRow(new Object[]{"Requirement1", "Description1"});
		table.getTableModel().addRow(new Object[]{"Requirement2", "Description2"});
		table.getTableModel().addRow(new Object[]{"Requirement3", "Description3"});
		table.getTableModel().addRow(new Object[]{"Requirement4", "Description4"});
		table.getTableModel().addRow(new Object[]{"Requirement5", "Description5"});
		table.getTableModel().addRow(new Object[]{"Requirement6", "Description6"});
		table.getTableModel().addRow(new Object[]{"Requirement7", "Description7"});
		table.getTableModel().addRow(new Object[]{"Requirement8", "Description8"});
		table.getTableModel().addRow(new Object[]{"Requirement9", "Description9"});
		table.getTableModel().addRow(new Object[]{"Requirement10", "Description10"});
		return table;
	}
	
	private void submitButtonPressed(){
		if(validateNameAndDesc(true)){
			addRequirement(new Requirement(nameArea.getText(), descArea.getText()));
			nameArea.setText("");
			descArea.setText("");
			createReqsPanel.setVisible(false);
			currentReqsPanel.setVisible(true);
			enableButtons();
			submitAddReqButton.setEnabled(false);
			addReqButton.setEnabled(true);
			importReqButton.setEnabled(true);
			removeReqButton.setEnabled(true);
			editReqButton.setEnabled(true);
			globalRow = -1;
			parent.updateButtons();
		}
		
		
	}
	
	private void updateButtonPressed(){
		if(validateNameAndDesc(true) && globalRow != -1){
			currentTable.setValueAt(nameArea.getText(), globalRow, 0);
			currentTable.setValueAt(descArea.getText(), globalRow, 1);
			globalRow = -1;
			nameArea.setText("");
			descArea.setText("");
			createReqsPanel.setVisible(false);
			currentReqsPanel.setVisible(true);
			submitAddReqButton.setEnabled(false);
			enableButtons();
			parent.updateButtons();
		}
		
		
	}
	
	private void enableButtons() {
		addReqButton.setEnabled(true);
		importReqButton.setEnabled(true);
		removeReqButton.setEnabled(true);
		editReqButton.setEnabled(true);
	}
	
	private void disableButtons() {
		addReqButton.setEnabled(false);
		importReqButton.setEnabled(false);
		removeReqButton.setEnabled(false);
		editReqButton.setEnabled(false);
	}


	private void submitImportButtonPressed(){
		if(importTable.getSelectedRowCount() == 0){
			//SET ERROR LABEL TO SAY: "You must select one requirement to import, or click cancel to exit import."
		}
		else if(1==2){//SET IF-STATEMENT THAT CHECKS TO SEE IF THE SELECTED REQUIREMENTS ARE ALREADY ADDED
			//SET ERROR LABEL TO SAY: "The requirement you have selected to import ha already been added to this game."
		}
		else{
			int[] rows = importTable.getSelectedRows();
			for (int i = 0; i < rows.length; i++){
				String selectedName = (String) importTable.getValueAt(rows[i], 0);
				String selectedDesc = (String) importTable.getValueAt(rows[i], 1);
				currentTable.getTableModel().addRow(new Object[]{selectedName, selectedDesc});
			}
		}
		createReqsPanel.setVisible(false);
		currentReqsPanel.setVisible(true);
		importReqsPanel.setVisible(false);
		addReqButton.setEnabled(true);
		importReqButton.setEnabled(true);
		removeReqButton.setEnabled(true);
		enableButtons();
	}
	
	private boolean validateNameAndDesc(boolean show){
		boolean descriptionValid = false;
		boolean nameValid = false;
		
		if(descArea.getText().equals("")){
			displayError("A description must be entered");
			descArea.setBorder(errorBorder);
			descriptionValid = false;
		}
		else{
			descArea.setBorder(defaultTextAreaBorder);
			descriptionValid = true;
		}
		
		if(nameArea.getText().equals("")){
			displayError("A name must be entered");
			nameArea.setBorder(errorBorder);
			descArea.setBorder(defaultTextAreaBorder);
			nameValid = false;
		}
		else{
			nameArea.setBorder(defaultTextFieldBorder);
			nameValid = true;
		}
		
		if(!show){
			errorLabel.setText("");
			nameArea.setBorder(defaultTextFieldBorder);
			descArea.setBorder(defaultTextAreaBorder);
		}
		
		return nameValid && descriptionValid;
	}
	
	private void displayError(String errorString){
		errorLabel.setForeground(Color.RED);
		errorLabel.setText(errorString);
	}
	
	/**
	 * Sets the fields for this panel
	 */
	public void buildFields(){
		if(parent.getGame() != null){
			for(Requirement r : parent.getGame().getRequirements()){
				addRequirement(r);
			}
		}	
	}
	
	public List<Requirement> getRequirements(){
		return requirements;
	}
		
	public JPanel getCurrentReqsPanel() {
		return currentReqsPanel;
	}
	
	private void editReqButtonAction() {
		int row = currentTable.getSelectedRow();
		if (row == -1) {
			return;
		}
		currentReqsPanel.setVisible(false);
		createReqsPanel.setVisible(true);
		importReqsPanel.setVisible(false);
		submitAddReqButton.setEnabled(false);
		updateAddReqButton.setEnabled(false);
		updateReqsLabel.setVisible(true);
		createReqsLabel.setVisible(false);
		
		disableButtons();
		nameArea.setText((String) currentTable.getValueAt(row, 0));
		descArea.setText((String) currentTable.getValueAt(row, 1));
		globalRow = row;
	}


	public void addRequirement(Requirement requirement){
		currentTable.getTableModel().addRow(new Object[]{requirement.getName(), requirement.getDescription()});
		requirements.add(requirement);
	}


	@Override
	public boolean validateField(IErrorView warningField, boolean show) {
		parent.getLeftHalf().getBoxName().setBorder(defaultTextFieldBorder);
		parent.getLeftHalf().getBoxDescription().setBorder(defaultTextAreaBorder);
		parent.getLeftHalf().getEndDateField().setBorder((new JXDatePicker()).getBorder());
		
		if(requirements.size() <= 0){
			if(warningField != null){
				if(show){
					currentReqsPanel.setBorder(errorBorder);
					warningField.setText("The requirments list can not be empty");
				}
			}
			return false;
		}
		else{
			currentReqsPanel.setBorder(defaultPanelBorder);
			return true;
		}
	}


	@Override
	public boolean hasChanges() {
		// TODO Auto-generated method stub
		return false;
	}
	
	private void addKeyListenerTo(JComponent component){
		component.addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent arg0) {
				if (globalRow == -1) {
					updateSubmitButton();
				}
				else {
					updateUpdateButton();
				}
			}
		});
	}
	
	private void addMouseListenerTo(JComponent component){
		component.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent arg0) {
					validateNameAndDesc(true);
			}
		});
	}
	
	private void updateUpdateButton(){
		if(validateNameAndDesc(false) && updateValid()){
			updateAddReqButton.setEnabled(true);
		}
		else{
			updateAddReqButton.setEnabled(false);	
		}
	}
	
	private void updateSubmitButton(){
		if(validateNameAndDesc(false)){
			submitAddReqButton.setEnabled(true);
		}
		else{
			submitAddReqButton.setEnabled(false);
		}
	}
	
	/*
	 * Returns true if the current update is different from the stored value
	 */
	private boolean updateValid() {
		String updateName = nameArea.getText();
		String currentName = (String) currentTable.getValueAt(globalRow, 0);
		String updateDesc = descArea.getText();
		String currentDesc = (String) currentTable.getValueAt(globalRow, 1);
		return (!(currentName.equals(updateName))) || (!(currentDesc.equals(updateDesc)));
	}
	
}