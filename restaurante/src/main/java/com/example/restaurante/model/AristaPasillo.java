package com.example.restaurante.model;

public class AristaPasillo {
    public String origen;
    public String destino;
    public double distancia; // Peso de la arista

    public AristaPasillo() {}

    public AristaPasillo(String origen, String destino, double distancia) {
        this.origen = origen;
        this.destino = destino;
        this.distancia = distancia;
    }
}
