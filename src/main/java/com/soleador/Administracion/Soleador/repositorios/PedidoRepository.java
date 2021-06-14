package com.soleador.Administracion.Soleador.repositorios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.soleador.Administracion.Soleador.entidades.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, String>, PagingAndSortingRepository<Pedido, String> {
	@Query("SELECT p from Pedido p WHERE p.baja IS NULL")
	public Page<Pedido> buscarActivos(Pageable pageable);

	@Query("SELECT p from Pedido p WHERE p.baja IS NULL ORDER BY p.fechaPedido")
	public List<Pedido> buscarActivos();

	@Query("SELECT p from Pedido p WHERE p.baja IS NULL AND p.cliente.nombre LIKE :nombre")
	public Page<Pedido> buscarActivos(Pageable pageable, @Param("nombre") String nombre);

	@Query("SELECT p from Pedido p WHERE p.baja IS NULL AND p.cliente.nombre = :nombre")
	public List<Pedido> buscarEnvasePorNombre(@Param("nombre") String nombre);
	
	@Query("SELECT COUNT(p) from Pedido p WHERE p.baja IS NULL")
	public Long cantidadPedidos();
}
