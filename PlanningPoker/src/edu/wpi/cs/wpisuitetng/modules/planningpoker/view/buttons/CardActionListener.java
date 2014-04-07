package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JToggleButton;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.EstimatePanel;

public class CardActionListener implements ActionListener {
	int index;
	int size;
	EstimatePanel panel;
	ArrayList<String> deck;
	ArrayList<JToggleButton> JToggleButtonList;
	
	public CardActionListener(int index, ArrayList<String> deckUsed, ArrayList<JToggleButton> JToggleButtonListUsed, EstimatePanel passedPanel) {
		this.index = index;
		this.size = JToggleButtonListUsed.size();
		this.deck = deckUsed;
		this.panel = passedPanel;
		this.JToggleButtonList = JToggleButtonListUsed;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//if the button is the I don't know button 
		if (index == (size - 1)) { 
			if(JToggleButtonList.get(index).isSelected()) {
				for (int i = 0; i < (deck.size()); i++){
					if (JToggleButtonList.get(i).isSelected()){
						panel.memoryArrayAddElt(i);
						JToggleButtonList.get(i).doClick();
					}
				}
			}
			else {
				for (int i = 0; i < panel.memoryArrayGetSize(); i++) {
					JToggleButtonList.get(panel.memoryArrayGetElt(i)).doClick();
				}
				panel.memoryArrayClear();	
			}
		}
		else { //otherwise, if it is a button other than the I don't know button
			if(JToggleButtonList.get(index).isSelected()) { // if button is pressed
				if (JToggleButtonList.get(size - 1).isSelected()) { 
					panel.memoryArrayClear();
					JToggleButtonList.get(size - 1).doClick();
				}
				addToCardSum(Integer.parseInt(deck.get(index)));
			}
			else {
				decToCardSum(Integer.parseInt(deck.get(index)));
			}
		}

		
	}
	
	public void addToCardSum(int cardValue) {
		panel.addToCardSum(cardValue);
	}
	
	public void decToCardSum(int cardValue) {
		panel.decToCardSum(cardValue);
	}	
}
