package com.gastos.gastos.dtos;

import java.math.BigDecimal;

import com.gastos.gastos.model.GastosModel;

import jakarta.validation.constraints.NotBlank;

public record SaidaDto(@NotBlank String descricao,@NotBlank BigDecimal valor, GastosModel gastos) {
    
}
