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
    
    private final Border errorBorder = BorderFactory
			.createLineBorder(Color.RED);

    //THIS IS THE REQUIREMENT NAME FIELD THAT WILL BE NEEDED FOR CONTROLLER
	private JTextField nameArea = new NameJTextField(30);	
		
	//THIS IS THE REQUIREMENT DESCRIPTION FIELD THAT WILL BE NEEDED FOR CONTROLLER
	private JTextArea descArea = new JTextArea();
    
    private JLabel errorLabel = new JLabel();
    
    private ImportGamesTable importTable;
	
    private ActiveGamesTable currentTable;
   
    private JPanel createReqsPanel = new JPanel();
    
    final JPanel currentReqsPanel = new JPanel();

    private JPanel importReqsPanel = new JPanel();
    
    private JButton addReqButton = new JButton("Add Requirement");
    
    private JButton removeReqButton = new JButton("Remove Requirement");
    
    private JButton submitAddReqButton = new JButton("Submit");

    private JButton importReqButton = new JButton("Import Requirement");
   
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
        JLabel createReqsLabel = new JLabel("Create Requirements");
        createReqsLabel.setFont(labelFont);
        createReqsPanel.add(createReqsLabel);
   
        
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
		 * add submit and cancel button to createReqsPanel
		 */

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
			}
		});
		
		createReqsPanel.add(submitAddReqButton);
		createReqsPanel.add(cancelRequirementButton);
		
		createReqsPanel.setVisible(false);
       
		
		/**
         * CODE FOR IMPORT REQ PANEL
         */
        SpringLayout importLayout = new SpringLayout();
        importReqsPanel.setLayout(importLayout);
		
		JLabel importReq = new JLabel("Import Requirement");	//Creates the label import requirement
		importReq.setFont(labelFont);							//Sets the label font
		
		JScrollPane tablePanel = new JScrollPane(importTable);
        tablePanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);        
		
        /**
		 * Creates a new button to add the requirements to the game
		 */
		JButton submitImportReqButton = new JButton("Submit");
		submitImportReqButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				submitImportButtonPressed();
			}
		});
		
		/*submitImportReqButton.addActionListener(new ActionListener() {
			 @Override
			public void actionPerformed(ActionEvent e) {
				submitButtonPressed();
			 }
		});*/
		
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
			}
		});
		
		removeReqButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int[] rows = currentTable.getSelectedRows();
				for (int i = 0; i < rows.length; i++) {
					currentTable.getTableModel().removeRow(rows[i]);
				}
			}
		});
		
		importReqsPanel.add(importReq);
		importReqsPanel.add(tablePanel);
		importReqsPanel.add(submitImportReqButton);
		importReqsPanel.add(cancelImportReqButton);
		
		importReqsPanel.setVisible(false);
		
        rightView.add(currentReqsPanel);
        rightView.add(createReqsPanel);
        rightView.add(importReqsPanel);
        rightView.add(addReqButton);
        rightView.add(removeReqButton);
        rightView.add(importReqButton);
		
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
        // currentPanel with respect to the button 
        layout.putConstraint(SpringLayout.SOUTH, addReqButton, -5, SpringLayout.SOUTH, rightView);
        layout.putConstraint(SpringLayout.SOUTH, currentReqsPanel, -5, SpringLayout.NORTH, addReqButton);
        
        //remove req button //TODO
        layout.putConstraint(SpringLayout.SOUTH, removeReqButton, -5, SpringLayout.SOUTH, rightView);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, removeReqButton, 0, SpringLayout.HORIZONTAL_CENTER, rightView);
        // button with respect to container
        layout.putConstraint(SpringLayout.WEST, addReqButton, 5, SpringLayout.WEST, rightView);
        
        
        layout.putConstraint(SpringLayout.SOUTH, importReqButton, -5, SpringLayout.SOUTH, rightView);
        layout.putConstraint(SpringLayout.SOUTH, currentReqsPanel, -5, SpringLayout.NORTH, importReqButton);
        // button with respect to container
        layout.putConstraint(SpringLayout.EAST, importReqButton, -5, SpringLayout.EAST, rightView);
        
        
        //CREATE REQS PANEL
        //createPanel with respect to the container 
        layout.putConstraint(SpringLayout.NORTH, createReqsPanel, 5, SpringLayout.NORTH, rightView);
        layout.putConstraint(SpringLayout.SOUTH, createReqsPanel, -50, SpringLayout.SOUTH, rightView);
        layout.putConstraint(SpringLayout.WEST, createReqsPanel, 5, SpringLayout.WEST, rightView);
        layout.putConstraint(SpringLayout.EAST, createReqsPanel, -5, SpringLayout.EAST, rightView);
        // createPanel with respect to the button 
        layout.putConstraint(SpringLayout.SOUTH, addReqButton, -5, SpringLayout.SOUTH, rightView);
        layout.putConstraint(SpringLayout.SOUTH, createReqsPanel, -5, SpringLayout.NORTH, addReqButton);
        
        layout.putConstraint(SpringLayout.SOUTH, importReqButton, -5, SpringLayout.SOUTH, rightView);
        layout.putConstraint(SpringLayout.SOUTH, createReqsPanel, -5, SpringLayout.NORTH, importReqButton);
        
        // the tile with respect to the panel
        createLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, createReqsLabel, 5, SpringLayout.HORIZONTAL_CENTER, createReqsPanel);
        // Table with respect to the title
        createLayout.putConstraint(SpringLayout.NORTH, createReqsLabel, 5, SpringLayout.NORTH, parent);
        
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
       
        //postion error label with respect to creaReqPanel
        createLayout.putConstraint(SpringLayout.SOUTH, errorLabel, -1, SpringLayout.SOUTH, createReqsPanel);
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
        
        setMinimumSize(new Dimension(350, 350));
        getViewport().add(rightView);
        
