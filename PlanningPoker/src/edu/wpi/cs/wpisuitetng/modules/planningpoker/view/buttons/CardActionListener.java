package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JToggleButton;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.EstimatePanel;

public class CardActionListener implements ActionListener {
	int index;
	int size;
	int sum;
	EstimatePanel panel;
//	ArrayList<Integer> memoryArray = new ArrayList<Integer>();
	ArrayList<String> deck;
	ArrayList<JToggleButton> JToggleButtonList;
	
	public CardActionListener(int index, int passedSum, ArrayList<String> deckUsed, ArrayList<JToggleButton> JToggleButtonListUsed, EstimatePanel passedPanel) {
		this.index = index;
		this.size = JToggleButtonListUsed.size();
		this.sum = passedSum;
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
//						memoryArray.add(i);
						JToggleButtonList.get(i).doClick();
					}
				}
			}
//			else {
//				for (int i = 0; i < memoryArray.size(); i++) {
//					JToggleButtonList.get(memoryArray.get(i)).doClick();
//				}
//				memoryArray.clear();	
//			}
		}
		else { //otherwise, if it is a button other than the I don't know button
			if(JToggleButtonList.get(index).isSelected()) { // if button is pressed
				if (JToggleButtonList.get(size - 1).isSelected()) { 
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
	
//	public void clearMemoryArray() {
//		
//	}


	
	
}
