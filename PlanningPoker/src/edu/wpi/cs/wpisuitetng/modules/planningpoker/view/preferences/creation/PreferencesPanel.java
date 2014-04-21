package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.preferences.creation;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.IDataField;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.IErrorView;

public class PreferencesPanel extends JScrollPane implements IDataField {
	JPanel emailPanel;
	
	JPanel mobilePanel;
	
	JLabel emailOffNotify;
	JTextField emailField;
	JButton updateEmailButton;
	JCheckBox emailCheckBox;

	JLabel mobileOffNotify;
	JTextField mobileField;
	JButton updateMobileButton;
	JCheckBox mobileCheckBox;
	JButton updateCarrierButton;
	
    public PreferencesPanel () {
    	build();
    }
	
    private void build(){
    	/**
    	 *  Set up initial container with spring layout */
    	Container 	view = new Container();
    	SpringLayout layout = new SpringLayout();
    	view.setLayout(layout);
    	
    	/**
    	 * Create and add the heading label */
    	JLabel headingLabel = new JLabel("Change Your Preferences Here");
    	headingLabel.setFont(makeFont(10));
    	view.add(headingLabel);
    	
    	/**
    	 * Code for the Email Panel */

    	//Create and add the email preferences panel
    	emailPanel = new JPanel();
    	SpringLayout emailLayout = new SpringLayout();
    	emailPanel.setLayout(emailLayout);
    	emailPanel.setBorder((new JTextField()).getBorder());
    	emailPanel.setPreferredSize(new Dimension(600, 110));
  
    	//Create and add the email heading Label
    	JLabel emailPanelLabel = new JLabel("Email Preferences");
    	emailPanelLabel.setFont(makeFont(9));
    	emailPanel.add(emailPanelLabel);
    	
    	//Create and add the "you are not receiving email" warning message. 
    	emailOffNotify = new JLabel("*You are not receiving email notifications");
    	emailOffNotify.setForeground(Color.blue);
    	if (!receivingEmail()) {
    		emailOffNotify.setVisible(true);
    	}
    	else {
    		emailOffNotify.setVisible(false);
    	}
    	emailPanel.add(emailOffNotify);
    	
    	//Create and add the user email label to the panel
    	JLabel userEmailLabel = new JLabel("Your Email:");
    	emailPanel.add(userEmailLabel);
    	
    	//Create, configure, and add the user email text box
    	emailField = new JTextField(50);
    	emailField.setEditable(true);
    	emailField.setText("FillerText@gmail.com");
    	emailPanel.add(emailField);
   
    	/**
    	 * TODO autopopulate email field with user's email.
    	 */
    	
    	//Create the update email button
    	updateEmailButton = new JButton("Update Email");
    	updateEmailButton.setEnabled(false);
    	emailPanel.add(updateEmailButton);
    	
    	//Create and add the checkbox for receiving emails
    	emailCheckBox = new JCheckBox("Receive Email notifications", true);
    	//TODO make this field initialize to the correct toggled state. Do that by modifying the constant "true" above
    	
    	emailCheckBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				emailCheckBoxListener();	
			}
		});
    	
    	reValidateEmailPanel();
    	emailPanel.add(emailCheckBox);
    
    	//Add the email Panel to the view
    	view.add(emailPanel);
    	
    	/**
    	 * Code for the mobile settings panel */
    	
    	//Create and add the email preferences panel
    	mobilePanel = new JPanel();
    	SpringLayout mobileLayout = new SpringLayout();
    	mobilePanel.setLayout(mobileLayout);
    	mobilePanel.setBorder((new JTextField()).getBorder());
    	mobilePanel.setPreferredSize(new Dimension(600, 140));
    	
    	//Create and add the email heading Label
    	JLabel mobilePanelLabel = new JLabel("Mobile Preferences");
    	mobilePanelLabel.setFont(makeFont(9));
    	mobilePanel.add(mobilePanelLabel);
    	
    	//Create and add the "you are not receiving mobile notifications" warning message. 
    	mobileOffNotify = new JLabel("*You are not receiving mobile notifications");
    	mobileOffNotify.setForeground(Color.blue);
    	if (!receivingMobile()) {
    		mobileOffNotify.setVisible(true);
    	}
    	else {
    		mobileOffNotify.setVisible(false);
    	}
    	mobilePanel.add(mobileOffNotify);
    	
    	//Create and add the user email label to the panel
    	JLabel userMobileLabel = new JLabel("Your Phone Number:");
    	mobilePanel.add(userMobileLabel);
    	
    	//Create, configure, and add the user email text box
    	mobileField = new JTextField(50);
    	mobileField.setEditable(true);
    	mobileField.setText("555-555-5555");
    	mobilePanel.add(mobileField);
   
    	/**
    	 * TODO autopopulate mobile field with user's number.
    	 */
    	
    	
    	//Create the update mobile button
    	updateMobileButton = new JButton("Update Mobile Number");
    	updateMobileButton.setEnabled(false);
    	mobilePanel.add(updateMobileButton);
    	
    	//Create the update carrier button
    	updateCarrierButton = new JButton("Update Carrier");
    	updateCarrierButton.setEnabled(false);
    	mobilePanel.add(updateCarrierButton);
    	
    	//Create and add the user carrier label to the panel
    	JLabel userCarrierLabel = new JLabel("Your Carrier:");
    	mobilePanel.add(userCarrierLabel);
    	
    	//Create and add drop down menu for carriers
    	String[] items = { "Verizon", "AT&T", "T-Mobile", "Sprint", "U.S. Cellular"};
    	JComboBox carrierDropDown = new JComboBox(items);
    	carrierDropDown.setSelectedIndex(getUserCarrier());
    	mobilePanel.add(carrierDropDown);
    	
    	//Create and add the checkbox for receiving emails
    	mobileCheckBox = new JCheckBox("Receive Mobile notifications", true);
    	//TODO make this field initialize to the correct toggled state. Do that by modifying the constant "true" above
    	
    	mobileCheckBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mobileCheckBoxListener();	
			}
		});
    	
    	reValidateMobilePanel();
    	mobilePanel.add(mobileCheckBox);
    	
    	//Add the mobile Panel to the view
    	view.add(mobilePanel);
    	
    	/**
    	Put constraints on the heading label */
    	layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, headingLabel, 0, SpringLayout.HORIZONTAL_CENTER, view);
    	layout.putConstraint(SpringLayout.NORTH, headingLabel, 5, SpringLayout.NORTH, view);	
    	
    	/**
    	 * Put constraints on the email preferences panel 
    	 */
    	layout.putConstraint(SpringLayout.WEST, emailPanel, 5, SpringLayout.WEST, view);
    	layout.putConstraint(SpringLayout.NORTH, emailPanel, 20, SpringLayout.SOUTH, headingLabel);	
       	layout.putConstraint(SpringLayout.EAST, emailPanel, -5, SpringLayout.EAST, view);
       	
    	// put constraints on the email label 
    	emailLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, emailPanelLabel, 0, SpringLayout.HORIZONTAL_CENTER, emailPanel);
    	
    	//put constraints on the emailOffNotify label
    	emailLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, emailOffNotify, 0, SpringLayout.HORIZONTAL_CENTER, emailPanel);
    	emailLayout.putConstraint(SpringLayout.NORTH, emailOffNotify, 5, SpringLayout.SOUTH, emailPanelLabel);
    	
    	//put constraints on the user email label 
    	emailLayout.putConstraint(SpringLayout.NORTH, userEmailLabel, 5, SpringLayout.SOUTH, emailOffNotify);
    	emailLayout.putConstraint(SpringLayout.WEST, userEmailLabel, 5, SpringLayout.WEST, emailPanel);
    	
      	//Constraints for the email update button
    	emailLayout.putConstraint(SpringLayout.NORTH, updateEmailButton, 5, SpringLayout.SOUTH, emailField);
    	emailLayout.putConstraint(SpringLayout.EAST, updateEmailButton, 0, SpringLayout.EAST, emailField);
    	
    	
    	//Put constraints on the email text field 
    	emailLayout.putConstraint(SpringLayout.NORTH, emailField, 5, SpringLayout.SOUTH, emailOffNotify);
    	emailLayout.putConstraint(SpringLayout.WEST, emailField, 5, SpringLayout.EAST, userEmailLabel);
  //  	emailLayout.putConstraint(SpringLayout.EAST, emailField, -5, SpringLayout.WEST, updateEmailButton);
    	
    	//Put constraints on the email checkbox
    	emailLayout.putConstraint(SpringLayout.NORTH, emailCheckBox, 5, SpringLayout.SOUTH, userEmailLabel);
    	emailLayout.putConstraint(SpringLayout.WEST, emailCheckBox, 20, SpringLayout.WEST, emailPanel);
    	
    	
     	
    	/**
    	 * put constraints on the mobile preferences panel 
    	 */
    	layout.putConstraint(SpringLayout.WEST, mobilePanel, 5, SpringLayout.WEST, view);
    	layout.putConstraint(SpringLayout.NORTH, mobilePanel, 20, SpringLayout.SOUTH, emailPanel);
    	layout.putConstraint(SpringLayout.EAST, mobilePanel, -5, SpringLayout.EAST, view);
    	
    	// put constraints on the mobile panel label
    	mobileLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, mobilePanelLabel, 0, SpringLayout.HORIZONTAL_CENTER, mobilePanel);
    	
    	//put constraints on the mobileOffNotify label
    	mobileLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, mobileOffNotify, 0, SpringLayout.HORIZONTAL_CENTER, mobilePanel);
    	mobileLayout.putConstraint(SpringLayout.NORTH, mobileOffNotify, 5, SpringLayout.SOUTH, mobilePanelLabel);
    	
    	//put constraints on the user mobile label 
    	mobileLayout.putConstraint(SpringLayout.NORTH, userMobileLabel, 5, SpringLayout.SOUTH, mobileOffNotify);
    	mobileLayout.putConstraint(SpringLayout.WEST, userMobileLabel, 5, SpringLayout.WEST, mobilePanel);
    
    	//Put constraints on the mobile text field 
    	mobileLayout.putConstraint(SpringLayout.NORTH, mobileField, 5, SpringLayout.SOUTH, mobileOffNotify);
    	mobileLayout.putConstraint(SpringLayout.WEST, mobileField, 5, SpringLayout.EAST, userMobileLabel);
