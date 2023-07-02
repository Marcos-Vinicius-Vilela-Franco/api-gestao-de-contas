package com.gastos.gastos.dtos;

import java.math.BigDecimal;
import java.util.UUID;

import com.gastos.gastos.model.GastosModel;

import jakarta.validation.constraints.NotBlank;

public record Entradadto(@NotBlank String descricao,@NotBlank BigDecimal valor, GastosModel gastos) {
     
}
