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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.IActiveGamePanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.NewActiveGamePanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.tree.GameTree;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.decks.creation.CreateDeckPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation.CreateGamePanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation.ICreateGamePanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation.NewCreateGamePanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.end.EndGamePanel;

/**
 * @author jonathanleitschuh
 * 
 */
public class ViewEventController {
	private static ViewEventController instance = null;
	private MainView main = null;
	private ToolbarView toolbar = null;
	private GameTree gameTree = null;
	
	
	/**
	 * Default constructor for the ViewEventController. Is protected to prevent instantiation.
	 */
	private ViewEventController(){}
	
	/**
	 * Returns the single instance of the ViewEcentController.

	 * @return the instance of this controller.
	 */
	public static ViewEventController getInstance(){
		if(instance == null){
			instance = new ViewEventController();
		}
		return instance;
	}
	
	/**
	 * Sets the main view to the given view.
	
	 * @param mainview The TabbedView
	 */
	public void setMainView(MainView mainview){
		main = mainview;
	}
	
	/**
	 * Sets the ToolBar view to the given toolbar
	 * @param tb The toolbar to be set as active.
	 */
	public void setToolBar(ToolbarView tb){
		toolbar = tb;
		toolbar.repaint(); 
	}
	
	/**
	 * Creates a CreateGamePanel instance, adds a tab representing 
	 * that panel, and switches to that new panel
	 */
	public void createGame() {
		/**
		 * REMOVE ABOVE LINE (CreateGamePanel newGame = new CreateGamePanel();)
		 * AND ADD FOLLOWING LINE (NewCreateGamePanel newGame = new NewCreateGamePanel();)
		 * WHEN READY TO SEE NEW CREATEGAMEPANEL IN JANEWAY. THEN DO FUN CONTROLLER CHANGES!
		 */
		NewCreateGamePanel newGame = new NewCreateGamePanel();
		main.getTabbedView().addTab("New Game", null, newGame, "New Game");
		main.invalidate(); //force the tabbed pane to redraw.
		main.repaint();
		main.getTabbedView().setSelectedComponent(newGame);
	}
	
	/**
	 * Creates a CreateDeckPanel instance, adds a tab representing 
	 * that panel, and switches to that new panel
	 */
	public void createDeck() {
		CreateDeckPanel newDeck = new CreateDeckPanel();
		main.getTabbedView().addTab("New Deck", null, newDeck, "New Deck");
		main.invalidate(); //force the tabbed pane to redraw.
		main.repaint();
		main.getTabbedView().setSelectedComponent(newDeck);
	}
	
	/**
	 * Creates an EditGamePanel instance, adds a tab representing 
	 * that panel, and switches to that new panel
	 * @param game The game to be added
	 */
	public void editGame(Game game) {
		main.getTabbedView().editGame(game);
		main.invalidate();
		main.repaint();
	}
	
	/**
	 * Creates a CreateGamePanel with an existing game.
	 * At the moment this is used only when the database fails to add the object.
	 * @param game
	 */
	public void updateGame(Game game, boolean serverError){
		NewCreateGamePanel aGame = new NewCreateGamePanel(game, serverError);
		main.getTabbedView().addTab("New Game", null, aGame, "New Game");
		main.invalidate();
		main.repaint();
		main.getTabbedView().setSelectedComponent(aGame);
	}
	
	/**
	 * After clicking a game in the games list, the active games view will be displayed.
	 * This also pulls an active game to focus on double click
	 * @param game Game to be joined
	 */
	public void joinGame(Game game){
		main.getTabbedView().joinGame(game);
		main.invalidate();
		main.repaint();
	}
	
	/**
	 * Shows the ended game.
	 * Displays the End Game Panel
	 * @param game Game to be searched for
	 */
	public void viewEndGame(Game game){
		main.getTabbedView().viewEndGame(game);
		main.invalidate();
		main.repaint();
		
	}
	
	/**
	 * Removes the tab for the given JComponent
	 * CreateGamePanel is only removed when it returns that it is ready to be removed
	 * 
	 * @param comp the component to remove
	 */
	public void removeTab(JComponent comp){
		main.getTabbedView().removeTab(comp);
	}
	
	/**
	 * Closes all of the tabs besides the overview tab in the main view.
	 */
	public void closeAllTabs() {
		main.getTabbedView().closeAllTabs();
	}
	
	/**
	 * Closes all the tabs except for the one that was clicked.
	 * Used in conjunction with the tab right click drop-down 
	 * that allows users to close multiple tabs at once.
	 */
	public void closeOthers() {
		main.getTabbedView().closeOthers();
		main.repaint();

	}

	public void refreshGameTable() {
		// TODO Auto-generated method stub
		//This method will call a method that refreshes the data in the requirement model
		// something like: overviewTable.refresh();
		
	}

	/**
	 * Refreshes the tree model using data from the Game Model
	 */
	public void refreshGameTree() {
		//This method will call a method that refreshes the the tree
		gameTree.refresh();
		
	}

	/**
	 * Sets the game overview tree model
	 * @param gameTree the game tree model
	 */
	public void setGameOverviewTree(GameTree gameTree) {
		this.gameTree = gameTree;
		
	}


}
