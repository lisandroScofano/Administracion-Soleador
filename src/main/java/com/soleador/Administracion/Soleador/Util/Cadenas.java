package com.soleador.Administracion.Soleador.Util;

public final class Cadenas {
	private Cadenas() {

	}

	public static String codificar(long numero, int longitud) {
		StringBuilder codigo = new StringBuilder();
		codigo.append(numero);
		if (codigo.length() < longitud) {
			for (int caracteres = codigo.length(); caracteres < longitud; caracteres++) {
				codigo.insert(0, "0");
			}
		}

		return codigo.toString();
	}
}
