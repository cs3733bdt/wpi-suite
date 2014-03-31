/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JComponent;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.ActiveGamesPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.tree.JoinGameTree;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation.CreateGamePanel;

/**
 * @author jonathanleitschuh
 * 
 */
public class ViewEventController {
	private static ViewEventController instance = null;
	private MainView main = null;
	private ToolbarView toolbar = null;
	private JoinGameTree gameTreee = null;
	private ArrayList<CreateGamePanel> listOfCreateGamePanels = new ArrayList<CreateGamePanel>();
	
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
	
	 * @param mainview The MainView
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
		main.addTab("New Game", null, newGame, "New Game");
		main.invalidate(); //force the tabbedpane to redraw.
		main.repaint();
		main.setSelectedComponent(newGame);
	}
	
	/**
	 * After clicking a game in the games list, the active games view will be displayed.
	 */
	public void joinGame(){
		ActiveGamesPanel viewGame = new ActiveGamesPanel();
		//TODO: MAKE THIS NOT A TAB, MAKE IT OVERWRITE THE MAIN VIEW.
		main.addTab("Active Game",  viewGame);
		main.invalidate();
		main.repaint();
		main.setSelectedComponent(viewGame);
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
			this.listOfCreateGamePanels.remove(comp);
		}
		main.remove(comp);
	}
	
	/**
	 * Closes all of the tabs besides the overview tab in the main view.
	 */
	public void closeAllTabs() {

		int tabCount = main.getTabCount();

		for(int i = tabCount - 1; i >= 0; i--)
		{
			Component toBeRemoved = main.getComponentAt(i);

			if(toBeRemoved instanceof ActiveGamesPanel) continue;
			

			if(toBeRemoved instanceof CreateGamePanel)
			{
				if(!((CreateGamePanel)toBeRemoved).readyToRemove()) continue;
				this.listOfCreateGamePanels.remove(toBeRemoved);
			}

			main.removeTabAt(i);
		}

		main.repaint();
	}
	
	/**
	 * Closes all the tabs except for the one that was clicked.
	 * 
	 */
	public void closeOthers() {
		int tabCount = main.getTabCount();
		Component selected = main.getSelectedComponent();

		for(int i = tabCount - 1; i >= 0; i--)
		{
			Component toBeRemoved = main.getComponentAt(i);

			if(toBeRemoved instanceof ActiveGamesPanel){continue;}
			if(toBeRemoved == selected){
				continue;}

			if(toBeRemoved instanceof CreateGamePanel)
			{
				if(!((CreateGamePanel)toBeRemoved).readyToRemove()){
					break;}
				this.listOfCreateGamePanels.remove(toBeRemoved);
			}

			main.removeTabAt(i);
		}
		main.repaint();

	}

	public void refreshGameTable() {
		// TODO Auto-generated method stub
		//This method will call a method that refreshes the data in the requirement model
		// something like: overviewTable.refresh();
		
	}

	public void refreshGameTree() {
		// TODO Auto-generated method stub
		//This method will call a method that refreshes the the tree
		// something like this.overviewTree.refresh();
		
	}

}
