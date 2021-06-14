package com.soleador.Administracion.Soleador.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.soleador.Administracion.Soleador.enumeraciones.ContraReembolso;
import com.soleador.Administracion.Soleador.enumeraciones.EstadoPedido;
import com.soleador.Administracion.Soleador.enumeraciones.LugarPagoFlete;

import lombok.Data;

@Data
@Entity
public class Pedido implements Serializable {

	private static final long serialVersionUID = -8783981358461384278L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	private Long numeroPedido;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaPedido;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaRetiroDeposito;

	@ManyToOne
	private Cliente cliente;

	@ManyToOne
	private Transporte transporte;

	@Enumerated(EnumType.STRING)
	private ContraReembolso contraReembolso;

	@Enumerated(EnumType.STRING)
	private LugarPagoFlete lugarPagoFlete;

	@Enumerated(EnumType.STRING)
	private EstadoPedido estadoPedido = EstadoPedido.PENDIENTE;

	private String ordenDeCompra;

	private String factura;

	private Double importePedido;

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
