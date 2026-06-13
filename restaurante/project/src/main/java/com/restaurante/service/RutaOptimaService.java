package com.restaurante.service;
import org.springframework.stereotype.Service;
import java.util.*;
import com.restaurante.model.Arista;
import com.restaurante.model.GrafoRestaurante;

@Service
public class RutaOptimaService {

    public Map<String, Integer> calcularRutaMasCorta(GrafoRestaurante grafo, String inicio) {
        PriorityQueue<NodoDistancia> pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.distancia));
        Map<String, Integer> distanciasMinimas = new HashMap<>();
        
      
        grafo.getAdyacencia().keySet().forEach(nodo -> distanciasMinimas.put(nodo, Integer.MAX_VALUE));
        distanciasMinimas.put(inicio, 0);
        pq.add(new NodoDistancia(inicio, 0));

        while (!pq.isEmpty()) {
            
            NodoDistancia actual = pq.poll();

            Integer distanciaActual = distanciasMinimas.get(actual.nombre);
if (distanciaActual == null) continue; 
for (Arista arista : grafo.getAdyacencia().getOrDefault(actual.nombre, new ArrayList<>())) {
    Integer distanciaDestino = distanciasMinimas.get(arista.getDestino());
    if (distanciaDestino == null) continue; 
    
    int nuevaDist = distanciaActual + arista.getDistancia();
    if (nuevaDist < distanciaDestino) {
        distanciasMinimas.put(arista.getDestino(), nuevaDist);
        pq.add(new NodoDistancia(arista.getDestino(), nuevaDist));
    }

            }
        }
        return distanciasMinimas;
    }


private static class NodoDistancia {
    String nombre;
    int distancia;

    public NodoDistancia(String nombre, int distancia) {
        this.nombre = nombre;
        this.distancia = distancia;
    }
}
}