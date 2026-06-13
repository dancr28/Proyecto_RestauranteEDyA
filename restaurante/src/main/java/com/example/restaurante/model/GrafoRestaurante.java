package com.example.restaurante.model;

import java.util.*;

public class GrafoRestaurante {
    private final Map<String, NodoRestaurante> nodos = new HashMap<>();
    private final Map<String, List<AristaPasillo>> adyacencia = new HashMap<>();
    public int contadorPasos = 0;

    public GrafoRestaurante(PeticionGrafo peticion) {
        for (NodoRestaurante n : peticion.nodos) {
            nodos.put(n.id, n);
            adyacencia.put(n.id, new ArrayList<>());
        }
        for (AristaPasillo a : peticion.aristas) {
            adyacencia.get(a.origen).add(a);
            if (!peticion.dirigido) {
                adyacencia.get(a.destino).add(new AristaPasillo(a.destino, a.origen, a.distancia));
            }
        }
    }

    // 1. POST /api/dfs
    public List<String> ejecutarDFS(String inicio, String fin) {
        this.contadorPasos = 0;
        List<String> visitados = new ArrayList<>();
        Set<String> visitadosSet = new HashSet<>();
        dfsRecursivo(inicio, fin, visitadosSet, visitados);
        return visitados;
    }

    private boolean dfsRecursivo(String actual, String fin, Set<String> visitadosSet, List<String> visitados) {
        this.contadorPasos++;
        visitados.add(actual);
        visitadosSet.add(actual);
        if (actual.equals(fin)) return true;
        
        for (AristaPasillo a : adyacencia.getOrDefault(actual, Collections.emptyList())) {
            if (!visitadosSet.contains(a.destino)) {
                if (dfsRecursivo(a.destino, fin, visitadosSet, visitados)) return true;
            }
        }
        return false;
    }

    // 2. POST /api/bfs
    public List<String> ejecutarBFS(String inicio, String fin) {
        this.contadorPasos = 0;
        List<String> ordenVisita = new ArrayList<>();
        Set<String> visitados = new HashSet<>();
        Queue<String> cola = new LinkedList<>();

        cola.add(inicio);
        visitados.add(inicio);

        while (!cola.isEmpty()) {
            this.contadorPasos++;
            String actual = cola.poll();
            ordenVisita.add(actual);

            if (actual.equals(fin)) break;

            for (AristaPasillo a : adyacencia.getOrDefault(actual, Collections.emptyList())) {
                if (!visitados.contains(a.destino)) {
                    visitados.add(a.destino);
                    cola.add(a.destino);
                }
            }
        }
        return ordenVisita;
    }

    // 3. POST /api/depth-limited
    public List<String> ejecutarProfundidadLimitada(String inicio, String fin, int limite) {
        this.contadorPasos = 0;
        List<String> visitados = new ArrayList<>();
        Set<String> visitadosSet = new HashSet<>();
        dlsRecursivo(inicio, fin, 0, limite, visitadosSet, visitados);
        return visitados;
    }

    private boolean dlsRecursivo(String actual, String fin, int d, int lim, Set<String> vSet, List<String> vis) {
        this.contadorPasos++;
        vis.add(actual);
        vSet.add(actual);
        if (actual.equals(fin)) return true;
        if (d >= lim) return false;

        for (AristaPasillo a : adyacencia.getOrDefault(actual, Collections.emptyList())) {
            if (!vSet.contains(a.destino)) {
                if (dlsRecursivo(a.destino, fin, d + 1, lim, vSet, vis)) return true;
            }
        }
        return false;
    }

    // 4. POST /api/iterative-deepening
    public List<String> ejecutarProfundidadIterativa(String inicio, String fin) {
        this.contadorPasos = 0;
        List<String> resultadoFinal = new ArrayList<>();
        for (int limite = 0; limite < nodos.size(); limite++) {
            Set<String> visitadosSet = new HashSet<>();
            List<String> visitados = new ArrayList<>();
            if (dlsRecursivo(inicio, fin, 0, limite, visitadosSet, visitados)) {
                resultadoFinal.addAll(visitados);
                break;
            }
            resultadoFinal.addAll(visitados); // Guarda el rastro de la iteración profunda anterior
        }
        return resultadoFinal;
    }

