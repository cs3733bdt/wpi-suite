package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirementmodels.Requirement;
/**
 * @author Jeffrey Signore Sets up the panel for the active games screen, which
 *         has the list of all active games in which the user is playing. When
 *         you click on a game, the bottom section of the screen will display
 *         more details about that specific game.
 */
public class ActiveGamesPanel extends JPanel {
	private Game active;

	/**
	 * Set the gameName equal to the name of the game that was selected from the
	 * active games list
	 */
	private JTextArea gameName = new JTextArea();

	/**
	 * Set the gameDesc equal to the description of the game that was selected
	 * from the active games list
	 */
	private JTextArea gameDesc = new JTextArea();

	/**
	 * Set the userStoryDesc equal to the description of the requirement being
	 * selected in the table
	 */
	private JTextArea userStoryDesc = new JTextArea();

	/**
	 * The estText is needed when the user inputs their estimate, since it must
	 * be added to the server
	 */
	/** Shows the names of the errors */
	JLabel errorField = new JLabel();
	
	private boolean isEstimatePanelCreated = false;

	public ActiveGamesPanel(final Game game) {
		active = game;
		this.isEstimatePanelCreated = false;
		
		final Container rightPanel = new Container();
		rightPanel.setLayout(new GridBagLayout());
		rightPanel.setPreferredSize(new Dimension(700,300));

		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.HORIZONTAL;

		/**
		 * creates and adds the label "Name"
		 */
		JLabel nameLabel = new JLabel("Name: ");
		//c.weightx = 0.5;
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = 0;
		//c.ipadx = 0;
		//c.ipady = 0;
		rightPanel.add(nameLabel, c);

		/**
		 * the game name is determined by whichever game was clicked in the
		 * list.
		 */
		gameName.setText(game.getName());
		gameName.setEditable(false);
		gameName.setLineWrap(true);
		//c.weightx = 0.75;
		//c.gridwidth = 2;
		c.gridx = 1;
		c.gridy = 0;
		rightPanel.add(gameName, c);
		
		/**
		 * Blank Panel for formatting purposes
		 */
		JPanel blankPanel0 = new JPanel();
		//blankPanel0.setMinimumSize(new Dimension(310, 10));
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		blankPanel0.setPreferredSize(new Dimension(500, 10));
		rightPanel.add(blankPanel0, c);

		/**
		 * creates and adds the label "Description"
		 */
		JLabel descLabel = new JLabel("Description: ");
		c.gridwidth = 1;
		//c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 2;
		//c.ipadx = 0;
		//c.ipady = 0;
		rightPanel.add(descLabel, c);
		
		/**
		 * Creates a scroll pane for the game description
		 */
		JScrollPane descPane = new JScrollPane(gameDesc);	
		/**
		 * the description is determined by whichever game was clicked in the list
		 */
		//gameDesc.setMaximumSize(new Dimension(100,100));
		gameDesc.setText(game.getDescription());
		gameDesc.setEditable(false);
		gameDesc.setLineWrap(true);
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		//c.weightx = 0.75;
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = 2;
		descPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		descPane.setMinimumSize(new Dimension(400, 80));
		descPane.setPreferredSize(new Dimension(450, 80));
		rightPanel.add(descPane, c);

		/**
		 * Initializes the two columns for the table of requirements.
		 */
		String[] columnNames = { "Requirement", "Description" };
		Object[][] data = {};
		final ActiveGamesTable table = new ActiveGamesTable(data, columnNames);

		/**
		 * Adds data to the table
		 */
		for (int i = 0; i < game.getRequirements().size(); i++) {
			table.tableModel.addRow(new Object[] {
					game.getRequirements().get(i).getName(),
					game.getRequirements().get(i).getDescription() });
		}

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					JTable target = (JTable) e.getSource();
					int row = target.getSelectedRow();
					int column = target.getSelectedColumn();
					String selected = (String) target.getValueAt(row, column);
					for (int i = 0; i < game.getRequirements().size(); i++) {
						if (selected.equals(game.getRequirements().get(i).getName())
								|| selected.equals(game.getRequirements().get(i).getDescription())) {
							if(isEstimatePanelCreated == true){
								removeEstimatePanel();
								updateEstimatePanel(game, game.getRequirements().get(i));
							}
							else{
								updateEstimatePanel(game, game.getRequirements().get(i));
								isEstimatePanelCreated = true;
							}
						}
					}
				}
			}
		});
		
		/**
		 * Puts the table within a scroll pane, and adds to the view.
		 */
		JScrollPane tablePanel = new JScrollPane(table);
		c.gridwidth = 2;
		//c.gridheight = 1;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 3;
		//c.ipady = -300;
		tablePanel.setPreferredSize(new Dimension(450, 100));
		tablePanel.setMaximumSize(new Dimension(450, 100));	
		rightPanel.add(tablePanel, c);
		
	
		this.add(rightPanel);
	}

	public void setGameName(String newGameName) {
		gameName.setText(newGameName);
	}

	public void setGameDesc(String newGameDesc) {
		gameDesc.setText(newGameDesc);
	}

	public void setUserStoryDesc(String newUserStoryDesc) {
		userStoryDesc.setText(newUserStoryDesc);
	}

	public boolean readyToRemove() {
		// TODO Make this validate
		return true;
	}

	public Game getGame() {
		return active;
	}
	
	public void updateEstimatePanel(Game game, Requirement requirement){
		this.add(new EstimatePanel(game, requirement));
		this.repaint();
	}
	
	public void removeEstimatePanel(){
		this.remove(1);
		this.repaint();
	}

}