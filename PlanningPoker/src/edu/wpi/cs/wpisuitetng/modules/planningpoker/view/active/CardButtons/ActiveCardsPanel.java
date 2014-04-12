package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.CardButtons;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

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
		this.setPreferredSize(new Dimension(800, 100));
		for (int i = 0; i < deck.size(); i++) {
			this.add(new CardButton(i, deck, this, panel)); //TODO: figure out constructor
		}//idk button is part of array
		
		
		//adds the button to clear all entered estimates
		JButton clearButton = new JButton("Clr");
		clearButton.setToolTipText("Clear all Estimates");
		this.add(clearButton); 
		System.out.println(clearButton.getToolTipText());
		
		//action Listener for the clear button 
		clearButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				memoryArray.clear();
				try {
					Image frontImg = ImageIO.read(getClass().getResource("card_front.png"));
					for (int i = 0; i < (deck.size()); i++){
						if (JToggleButtonList.get(i).isSelected()){
							JToggleButtonList.get(i).doClick();
							JToggleButtonList.get(i).setIcon(new ImageIcon(frontImg));
						}
					}
					if (JToggleButtonList.get(deck.size()).isSelected()) {
						JToggleButtonList.get(deck.size()).doClick();
					}
					sum = 0;
				} catch (IOException ex) {}
			}
		});	
		
		if (!panel.getGame().doesUseCards()) {
			clearButton.setVisible(false);
		}
	
	}



	
	/*
	 * initializes all the buttons and add them to the panel, as well as adding images.
	 * the exception is required for getting image IO
	 */
//	 try {
//		 Image frontImg = ImageIO.read(getClass().getResource("card_front.png"));
//		 Image backImg =  ImageIO.read(getClass().getResource("card_back.png"));
//
//		 for (int i = 0; i < deck.size(); i++) {
//			 this.JToggleButtonList.add(new JToggleButton(deck.get(i)));
//			 this.JToggleButtonList.get(i).setIcon(new ImageIcon(frontImg));
//			 cardPanel.add(JToggleButtonList.get(i));
//		 }
//	 } catch (IOException ex) {}
	 
//	//add ToolTips
//	 for (int i = 0; i < deck.size(); i++) {
//		 this.JToggleButtonList.get(i).setToolTipText("Add " + deck.get(i) + " to the total");
//		 JToggleButtonList.get(i).setHorizontalTextPosition(SwingConstants.CENTER);
//		 JToggleButtonList.get(i).setVerticalAlignment(SwingConstants.CENTER);
//	 }
	 
	
	/*
	 * CHANGED LINE 260
	 */
//	//creates action listeners for all other buttons
//	for (int i = 0;  i < JToggleButtonList.size(); i++ ) { 
//	//	JToggleButtonList.get(i).addActionListener(new CardActionListenerRefactor(i, deck, JToggleButtonList.get(i), this));
//	}
	

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
	return new ArrayList<CardButton>(); //TODO implement
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
	// TODO Auto-generated method stub
	return false;
}

}