package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.CardButtons;

import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;

import javax.accessibility.Accessible;
import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.EstimatePanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.IDataField;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.IErrorView;

public class CardButton extends JToggleButton implements Accessible {
	JToggleButton button;

	public CardButton(int cardNum, ArrayList<String> deck, ActiveCardsPanel passedCardsPanel, EstimatePanel passedEstimatePanel){
		
		//Initialize the Button and the number on the button
		String buttonNum = deck.get(cardNum);
		boolean isIDK = (cardNum == (deck.size() + 1)); 
		
		
		//if the button isn't the idk button
		if (!isIDK) {
			button = new JToggleButton(buttonNum);
			try {
				Image frontImg = ImageIO.read(getClass().getResource("card_front.png"));
				this.button.setIcon(new ImageIcon(frontImg));
			} catch (IOException ex) {}
			//Set the Button's tooltiptext and position it correctly
			button.setToolTipText("Add " + buttonNum + " to the total");
		}
		else {
			button = new JToggleButton("0?");
			button.setToolTipText("I don't know what to estimate");
		}
	
		 button.setHorizontalTextPosition(SwingConstants.CENTER);
		 button.setVerticalAlignment(SwingConstants.CENTER);
		 
		 //Add the action listener to the button
		 button.addActionListener(new CardActionListenerRefactor(cardNum, deck, button, passedCardsPanel, passedEstimatePanel));

	}
}
