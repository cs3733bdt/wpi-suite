package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.preferences.creation;

import java.awt.Container;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;

public class EmailPreferencesPanel extends JPanel {
	
	public EmailPreferencesPanel() {
		build();
	}
	
	private void build() {
		/* Set up initial container with spring layout */
    	Container view = new Container();
    	SpringLayout layout = new SpringLayout();
    	view.setLayout(layout);
    	
    	/**
    	 * Create and add the heading label
    	 */
    	JLabel headingLabel = new JLabel("Email Preferences");
    	headingLabel.setFont(makeFont(8));
    	view.add(headingLabel);
    	
    	/**
    	 * Put constraints on the heading label
    	 */
    	layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, view, 0, SpringLayout.HORIZONTAL_CENTER, headingLabel);
    	layout.putConstraint(SpringLayout.NORTH, headingLabel, 5, SpringLayout.NORTH, view);
    	
    	add(view);
    	revalidate();
    	repaint();
    	
	}
	
	public Font makeFont(int size) {
		/**
		 * Creates a new font for use later
		 */
		//create a dummy JTextArea
		JTextArea editingArea = new JTextArea();
		// get the current font
		Font f = editingArea.getFont();
		// create a new, larger font from the current font
		Font newFont = new Font(f.getFontName(), f.getStyle(), f.getSize()+size);		
		//set the bigger font for userStoryDesc
		Font bigFont = newFont;
		return bigFont;
	}
}
