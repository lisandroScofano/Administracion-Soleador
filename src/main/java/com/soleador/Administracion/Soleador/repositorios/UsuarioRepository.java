package com.soleador.Administracion.Soleador.repositorios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.soleador.Administracion.Soleador.entidades.Usuario;
import com.soleador.Administracion.Soleador.enumeraciones.Rol;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String>, PagingAndSortingRepository<Usuario, String> {

	@Query("select u from Usuario u WHERE u.baja IS NULL")
	public Page<Usuario> listarActivos(Pageable pageable);

	@Query("select u from Usuario u, IN(u.roles) r WHERE u.baja IS NULL AND (u.nombre LIKE :q OR u.email LIKE :q)")
	public Page<Usuario> listarActivos(Pageable pageable, @Param("q") String q);

	@Query("select u from Usuario u WHERE u.baja IS NOT NULL")
	public Page<Usuario> listarEliminados(Pageable pageable);

	@Query("select u from Usuario u")
	public Page<Usuario> listarTodos(Pageable pageable);

	@Query("select u from Usuario u WHERE u.baja IS NULL AND u.email = :email")
	public Usuario buscarPorEmail(@Param("email") String email);

	@Query("select u from Usuario u WHERE u.baja IS NULL AND u.id = :id")
	public Usuario buscarPorId(@Param("id") String id);

	@Query("select u from Usuario u, IN(u.roles) r WHERE u.baja IS NULL AND r = :rol ORDER BY u.nombre")
	public List<Usuario> buscarPorRol(@Param("rol") Rol rol);

	@Query("SELECT u from Usuario u WHERE u.baja IS NULL ORDER BY u.nombre")
	public List<Usuario> buscarActivos();

	@Query("SELECT u from Usuario u WHERE u.baja IS NULL AND u.nombre LIKE :nombre")
	public Page<Usuario> buscarActivos(Pageable pageable, @Param("nombre") String nombre);

	@Query("SELECT u from Usuario u WHERE u.baja IS NULL AND u.nombre = :nombre")
	public List<Usuario> buscarUsuarioPorNombre(@Param("nombre") String nombre);

	@Query("SELECT u from Usuario u WHERE u.baja IS NULL")
	public List<Usuario> buscarUsuarioPorId();
}
