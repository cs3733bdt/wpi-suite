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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.help;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;

/**
 * Designs the preferences help menu
 * @author Kevin Zhao
 *
 */
public class PreferencesHelp extends JScrollPane implements IHelpPanel {
	JLabel headingLabel;
	JLabel emailHelpHeading;
	JLabel emailHelppic;
	JTextArea emailHelpExplanation;
	JLabel facebookHelpHeading;
	JLabel facebookHelppic;
	JTextArea facebookHelpExplanation;
	JLabel mobileHelpHeading;
	JLabel mobileHelppic;
	JTextArea mobileHelpExplanation;
	
	public PreferencesHelp() {
		build();
	}
	
	private void build() {
		/**
		 *  Set up initial container with spring layout */
		Container view = new Container();
		SpringLayout layout = new SpringLayout();
		view.setLayout(layout);
		view.setPreferredSize(new Dimension(1000, 620));
		//Add the heading label to the Panel
		headingLabel = new JLabel("Preferences Help");
		headingLabel.setFont(makeFont(8));
		setMinimumSize(new Dimension(1600, 620));
		setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		//Add email image panel;
		emailHelppic = addImage("email_preferences1.png");
		
		//Add label to explanation for email
		emailHelpHeading = new JLabel("Updating Email");
		emailHelpHeading.setFont(makeFont(5));
		
		
		
		//Add explanation for the first image, the email help
		emailHelpExplanation = new JTextArea();
		emailHelpExplanation.setText("This window allows you to choose whether or not you "
				+ "want to recieve email notifications and allows you to add or update "
				+ "your email address. The email must be a valid email address before "
				+ "it can be added or updated.");
		
		emailHelpExplanation.setEditable(false);
		emailHelpExplanation.setBackground(null);
		emailHelpExplanation.setWrapStyleWord(true);
		emailHelpExplanation.setLineWrap(true);
				
		//Add Facebook image panel
		facebookHelppic = addImage("facebook_preferences1.png");
		
		//Add label to explanation for facebook
		facebookHelpHeading = new JLabel("Updating Facebook Username");
		facebookHelpHeading.setFont(makeFont(5));
		
		//Add explanation for the second image, the Facebook help
		facebookHelpExplanation = new JTextArea();
		facebookHelpExplanation.setText("This window allows you to choose whether or not you "
				+ "want to be notified by Facebook messages and allows you to add or update "
				+ "your Facebook username. Your facebook username is in the link to your profile."
				+ "In the link the username is the text after \"www.facebook.com/\" "
				+ "The username must be a valid username before it can be added or updated.");
		
		facebookHelpExplanation.setEditable(false);
		facebookHelpExplanation.setBackground(null);
		facebookHelpExplanation.setWrapStyleWord(true);
		facebookHelpExplanation.setLineWrap(true);
		
		//Add mobile image panel
		mobileHelppic = addImage("mobile_preferences1.png");
		
		//Add label to mobile explanation
		mobileHelpHeading = new JLabel("Updating Mobile Phone");
		mobileHelpHeading.setFont(makeFont(5));
		
		//Add explanation for the third image, the mobile help
		mobileHelpExplanation = new JTextArea();
		mobileHelpExplanation.setText("This window allows you to choose whether or not you "
				+ "want to be notified by mobile text messages and allows you to add or update "
				+ "your mobile phone number. A valid phone number and a carrier from the list "
				+ "must be selected before the adding or updating is allowed.");
		
		mobileHelpExplanation.setEditable(false);
		mobileHelpExplanation.setBackground(null);
		mobileHelpExplanation.setWrapStyleWord(true);
		mobileHelpExplanation.setLineWrap(true);
		
		view.add(headingLabel);
		view.add(emailHelppic);
		view.add(emailHelpHeading);
		view.add(emailHelpExplanation);
		view.add(facebookHelppic);
		view.add(facebookHelpHeading);
		view.add(facebookHelpExplanation);
		view.add(mobileHelppic);
		view.add(mobileHelpHeading);
		view.add(mobileHelpExplanation);
		
		/**
		 * Constraints for the overall panel layout
		 */
		layout.putConstraint(SpringLayout.NORTH, headingLabel, 5, SpringLayout.NORTH, view);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, headingLabel, 0, SpringLayout.HORIZONTAL_CENTER, view);
		
