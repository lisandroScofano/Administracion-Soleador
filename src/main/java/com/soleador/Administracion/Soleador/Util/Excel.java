package com.soleador.Administracion.Soleador.Util;


import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel {
	
	private Excel() {
		
	}

	public static CellStyle crearEstiloCabecera(Workbook wb) {
		return crearEstiloCabecera(wb, crearEstiloBorde(wb));
	}

	public static XSSFCell crearTitulo(XSSFWorkbook excel, XSSFRow row, int columna, String valor, boolean cabecera) {
		XSSFCell celda = crearCelda(excel, row, columna, valor, cabecera);
		celda.setCellStyle(crearEstiloTitulo(excel));
		return celda;
	}
	
	public static XSSFCell crearCelda(XSSFWorkbook excel, XSSFRow row, int columna, String valor, boolean cabecera) {
		XSSFCell celda = row.createCell(columna);
		celda.setCellValue(valor);

		if (cabecera) {
			celda.setCellStyle(crearEstiloCabecera(excel));
		} else {
			celda.setCellStyle(crearEstiloBorde(excel));
		}
		
		return celda;

	}
	
	public static XSSFCell crearCeldaSinBorde(XSSFWorkbook excel, XSSFRow row, int columna, String valor, boolean cabecera) {
		XSSFCell celda = row.createCell(columna);
		celda.setCellValue(valor);

		if (cabecera) {
			celda.setCellStyle(crearEstiloCabecera(excel));
		}
		return celda;

	}
	
	public static XSSFCell crearCeldaCentro(XSSFWorkbook excel, XSSFRow row, int columna, String valor, boolean cabecera) {
		XSSFCell celda = row.createCell(columna);
		CellStyle cellStyle = excel.createCellStyle();
		
		cellStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		
		celda.setCellStyle(cellStyle);
		celda.setCellValue(valor);
		cellStyle.setWrapText(true);
		
		return celda;

	}

	public static CellStyle crearEstiloTitulo(Workbook wb) {
		
		CellStyle style = crearEstiloBorde(wb);
		
		Font headerFont = wb.createFont();
		headerFont.setFontName("Cambria");
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 14);
		style.setVerticalAlignment(VerticalAlignment.CENTER);

		style.setAlignment(HorizontalAlignment.CENTER);
		style.setFont(headerFont);

		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		style.setFillBackgroundColor(IndexedColors.BLACK.getIndex());

		return style;
	}
	
	
	public static CellStyle crearEstiloCabecera(Workbook wb, CellStyle style) {
		Font headerFont = wb.createFont();
		headerFont.setFontName("Cambria");
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 11);
		style.setVerticalAlignment(VerticalAlignment.CENTER);

		style.setAlignment(HorizontalAlignment.CENTER);
		style.setFont(headerFont);

		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		style.setFillBackgroundColor(IndexedColors.BLACK.getIndex());

		return style;
	}

	public static CellStyle crearEstiloComun(Workbook wb) {
		CellStyle estilo = crearEstiloBorde(wb);

		Font fuente = wb.createFont();
		fuente.setFontName("Cambria");
		fuente.setFontHeightInPoints((short) 10);

		estilo.setFont(fuente);
		estilo.setVerticalAlignment(VerticalAlignment.CENTER);
		estilo.setAlignment(HorizontalAlignment.CENTER);
		return estilo;
	}

	public static CellStyle crearEstiloBorde(Workbook wb) {
		CellStyle style = wb.createCellStyle();
		style.setBorderRight(BorderStyle.THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderBottom(BorderStyle.THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderLeft(BorderStyle.THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderTop(BorderStyle.THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		style.setWrapText(true);
		style.setAlignment(HorizontalAlignment.CENTER);
		return style;
	}
}
	
	
	

