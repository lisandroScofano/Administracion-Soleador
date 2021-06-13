package com.soleador.Administracion.Soleador.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.soleador.Administracion.Soleador.entidades.Pedido;

@Repository
public interface ItemPedidoRepository extends JpaRepository<Pedido, String>, PagingAndSortingRepository<Pedido, String> {

}
