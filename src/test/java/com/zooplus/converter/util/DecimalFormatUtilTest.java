package com.zooplus.converter.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DecimalFormatUtilTest {

	@Test
	void testTrimDecimals() {
		String price = "127.777989";
		String trimmedAmount = DecimalFormatUtil.trimDecimals(price);
		assertEquals("127.7779", trimmedAmount);
	}

}
