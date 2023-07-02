package com.gastos.gastos.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
@Table(name = "saidas")
public class SaidasModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank(message = "A descrição não pode estar vazia")
    private String descricao;

    @NotBlank(message = "O campo valor não pode ser vazio!")
    private BigDecimal valor;

    private LocalDateTime data;

    @ManyToOne
    @JoinColumn(name = "gastos_id")
    @JsonBackReference
    private GastosModel gastos;
}
