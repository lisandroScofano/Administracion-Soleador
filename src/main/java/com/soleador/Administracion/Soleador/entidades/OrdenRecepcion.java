package com.soleador.Administracion.Soleador.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
public class OrdenRecepcion implements Serializable {

	private static final long serialVersionUID = -8783981358461384278L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;

	@ManyToOne
	private Proveedor proveedor;

	@ManyToOne
	private ProductoCompra productoCompra;
	private Double kgsTotales;

	@ManyToOne
	private Envase envase;

	@ManyToOne
	private PagoProveedor pago;

	private Double precioKg;
	private Double total;
	private Double totalPagado;
	private Boolean estaPagado;
	private Double costoFlete;
	private String observaciones;

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
