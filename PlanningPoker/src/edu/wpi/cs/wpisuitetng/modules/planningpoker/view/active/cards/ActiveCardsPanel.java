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

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.EstimatePanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.IDataField;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.IErrorView;

public class ActiveCardsPanel extends JPanel implements IDataField {

	private int sum = 0;
	private ArrayList<String> deck;
	private ArrayList<CardButton> JToggleButtonList = new ArrayList<CardButton>();
	JLabel counterLabel = new JLabel("Your current estimate total: " + 0);

	//initialized array to remember what buttons were pressed if "0?" button is pressed
    private ArrayList<Integer> memoryArray = new ArrayList<Integer>();
    private EstimatePanel panel;
	
	public ActiveCardsPanel(ArrayList<String> passedDeck, EstimatePanel passedPanel) {
		this.panel = passedPanel;
		this.deck = passedDeck;
		
		this.setPreferredSize(new Dimension(800, (68 * (Math.round(deck.size() / 11)) ) ) ); //800,100
		for (int i = 0; i < (deck.size()); i++) {
			this.JToggleButtonList.add(new CardButton(i, deck, this, panel));
		}//idk button is part of array


		//adds the button to clear all entered estimates
		JButton clearButton = new JButton("Clr");
		clearButton.setToolTipText("Clear all Estimates");
		this.add(clearButton); 

		//action Listener for the clear button 
		clearButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				memoryArray.clear();
				try {
					Image frontImg = ImageIO.read(getClass().getResource("card_front.png"));
					for (int i = 0; i < (deck.size()-1); i++){
						if (JToggleButtonList.get(i).isSelected()){
							JToggleButtonList.get(i).doClick();
							JToggleButtonList.get(i).setIcon(new ImageIcon(frontImg));
						}
					}
					if (JToggleButtonList.get(deck.size()-1).isSelected()) {
						JToggleButtonList.get(deck.size()-1).doClick();
					}
					sum = 0;
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		});	
		
		if (!panel.getGame().doesUseCards()) {
			clearButton.setVisible(false);
		}
	
	}

public int getCount() {
	return 0;
}

public void addToCardSum(int cardValue) {
	sum += cardValue;
	counterLabel.setText("Your current estimate total: " + sum);
	System.out.println(sum);
}

/*
 * Decrease total sum by amount entered
 */
public void decToCardSum(int cardValue) {
	sum -= cardValue;
	counterLabel.setText("Your current estimate total: " + sum);
	System.out.println(sum);
}

/*
 * Clicks all the buttons. Used for testing
 */
public void doClicks() {
	for (int i = 0; i < JToggleButtonList.size(); i++) {
		JToggleButtonList.get(i).doClick();
	}
}
/*
 * Returns the sum of all the cards
 */
public int getMaxSum() {
	int sum = 0;
	for (int i = 0; i < deck.size(); i++) {
		sum += Integer.parseInt(deck.get(i));
	}
	return sum;
}

public int getSum() {
	return sum;
}

public ArrayList<CardButton> getCardButtonArray() {
	return JToggleButtonList; 
}
/*
 * adds an element to the array of buttons to be remembered when the "0?" button is unpress
 */
public void memoryArrayAddElt(int elt) {
	memoryArray.add(elt);
}

/*
 * getter for size of the memory array
 */
public int memoryArrayGetSize() {
	return memoryArray.size();
}

/*
 * clears memory array; use after the values stored in the array are restored
 */
public void memoryArrayClear() {
	memoryArray.clear();
}

/*
 * adds index of the button to be remembered to the memory array
 */
public int memoryArrayGetElt(int elt) {
	return memoryArray.get(elt);
}

@Override
public boolean verifyField(IErrorView warningField) {
	return false;
}

}