package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

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
			 try {
				 Image frontImg = ImageIO.read(getClass().getResource("card_front.png"));
				 Image backImg =  ImageIO.read(getClass().getResource("card_back.png"));	
				 //if the button is the I don't know button 
				 if (index == (size - 1)) { 
					 if(JToggleButtonList.get(index).isSelected()) { //if the IDK button is toggled on
						 for (int i = 0; i < (deck.size()); i++){
							 if (JToggleButtonList.get(i).isSelected()){
								 panel.memoryArrayAddElt(i);
								 JToggleButtonList.get(i).doClick();
							 }
						 }
					 }
					 else { //if the IDK button is toggled off
						 for (int i = 0; i < panel.memoryArrayGetSize(); i++) {
							 JToggleButtonList.get(panel.memoryArrayGetElt(i)).doClick();
						 }
						 panel.memoryArrayClear();	
					 }
				 }
				 else { //otherwise, if it is a button other than the I don't know button
					 if(JToggleButtonList.get(index).isSelected()) { // if button is toggled on
						 if (JToggleButtonList.get(size - 1).isSelected()) { 
							 panel.memoryArrayClear();
							 JToggleButtonList.get(size - 1).doClick();
						 }
						 JToggleButtonList.get(index).setIcon(new ImageIcon(backImg));
						 addToCardSum(Integer.parseInt(deck.get(index)));
						 
					 }
					 else { // if button is toggled off
						 JToggleButtonList.get(index).setIcon(new ImageIcon(frontImg));
						 decToCardSum(Integer.parseInt(deck.get(index)));
					 }
				 }	
			 } catch (IOException ex) {}
		 }
	
	public void addToCardSum(int cardValue) {
		panel.addToCardSum(cardValue);
	}
	
	public void decToCardSum(int cardValue) {
		panel.decToCardSum(cardValue);
	}	
}
