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
import com.soleador.Administracion.Soleador.entidades.Transporte;
import com.soleador.Administracion.Soleador.repositorios.TransporteRepository;

@Service
public class TransporteService {

	@Autowired
	private TransporteRepository transporteRepository;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { WebException.class, Exception.class })
	public Transporte guardar(Transporte transporte) throws WebException {
		validar(transporte);
		transporte.setModificacion(new Date());
		return transporteRepository.save(transporte);
	}

	private void validar(Transporte transporte) throws WebException {

		if (transporte.getBaja() != null) {
			throw new WebException("El transporte que intenta modificar se encuentra dada de baja.");
		}

		if (transporte.getNombre() == null || transporte.getNombre().isEmpty()) {
			throw new WebException("El nombre del transporte no puede ser vacío.");
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { WebException.class, Exception.class })
	public Transporte eliminar(String id) throws WebException {
		Transporte transporte = transporteRepository.getOne(id);
		if (transporte.getBaja() == null) {
			transporte.setBaja(new Date());
			transporte = transporteRepository.save(transporte);
		} else {
			throw new WebException("El transporte que intenta eliminar ya se encuentra dado de baja.");
		}
		return transporte;
	}

	public Page<Transporte> listarActivos(Pageable paginable, String q) {
		if ((q != null && !q.isEmpty())) {
			return transporteRepository.buscarActivos(paginable, "%" + q + "%");
		}
		return transporteRepository.buscarActivos(paginable);
	}

	public List<Transporte> listarActivos() {
		return transporteRepository.buscarActivos();
	}

	public Transporte buscar(String id) {
		Transporte transporte = transporteRepository.getOne(id);
		return transporte;
	}

}