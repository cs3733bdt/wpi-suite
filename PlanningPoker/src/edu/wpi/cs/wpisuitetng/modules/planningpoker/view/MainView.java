package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

/*import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;*/

import javax.swing.JSplitPane;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.GamesTreePanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.TabbedView;

@SuppressWarnings("serial")
public class MainView extends JSplitPane {
	TabbedView tabView = new TabbedView();
	GamesTreePanel filterPanel = new GamesTreePanel();
	
	public MainView() {
		
		
		/*Container rightPanel = new Container();
		rightPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;*/
		
		this.setRightComponent(tabView);
		this.setLeftComponent(filterPanel);
		this.setDividerLocation(180);
		
	}
	
	public TabbedView getTabbedView(){
		return tabView;
	}
}
