/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import java.util.ArrayList;

import javax.swing.JComponent;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation.CreateGamePanel;

/**
 * @author jonathanleitschuh
 *
 */
public class ViewEventController {
	private static ViewEventController instance = null;
	private MainView main = null;
	private ToolbarView toolbar = null;
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
	public void joinGame(){
		
	}
	**/
	
	//When creating this method remember to uncomment the call for this in the closable tab component class
	public void removeTab(JComponent comp){
		if (comp instanceof CreateGamePanel){
			if(!((CreateGamePanel) comp).readyToRemove()) return;
			
		}
	} 

}
