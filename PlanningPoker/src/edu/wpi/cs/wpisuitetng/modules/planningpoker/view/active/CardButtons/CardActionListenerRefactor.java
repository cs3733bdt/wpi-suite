package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.CardButtons;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.EstimatePanel;

public class CardActionListenerRefactor implements ActionListener {

	//Size is the size of the button array including the "IDK" button
	int size;
	JToggleButton button;
	int index;
	ArrayList<String> deck;
	ActiveCardsPanel cardsPanel;
	EstimatePanel estimatePanel;
	
	/**
	 * @param index of the button to be created
	 * @param deckUsed to create the button
	 */
	public CardActionListenerRefactor(int index, ArrayList<String> deckUsed, JToggleButton passedButton, ActiveCardsPanel passedCardsPanel, EstimatePanel passedEstimatePanel) {
		this.size = deck.size() + 1;
		this.cardsPanel = passedCardsPanel;
		this.estimatePanel = passedEstimatePanel;
		this.deck = deckUsed;
		this.index = index;
		this.button = passedButton;
	}	 
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			Image frontImg = ImageIO.read(getClass().getResource("card_front.png"));
			Image backImg =  ImageIO.read(getClass().getResource("card_back.png"));	
			//if the button is the I don't know button 
			if (index == (size - 1)) { 
				if(button.isSelected()) { //if the IDK button is toggled on
					for (int i = 0; i < (deck.size()); i++){
						if (cardsPanel.getCardButtonArray().get(i).isSelected()){
							cardsPanel.memoryArrayAddElt(i);
							cardsPanel.getCardButtonArray().get(i).doClick();
						}
					}
				}
				else { //if the IDK button is toggled off
					for (int i = 0; i < cardsPanel.memoryArrayGetSize(); i++) {
						cardsPanel.getCardButtonArray().get(cardsPanel.memoryArrayGetElt(i)).doClick();
					}
					cardsPanel.memoryArrayClear();	
				}
			}
			else { //otherwise, if it is a button other than the I don't know button
				if(button.isSelected()) { // if button is toggled on
					if (cardsPanel.getCardButtonArray().get(size - 1).isSelected()) { 
						cardsPanel.memoryArrayClear();
						cardsPanel.getCardButtonArray().get(size - 1).doClick();
					}
					button.setIcon(new ImageIcon(backImg));
					addToCardSum(Integer.parseInt(deck.get(index)));
				}
				else { // if button is toggled off
					button.setIcon(new ImageIcon(frontImg));
					decToCardSum(Integer.parseInt(deck.get(index)));
				}
			}	
		} catch (IOException ex) {}
	}

	public void addToCardSum(int cardValue) {
		cardsPanel.addToCardSum(cardValue);
		estimatePanel.updateSum();
	}

	public void decToCardSum(int cardValue) {
		cardsPanel.decToCardSum(cardValue);
		estimatePanel.updateSum();
	}	
}

