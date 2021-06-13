package com.soleador.Administracion.Soleador.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.soleador.Administracion.Soleador.enumeraciones.EstadoPedido;
import com.soleador.Administracion.Soleador.enumeraciones.LugarPagoFlete;

import lombok.Data;

@Data
@Entity
public class Pedido implements Serializable{

	private static final long serialVersionUID = -8783981358461384278L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;
	
	@ManyToOne
	private Cliente cliente;
	
	@ManyToOne
	private Transporte transporte;
	
	private boolean contraReembolso;
	
	@Enumerated(EnumType.STRING)
	private LugarPagoFlete tipoFlete;
	
	@Enumerated(EnumType.STRING)
	private EstadoPedido estadoPedido = EstadoPedido.PENDIENTE;
	
	private String factura;
	
	@ManyToOne
	private Pedido pedido;
	
	private Double importePedido;
	
	private String observaciones;


	@Temporal(TemporalType.TIMESTAMP)
	private Date alta;

	@Temporal(TemporalType.TIMESTAMP)
	private Date baja;

	@Temporal(TemporalType.TIMESTAMP)
	private Date modificacion;
}
