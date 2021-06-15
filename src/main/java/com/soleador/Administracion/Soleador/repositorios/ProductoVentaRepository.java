package com.soleador.Administracion.Soleador.repositorios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.soleador.Administracion.Soleador.entidades.ProductoVenta;

@Repository
public interface ProductoVentaRepository extends JpaRepository<ProductoVenta, String>, PagingAndSortingRepository<ProductoVenta, String> {
	@Query("SELECT p from ProductoVenta p WHERE p.baja IS NULL")
	public Page<ProductoVenta> buscarActivos(Pageable pageable);

	@Query("SELECT p from ProductoVenta p WHERE p.baja IS NULL ORDER BY p.nombre")
	public List<ProductoVenta> buscarActivos();

	@Query("SELECT p from ProductoVenta p WHERE p.baja IS NULL AND p.nombre LIKE :nombre")
	public Page<ProductoVenta> buscarActivos(Pageable pageable, @Param("nombre") String nombre);

	@Query("SELECT p from ProductoVenta p WHERE p.baja IS NULL AND p.nombre = :nombre")
	public List<ProductoVenta> buscarEnvasePorNombre(@Param("nombre") String nombre);
}
