package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.NumberTextField.NumberFieldCustomError;

/**
 * @author jonathanleitschuh
 * 
 * The NumberJTextField is designed to prevent the user from entering anything besides
 * numbers
 *
 */
public class NumberJTextField extends JTextField implements IDataField {
	
	/** The default border when there aren't errors in this field */
	public static final Border BORDER_DEFAULT = (new JTextField()).getBorder();
	
	/** The default border when there are errors in this field */
	public static final Border BORDER_ERROR = BorderFactory.createLineBorder(Color.RED);
	
	private String initialText;
	private IErrorView warningField;
	private int maxValue = -1;
	private NumberFieldCustomError errorFields;

	
	/**
	 * The Default constructor for the NumberJTextField
	 */
	public NumberJTextField() {
		initialText = "";
		setup();
	}

	/**
	 * @param text the initial value of this 
	 */
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
		.setDocumentFilter(new MyDocumentFilter(this));
		errorFields = new NumberFieldCustomError();
	}
	
	@Override
	public void setText(String text){
		text = text.replaceAll("\n", "");
		initialText = text;
		super.setText(text);
	}
	
	public void setMaxValue(int maxValue){
		this.maxValue = maxValue;
	}
	
	public void setCustomErrorFields(NumberFieldCustomError errorFields){
		this.errorFields = errorFields;
	}
	
	public void setIErrorView(IErrorView warningField){
		this.warningField = warningField;
	}

	@Override
	public boolean validateField(IErrorView warningField, boolean showLabel,
			boolean showBox) {
		this.warningField = warningField;
		boolean isValid = false;
		if(getText().equals("")){
			isValid = false;
			showInvalid(errorFields.STRING_NOT_EMPTY, showLabel, showBox);
		} else if(!hasChanges()){ //If this has changed
			isValid = true;
			showValid(showLabel, showBox);
		} else if(maxValue != -1){
			if(Integer.parseInt(getText()) > maxValue ){
				isValid = false;
				showInvalid(errorFields.STRING_TOO_LONG + maxValue, showLabel, showBox);
			} else {
				isValid = true;
				showValid(showLabel, showBox);
			}
		} else{
			isValid = true;
			showValid(showLabel, showBox);
		}	//Should not need to handle checking to see if there not numbers because this should have already been caught
		
		return isValid;
	}
	
	private void showValid(boolean showLabel, boolean showBox){
		if(showLabel){
			//NOTHING SHOULD HAPPEN HERE BECAUSE WE DONT WANT TO OVERWRITE A DIFFERENT ERROR
		}
		if(showBox){
			setBorder(BORDER_DEFAULT);
		}
	}
	
	private void showInvalid(String text, boolean showLabel, boolean showBox){
		if(showLabel){
			warningField.setText(text);
		}
		if(showBox){
			setBorder(BORDER_ERROR);
		}
	}
	
	/**
	 * adds a key listener that will update buttons based on this data field
	 */
	public void addKeyListener(final IValidateButtons parent){
		this.addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent arg0) {	
				parent.updateButtons();
			}
		});
	}
	
	@Override
	public boolean hasChanges() {
		return !initialText.equals(getText());
	}
	
	public void setTextNoUpdate(String text){
		text = text.replaceAll("\n", "");
		super.setText(text);
		
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

	public NumberFieldCustomError getErrorFields() {
		return errorFields;
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
	
	@Override
	public void insertString(DocumentFilter.FilterBypass fp, int offset,
			String string, AttributeSet aset) throws BadLocationException {
		string = string.replaceAll("\n", ""); //Parse out newline character
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
		string = string.replaceAll("\n", ""); //Parse out newline character
		int len = string.length();
		boolean isValidInteger = true;

		for (int i = 0; i < len; i++) {
			if (!Character.isDigit(string.charAt(i))) {
				isValidInteger = false;
				break;
			}
			//System.out.println("Char was: " + string.charAt(i));
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
			parent.getIErrorView().setText(parent.getErrorFields().STRING_NOT_NUMBER);
		}
	}
	
	private void numberValid(){
		if(parent.getIErrorView() != null){
			parent.getIErrorView().setText("");
		}
	}
}