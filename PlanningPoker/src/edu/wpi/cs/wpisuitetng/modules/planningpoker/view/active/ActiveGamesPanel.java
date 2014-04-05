package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.game.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.game.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.requirement.Requirement;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.vote.Vote;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;

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
	private JTextField estText = new JTextField();

	/** Shows the names of the errors */
	JLabel errorField = new JLabel();

	/** Field Border Definitions */
	private final Border defaultBorder = (new JTextField()).getBorder();
	private final Border errorBorder = BorderFactory
			.createLineBorder(Color.RED);

	/* End field Border Definitions */

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
						if (selected.equals(game.getRequirements().get(i)
								.getName())
								|| selected.equals(game.getRequirements()
										.get(i).getDescription())) {
							userStoryDesc.setText(game.getRequirements().get(i)
									.getDescription());
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

		/**
		 * Creates and adds the user story text area to the view.
		 */
		userStoryDesc.setText("Requirement Description");

		// create a dummy JTextArea
		JTextArea editingArea = new JTextArea();
		// get the current font
		Font f = editingArea.getFont();

		// create a new, smaller font from the current font
		Font newFont = new Font(f.getFontName(), f.getStyle(), f.getSize() + 5);

		// set the bigger font for userStoryDesc
		Font bigFont = newFont;
		
		JScrollPane userStoryPane = new JScrollPane(userStoryDesc);
		userStoryDesc.setFont(bigFont);
		userStoryDesc.setLineWrap(true);
		userStoryDesc.setEditable(false);
		c.gridwidth = 2;
		//c.weightx = 0.75;
		c.gridx = 0;
		c.gridy = 4;
		//c.ipady = 50;
		userStoryPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		userStoryPane.setPreferredSize(new Dimension(400, 150));
		userStoryPane.setMaximumSize(new Dimension(400, 150));
		rightPanel.add(userStoryPane, c);
		
		/**
		 * Creates a panel for all the cards
		 */
		JPanel cardPanel = new JPanel();
		cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.X_AXIS));
		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 2;
		cardPanel.setPreferredSize(new Dimension(425, 50));
		rightPanel.add(cardPanel, c);
		
		/**
		 * Creates and adds the 1st card to the view.
		 */
		JButton but0 = new JButton("1");
		/*c.gridwidth = 1;
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 4;
		c.ipadx = 20;
		c.ipady = 75;*/
		cardPanel.add(but0);

		/**
		 * Creates and adds the 2nd card to the view.
		 */
		JButton but1 = new JButton("1");
		/*c.gridx = 2;
		c.gridy = 4;
		c.ipadx = 20;
		c.ipady = 75;*/
		cardPanel.add(but1);

		/**
		 * Creates and adds the 3rd card to the view.
		 */
		JButton but2 = new JButton("2");
		/*c.gridx = 3;
		c.gridy = 4;
		c.ipadx = 20;
		c.ipady = 75;*/
		cardPanel.add(but2);

		/**
		 * Creates and adds the 4th card to the view.
		 */
		JButton but3 = new JButton("3");
		/*c.gridx = 4;
		c.gridy = 4;
		c.ipadx = 20;
		c.ipady = 75;*/
		cardPanel.add(but3);

		/**
		 * Creates and adds the 5th card to the view.
		 */
		JButton but4 = new JButton("5");
		/*c.gridx = 5;
		c.gridy = 4;
		c.ipadx = 20;
		c.ipady = 75;*/
		cardPanel.add(but4);

		/**
		 * Creates and adds the 6th card to the view.
		 */
		JButton but5 = new JButton("8");
		/*c.gridx = 6;
		c.gridy = 4;
		c.ipadx = 20;
		c.ipady = 75;*/
		cardPanel.add(but5);

		/**
		 * Creates and adds the 7th card to the view.
		 */
		JButton but6 = new JButton("13");
		/*c.gridx = 7;
		c.gridy = 4;
		c.ipadx = 20;
		c.ipady = 75;*/
		cardPanel.add(but6);

		/**
		 * Creates and adds the 8th card to the view.
		 */
		JButton but7 = new JButton("0?");
		/*c.gridx = 8;
		c.gridy = 4;
		c.ipadx = 20;
		c.ipady = 75;*/
		cardPanel.add(but7);

		/**
		 * The text area where the user types their estimate
		 */
		estText.setText("Estimate Here");
		estText.setPreferredSize(new Dimension(75, 40));
		//c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 5;
		estText.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                estText.setText("");
            }
        });
		
		rightPanel.add(estText, c);

		if (game.doesUseCards()) {
			estText.setVisible(false);
		} else {
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
		c.gridwidth = 2;
		//c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 6;
		//c.ipadx = 0;
		//c.ipady = 0;
		invisPanel.setPreferredSize(new Dimension(400, 25));
		//rightPanel.add(invisPanel, c);

		/**
		 * The submit button for when the user is ready to submit the estimate
		 */
		JButton submitEstimate = new JButton("Submit Estimate");
		submitEstimate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				submitEstimatePressed();
			}
		});

		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 7;
		rightPanel.add(submitEstimate, c);

		/**
		 * label for displaying errors
		 */
		errorField = new JLabel();
		errorField.setMinimumSize(new Dimension(150, 25));
		errorField.setForeground(Color.RED);
		c.gridx = 0;
		c.gridy = 8;
		//c.insets = new Insets(0, 175, 0, 0);
		rightPanel.add(errorField, c);

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
		// TODO Make this validate
		return true;
	}

	public Game getGame() {
		return active;
	}

	public void submitEstimatePressed() {
		if(getGame().doesUseCards()){
			this.submitEstimate();
			System.out.println("Submit Vote Pressed Passed");
		}
		else{
			if (this.validateField(true)) {
				this.submitEstimate();
				System.out.println("Submit Vote Pressed Passed.");
			} else {
				System.out.println("Submit Vote Pressed Failed.");
			}
		}

	}

	private boolean validateField(boolean warn) {
		boolean isEstimateValid = false;
		
		// check to see if estimate is parsable to int
		boolean parsable = true;
		try{
		Integer.parseInt(getEstimateText().getText());

		}catch(NumberFormatException e){
		parsable = false;
		}
		// end parsable check
		
				
		//BEGIN ESTIMATE BOX VALDATION
		if (getEstimateText().getText().length() <= 0) {
			isEstimateValid = false;
			if (warn) {
				getEstimateText().setBorder(errorBorder);
			}
			displayError("An estimation is required before submission");
		} else if(parsable == true){
			if(Integer.parseInt(getEstimateText().getText()) < 0){
				isEstimateValid = false;
				if (warn) {
					getEstimateText().setBorder(errorBorder);
				}
				displayError("An estimate must be at least 0");
			}
			else{ 
				if(warn) {
					getEstimateText().setBorder(defaultBorder);
				}
				isEstimateValid = true;
			}
		}
		else{
			isEstimateValid = false;
			if (warn) {
				getEstimateText().setBorder(errorBorder);
			}
			displayError("An estimation must contain only numbers");
		}
		
		
		return isEstimateValid;
	}

	public void submitEstimate() {
		String currentUser = ConfigManager.getConfig().getUserName(); // Gets the currently active user
		int voteNumber;
		if(getGame().doesUseCards()){
			voteNumber = 1;
		}
		else{
			voteNumber = Integer.parseInt(estText.getText());
		}
		Vote vote = new Vote(currentUser, voteNumber);

		// I am currently working on updating a game's requirements to
		// reflect the addition of the vote. Until then, I am printing out
		// the fields of the vote to ensure the information is getting through
		System.out.println("current user: " + vote.getUsername());
		System.out.println("vote number: " + vote.getVoteNumber());
		// these lines should be deleted when proper implementation is complete

		ViewEventController.getInstance().refreshGameTable();
		ViewEventController.getInstance().refreshGameTree();
	}

	public void displayError(String error) {
		errorField.setText(error);
	}

}