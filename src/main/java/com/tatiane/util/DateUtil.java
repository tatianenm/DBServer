package com.tatiane.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateUtil {
	
	public static LocalDate converteParaLocalDate(Date data) {
		return data.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public static Date converteParaDate(LocalDate localDate) {
	 return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
}
