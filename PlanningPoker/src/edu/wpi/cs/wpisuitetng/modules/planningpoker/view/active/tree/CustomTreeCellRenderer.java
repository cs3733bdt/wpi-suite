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
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.tree;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.deck.models.Deck;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.decks.creation.ColorEnum;


/**
 * 
 * @author Bobby Drop Tables
 */
public class CustomTreeCellRenderer extends DefaultTreeCellRenderer {
	Icon newIcon;
	JPanel parent;
	
	/**
	 * 
	 * @param parent JPanel
	 */
	public CustomTreeCellRenderer(JPanel parent) {
		this.parent = parent;
	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {

		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
				row, hasFocus);
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
		
		if(parent instanceof GameTree){
			newIcon = new GameIcon();
		}
		if(parent instanceof DeckTree){
			Object valueHolder = ((DefaultMutableTreeNode)value).getUserObject();
			
			if(valueHolder instanceof Deck){
				if(((Deck)valueHolder).getColor() == ColorEnum.RED){
					newIcon = new DeckIcon(ColorEnum.RED);
				}
				if(((Deck)valueHolder).getColor() == ColorEnum.BLUE){
					newIcon = new DeckIcon(ColorEnum.BLUE);
				}
				if(((Deck)valueHolder).getColor() == ColorEnum.GREEN){
					newIcon = new DeckIcon(ColorEnum.GREEN);
				}
				if(((Deck)valueHolder).getColor() == ColorEnum.PURPLE){
					newIcon = new DeckIcon(ColorEnum.PURPLE);
				}
				if(((Deck)valueHolder).getColor() == ColorEnum.YELLOW){
					newIcon = new DeckIcon(ColorEnum.YELLOW);
				}
			}
			else{
				newIcon = new DeckIcon(null);
			}
		}
		
		setIcon(newIcon);
		return this; 
	}
}
