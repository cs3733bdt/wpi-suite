/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

/**
 * @author jonathanleitschuh
 *
 */
public class ViewEventController {
	private static ViewEventController instance = null;
	private MainView main = null;
	private ToolbarView toolbar = null;
	
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

}
