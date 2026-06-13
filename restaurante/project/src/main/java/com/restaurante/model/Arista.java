package com.restaurante.model;

public class Arista {
    private String destino;
    private int distancia;

    public Arista(String destino, int distancia) {
        this.destino = destino;
        this.distancia = distancia;
    }

    // Getters
    public String getDestino() { return destino; }
    public int getDistancia() { return distancia; }
}