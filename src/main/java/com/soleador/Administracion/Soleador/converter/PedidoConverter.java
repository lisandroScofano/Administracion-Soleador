package com.soleador.Administracion.Soleador.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.soleador.Administracion.Soleador.entidades.Pedido;
import com.soleador.Administracion.Soleador.models.PedidoModel;

@Component
public class PedidoConverter extends Convertidor<PedidoModel, Pedido> {

	@Override
	public Pedido modeloToEntidad(PedidoModel pedidoModel) throws Exception {
		Pedido pedido = new Pedido();
		try {
			BeanUtils.copyProperties(pedidoModel, pedido);
		} catch (Exception e) {
			log.error("Error al convertir el modelo de pedido en entidad", e);
		}
		return pedido;
	}

	@Override
	public PedidoModel entidadToModelo(Pedido pedido) {
		PedidoModel pedidoModel = new PedidoModel();
		try {
			BeanUtils.copyProperties(pedido, pedidoModel);

		} catch (Exception e) {
			log.error("Error al convertir la entidad en el modelo de pedido", e);
		}

		return pedidoModel;
	}

	public List<PedidoModel> entidadesToModelos(List<Pedido> pedidos) {
		List<PedidoModel> model = new ArrayList<>();
		for (Pedido pedido : pedidos) {
			model.add(entidadToModelo(pedido));
		}
		return model;
	}

	public List<Pedido> modelosToEntidades(List<PedidoModel> pedidosM) throws Exception {
		List<Pedido> pedidos = new ArrayList<>();
		for (PedidoModel pedidoM : pedidosM) {
			pedidos.add(modeloToEntidad(pedidoM));
		}
		return pedidos;
	}

}
