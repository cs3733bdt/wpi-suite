package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextArea;

public abstract class CustomJTextArea extends JTextArea implements IDataField {

	/** @see JTextArea#JTextArea()*/
	protected CustomJTextArea(){}
	/** @see JTextArea#JTextArea(String) */
	protected CustomJTextArea(String text) { super(text); }
	
	/**
	 * Mouse listener for number of cards textfield. When the text field is
	 * clicked, it selects all of the text so that the user can easily overwrite
	 * their previous number.
	 */
	public void enableSelectAllTextOnMouseListener(){
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				selectAll();
			}
		});
	}

}
