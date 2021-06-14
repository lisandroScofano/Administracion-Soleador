package com.soleador.Administracion.Soleador.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
public class ProductoVenta implements Serializable {

	private static final long serialVersionUID = -8783981358461384278L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	private String nombre;
	private String observaciones;
	private Double presentacion;
	private Double precio;

	@Temporal(TemporalType.TIMESTAMP)
	private Date alta;

	@Temporal(TemporalType.TIMESTAMP)
	private Date baja;

	@Temporal(TemporalType.TIMESTAMP)
	private Date modificacion;

	@PrePersist
	public void agregarFechaAlta() {
		alta = new Date();
		modificacion = new Date();
	}

	@PreUpdate
	public void agregarFechaActualizacion() {
		modificacion = new Date();
	}
}
