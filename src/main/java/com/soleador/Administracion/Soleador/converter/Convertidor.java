package com.soleador.Administracion.Soleador.converter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



public abstract class Convertidor<M extends Object, E extends Object> {
	public abstract E modeloToEntidad(M m) throws Exception;

	public abstract M entidadToModelo(E e);

	protected Log log;

	public Convertidor() {
		this.log = LogFactory.getLog(getClass());
	}

}