//<<<<<<< HEAD
//		addReqButton.addActionListener(new ActionListener() {
//		 @Override
//		public void actionPerformed(ActionEvent e) {
//			 /*view.addRequirement(new Requirement(nameArea.getText(), descArea.getText()));
//			 nameArea.setText("");
//			 descArea.setText("");*/
//			 
//			 //set everything on create reqPanel to visible.setVisible(true);
//			 
//			//reqPanel.getImportReqPanel().setVisible(true); //TODO Not applicable at time of coding 
//			 currentReqsPanel.setVisible(false);
//			 createReqsPanel.setVisible(true);
//			 importReqsPanel.setVisible(false);
//		 }
//		 
//		 
//	});
//=======
		addReqButton.addActionListener(new ActionListener() {    
			@Override
			public void actionPerformed(ActionEvent e) {
				currentReqsPanel.setVisible(false);
				createReqsPanel.setVisible(true);
				importReqsPanel.setVisible(false);
				addReqButton.setEnabled(false);
				removeReqButton.setEnabled(false);
				importReqButton.setEnabled(false);
			}
		});
//>>>>>>> 746f88387ad67fcb7806f108589cf4f8c5e6aacf
		
		importReqButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {			 
				currentReqsPanel.setVisible(false);
				createReqsPanel.setVisible(false);
				importReqsPanel.setVisible(true);
				addReqButton.setEnabled(false);
				removeReqButton.setEnabled(false);
				importReqButton.setEnabled(false);
			} 
		});
		
		//THIS ACTION LISTENER NEEDS FIXING!!!!!!!!
		//RIGHT NOW, IT AUTOMATICALLY ADDS EACH ROW YOU 
		importTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (importTable.getSelectedRowCount() > 1){}
				if (e.getClickCount() == 1) {
					JTable target = (JTable) e.getSource();
					int row = target.getSelectedRow();
					String selectedName = (String) target.getValueAt(row, 0);
					String selectedDesc = (String) target.getValueAt(row, 1);
					currentTable.getTableModel().addRow(new Object[]{selectedName, selectedDesc});
				}
				if (e.getClickCount() > 1) {
					JTable target = (JTable) e.getSource();
					int[] rows = target.getSelectedRows();
					for (int i = 0; i < rows.length; i++){
						String selectedName = (String) target.getValueAt(rows[i], 0);
						String selectedDesc = (String) target.getValueAt(rows[i], 1);
						currentTable.getTableModel().addRow(new Object[]{selectedName, selectedDesc});
				
					}
				}
				else {}
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
		table.getColumnModel().getColumn(0).setMinWidth(100);
		table.getColumnModel().getColumn(0).setMaxWidth(400);
		table.getColumnModel().getColumn(1).setMinWidth(100);
		table.getColumnModel().getColumn(1).setMaxWidth(800);
		return table;
	}
	
	private ImportGamesTable initializeImportTable() {
		String[] columnNames2 = {"Requirement", "Description"};
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
			submitAddReqButton.setEnabled(false);
			addReqButton.setEnabled(true);
			importReqButton.setEnabled(true);
			removeReqButton.setEnabled(true);
			parent.updateButtons();
		}
	}
	
	private void submitImportButtonPressed(){
		/*if(IS_A_REQUIREMENT_SELECTED()){
			addRequirement(SELECTED_REQUIREMENT(S));
			createReqsPanel.setVisible(false);
			currentReqsPanel.setVisible(true);
			importReqsPanel.setVisible(false);
			addReqButton.setEnabled(true);
			importReqButton.setEnabled(true);
		}*/
		createReqsPanel.setVisible(false);
		currentReqsPanel.setVisible(true);
		importReqsPanel.setVisible(false);
		addReqButton.setEnabled(true);
		importReqButton.setEnabled(true);
		removeReqButton.setEnabled(true);
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
				updateSubmitButton();
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
	
	
	private void updateSubmitButton(){
		if(validateNameAndDesc(false)){
			submitAddReqButton.setEnabled(true);
		}
		else{
			submitAddReqButton.setEnabled(false);
		}
	}
}