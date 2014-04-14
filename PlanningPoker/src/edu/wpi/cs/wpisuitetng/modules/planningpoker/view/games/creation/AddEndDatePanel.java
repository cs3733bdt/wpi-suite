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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.games.creation;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jdesktop.swingx.JXDatePicker;

/**
 * This is where the graphical elements and calculation are done for 
 * allowing users to select an end date for their Planning Poker games
 * @author BDT
 */
public class AddEndDatePanel extends JPanel {

	Calendar dateMaker;
	Date endDate;
	
	private final JXDatePicker datePicker = new JXDatePicker(new Date());
	private final String[] hourArray = 
		{"1","2","3","4","5","6","7","8","9","10","11","12"} ;
	private final String[] minuteArray = {"00","15","30","45"};
	private final String[] AmPmArray = {"AM","PM"};
	private final JComboBox<String> hourSelection = new JComboBox<>(hourArray);
	private final JComboBox<String> minuteSelection = new JComboBox<>(minuteArray);
	private final JComboBox<String> AmPmSelection = new JComboBox<>(AmPmArray);
	private final JLabel endDateSelection = new JLabel("Select End Date:");
	
	/**
	 * Only constructor for AddEndDatePanel
	 * Creates graphical elements required for creation of an end date
	 * @param view
	 */
	public AddEndDatePanel(final CreateGamePanel view){
		this.add(endDateSelection);
		this.add(datePicker);
		this.add(hourSelection);
		this.add(minuteSelection);
		this.add(AmPmSelection);
		dateMaker = new GregorianCalendar();
	}
	
	/**
	 * @return Date the end date selected in the DatePicker and drop down menus
	 */
	public Date getEndDate(){
		endDate = datePicker.getDate();
		dateMaker.setTime(endDate);
		dateMaker.set(Calendar.HOUR, getHours());
		dateMaker.set(Calendar.MINUTE, getMinutes());		
		endDate = dateMaker.getTime();
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
		return minutes;
	}
	
	private int getHours() {
		int index;
		int hours;
		int isAMPM;
		
		index = hourSelection.getSelectedIndex();
		isAMPM = AmPmSelection.getSelectedIndex();
		
		switch(isAMPM){
		case 0: dateMaker.set(Calendar.AM_PM, Calendar.AM);
				break;
		case 1: dateMaker.set(Calendar.AM_PM, Calendar.PM);
				break;
		default: break;
		}
		
		switch (index) {
		case 0:
			hours = 1;
			break;
		case 1:
			hours = 2;
			break;
		case 2:
			hours = 3;
			break;
		case 3:
			hours = 4;
			break;
		case 4:
			hours = 5;
			break;
		case 5:
			hours = 6;
			break;
		case 6:
			hours = 7;
			break;
		case 7:
			hours = 8;
			break;
		case 8:
			hours = 9;
			break;
		case 9:
			hours = 10;
			break;
		case 10:
			hours = 11;
			break;
		case 11:
			hours = 0;
			break;
		default:
			hours = 1;
			break;
		}
		
		return hours;
	}
	
	private void setDate(Date oldDate){
		datePicker.setDate(oldDate);
	}
	
	private void setHour(String oldHour){
		if(oldHour.equals("0")){
			hourSelection.setSelectedIndex(11);
		}
		else{
			hourSelection.setSelectedItem(oldHour);
		}
	}
	
	private void setMinute(String oldMinute){
		minuteSelection.setSelectedItem(oldMinute);
	}
	
	private void setAMPM(String oldAMPM){
		AmPmSelection.setSelectedItem(oldAMPM);
	}
	
	/**
	 * Sets the defaults for the graphical elements displayed for the end date
	 * @param oldDate Date to be displayed for the DatePicker
	 * @param oldHour String to set the hour drop down menu
	 * @param oldMinute String to set the minute drop down menu
	 * @param oldAMPM String to set the AMPM drop down menu
	 */
	public void setDateAndTime(Date oldDate, String oldHour, String oldMinute, String oldAMPM){
		setDate(oldDate);
		setHour(oldHour);
		setMinute(oldMinute);
		setAMPM(oldAMPM);		
	}	
}
