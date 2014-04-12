/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jdesktop.swingx.JXDatePicker;

/**
 *
 */
public class AddEndDatePanel extends JPanel {
	
	Calendar dateMaker;
	Date endDate;
	
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
		dateMaker = new GregorianCalendar();
	}
	
	public Date getEndDate(){
		endDate = datePicker.getDate();
		dateMaker.setTime(endDate);
		dateMaker.set(Calendar.HOUR, getHours());
		dateMaker.set(Calendar.MINUTE, getMinutes());
		
		endDate = dateMaker.getTime();
		
		System.out.println(endDate.toString());
		
		return endDate;
	}
	
	private int getMinutes() {
		int index = minuteSelection.getSelectedIndex();
		int minutes;
		
		switch(index) {
		case 0: minutes = 0;
				break;
		case 1: minutes = 15;
				break;
		case 2: minutes = 30;
				break;
		case 3: minutes = 45;
				break;
		default: minutes = 0;
				 break;
		}
		System.out.println(index);

		return minutes;
	}
	
	private int getHours() {
		int hours;
		int isAMPM;
		
		hours = hourSelection.getSelectedIndex()+1;
		isAMPM = AmPmSelection.getSelectedIndex();
		
		switch(isAMPM){
		case 0: dateMaker.set(Calendar.AM_PM, Calendar.AM);
				break;
		case 1: dateMaker.set(Calendar.AM_PM, Calendar.PM);
				break;
		default: break;
		}
		
		System.out.println("AM_PM string: " + Calendar.AM_PM);
		return hours;
	}
	
	private void setDate(Date oldDate){
		datePicker.setDate(oldDate);
	}
	
	private void setHour(String oldHour){
		hourSelection.setSelectedItem(oldHour);
	}
	
	private void setMinute(String oldMinute){
		minuteSelection.setSelectedItem(oldMinute);
	}
	
	private void setAMPM(String oldAMPM){
		AmPmSelection.setSelectedItem(oldAMPM);
	}
	
	public void setDateAndTime(Date oldDate, String oldHour, String oldMinute, String oldAMPM){
		setDate(oldDate);
		setHour(oldHour);
		setMinute(oldMinute);
		setAMPM(oldAMPM);		
	}	
}
