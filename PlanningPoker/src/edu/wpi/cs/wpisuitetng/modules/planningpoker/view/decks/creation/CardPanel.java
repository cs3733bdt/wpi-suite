package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.decks.creation;

import java.awt.Dimension;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.IDataField;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.IErrorView;

public class CardPanel extends JPanel implements IDataField {
	
	private CreateDeckPanel parent;
	private final CardImage cardRed;
	
	private List<CardImage> cards;

	public CardPanel(CreateDeckPanel parent) {
		this.parent = parent;
		cardRed = new CardImage(ColorEnum.RED, parent);
		cards = new ArrayList<CardImage>();
		
		build();
		
		revalidate();
		repaint();
	}
	
	private void build(){
		setPreferredSize(new Dimension(10, 450));
		add(cardRed); // adds initial card to panel
		cards.add(cardRed);
		cardRed.setVisible(true);
	}
	
	/**
	 * Changes every card in the deck to have the new color
	 * @param color the color you want the deck to be
	 */
	public void setDeckColor(ColorEnum color){
		for(CardImage c: cards){
			c.setColor(color);
		}
	}

	/**
	 * Retrieves all of the values from the cards currently in the pannel
	 * @return a list of the integers for all of the cards
	 */
	private List<Integer> getCardValues(){
		List<Integer> values = new ArrayList<Integer>();
		for(CardImage c : cards){
			values.add(c.getCardValue());
		}
		return values;
	}
	
	@Override
	public boolean validateField(IErrorView warningField, boolean showLabel,
			boolean showBox) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasChanges() {
		// TODO Auto-generated method stub
		return false;
	}

}
