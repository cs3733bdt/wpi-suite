package edu.wpi.cs.wpisuitetng.modules.planningpoker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.Timer;

import edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule;
import edu.wpi.cs.wpisuitetng.janeway.modules.JanewayTabModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetGameController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.MainView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ToolbarView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.TabbedView;

/**
 * This is the module for WPISuite that provides the Planning Poker system.
 * This is where WPISuite users will be able to play the Planning Poker game.
 * 
 * @author Andrew Busch
 */
public class PlanningPoker implements IJanewayModule {
	
	List<JanewayTabModel> tabs;

	/**
	 * This is the only constructor for PlanningPoker. It constructs the main
	 * view of the PlanningPoker module. Only creates the toolbar panel and the
	 * main panel.
	 */
	public PlanningPoker() {
	    // Initialize the list of tabs (however, this module has only one tab)
	    tabs = new ArrayList<JanewayTabModel>();

	    // Create a JPanel to hold the toolbar for the tab
	    ToolbarView toolbarPanel = new ToolbarView();

	    // Create a JPanel to hold the main contents of the tab
	    MainView mainPanel = new MainView();
	    
	    //Add the panels to the view controller
	    ViewEventController.getInstance().setMainView(mainPanel);
	    ViewEventController.getInstance().setToolBar(toolbarPanel);

	    // Create a tab model that contains the toolbar panel and the main content panel
	    JanewayTabModel tab1 = new JanewayTabModel(getName(), new ImageIcon(), toolbarPanel, mainPanel);

	    // Add the tab to the list of tabs owned by this module
	    tabs.add(tab1);
	}
	
	/**
	 * Returns the name of the PlanningPoker tab.
	
	 * @return String * @see edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule#getName() * @see edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule#getName()
	 */
	@Override
	public String getName() {
		return "PlanningPoker";
	}

	
	/**
	 * Returns the tab that makes up the Planning Poker tab.
	
	
	 * @return List<JanewayTabModel> * @see edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule#getTabs() * @see edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule#getTabs()
	 */
	@Override
	public List<JanewayTabModel> getTabs() {
		return tabs;
	}

}
