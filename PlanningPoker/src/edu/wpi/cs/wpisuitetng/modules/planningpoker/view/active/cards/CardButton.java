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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.cards;

import java.awt.Image;
import java.io.IOException;
import java.util.List;

import javax.accessibility.Accessible;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.NewRightHalfActiveGamePanel;

public class CardButton extends JToggleButton implements Accessible {
	JToggleButton button;
	List<String> deck;
	ActiveCardsPanel panel;

	public CardButton(int cardNum, List<String> passedDeck, ActiveCardsPanel passedCardsPanel, NewRightHalfActiveGamePanel panel2){
		this.deck = passedDeck;
		this.panel = passedCardsPanel;
		deck = passedDeck;
		panel = passedCardsPanel;
		
		//Initialize the Button and the number on the button
		String buttonNum;
		boolean isIDK = (cardNum == (deck.size() - 1)); 		
		
		//if the button isn't the idk button
		if (!isIDK) {
			buttonNum = deck.get(cardNum);
			//button = new JToggleButton(buttonNum);
			try {
				Image frontImg = ImageIO.read(getClass().getResource("card_front.png"));
				this.setIcon(new ImageIcon(frontImg));
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			//Set the Button's tooltiptext and position it correctly
			this.setText(deck.get(cardNum));
			this.setToolTipText("Add " + buttonNum + " to the total");
		}
		else {
			this.setText("0?");
			this.setToolTipText("I don't know what to estimate");
		}
	
		 this.setHorizontalTextPosition(SwingConstants.CENTER);
		 this.setVerticalAlignment(SwingConstants.CENTER);
		 
		 //Add the action listener to the button
		this.addActionListener(new CardActionListenerRefactor(cardNum, deck, this, passedCardsPanel, panel2));
		panel.add(this);
	}

	public String getValue() {
		return this.getText();
	}
}