//    	mobileLayout.putConstraint(SpringLayout.EAST, mobileField, -5, SpringLayout.WEST, updateMobileButton);
//    
      	//Constraints for the mobile update button
    	mobileLayout.putConstraint(SpringLayout.EAST, updateMobileButton, 0, SpringLayout.EAST, mobileField);
    	mobileLayout.putConstraint(SpringLayout.NORTH, updateMobileButton, 5, SpringLayout.SOUTH, mobileField);
    	
    	//Constraints for the carrier update button
    	mobileLayout.putConstraint(SpringLayout.WEST, updateCarrierButton, 5, SpringLayout.EAST, carrierDropDown);
    	mobileLayout.putConstraint(SpringLayout.NORTH, updateCarrierButton, 5, SpringLayout.SOUTH, mobileField);
    	
    	//Put constraints on the mobile carrier label
    	mobileLayout.putConstraint(SpringLayout.NORTH, userCarrierLabel, 5, SpringLayout.SOUTH, userMobileLabel);
    	mobileLayout.putConstraint(SpringLayout.WEST, userCarrierLabel, 5, SpringLayout.WEST, mobilePanel);
    	
    	//put constraints on the mobile carrier drop down
    	mobileLayout.putConstraint(SpringLayout.NORTH, carrierDropDown, 5, SpringLayout.SOUTH, mobileField);
    	mobileLayout.putConstraint(SpringLayout.WEST, carrierDropDown, 5, SpringLayout.EAST, userMobileLabel);
    	
    	//Put constraints on the mobile checkbox
    	mobileLayout.putConstraint(SpringLayout.NORTH, mobileCheckBox, 5, SpringLayout.SOUTH, carrierDropDown);
    	mobileLayout.putConstraint(SpringLayout.WEST, mobileCheckBox, 20, SpringLayout.WEST, mobilePanel);
    	
    	
    	setViewportView(view);
    	revalidate();
    	repaint();
    }
    
	private int getUserCarrier() {
		// TODO Auto-generated method stub
		return 0;
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

	@Override
	public boolean validateField(IErrorView warningField, boolean show) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasChanges() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean receivingEmail() {
		//TODO
		return true;
	}
	
	public boolean receivingMobile() {
		//TODO 
		return true;
	}
	
	public void emailCheckBoxListener() {
		if (!emailCheckBox.isSelected()) {
			emailOffNotify.setVisible(true);
		
			emailField.setEnabled(false);
			updateEmailButton.setEnabled(false);
		}
		else {
			emailOffNotify.setVisible(false);
			
			emailField.setEnabled(true);
			reValidateEmailUpdateButton();
		}
	}
	
	public void mobileCheckBoxListener() {
		if (!mobileCheckBox.isSelected()) {
			mobileOffNotify.setVisible(true);
		
			mobileField.setEnabled(false);
			updateEmailButton.setEnabled(false);
		}
		else {
			mobileOffNotify.setVisible(false);
			
			mobileField.setEnabled(true);
			reValidateMobileUpdateButton();
		}
	}
	
	public void reValidateEmailPanel() {
		emailCheckBoxListener();
		reValidateEmailUpdateButton();
	}
	
	public void reValidateMobilePanel() {
		mobileCheckBoxListener();
		reValidateMobileUpdateButton();
	}
	
	public void reValidateEmailUpdateButton() {
		if (verifyEmailField()) {
			updateEmailButton.setEnabled(true);
		}
		else {
			updateEmailButton.setEnabled(false);
		}
	}
	
	public void reValidateMobileUpdateButton() {
		if (verifyMobileField()) {
			updateMobileButton.setEnabled(true);
		}
		else {
			updateMobileButton.setEnabled(false);
		}
	}
	

	private boolean verifyEmailField() {
		// TODO Auto-generated method stub
		return false;
	}
	
	private boolean verifyMobileField() {
		// TODO Auto-generated method stub
		return false;
	}
	
}