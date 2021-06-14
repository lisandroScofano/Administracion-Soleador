package com.soleador.Administracion.Soleador.entidades;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.soleador.Administracion.Soleador.enumeraciones.Rol;

import lombok.Data;

@Data
@Entity
public class Usuario implements Serializable {

	private static final long serialVersionUID = -8783981358461384278L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	private String nombre;
	private String direccion;
	private String telefono;
	private String email;
	private String observaciones;

	private String password;

	@Enumerated(EnumType.STRING)
	@ElementCollection(targetClass = Rol.class, fetch = FetchType.EAGER)
	private Collection<Rol> roles;

	@Temporal(TemporalType.TIMESTAMP)
	private Date alta;

	@Temporal(TemporalType.TIMESTAMP)
	private Date baja;

	@Temporal(TemporalType.TIMESTAMP)
	private Date modificacion;

	@Temporal(TemporalType.TIMESTAMP)
	private Date ingreso;

	@PrePersist
	public void agregarFechaAlta() {
		alta = new Date();
		modificacion = new Date();
	}

	@PreUpdate
	public void agregarFechaActualizacion() {
		modificacion = new Date();
	}
}
