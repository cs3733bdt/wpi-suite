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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.end;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.abstractmodel.AbstractModelObserver;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.abstractmodel.ObservableModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirement.models.Requirement;
/**
 * used to display the completed game and requirements within it
 * @author TomPaolillo
 */
public class EndGamePanel extends JScrollPane implements AbstractModelObserver{
	private final Border defaultBorder = (new JTextField()).getBorder();
	
	private final Game active;

	/**
	 * Set the gameName equal to the name of the game that was selected from the
	 * active games list
	 */
	private final JTextArea gameName = new JTextArea();

	/**
	 * Set the gameDesc equal to the description of the game that was selected
	 * from the active games list
	 */
	private final JTextArea gameDesc = new JTextArea();

	/**
	 * Set the userStoryDesc equal to the description of the requirement being
	 * selected in the table
	 */
	private final JTextArea userStoryDesc = new JTextArea();

	/**
	 * The estText is needed when the user inputs their estimate, since it must
	 * be added to the server
	 */
	/** Shows the names of the errors */
	JLabel errorField = new JLabel();
	
	JPanel topHalfPanel = new JPanel();
	
	JPanel middleHalfPanel = new JPanel();
	
	Container rightPanel = new Container();
	
	GridBagConstraints c = new GridBagConstraints();
	
	private boolean isEstimatePanelCreated = false;
	
	/**
	 * Creates a scrollPane to contain everything
	 */
	//private JScrollPane activeGameScrollPane;
	
	private final JPanel blankPanel2;

	public EndGamePanel(final Game game) {
		//super(new GridBagLayout());
	
		game.addObserver(this); //Makes this the observer for the game
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		active = game;
		isEstimatePanelCreated = false;
	
		topHalfPanel.setLayout(new GridBagLayout());

		c.fill = GridBagConstraints.HORIZONTAL;

		/**
		 * creates and adds the label "Name"
		 */
		JLabel nameLabel = new JLabel("Name: ");
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0;
		c.weighty = 1;
		topHalfPanel.add(nameLabel, c);

		/**
		 * the game name is determined by whichever game was clicked in the
		 * list.
		 */
		gameName.setText(game.getName());
		gameName.setEditable(false);
		gameName.setLineWrap(true);
		gameName.setPreferredSize(new Dimension(450, 20));
		gameName.setBorder(defaultBorder);
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 1;
		topHalfPanel.add(gameName, c);
		
		/**
		 * Blank Panel for formatting purposes
		 */
		JPanel blankPanel0 = new JPanel();
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		c.weightx = 0;
		c.weighty = 1;
		blankPanel0.setPreferredSize(new Dimension(515, 10));
		topHalfPanel.add(blankPanel0, c); 

		/**
		 * creates and adds the label "Description"
		 */
		JLabel descLabel = new JLabel("Description: ");
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 0;
		c.weighty = 1;
		topHalfPanel.add(descLabel, c);
		
		/**
		 * Creates a scroll pane for the game description
		 */
		JScrollPane descPane = new JScrollPane(gameDesc);	
		/**
		 * the description is determined by whichever game was clicked in the list
		 */
		gameDesc.setText(game.getDescription());
		gameDesc.setEditable(false);
		gameDesc.setLineWrap(true);
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = 2;
		c.weightx = 1;
		c.weighty = 1;
		descPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		descPane.setPreferredSize(new Dimension(450, 80));
		topHalfPanel.add(descPane, c);
		
		/**
		 * Blank Panel for formatting purposes
		 */
		JPanel blankPanel1 = new JPanel();
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 2;
		c.weightx = 0;
		c.weighty = 1;
		blankPanel1.setPreferredSize(new Dimension(500, 10));
		topHalfPanel.add(blankPanel1, c);
		
		/**
		 * Initializes the two columns for the table of requirements.
		 */
		String[] columnNames = { "Requirement", "Description", "Complete"};
		Object[][] data = {};
		final EndGameTable table = new EndGameTable(data, columnNames);
		table.getColumnModel().getColumn(0).setMinWidth(300);
		table.getColumnModel().getColumn(1).setMinWidth(300);
		table.getColumnModel().getColumn(2).setMinWidth(50);
		table.getColumnModel().getColumn(2).setMaxWidth(75);
		

		/**
		 * Adds data to the table
		 */
		for (int i = 0; i < game.getRequirements().size(); i++) {
			table.getTableModel().addRow(new Object[] {
					game.getRequirements().get(i).getName(),
					game.getRequirements().get(i).getDescription(),
					game.getRequirements().get(i).displayComplete() });
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
						if (selected.equals(game.getRequirements().get(i).getName()) ||
								selected.equals(game.getRequirements().get(i).getDescription())) {
							if(isEstimatePanelCreated){
								removeStatisticsPanel();
								updateStatisticsPanel(game, game.getRequirements().get(i));
							}
							else{
								updateStatisticsPanel(game, game.getRequirements().get(i));
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
		c.weightx = 1;
		c.gridx = 0;
		c.gridy = 4;
		c.weighty = 1;
		tablePanel.setPreferredSize(new Dimension(450, 100));
		topHalfPanel.add(tablePanel, c);
		
		/**
		 * Blank Panel for formatting purposes
		 */
		blankPanel2 = new JPanel();
		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 2;
		c.weightx = 0;
		c.weighty = 0;
		blankPanel2.setPreferredSize(new Dimension(500, 300));
		topHalfPanel.add(blankPanel2, c);
		
		rightPanel.add(topHalfPanel);
	
		this.getViewport().add(rightPanel);
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
	
	public void updateStatisticsPanel(Game game, Requirement requirement){
		blankPanel2.setVisible(false);
		rightPanel.add(new StatisticsPanel(game, requirement));
		rightPanel.revalidate();
		this.revalidate();
	}
	
	public void removeStatisticsPanel(){
		blankPanel2.setVisible(false);
		rightPanel.remove(1);
		rightPanel.repaint();
		//activeGameScrollPane.repaint();
		this.revalidate();
		//this.repaint();
	}

	@Override
	public void update(ObservableModel o, Object arg) {
		if(o instanceof Game){
			//TODO Handle an update to a model
		}
	}
	
	public void endGameButtonPressed(){
		active.makeComplete();
		active.notifyObservers();
	}
	
	public static void main(String args[]){
		JFrame frame = new JFrame("GridBagLayoutDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        List<Requirement> reqs = new ArrayList<Requirement>();
        reqs.add(new Requirement("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW",
				"WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW"
				+ "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW"
				+ "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW"
				+ "WWWWWWWWWWWWWWWWWWWWWWWWWWWW"));
				
        //Set up the content pane.
        frame.add(new EndGamePanel(new Game("name", "desc", reqs, false, true)));
        frame.setSize(400, 400);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
	}

}