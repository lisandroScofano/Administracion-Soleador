package com.soleador.Administracion.Soleador.repositorios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.soleador.Administracion.Soleador.entidades.Envase;

@Repository
public interface EnvaseRepository extends JpaRepository<Envase, String>, PagingAndSortingRepository<Envase, String> {
	@Query("SELECT e from Envase e WHERE e.baja IS NULL")
	public Page<Envase> buscarActivos(Pageable pageable);

	@Query("SELECT e from Envase e WHERE e.baja IS NULL ORDER BY e.nombre")
	public List<Envase> buscarActivos();

	@Query("SELECT e from Envase e WHERE e.baja IS NULL AND e.nombre LIKE :nombre")
	public Page<Envase> buscarActivos(Pageable pageable, @Param("nombre") String nombre);

	@Query("SELECT e from Envase e WHERE e.baja IS NULL AND e.nombre = :nombre")
	public List<Envase> buscarEnvasePorNombre(@Param("nombre") String nombre);
}
