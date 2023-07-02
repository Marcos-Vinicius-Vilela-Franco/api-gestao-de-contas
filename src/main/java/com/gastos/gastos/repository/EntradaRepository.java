package com.gastos.gastos.repository;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gastos.gastos.model.EntradaModel;

public interface EntradaRepository extends JpaRepository<EntradaModel,UUID>{
    @Query("SELECT e.valor FROM EntradaModel e WHERE e.id = :id")
    BigDecimal buscarValorPorId(@Param("id") UUID id);

    
}
