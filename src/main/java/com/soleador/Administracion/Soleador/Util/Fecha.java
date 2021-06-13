package com.soleador.Administracion.Soleador.Util;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.joda.time.DateTime;

public class Fecha {
	private final SimpleDateFormat formatoGuiones = new SimpleDateFormat("dd-MM-yyyy");

	private final SimpleDateFormat formatoStandar = new SimpleDateFormat("dd/MM/yyyy", Locale.forLanguageTag("es"));
	private final SimpleDateFormat formatoHora = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.forLanguageTag("es"));
	private final SimpleDateFormat formatoCalendario = new SimpleDateFormat("yyyy-MM-dd", Locale.forLanguageTag("es"));

	private final NumberFormat formatoIngles;
	private final NumberFormat formatoEspanol;

	public Fecha() {
		formatoIngles = NumberFormat.getInstance(Locale.US);
		formatoIngles.setMinimumFractionDigits(2);
		formatoIngles.setMaximumFractionDigits(2);
		formatoIngles.setGroupingUsed(false);

		formatoEspanol = NumberFormat.getInstance(Locale.US);
		formatoEspanol.setMinimumFractionDigits(2);
		formatoEspanol.setMaximumFractionDigits(2);
		formatoEspanol.setGroupingUsed(true);
	}

	public Date parseFechaGuiones(String f) {
		try {
			return formatoCalendario.parse(f);
		} catch (ParseException e) {
			return null;
		}
	}

	public String formatFechaGuiones(Date f) {
		try {
			return formatoGuiones.format(f);
		} catch (Exception e) {
			return null;
		}
	}

	public Date parseFecha(String f) {
		try {
			return formatoStandar.parse(f);
		} catch (ParseException e) {
			return null;
		}
	}

	public Date parseHora(String f) {
		try {
			return formatoHora.parse(f);
		} catch (ParseException e) {
			return null;
		}
	}

	public String formatFecha(Date f) {
		try {
			return formatoStandar.format(f);
		} catch (Exception e) {
			return null;
		}
	}

	public String formatHora(Date f) {
		try {
			return formatoHora.format(f);
		} catch (Exception e) {
			return null;
		}
	}

	public Date primerDiaAnio() {
		DateTime date = new DateTime().dayOfYear().withMinimumValue().withTimeAtStartOfDay();
		return date.toDate();
	}

	public Date ultimoDiaAnio() {
		DateTime date = new DateTime().plusYears(1).dayOfYear().withMinimumValue().withTimeAtStartOfDay();
		return date.toDate();
	}
}
