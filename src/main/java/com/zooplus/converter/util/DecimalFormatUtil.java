package com.zooplus.converter.util;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class DecimalFormatUtil {
	
	public static String trimDecimals(String price) {
		Float priceFloat = Float.parseFloat(price);
		DecimalFormat df = new DecimalFormat("0.00");
		df.setRoundingMode(RoundingMode.DOWN);
		df.setMaximumFractionDigits(4);
		return df.format(priceFloat);
	}
}
