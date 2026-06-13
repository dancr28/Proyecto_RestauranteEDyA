package com.restaurante.service;

import com.restaurante.model.Mesa;
import org.springframework.stereotype.Service;
import java.util.PriorityQueue;
import java.util.Comparator;

@Service
public class GestionPedidosService {
    
    private PriorityQueue<Mesa> colaAtencion = new PriorityQueue<>(
        Comparator.comparingInt(Mesa::getNivelPrioridad)
                  .thenComparingLong(Mesa::getTimestampLlegada)
    );

    public void agregarMesaACola(Mesa mesa) {
        colaAtencion.add(mesa);
    }

    public Mesa obtenerSiguienteMesa() {
        return colaAtencion.poll(); // Saca la mesa que debe ser atendida con urgencia
    }
}