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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.game.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.requirement.Requirement;

/**
 * TODO: add documentation
 * @author Bobby Drop Tables
 *
 */
public class ActiveGamesPanelTest {
	Game gameView;
	ActiveGamesPanel panel;

	@Before
	public void setUp() throws Exception {
		gameView = new Game("A Game", "A Description", new ArrayList<Requirement>(), true, false);
		panel = new ActiveGamesPanel(gameView);
	}

	@Test
	public void test() {
		
	}

}
