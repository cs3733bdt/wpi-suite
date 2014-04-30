package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components;

import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

/**
 * @author jonathanleitschuh
 * 
 * The NumberJTextField is designed to prevent the user from entering
 *
 */
public class NumberJTextField extends JTextField implements IDataField {
	private final Border defaultBorder = (new JTextField()).getBorder();
	private final Border errorBorder = BorderFactory
			.createLineBorder(Color.RED);
	
	private String initialText;
	private IErrorView warningField;

	public NumberJTextField() {
		initialText = "";
		setup();
	}
	
	@Override
	public void setText(String text){
		initialText = Integer.toString(Integer.getInteger(text));
		super.setText(text);
	}

	public NumberJTextField(int text) {
		super(Integer.toString(text));
		
		setup(text);
	}

	public NumberJTextField(int text, int columns) {
		super(Integer.toString(text), columns);
		setup(text);
	}
	
	IErrorView getIErrorView(){
		return warningField;
	}

	/**
	 * Does the initial setup for the NumberJTextField
	 * @param text the initial text
	 */
	private void setup(int text) {
		initialText = Integer.toString(text);
		setup();
	}
	/**
	 * Sets up the Document for this class
	 */
	private void setup(){
		((AbstractDocument) this.getDocument())
		.setDocumentFilter(new MyDocumentFilter());
	}
	
	public void setIErrorView(IErrorView warningField){
		this.warningField = warningField;
	}

	@Override
	public boolean validateField(IErrorView warningField, boolean showLabel,
			boolean showBox) {
		boolean isValid = false;
		if(!hasChanges()){ //If this has changed
			isValid = true;
		} else if(getText().equals("")){
			isValid = false;
		} //Should not need to handle checking to see if there not numbers because this should have already been caught
		
		if(isValid){
			if(showLabel){
				warningField.setText("");
			}
			if(showBox){
				setBorder(defaultBorder);
			}
			return true;
		} else {
			if(showLabel){
				warningField.setText("The field can not be empty");
			}
			if(showBox){
				setBorder(errorBorder);
			}
			return false;
		}
	}

	@Override
	public boolean hasChanges() {
		return initialText.equals(getText());
	}
	
	public static void main(String... args){
		JFrame frame = new JFrame("Input Integer Example");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(
            BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        NumberJTextField text = new NumberJTextField(0, 20);
        
        contentPane.add(text);
        
        frame.setContentPane(contentPane);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
	}
}

/**
 * @author jonathanleitschuh
 * This class is a direct copy from:
 * http://stackoverflow.com/questions/9477354/how-to-allow-introducing-only-digits-in-jtextfield
 * 
 * This document filter prevents anything besides integers entered into the field
 * 
 */
class MyDocumentFilter extends DocumentFilter {
	private NumberJTextField parent;
	
	public MyDocumentFilter(NumberJTextField parent){
		this.parent = parent;
	}
	
	public MyDocumentFilter(){
		parent = null;
	}
	
	@Override
	public void insertString(DocumentFilter.FilterBypass fp, int offset,
			String string, AttributeSet aset) throws BadLocationException {
		int len = string.length();
		boolean isValidInteger = true;

		for (int i = 0; i < len; i++) {
			if (!Character.isDigit(string.charAt(i))) {
				isValidInteger = false;
				break;
			}
		}
		if (isValidInteger) {
			super.insertString(fp, offset, string, aset);
			numberValid();
		} else {
			Toolkit.getDefaultToolkit().beep();
			numberInvalid();
		}
	}

	@Override
	public void replace(DocumentFilter.FilterBypass fp, int offset, int length,
			String string, AttributeSet aset) throws BadLocationException {
		int len = string.length();
		boolean isValidInteger = true;

		for (int i = 0; i < len; i++) {
			if (!Character.isDigit(string.charAt(i))) {
				isValidInteger = false;
				break;
			}
		}
		if (isValidInteger) {
			super.replace(fp, offset, length, string, aset);
			numberValid();
		} else {
			Toolkit.getDefaultToolkit().beep();
			numberInvalid();
		}
	}
	
	private void numberInvalid(){
		if(parent.getIErrorView() != null){
			parent.getIErrorView().setText("You can only enter numbers here");
		}
	}
	
	private void numberValid(){
		if(parent.getIErrorView() != null){
			parent.getIErrorView().setText("");
		}
	}
}