package com.example.restaurante.model;

import java.util.List;

public class RespuestaAlgoritmo {
    public List<String> recorrido;      // Orden de visita o nodos del árbol
    public double costoTotal;           // Suma de distancias (cuando aplique)
    public long tiempoEjecucionMs;      // Medido en milisegundos
    public int numeroPasos;             // Contador de iteraciones / pasos

    public RespuestaAlgoritmo(List<String> recorrido, double costoTotal, long tiempoEjecucionMs, int numeroPasos) {
        this.recorrido = recorrido;
        this.costoTotal = costoTotal;
        this.tiempoEjecucionMs = tiempoEjecucionMs;
        this.numeroPasos = numeroPasos;
    }
}