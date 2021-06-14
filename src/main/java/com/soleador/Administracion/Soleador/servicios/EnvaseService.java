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
import com.soleador.Administracion.Soleador.entidades.Envase;
import com.soleador.Administracion.Soleador.repositorios.EnvaseRepository;

@Service
public class EnvaseService {

	@Autowired
	private EnvaseRepository envaseRepository;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { WebException.class, Exception.class })
	public Envase guardar(Envase envase) throws WebException {
		validar(envase);
		envase.setModificacion(new Date());
		return envaseRepository.save(envase);
	}

	private void validar(Envase envase) throws WebException {

		if (envase.getBaja() != null) {
			throw new WebException("El envase que intenta modificar se encuentra dada de baja.");
		}

		if (envase.getNombre() == null || envase.getNombre().isEmpty()) {
			throw new WebException("El nombre del envase no puede ser vacío.");
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { WebException.class, Exception.class })
	public Envase eliminar(String id) throws WebException {
		Envase envase = envaseRepository.getOne(id);
		if (envase.getBaja() == null) {
			envase.setBaja(new Date());
			envase = envaseRepository.save(envase);
		} else {
			throw new WebException("El envase que intenta eliminar ya se encuentra dado de baja.");
		}
		return envase;
	}

	public Page<Envase> listarActivos(Pageable paginable, String q) {
		if ((q != null && !q.isEmpty())) {
			return envaseRepository.buscarActivos(paginable, "%" + q + "%");
		}
		return envaseRepository.buscarActivos(paginable);
	}

	public List<Envase> listarActivos() {
		return envaseRepository.buscarActivos();
	}

	public Envase buscar(String id) {
		Envase envase = envaseRepository.getOne(id);
		return envase;
	}

}