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
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JComponent;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.ActiveGamesPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.NewActiveGamePanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.tree.GameTree;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation.CreateGamePanel;
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

	private List<CreateGamePanel> listOfCreateGamePanels = new ArrayList<CreateGamePanel>();
	private List<NewActiveGamePanel> listOfActiveGamePanels = new ArrayList<NewActiveGamePanel>();
	private List<EndGamePanel> listOfEndGamePanels = new ArrayList<EndGamePanel>();
	
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
		toolbar.repaint(); //Unsure why this is necessary but this is what is done in Requirement Manager
	}
	
	/**
	 * Creates a CreateGamePanel instance, adds a tab representing 
	 * that panel, and switches to that new panel
	 */
	public void createGame() {
		CreateGamePanel newGame = new CreateGamePanel();
		/**
		 * REMOVE ABOVE LINE (CreateGamePanel newGame = new CreateGamePanel();)
		 * AND ADD FOLLOWING LINE (NewCreateGamePanel newGame = new NewCreateGamePanel();)
		 * WHEN READY TO SEE NEW CREATEGAMEPANEL IN JANEWAY. THEN DO FUN CONTROLLER CHANGES!
		 */
		//NewCreateGamePanel newGame = new NewCreateGamePanel();
		main.getTabbedView().addTab("New Game", null, newGame, "New Game");
		main.invalidate(); //force the tabbedpane to redraw.
		main.repaint();
		main.getTabbedView().setSelectedComponent(newGame);
	}
	
	/**
	 * Creates an EditGamePanel instance, adds a tab representing 
	 * that panel, and switches to that new panel 
	 */
	public void editGame(Game game) {
		CreateGamePanel newGame = new CreateGamePanel(game);
		newGame.setBoxName(game.getName());
		newGame.setBoxDescription(game.getDescription());
		for(int i = 0; i < game.getRequirements().size(); i++){
			newGame.getAddReqPan().addRequirement(game.getRequirements().get(i));
		}
		newGame.setUsesCards(game.doesUseCards());
		
		Calendar dateMaker = new GregorianCalendar();
		dateMaker.setTime(game.getEndDate());
		String hour = Integer.toString(dateMaker.get(Calendar.HOUR));
		String minute = Integer.toString(dateMaker.get(Calendar.MINUTE));		
		String AM_PM = "If this doesn't change, something is wrong";
		
		if(dateMaker.get(Calendar.AM_PM) == Calendar.AM){
			AM_PM = "AM";
		}
		if(dateMaker.get(Calendar.AM_PM) == Calendar.PM){
			AM_PM = "PM";
		}
		
		newGame.getEndDateField().setDateAndTime(dateMaker.getTime(), hour, minute, AM_PM);				
		
		for(CreateGamePanel gameSearch : listOfCreateGamePanels){
			if(game.equals(gameSearch.getGame())){
				main.getTabbedView().setSelectedComponent(gameSearch);
				main.invalidate();
				main.repaint();
				return;
			}
		}
		
		// Makes the game name not be longer than 6 characters
		StringBuilder tabName = new StringBuilder();
		int subStringLength = game.getName().length() > 6 ? 7 : game.getName().length();
		tabName.append(game.getName().subSequence(0, subStringLength));
		if (game.getName().length() > 6)
			tabName.append("...");
		main.getTabbedView().addTab(tabName.toString(), newGame);

		listOfCreateGamePanels.add(newGame);

		main.getTabbedView().setSelectedComponent(newGame);
		main.invalidate();
		main.repaint();
	}
	
	/**
	 * Creates a CreateGamePanel with an existing game.
	 * At the moment this is used only when the database fails to add the object.
	 * @param game
	 */
	public void updateGame(Game game, boolean serverError){
		CreateGamePanel aGame = new CreateGamePanel(game, serverError);
		main.getTabbedView().addTab("New Game", null, aGame, "New Game");
		main.invalidate();
		main.repaint();
		main.getTabbedView().setSelectedComponent(aGame);
	}
	
	/**
	 * After clicking a game in the games list, the active games view will be displayed.
	 * This also pulls an active game to focus on double click
	 */
	public void joinGame(Game game){
		//Attempt to find the game in the active panels list
		for(NewActiveGamePanel gameSearch : listOfActiveGamePanels){
			if(game.equals(gameSearch.getGame())){
				main.getTabbedView().setSelectedComponent(gameSearch);
				main.invalidate();
				main.repaint();
				return; //The game has been found and made active. Done!
			}
		}
		
		//Game not found in the active game list
		NewActiveGamePanel viewGame = new NewActiveGamePanel(game);
		//TODO: MAKE THIS NOT A TAB, MAKE IT OVERWRITE THE MAIN VIEW.
		
		
		//Makes the game name not be longer than 6 charaters
		StringBuilder tabName = new StringBuilder();
		int subStringLength = game.getName().length() > 6 ? 7 : game.getName().length();
		tabName.append(game.getName().subSequence(0,subStringLength));
		if(game.getName().length() > 6) tabName.append("...");
		main.getTabbedView().addTab(tabName.toString(),  viewGame);
		
		
		listOfActiveGamePanels.add(viewGame);
		
		main.getTabbedView().setSelectedComponent(viewGame);
		main.invalidate();
		main.repaint();
		
	}
	
	/**
	 * TODO: add documentation for this funciton
	 */
	public void viewEndGame(Game game){
		//Attempt to find the game in the active panels list
		for(EndGamePanel gameSearch : listOfEndGamePanels){
			if(game.equals(gameSearch.getGame())){
				main.getTabbedView().setSelectedComponent(gameSearch);
				main.invalidate();
				main.repaint();
				return; //The game has been found and made active. Done!
			}
		}
		
		//Game not found in the active game list
		EndGamePanel viewGame = new EndGamePanel(game);
		//TODO: MAKE THIS NOT A TAB, MAKE IT OVERWRITE THE MAIN VIEW.
		
		
		//Makes the game name not be longer than 6 charaters
		StringBuilder tabName = new StringBuilder();
		int subStringLength = game.getName().length() > 6 ? 7 : game.getName().length();
		tabName.append(game.getName().subSequence(0,subStringLength));
		if(game.getName().length() > 6) tabName.append("...");
		main.getTabbedView().addTab(tabName.toString(),  viewGame);
		
		
		listOfEndGamePanels.add(viewGame);
		
		main.getTabbedView().setSelectedComponent(viewGame);
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
		if (comp instanceof CreateGamePanel){
			if(!((CreateGamePanel) comp).readyToRemove()) return;
			listOfCreateGamePanels.remove(comp);
		}
		if (comp instanceof NewActiveGamePanel) {
			if(!((NewActiveGamePanel) comp).readyToRemove()) return;
			this.listOfActiveGamePanels.remove(comp);
		}
		if (comp instanceof ActiveGamesPanel) {
			if(!((ActiveGamesPanel) comp).readyToRemove()) return;
			listOfActiveGamePanels.remove(comp);
		}
		main.getTabbedView().remove(comp);
	}
	
	/**
	 * Closes all of the tabs besides the overview tab in the main view.
	 */
	public void closeAllTabs() {

		int tabCount = main.getTabbedView().getTabCount();
		
		System.out.println("THE TAB COUNT IS:" + tabCount);

		for(int i = tabCount - 1; i > 0; i--)
		{
			Component toBeRemoved = main.getTabbedView().getComponentAt(i);
			
			System.out.println(toBeRemoved.getClass().getName());

			if(toBeRemoved instanceof NewActiveGamePanel)
			{
				if(!((NewActiveGamePanel)toBeRemoved).readyToRemove()) continue;
				this.listOfActiveGamePanels.remove(toBeRemoved);
			}
			

			if(toBeRemoved instanceof CreateGamePanel)
			{
				if(!((CreateGamePanel)toBeRemoved).readyToRemove()) continue;
				listOfCreateGamePanels.remove(toBeRemoved);
			}

			main.getTabbedView().removeTabAt(i);
		}

		main.repaint();
	}
	
	/**
	 * Closes all the tabs except for the one that was clicked.
	 * Used in conjunction with the tab right click drop-down that allows users to close multiple tabs at once.
	 */
	public void closeOthers() {
		int tabCount = main.getTabbedView().getTabCount();
		Component selected = main.getTabbedView().getSelectedComponent();

		for(int i = tabCount - 1; i >= 0; i--)
		{
			Component toBeRemoved = main.getTabbedView().getComponentAt(i);

			if(toBeRemoved instanceof NewActiveGamePanel){continue;}
			if(toBeRemoved == selected){
				continue;}

			if(toBeRemoved instanceof CreateGamePanel)
			{
				if(!((CreateGamePanel)toBeRemoved).readyToRemove()){
					break;}
				listOfCreateGamePanels.remove(toBeRemoved);
			}

			main.getTabbedView().removeTabAt(i);
		}
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
