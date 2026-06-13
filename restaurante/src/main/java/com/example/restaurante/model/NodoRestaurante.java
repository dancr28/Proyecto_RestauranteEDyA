package com.example.restaurante.model;

public class NodoRestaurante {
    public String id;
    public String nombre; // Ej: "Cocina", "Mesa 1"
    public double x;      // Coordenada X para A*
    public double y;      // Coordenada Y para A*

    public NodoRestaurante() {}

    public NodoRestaurante(String id, String nombre, double x, double y) {
        this.id = id;
        this.nombre = nombre;
        this.x = x;
        this.y = y;
    }
}