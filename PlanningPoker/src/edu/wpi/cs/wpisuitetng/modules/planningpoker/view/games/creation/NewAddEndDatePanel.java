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

import java.awt.Color;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import org.jdesktop.swingx.JXDatePicker;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.IDataField;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.components.IErrorView;

/**
 * This is where the graphical elements and calculation are done for allowing
 * users to select an end date for their Planning Poker games
 * 
 * @author BDT
 */
public class NewAddEndDatePanel extends JPanel implements IDataField {

	Calendar dateMaker;
	Date endDate;

	private final JXDatePicker datePicker = new JXDatePicker(new Date());
	private final String[] hourArray = { "1", "2", "3", "4", "5", "6", "7",
			"8", "9", "10", "11", "12" };
	private final String[] minuteArray = { "00", "15", "30", "45" };
	private final String[] AmPmArray = { "AM", "PM" };
	private final JComboBox<String> hourSelection = new JComboBox<>(hourArray);
	private final JComboBox<String> minuteSelection = new JComboBox<>(
			minuteArray);
	private final JComboBox<String> AmPmSelection = new JComboBox<>(AmPmArray);
	private final JLabel endDateSelection = new JLabel("End Date * ");

	private final Border errorBorder = BorderFactory
			.createLineBorder(Color.RED);
	private final Border defaultBorder = (new JXDatePicker()).getBorder();

	/**
	 * Only constructor for AddEndDatePanel Creates graphical elements required
	 * for creation of an end date
	 * 
	 * @param view
	 */
	public NewAddEndDatePanel(final NewLeftHalfCreateGamePanel view) {
		add(endDateSelection);
		add(datePicker);
		add(hourSelection);
		add(minuteSelection);
		add(AmPmSelection);
		dateMaker = new GregorianCalendar();
		
		//Setting fields of date selection GUI elements to about 24 hours from creation
		dateMaker.set(Calendar.DAY_OF_YEAR, dateMaker.get(Calendar.DAY_OF_YEAR)+1);
		datePicker.setDate(dateMaker.getTime());
		int currentHour = dateMaker.get(Calendar.HOUR);
		if (currentHour == 0) {
			hourSelection.setSelectedIndex(11);
		} else {
			hourSelection.setSelectedIndex(currentHour-1);
		}
		int currentMinute = dateMaker.get(Calendar.MINUTE);
		minuteSelection.setSelectedIndex(currentMinute/15);
		if(dateMaker.get(Calendar.AM_PM) == Calendar.AM) {
			AmPmSelection.setSelectedIndex(0);
		} else {
			AmPmSelection.setSelectedIndex(1);
		}
	}

	/**
	 * @return Date the end date selected in the DatePicker and drop down menus
	 */
	public Date getEndDate() {
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

		switch (index) {
		case 0:
			minutes = 0;
			break;
		case 1:
			minutes = 15;
			break;
		case 2:
			minutes = 30;
			break;
		case 3:
			minutes = 45;
			break;
		default:
			minutes = 0;
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

		switch (isAMPM) {
		case 0:
			dateMaker.set(Calendar.AM_PM, Calendar.AM);
			break;
		case 1:
			dateMaker.set(Calendar.AM_PM, Calendar.PM);
			break;
		default:
			break;
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

	public JXDatePicker getDatePicker() {
		return datePicker;
	}

	public JComboBox<String> getHourSelection() {
		return hourSelection;
	}

	public JComboBox<String> getMinuteSelection() {
		return minuteSelection;
	}

	public JComboBox<String> getAmPmSelection() {
		return AmPmSelection;
	}

	private void setDate(Date oldDate) {
		datePicker.setDate(oldDate);
	}

	private void setHour(String oldHour) {
		if (oldHour.equals("0")) {
			hourSelection.setSelectedIndex(11);
		} else {
			hourSelection.setSelectedItem(oldHour);
		}
	}

	private void setMinute(String oldMinute) {
		minuteSelection.setSelectedItem(oldMinute);
	}

	private void setAMPM(String oldAMPM) {
		AmPmSelection.setSelectedItem(oldAMPM);
	}

	/**
	 * Sets the defaults for the graphical elements displayed for the end date
	 * 
	 * @param oldDate
	 *            Date to be displayed for the DatePicker
	 * @param oldHour
	 *            String to set the hour drop down menu
	 * @param oldMinute
	 *            String to set the minute drop down menu
	 * @param oldAMPM
	 *            String to set the AMPM drop down menu
	 */
	public void setDateAndTime(Date oldDate, String oldHour, String oldMinute,
			String oldAMPM) {
		setDate(oldDate);
		setHour(oldHour);
		setMinute(oldMinute);
		setAMPM(oldAMPM);
	}

	@Override
	public boolean validateField(IErrorView warningField, boolean showLabel,
			boolean showBox) {
		// TODO Auto-generated method stub
		boolean isEndDateValid = false;
		endDate = getEndDate();
		Calendar endCalendar = new GregorianCalendar();
		Calendar currentCalendar = new GregorianCalendar();
		endCalendar.setTime(endDate);
		currentCalendar.setTime(new Date());

		if (endDate.compareTo(new Date()) >= 0) {
			isEndDateValid = true;
			this.setBorder(defaultBorder);
		} else {
			isEndDateValid = false;
			if (showLabel) {
				warningField.setText("End Time is too early to start a game");
			}
			if (showBox) {
				this.setBorder(errorBorder);
			}
		}

		return isEndDateValid;
	}

	@Override
	public boolean hasChanges() {
		// TODO Auto-generated method stub
		return false;
	}

	public String toString() {
		try {
			return endDate.toString() + Integer.toString(getHours())
					+ Integer.toString(getMinutes())
					+ Integer.toString(AmPmSelection.getSelectedIndex());
		} catch (NullPointerException e) {
			return "";
		}
	}
}
