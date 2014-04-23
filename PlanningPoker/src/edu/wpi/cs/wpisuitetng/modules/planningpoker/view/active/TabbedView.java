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

import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ClosableTabComponent;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation.ICreateGamePanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation.NewCreateGamePanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.end.EndGamePanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.end.IEndedGamePanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.preferences.creation.PreferencesPanel;

/**
 * This class sets the main view when the user goes to the PlanningPoker tab 
 * @author jonathanleitschuh
 *
 */
public class TabbedView extends JTabbedPane {
	private boolean dragging = false;
	private Image tabImage = null;
	private int draggedTabIndex = 0;
	private GameOverview gameOverview = new GameOverview();
	private DeckOverview deckOverview = new DeckOverview();
	
	private final JPopupMenu popup = new JPopupMenu();
	private JMenuItem closeAll = new JMenuItem("Close All Tabs");
	private JMenuItem closeOthers = new JMenuItem("Close Others");
	
	private List<ICreateGamePanel> listOfCreateGamePanels = new ArrayList<ICreateGamePanel>();
	private List<IActiveGamePanel> listOfActiveGamePanels = new ArrayList<IActiveGamePanel>();
	private List<IEndedGamePanel>  listOfEndedGamePanels = new ArrayList<IEndedGamePanel>();
	
	private PreferencesPanel preferencesPanel;
	private boolean hasPreferencesTab=false;
	
