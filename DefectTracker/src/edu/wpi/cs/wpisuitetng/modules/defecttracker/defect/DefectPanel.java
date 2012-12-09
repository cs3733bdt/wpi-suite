package edu.wpi.cs.wpisuitetng.modules.defecttracker.defect;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.Border;
import javax.swing.text.JTextComponent;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.defecttracker.models.Defect;
import edu.wpi.cs.wpisuitetng.modules.defecttracker.models.Tag;

/**
 * Panel to display the fields of a Defect and allow editing
 */
@SuppressWarnings("serial")
public class DefectPanel extends JPanel {
	protected Defect model;

	protected JTextField txtTitle;
	protected JTextArea txtDescription;
	protected JTextField txtCreator;
	protected JTextField txtAssignee;
	protected TagPanel tagPanel;
	
	protected final TextUpdateListener txtTitleListener;
	protected final TextUpdateListener txtDescriptionListener;
	protected final TextUpdateListener txtCreatorListener;
	protected final TextUpdateListener txtAssigneeListener;

	protected static final int HORIZONTAL_PADDING = 5;
	protected static final int VERTICAL_PADDING = 15;
	protected static final int LABEL_ALIGNMENT = JLabel.TRAILING;

	/**
	 * Construct a DefectPanel for creating a defect
	 */
	public DefectPanel() {
		this(new Defect());
	}

	/**
	 * Construct a DefectPanel for viewing a defect
	 * 
	 * @param defect	The Defect to show.
	 */
	public DefectPanel(Defect defect) {

		this.model = defect;

		SpringLayout layout = new SpringLayout();
		this.setLayout(layout);

		addComponents(layout);
		
		// Populate the fields based on the given model
		txtTitle.setText(defect.getTitle());
		txtDescription.setText(defect.getDescription());
		if (defect.getCreator() != null) {
			txtCreator.setText(defect.getCreator().getUsername());
		}
		if (defect.getAssignee() != null) {
			txtAssignee.setText(defect.getAssignee().getUsername());
		}
		if (defect.getTags() != null) {
			Iterator<Tag> tagsI = defect.getTags().iterator();
			Tag nextTag;
			while (tagsI.hasNext()) {
				nextTag = tagsI.next();
				tagPanel.lmTags.addElement(nextTag.getName());
			}
		}
		
		// Add TextUpdateListeners
		txtTitleListener = new TextUpdateListener(this, txtTitle);
		txtTitle.addKeyListener(txtTitleListener);
		
		txtDescriptionListener = new TextUpdateListener(this, txtDescription);
		txtDescription.addKeyListener(txtDescriptionListener);
		
		txtCreatorListener = new TextUpdateListener(this, txtCreator);
		txtCreator.addKeyListener(txtCreatorListener);
		
		txtAssigneeListener = new TextUpdateListener(this, txtAssignee);
		txtAssignee.addKeyListener(txtAssigneeListener);
	}

	/**
	 * Adds the components to the panel and places constraints on them
	 * for the SpringLayout manager.
	 * @param layout the layout manager
	 */
	protected void addComponents(SpringLayout layout) {
		txtTitle = new JTextField(50);
		txtDescription = new JTextArea();
		txtDescription.setLineWrap(true);
		txtDescription.setBorder(txtTitle.getBorder());
		txtCreator = new JTextField(20);
		txtAssignee = new JTextField(20);
		tagPanel = new TagPanel(model);
		
		// set component names
		txtTitle.setName("Title");
		txtDescription.setName("Description");
		txtCreator.setName("Creator");
		txtAssignee.setName("Assignee");

		JLabel lblTitle = new JLabel("Title:", LABEL_ALIGNMENT);
		JLabel lblDescription = new JLabel("Description:", LABEL_ALIGNMENT);
		JLabel lblCreator = new JLabel("Creator:", LABEL_ALIGNMENT);
		JLabel lblAssignee = new JLabel("Assignee:", LABEL_ALIGNMENT);

		int labelWidth = lblDescription.getPreferredSize().width;

		layout.putConstraint(SpringLayout.NORTH, lblTitle, VERTICAL_PADDING, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, lblTitle, 15, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, lblTitle, labelWidth, SpringLayout.WEST, lblTitle);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, txtTitle, 0, SpringLayout.VERTICAL_CENTER, lblTitle);
		layout.putConstraint(SpringLayout.WEST, txtTitle, HORIZONTAL_PADDING, SpringLayout.EAST, lblTitle);
		layout.putConstraint(SpringLayout.EAST, txtTitle, txtTitle.getPreferredSize().width, SpringLayout.WEST, txtTitle);

