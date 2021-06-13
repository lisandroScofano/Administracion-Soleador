package com.soleador.Administracion.Soleador.repositorios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.soleador.Administracion.Soleador.entidades.Transporte;

@Repository
public interface TransporteRepository extends JpaRepository<Transporte, String>, PagingAndSortingRepository<Transporte, String> {

	@Query("SELECT t from Transporte t WHERE t.baja IS NULL")
	public Page<Transporte> buscarActivos(Pageable pageable);

	@Query("SELECT t from Transporte t WHERE t.baja IS NULL ORDER BY t.nombre")
	public List<Transporte> buscarActivos();

	@Query("SELECT t from Transporte t WHERE t.baja IS NULL AND t.nombre LIKE :nombre")
	public Page<Transporte> buscarActivos(Pageable pageable, @Param("nombre") String nombre);

	@Query("SELECT t from Transporte t WHERE t.baja IS NULL AND t.nombre = :nombre")
	public List<Transporte> buscarTransportePorNombre(@Param("nombre") String nombre);
}
