package com.gastos.gastos.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gastos.gastos.model.EntradaModel;
import com.gastos.gastos.model.GastosModel;
import com.gastos.gastos.model.Mensagem;
import com.gastos.gastos.model.SaidasModel;
import com.gastos.gastos.repository.EntradaRepository;
import com.gastos.gastos.repository.GastosRepository;
import com.gastos.gastos.repository.SaidasRepository;

@Service
public class GastosService {

   @Autowired
   private EntradaRepository entradaRepository;

   @Autowired
   private SaidasRepository saidasRepository;

   @Autowired
   private GastosRepository gastosRepository;

   @Autowired
   private Mensagem mensagem;

   //--------------------------------------------lista todos
   public ResponseEntity<List<GastosModel>> listar() {
      return new ResponseEntity<>(gastosRepository.findAll(), HttpStatus.OK);
   }

   //--------------------------------------------encontrar gasto
   public Optional<GastosModel> findGasto(UUID id) {
      return gastosRepository.findById(id);
   }


   //--------------------------------------------cria entrada
   public ResponseEntity<?> createEntrada(EntradaModel obj) {
      // System.out.println("ta aqui >>>>>>>>>>>"+ obj);
      Optional<GastosModel> gastoExiste = gastosRepository.findById(obj.getGastos().getId());
      if (obj.getGastos().getId() == null) {
         mensagem.setMensagem("Id inválido!");
         return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
      } else if (gastoExiste.isPresent()) {
         gastosRepository.adicionarValorPorUuid(obj.getGastos().getId(), obj.getValor());
         return new ResponseEntity<>(entradaRepository.save(obj), HttpStatus.CREATED);
      } else {
         mensagem.setMensagem("Id inválido!");
         return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
      }
   }

   //--------------------------------------------cria saída
   public ResponseEntity<?> createSaidas(SaidasModel obj) {
      // System.out.println("ta aqui >>>>>>>>>>>"+ obj);
      Optional<GastosModel> gastoExiste = gastosRepository.findById(obj.getGastos().getId());
      if (obj.getGastos().getId() == null) {
         mensagem.setMensagem("Id inválido!");
         return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
      } else if (gastoExiste.isPresent()) {
         gastosRepository.subtrairValorPorUuid(obj.getGastos().getId(), obj.getValor());
         return new ResponseEntity<>(saidasRepository.save(obj), HttpStatus.CREATED);
      } else {
         mensagem.setMensagem("Id inválido!");
         return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
      }
   }

   //--------------------------------------------cria Gastos
   public ResponseEntity<?> createGastos(GastosModel obj) {

      return new ResponseEntity<>(gastosRepository.save(obj), HttpStatus.CREATED);
   }

   //--------------------------------------------deleta entrada
    public ResponseEntity<Mensagem> deleteEntrada(UUID uuid){
       Optional<EntradaModel> entradaExiste = entradaRepository.findById(uuid);
       if(entradaExiste.isPresent()){
         EntradaModel entradaParaSerExluida = entradaExiste.get();
         UUID gastosUuid = entradaParaSerExluida.getGastos().getId();
         gastosRepository.subtrairValorPorUuid(gastosUuid,entradaParaSerExluida.getValor());
         entradaRepository.delete(entradaParaSerExluida);
         mensagem.setMensagem("Excluido com sucesso!");
         return new ResponseEntity<Mensagem>(mensagem,HttpStatus.OK);
      }else{
         mensagem.setMensagem("ID inválido!");
         return new ResponseEntity<Mensagem>(mensagem,HttpStatus.BAD_REQUEST); 
      }
      
   }

   //--------------------------------------------deleta saída
   public ResponseEntity<Mensagem> deleteSaida(UUID uuid){
       Optional<SaidasModel> saidaExiste = saidasRepository.findById(uuid);
       if(saidaExiste.isPresent()){
         SaidasModel saidaParaSerExluida = saidaExiste.get();
         UUID gastosUuid = saidaParaSerExluida.getGastos().getId();
         gastosRepository.adicionarValorPorUuid(gastosUuid,saidaParaSerExluida.getValor());
         saidasRepository.delete(saidaParaSerExluida);
         mensagem.setMensagem("Excluido com sucesso!");
         return new ResponseEntity<Mensagem>(mensagem,HttpStatus.OK);
      }else{
         mensagem.setMensagem("ID inválido!");
         return new ResponseEntity<Mensagem>(mensagem,HttpStatus.BAD_REQUEST); 
      }
      
   }
   
   //--------------------------------------------atualiza entrada
   public ResponseEntity<Mensagem> updateEntrada (EntradaModel obj, UUID uuidSelf){
      Optional<EntradaModel> entradaExiste = entradaRepository.findById(uuidSelf);
      
      if(entradaExiste.isPresent()){
         
         var valorParaSerSubtraido = entradaRepository.buscarValorPorId(uuidSelf);
         gastosRepository.subtrairValorPorUuid(obj.getGastos().getId(), valorParaSerSubtraido);
         gastosRepository.adicionarValorPorUuid(obj.getGastos().getId(), obj.getValor());
         mensagem.setMensagem("Concluido!");
         obj.setId(uuidSelf);
         entradaRepository.save(obj);
         return new ResponseEntity<Mensagem>(mensagem,HttpStatus.OK);
      }else{
          mensagem.setMensagem("Id de entrada não encontrado!");
          return new ResponseEntity<Mensagem>(mensagem,HttpStatus.BAD_REQUEST);
      }
   }

   //--------------------------------------------atualiza saída
    public ResponseEntity<Mensagem> updateSaida (SaidasModel obj, UUID uuidSelf){
      Optional<SaidasModel> saidaExiste = saidasRepository.findById(uuidSelf);
      
      if(saidaExiste.isPresent()){
         
         BigDecimal valorParaSerAlterado = saidasRepository.buscarValorPorId(uuidSelf);
        // System.out.println("ta aki >>>>>>>>>>>>>>>>>>>>" + valorParaSerAlterado);
         gastosRepository.adicionarValorPorUuid(obj.getGastos().getId(), valorParaSerAlterado);
         gastosRepository.subtrairValorPorUuid(obj.getGastos().getId(), obj.getValor());
         mensagem.setMensagem("Concluido!");
         obj.setId(uuidSelf);
         saidasRepository.save(obj);
         return new ResponseEntity<Mensagem>(mensagem,HttpStatus.OK);
      }else{
          mensagem.setMensagem("Id de saida não encontrado!");
          return new ResponseEntity<Mensagem>(mensagem,HttpStatus.BAD_REQUEST);
      }
   }

}
