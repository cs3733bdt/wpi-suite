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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.decks.creation;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ColorCardImage {
	private static ColorCardImage instance = null;
	
	private static ColorCardImage getInstance(){
		if(instance == null){
			instance = new ColorCardImage();
		}
		return instance;
	}
	
	/**
	 * 
	 * @param color
	 * @return color card image
	 * @throws IOException
	 */
	public static BufferedImage getColorCardImage(ColorEnum color) throws IOException {
		getInstance();
		try {
			switch (color) {
			case RED:
				return ImageIO.read(instance.getClass().getResource("card_back.png"));
			case BLUE:
				return ImageIO.read(instance.getClass().getResource("card_back_blue.png"));
			case GREEN:
				return ImageIO.read(instance.getClass().getResource("card_back_green.png"));
			case PURPLE:
				return ImageIO.read(instance.getClass().getResource("card_back_purple.png"));
			case YELLOW:
				return ImageIO.read(instance.getClass().getResource("card_back_yellow.png"));
			case FRONT:
				return ImageIO.read(instance.getClass().getResource("card_front.png"));
			}
		} catch (IOException e1) {
			throw e1;
		} 
		throw new IOException();
	}

}
