package com.soleador.Administracion.Soleador.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.soleador.Administracion.Soleador.enumeraciones.TipoFlete;

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
	
	@OneToMany
	private List<ItemPedido> itemsPedido;
	
	private Double total;
	private Double totalCobrado;
	private Boolean estaCobrado;	
	private Boolean estaEntregado;
	private Transporte transporte;
	private Double reembolso;
	private TipoFlete tipoFlete;
	
	@ManyToOne
	private Pedido pedido;
	
	private Double importe;
	
	private String observaciones;


	@Temporal(TemporalType.TIMESTAMP)
	private Date alta;

	@Temporal(TemporalType.TIMESTAMP)
	private Date baja;

	@Temporal(TemporalType.TIMESTAMP)
	private Date modificacion;
}
