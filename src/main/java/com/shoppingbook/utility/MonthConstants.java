package com.shoppingbook.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonthConstants {
	
	private final static Map<String, String> mapOfMonths = new HashMap<String, String>(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put("01", "January");
			put("02", "February");
			put("03", "March");
			put("04", "April");
			put("05", "May");
			put("06", "June");
			put("07", "July");
			put("08", "August");
			put("09", "September");
			put("10", "October");
			put("11", "November");
			put("12", "December");
		}
	};
	public final static List<String> listOfMonthCodes = new ArrayList<String>(mapOfMonths.keySet());
	public final static List<String> listOfMonthNames = new ArrayList<String>(mapOfMonths.values());
}
