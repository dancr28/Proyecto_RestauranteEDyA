package com.restaurante.controller;
import com.restaurante.service.RutaOptimaService;
import com.restaurante.model.GrafoRestaurante;
import com.restaurante.model.Mesa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import com.restaurante.service.GestionPedidosService;
@RestController


@RequestMapping("/api/v2/logistica")
public class LogisticaController {

    @Autowired
    private RutaOptimaService rutaService;
    @Autowired
    private GestionPedidosService gestionService;

@GetMapping("/siguiente-servicio")
public Map<String, Object> obtenerSiguienteServicio() {
    Mesa proximaMesa = gestionService.obtenerSiguienteMesa();
    
    if (proximaMesa == null) {
        return Map.of("mensaje", "No hay pedidos pendientes en la cola.");
    }


    GrafoRestaurante red = new GrafoRestaurante(); 
    red.agregarConexion("Cocina", "Mesa-1", 4);
    red.agregarConexion("Cocina", "Mesa-5", 10);

   
    Map<String, Integer> distanciasInt = rutaService.calcularRutaMasCorta(red, "Cocina");


    Map<String, Object> respuesta = new HashMap<>();
    respuesta.put("mesaDestino", proximaMesa.getNombre());
    respuesta.put("prioridad", proximaMesa.getNivelPrioridad());
    

    respuesta.put("distanciaEstimada", distanciasInt.get(proximaMesa.getNombre()));
    respuesta.put("mapaCompleto", distanciasInt);

    return respuesta;
}
}
