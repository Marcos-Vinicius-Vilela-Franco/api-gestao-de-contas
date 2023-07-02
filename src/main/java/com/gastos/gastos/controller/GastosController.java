package com.gastos.gastos.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gastos.gastos.dtos.Entradadto;
import com.gastos.gastos.dtos.SaidaDto;
import com.gastos.gastos.model.EntradaModel;
import com.gastos.gastos.model.GastosModel;
import com.gastos.gastos.model.Mensagem;
import com.gastos.gastos.model.SaidasModel;
import com.gastos.gastos.service.GastosService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class GastosController {
    
    @Autowired
    private GastosService gastosService;

    @GetMapping("/list")
    public ResponseEntity<List<GastosModel>> listar(){
            return gastosService.listar();
    }

    @PostMapping("/create")
    public ResponseEntity<?> salvarGastos (@Valid @RequestBody GastosModel obj){
        return gastosService.createGastos(obj);
    }

    @PostMapping("/create/entrada")
    public ResponseEntity<?> criarEntrada(@Valid @RequestBody Entradadto objDTO){
       
        var entradaModel = new EntradaModel();
        BeanUtils.copyProperties(objDTO, entradaModel);
        entradaModel.setData(LocalDateTime.now(ZoneId.of("UTC")));
         //System.out.println(entradaModel + "ta aqui <<<<<<<<<<<<<<<<<<");
        return gastosService.createEntrada(entradaModel);
        
    }

    @PostMapping("/create/saida")
    public ResponseEntity<?> criarSaida(@Valid @RequestBody SaidaDto objDTO){
       
        var saidasModel = new SaidasModel();
        BeanUtils.copyProperties(objDTO, saidasModel);
        saidasModel.setData(LocalDateTime.now(ZoneId.of("UTC")));
        return gastosService.createSaidas(saidasModel);
        
    }

    @DeleteMapping("/delete/entrada/{uuid}")
    public ResponseEntity<Mensagem> delete(@PathVariable UUID uuid){
        return gastosService.deleteEntrada(uuid);
    }

     @DeleteMapping("/delete/saida/{uuid}")
    public ResponseEntity<Mensagem> deletarSaida(@PathVariable UUID uuid){
        return gastosService.deleteSaida(uuid);
    }
    
    @PutMapping("/update/entrada/{uuid}")
    public ResponseEntity<Mensagem> updateEntrada(@PathVariable UUID uuid,@RequestBody Entradadto objDTO){
        var entradaModel = new EntradaModel();
        BeanUtils.copyProperties(objDTO, entradaModel);
        entradaModel.setData(LocalDateTime.now(ZoneId.of("UTC")));
        return gastosService.updateEntrada(entradaModel,uuid);
    }

     @PutMapping("/update/saida/{uuid}")
    public ResponseEntity<Mensagem> updateSaida(@PathVariable UUID uuid,@RequestBody SaidaDto objDTO){
        var saidasModel = new SaidasModel();
        BeanUtils.copyProperties(objDTO, saidasModel);
        saidasModel.setData(LocalDateTime.now(ZoneId.of("UTC")));
        return gastosService.updateSaida(saidasModel,uuid);
    }

}
