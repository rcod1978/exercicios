package com.exercicio.movie.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {
		
	public static int getYearDate(Date data) {
		LocalDate localDate = data.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return localDate.getYear();
	}
}
