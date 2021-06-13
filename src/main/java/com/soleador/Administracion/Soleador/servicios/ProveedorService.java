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
import com.soleador.Administracion.Soleador.entidades.Proveedor;
import com.soleador.Administracion.Soleador.repositorios.ProveedorRepository;

@Service
public class ProveedorService {

	@Autowired
	private ProveedorRepository proveedorRepository;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { WebException.class, Exception.class })
	public Proveedor guardar(Proveedor proveedor) throws WebException {
		validar(proveedor);
		proveedor.setModificacion(new Date());
		return proveedorRepository.save(proveedor);
	}

	private void validar(Proveedor proveedor) throws WebException {

		if (proveedor.getBaja() != null) {
			throw new WebException("El proveedor que intenta modificar se encuentra dada de baja.");
		}

		if (proveedor.getNombre() == null || proveedor.getNombre().isEmpty()) {
			throw new WebException("El nombre del proveedor no puede ser vacío.");
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { WebException.class, Exception.class })
	public Proveedor eliminar(String id) throws WebException {
		Proveedor proveedor = proveedorRepository.getOne(id);
		if (proveedor.getBaja() == null) {
			proveedor.setBaja(new Date());
			proveedor = proveedorRepository.save(proveedor);
		} else {
			throw new WebException("El proveedor que intenta eliminar ya se encuentra dado de baja.");
		}
		return proveedor;
	}

	public Page<Proveedor> listarActivos(Pageable paginable, String q) {
		if ((q != null && !q.isEmpty())) {
			return proveedorRepository.buscarActivos(paginable, "%" + q + "%");
		}
		return proveedorRepository.buscarActivos(paginable);
	}

	public List<Proveedor> listarActivos() {
		return proveedorRepository.buscarActivos();
	}

	public Proveedor buscar(String id) {
		Proveedor proveedor = proveedorRepository.getOne(id);
		return proveedor;
	}

}