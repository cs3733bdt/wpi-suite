/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import java.lang.System;
import java.util.Date;

import org.jdesktop.swingx.JXDatePicker;

/**
 *
 */
public class AddEndDatePanel extends JPanel {

	private JPanel pickEndDatePanel = new JPanel();
	
	final JLabel label = new JLabel();
	
	final JXDatePicker datePicker = new JXDatePicker(new Date());
	
	private final Border defaultBorder = (new JTextField()).getBorder();
	
	public AddEndDatePanel(final CreateGamePanel view){

		super(new GridBagLayout());
		
		Container endDatePanel = new Container();
		endDatePanel.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		
		/**
		 * Panel for creating a requirement by text entry
		 */
		pickEndDatePanel.setLayout(new GridBagLayout());
		pickEndDatePanel.setBorder(defaultBorder);
		c.insets = new Insets(0, 0, 0 ,0);
		c.ipadx = 0;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 1;
		pickEndDatePanel.setVisible(false);
		endDatePanel.add(pickEndDatePanel, c);
		
		label.setText("Choose Date by selecting below.");

		view.add(label, c);
		view.add(datePicker, c);
		
		this.add(endDatePanel);
		
	}
	
	public JPanel getPickEndDatePanel() {
		return pickEndDatePanel;
	}
}
