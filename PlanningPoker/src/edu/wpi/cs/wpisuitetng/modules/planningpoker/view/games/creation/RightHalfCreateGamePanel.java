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
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jdesktop.swingx.JXDatePicker;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.controllers.RetrievePPRequirementController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.models.PPRequirement;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.pprequirement.models.PPRequirementModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirement.characteristics.RequirementStatus;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirement.controllers.GetRequirementsController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirement.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirement.models.RequirementModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.RequirementTable;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.RequirementTableMode;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.DescriptionJTextArea;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.ErrorLabel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.IDataField;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.IErrorView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.NameJTextField;

/**
 * The Right Half panel for the NewCreateGamePanel Used to import and add
 * requirements to the game
 */
public class RightHalfCreateGamePanel extends JScrollPane implements IDataField {
	private AddRequirementsPanel reqPanel; // initialize new add requirements
											// panel
	private AddReqImportReqPanel importPanel; // initialize the panel with the
												// buttons Add
												// Requirement" and "Import
												// Requirements"
	private CreateGamePanel parent; // initialize variable to hold panel above
									// this panel

	private List<PPRequirement> requirements = new ArrayList<PPRequirement>();

	private final Border defaultTextFieldBorder = (new JTextField())
			.getBorder();

	private final Border defaultTextAreaBorder = (new JTextArea()).getBorder();

	private final Border defaultPanelBorder = (new JPanel()).getBorder();

	private int globalRow = -1; // this Variable is used to keep track of when
								// the submit or update button is grayed out as
								// well as which row is being updated when the
								// edit button is pressed

	private final Border errorBorder = BorderFactory
			.createLineBorder(Color.RED);

	// THIS IS THE REQUIREMENT NAME FIELD THAT WILL BE NEEDED FOR CONTROLLER
	private NameJTextField nameArea = new NameJTextField(30);

	// THIS IS THE REQUIREMENT DESCRIPTION FIELD THAT WILL BE NEEDED FOR
	// CONTROLLER
	private DescriptionJTextArea descArea = new DescriptionJTextArea();

	private ErrorLabel errorLabel = new ErrorLabel();

	private ErrorLabel importErrorLabel = new ErrorLabel();

	private RequirementTable importTable;

	private JLabel createReqsLabel;
	private JLabel updateReqsLabel;

	private RequirementTable currentTable;

	private JPanel createReqsPanel = new JPanel();

	final JPanel currentReqsPanel = new JPanel();

	private JPanel importReqsPanel = new JPanel();

	private JButton addReqButton = new JButton("Create Requirement");

	private JButton editReqButton = new JButton("Edit");

	private JButton updateAddReqButton = new JButton("Update");

	private JButton submitAddReqButton = new JButton("Create");

	private JButton importReqButton = new JButton("Import Requirements");

	private JButton removeReqButton = new JButton("Remove");

	private JButton submitImportReqButton;

	private List<PPRequirement> savedRequirements = new ArrayList<PPRequirement>();

	private Game game;

	private JButton cancelImportReqButton;

	/**
	 * Builds the right half of the CreateGamePanel.
	 * 
	 * @param createGamePanel
	 */
	public RightHalfCreateGamePanel(CreateGamePanel createGamePanel) {
		parent = createGamePanel;
		game = parent.getGame();
		build();
		buildFields();

		if (game != null) {
			for (PPRequirement req : game.getRequirements()) {
				PPRequirement newReq = new PPRequirement();
				newReq.setName(req.getName());
				newReq.setDescription(req.getDescription());
				savedRequirements.add(newReq);
			}
		}
	}

