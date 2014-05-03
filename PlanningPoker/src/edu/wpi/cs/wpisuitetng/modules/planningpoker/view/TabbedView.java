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

import edu.wpi.cs.wpisuitetng.modules.planningpoker.deck.models.Deck;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.ActiveGamePanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.DeckOverview;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.GameOverview;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.IActiveGamePanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.decks.creation.CreateDeckPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.decks.creation.ICreateDeckPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation.CreateGamePanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation.ICreateGamePanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.end.EndGamePanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.end.IEndedGamePanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.help.ActiveGameHelp;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.help.CreateDeckHelp;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.help.CreateGameHelp;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.help.EndGameHelp;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.help.IHelpPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.help.PreferencesHelp;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.preferences.creation.IPreferencesPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.preferences.creation.PreferencesPanel;

/**
 * This class sets the main view when the user goes to the PlanningPoker tab
 * 
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

	private List<IEndedGamePanel> listOfEndedGamePanels = new ArrayList<IEndedGamePanel>();
	private ArrayList<IHelpPanel> listOfHelpPanels = new ArrayList<IHelpPanel>();
	private List<ICreateDeckPanel> listOfCreateDeckPanels = new ArrayList<ICreateDeckPanel>();

	private PreferencesPanel preferencesPanel;
	private boolean hasPreferencesTab = false;

	private int preferenceIndex = 0;
	private int endGameIndex = 1;
	private int activeGameIndex = 2;
	private int createGameIndex = 3;
	private int createDeckIndex = 4;

	private boolean hasPreferencesHelp = false;
	private boolean hasActiveGameHelp = false;
	private boolean hasEndGameHelp = false;
	private boolean hasCreateGameHelp = false;
	private boolean hasCreateDeckHelp = false;

	/**
	 * Adds Main View of the planning poker panel when the user goes to the
	 * planning poker tab
	 */
	public TabbedView() {

		addTab("Game Overview", gameOverview);

		addTab("Deck Overview", deckOverview);

		// this.setBorder(BorderFactory.createLineBorder(Color.green, 2));
		setTabLayoutPolicy(SCROLL_TAB_LAYOUT);

		closeAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ViewEventController.getInstance().closeAllTabs();

			}
		});

		closeOthers.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ViewEventController.getInstance().closeOthers();

			}
		});

		popup.add(closeAll);
		popup.add(closeOthers);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger())
					popup.show(e.getComponent(), e.getX(), e.getY());
				// Get the selected component, call setSelectedComponent so help
				// is enabled correctly
				JComponent selected = (JComponent) TabbedView.this
						.getSelectedComponent();
				setSelectedComponent(selected);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (dragging) {
					int tabNumber = getUI().tabForCoordinate(TabbedView.this,
							e.getX(), e.getY());
					if (tabNumber >= 0) {
						Component comp = getComponentAt(draggedTabIndex);
						String title = getTitleAt(draggedTabIndex);
						if (!title.equals("Game Overview")
								&& !title.equals("Deck Overview")) {
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
	 * needed to get controller functioning TODO add purpose for this function
	 * 
	 * @return boolean true always
	 */
	public boolean getNewGame() {
		return true;
	}

	/**
	 * Overridden insertTab function to add the closable tab element.
	 * 
	 * @param title
	 *            Title of the tab
	 * @param icon
	 *            Icon for the tab
	 * @param component
	 *            The tab
	 * @param tip
	 *            Showing mouse tip when hovering over tab
	 * @param index
	 *            Location of the tab
	 */
	@Override
	public void insertTab(String title, Icon icon, Component component,
			String tip, int index) {
		super.insertTab(title, icon, component, tip, index);
		if (!(component instanceof GameOverview)
				&& !(component instanceof DeckOverview)) {
			setTabComponentAt(index, new ClosableTabComponent(this));
		}
	}

	/**
	 * After clicking a game in the games list, the active games view will be
	 * displayed. This also pulls an active game to focus on double click
	 * 
	 * @param game
	 *            Game to be joined
	 */
	public void joinGame(Game game) {
		// Attempt to find the game in the active panels list
		for (IActiveGamePanel gameSearch : listOfActiveGamePanels) {
			if (game.equals(gameSearch.getGame())) {
				setSelectedComponent((Component) gameSearch);
				ViewEventController.getInstance().enableHelpButton();
				invalidate();
				repaint();
				return; // The game has been found and made active. Done!
			}
		}

		// Game not found in the active game list
		ActiveGamePanel viewGame = new ActiveGamePanel(game);
		// TODO: MAKE THIS NOT A TAB, MAKE IT OVERWRITE THE MAIN VIEW.

		addTab(getTabName(game), viewGame);
		setToolTipTextAt(getTabCount() - 1, game.getName());

		listOfActiveGamePanels.add(viewGame);

		setSelectedComponent(viewGame);
		ViewEventController.getInstance().enableHelpButton();

		invalidate();
		repaint();
	}

	/**
	 * Iterates through the list of active game panels currently open and
	 * returns true if the given game is currently open in a tab and is active
	 * 
	 * @param game
	 *            Game to check for in open tabs
	 * @return returns true if the given game is open in a tab and active,
	 *         returns false otherwise
	 */
	public boolean hasActiveGameOpen(Game game) {
		for (IActiveGamePanel gameSearch : listOfActiveGamePanels) {
			if (game.equals(gameSearch.getGame())) {
				setSelectedComponent((Component) gameSearch);
				invalidate();
				repaint();
				return true;
			}
		}
		return false;
	}

	/**
	 * Iterates through the list of active game panels currently open and
	 * returns the panel for the game given if it is found
	 * 
	 * @param game
	 *            game to search for in the list of active game panels
	 * @return returns the active game panel corresponding to the given game
	 * @throws NullPointerException
	 *             if the list of active panels is empty, throws an exception
	 */
	public Component getActiveGamePanel(Game game) throws NullPointerException {
		if (listOfActiveGamePanels.isEmpty()) {
			throw new NullPointerException("The list of game panels was null");
		} else {
			for (IActiveGamePanel gameSearch : listOfActiveGamePanels) {
				if (game.equals(gameSearch.getGame())) {
					return (Component) gameSearch;
				}
			}
		}
		return null;
	}

	/**
	 * Creates an EditGamePanel instance, adds a tab representing that panel,
	 * and switches to that new panel
	 * 
	 * @param game
	 *            The game to be added
	 */
	public void editGame(Game game) {
		CreateGamePanel newGame = new CreateGamePanel(game);

		// FIND THE CURRENT EDIT GAME PANEL
		for (ICreateGamePanel gameSearch : listOfCreateGamePanels) {
			if (game.equals(gameSearch.getGame())) { // If found then make it
														// the active
				setSelectedComponent((Component) gameSearch);
				ViewEventController.getInstance().enableHelpButton();
				invalidate();
				repaint();
				return;
			}
		}

		addTab(getTabName(game), newGame);
		setToolTipTextAt(getTabCount() - 1, game.getName());

		listOfCreateGamePanels.add((ICreateGamePanel) newGame);

		setSelectedComponent(newGame);
		ViewEventController.getInstance().enableHelpButton();
		invalidate();
		repaint();
	}

	public void openPreferencesTab() {
		if (!hasPreferencesTab) {
			preferencesPanel = new PreferencesPanel();
			addTab("Preferences", null, preferencesPanel, "Preferences");
			setSelectedComponent(preferencesPanel);
			ViewEventController.getInstance().enableHelpButton();
			hasPreferencesTab = true;
			invalidate();
			repaint();
			return;
		}

		setSelectedComponent(preferencesPanel);
		ViewEventController.getInstance().enableHelpButton();
		invalidate();
		repaint();

	}

	public void openHelpTab() {
		Component currentComp = getSelectedComponent();
		IHelpPanel helpPanel;
		if (isRepeatedPanel(currentComp)) {
			return;
		} else if (currentComp instanceof PreferencesPanel) {
			helpPanel = new PreferencesHelp();
			addTab("Preferences Help", (Component) helpPanel);
			hasPreferencesHelp = true;
		} else if (currentComp instanceof IActiveGamePanel) {
			helpPanel = new ActiveGameHelp();
			addTab("Active Game Help", (Component) helpPanel);
			hasActiveGameHelp = true;
		} else if (currentComp instanceof IEndedGamePanel) {
			helpPanel = new EndGameHelp();
			addTab("End Game Help", (Component) helpPanel);
			hasEndGameHelp = true;
		} else if (currentComp instanceof ICreateGamePanel) {
			helpPanel = new CreateGameHelp();
			addTab("Create Game Help", (Component) helpPanel);
			hasCreateGameHelp = true;
		} else if (currentComp instanceof CreateDeckPanel) {
			helpPanel = new CreateDeckHelp();
			addTab("Create Deck Help", (Component) helpPanel);
			hasCreateDeckHelp = true;
		} else {
			return;
		}
		listOfHelpPanels.add(helpPanel);
		setSelectedComponent((Component) helpPanel);
		invalidate();
		repaint();
		return;
	}

	/**
	 * @param currentComp
	 *            help panel about to be added
	 * @return true if the help panel about to be added is already present
	 */
	private boolean isRepeatedPanel(Component currentComp) {
		if (currentComp instanceof PreferencesPanel && hasPreferencesHelp) {
			selectOpenHelp(currentComp, preferenceIndex);
			return true;
		}
		if (currentComp instanceof IActiveGamePanel && hasActiveGameHelp) {
			selectOpenHelp(currentComp, activeGameIndex);
			return true;
		}
		if (currentComp instanceof IEndedGamePanel && hasEndGameHelp) {
			selectOpenHelp(currentComp, endGameIndex);
			return true;
		}
		if (currentComp instanceof ICreateGamePanel && hasCreateGameHelp) {
			selectOpenHelp(currentComp, createGameIndex);
			return true;
		}
		if (currentComp instanceof CreateDeckPanel && hasCreateDeckHelp) {
			selectOpenHelp(currentComp, createDeckIndex);
			return true;
		}
		return false;
	}

	private void selectOpenHelp(Component currentComp, int identifierIndex) {
		for (IHelpPanel p : listOfHelpPanels) {
			if (p.getIdentifierIndex() == identifierIndex) {
				setSelectedComponent((Component) p);
			}
		}

	}

	/**
	 * Shows the ended game. Displays the End Game Panel
	 * 
	 * @param game
	 *            Game to be searched for
	 */
	public void viewEndGame(Game game) {
		// Attempt to find the game in the active panels list
		for (IEndedGamePanel gameSearch : listOfEndedGamePanels) {
			if (game.equals(gameSearch.getGame())) {
				setSelectedComponent((Component) gameSearch);
				invalidate();
				repaint();
				return; // The game has been found and made active. Done!
			}
		}

		// Game not found in the active game list
		EndGamePanel viewGame = new EndGamePanel(game);
		// TODO: MAKE THIS NOT A TAB, MAKE IT OVERWRITE THE MAIN VIEW.

		addTab(getTabName(game), viewGame);
		setToolTipTextAt(getTabCount() - 1, game.getName());

		listOfEndedGamePanels.add(viewGame);

		setSelectedComponent(viewGame);
		invalidate();
		repaint();

	}

	/**
	 * Shows the created deck. Displays the Create Deck panel
	 * 
	 * @param deck
	 *            Deck to be searched for
	 */
	public void viewDeck(Deck deck) {
		// Attempt to find the deck in the active panels list
		for (ICreateDeckPanel deckSearch : listOfCreateDeckPanels) {
			if (deck.equals(deckSearch.getDeck())) {
				setSelectedComponent((Component) deckSearch);
				invalidate();
				repaint();
				return; // The deck has been found and made active. Done!
			}
		}

		// Game not found in the active game list
		CreateDeckPanel viewDeck = new CreateDeckPanel(deck);
		// TODO: MAKE THIS NOT A TAB, MAKE IT OVERWRITE THE MAIN VIEW.
		viewDeck.disableFields();
		addTab(getTabName(deck), viewDeck);
		setToolTipTextAt(getTabCount() - 1, deck.getName());

		listOfCreateDeckPanels.add(viewDeck);

		setSelectedComponent(viewDeck);
		invalidate();
		repaint();

	}

	/**
	 * Creates a tab name that is shortened if the name of the game is longer
	 * than 12 characters
	 * 
	 * @param game
	 *            the game to get the tab text from
	 * @return the tabs text
	 */
	private String getTabName(Deck deck) {
		// Makes the game name not be longer than 12 characters
		StringBuilder tabName = new StringBuilder();
		int subStringLength = deck.getName().length() > 12 ? 13 : deck
				.getName().length();
		tabName.append(deck.getName().subSequence(0, subStringLength));
		if (deck.getName().length() > 12) {
			tabName.append("...");
		}
		return tabName.toString();
	}

	/**
	 * Creates a tab name that is shortened if the name of the game is longer
	 * than 12 characters
	 * 
	 * @param game
	 *            the game to get the tab text from
	 * @return the tabs text
	 */
	private String getTabName(Game game) {
		// Makes the game name not be longer than 12 characters
		StringBuilder tabName = new StringBuilder();
		int subStringLength = game.getName().length() > 12 ? 13 : game
				.getName().length();
		tabName.append(game.getName().subSequence(0, subStringLength));
		if (game.getName().length() > 12) {
			tabName.append("...");
		}
		return tabName.toString();
	}

	/**
	 * Removes the tab for the given JComponent The panel is only removed when
	 * it returns that it is ready to be removed
	 * 
	 * @param comp
	 *            the component to remove
	 */
	public void removeTab(JComponent comp) {
		if (comp instanceof ICreateGamePanel) {
			if (!((ICreateGamePanel) comp).readyToRemove())
				return;
			listOfCreateGamePanels.remove(comp);
			if (comp.equals(getSelectedComponent())) {
				setSelectedComponent(gameOverview);
			}
		}
		if (comp instanceof IActiveGamePanel) {
			if (!((IActiveGamePanel) comp).readyToRemove())
				return;
			listOfActiveGamePanels.remove(comp);
			if (comp.equals(getSelectedComponent())) {
				setSelectedComponent(gameOverview);
			}
		}
		if (comp instanceof IEndedGamePanel) {
			if (!((IEndedGamePanel) comp).readyToRemove())
				return;
			listOfEndedGamePanels.remove(comp);
			if (comp.equals(getSelectedComponent())) {
				setSelectedComponent(gameOverview);
			}
		}
		if (comp instanceof PreferencesPanel) {
			// TODO Implement preferences like other panels to use readyToRemove
			if (!((PreferencesPanel) comp).readyToRemove())
				return;
			listOfEndedGamePanels.remove(comp);
			hasPreferencesTab = false;
			setSelectedComponent(gameOverview);
		}
		if (comp instanceof CreateDeckPanel) {
			if (comp.equals(getSelectedComponent())) {
				setSelectedComponent(deckOverview);
				listOfCreateDeckPanels.remove(comp);
			}
		}
		if (comp instanceof IHelpPanel) {
			if (!((IHelpPanel) comp).readyToRemove())
				return;
			removeHelpPanel((IHelpPanel) comp);
			setSelectedComponent(gameOverview);
		}
		remove(comp);
	}

	private void disableHelpButton(String errorMessage) {
		ViewEventController.getInstance().disableHelpButton(errorMessage);
	}

	private void enableHelpButton() {
		ViewEventController.getInstance().enableHelpButton();
	}

	/**
	 * Closes all of the tabs besides the overview tab in the main view. Used in
	 * conjunction with the tab right click drop-down that allows users to close
	 * multiple tabs at once.
	 */
	public void closeAllTabs() {

		int tabCount = getTabCount();

		System.out.println("THE TAB COUNT IS:" + tabCount);

		for (int i = tabCount - 1; i > 0; i--) {
			Component toBeRemoved = getComponentAt(i);

			System.out.println(toBeRemoved.getClass().getName());

			if ((toBeRemoved instanceof DeckOverview)
					|| toBeRemoved instanceof GameOverview) {
				continue;
			}

			if (toBeRemoved instanceof IActiveGamePanel) {
				if (!((IActiveGamePanel) toBeRemoved).readyToRemove())
					continue;
				listOfActiveGamePanels.remove(toBeRemoved);
			}

			if (toBeRemoved instanceof ICreateGamePanel) {
				if (!((ICreateGamePanel) toBeRemoved).readyToRemove())
					continue;
				listOfCreateGamePanels.remove(toBeRemoved);
			}

			if (toBeRemoved instanceof IEndedGamePanel) {
				if (!((IEndedGamePanel) toBeRemoved).readyToRemove())
					continue;
				listOfEndedGamePanels.remove(toBeRemoved);
			}

			if (toBeRemoved instanceof IPreferencesPanel) {
				if (!((IPreferencesPanel) toBeRemoved).readyToRemove())
					continue;
				listOfEndedGamePanels.remove(toBeRemoved);
				hasPreferencesTab = false;
			}

			if (toBeRemoved instanceof ActiveGameHelp) {
				hasActiveGameHelp = false;
			}

			if (toBeRemoved instanceof CreateDeckHelp) {
				hasCreateDeckHelp = false;
			}

			if (toBeRemoved instanceof CreateGameHelp) {
				hasCreateGameHelp = false;
			}

			if (toBeRemoved instanceof EndGameHelp) {
				hasEndGameHelp = false;
			}

			if (toBeRemoved instanceof PreferencesHelp) {
				hasPreferencesHelp = false;
			}

			removeTabAt(i);
		}
		setSelectedComponent(this.getSelectedComponent());
		repaint();
	}

	/**
	 * Closes all the tabs except for the one that was clicked. Used in
	 * conjunction with the tab right click drop-down that allows users to close
	 * multiple tabs at once.
	 */
	public void closeOthers() {
		int tabCount = getTabCount();
		Component selected = getSelectedComponent();

		for (int i = tabCount - 1; i >= 0; i--) {
			Component toBeRemoved = getComponentAt(i);
			if (toBeRemoved == selected) {
				continue;
			}

			if ((toBeRemoved instanceof DeckOverview)
					|| toBeRemoved instanceof GameOverview) {
				continue;
			}

			if (toBeRemoved instanceof IActiveGamePanel) {
				if (!((IActiveGamePanel) toBeRemoved).readyToRemove()) {
					continue;
				}
				listOfActiveGamePanels.remove(toBeRemoved);
			}

			if (toBeRemoved instanceof ICreateGamePanel) {
				if (!((ICreateGamePanel) toBeRemoved).readyToRemove()) {
					continue;
				}
				listOfCreateGamePanels.remove(toBeRemoved);
			}

			if (toBeRemoved instanceof IEndedGamePanel) {
				if (!((EndGamePanel) toBeRemoved).readyToRemove()) {
					continue;
				}
				listOfEndedGamePanels.remove(toBeRemoved);
			}

			if (toBeRemoved instanceof IPreferencesPanel) {
				if (!((IPreferencesPanel) toBeRemoved).readyToRemove())
					continue;
				listOfEndedGamePanels.remove(toBeRemoved);
				hasPreferencesTab = false;
			}

			if (toBeRemoved instanceof ActiveGameHelp) {
				hasActiveGameHelp = false;
			}

			if (toBeRemoved instanceof CreateDeckHelp) {
				hasCreateDeckHelp = false;
			}

			if (toBeRemoved instanceof CreateGameHelp) {
				hasCreateGameHelp = false;
			}

			if (toBeRemoved instanceof EndGameHelp) {
				hasEndGameHelp = false;
			}

			if (toBeRemoved instanceof PreferencesHelp) {
				hasPreferencesHelp = false;
			}

			removeTabAt(i);
		}
		setSelectedComponent(this.getSelectedComponent());
		repaint();

	}

	public void removeHelpPanel(IHelpPanel comp) {
		listOfHelpPanels.remove(comp);
		resetBoolean(comp);
	}

	public void resetBoolean(IHelpPanel comp) {
		if (comp instanceof ActiveGameHelp) {
			hasActiveGameHelp = false;
		} else if (comp instanceof CreateDeckHelp) {
			hasCreateDeckHelp = false;
		} else if (comp instanceof CreateGameHelp) {
			hasCreateGameHelp = false;
		} else if (comp instanceof EndGameHelp) {
			hasEndGameHelp = false;
		} else if (comp instanceof PreferencesHelp) {
			hasPreferencesHelp = false;
		} else {
			return;
		}
	}

	@Override
	public void setSelectedComponent(Component comp) {
		super.setSelectedComponent(comp);
		if (comp instanceof DeckOverview) {
			disableHelpButton("Help is not available for the Overviews. You must navigate to a tab.");
			
		} else if (comp instanceof GameOverview) {
			disableHelpButton("Help is not available for the Overviews. You must navigate to a tab.");
		} else {
			enableHelpButton();
		}
	}
}
