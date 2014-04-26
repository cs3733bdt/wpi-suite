/*******************************************************************************
* Copyright (c) 2012-2014 -- WPI Suite
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.decks.creation;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
* @author Team BobbyDropTables
*/
public class CardImage extends JPanel{
	
	ColorEnum color;
	
	private JTextField addValue = new JTextField();
	
	private JLabel valueLabel = new JLabel("");
	
	private GridBagConstraints c;
	
	public CardImage(ColorEnum color){
		this.color = color;
		BufferedImage myPicture = null;
		try {
			switch (color) {
			case RED:
				myPicture = ImageIO.read(getClass().getResource("card_back.png"));
				break;

			case BLUE:
				myPicture = ImageIO.read(getClass().getResource("card_back_blue.png"));
				break;

			case GREEN:
				myPicture = ImageIO.read(getClass().getResource("card_back_green.png"));
				break;

			case PURPLE:
				myPicture = ImageIO.read(getClass().getResource("card_back_purple.png"));
				break;

			case YELLOW:
				myPicture = ImageIO.read(getClass().getResource("card_back_yellow.png"));
				break;
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		c = new GridBagConstraints();
		
		JButton picButton = new JButton(new ImageIcon(myPicture));
		picButton.setBorderPainted(false);
		picButton.setContentAreaFilled(false);
		addMouseListenerToCard(picButton);
		
		c.gridx = 1;
		c.gridwidth = 1;
		c.insets = new Insets(10, 30, 0, 0);
		add(valueLabel, c);
		
		c.gridx = 1;
		c.gridwidth = 1;
		c.insets = new Insets(35, 30, 0, 0);
		add(addValue, c);
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		c.gridheight = 3;
		c.insets = new Insets(0, 0, 0, 0);
		add(picButton, c);
		
		addKeyListenerToAddValueText(addValue);
		focusListenerAddValueText(addValue);
		addValue.setPreferredSize(new Dimension(40, 18));
		addValue.setVisible(false);
		valueLabel.setVisible(false);
		
	}
	
	public ColorEnum getColor(){
		return color;
	}
	
	public int getCardValue(){
		return Integer.parseInt(valueLabel.getText());
	}
	
	private void addMouseListenerToCard(JComponent component){
		component.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent arg0) {
				addValue.setVisible(true);
				revalidate();
				repaint();
				addValue.requestFocus();
			}
		});
	}
	
	private void addKeyListenerToAddValueText(JComponent component){
		component.addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent arg0) {	
				if(arg0.getKeyCode() == 10){		//if enter is pressed
					valueLabel.setText(addValue.getText());
					valueLabel.setVisible(true);
					addValue.setVisible(false);
					revalidate();
					repaint();
				}
			}
		});
	}
	
	private void focusListenerAddValueText(JComponent component){
		component.addFocusListener(new FocusListener(){
			public void focusLost(FocusEvent e) {
				valueLabel.setText(addValue.getText());
				valueLabel.setVisible(true);
				addValue.setVisible(false);
				revalidate();
				repaint();
			}

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
}