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

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.IDataField;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.IErrorView;

/**
 * used to display user preferences, allowing the user to
 * edit their facebook email, and mobile phone number and
 * select how they wanted to be contacted
 * @author Bobby Drop Tables
 *
 */
public class PreferencesPanel extends JScrollPane implements IDataField {
	
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
	JButton updateCarrierButton;
	JComboBox<String> carrierDropDown;
	
	JLabel facebookOffNotify;
	JTextField facebookField;
	JButton updateFacebookButton;
	JCheckBox facebookCheckBox;

    public PreferencesPanel() {

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
    	emailField.setFocusable(true);
    	addKeyListenerTo(emailField);
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
    	facebookOffNotify = new JLabel("*You are not receiving facebook notifications");
    	facebookOffNotify.setForeground(Color.blue);
    	if (!receivingFacebook()) {
    		facebookOffNotify.setVisible(true);
    	}
    	else {
    		facebookOffNotify.setVisible(false);
    	}
    	facebookPanel.add(facebookOffNotify);
    	
    	//Create and add the user facebook label to the panel
    	JLabel userfacebookLabel = new JLabel("Your facebook:");
    	facebookPanel.add(userfacebookLabel);
    	
    	//Create, configure, and add the user facebook text box
    	facebookField = new JTextField(50);
    	facebookField.setEditable(true);
    	facebookField.setFocusable(true);
    	addKeyListenerTo(facebookField);
    	facebookField.setText("facebook.username");
    	facebookPanel.add(facebookField);
   
    	/**
    	 * TODO autopopulate facebook field with user's facebook.
    	 */
    	
    	//Create the update facebook button
    	updateFacebookButton = new JButton("Update facebook");
    	updateFacebookButton.setEnabled(false);
    	facebookPanel.add(updateFacebookButton);
    	
    	//Create and add the checkbox for receiving facebooks
    	facebookCheckBox = new JCheckBox("Receive facebook notifications", true);
    	//TODO make this field initialize to the correct toggled state. Do that by modifying the constant "true" above
    	
    	facebookCheckBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				facebookCheckBoxListener();	
			}
		});
    	
    	reValidateFacebookPanel();
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
    	mobileOffNotify = new JLabel("*You are not receiving mobile notifications");
    	mobileOffNotify.setForeground(Color.blue);
    	if (!receivingMobile()) {
    		mobileOffNotify.setVisible(true);
    	}
    	else {
    		mobileOffNotify.setVisible(false);
    	}
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
    	 * TODO autopopulate mobile field with user's number.
    	 */
    	
    	//Create the update mobile button
    	updateMobileButton = new JButton("Update Number");
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
    	carrierDropDown = new JComboBox<String>(items);
    	carrierDropDown.setSelectedIndex(getUserCarrier());
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
    	layout.putConstraint(SpringLayout.NORTH, emailPanel, 20, SpringLayout.SOUTH, headingLabel);	
    	layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, emailPanel, 0, SpringLayout.HORIZONTAL_CENTER, view);
//      layout.putConstraint(SpringLayout.EAST, emailPanel, -5, SpringLayout.EAST, view);
//    	layout.putConstraint(SpringLayout.WEST, emailPanel, 5, SpringLayout.WEST, view);
    	
    	
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
    	
    	//Put constraints on the email checkbox
    	emailLayout.putConstraint(SpringLayout.NORTH, emailCheckBox, 5, SpringLayout.SOUTH, userEmailLabel);
    	emailLayout.putConstraint(SpringLayout.WEST, emailCheckBox, 20, SpringLayout.WEST, emailPanel);
    	
    	/**
    	 * Put constraints on the facebook preferences panel 
    	 */
//    	layout.putConstraint(SpringLayout.WEST, facebookPanel, 5, SpringLayout.WEST, view);
//      layout.putConstraint(SpringLayout.EAST, facebookPanel, -5, SpringLayout.EAST, view);
    	layout.putConstraint(SpringLayout.NORTH, facebookPanel, 20, SpringLayout.SOUTH, emailPanel);	
       	layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, facebookPanel, 0, SpringLayout.HORIZONTAL_CENTER, view);
//      
       	
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
    	
    	//Put constraints on the facebook checkbox
    	facebookLayout.putConstraint(SpringLayout.NORTH, facebookCheckBox, 5, SpringLayout.SOUTH, userfacebookLabel);
    	facebookLayout.putConstraint(SpringLayout.WEST, facebookCheckBox, 20, SpringLayout.WEST, facebookPanel);

     	
    	/**
    	 * put constraints on the mobile preferences panel 
    	 */