		layout.putConstraint(SpringLayout.NORTH, txtDescription, VERTICAL_PADDING, SpringLayout.SOUTH, txtTitle);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, lblDescription, 0, SpringLayout.VERTICAL_CENTER, txtDescription);
		layout.putConstraint(SpringLayout.WEST, lblDescription, 0, SpringLayout.WEST, lblTitle);
		layout.putConstraint(SpringLayout.EAST, lblDescription, labelWidth, SpringLayout.WEST, lblDescription);
		layout.putConstraint(SpringLayout.WEST, txtDescription, HORIZONTAL_PADDING, SpringLayout.EAST, lblDescription);
		layout.putConstraint(SpringLayout.EAST, txtDescription, 0, SpringLayout.EAST, txtTitle);
		layout.putConstraint(SpringLayout.SOUTH, txtDescription, txtTitle.getPreferredSize().height * 4, SpringLayout.NORTH, txtDescription);

		layout.putConstraint(SpringLayout.NORTH, txtCreator, VERTICAL_PADDING, SpringLayout.SOUTH, txtDescription);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, lblCreator, 0, SpringLayout.VERTICAL_CENTER, txtCreator);
		layout.putConstraint(SpringLayout.WEST, lblCreator, 0, SpringLayout.WEST, lblTitle);
		layout.putConstraint(SpringLayout.EAST, lblCreator, labelWidth, SpringLayout.WEST, lblCreator);
		layout.putConstraint(SpringLayout.WEST, txtCreator, HORIZONTAL_PADDING, SpringLayout.EAST, lblCreator);

		layout.putConstraint(SpringLayout.NORTH, txtAssignee, VERTICAL_PADDING, SpringLayout.SOUTH, txtCreator);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, lblAssignee, 0, SpringLayout.VERTICAL_CENTER, txtAssignee);
		layout.putConstraint(SpringLayout.WEST, lblAssignee, 0, SpringLayout.WEST, lblTitle);
		layout.putConstraint(SpringLayout.EAST, lblAssignee, labelWidth, SpringLayout.WEST, lblAssignee);
		layout.putConstraint(SpringLayout.WEST, txtAssignee, HORIZONTAL_PADDING, SpringLayout.EAST, lblAssignee);

		layout.putConstraint(SpringLayout.NORTH, tagPanel, VERTICAL_PADDING * 3, SpringLayout.NORTH, txtAssignee);
		layout.putConstraint(SpringLayout.WEST, tagPanel, 0, SpringLayout.WEST, lblTitle);
		layout.putConstraint(SpringLayout.EAST, tagPanel, 0, SpringLayout.EAST, txtTitle);
		layout.putConstraint(SpringLayout.SOUTH, this, 15, SpringLayout.SOUTH, tagPanel);

		add(lblTitle);
		add(txtTitle);
		add(lblDescription);
		add(txtDescription);
		add(lblCreator);
		add(txtCreator);
		add(lblAssignee);
		add(txtAssignee);
		add(tagPanel);
	}

	/**
	 * Gets the DefectPanel's internal model.
	 * @return
	 */
	public Defect getModel() {
		return model;
	}
	
	/**
	 * Returns the model object represented by this view's fields.
	 * 
	 * TODO: Change return type to the abstract class / interface
	 * TODO: Ensure that if id field is set to -1, that a new defect is created on the server
	 * TODO: Do some basic input verification
	 * TODO: Deal with tags and other assignee
	 * @return the model represented by this view
	 */
	public Defect getFieldModel() {
		return new Defect(model.getId(), txtTitle.getText(), txtDescription.getText(), new User("", txtCreator.getText(), "", -1));
	}
}