		//Email
		layout.putConstraint(SpringLayout.NORTH, emailHelpHeading, 5, SpringLayout.SOUTH, headingLabel); 
		layout.putConstraint(SpringLayout.EAST, emailHelpHeading, 0, SpringLayout.EAST, view);
		layout.putConstraint(SpringLayout.WEST, emailHelpHeading, 20, SpringLayout.WEST, view);
		
		layout.putConstraint(SpringLayout.NORTH, emailHelppic, 20, SpringLayout.SOUTH, emailHelpHeading); 
		layout.putConstraint(SpringLayout.WEST, emailHelppic, 5, SpringLayout.WEST, view);
		
		layout.putConstraint(SpringLayout.NORTH, emailHelpExplanation, 20, SpringLayout.SOUTH, emailHelpHeading); 
		layout.putConstraint(SpringLayout.EAST, emailHelpExplanation, 0, SpringLayout.EAST, view);
		layout.putConstraint(SpringLayout.WEST, emailHelpExplanation, 20, SpringLayout.EAST, emailHelppic);
		
		//Facebook
		layout.putConstraint(SpringLayout.NORTH, facebookHelpHeading, 20, SpringLayout.SOUTH, emailHelppic);
		layout.putConstraint(SpringLayout.EAST, facebookHelpHeading, 0, SpringLayout.EAST, view);
		layout.putConstraint(SpringLayout.WEST, facebookHelpHeading, 20, SpringLayout.WEST, view);
		
		layout.putConstraint(SpringLayout.NORTH, facebookHelppic, 20, SpringLayout.SOUTH, facebookHelpHeading);
		layout.putConstraint(SpringLayout.WEST, facebookHelppic, 5, SpringLayout.WEST, view);
		
		layout.putConstraint(SpringLayout.NORTH, facebookHelpExplanation, 20, SpringLayout.SOUTH, facebookHelpHeading);
		layout.putConstraint(SpringLayout.EAST, facebookHelpExplanation, 0, SpringLayout.EAST, view);
		layout.putConstraint(SpringLayout.WEST, facebookHelpExplanation, 20, SpringLayout.EAST, facebookHelppic);
		
		//Mobile
		layout.putConstraint(SpringLayout.NORTH, mobileHelpHeading, 20, SpringLayout.SOUTH, facebookHelppic);
		layout.putConstraint(SpringLayout.EAST, mobileHelpHeading, 0, SpringLayout.EAST, view);
		layout.putConstraint(SpringLayout.WEST, mobileHelpHeading, 20, SpringLayout.WEST, view);
		
		layout.putConstraint(SpringLayout.NORTH, mobileHelppic, 20, SpringLayout.SOUTH, mobileHelpHeading);
		layout.putConstraint(SpringLayout.WEST, mobileHelppic, 5, SpringLayout.WEST, view);
		
		layout.putConstraint(SpringLayout.NORTH, mobileHelpExplanation, 20, SpringLayout.SOUTH, mobileHelpHeading);
		layout.putConstraint(SpringLayout.EAST, mobileHelpExplanation, 0, SpringLayout.EAST, view);
		layout.putConstraint(SpringLayout.WEST, mobileHelpExplanation, 20, SpringLayout.EAST, mobileHelppic);
		
		setViewportView(view);
		revalidate();
		repaint();
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
	public boolean readyToRemove() {
		return true;
	}

	@Override
	public int getIdentifierIndex() {
		return 0;
	}
	
	@Override
	public JLabel addImage(String image){
		JLabel helpLabel = new JLabel();
		
		try {
			Image imageToBeAdded = ImageIO.read(getClass().getResource(image));
			ImageIcon helpImage = new ImageIcon(imageToBeAdded);
			helpLabel = new JLabel(helpImage);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return helpLabel;
		
	}

}
