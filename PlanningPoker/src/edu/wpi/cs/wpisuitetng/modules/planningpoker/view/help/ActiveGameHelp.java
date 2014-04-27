package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.help;

import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;

public class ActiveGameHelp extends JScrollPane implements IHelpPanel {
	JLabel headingLabel;
	
	public ActiveGameHelp() {
		build();
	}
	
	private void build() {
		/**
		 *  Set up initial container with spring layout */
		Container view = new Container();
		SpringLayout layout = new SpringLayout();
		view.setLayout(layout);
		
		//Add the heading label to the Panel
		headingLabel = new JLabel("Active Games Help");
		headingLabel.setFont(makeFont(8));
		view.add(headingLabel);
		
		/**
		 * Constraints for the overall panel layout
		 */
		layout.putConstraint(SpringLayout.NORTH, headingLabel, 5, SpringLayout.NORTH, view);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, headingLabel, 0, SpringLayout.HORIZONTAL_CENTER, view);
		setViewportView(view);
		revalidate();
		repaint();
	}
	
	/**
	 * Creates a font to be used for later
	 * @param size The size of the font
	 * @return font to be used 
	 */
	public Font makeFont(int size) {
		//create a dummy JTextArea
		JTextArea editingArea = new JTextArea();
		// get the current font
		Font f = editingArea.getFont();
		// create a new, larger font from the current font
		Font newFont = new Font(f.getFontName(), f.getStyle(), f.getSize()+size);		
		//set the bigger font for userStoryDesc
		Font bigFont = newFont;
		return bigFont;
	}

	@Override
	public boolean readyToRemove() {
		return true;
	}


	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.planningpoker.view.help.IHelpPanel#getIdentifierIndex()
	 */
	@Override
	public int getIdentifierIndex() {
		return 2;
	}
	
	@Override
	public JLabel addImage(String image){
		JLabel helpLabel = new JLabel();
		
		try {
			Image imageToBeAdded = ImageIO.read(getClass().getResource(image));
			ImageIcon helpImage = new ImageIcon(imageToBeAdded);
			helpLabel = new JLabel(helpImage);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return helpLabel;
		
	}

}
