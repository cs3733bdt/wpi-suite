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

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.abstractmodel.AbstractModelObserver;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.abstractmodel.ObservableModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.game.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirement.models.Requirement;
/**
 * used to display the completed game and requirements within it
 * @author TomPaolillo
 */
public class EndGamePanel extends JSplitPane implements AbstractModelObserver, IEndedGamePanel{
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
	
	private Game game;
	
	private boolean isEstimatePanelCreated = false;

	private StatisticsPanel rightHalf;
	
	private EndGameLeftHalf leftHalf;
	
	/**
	 * Creates a scrollPane to contain everything
	 */
	//private JScrollPane activeGameScrol

	public EndGamePanel(Game game) {
		this.game = game;

		rightHalf = new StatisticsPanel(game);
		leftHalf = new EndGameLeftHalf(game, this);
	
		game.addObserver(this); //Makes this the observer for the game
		active = game;
		isEstimatePanelCreated = false;
	
		setLeftComponent(leftHalf);
		setRightComponent(rightHalf);
		
		rightHalf.setMinimumSize(new Dimension(333, 500));
		setDividerLocation(420);
		
	}
	
	public void updateRightHalf(Requirement req) {
		rightHalf.reqClicked(req);
		return;		
	}

	
		
//		/**
//		 * Initializes the two columns for the table of requirements.
//		 */
//		String[] columnNames = { "Requirement", "Description", "Complete"};
//		Object[][] data = {};
//		final EndGameTable table = new EndGameTable(data, columnNames);
//		table.getColumnModel().getColumn(0).setMinWidth(300);
//		table.getColumnModel().getColumn(1).setMinWidth(300);
//		table.getColumnModel().getColumn(2).setMinWidth(50);
//		table.getColumnModel().getColumn(2).setMaxWidth(75);
//		
//
//		/**
//		 * Adds data to the table
//		 */
//		for (int i = 0; i < game.getRequirements().size(); i++) {
//			table.getTableModel().addRow(new Object[] {
//					game.getRequirements().get(i).getName(),
//					game.getRequirements().get(i).getDescription(),
//					game.getRequirements().get(i).displayComplete() });
//		}
//		
//		table.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				if (e.getClickCount() == 1) {
//					JTable target = (JTable) e.getSource();
//					int row = target.getSelectedRow();
//					int column = target.getSelectedColumn();
//					String selected = (String) target.getValueAt(row, column);
//					for (int i = 0; i < game.getRequirements().size(); i++) {
//						if (selected.equals(game.getRequirements().get(i).getName()) ||
//								selected.equals(game.getRequirements().get(i).getDescription())) {
//							if(isEstimatePanelCreated){
//								removeStatisticsPanel();
//								updateStatisticsPanel(game, game.getRequirements().get(i));
//							}
//							else{
//								updateStatisticsPanel(game, game.getRequirements().get(i));
//								isEstimatePanelCreated = true;
//							}
//						}
//					}
//				}
//			}
//		});
		
		
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
	
//	public void updateStatisticsPanel(Game game, Requirement requirement){ //TODO rewrite
//		blankPanel2.setVisible(false);
//		rightPanel.add(new StatisticsPanel(game, requirement));
//		rightPanel.revalidate();
//		revalidate();
//	}
//	
//	public void removeStatisticsPanel(){ //TODO rewrite
//		blankPanel2.setVisible(false);
//		rightPanel.remove(1);
//		rightPanel.repaint();
//		//activeGameScrollPane.repaint();
//		revalidate();
//		//this.repaint();
//	}

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