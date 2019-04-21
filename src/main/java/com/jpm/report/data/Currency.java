package com.jpm.report.data;

import java.time.DayOfWeek;

/**
 * A currency like SGP, GBP is the enum which has week start day and weekends. 
 * The default week start day is MONDAY and weekends are Saturday and Sunday. 
 * For SAR and AED the default start date is Sunday and weekends are Friday and Saturday.
 * 
 * @author Radhika
 *
 */
public enum Currency {
	SAR(DayOfWeek.SUNDAY.getValue(), new int[]{DayOfWeek.FRIDAY.getValue(), 
			DayOfWeek.SATURDAY.getValue()}), 
	AED(DayOfWeek.SUNDAY.getValue(), new int[]{DayOfWeek.FRIDAY.getValue(), 
			DayOfWeek.SATURDAY.getValue()}), 
	SGP,
	GBP, 
	INR;

	private int weekStartDay = DayOfWeek.MONDAY.getValue();
	private int[] weekends_default = new int[]{DayOfWeek.SATURDAY.getValue(), 
			DayOfWeek.SUNDAY.getValue()};	
	private int[] weekends = weekends_default;

	
	Currency() {
	}

	Currency(int weekStartDay, int... weekends) {
		this.weekStartDay = weekStartDay;
		this.weekends = weekends;
	}

	public int getWeekStartDay() {
		return this.weekStartDay;
	}

	public int[] getWeekends() {
		return this.weekends;
	}
}