	private void build() {
		Container rightView = new Container(); // Creates the container for
												// everything in the view
		SpringLayout layout = new SpringLayout(); // Creates the layout to be
													// used: Spring Layout
		rightView.setLayout(layout); // Sets the container to have the spring
										// layout

		currentTable = new RequirementTable(new ArrayList<PPRequirement>(),
				RequirementTableMode.CREATE);
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
		tablePanel2
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		currentReqsPanel.add(currentReqs);
		currentReqsPanel.add(tablePanel2);

		/**
		 * CODE FOR CREATE REQ PANEL
		 */
		SpringLayout createLayout = new SpringLayout();
		createReqsPanel.setLayout(createLayout);
		createReqsPanel.setBorder(defaultPanelBorder);

		// initializes and set up the Create Requirement Label
		createReqsLabel = new JLabel("Create Requirement");
		createReqsLabel.setFont(labelFont);
		createReqsPanel.add(createReqsLabel);

		// initializes and set up the update requirement label
		updateReqsLabel = new JLabel("Update Requirement");
		updateReqsLabel.setFont(labelFont);
		createReqsPanel.add(updateReqsLabel);

		// Initializes the Name label and Area and adds them to the
		// createReqPanel
		nameArea.setPreferredSize(new Dimension(75, 25));
		JLabel reqName = new JLabel("Requirement Name: *");
		createReqsPanel.add(reqName);
		createReqsPanel.add(nameArea);
		addKeyListenerTo(nameArea);

		// Adds error label to the createReqPanel
		createReqsPanel.add(errorLabel);
		validateNameAndDesc(true, false);

		// initializes the Desc Label and area and adds them to the createPanel
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
				globalRow = -1;
				submitAddReqButton.setEnabled(false);
				enableButtons();
			}
		});

		/**
		 * Add all components to the createReqsPanel and set it to not visible
		 * at first
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

		JLabel importReq = new JLabel("Import Requirements"); // Creates the
																// label import
																// requirement
		importReq.setFont(labelFont); // Sets the label font

		importTable = new RequirementTable(new ArrayList<PPRequirement>(),
				RequirementTableMode.ENDED);
		importTable.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent event) {
						int viewRow = importTable.getSelectedRow();
						if (viewRow < 0) {
							submitImportReqButton.setEnabled(false);
						} else {
							submitImportReqButton.setEnabled(true);
						}
						boolean duplicateName = false;
						boolean duplicateDesc = false;
						int[] rows = importTable.getSelectedRows();
						String selectedName = null;
						String selectedDesc = null;
						for (int i = 0; i < rows.length; i++) {
							selectedName = (String) importTable.getValueAt(
									rows[i], 0);
							selectedDesc = (String) importTable.getValueAt(
									rows[i], 1);
							for (int j = 0; j < requirements.size(); j++) {
								String reqName = requirements.get(j).getName();
								String reqDesc = requirements.get(j).getDescription();
								if (selectedName.equals(reqName)) {
									duplicateName = true;
								}
								if ( selectedDesc.equals(reqDesc)) {
									duplicateDesc = true;
								}
									
							}
						}
						
						if (duplicateName && duplicateDesc) {
							submitImportReqButton.setEnabled(false);
							importErrorLabel.setForeground(Color.RED);
							importErrorLabel
									.setText("The name and description of a selected requirement is already taken");
						} else {
							submitImportReqButton.setEnabled(true);
							importErrorLabel.setText("");
						}
					}
				});

		JScrollPane tablePanel = new JScrollPane(importTable);
		tablePanel
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		/**
		 * Creates a new button to import the requirements to the game
		 */
		submitImportReqButton = new JButton("Import");
		submitImportReqButton.setEnabled(false);
		submitImportReqButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				submitImportButtonPressed();
			}
		});

		/**
		 * Action listener for the cancel button in the import req panel
		 */

		cancelImportReqButton = new JButton("Cancel");
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
				enableButtons();
			}
		});

		/**
		 * Add all components to the importReqsPanel and make it not visible to
		 * start
		 */
		importReqsPanel.add(importReq);
		importReqsPanel.add(tablePanel);
		importReqsPanel.add(submitImportReqButton);
		importReqsPanel.add(cancelImportReqButton);
		importReqsPanel.add(importErrorLabel);

		importReqsPanel.setVisible(false);
		editReqButton.setEnabled(false);
		removeReqButton.setEnabled(false);

		/**
		 * Formats the buttons underneath the panels
		 */
		JButton testButton = new JButton("tttttttttttttttttt"); // Don't change
																// text, it
																// determines
																// button
																// width...
		Dimension buttonD = testButton.getPreferredSize();
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

		// IN THE CURRENT REQ PANEL
		// the tile with respect to the panel
		currentLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER,
				currentReqs, 5, SpringLayout.HORIZONTAL_CENTER,
				currentReqsPanel);

		// Table with respect to the title
		currentLayout.putConstraint(SpringLayout.NORTH, tablePanel2, 5,
				SpringLayout.SOUTH, currentReqs);

		// table with respect panel
		currentLayout.putConstraint(SpringLayout.WEST, tablePanel2, 5,
				SpringLayout.WEST, currentReqsPanel);
		currentLayout.putConstraint(SpringLayout.EAST, tablePanel2, -5,
				SpringLayout.EAST, currentReqsPanel);
		currentLayout.putConstraint(SpringLayout.SOUTH, tablePanel2, -5,
				SpringLayout.SOUTH, currentReqsPanel);

		// IN RIGHT VEIW CONTAINER
		// currentPanel with respect to the container
		layout.putConstraint(SpringLayout.NORTH, currentReqsPanel, 5,
				SpringLayout.NORTH, rightView);
		layout.putConstraint(SpringLayout.SOUTH, currentReqsPanel, -50,
				SpringLayout.SOUTH, rightView);
		layout.putConstraint(SpringLayout.WEST, currentReqsPanel, 5,
				SpringLayout.WEST, rightView);
		layout.putConstraint(SpringLayout.EAST, currentReqsPanel, -5,
				SpringLayout.EAST, rightView);

		// addreq button
		layout.putConstraint(SpringLayout.SOUTH, addReqButton, -5,
				SpringLayout.SOUTH, rightView);
		layout.putConstraint(SpringLayout.SOUTH, currentReqsPanel, -5,
				SpringLayout.NORTH, addReqButton);
		layout.putConstraint(SpringLayout.WEST, addReqButton, 10,
				SpringLayout.WEST, rightView);

		// importreq button
		layout.putConstraint(SpringLayout.SOUTH, importReqButton, -5,
				SpringLayout.SOUTH, rightView);
		layout.putConstraint(SpringLayout.SOUTH, currentReqsPanel, -5,
				SpringLayout.NORTH, importReqButton);
		layout.putConstraint(SpringLayout.WEST, importReqButton, 5,
				SpringLayout.EAST, addReqButton);

		// removereq button
		layout.putConstraint(SpringLayout.SOUTH, removeReqButton, -5,
				SpringLayout.SOUTH, rightView);
		layout.putConstraint(SpringLayout.SOUTH, currentReqsPanel, -5,
				SpringLayout.NORTH, removeReqButton);
		layout.putConstraint(SpringLayout.EAST, removeReqButton, -10,
				SpringLayout.EAST, rightView);

		// currentPanel with respect to the button
		layout.putConstraint(SpringLayout.SOUTH, addReqButton, -5,
				SpringLayout.SOUTH, rightView);
		layout.putConstraint(SpringLayout.SOUTH, currentReqsPanel, -5,
				SpringLayout.NORTH, addReqButton);
		layout.putConstraint(SpringLayout.SOUTH, createReqsPanel, -5,
				SpringLayout.NORTH, addReqButton);

		// edit req button
		layout.putConstraint(SpringLayout.SOUTH, editReqButton, -5,
				SpringLayout.SOUTH, rightView);
		layout.putConstraint(SpringLayout.EAST, editReqButton, -5,
				SpringLayout.WEST, removeReqButton);

		// CREATE REQS PANEL
		// createPanel with respect to the container
		layout.putConstraint(SpringLayout.NORTH, createReqsPanel, 5,
				SpringLayout.NORTH, rightView);
		layout.putConstraint(SpringLayout.SOUTH, createReqsPanel, -50,
				SpringLayout.SOUTH, rightView);
		layout.putConstraint(SpringLayout.WEST, createReqsPanel, 5,
				SpringLayout.WEST, rightView);
		layout.putConstraint(SpringLayout.EAST, createReqsPanel, -5,
				SpringLayout.EAST, rightView);

		// create Reqs label
		createLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER,
				createReqsLabel, 5, SpringLayout.HORIZONTAL_CENTER,
				createReqsPanel);
		createLayout.putConstraint(SpringLayout.NORTH, createReqsLabel, 5,
				SpringLayout.NORTH, parent);

		// update Reqs Label/
		createLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER,
				updateReqsLabel, 5, SpringLayout.HORIZONTAL_CENTER,
				createReqsPanel);
		createLayout.putConstraint(SpringLayout.NORTH, updateReqsLabel, 5,
				SpringLayout.NORTH, parent);

		// req name label with respect to create panel
		createLayout.putConstraint(SpringLayout.WEST, reqName, 5,
				SpringLayout.WEST, createReqsPanel);
		createLayout.putConstraint(SpringLayout.NORTH, reqName, 5,
				SpringLayout.SOUTH, createReqsLabel);

		// req name label with respect to the text box
		createLayout.putConstraint(SpringLayout.NORTH, nameArea, 5,
				SpringLayout.SOUTH, reqName);

		// name text box with respect to the create panel
		createLayout.putConstraint(SpringLayout.WEST, nameArea, 5,
				SpringLayout.WEST, createReqsPanel);
		createLayout.putConstraint(SpringLayout.EAST, nameArea, -5,
				SpringLayout.EAST, createReqsPanel);

		// desc label with respect to name area
		createLayout.putConstraint(SpringLayout.NORTH, reqDesc, 5,
				SpringLayout.SOUTH, nameArea);

		// desc label with respect to create panel
		createLayout.putConstraint(SpringLayout.WEST, reqDesc, 5,
				SpringLayout.WEST, createReqsPanel);

		// desc label with respect to the desc text box
		createLayout.putConstraint(SpringLayout.NORTH, descPane, 5,
				SpringLayout.SOUTH, reqDesc);

		// desc text box with respect to the create panel
		createLayout.putConstraint(SpringLayout.WEST, descPane, 5,
				SpringLayout.WEST, createReqsPanel);
		createLayout.putConstraint(SpringLayout.EAST, descPane, -5,
				SpringLayout.EAST, createReqsPanel);

		// position submit button with respect to createReqPanel
		createLayout.putConstraint(SpringLayout.SOUTH, submitAddReqButton, -18,
				SpringLayout.SOUTH, createReqsPanel);
		createLayout.putConstraint(SpringLayout.WEST, submitAddReqButton, 5,
				SpringLayout.WEST, createReqsPanel);

		// position update button with respect to createReqPanel
		createLayout.putConstraint(SpringLayout.SOUTH, updateAddReqButton, -18,
				SpringLayout.SOUTH, createReqsPanel);
		createLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER,
				updateAddReqButton, 5, SpringLayout.HORIZONTAL_CENTER,
				createReqsPanel);

		// position cancel button with respect to createReqPanel
		createLayout.putConstraint(SpringLayout.SOUTH, cancelRequirementButton,
				-18, SpringLayout.SOUTH, createReqsPanel);
		createLayout.putConstraint(SpringLayout.EAST, cancelRequirementButton,
				-5, SpringLayout.EAST, createReqsPanel);

		// postion error label with respect to creaReqPanel
		createLayout.putConstraint(SpringLayout.SOUTH, errorLabel, 0,
				SpringLayout.SOUTH, createReqsPanel);
		createLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, errorLabel,
				1, SpringLayout.HORIZONTAL_CENTER, createReqsPanel);

		// anchor descPane to the top of the submit button
		createLayout.putConstraint(SpringLayout.SOUTH, descPane, -8,
				SpringLayout.NORTH, submitAddReqButton);

		// IMPORT REQS PANEL
		layout.putConstraint(SpringLayout.NORTH, importReqsPanel, 5,
				SpringLayout.NORTH, rightView);
		layout.putConstraint(SpringLayout.SOUTH, importReqsPanel, -36,
				SpringLayout.SOUTH, rightView);
		layout.putConstraint(SpringLayout.WEST, importReqsPanel, 5,
				SpringLayout.WEST, rightView);
		layout.putConstraint(SpringLayout.EAST, importReqsPanel, -5,
				SpringLayout.EAST, rightView);

		importLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, importReq,
				5, SpringLayout.HORIZONTAL_CENTER, importReqsPanel);
		importLayout.putConstraint(SpringLayout.NORTH, importReq, 5,
				SpringLayout.NORTH, importReqsPanel);

		importLayout.putConstraint(SpringLayout.NORTH, tablePanel, 5,
				SpringLayout.SOUTH, importReq);
		importLayout.putConstraint(SpringLayout.WEST, tablePanel, 5,
				SpringLayout.WEST, importReqsPanel);
		importLayout.putConstraint(SpringLayout.EAST, tablePanel, -5,
				SpringLayout.EAST, importReqsPanel);
		importLayout.putConstraint(SpringLayout.SOUTH, tablePanel, 5,
				SpringLayout.NORTH, submitImportReqButton);

		importLayout.putConstraint(SpringLayout.SOUTH, submitImportReqButton,
				-5, SpringLayout.SOUTH, importReqsPanel);
		importLayout.putConstraint(SpringLayout.WEST, submitImportReqButton, 5,
				SpringLayout.WEST, importReqsPanel);

		importLayout.putConstraint(SpringLayout.SOUTH, cancelImportReqButton,
				-5, SpringLayout.SOUTH, importReqsPanel);
		importLayout.putConstraint(SpringLayout.EAST, cancelImportReqButton,
				-5, SpringLayout.EAST, importReqsPanel);

		importLayout.putConstraint(SpringLayout.NORTH, importErrorLabel, 2,
				SpringLayout.NORTH, submitImportReqButton);
		importLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER,
				importErrorLabel, 0, SpringLayout.HORIZONTAL_CENTER,
				importReqsPanel);

		importLayout.putConstraint(SpringLayout.SOUTH, tablePanel, -20,
				SpringLayout.NORTH, submitImportReqButton);

		
		validateNameAndDesc(false, true);
		/**
		 * Set the minimum size and add components to the viewport of the
		 * scrollpane
		 */
		setMinimumSize(new Dimension(350, 350));
		rightView.setPreferredSize(new Dimension(550, 350));
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
		 * action listener for the edit button inside the view for updating
		 * requirements
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
				updateAddReqButton.setEnabled(false);
				updateReqsLabel.setVisible(false);
				createReqsLabel.setVisible(true);
				nameArea.requestFocus();
				nameArea.select(0, 0);
				displayError("Name is required");
				disableButtons();
			}
		});

		/**
		 * Action listener for the import requirement button
		 */
		importReqButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Add requirements to table
				updateImportTable();
				currentReqsPanel.setVisible(false);
				createReqsPanel.setVisible(false);
				importReqsPanel.setVisible(true);
				addReqButton.setEnabled(false);
				importReqButton.setEnabled(false);
				removeReqButton.setEnabled(false);
				submitImportReqButton.setEnabled(false);
				disableButtons();
			}
		});

		/**
		 * Add action listener to the remove button
		 */
		removeReqButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeRequirement();
			}
		});

		currentTable.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent event) {
						if (currentTable.getSelectedRowCount() == 0) {
							editReqButton.setEnabled(false);
							removeReqButton.setEnabled(false);
						} else if (currentTable.getSelectedRowCount() > 0) {
							editReqButton.setEnabled(true);
							removeReqButton.setEnabled(true);
						} else {
							editReqButton.setEnabled(true);
							removeReqButton.setEnabled(true);
						}
						int[] rows = currentTable.getSelectedRows();
						String selectedName = null;
						boolean hasImported = false;
						for (int i = 0; i < rows.length; i++) {
							selectedName = (String) currentTable.getValueAt(
									rows[i], 0);
							// if
							// (PPRequirementModel.getInstance().getRequirement(selectedName)
							// != null) { //TODO this line always returns true.
							// fix it if you wrote it.
							// hasImported = true;
							// }
						}
						if (hasImported) {
							System.out
									.println("unexpected edit button disable 2");
							editReqButton.setEnabled(false);
						}

					}
				});

	}

	/**
	 * Creates a new font for use later
	 */
	public Font makeFont() {
		// create a dummy JTextArea
		JTextArea editingArea = new JTextArea();
		// get the current font
		Font f = editingArea.getFont();
		// create a new, larger font from the current font
		Font newFont = new Font(f.getFontName(), f.getStyle(), f.getSize() + 8);
		// set the bigger font for userStoryDesc
		Font bigFont = newFont;
		return bigFont;
	}

	private void updateImportTable() {
		// Clear Table
		while (importTable.getTableModel().getRowCount() > 0) {
			importTable.getTableModel().removeRow(0);
		}

		// Add the Requirements from the Requirement Manager to the model
		RetrievePPRequirementController.getInstance().retrieveRequirements();
		GetRequirementsController.getInstance().retrieveRequirements();

		// Sleep the thread for a little bit to ensure that
		// the requirements get added to the model before
		// this continues
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Add the imported requirements to the table
		for (Requirement r : RequirementModel.getInstance().getRequirements()) {
			if (r.getEstimate() == 0) {
				// Can't import deleted requirements
				if (!r.getStatus().equals(RequirementStatus.DELETED)) {
					// Don't allow duplicate requirements in table
					if (!containsReq(r)) {
						importTable.getTableModel().addRow(
								new Object[] { r.getName(), r.getDescription() });
					}
				}
			}
		}

		if (importTable.getRowCount() == 0) {
			submitImportReqButton.setEnabled(false);
			importErrorLabel
					.setText("No requirements to be imported. Please click cancel.");
			importErrorLabel.setForeground(Color.red);
		} else {
			importErrorLabel.setText("");
		}
	}

	private void submitButtonPressed() {
		// Get requirements from manager
		GetRequirementsController.getInstance().retrieveRequirements();
		// Sleep to make sure retrieve finishes
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// Check to see if requirement exists in manager
		for (Requirement r : RequirementModel.getInstance().getRequirements()) {
			if (r.getName().equals(nameArea.getText())
					&& r.getDescription().equals(descArea.getText())) {
				// TODO: error message not working on this branch
				displayError("Requirement already exists in Requirement Manager.");
				return;
			}
		}
		// Submit requirement
		if (validateNameAndDesc(true, true)) {
			addRequirement(new PPRequirement(nameArea.getText(),
					descArea.getText()));
			nameArea.setText("");
			descArea.setText("");
			createReqsPanel.setVisible(false);
			currentReqsPanel.setVisible(true);
			enableButtons();
			submitAddReqButton.setEnabled(false);
			addReqButton.setEnabled(true);
			importReqButton.setEnabled(true);
			globalRow = -1;
			parent.updateButtons();
			displayError("");
		}
	}

	private boolean containsReq(Requirement req) {
		for (PPRequirement ppr : requirements) {
			if (ppr.getName().equals(req.getName())
					&& ppr.getDescription().equals(req.getDescription())) {
				return true;
			}
		}
		return false;
	}

	private void updateButtonPressed() {
		if (globalRow != -1) {
			int[] rows = currentTable.getSelectedRows();
			for (int i = 0; i < requirements.size(); i++) {
				if (requirements.get(i).getName()
						.equals(currentTable.getValueAt(rows[0], 0))) {
					System.out.println(requirements.get(i).getName());
					requirements.get(i).setName(nameArea.getText());
					requirements.get(i).setDescription(descArea.getText());
				}
			}

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
		if (currentTable.getSelectedRowCount() != 0) {
			editReqButton.setEnabled(true);
			removeReqButton.setEnabled(true);
		}
	}

	private void disableButtons() {
		addReqButton.setEnabled(false);
		importReqButton.setEnabled(false);
		removeReqButton.setEnabled(false);
		System.out.println("Unexpected edit disable 3");
		editReqButton.setEnabled(false);
	}

	private void submitImportButtonPressed() {
		int[] rows = importTable.getSelectedRows();
		for (int i = 0; i < rows.length; i++) {
			String selectedName = (String) importTable.getValueAt(rows[i], 0);
			String selectedDesc = (String) importTable.getValueAt(rows[i], 1);
			addRequirement(PPRequirementModel.getInstance().getRequirement(
					selectedName, selectedDesc));
		}

		parent.updateButtons();
		createReqsPanel.setVisible(false);
		currentReqsPanel.setVisible(true);
		importReqsPanel.setVisible(false);
		addReqButton.setEnabled(true);
		importReqButton.setEnabled(true);
		removeReqButton.setEnabled(true);
		enableButtons();
	}

	private boolean validateNameAndDesc(boolean showLabel, boolean showBox) {
		boolean descriptionValid = true;
		boolean nameValid = true;
		boolean uniqueName;
		boolean returnBoolean;

		if (checkduplicateReq(new PPRequirement(nameArea.getText(),
				descArea.getText()))) {
			uniqueName = false;
			displayError("A requirement already exists with that name and description");
		} else {
			displayError("");
			uniqueName = true;
		}

		if (descArea.getText().isEmpty()) {
			if (showLabel) {
				displayError("Description is required");
			}
			if (showBox) {
				descArea.setBorder(errorBorder);
			}
			descriptionValid = false;
			System.out.println(descriptionValid + "descValid");
		}

		if (nameArea.getText().isEmpty()) {
			if (showLabel) {
				displayError("Name is required");
			}
			if (showBox) {
				nameArea.setBorder(errorBorder);
			}
			nameValid = false;
		}

		if (!nameValid && !descriptionValid) {
			displayError("Name and Description are required");
		}
		nameValid = nameArea.validateField(errorLabel, showLabel, showBox);

		returnBoolean = nameValid && descriptionValid && uniqueName;
		System.out.println("Return boolean:" + returnBoolean);

		if (returnBoolean) {
			errorLabel.setText("");
		}

		if (!showLabel) {
			errorLabel.setText("");
			// nameArea.setBorder(defaultTextFieldBorder);
			// descArea.setBorder(defaultTextAreaBorder);
		}

		return returnBoolean;
	}

	private void displayError(String errorString) {
		errorLabel.setForeground(Color.RED);
		errorLabel.setText(errorString);
		revalidate();
		repaint();
	}

	/**
	 * Sets the fields for this panel
	 */
	public void buildFields() {
		if (parent.getGame() != null) {
			for (PPRequirement r : parent.getGame().getRequirements()) {
				addRequirement(r);
			}
		}
	}

	public List<PPRequirement> getRequirements() {
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
		displayError("No changes have been made");
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

	/**
	 * Adds a requirement to the game
	 * 
	 * @param requirement
	 *            The requirement to be added to the game
	 */
	public void addRequirement(PPRequirement requirement) {
		if (!checkduplicateReq(requirement)) {
			currentTable.getTableModel().addRow(
					new Object[] { requirement.getName(),
							requirement.getDescription() });
			requirements.add(requirement);
		} else {
			displayError("Duplicate Requirement Added");
			errorLabel.setVisible(true); // TODO
			// errorLabel.setVisible(false);
		}
	}

	private void removeRequirement() {
		if (currentTable.getSelectedRowCount() != 0) {
			while (currentTable.getSelectedRowCount() > 0) {
				int[] rows = currentTable.getSelectedRows();
				// Remove requirement from requirements list
				for (int i = 0; i < requirements.size(); i++) {
					if (requirements.get(i).getName()
							.equals(currentTable.getValueAt(rows[0], 0))) {
						requirements.remove(requirements.get(i));
					}
				}
				currentTable.getTableModel().removeRow(rows[0]);
			}
			if (currentTable.getTableModel().getRowCount() == 0) {
				removeReqButton.setEnabled(false);
				System.out.println("Unexpected edit disable 1");
				editReqButton.setEnabled(false);
			}
			parent.updateButtons();
		}
	}

	/**
	 * @param requirement
	 * @return true if the requirement is already in the table
	 */
	private boolean checkduplicateReq(PPRequirement requirement) {
		for (PPRequirement ppr: requirements) {
			if (ppr.getName().equals(requirement.getName()) && ppr.getDescription().equals(requirement.getDescription())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean validateField(IErrorView warningField, boolean showLabel,
			boolean showBox) {
		parent.getLeftHalf().getBoxName().setBorder(defaultTextFieldBorder);
		parent.getLeftHalf().getBoxDescription()
				.setBorder(defaultTextAreaBorder);
		parent.getLeftHalf().getEndDateField()
				.setBorder((new JXDatePicker()).getBorder());

		if (requirements.size() <= 0) {
			if (warningField != null) {
				if (showLabel) {
					warningField.setText("At least one requirement is needed");
				}
				if (showBox) {
					currentReqsPanel.setBorder(errorBorder);
				}
			}
			return false;
		} else {
			currentReqsPanel.setBorder(defaultPanelBorder);
			warningField.setText("");
			return true;
		}
	}

	@Override
	public boolean hasChanges() {
		return reqsHasChanges() || nameArea.hasChanges()
				|| descArea.hasChanges();
	}

	private boolean reqsHasChanges() {
		if (savedRequirements.size() != requirements.size()) {
			return true;
		} else {
			for (int i = 0; i < requirements.size(); i++) {
				boolean nameChanged = !requirements.get(i).getName()
						.equals(savedRequirements.get(i).getName());
				boolean descChanged = !requirements.get(i).getDescription()
						.equals(savedRequirements.get(i).getDescription());
				if (nameChanged || descChanged) {
					return true;
				}
			}
		}
		return false;
	}

	private void addKeyListenerTo(JComponent component) {
		component.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent arg0) {
				if (globalRow == -1) {
					updateSubmitButton();
				} else {
					System.out.println("updating update");
					updateUpdateButton();
				}
			}
		});
	}

	private void addMouseListenerTo(JComponent component) {
		component.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				validateNameAndDesc(true, true);
			}
		});
	}

	private void updateUpdateButton() {
		if (validateNameAndDesc(true, false) && updateValid()) {
			updateAddReqButton.setEnabled(true);
			displayError("");
		} else if (!updateValid()) {
			updateAddReqButton.setEnabled(false);
			displayError("No changes have been made");
		} else {
			updateAddReqButton.setEnabled(false);
			displayError("A requirement already exists with that name and description");
		}
	}

	private void updateSubmitButton() {
		if (validateNameAndDesc(true, true)) {
			submitAddReqButton.setEnabled(true);
		} else {
			submitAddReqButton.setEnabled(false);
		}
	}

	/**
	 * @return true if the current update is different from the stored value
	 */
	private boolean updateValid() {
		String updateName = nameArea.getText();
		String currentName = (String) currentTable.getValueAt(globalRow, 0);
		String updateDesc = descArea.getText();
		String currentDesc = (String) currentTable.getValueAt(globalRow, 1);
		return (!(currentName.equals(updateName)))|| (!(currentDesc.equals(updateDesc)));
	}

	public boolean isNameAreaEmpty() {
		return nameArea.getText().equals("");
	}

	public boolean isDescAreaEmpty() {
		return descArea.getText().equals("");
	}

	/**
	 * Getter for testing purposes
	 * 
	 * @return importReqButton
	 */
	public JButton getImportReqButton() {
		return importReqButton;
	}

	public JButton getRemoveReqButton() {
		return removeReqButton;
	}

	public JButton getAddReqButton() {
		return addReqButton;
	}

	public JButton getCancelImportReqButton() {
		return cancelImportReqButton;
	}

}
