package com.soleador.Administracion.Soleador.servicios;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.soleador.Administracion.Soleador.Error.WebException;
import com.soleador.Administracion.Soleador.entidades.Usuario;
import com.soleador.Administracion.Soleador.enumeraciones.Rol;
import com.soleador.Administracion.Soleador.repositorios.UsuarioRepository;

@Service("usuarioService")
public class UsuarioService // implements UserDetailsService
{

	@Autowired
	@Qualifier("usuarioRepository")
	private UsuarioRepository usuarioRepository;

	public Usuario buscar(String id) {
		return usuarioRepository.getOne(id);
	}

	public Usuario findByUsername(String email) {
		return usuarioRepository.buscarPorEmail(email);
	}

	public Usuario guardar(Usuario usuario) throws WebException {

//		if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
//			usuario.setPassword(new BCryptPasswordEncoder().encode(usuario.getPassword()));
//		}

		if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
			throw new WebException("El usuario no puede ser nulo o vacío.");
		}

		if (usuario.getPassword() == null || usuario.getPassword().isEmpty()) {
			throw new WebException("La clave del usuario no puede ser nula o vacía.");
		}

		usuario.setModificacion(new Date());

		usuario = usuarioRepository.save(usuario);

		return usuario;
	}

	public void eliminar(String id) throws WebException {
		if (id != null) {
			Usuario usuario = buscar(id);
			if (usuario == null) {
				throw new WebException("El identificador no se encuentra asociado a ningún usuario.");
			}
			usuario.setBaja(new Date());
			usuarioRepository.save(usuario);
		} else {
			throw new WebException("El identificador del usuario no puede ser nulo.");
		}
	}

	// SPRING SECURITY METHODS

//	public UserDetails loadUserByUsername(String username) {
//		Usuario usuario = findByUsername(username);
//		
//		if(usuario != null){
//		    ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
//		    HttpSession session = attr.getRequest().getSession(true); 
//		    session.setAttribute(Textos.USUARIO_LABEL, usuario);	    	
//	    	return transformar(usuario);
//		} else {
//			throw new UsernameNotFoundException("Nombre de usuario o contraseña incorrecta.");
//		}
//		
//	}

//	private User transformar(Usuario usuario){
//		List<GrantedAuthority> permisos = new ArrayList<>();
//		if(usuario != null){
//			
//			if(usuario.getRoles() != null) {
//				for(Rol rol : usuario.getRoles()) {
//					GrantedAuthority permiso = new SimpleGrantedAuthority("ROLE_" + rol.toString());
//					permisos.add(permiso);
//				}
//			}
//			
//			usuario.setIngreso(new Date());
//			
//			usuario = usuarioRepository.save(usuario);
//			
//			
//			return new User(usuario.getEmail(), usuario.getPassword(), usuario.getBaja() == null, true, true, true, permisos);
//		} else {
//			return null;
//		}
//	}

	public Page<Usuario> buscarTodos(Pageable paginable, String q) {
		if (q == null || q.isEmpty()) {
			return usuarioRepository.listarActivos(paginable);
		} else {
			return usuarioRepository.listarActivos(paginable, "%" + q + "%");
		}
	}

	public List<Usuario> buscarPorRol(Rol rol) {
		return usuarioRepository.buscarPorRol(rol);
	}
}
