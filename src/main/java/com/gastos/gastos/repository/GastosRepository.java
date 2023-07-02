package com.gastos.gastos.repository;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gastos.gastos.model.GastosModel;

import jakarta.transaction.Transactional;


public interface GastosRepository extends JpaRepository<GastosModel,UUID>{

    @Transactional
    @Modifying
    @Query("UPDATE GastosModel g SET g.valor = g.valor - :valor WHERE g.id = :id")
    void findByUuidForUpdate2(@Param("id") UUID id, @Param("valor") BigDecimal valor);


    @Transactional
    @Modifying
    @Query("UPDATE GastosModel g SET g.valor = g.valor + :valor WHERE g.id = :id")
    void findByUuidForUpdate(@Param("id") UUID id, @Param("valor") BigDecimal valor);

    default void adicionarValorPorUuid(UUID id, BigDecimal valor) {
        findByUuidForUpdate(id, valor);
    }

    default void subtrairValorPorUuid(UUID id, BigDecimal valor) {
        findByUuidForUpdate2(id, valor);
    }

    @Query("SELECT e.valor FROM GastosModel e WHERE e.id = :id")
    BigDecimal buscarValorPorId(@Param("id") UUID id);
}
