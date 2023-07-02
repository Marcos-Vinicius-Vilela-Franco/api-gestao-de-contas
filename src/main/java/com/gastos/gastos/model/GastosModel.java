package com.gastos.gastos.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Data
@Table(name = "gastos_totais")
public class GastosModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
     private UUID id;

     @NotBlank(message = "O campo valor não pode ser vazio")
     private String descricao;

    private BigDecimal valor = BigDecimal.ZERO;;

    @OneToMany(mappedBy = "gastos", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<EntradaModel> entradas;

    @OneToMany(mappedBy = "gastos",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<SaidasModel> saidas;
}
