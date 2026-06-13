package com.restaurante.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.*;

@Data
@AllArgsConstructor



public class GrafoRestaurante {
    private final Map<String, List<Arista>> adyacencia = new HashMap<>();

    public void agregarConexion(String origen, String destino, int distancia) {
        this.adyacencia.computeIfAbsent(origen, k -> new ArrayList<>())
                       .add(new Arista(destino, distancia));
    }

    public Map<String, List<Arista>> getAdyacencia() {
        return adyacencia;
    }
}