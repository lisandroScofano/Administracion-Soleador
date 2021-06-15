package com.soleador.Administracion.Soleador.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.soleador.Administracion.Soleador.entidades.Cliente;
import com.soleador.Administracion.Soleador.entidades.ItemPedido;
import com.soleador.Administracion.Soleador.entidades.Transporte;
import com.soleador.Administracion.Soleador.enumeraciones.ContraReembolso;
import com.soleador.Administracion.Soleador.enumeraciones.EstadoPedido;
import com.soleador.Administracion.Soleador.enumeraciones.LugarPagoFlete;

import lombok.Data;

@Data
public class PedidoModel {

	private String id;

	private Long numeroPedido;

	@Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")	
	private Date fechaPedido;

	@Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")	
	private Date fechaRetiroDeposito;

	private Cliente cliente;

	private Transporte transporte;

	private ContraReembolso contraReembolso;

	private LugarPagoFlete lugarPagoFlete;

	private EstadoPedido estadoPedido = EstadoPedido.PENDIENTE;

	private String ordenDeCompra;

	private String factura;

	private Double importePedido;

	private String observaciones;
	
	private List<ItemPedido> itemsPedido;

}
