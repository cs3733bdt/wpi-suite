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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirement.models.Requirement;

/**
 * TODO: add documentation
 * @author Bobby Drop Tables
 *
 */
public class CreateGamePanelTest {
	Game gameView;
	CreateGamePanel panel;
	
	@Before
	public void setUp() throws Exception {
		gameView = new Game("A Game", "A Description", new ArrayList<Requirement>(), true, false);
		panel = new CreateGamePanel(gameView, false);
	}
	
	@Test
	public void test() {
		assertEquals(gameView.getName(), panel.getBoxName().getText());
		assertEquals(gameView.getDescription(), panel.getDescText());
	}

}
