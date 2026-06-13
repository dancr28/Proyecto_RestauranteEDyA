package com.example.restaurante.model;

import java.util.List;

public class PeticionGrafo {
    public List<NodoRestaurante> nodos;
    public List<AristaPasillo> aristas;
    public String inicioId;
    public String finId;      // Usado para búsquedas de caminos (DFS, BFS, A*)
    public Integer limite;    // Usado exclusivamente en Depth-Limited
    public boolean dirigido;
}