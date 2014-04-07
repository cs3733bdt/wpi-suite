/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.util.Date;

import org.jdesktop.swingx.JXDatePicker;

/**
 *
 */
public class AddEndDatePanel extends JPanel {
	
	private final JXDatePicker datePicker = new JXDatePicker(new Date());
	private final String[] hourArray = 
		{"1","2","3","4","5","6","7","8","9","10","11","12"} ;
	private final String[] minuteArray = {"00","15","30","45"};
	private final String[] AmPmArray = {"AM","PM"};
	private final JComboBox hourSelection = new JComboBox(hourArray);
	private final JComboBox minuteSelection = new JComboBox(minuteArray);
	private final JComboBox AmPmSelection = new JComboBox(AmPmArray);
	private final JLabel endDateSelection = new JLabel("Select End Date:");
	
	public AddEndDatePanel(final CreateGamePanel view){
		this.add(endDateSelection);
		this.add(datePicker);
		this.add(hourSelection);
		this.add(minuteSelection);
		this.add(AmPmSelection);
	}
	
	public Date getEndDate(){
		return datePicker.getDate();
	}
}
