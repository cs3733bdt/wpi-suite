/**
 * @author TomPaolillo
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.lists;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;

public class JoinGameList extends JPanel{
	// initialize the main view toolbar buttons
		private ArrayList<String> gameNames = new ArrayList<String>(); 	
		private JList joinGameList;
		private JScrollPane joinGameScroll;
		private final JPanel contentPanel = new JPanel();
	
	public JoinGameList(){
		this.gameNames.add("test game1");
		this.gameNames.add("test game2");
		this.gameNames.add("test game3");
		this.gameNames.add("test game4");
		this.gameNames.add("test game5");
		
		this.joinGameList = new JList(gameNames.toArray());
		this.joinGameScroll = new JScrollPane(joinGameList);
		
		this.setPreferredSize(new Dimension(190,350));
		this.joinGameScroll.setPreferredSize(new Dimension(175,350));
		this.joinGameList.setPreferredSize(new Dimension(200,1000));
		
	
		
		// the action listener for the Create Game Button
		/**joinGameList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// bring up a create game panel
					ViewEventController.getInstance().joinGame("test string");
			}
		});
		**/
		this.add(joinGameScroll);
	}
	
	/**
	 * Method getJoinGameList.
	
	 * @return JList */
	public JList getJoinGameList() {
		return joinGameList;
	}
	
}
