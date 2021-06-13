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
import com.soleador.Administracion.Soleador.entidades.Cliente;
import com.soleador.Administracion.Soleador.repositorios.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { WebException.class, Exception.class })
	public Cliente guardar(Cliente cliente) throws WebException {
		validar(cliente);
		cliente.setModificacion(new Date());
		return clienteRepository.save(cliente);
	}

	private void validar(Cliente cliente) throws WebException {

		if (cliente.getBaja() != null) {
			throw new WebException("El cliente que intenta modificar se encuentra dada de baja.");
		}

		if (cliente.getNombre() == null || cliente.getNombre().isEmpty()) {
			throw new WebException("El nombre del cliente no puede ser vacío.");
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { WebException.class, Exception.class })
	public Cliente eliminar(String id) throws WebException {
		Cliente cliente = clienteRepository.getOne(id);
		if (cliente.getBaja() == null) {
			cliente.setBaja(new Date());
			cliente = clienteRepository.save(cliente);
		} else {
			throw new WebException("El cliente que intenta eliminar ya se encuentra dado de baja.");
		}
		return cliente;
	}

	public Page<Cliente> listarActivos(Pageable paginable, String q) {
		if ((q != null && !q.isEmpty())) {
			return clienteRepository.buscarActivos(paginable, "%" + q + "%");
		}
		return clienteRepository.buscarActivos(paginable);
	}

	public List<Cliente> listarActivos() {
		return clienteRepository.buscarActivos();
	}

	public Cliente buscar(String id) {
		Cliente cliente = clienteRepository.getOne(id);
		return cliente;
	}

}