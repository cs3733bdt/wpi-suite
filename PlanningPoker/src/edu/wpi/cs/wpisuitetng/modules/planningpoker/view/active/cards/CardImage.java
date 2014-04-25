package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.cards;

/*******************************************************************************
* Copyright (c) 2012-2014 -- WPI Suite
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
* @author Team BobbyDropTables
*/
public class CardImage extends JPanel{
	JLabel picLabel;
	public CardImage(String color){
		if(color == "red"){
			BufferedImage myPicture = null;
			try {
				myPicture = ImageIO.read(getClass().getResource("card_back.png"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			picLabel = new JLabel(new ImageIcon(myPicture));
			add(picLabel);
		}
		
		if(color == "blue"){
			BufferedImage myPicture = null;
			try {
				myPicture = ImageIO.read(getClass().getResource("card_back_blue.png"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			picLabel = new JLabel(new ImageIcon(myPicture));
			add(picLabel);
		}
		
		if(color == "green"){
			BufferedImage myPicture = null;
			try {
				myPicture = ImageIO.read(getClass().getResource("card_back_green.png"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			picLabel = new JLabel(new ImageIcon(myPicture));
			add(picLabel);
		}
		
		if(color == "purple"){
			BufferedImage myPicture = null;
			try {
				myPicture = ImageIO.read(getClass().getResource("card_back_purple.png"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			picLabel = new JLabel(new ImageIcon(myPicture));
			add(picLabel);
		}
		
		if(color == "yellow"){
			BufferedImage myPicture = null;
			try {
				myPicture = ImageIO.read(getClass().getResource("card_back_yellow.png"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			picLabel = new JLabel(new ImageIcon(myPicture));
			add(picLabel);
		}
	
	}
	
	public void addBorder() {
		picLabel.setBackground(Color.green);//TODO get complementary color
		picLabel.setBorder(BorderFactory.createRaisedSoftBevelBorder());
	}
}