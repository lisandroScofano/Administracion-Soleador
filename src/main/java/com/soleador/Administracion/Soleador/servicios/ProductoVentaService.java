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
import com.soleador.Administracion.Soleador.entidades.ProductoVenta;
import com.soleador.Administracion.Soleador.repositorios.ProductoVentaRepository;

@Service
public class ProductoVentaService {

	@Autowired
	private ProductoVentaRepository productoVentaRepository;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { WebException.class, Exception.class })
	public ProductoVenta guardar(ProductoVenta productoVenta) throws WebException {
		validar(productoVenta);
		productoVenta.setModificacion(new Date());
		return productoVentaRepository.save(productoVenta);
	}

	private void validar(ProductoVenta productoVenta) throws WebException {

		if (productoVenta.getBaja() != null) {
			throw new WebException("El productoVenta que intenta modificar se encuentra dada de baja.");
		}

		if (productoVenta.getNombre() == null || productoVenta.getNombre().isEmpty()) {
			throw new WebException("El nombre del productoVenta no puede ser vacío.");
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { WebException.class, Exception.class })
	public ProductoVenta eliminar(String id) throws WebException {
		ProductoVenta productoVenta = productoVentaRepository.getOne(id);
		if (productoVenta.getBaja() == null) {
			productoVenta.setBaja(new Date());
			productoVenta = productoVentaRepository.save(productoVenta);
		} else {
			throw new WebException("El productoVenta que intenta eliminar ya se encuentra dado de baja.");
		}
		return productoVenta;
	}

	public Page<ProductoVenta> listarActivos(Pageable paginable, String q) {
		if ((q != null && !q.isEmpty())) {
			return productoVentaRepository.buscarActivos(paginable, "%" + q + "%");
		}
		return productoVentaRepository.buscarActivos(paginable);
	}

	public List<ProductoVenta> listarActivos() {
		return productoVentaRepository.buscarActivos();
	}

	public ProductoVenta buscar(String id) {
		ProductoVenta productoVenta = productoVentaRepository.getOne(id);
		return productoVenta;
	}

}