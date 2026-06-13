package com.example.restaurante.controller;

import com.example.restaurante.model.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Permite que tu HTML se conecte sin bloqueos de red
public class AlgoritmosController {

    private RespuestaAlgoritmo empaquetar(List<String> resultado, GrafoRestaurante grafo, long startTime, boolean esArbol) {
        long endTime = System.currentTimeMillis();
        double costo = esArbol ? 0 : grafo.calcularCostoCamino(resultado);
        return new RespuestaAlgoritmo(resultado, costo, (endTime - startTime), grafo.contadorPasos);
    }

    @PostMapping("/dfs")
    public RespuestaAlgoritmo obtenerDFS(@RequestBody PeticionGrafo peticion) {
        long start = System.currentTimeMillis();
        GrafoRestaurante g = new GrafoRestaurante(peticion);
        return empaquetar(g.ejecutarDFS(peticion.inicioId, peticion.finId), g, start, false);
    }

    @PostMapping("/bfs")
    public RespuestaAlgoritmo obtenerBFS(@RequestBody PeticionGrafo peticion) {
        long start = System.currentTimeMillis();
        GrafoRestaurante g = new GrafoRestaurante(peticion);
        return empaquetar(g.ejecutarBFS(peticion.inicioId, peticion.finId), g, start, false);
    }

    @PostMapping("/depth-limited")
    public RespuestaAlgoritmo obtenerDepthLimited(@RequestBody PeticionGrafo peticion) {
        long start = System.currentTimeMillis();
        GrafoRestaurante g = new GrafoRestaurante(peticion);
        int lim = peticion.limite != null ? peticion.limite : 3;
        return empaquetar(g.ejecutarProfundidadLimitada(peticion.inicioId, peticion.finId, lim), g, start, false);
    }

    @PostMapping("/iterative-deepening")
    public RespuestaAlgoritmo obtenerIterativeDeepening(@RequestBody PeticionGrafo peticion) {
        long start = System.currentTimeMillis();
        GrafoRestaurante g = new GrafoRestaurante(peticion);
        return empaquetar(g.ejecutarProfundidadIterativa(peticion.inicioId, peticion.finId), g, start, false);
    }

    @PostMapping("/a-star")
    public RespuestaAlgoritmo obtenerAEstrella(@RequestBody PeticionGrafo peticion) {
        long start = System.currentTimeMillis();
        GrafoRestaurante g = new GrafoRestaurante(peticion);
        return empaquetar(g.ejecutarAEstrella(peticion.inicioId, peticion.finId), g, start, false);
    }

    @PostMapping("/kruskal")
    public RespuestaAlgoritmo obtenerKruskal(@RequestBody PeticionGrafo peticion) {
        long start = System.currentTimeMillis();
        GrafoRestaurante g = new GrafoRestaurante(peticion);
        return empaquetar(g.ejecutarKruskal(), g, start, true);
    }

    @PostMapping("/prim")
    public RespuestaAlgoritmo obtenerPrim(@RequestBody PeticionGrafo peticion) {
        long start = System.currentTimeMillis();
        GrafoRestaurante g = new GrafoRestaurante(peticion);
        return empaquetar(g.ejecutarPrim(peticion.inicioId), g, start, true);
    }
}