    // 5. POST /api/a-star
    public List<String> ejecutarAEstrella(String inicio, String fin) {
        this.contadorPasos = 0;
        Map<String, Double> gScore = new HashMap<>();
        Map<String, Double> fScore = new HashMap<>();
        Map<String, String> cameFrom = new HashMap<>();

        for (String key : nodos.keySet()) {
            gScore.put(key, Double.MAX_VALUE);
            fScore.put(key, Double.MAX_VALUE);
        }

        gScore.put(inicio, 0.0);
        fScore.put(inicio, calcularHeuristica(inicio, fin));

        PriorityQueue<String> openSet = new PriorityQueue<>(Comparator.comparingDouble(fScore::get));
        openSet.add(inicio);

        while (!openSet.isEmpty()) {
            this.contadorPasos++;
            String actual = openSet.poll();

            if (actual.equals(fin)) {
                return reconstruirCamino(cameFrom, actual);
            }

            for (AristaPasillo a : adyacencia.getOrDefault(actual, Collections.emptyList())) {
                double tentativeGScore = gScore.get(actual) + a.distancia;
                if (tentativeGScore < gScore.get(a.destino)) {
                    cameFrom.put(a.destino, actual);
                    gScore.put(a.destino, tentativeGScore);
                    fScore.put(a.destino, tentativeGScore + calcularHeuristica(a.destino, fin));
                    if (!openSet.contains(a.destino)) {
                        openSet.add(a.destino);
                    }
                }
            }
        }
        return new ArrayList<>(); // No se encontró pasillo libre
    }

    private double calcularHeuristica(String n1, String n2) {
        NodoRestaurante nodo1 = nodos.get(n1);
        NodoRestaurante nodo2 = nodos.get(n2);
        if (nodo1 == null || nodo2 == null) return 0.0;
        // Distancia Euclidiana entre áreas del restaurante
        return Math.sqrt(Math.pow(nodo1.x - nodo2.x, 2) + Math.pow(nodo1.y - nodo2.y, 2));
    }

    private List<String> reconstruirCamino(Map<String, String> cameFrom, String actual) {
        List<String> camino = new ArrayList<>();
        camino.add(actual);
        while (cameFrom.containsKey(actual)) {
            actual = cameFrom.get(actual);
            camino.add(0, actual);
        }
        return camino;
    }

    // 6. POST /api/kruskal
    public List<String> ejecutarKruskal() {
        this.contadorPasos = 0;
        List<AristaPasillo> todasLasAristas = new ArrayList<>();
        for (List<AristaPasillo> lista : adyacencia.values()) {
            todasLasAristas.addAll(lista);
        }
        todasLasAristas.sort(Comparator.comparingDouble(a -> a.distancia));

        Map<String, String> padre = new HashMap<>();
        for (String n : nodos.keySet()) padre.put(n, n);

        List<String> arbolMinimo = new ArrayList<>();
        
        for (AristaPasillo a : todasLasAristas) {
            this.contadorPasos++;
            String raizOrigen = encontrarRaiz(a.origen, padre);
            String raizDestino = encontrarRaiz(a.destino, padre);

            if (!raizOrigen.equals(raizDestino)) {
                arbolMinimo.add(a.origen + " -> " + a.destino + " (" + a.distancia + "m)");
                padre.put(raizOrigen, raizDestino);
            }
        }
        return arbolMinimo;
    }

    private String encontrarRaiz(String nodo, Map<String, String> padre) {
        if (padre.get(nodo).equals(nodo)) return nodo;
        return encontrarRaiz(padre.get(nodo), padre);
    }

    // 7. POST /api/prim
    public List<String> ejecutarPrim(String inicio) {
        this.contadorPasos = 0;
        List<String> arbolMinimo = new ArrayList<>();
        Set<String> dentroDelArbol = new HashSet<>();
        PriorityQueue<AristaPasillo> colaPrioridad = new PriorityQueue<>(Comparator.comparingDouble(a -> a.distancia));

        dentroDelArbol.add(inicio);
        colaPrioridad.addAll(adyacencia.getOrDefault(inicio, Collections.emptyList()));

        while (!colaPrioridad.isEmpty() && dentroDelArbol.size() < nodos.size()) {
            this.contadorPasos++;
            AristaPasillo a = colaPrioridad.poll();

            if (dentroDelArbol.contains(a.origen) && dentroDelArbol.contains(a.destino)) continue;

            String nuevoNodo = dentroDelArbol.contains(a.origen) ? a.destino : a.origen;
            arbolMinimo.add(a.origen + " -> " + a.destino + " (" + a.distancia + "m)");
            dentroDelArbol.add(nuevoNodo);

            colaPrioridad.addAll(adyacencia.getOrDefault(nuevoNodo, Collections.emptyList()));
        }
        return arbolMinimo;
    }

    public double calcularCostoCamino(List<String> camino) {
        double costo = 0;
        for (int i = 0; i < camino.size() - 1; i++) {
            for (AristaPasillo a : adyacencia.getOrDefault(camino.get(i), Collections.emptyList())) {
                if (a.destino.equals(camino.get(i + 1))) {
                    costo += a.distancia;
                    break;
                }
            }
        }
        return costo;
    }
}