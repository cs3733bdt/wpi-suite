/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirement.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.NewActiveGamePanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.TabbedView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.tree.GameTree;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation.NewCreateGamePanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.end.EndGamePanel;



/**
 * GUI test for the main view. Initializes the view event controller and adds/removes tabs
 * @author Bobby Drop Tables
 *
 */
public class MainViewTest {

	
	private ViewEventController vec;
	private ToolbarView toolbar;
	private GameTree gameTree;
	private TabbedView tabs;
	private NewActiveGamePanel activeGameTab;
	private NewCreateGamePanel createGameTab;
	private EndGamePanel endGameTab;
	private Game activeGame;
	private ArrayList<Requirement> reqs;
	
	@Before
	public void setUp() throws Exception {
		reqs = new ArrayList<Requirement>();
		reqs.add(new Requirement("Test Req1", "Test Desc1"));
		
		activeGame = new Game("ActiveGame1", "This is active game1",
				reqs, true, true);
				
		vec = ViewEventController.getInstance();
		toolbar = new ToolbarView(true);
		tabs = new TabbedView();
		gameTree = GameTree.getInstance();
		createGameTab = new NewCreateGamePanel(activeGame);
		endGameTab = new EndGamePanel(activeGame);
		//activeGameTab = new NewActiveGamePanel(activeGame);
		vec.setGameOverviewTree(gameTree);
		vec.setTabbedView(tabs);
		vec.setToolBar(toolbar);
		
	}
	
	@Test
	public void numberOfTabs() {
		assertEquals(2, tabs.getTabCount());
		tabs.addTab(activeGame.getName(),createGameTab);
		assertEquals(3, tabs.getTabCount());
		tabs.addTab("EndGame 1", endGameTab);
		assertEquals(4, tabs.getTabCount());
		//tabs.removeTab(activeGameTab);
		//tabs.addTab(activeGame.getName(),activeGameTab);
	}
	
}
