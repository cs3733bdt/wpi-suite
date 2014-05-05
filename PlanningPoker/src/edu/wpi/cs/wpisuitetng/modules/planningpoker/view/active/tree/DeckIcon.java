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

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.decks.creation.ColorEnum;

public class DeckIcon implements Icon {
	
	private int width;
	private int height;
	ColorEnum color;
	
	/**
	 * 
	 * @param newColor
	 */
	public DeckIcon(ColorEnum newColor){
		width = 5;
		height = 5;
		color = newColor;
	}
	
	/**
	 * Method getIconHeight.
	
	
	 * @return int * @see javax.swing.Icon#getIconHeight() */
	@Override
	public int getIconHeight() {
		return height;
	}

	/**
	 * Method getIconWidth.
	
	
	 * @return int * @see javax.swing.Icon#getIconWidth() */
	@Override
	public int getIconWidth() {
		return width;
	}

	/**
	 * Method paintIcon.
	 * @param c Component
	 * @param g Graphics
	 * @param x int
	 * @param y int
	
	 * @see javax.swing.Icon#paintIcon(Component, Graphics, int, int) */
	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		g.setColor(new Color(175, 0, 255));
		
		if(color == ColorEnum.RED){
			g.setColor(Color.RED);
		}
		if(color == ColorEnum.BLUE){
			g.setColor(Color.BLUE);
		}
		if(color == ColorEnum.GREEN){
			g.setColor(Color.GREEN);
		}
		if(color == ColorEnum.PURPLE){
			g.setColor(new Color(153, 0, 153));
		}
		if(color == ColorEnum.YELLOW){
			g.setColor(Color.YELLOW);
		}
		g.fillOval(x, y, width, height);
	}

}
