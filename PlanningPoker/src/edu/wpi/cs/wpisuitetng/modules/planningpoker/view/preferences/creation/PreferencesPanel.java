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
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.preferences.creation;


import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.abstractmodel.AbstractStorageModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.user.controllers.RetrieveUserController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.user.controllers.UpdateUserController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.IDataField;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.IErrorView;

/**
 * used to display user preferences, allowing the user to
 * edit their facebook email, and mobile phone number and
 * select how they wanted to be contacted
 * @author Bobby Drop Tables
 *
 */
public class PreferencesPanel extends JScrollPane implements IDataField, IPreferencesPanel {
	private static final Logger logger = Logger.getLogger(AbstractStorageModel.class.getName());
	JPanel emailPanel;

	JPanel mobilePanel;

	JPanel facebookPanel;

	JLabel emailOffNotify;
	JTextField emailField;
	JButton updateEmailButton;
	JCheckBox emailCheckBox;

	JLabel mobileOffNotify;
	JTextField mobileField;
	JButton updateMobileButton;
	JCheckBox mobileCheckBox;
	JComboBox<String> carrierDropDown;

	JLabel facebookOffNotify;
	JTextField facebookField;
	JButton updateFacebookButton;
	JCheckBox facebookCheckBox;

	boolean hasNumber;
	boolean hasCarrier;
	boolean hasEmail;
	boolean hasFaceBook;

	RetrieveUserController getUserController;
	UpdateUserController updateUserController;
	User testUser;
	

