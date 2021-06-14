package com.soleador.Administracion.Soleador.servicios;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.soleador.Administracion.Soleador.Error.WebException;
import com.soleador.Administracion.Soleador.entidades.Pedido;
import com.soleador.Administracion.Soleador.repositorios.PedidoRepository;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { WebException.class, Exception.class })
	public Pedido guardar(Pedido pedido) throws WebException {
		validar(pedido);
		pedido.setModificacion(new Date());
		return pedidoRepository.save(pedido);
	}

	private void validar(Pedido pedido) throws WebException {

		if (pedido.getBaja() != null) {
			throw new WebException("El pedido que intenta modificar se encuentra dada de baja.");
		}

//		if (pedido.getCliente() == null) {
//			throw new WebException("El cliente del pedido no puede ser vacío.");
//		}

	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { WebException.class, Exception.class })
	public Pedido eliminar(String id) throws WebException {
		Pedido pedido = pedidoRepository.getOne(id);
		if (pedido.getBaja() == null) {
			pedido.setBaja(new Date());
			pedido = pedidoRepository.save(pedido);
		} else {
			throw new WebException("El pedido que intenta eliminar ya se encuentra dado de baja.");
		}
		return pedido;
	}

	public Page<Pedido> listarActivos(Pageable paginable, String q) {
		if ((q != null && !q.isEmpty())) {
			return pedidoRepository.buscarActivos(paginable, "%" + q + "%");
		}
		return pedidoRepository.buscarActivos(paginable);
	}

	public List<Pedido> listarActivos() {
		return pedidoRepository.buscarActivos();
	}

	public Pedido buscar(String id) {
		Pedido pedido = pedidoRepository.getOne(id);
		return pedido;
	}

	public Long obtenerNumeroPedido() {
		Long cantidad = pedidoRepository.cantidadPedidos();
		return cantidad + 1;
	}

}