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
import com.soleador.Administracion.Soleador.entidades.Vendedor;
import com.soleador.Administracion.Soleador.repositorios.VendedorRepository;

@Service
public class VendedorService {

	@Autowired
	private VendedorRepository vendedorRepository;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { WebException.class, Exception.class })
	public Vendedor guardar(Vendedor vendedor) throws WebException {
		validar(vendedor);
		vendedor.setModificacion(new Date());
		return vendedorRepository.save(vendedor);
	}

	private void validar(Vendedor vendedor) throws WebException {

		if (vendedor.getBaja() != null) {
			throw new WebException("El vendedor que intenta modificar se encuentra dada de baja.");
		}

		if (vendedor.getNombre() == null || vendedor.getNombre().isEmpty()) {
			throw new WebException("El nombre del vendedor no puede ser vacío.");
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { WebException.class, Exception.class })
	public Vendedor eliminar(String id) throws WebException {
		Vendedor vendedor = vendedorRepository.getOne(id);
		if (vendedor.getBaja() == null) {
			vendedor.setBaja(new Date());
			vendedor = vendedorRepository.save(vendedor);
		} else {
			throw new WebException("El vendedor que intenta eliminar ya se encuentra dado de baja.");
		}
		return vendedor;
	}

	public Page<Vendedor> listarActivos(Pageable paginable, String q) {
		if ((q != null && !q.isEmpty())) {
			return vendedorRepository.buscarActivos(paginable, "%" + q + "%");
		}
		return vendedorRepository.buscarActivos(paginable);
	}

	public List<Vendedor> listarActivos() {
		return vendedorRepository.buscarActivos();
	}

	public Vendedor buscar(String id) {
		Vendedor vendedor = vendedorRepository.getOne(id);
		return vendedor;
	}

}