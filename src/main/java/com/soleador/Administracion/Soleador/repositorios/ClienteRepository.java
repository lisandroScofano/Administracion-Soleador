package com.soleador.Administracion.Soleador.repositorios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.soleador.Administracion.Soleador.entidades.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String>, PagingAndSortingRepository<Cliente, String> {

	@Query("SELECT c from Cliente c WHERE c.baja IS NULL")
	public Page<Cliente> buscarActivos(Pageable pageable);

	@Query("SELECT c from Cliente c WHERE c.baja IS NULL ORDER BY c.nombre")
	public List<Cliente> buscarActivos();

	@Query("SELECT c from Cliente c WHERE c.baja IS NULL AND c.nombre LIKE :nombre")
	public Page<Cliente> buscarActivos(Pageable pageable, @Param("nombre") String nombre);

	@Query("SELECT c from Cliente c WHERE c.baja IS NULL AND c.nombre = :nombre")
	public List<Cliente> buscarClientePorNombre(@Param("nombre") String nombre);
}