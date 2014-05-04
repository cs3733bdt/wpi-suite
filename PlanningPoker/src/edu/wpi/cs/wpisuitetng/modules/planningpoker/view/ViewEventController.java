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

import javax.swing.JComponent;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.deck.models.Deck;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.tree.DeckTree;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.tree.GameTree;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.decks.creation.CreateDeckPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation.CreateGamePanel;

/**
 * @author jonathanleitschuh
 * 
 */
public class ViewEventController {
	private static ViewEventController instance = null;
	private ToolbarView toolbar = null;
	private GameTree gameTree = null;
	private DeckTree deckTree = null;
	private TabbedView tabs = null;
	
	
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
//	public void setMainView(MainView mainview){
//		main = mainview;
//	}
	
	/**
	 * Sets the ToolBar view to the given toolbar
	 * @param tb The toolbar to be set as active.
	 */
	public void setToolBar(ToolbarView tb){
		toolbar = tb;
		toolbar.repaint(); 
	}
	
	public void disableHelpButton(String message) {
		toolbar.disableHelpButton(message);
	}
	
	public void enableHelpButton() {
		toolbar.enableHelpButton();
	}
	
	/**
	 * Sets the modules tabbed view
	 * @param tabs
	 */
	public void setTabbedView(TabbedView tabs){
		this.tabs = tabs;
		tabs.repaint();
	}
	
	public TabbedView getTabbedView(){
		return tabs;
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
		CreateGamePanel newGame = new CreateGamePanel();
		getTabbedView().addTab("New Game", null, newGame, "New Game");
		getTabbedView().setSelectedComponent(newGame);
		newGame.getLeftHalf().getBoxName().requestFocus();
		newGame.getLeftHalf().getBoxName().select(0,0);
		getTabbedView().invalidate(); //force the tabbed pane to redraw.
		getTabbedView().repaint();
	}
	
	/**
	 * Creates a CreateDeckPanel instance, adds a tab representing 
	 * that panel, and switches to that new panel
	 */
	public void createDeck() {
		CreateDeckPanel newDeck = new CreateDeckPanel();
		getTabbedView().addTab("New Deck", null, newDeck, "New Deck");
		getTabbedView().setSelectedComponent(newDeck);
		newDeck.getBoxName().requestFocus();
		newDeck.getBoxName().select(0,0);
		getTabbedView().invalidate(); //force the tabbed pane to redraw.
		getTabbedView().repaint();
	}
	
	public void editPreferences() {
		getTabbedView().openPreferencesTab();
		getTabbedView().invalidate();
		getTabbedView().repaint();
		
	}
	
	public void openHelp() {
		getTabbedView().openHelpTab();
		getTabbedView().invalidate();
		getTabbedView().repaint();
	}
	
	/**
	 * Creates an EditGamePanel instance, adds a tab representing 
	 * that panel, and switches to that new panel
	 * @param game The game to be added
	 */
	public void editGame(Game game) {
		getTabbedView().editGame(game);
		getTabbedView().invalidate();
		getTabbedView().repaint();
	}
	
	/**
	 * Creates a CreateGamePanel with an existing game.
	 * At the moment this is used only when the database fails to add the object.
	 * @param game the game to be updated
	 * @param serverError
	 */
	public void updateGame(Game game, boolean serverError){
		CreateGamePanel aGame = new CreateGamePanel(game, serverError);
		getTabbedView().addTab("New Game", null, aGame, "New Game");
		getTabbedView().invalidate();
		getTabbedView().repaint();
		getTabbedView().setSelectedComponent(aGame);
	}
	
	/**
	 * After clicking a game in the games list, the active games view will be displayed.
	 * This also pulls an active game to focus on double click
	 * @param game Game to be joined
	 */
	public void joinGame(Game game){
		getTabbedView().joinGame(game);
		getTabbedView().invalidate();
		getTabbedView().repaint();
	}
	
	/**
	 * Shows the ended game.
	 * Displays the End Game Panel
	 * @param game Game to be searched for
	 */
	public void viewEndGame(Game game){
		getTabbedView().viewEndGame(game);
		getTabbedView().invalidate();
		getTabbedView().repaint();
		
	}
	
	/**
	 * Shows the deck
	 * Displays the deck creation panel
	 * @deck the deck to be viewed
	 */
	public void viewDeck(Deck deck) {
		getTabbedView().viewDeck(deck);
		getTabbedView().invalidate();
		getTabbedView().repaint();
	}
	
	/**
	 * Removes the tab for the given JComponent
	 * CreateGamePanel is only removed when it returns that it is ready to be removed
	 * 
	 * @param comp the component to remove
	 */
	public void removeTab(JComponent comp){
		getTabbedView().removeTab(comp);
	}
	
	/**
	 * Closes all of the tabs besides the overview tab in the main view.
	 */
	public void closeAllTabs() {
		getTabbedView().closeAllTabs();
	}
	
	/**
	 * Closes all the tabs except for the one that was clicked.
	 * Used in conjunction with the tab right click drop-down 
	 * that allows users to close multiple tabs at once.
	 */
	public void closeOthers() {
		getTabbedView().closeOthers();
		getTabbedView().repaint();

	}
	
	/**
	 * Refreshes the tree model using data from the Game Model
	 */
	public void refreshGameTree() {
		//This method will call a method that refreshes the the tree
		gameTree.refresh();
		
	}

	/**
	 * Refreshes the tree model using data from the Deck Model
	 */
	public void refreshDeckTree() {
		//This method will call a method that refreshes the the tree
		deckTree.refresh();
		
	}
	
	/**
	 * Sets the game overview tree model
	 * @param gameTree the game tree model
	 */
	public void setGameOverviewTree(GameTree gameTree) {
		this.gameTree = gameTree;
		
	}

	/**
	 * Sets the deck overview tree model
	 * @param deckTree the game tree model
	 */
	public void setDeckOverviewTree(DeckTree deckTree) {
		this.deckTree = deckTree;
		
	}

	public void updateActiveGame(Game game) {
		getTabbedView().updateActiveGame(game);
		getTabbedView().invalidate();
		getTabbedView().repaint();
	}
}