//    	layout.putConstraint(SpringLayout.WEST, mobilePanel, 5, SpringLayout.WEST, view);
//    	layout.putConstraint(SpringLayout.EAST, mobilePanel, -5, SpringLayout.EAST, view);
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
    
	private void addKeyListenerTo(JComponent component){
		component.addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent arg0) {
				if (emailField.isFocusOwner()) {
					System.out.println("Here:");
					reValidateEmailUpdateButton();
				}
				else if(facebookField.isFocusOwner()) {
					System.out.println("Here:");
					reValidateFacebookUpdateButton();
				}
				else if(mobileField.isFocusOwner()) {
					System.out.println("Here:");
					reValidateMobileUpdateButton();
				}
				else {}
			}
		});
	}
    
	
	private String getUserEmail() {
		// TODO Auto-generated method stub	
		
		return null;
	}
	
	private String getUserPhoneNumber() {
		// TODO Auto-generated method stub	
		
		return null;
	}
	
	private int getUserCarrier() {
		// TODO Auto-generated method stub	
		
		return 0;
	}
	
	private String getUserFacebookUsername() {
		// TODO Auto-generated method stub	
		
		return null;
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
	
	public boolean receivingEmail() {
		//TODO
		return true;
	}
	
	public boolean receivingFacebook() {
		//TODO
		return true;
	}
	
	public boolean receivingMobile() {
		//TODO 
		return true;
	}
	
	/**
	 * displays the option to update the user's email if 
	 * the user selected the option to be notified through email
	 */
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
			reValidateFacebookUpdateButton();
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
			updateCarrierButton.setEnabled(false);
			updateMobileButton.setEnabled(false);
		}
		else {
			mobileOffNotify.setVisible(false);
			
			
			mobileField.setEnabled(true);
			carrierDropDown.setEnabled(true);
			updateCarrierButton.setEnabled(true);
			updateMobileButton.setEnabled(true);
			reValidateMobileUpdateButton(); //TODO fix this method to include both update buttons
		}
	}
	
	public void reValidateEmailPanel() {
		emailCheckBoxListener();
		reValidateEmailUpdateButton();
	}
	
	public void reValidateFacebookPanel() {
		facebookCheckBoxListener();
		reValidateFacebookUpdateButton();
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
	
	public void reValidateFacebookUpdateButton() {
		if (verifyFacebookField()) {
			updateFacebookButton.setEnabled(true);
		}
		else {
			updateFacebookButton.setEnabled(false);
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
				System.out.println("Atsubstring:" + atSubstring);
			}	
		}
		if (atCount == 1 && atIndex != 0 && atIndex != -1 && atIndex+1 != text.length() ) {
			atBoolean = true;
		}
		
		//Code for checking the at substring
		boolean atSubstringBool = true;
		if (atSubstring.length() < 3) {
			System.out.println("The length of the at substring is too short");
			atSubstringBool = false;
		}
		
		//Code for checking forbidden character
		String forbiddenChars = "()<>[]\\:,;!#$%&'*+-/=?^_`{}| ~\" ";
		boolean forbiddenCharsBool = true;
		for (int i = 0; i < text.length(); i++) {
			currChar = Character.toString(text.charAt(i));
			if (forbiddenChars.contains(currChar)) {
				System.out.println("ForbiddenChar present:" + currChar);
				forbiddenCharsBool = false;
			}
		}
		
		//Checking dot substring
		String dotSubstring;
		boolean dotSubstringBool = true;
//	    for (int i = 0; i < text.length(); i++) {
//			currChar = Character.toString(text.charAt(i));
//			if (currChar.equals(".")) {
//				dotSubstring = text.substring(i + 1);
//				if (dotSubstring.length() != 3 || i <= atIndex + 1) {
//					dotSubstringBool = false;
//				}
//			}
//			
//	    }
			
			
 		return atBoolean && forbiddenCharsBool && atSubstringBool && dotSubstringBool;
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
		//boolean forbiddenCharsBool = true;
		String currChar;
		for (int i = 0; i < text.length(); i++) {
			currChar = Character.toString(text.charAt(i));
			if (forbiddenChars.contains(currChar)) {
				System.out.println("ForbiddenChar present:" + currChar);
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

	@Override
	public boolean validateField(IErrorView warningField, boolean showLabel,
			boolean showBox) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
}