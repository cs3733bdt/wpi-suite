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
import javax.swing.SpringLayout;

/**
 * Designs the preferences help menu
 * @author Kevin Zhao
 *
 */
public class PreferencesHelp extends JScrollPane implements IHelpPanel {
	JLabel headingLabel;
	JTextArea preferencesExplanation;
	JLabel emailHelp;
	JTextArea emailHelpExplanation;
	JLabel facebookHelp;
	JTextArea facebookHelpExplanation;
	JLabel mobileHelp;
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
		view.setPreferredSize(new Dimension(610, 500));
		
		//Add the heading label to the Panel
		headingLabel = new JLabel("Preferences Help");
		headingLabel.setFont(makeFont(8));
		
		//Add explanation for the preferences panel
		preferencesExplanation = new JTextArea();
		preferencesExplanation.setText("The Preferences Menu allows you to set whether or not "
				+ "you want to be notified by email, Facebook message, or mobile phone number "
				+ "when a game starts and ends. Upon checking one of the boxes, you can add or "
				+ "change the information in the textbox. After the information has been enterted "
				+ "you can save those changes by clicking the section's respective update button.");
		
		preferencesExplanation.setEditable(false);
		preferencesExplanation.setBackground(null);
		preferencesExplanation.setWrapStyleWord(true);
		preferencesExplanation.setLineWrap(true);
		
		//Add email image panel;
		emailHelp = addImage("email_preferences1.png");
		
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
		facebookHelp = addImage("facebook_preferences1.png");
		
		//Add explanation for the second image, the Facebook help
		facebookHelpExplanation = new JTextArea();
		facebookHelpExplanation.setText("This window allows you to choose whether or not you "
				+ "want to be notified by Facebook messages and allows you to add or update "
				+ "your Facebook username. The username must be a valid email address before "
				+ "it can be added or updated.");
		
		facebookHelpExplanation.setEditable(false);
		facebookHelpExplanation.setBackground(null);
		facebookHelpExplanation.setWrapStyleWord(true);
		facebookHelpExplanation.setLineWrap(true);
		
		//Add mobile image panel
		mobileHelp = addImage("mobile_preferences1.png");
		
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
		//view.add(preferencesExplanation);
		view.add(emailHelp);
		view.add(emailHelpExplanation);
		view.add(facebookHelp);
		view.add(facebookHelpExplanation);
		view.add(mobileHelp);
		view.add(mobileHelpExplanation);
		
		/**
		 * Constraints for the overall panel layout
		 */
		layout.putConstraint(SpringLayout.NORTH, headingLabel, 5, SpringLayout.NORTH, view);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, headingLabel, 0, SpringLayout.HORIZONTAL_CENTER, view);
		
		//layout.putConstraint(SpringLayout.NORTH, preferencesExplanation, 5, SpringLayout.SOUTH, headingLabel);
		//layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, preferencesExplanation, 0, SpringLayout.HORIZONTAL_CENTER, view);
	
		layout.putConstraint(SpringLayout.NORTH, emailHelp, 5, SpringLayout.SOUTH, headingLabel); // will change to preferencesExplanation when working
		layout.putConstraint(SpringLayout.WEST, emailHelp, 5, SpringLayout.WEST, view);
		
		layout.putConstraint(SpringLayout.NORTH, emailHelpExplanation, 5, SpringLayout.SOUTH, headingLabel); // will change to preferencesExplanation when working
		layout.putConstraint(SpringLayout.EAST, emailHelpExplanation, 0, SpringLayout.EAST, view);
		layout.putConstraint(SpringLayout.WEST, emailHelpExplanation, 20, SpringLayout.EAST, emailHelp);
		
		layout.putConstraint(SpringLayout.NORTH, facebookHelp, 20, SpringLayout.SOUTH, emailHelp);
		layout.putConstraint(SpringLayout.WEST, facebookHelp, 5, SpringLayout.WEST, view);
		
		layout.putConstraint(SpringLayout.NORTH, facebookHelpExplanation, 20, SpringLayout.SOUTH, emailHelp);
		layout.putConstraint(SpringLayout.EAST, facebookHelpExplanation, 0, SpringLayout.EAST, view);
		layout.putConstraint(SpringLayout.WEST, facebookHelpExplanation, 20, SpringLayout.EAST, facebookHelp);
		
		layout.putConstraint(SpringLayout.NORTH, mobileHelp, 20, SpringLayout.SOUTH, facebookHelp);
		layout.putConstraint(SpringLayout.WEST, mobileHelp, 5, SpringLayout.WEST, view);
		
		layout.putConstraint(SpringLayout.NORTH, mobileHelpExplanation, 20, SpringLayout.SOUTH, facebookHelp);
		layout.putConstraint(SpringLayout.EAST, mobileHelpExplanation, 0, SpringLayout.EAST, view);
		layout.putConstraint(SpringLayout.WEST, mobileHelpExplanation, 20, SpringLayout.EAST, mobileHelp);
		
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