	public PreferencesPanel() {
		//Get the instance of the user controller to get current user data.
		InitializeControllers();
		build();
	}
	/**
	 * THIS CONSTRUCTOR IS ONLY USED FOR TESTING PURPOSES!!!
	 * @param currentUser fake user which replaces the user retrieved from core/user
	 */
	public PreferencesPanel(User currentUser) {
		testUser = currentUser;
		InitializeControllers();
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
		emailOffNotify = new JLabel("You are not receiving email notifications");
		emailOffNotify.setForeground(Color.blue);
		emailPanel.add(emailOffNotify);

		//Create and add the user email label to the panel
		JLabel userEmailLabel = new JLabel("Your Email:");
		emailPanel.add(userEmailLabel);

		//Create, configure, and add the user email text box
		emailField = new JTextField(50);
		emailField.setEditable(true);
		emailField.setFocusable(true);
		addKeyListenerTo(emailField);
		emailField.setText("FillerText@gmail.com");
		emailPanel.add(emailField);

		/**
		 * IF TEST CONSTRUCTOR IS CALLED, FILL THE EMAIL FIELD
		 * WITH THE GIVEN USER'S EMAIL FOR TESTING PURPOSES
		 */
		if(testUser != null) {
			emailField.setText(testUser.getEmail());
		} 
		else {
			/**
			 * TODO autopopulate email field with user's email.
			 */
			emailField.setText(getUserEmail());
		}
		//Create the update email button
		updateEmailButton = new JButton("Update Email");
		updateEmailButton.setEnabled(false);
		emailPanel.add(updateEmailButton);
		
		updateEmailButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e){
				updateEmailButtonPressed();
			}
		});

		//Create and add the checkbox for receiving emails
		emailCheckBox = new JCheckBox("Receive Email notifications", true);
		//TODO make this field initialize to the correct toggled state. Do that by modifying the constant "true" above

		emailCheckBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				emailCheckBoxListener();	
			}
		});

		//reValidateEmailPanel();
		emailPanel.add(emailCheckBox);

		//Add the email Panel to the view
		view.add(emailPanel);

		/**
		 * Code for the facebook Panel 
		 * 
		 **/

		//Create and add the facebook preferences panel
		facebookPanel = new JPanel();
		SpringLayout facebookLayout = new SpringLayout();
		facebookPanel.setLayout(facebookLayout);
		facebookPanel.setBorder((new JTextField()).getBorder());
		facebookPanel.setPreferredSize(new Dimension(600, 110));

		//Create and add the facebook heading Label
		JLabel facebookPanelLabel = new JLabel("Facebook Preferences");
		facebookPanelLabel.setFont(makeFont(9));
		facebookPanel.add(facebookPanelLabel);

		//Create and add the "you are not receiving facebook" warning message. 
		facebookOffNotify = new JLabel("You are not receiving facebook messages");
		facebookOffNotify.setForeground(Color.blue);
		facebookPanel.add(facebookOffNotify);

		//Create and add the user facebook label to the panel
		JLabel userfacebookLabel = new JLabel("Your Facebook Username:");
		facebookPanel.add(userfacebookLabel);

		//Create, configure, and add the user facebook text box
		facebookField = new JTextField(50);
		facebookField.setEditable(true);
		facebookField.setFocusable(true);
		facebookField.setToolTipText("Your facebook username is the string after www.facebook.com/ in your facebook profile URL");
		addKeyListenerTo(facebookField);
		facebookField.setText("facebook.username");
		facebookPanel.add(facebookField);
		
		/**
		 * IF TEST CONSTRUCTOR IS CALLED, FILL THE FB USERNAME FIELD
		 * WITH THE GIVEN USER'S FB USERNAME FOR TESTING PURPOSES
		 */
		if(testUser != null) {
			facebookField.setText(testUser.getFacebookUsername());
		}
		else {
			facebookField.setText(getUserFacebookUsername());
		}
		//Create the update facebook button
		updateFacebookButton = new JButton("Update Facebook Username");
		updateFacebookButton.setEnabled(false);
		facebookPanel.add(updateFacebookButton);
		
		updateFacebookButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e){
				updateFacebookButtonPressed();
			}
		});

		//Create and add the checkbox for receiving facebooks
		facebookCheckBox = new JCheckBox("Receive Facebook messages", true);
		//TODO make this field initialize to the correct toggled state. Do that by modifying the constant "true" above

		facebookCheckBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				facebookCheckBoxListener();	
			}
		});

		//reValidateFacebookPanel();
		facebookPanel.add(facebookCheckBox);

		//Add the facebook Panel to the view
		view.add(facebookPanel);

		/**
		 * Code for the mobile settings panel */

		//Create and add the mobile preferences panel
		mobilePanel = new JPanel();
		SpringLayout mobileLayout = new SpringLayout();
		mobilePanel.setLayout(mobileLayout);
		mobilePanel.setBorder((new JTextField()).getBorder());
		mobilePanel.setPreferredSize(new Dimension(600, 140));

		//Create and add the mobile heading Label
		JLabel mobilePanelLabel = new JLabel("Mobile Preferences");
		mobilePanelLabel.setFont(makeFont(9));
		mobilePanel.add(mobilePanelLabel);

		//Create and add the "you are not receiving mobile notifications" warning message. 
		mobileOffNotify = new JLabel("You are not receiving mobile notifications");
		mobileOffNotify.setForeground(Color.blue);
		mobilePanel.add(mobileOffNotify);

		//Create and add the user mobile label to the panel
		JLabel userMobileLabel = new JLabel("Your Phone Number:");
		mobilePanel.add(userMobileLabel);

		//Create, configure, and add the user mobile text box
		mobileField = new JTextField(50);
		mobileField.setEditable(true);
		mobileField.setFocusable(true);
		addKeyListenerTo(mobileField);
		mobileField.setText("555-555-5555");
		mobilePanel.add(mobileField);
		
		/**
		 * IF TEST CONSTRUCTOR IS CALLED, FILL THE MOBILE FIELD WITH
		 * GIVEN USER'S MOBILE NUMBER FOR TESTING PURPOSES
		 */
		if(testUser != null) {
			mobileField.setText(testUser.getPhoneNumber());
		}
		else {
			/**
			* TODO autopopulate mobile field with user's number.
			*/
			mobileField.setText(getUserMobile());
		}
		//Create the update mobile button
		updateMobileButton = new JButton("Update Mobile Number");
		updateMobileButton.setEnabled(false);
		mobilePanel.add(updateMobileButton);
		
		updateMobileButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e){
				updateMobileButtonPressed();
			}
		});

		//Create and add the user carrier label to the panel
		JLabel userCarrierLabel = new JLabel("Your Carrier:");
		mobilePanel.add(userCarrierLabel);

		//Create and add drop down menu for carriers
		String[] items = { "Verizon", "AT&T", "T-Mobile", "Sprint", "U.S. Cellular", "--"};
		carrierDropDown = new JComboBox<String>(items);
		try{
		carrierDropDown.setSelectedIndex(getUserCarrierIndex());
		}catch(NullPointerException e){
			logger.log(Level.FINE, " '--' is selected.", e);
			carrierDropDown.setSelectedIndex(5);
		}
		carrierDropDown.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				reValidateMobileUpdateButton();
			}
		});

		mobilePanel.add(carrierDropDown);

		//Create and add the checkbox for receiving mobile notifications
		mobileCheckBox = new JCheckBox("Receive Mobile notifications", true);
		//TODO make this field initialize to the correct toggled state. Do that by modifying the constant "true" above

		mobileCheckBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mobileCheckBoxListener();	
			}
		});

		//reValidateMobilePanel();
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
		layout.putConstraint(SpringLayout.NORTH, emailPanel, 20, SpringLayout.SOUTH, headingLabel);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, emailPanel, 0, SpringLayout.HORIZONTAL_CENTER, view);

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

		emailLayout.putConstraint(SpringLayout.EAST, emailField, -5, SpringLayout.EAST, emailPanel);
		//  	emailLayout.putConstraint(SpringLayout.EAST, emailField, -5, SpringLayout.WEST, updateEmailButton);

		//Put constraints on the email checkbox
		emailLayout.putConstraint(SpringLayout.NORTH, emailCheckBox, 5, SpringLayout.SOUTH, userEmailLabel);
		emailLayout.putConstraint(SpringLayout.WEST, emailCheckBox, 20, SpringLayout.WEST, emailPanel);

		/**
		 * Put constraints on the facebook preferences panel 
		 */
		layout.putConstraint(SpringLayout.NORTH, facebookPanel, 20, SpringLayout.SOUTH, emailPanel);	
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, facebookPanel, 0, SpringLayout.HORIZONTAL_CENTER, view);

		
		// put constraints on the facebook label 
		facebookLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, facebookPanelLabel, 0, SpringLayout.HORIZONTAL_CENTER, facebookPanel);

		//put constraints on the facebookOffNotify label
		facebookLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, facebookOffNotify, 0, SpringLayout.HORIZONTAL_CENTER, facebookPanel);
		facebookLayout.putConstraint(SpringLayout.NORTH, facebookOffNotify, 5, SpringLayout.SOUTH, facebookPanelLabel);

		//put constraints on the user facebook label 
		facebookLayout.putConstraint(SpringLayout.NORTH, userfacebookLabel, 5, SpringLayout.SOUTH, facebookOffNotify);
		facebookLayout.putConstraint(SpringLayout.WEST, userfacebookLabel, 5, SpringLayout.WEST, facebookPanel);

		//Constraints for the facebook update button
		facebookLayout.putConstraint(SpringLayout.NORTH, updateFacebookButton, 5, SpringLayout.SOUTH, facebookField);
		facebookLayout.putConstraint(SpringLayout.EAST, updateFacebookButton, 0, SpringLayout.EAST, facebookField);


		//Put constraints on the facebook text field 
		facebookLayout.putConstraint(SpringLayout.NORTH, facebookField, 5, SpringLayout.SOUTH, facebookOffNotify);
		facebookLayout.putConstraint(SpringLayout.WEST, facebookField, 5, SpringLayout.EAST, userfacebookLabel);
		facebookLayout.putConstraint(SpringLayout.EAST, facebookField, -5, SpringLayout.EAST, facebookPanel);
		//  	facebookLayout.putConstraint(SpringLayout.EAST, facebookField, -5, SpringLayout.WEST, updatefacebookButton);

		//Put constraints on the facebook checkbox
		facebookLayout.putConstraint(SpringLayout.NORTH, facebookCheckBox, 5, SpringLayout.SOUTH, userfacebookLabel);
		facebookLayout.putConstraint(SpringLayout.WEST, facebookCheckBox, 20, SpringLayout.WEST, facebookPanel);


		/**
		 * put constraints on the mobile preferences panel 
		 */
		layout.putConstraint(SpringLayout.NORTH, mobilePanel, 20, SpringLayout.SOUTH, facebookPanel);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, mobilePanel, 0, SpringLayout.HORIZONTAL_CENTER, view);

		
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
		mobileLayout.putConstraint(SpringLayout.EAST, mobileField, -5, SpringLayout.EAST, mobilePanel);
 
		//Constraints for the mobile update button
		mobileLayout.putConstraint(SpringLayout.EAST, updateMobileButton, 0, SpringLayout.EAST, mobileField);
		mobileLayout.putConstraint(SpringLayout.NORTH, updateMobileButton, 5, SpringLayout.SOUTH, mobileField);

		//Put constraints on the mobile carrier label
		mobileLayout.putConstraint(SpringLayout.NORTH, userCarrierLabel, 5, SpringLayout.SOUTH, userMobileLabel);
		mobileLayout.putConstraint(SpringLayout.WEST, userCarrierLabel, 5, SpringLayout.WEST, mobilePanel);

		//put constraints on the mobile carrier drop down
		mobileLayout.putConstraint(SpringLayout.NORTH, carrierDropDown, 5, SpringLayout.SOUTH, mobileField);
		mobileLayout.putConstraint(SpringLayout.WEST, carrierDropDown, 5, SpringLayout.EAST, userMobileLabel);

		//Put constraints on the mobile checkbox
		mobileLayout.putConstraint(SpringLayout.NORTH, mobileCheckBox, 5, SpringLayout.SOUTH, carrierDropDown);
		mobileLayout.putConstraint(SpringLayout.WEST, mobileCheckBox, 20, SpringLayout.WEST, mobilePanel);

		initializeCheckBoxes();
		setViewportView(view);
		revalidate();
		repaint();
	}

	public void updateEmailButtonPressed() {
		/**
		 * IF TEST CONSTRUCTOR WAS CALLED, DO NOT
		 * UPDATE USER CONTROLLER
		 */
		if(testUser != null) {
			emailField.setText("newEmailaddress");
			String newEmail = emailField.getText();
			testUser.setEmail(newEmail);
		}
		else {
			User newUser = getUserController.getCurrentUser();
			newUser.setEmail(emailField.getText());
	
			updateUserController.updateUser(newUser);
		}
	}
	public void updateFacebookButtonPressed() {
		/**
		 * IF TEST CONSTRUCTOR WAS CALLED, DO NOT
		 * UPDATE USER CONTROLLER
		 */
		if(testUser != null) {
			facebookField.setText("newFacebook");
			String newFb = facebookField.getText();
			testUser.setFacebookUsername(newFb);
		}
		else {
			User newUser = getUserController.getCurrentUser();
			newUser.setFacebookUsername(facebookField.getText());
			updateUserController.updateUser(newUser);
		}
	}
	public void updateMobileButtonPressed() {
		/**
		 * IF TEST CONSTRUCTOR WAS CALLED, DO NOT
		 * UPDATE USER CONTROLLER
		 */
		if(testUser != null) {
			mobileField.setText("1234567890");
			String newMobile = mobileField.getText();
			testUser.setPhoneNumber(newMobile);
		}
		else {
			String phoneNumber = mobileField.getText();
			if(phoneNumber.length() > 10){
				phoneNumber = phoneNumber.replace("-", "");
				phoneNumber = phoneNumber.replace(" ", "");
				if(phoneNumber.charAt(0) == '1'){
					phoneNumber = phoneNumber.substring(1);
				}
				
			}
			User newUser = getUserController.getCurrentUser();
			newUser.setPhoneNumber(phoneNumber);
			newUser.setCarrier(getCarrierFromIndex());
			updateUserController.updateUser(newUser);
		}
	}
	
	/**
	 * This method returns a string representing the currently selected 
	 * carrier by getting the carrier drop down's selected index
	 * @return returns a string representing the currently selected carrier
	 */
	public String getCarrierFromIndex() {
		int carrierIndex = carrierDropDown.getSelectedIndex();
		String carrier;
		
		switch(carrierIndex){
		case 0:
			carrier = "VERIZON";
			break;
		case 1:
			carrier = "ATT";
			break;
		case 2:
			carrier = "TMOBILE";
			break;
		case 3:
			carrier = "SPRINT";
			break;
		case 4:
			carrier = "USCELLULAR";
			break;
		case 5:
			carrier = "--";
			break;
		default:
			carrier = "--";
			break;
		}

		return carrier;
	}
	
	public void updateNotificationPreferences(){
		User newUser = getUserController.getCurrentUser();
		String newPreferences = "";
		if(emailCheckBox.isSelected()){
			newPreferences= newPreferences + "E";
		}
		if(facebookCheckBox.isSelected()){
			newPreferences= newPreferences + "F";
		}
		if(mobileCheckBox.isSelected()){
			newPreferences= newPreferences + "M";
		}
		newUser.setNotificationPreferences(newPreferences);
		updateUserController.updateUser(newUser);
	}
	
	private void addKeyListenerTo(JComponent component){
		component.addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent arg0) {
				if (emailField.isFocusOwner()) {
					reValidateEmailUpdateButton();
				}
				else if(facebookField.isFocusOwner()) {
					reValidateFacebookUpdateButton();
				}
				else if(mobileField.isFocusOwner()) {
					reValidateMobileUpdateButton();
				}
				else {}
			}
		});
	}
	
	private void InitializeControllers() {
		getUserController = RetrieveUserController.getInstance();
		updateUserController = UpdateUserController.getInstance();
	}

	/**
	 * Waits for a successful request to get the current user,
	 * then returns the appropriate email string.
	 * Other getUser Data methods below do not require try catch, because
	 * getUserEmail is called first and insures a completed request
	 * @return returns the email of the user currently logged in
	 */
	private String getUserEmail() {
		//Try to get user data, if the request has not completed, will catch
		//exception and try again, this time waiting a few seconds to insure
		//that the request has completed.
		String userEmail;
		try{
			userEmail = getUserController.getCurrentUser().getEmail();
			if (userEmail == null) {
				hasEmail = false;
				return "";
			}
			else {
				hasEmail = true;
				return userEmail;
			}
		}catch(NullPointerException e){
			logger.log(Level.WARNING, "Request couldn't be completed. Trying again...", e);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e1) {
				logger.log(Level.WARNING, "Thread is interrupted.", e1);
			}
			userEmail = getUserController.getCurrentUser().getEmail();
			if (userEmail == null) {
				hasEmail = false;
				return "";
			}
			else {
				hasEmail = true;
				return userEmail;
			}
		}
	}

	/**
	 * @return returns the phone number of the user currently logged in
	 */
	private String getUserMobile() {
		String number = getUserController.getCurrentUser().getPhoneNumber();
		if (number == null) {
			hasNumber = false;
			return "";
		}
		else {
			hasNumber = true;
			return number;
		}
	}

	/**
	 * @return returns the drop down index for the carrier of the user currently logged in
	 */
	private int getUserCarrierIndex() {
		int carrierNum;	
		String carrier = getUserController.getCurrentUser().getCarrier(); //TODO fix: this throws a null pointer exception
		switch(carrier) {
		case "ATT":
			carrierNum = 1;
			hasCarrier = true;
			break;
		case "VERIZON":
			carrierNum = 0;
			hasCarrier = true;
			break;
		case "TMOBILE":
			carrierNum = 2;
			hasCarrier = true;
			break;
		case "SPRINT":
			carrierNum = 3;
			hasCarrier = true;
			break;
		case "USCELLULAR":
			carrierNum = 4;
			hasCarrier = true;
			break;
		case "--":
			carrierNum = 5;
			hasCarrier = false;
			break;
		default:
			carrierNum = 5;
			break;
		} 
		return carrierNum;
	}

	/**
	 * @return returns the facebook username of the user currently logged in
	 */
	private String getUserFacebookUsername() {
		String facebookName = getUserController.getCurrentUser().getFacebookUsername();
		if (facebookName == null) {
			hasFaceBook = false;
			return "";
		}
		else {
			hasFaceBook = true;
			return facebookName;
		}
	}

	/**
	 * Creates a font to be used for later
	 * @param size The size of the font
	 * @return font to be used 
	 */
	public Font makeFont(int size) {
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
	public boolean hasChanges() {
		// TODO Auto-generated method stub
		return false;
	}

	public void initializeCheckBoxes() {
		try{
			String preferences = getUserController.getCurrentUser().getNotificationPreferences();
			emailCheckBox.setSelected(false);
			facebookCheckBox.setSelected(false);
			mobileCheckBox.setSelected(false);
			//E: Email preference
			if(preferences.contains("E")){
				emailCheckBox.setSelected(true);
				emailOffNotify.setVisible(false);
			}
			//F: Facebook preference
			if(preferences.contains("F")){
				facebookCheckBox.setSelected(true);
				facebookOffNotify.setVisible(false);
			}
			//M: Mobile preference
			if(preferences.contains("M")){
				mobileCheckBox.setSelected(true);
				mobileOffNotify.setVisible(false);
			}
			
		}catch(NullPointerException e){
			logger.log(Level.WARNING, "Couldn't get user preferences.", e);
			emailCheckBox.setSelected(false);
			facebookCheckBox.setSelected(false);
			mobileCheckBox.setSelected(false);
			emailOffNotify.setVisible(true);
			facebookOffNotify.setVisible(true);
			mobileOffNotify.setVisible(true);
		}
		reValidateEmailUpdateButton();
		reValidateFacebookUpdateButton();
		reValidateMobileUpdateButton();
		
	}

	public boolean receivingEmail() {
		return (emailCheckBox.isSelected());
	}

	public boolean receivingFacebook() {
		return (facebookCheckBox.isSelected());
	}

	public boolean receivingMobile() {
		return (mobileCheckBox.isSelected());
	}
	
	/**
	 * displays the option to update the user's email if 
	 * the user selected the option to be notified through email
	 */
	public void emailCheckBoxListener() {
		if (emailCheckBox.isSelected()) {
			emailOffNotify.setVisible(false);
			emailField.setEnabled(true);
			updateEmailButton.setEnabled(true);
			reValidateEmailUpdateButton();
		}
		else {
			emailOffNotify.setVisible(true);

			emailField.setEnabled(false);
			updateEmailButton.setEnabled(false);	
		}
		
		/**
		 * IF TEST CONSTRUCTOR IS CALLED, DO NOT UPDATE THE
		 * NOTIFICATION PREFERENCES
		 */
		if(testUser != null) {
			
		}
		else {
			updateNotificationPreferences();
		}
	}
	
	/**
	 * displays the option to update the user's facebook if 
	 * the user selected the option to be notified through facebook
	 */
	public void facebookCheckBoxListener() {
		if (!facebookCheckBox.isSelected()) {
			facebookOffNotify.setVisible(true);

			facebookField.setEnabled(false);
			updateFacebookButton.setEnabled(false);
		}
		else {
			facebookOffNotify.setVisible(false);

			facebookField.setEnabled(true);
			updateFacebookButton.setEnabled(true);
			
			reValidateFacebookUpdateButton();
		}
		/**
		 * IF TEST CONSTRUCTOR IS CALLED, DO NOT 
		 * UPDATE NOTIFICATION PREFERENCES
		 */
		if(testUser != null) {
			
		} else {
			updateNotificationPreferences();
		}
	}
	
	/**
	 * displays the option to update the user's phone number if 
	 * the user selected the option to be notified through texts
	 */
	public void mobileCheckBoxListener() {
		if (!mobileCheckBox.isSelected()) {
			mobileOffNotify.setVisible(true);

			mobileField.setEnabled(false);
			carrierDropDown.setEnabled(false);
			updateMobileButton.setEnabled(false);
		}
		else {
			mobileOffNotify.setVisible(false);


			mobileField.setEnabled(true);
			carrierDropDown.setEnabled(true);
			updateMobileButton.setEnabled(true);
			reValidateMobileUpdateButton();
		}
		/**
		 * IF TEST CONSTRUCTOR IS CALLED DO NOT 
		 * UPDATE NOTIFICATION PREFERENCES
		 */
		if(testUser != null) {
			
		}
		else {
			updateNotificationPreferences();
		}
	}

	
	/**
	 * Enable/Disable the email update button based on
	 * the email field's current input, and if notifications are requested
	 */
	public void reValidateEmailUpdateButton() {
		if (verifyEmailField() && emailCheckBox.isSelected()) {
			updateEmailButton.setEnabled(true);
		}
		else {
			updateEmailButton.setEnabled(false);
		}
	}

	/**
	 * Enable/Disable the facebook update button based on
	 * the facebook field's current input, and if notifications are requested
	 */
	public void reValidateFacebookUpdateButton() {
		if (verifyFacebookField() && facebookCheckBox.isSelected()) {
			updateFacebookButton.setEnabled(true);
		}
		else {
			updateFacebookButton.setEnabled(false);
		}
	}

	/**
	 * Enable/Disable the mobile update button based on
	 * the mobile field's current input, the carrier selection, 
	 * and if notifications are requested
	 */
	public void reValidateMobileUpdateButton() {
		if (verifyMobileField() && verifyCarrierField() && mobileCheckBox.isSelected()) {
			updateMobileButton.setEnabled(true);
		}
		else {
			updateMobileButton.setEnabled(false);
		}

	}

	/**
	 * Checks that the email field contains string with an @ symbol, is of a valid length
	 * and does not contain any forbidden characters
	 * @return returns true or false whether the email field contains a valid email
	 */
	private boolean verifyEmailField() {
		String text = emailField.getText();
		if (text.length() == 0) {
			return false;
		}
		String currChar;

		//Code for checking the @ symbol
		int atCount = 0;
		int atIndex = -1;
		String atSubstring = "";
		boolean atBoolean = false;
		for (int i = 0; i < text.length(); i++) {
			currChar = Character.toString(text.charAt(i)); 
			if (currChar.equals("@")) {
				atCount++;
				atIndex = i;
				atSubstring = text.substring(i+1);
			}	
		}
		if (atCount == 1 && atIndex != 0 && atIndex != -1 && atIndex+1 != text.length() ) {
			atBoolean = true;
		}

		//Code for checking the at substring
		boolean atSubstringBool = true;
		if (atSubstring.length() < 3) {
			atSubstringBool = false;
		}

		//Code for checking forbidden character
		String forbiddenChars = "()<>[]\\:,;!#$%&'*+-/=?^_`{}| ~\" ";
		boolean forbiddenCharsBool = true;
		for (int i = 0; i < text.length(); i++) {
			currChar = Character.toString(text.charAt(i));
			if (forbiddenChars.contains(currChar)) {
				forbiddenCharsBool = false;
			}
		}

		return atBoolean && forbiddenCharsBool && atSubstringBool;
	}

	/**
	 * Checks that the facebook field receives an input of appropriate length,
	 * and contains appropriate symbols to be a valid facebook username
	 * @return returns true or false whether the facebook field contains valid input
	 */
	private boolean verifyFacebookField() {

		String text = facebookField.getText();

		if (text.length() <5 ) {
			return false;
		}

		//Code for checking forbidden character
		String forbiddenChars = "()<>[]\\:,;!#$%&'@*+-/=?^_`{}| ~\" ";
		String currChar;
		for (int i = 0; i < text.length(); i++) {
			currChar = Character.toString(text.charAt(i));
			if (forbiddenChars.contains(currChar)) {
				return false;
			}
		}
		//No forbidden characters, and appropriate length, so return true.
		return true;

	}
	/**
	 * Checks that the mobile field contains a valid phone number, in this case a 
	 * ten digit number
	 * @return returns true or false whether the mobile field contains a 
	 * ten digit number
	 */
	private boolean verifyMobileField() {
		String text = mobileField.getText();
		if (text.length() == 0) {
			return false;
		}
		Pattern numPattern1 = Pattern.compile("\\d{3}-\\d{7}");
		Pattern numPattern2 = Pattern.compile("\\d{1}-\\d{3}-\\d{7}");
		Pattern numPattern3 = Pattern.compile("\\d{3}-\\d{3}-\\d{4}");
		Pattern numPattern4 = Pattern.compile("\\d{1}-\\d{3}-\\d{3}-\\d{4}");
		Pattern numPattern5 = Pattern.compile("\\d{10}");
		Pattern numPattern6 = Pattern.compile("\\d{11}");
		Pattern numPattern7 = Pattern.compile("\\(\\d{3}\\) \\d{3} \\d{4}");
		Pattern numPattern8 = Pattern.compile("\\d{3} \\d{3} \\d{4}");

		Matcher matcher1 = numPattern1.matcher(text);
		Matcher matcher2 = numPattern2.matcher(text);
		Matcher matcher3 = numPattern3.matcher(text);
		Matcher matcher4 = numPattern4.matcher(text);
		Matcher matcher5 = numPattern5.matcher(text);
		Matcher matcher6 = numPattern6.matcher(text);
		Matcher matcher7 = numPattern7.matcher(text);
		Matcher matcher8 = numPattern8.matcher(text);


		boolean matcher1Boolean = matcher1.matches();
		boolean matcher2Boolean = matcher2.matches();
		boolean matcher3Boolean = matcher3.matches();
		boolean matcher4Boolean = matcher4.matches();
		boolean matcher5Boolean = matcher5.matches();
		boolean matcher6Boolean = matcher6.matches();
		boolean matcher7Boolean = matcher7.matches();
		boolean matcher8Boolean = matcher8.matches();

		if (matcher2Boolean || matcher4Boolean || matcher6Boolean) { //if the pattern leads with 1 digit
			String firstChar = Character.toString(text.charAt(0));
			if (!firstChar.equals("1")) { //if the first character isn't 1
				return false;
			}
		}
		return (matcher1Boolean || matcher2Boolean || matcher3Boolean || matcher4Boolean || 
				matcher5Boolean || matcher6Boolean || matcher7Boolean || matcher8Boolean);
	}
	
	/**
	 * @return return false if the carrier drop down has "--" selected,
	 * otherwise a valid carrier has been selected, return true.
	 */
	private boolean verifyCarrierField(){
		return !(carrierDropDown.getSelectedIndex()==5);
	}

	@Override
	public boolean validateField(IErrorView warningField, boolean showLabel,
			boolean showBox) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * Getter for testing purposes
	 * @return the text from emailField
	 */
	public JTextField getEmailField() {
		return emailField;
	}
	
	/**
	 * Getter for testing purposes
	 * @return the emailCheckBox
	 */
	public JCheckBox getEmailCheckBox() {
		return emailCheckBox;
	}
	
	/**
	 * Getter for testing purposes
	 * @return the text from emailField
	 */
	public JTextField getFacebookField() {
		return facebookField;
	}
	
	/**
	 * Getter for testing purposes
	 * @return the facebookCheckBox
	 */
	public JCheckBox getFacebookCheckBox() {
		return facebookCheckBox;
	}
	
	/**
	 * Getter for testing purposes
	 * @return the text from mobileField
	 */
	public JTextField getMobileField() {
		return mobileField;
	}
	
	/**
	 * Getter for testing purposes
	 * @return the mobileCheckBox
	 */
	public JCheckBox getMobileCheckBox() {
		return mobileCheckBox;
	}
	
	/**
	 * Method to validate whether preferences tab is ready to be closed
	 * @return true, no validation yet
	 */
	public boolean readyToRemove() {
		return true;
	}
	
	/**
	 * Getter for testing purposes
	 * @return the text from mobileField
	 */
	public JLabel getemailOffNotify() {
		return emailOffNotify;
	}
	
	/**
	 * Getter for testing purposes
	 * @return the carrierDropdown
	 */
	public JComboBox<String> getCarrierDropDown() {
		return carrierDropDown;
	}
	
}