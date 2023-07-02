package com.gastos.gastos.repository;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gastos.gastos.model.SaidasModel;

public interface SaidasRepository extends JpaRepository<SaidasModel,UUID>{
    @Query("SELECT e.valor FROM SaidasModel e WHERE e.id = :id")
    BigDecimal buscarValorPorId(@Param("id") UUID id);
}