	/**
	 * Adds Main View of the planning poker panel when the user goes to the planning poker tab
	 */
	public TabbedView(){
		
		addTab("Game Overview", gameOverview);
		addTab("Deck Overview", deckOverview);
	    //this.setBorder(BorderFactory.createLineBorder(Color.green, 2));
		setTabLayoutPolicy(SCROLL_TAB_LAYOUT);
	    
	    closeAll.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				ViewEventController.getInstance().closeAllTabs();

			}	
		});
	    
	    closeOthers.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				ViewEventController.getInstance().closeOthers();

			}	
		});
	    
	    popup.add(closeAll);
	    popup.add(closeOthers);
	    
	    addMouseListener(new MouseAdapter()
	    {
	    	@Override
	    	public void mousePressed(MouseEvent e)
	    	{
	    		if(e.isPopupTrigger()) popup.show(e.getComponent(), e.getX(), e.getY());
	    	}

	    	@Override
	    	public void mouseReleased(MouseEvent e) {
	    		if(dragging) {
	    			int tabNumber = getUI().tabForCoordinate(TabbedView.this, e.getX(), e.getY());
	    			if(tabNumber >= 0) {
	    				Component comp = getComponentAt(draggedTabIndex);
	    				String title = getTitleAt(draggedTabIndex);
	    				if (!title.equals("Game Overview") && !title.equals("Deck Overview")) {
	    					removeTabAt(draggedTabIndex);
	    					insertTab(title, null, comp, null, tabNumber);
	    					setSelectedIndex(tabNumber);
	    				}
	    			}
	    		}

	    		dragging = false;
	    		tabImage = null;
	    	}
	    });
	}

	/**
	 * needed to get controller functioning
	 * TODO add purpose for this function
	 * @return boolean true always
	 */
	public boolean getNewGame(){
		return true;
	}
	
	/**
	 * Overridden insertTab function to add the closable tab element.
	 * 
	 * @param title	Title of the tab
	 * @param icon	Icon for the tab
	 * @param component	The tab
	 * @param tip	Showing mouse tip when hovering over tab
	 * @param index	Location of the tab
	 */
	@Override
	public void insertTab(String title, Icon icon, Component component,
			String tip, int index) {
		super.insertTab(title, icon, component, tip, index);
		if (!(component instanceof GameOverview) && !(component instanceof DeckOverview)) {
			setTabComponentAt(index, new ClosableTabComponent(this));
		}
	}
	
	
	/**
	 * After clicking a game in the games list, the active games view will be displayed.
	 * This also pulls an active game to focus on double click
	 * @param game Game to be joined
	 */
	public void joinGame(Game game){
		//Attempt to find the game in the active panels list
		for(IActiveGamePanel gameSearch : listOfActiveGamePanels){
			if(game.equals(gameSearch.getGame())){
				setSelectedComponent((Component)gameSearch);
				invalidate();
				repaint();
				return; //The game has been found and made active. Done!
			}
		}
		
		//Game not found in the active game list
		NewActiveGamePanel viewGame = new NewActiveGamePanel(game);
		//TODO: MAKE THIS NOT A TAB, MAKE IT OVERWRITE THE MAIN VIEW.
		
		addTab(getTabName(game), viewGame);
		
		listOfActiveGamePanels.add(viewGame);
		
		setSelectedComponent(viewGame);
		invalidate();
		repaint();
	}
	
	/**
	 * Creates an EditGamePanel instance, adds a tab representing 
	 * that panel, and switches to that new panel
	 * @param game The game to be added
	 */
	public void editGame(Game game) {
		NewCreateGamePanel newGame = new NewCreateGamePanel(game);
		
		//FIND THE CURRENT EDIT GAME PANEL
		for(ICreateGamePanel gameSearch : listOfCreateGamePanels){
			if(game.equals(gameSearch.getGame())){			//If found then make it the active
				setSelectedComponent((Component)gameSearch);
				invalidate();
				repaint();
				return;
			}
		}
		
		addTab(getTabName(game), newGame);

		listOfCreateGamePanels.add((ICreateGamePanel) newGame);

		setSelectedComponent(newGame);
		invalidate();
		repaint();
	}
	
	public void openPreferencesTab(){
		if(!hasPreferencesTab){
			preferencesPanel = new PreferencesPanel();
			addTab("Preferences", null, preferencesPanel, "Preferences");
			setSelectedComponent(preferencesPanel);
			hasPreferencesTab = true;
			invalidate();
			repaint();
			return;
		}
		
		setSelectedComponent(preferencesPanel);
		invalidate();
		repaint();
		
	}
	
	/**
	 * Shows the ended game.
	 * Displays the End Game Panel
	 * @param game Game to be searched for
	 */
	public void viewEndGame(Game game){
		//Attempt to find the game in the active panels list
		for(IEndedGamePanel gameSearch : listOfEndedGamePanels){
			if(game.equals(gameSearch.getGame())){
				setSelectedComponent((Component) gameSearch);
				invalidate();
				repaint();
				return; //The game has been found and made active. Done!
			}
		}
		
		//Game not found in the active game list
		EndGamePanel viewGame = new EndGamePanel(game);
		//TODO: MAKE THIS NOT A TAB, MAKE IT OVERWRITE THE MAIN VIEW.
		
		addTab(getTabName(game),  viewGame);
		
		
		listOfEndedGamePanels.add(viewGame);
		
		setSelectedComponent(viewGame);
		invalidate();
		repaint();
		
	}
	
	
	/**
	 * Creates a tab name that is shortened if the name of the game is longer than 6 characters
	 * @param game the game to get the tab text from
	 * @return the tabs text
	 */
	private String getTabName(Game game){
		// Makes the game name not be longer than 6 characters
		StringBuilder tabName = new StringBuilder();
		int subStringLength = game.getName().length() > 6 ? 7 : game.getName().length();
		tabName.append(game.getName().subSequence(0, subStringLength));
		if (game.getName().length() > 6)
			tabName.append("...");
		return tabName.toString();
	}
	
	/**
	 * Removes the tab for the given JComponent
	 * The panel is only removed when it returns that it is ready to be removed
	 * 
	 * @param comp the component to remove
	 */
	public void removeTab(JComponent comp){
		if (comp instanceof ICreateGamePanel){
			if(!((ICreateGamePanel) comp).readyToRemove()) return;
			listOfCreateGamePanels.remove(comp);
		}
		if (comp instanceof IActiveGamePanel) {
			if(!((IActiveGamePanel) comp).readyToRemove()) return;
			listOfActiveGamePanels.remove(comp);
		}
		if (comp instanceof IEndedGamePanel){
			if(!((IEndedGamePanel)comp).readyToRemove()) return;
			listOfEndedGamePanels.remove(comp);
		}
		if (comp instanceof PreferencesPanel){
			//TODO Implement preferences like other panels to use readyToRemove
			//if(!((IEndedGamePanel)comp).readyToRemove()) return;
			hasPreferencesTab = false;	
		}
		remove(comp);
		setSelectedComponent(gameOverview);
	}
	
	/**
	 * Closes all of the tabs besides the overview tab in the main view.
	 * Used in conjunction with the tab right click drop-down 
	 * that allows users to close multiple tabs at once.
	 */
	public void closeAllTabs() {

		int tabCount = getTabCount();
		
		System.out.println("THE TAB COUNT IS:" + tabCount);

		for(int i = tabCount - 1; i > 0; i--)
		{
			Component toBeRemoved = getComponentAt(i);
			
			System.out.println(toBeRemoved.getClass().getName());
			
			if((toBeRemoved instanceof DeckOverview) || toBeRemoved instanceof GameOverview)
			{
				continue;
			}

			if(toBeRemoved instanceof IActiveGamePanel)
			{
				if(!((IActiveGamePanel)toBeRemoved).readyToRemove()) continue;
				listOfActiveGamePanels.remove(toBeRemoved);
			}
			
			if(toBeRemoved instanceof ICreateGamePanel)
			{
				if(!((ICreateGamePanel)toBeRemoved).readyToRemove()) continue;
				listOfCreateGamePanels.remove(toBeRemoved);
			}
			
			if (toBeRemoved instanceof IEndedGamePanel){
				if(!((IEndedGamePanel)toBeRemoved).readyToRemove()) continue;
				listOfEndedGamePanels.remove(toBeRemoved);
			}

			removeTabAt(i);
		}
		repaint();
	}
	
	/**
	 * Closes all the tabs except for the one that was clicked.
	 * Used in conjunction with the tab right click drop-down 
	 * that allows users to close multiple tabs at once.
	 */
	public void closeOthers() {
		int tabCount = getTabCount();
		Component selected = getSelectedComponent();

		for(int i = tabCount - 1; i >= 0; i--)
		{
			Component toBeRemoved = getComponentAt(i);
			if(toBeRemoved == selected){
				continue;}
			
			if((toBeRemoved instanceof DeckOverview) || toBeRemoved instanceof GameOverview)
			{
				continue;
			}

			if(toBeRemoved instanceof IActiveGamePanel){
				if(!((IActiveGamePanel)toBeRemoved).readyToRemove()){continue;}
				listOfActiveGamePanels.remove(toBeRemoved);
			}

			if(toBeRemoved instanceof ICreateGamePanel)
			{
				if(!((ICreateGamePanel)toBeRemoved).readyToRemove()){continue;}
				listOfCreateGamePanels.remove(toBeRemoved);
			}
			
			if (toBeRemoved instanceof IEndedGamePanel){
				if(!((EndGamePanel)toBeRemoved).readyToRemove()) {continue;}
				listOfEndedGamePanels.remove(toBeRemoved);
			}

			removeTabAt(i);
		}
		repaint();

	}
}
