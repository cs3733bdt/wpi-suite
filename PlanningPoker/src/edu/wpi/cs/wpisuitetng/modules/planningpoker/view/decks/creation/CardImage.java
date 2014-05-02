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
import java.awt.Font;
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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.IDataField;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.IErrorView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.IValidateButtons;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.NumberJTextField;

/**
 * Creates a CardImage, which is to be used to display the cards when creating a deck, as well
 * as when voting on an estimate.
 * For the deck creation, it will have a switchable color,
 * and fields where the user can input and save the value.
 * For the voting, it will have a permanent color and value.
 * @author Team BobbyDropTables
 */
public class CardImage extends JPanel implements IDataField{
	
	private ColorEnum color;									//card's back color
	
	private NumberJTextField addValue = new NumberJTextField();		//the textfield that pops up when a user clicks a card, in which the user enters the desired value for the card
	
	private JLabel valueLabel = new JLabel("");			//the label that displays the value for each card as chosen by the user
	
	private JButton picButton; //The button where the image is displayed
	
	private GridBagConstraints c;

	private IErrorView errorField;
	
	private CardPanel parent;
	
	private static Logger logger = Logger.getLogger(CardImage.class.getName());
	

	public CardImage(ColorEnum color, CardPanel parent){
		errorField = parent.getCardPanelParent().getErrorField();
		this.parent = parent;
		this.color = color;
		//cards = parent.getCards();
		addValue.setIErrorView(errorField);
		addValue.setMaxValue(999);
		addValue.setMinValue(1);
		
		BufferedImage myPicture = null;
		try {
			myPicture = ColorCardImage.getColorCardImage(color);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Failed to load the images", e);
		}
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);											//sets the layout of the class (the panel)
		c = new GridBagConstraints();
		
		picButton = new JButton(new ImageIcon(myPicture));	//creates a button that holds the image that was rendered in the switch above
		picButton.setBorderPainted(false);							//sets the button's border to invisible
		picButton.setContentAreaFilled(false);						//sets the button's area to invisible so only the picture is visible
		addMouseListenerToCard(picButton);							//adds a mouselistener (see method) to the button
		
		c.gridx = 1;
		c.gridwidth = 1;
		c.insets = new Insets(10, 30, 0, 0);						//sets the layout constraints of the label that displays the cards value
		add(valueLabel, c);											//adds the label to the card panel
		
		c.gridx = 1;
		c.gridwidth = 1;
		c.insets = new Insets(35, 30, 0, 0);						//sets the layout constraints of the textfield in which the user enters the desired card value
		add(addValue, c);											//adds the textfield to the card panel
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		c.gridheight = 3;
		c.insets = new Insets(0, 0, 0, 0);							//sets the layout constraints of the actual button
		add(picButton, c);											//adds the button to the card panel
		
