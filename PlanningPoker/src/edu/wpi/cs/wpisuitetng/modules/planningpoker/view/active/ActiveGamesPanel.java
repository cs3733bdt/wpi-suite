package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;

/**
 * @author Jeffrey Signore
 * Sets up the panel for the active games screen, which has the list of all active games in which the user is playing.
 * When you click on a game, the bottom section of the screen will display more details about that specific game.
 */
public class ActiveGamesPanel extends JPanel {
	private Game active;
	
	/**
	 * Set the gameName equal to the name of the game that was selected from the active games list
	 */
	private JTextArea gameName = new JTextArea();
	
	/**
	 * Set the gameDesc equal to the description of the game that was selected from the active games list
	 */
	private JTextArea gameDesc = new JTextArea();
	
	/**
	 * Set the userStoryDesc equal to the description of the requirement being selected in the table
	 */
	private JTextArea userStoryDesc = new JTextArea();
	
	/**
	 * The estText is needed when the user inputs their estimate, since it must be added to the server
	 */
	private JTextField estText = new JTextField();

	public ActiveGamesPanel(final Game game) {
		active = game;
		
		Container rightPanel = new Container();
		rightPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		
		/**
		 * creates and adds the label "Name"
		 */
		JLabel nameLabel = new JLabel("Name: ");
		c.weightx = 0.5;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.gridx = 0;
		c.gridy = 0;
		c.ipadx = 0;
		c.ipady = 0;
		rightPanel.add(nameLabel, c);
		
		
		/**
		 * the game name is determined by whichever game was clicked in the list.
		 */
		gameName.setText(game.getName());
		gameName.setEditable(false);
		gameName.setLineWrap(true);
		c.weightx = 0.75;
		c.gridwidth = 8;
		c.gridx = 1;
		c.gridy = 0;
		rightPanel.add(gameName, c);
		
		
		/**
		 * creates and adds the label "Description"
		 */
		JLabel descLabel = new JLabel("Description: ");
		c.gridwidth = 1;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 1;
		c.ipadx = 0;
		c.ipady = 0;
		rightPanel.add(descLabel, c);
		
		
		/**
		 * the description is determined by whichever game was clicked in the list.
		 */
		gameDesc.setText(game.getDescription());
		gameDesc.setEditable(false);
		gameDesc.setLineWrap(true);
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.weightx = 0.75;
		c.gridwidth = 8;
		c.gridx = 1;
		c.gridy = 1;
		rightPanel.add(gameDesc, c);
		
		
		/**
		 * Initializes the two columns for the table of requirements.
		 */
		String[] columnNames = {"Requirement", "Description"};
		Object[][] data = {};
		final ActiveGamesTable table = new ActiveGamesTable(data, columnNames);
		
		/**
		 * Adds data to the table
		 */
		for(int i = 0; i < game.getRequirements().size(); i++){
			table.tableModel.addRow(new Object[]{game.getRequirements().get(i).getName(),game.getRequirements().get(i).getDescription()});
		}
		
		
		table.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					JTable target = (JTable) e.getSource();
					int row = target.getSelectedRow();
					int column = target.getSelectedColumn();
					String selected = (String) target.getValueAt(row, column);
					for(int i = 0; i < game.getRequirements().size(); i ++){
						if(selected.equals(game.getRequirements().get(i).getName()) || 
								selected.equals(game.getRequirements().get(i).getDescription()) ){
							userStoryDesc.setText(game.getRequirements().get(i).getDescription());
						}
					}
				}
			}
		});
		
		/**
		 * Puts the table within a scroll pane, and adds to the view.
		 */
		JScrollPane tablePanel = new JScrollPane(table);
		c.gridwidth = 8;
		c.gridheight = 1;
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 2;
		c.ipady = -300;
		rightPanel.add(tablePanel, c);
	
		
		/**
		 * Creates and adds the user story text area to the view.
		 */
		userStoryDesc.setText("Requirement Description");
		
		//create a dummy JTextArea
		JTextArea editingArea = new JTextArea();
		// get the current font
		Font f = editingArea.getFont();

		// create a new, smaller font from the current font
		Font newFont = new Font(f.getFontName(), f.getStyle(), f.getSize()+5);
		
		//set the bigger font for userStoryDesc
		Font bigFont = newFont;
		userStoryDesc.setFont(bigFont);
		userStoryDesc.setEditable(false);		
		c.gridwidth = 8;
		c.weightx = 0.75;
		c.gridx = 1;
		c.gridy = 3;
		c.ipady = 50;
		rightPanel.add(userStoryDesc, c);
		
		
		/**
		 * Creates and adds the 1st card to the view.
		 */
		JButton but0 = new JButton("1");
		c.gridwidth = 1;
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 4;
		c.ipadx = 20;
		c.ipady = 75;
		rightPanel.add(but0, c);
		
		/**
		 * Creates and adds the 2nd card to the view.
		 */
		JButton but1 = new JButton("1");
		c.gridx = 2;
		c.gridy = 4;
		c.ipadx = 20;
		c.ipady = 75;
		rightPanel.add(but1, c);
		
		/**
		 * Creates and adds the 3rd card to the view.
		 */
		JButton but2 = new JButton("2");
		c.gridx = 3;
		c.gridy = 4;
		c.ipadx = 20;
		c.ipady = 75;
		rightPanel.add(but2, c);
		
		/**
		 * Creates and adds the 4th card to the view.
		 */
		JButton but3 = new JButton("3");
		c.gridx = 4;
		c.gridy = 4;
		c.ipadx = 20;
		c.ipady = 75;
		rightPanel.add(but3, c);
		
		/**
		 * Creates and adds the 5th card to the view.
		 */
		JButton but4 = new JButton("5");
		c.gridx = 5;
		c.gridy = 4;
		c.ipadx = 20;
		c.ipady = 75;
		rightPanel.add(but4, c);
		
		/**
		 * Creates and adds the 6th card to the view.
		 */
		JButton but5 = new JButton("8");
		c.gridx = 6;
		c.gridy = 4;
		c.ipadx = 20;
		c.ipady = 75;
		rightPanel.add(but5, c);
		
		/**
		 * Creates and adds the 7th card to the view.
		 */
		JButton but6 = new JButton("13");
		c.gridx = 7;
		c.gridy = 4;
		c.ipadx = 20;
		c.ipady = 75;
		rightPanel.add(but6, c);
		
		/**
		 * Creates and adds the 8th card to the view.
		 */
		JButton but7 = new JButton("0?");
		c.gridx = 8;
		c.gridy = 4;
		c.ipadx = 20;
		c.ipady = 75;
		rightPanel.add(but7, c);
		
		/**
		 * The text area where the user types their estimate
		 */
		estText.setText("Estimate Here");
		estText.setPreferredSize(new Dimension(75,40));
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.gridwidth = 1;
		c.gridx = 5;
		c.gridy = 5;
		rightPanel.add(estText, c);
		
		if(game.doesUseCards()){
			estText.setVisible(false);
		}
		else{
			but0.setVisible(false);
			but1.setVisible(false);
			but2.setVisible(false);
			but3.setVisible(false);
			but4.setVisible(false);
			but5.setVisible(false);
			but6.setVisible(false);
			but7.setVisible(false);
		}
		
		/**
		 * A blank panel for formatting purposes
		 */
		JPanel invisPanel = new JPanel();
		c.gridwidth = 1;
		c.weightx = 0.5;
		c.gridx = 9;
		c.gridy = 4;
		c.ipadx = 0;
		c.ipady = 0;		
		rightPanel.add(invisPanel, c);
		
		/**
		 * The submit button for when the user is ready to submit the estimate
		 */
		JButton submitEstimate = new JButton("Submit Estimate");
		c.gridwidth = 2;
		c.gridx = 4;
		c.gridy = 6;
		rightPanel.add(submitEstimate, c);
		
		this.add(rightPanel);
		
	}
	
	public JTextField getEstimateText() {
		return estText;
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
		//TODO Make this validate
		return true;
	}
	
	public Game getGame(){
		return active;
	}
}