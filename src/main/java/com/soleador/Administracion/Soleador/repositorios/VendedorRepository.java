package com.soleador.Administracion.Soleador.repositorios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.soleador.Administracion.Soleador.entidades.Vendedor;

@Repository
public interface VendedorRepository extends JpaRepository<Vendedor, String>, PagingAndSortingRepository<Vendedor, String> {

	@Query("SELECT v from Vendedor v WHERE v.baja IS NULL")
	public Page<Vendedor> buscarActivos(Pageable pageable);

	@Query("SELECT v from Vendedor v WHERE v.baja IS NULL ORDER BY v.nombre")
	public List<Vendedor> buscarActivos();

	@Query("SELECT v from Vendedor v WHERE v.baja IS NULL AND v.nombre LIKE :nombre")
	public Page<Vendedor> buscarActivos(Pageable pageable, @Param("nombre") String nombre);

	@Query("SELECT v from Vendedor v WHERE v.baja IS NULL AND v.nombre = :nombre")
	public List<Vendedor> buscarVendedorPorNombre(@Param("nombre") String nombre);
}