		addKeyListenerToAddValueText(addValue,parent.getCardPanelParent());						//adds a key listener to the textfield (see method)
		focusListenerAddValueText(addValue);						//adds a focus listener to the textfield (see method)
		addValue.setPreferredSize(new Dimension(40, 18));			//sets the textfield to be the desired size.
		addValue.setVisible(true);									//sets the textfield to be visible at the start
		valueLabel.setVisible(false);								//sets the label to be invisible at the start
		valueLabel.setFont(makeFont(10));
	}
	/**
	 * A getter for the card's color
	 * @return color
	 */
	public ColorEnum getColor(){
		return color;
	}
	
	/**
	 * A getter for the card's value. This is not current functional since the label is reset through various ways in the GUI,
	 * and also because the label is not yet associated with a specific card.
	 * @return label's text as int
	 */
	public Integer getCardValue() throws NumberFormatException{
		if(valueLabel.getText().equals("")){
			throw new NumberFormatException();
		}
		try { return Integer.parseInt(valueLabel.getText()); 
		} catch(NumberFormatException e) {
			throw e;
		}
	}
	
	/**
	 * Changes the color on this card to be the new color
	 * @param color the new color to set
	 */
	public void setColor(ColorEnum color){
		BufferedImage myPicture = null;
		try {
			myPicture = ColorCardImage.getColorCardImage(color);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Failed to load the images", e);
		}
		picButton.setIcon(new ImageIcon(myPicture));
		revalidate();
		repaint();
		parent.revalidate();
		parent.repaint();
	}
	
	/**
	 * A mouse listener for the card (button).
	 * When it is clicked, the textfield in which the user can enter the desired value for that card is made visible.
	 * It also sets the focus to the text field as soon as it becomes visible.
	 * @param component (this method should only be used with the card)
	 */
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
	
	/**
	 * A key listener for the textfield.
	 * When enter key is hit, it sets the input of the textfield to the label's text to display the chosen value.
	 * It then maks the label visible, and the textfield invisible.
	 * TODO: validation. the text should only be an integer in a reasonable range (0-50? 0-100?...)
	 * @param component (this method should only be used with the textfield)
	 */
	private void addKeyListenerToAddValueText(JComponent component,final IValidateButtons buttonParent){
		component.addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent arg0) {	
				if(arg0.getKeyCode() == 10){		//if enter is pressed
					if(!addValue.validateField(errorField, true, false)){
						logger.log(Level.INFO, "addValue was not validated");
						//TODO: errorField.setText("The value for the card must be an integer between 1 and 999");
						//TODO: but errorField is in CreateDeckPanel... HALP
						return;
					} else {
						buttonParent.updateButtons();
						logger.log(Level.INFO, "addValue was validated");
					}
					int indexOfEnteredCard = 0;
					
					List<CardImage> cards = parent.getCards();
					
					for(CardImage c : cards){
						if(c.addValue.hasFocus()){
							indexOfEnteredCard = cards.indexOf(c);
						}
					}
					valueLabel.setText(addValue.getText());
					//TODO here the value in the value array needs to be set for the array to function.
					valueLabel.setVisible(true);
					addValue.setVisible(false);
					if(!(indexOfEnteredCard + 1 >= cards.size())){
						cards.get(indexOfEnteredCard + 1).addValue.requestFocus();
					}
					
					revalidate();
					repaint();
				}
			}
		});
	}
	
	/**
	 * A focus listener for the textfield.
	 * When focus is lost, it does the exact same thing as when enter is hit.
	 * TODO: is this implementation a good design decision?
	 * @param component (this method should only be used with the textfield)
	 */
	private void focusListenerAddValueText(JComponent component){
		component.addFocusListener(new FocusListener(){
			public void focusLost(FocusEvent e) {
				//valueLabel.setText(addValue.getText());
				if(!valueLabel.getText().equals("")){
					valueLabel.setVisible(true);
					addValue.setVisible(false);
					revalidate();
					repaint();
				}
				else {}
			}

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	
	
	/**
	 * FIXME currently unused code
	 * @return
	 */
	private boolean validateAddValueField(){
		String stringText = addValue.getText();
		int parsedText = Integer.parseInt(stringText);
		if(parsedText <= 999 && parsedText >= 1){
			if(stringText.length() <=3 ){
				return true;
			}
			else {
				errorField.setText("Value of cards must be a 3-digit-or-less integer between 1 and 999");
				return false;
			}
		}
		else {
			return false;
		}
		
	}
	
	public void setValueLabel(String value) {
		valueLabel.setText(value);
		if (!value.isEmpty()) {
			valueLabel.setVisible(true);
		}
		addValue.setVisible(false);
	}
	
	/**
	 * Creates a new font for use later
	 */
	private Font makeFont(int size) {
		// create a dummy JTextArea
		JTextArea editingArea = new JTextArea();
		// get the current font
		Font f = editingArea.getFont();
		// create a new, larger font from the current font
		Font newFont = new Font(f.getFontName(), f.getStyle(), f.getSize() + size);
		// set the bigger font for userStoryDesc
		Font bigFont = newFont;
		return bigFont;
	}

	@Override
	public boolean validateField(IErrorView warningField, boolean showLabel,
			boolean showBox) {
		return addValue.validateField(warningField, showLabel, showBox);
	}

	@Override
	public boolean hasChanges() {
		return addValue.hasChanges();
	}
	
	
	
}