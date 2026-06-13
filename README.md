# Proyecto_RestauranteEDyA
# Sistema de Optimización de Rutas - Los Compadres

Este proyecto es el examen práctico final y proyecto integrado para la materia de Estructuras de Datos y Algoritmos (EdyA). Desarrollamos una API REST en el backend usando Java 17 y Spring Boot 3.x, junto con una interfaz web sencilla en el frontend para probar y visualizar el comportamiento de diferentes algoritmos de grafos.

---

## Contexto del Proyecto

Para que el proyecto tuviera una aplicación real, decidimos modelar la logística y distribución física de nuestro restaurante familiar, "El Aguacate / Los Compadres". 

El espacio del local se representó mediante un grafo:
- Nodos: Son los puntos fijos clave dentro del negocio, como la Cocina Principal, la Barra de Bebidas, la Caja de Cobro, el Almacén General y las diferentes zonas de mesas (Zona de Birria, Zona de Tortas Ahogadas, Sección Terraza). Cada área tiene coordenadas X e Y fijas en metros.
- Aristas (Pasillos): Representan los pasillos reales por donde caminan los meseros. El peso de cada arista es la distancia física exacta en metros entre un punto y otro.

Con esto, el sistema puede calcular trayectos óptimos para que el personal entregue los platillos desde la cocina a las mesas en el menor tiempo posible, o diseñar la distribución de pasillos con el menor costo de espacio.

---

## Estructura por Capas del Proyecto

Para mantener el código ordenado y cumplir con la arquitectura limpia, separamos el software en los siguientes paquetes dentro de VS Code:

- controller: Contiene "AlgoritmosController.java", que se encarga de recibir las peticiones web y activar los algoritmos.
- model: Contiene las clases de datos y la lógica fuerte:
  - "NodoRestaurante.java": Define el ID, nombre y coordenadas del lugar.
  - "AristaPasillo.java": Define los caminos que conectan las áreas y su distancia en metros.
  - "PeticionGrafo.java" y "RespuestaAlgoritmo.java": Objetos de transferencia de datos para leer el JSON de entrada y armar la respuesta final.
  - "GrafoRestaurante.java": Es el motor principal donde programamos las estructuras de datos y los 7 algoritmos.

---

## Algoritmos Implementados

El backend expone 7 rutas web de tipo POST bajo la dirección "/api/". Todas las respuestas regresan el camino o árbol calculado, el costo total, el número de pasos que dio el código y el tiempo de ejecución en milisegundos (cumpliendo con la meta de tardar menos de 500 ms).

1. Búsquedas de Fuerza Bruta:
   - DFS (Búsqueda en Profundidad): Sigue un camino a lo profundo de los pasillos hasta dar con la mesa buscada.
   - BFS (Búsqueda en Amplitud): Busca por niveles de cercanía. Asegura encontrar el camino con menos conexiones de pasillos.
   - Depth-Limited (Profundidad Limitada): Corre un DFS pero se detiene al llegar a un límite de niveles definido para no atorarse en ciclos.
   - Iterative-Deepening (Profundidad Iterativa): Ejecuta el límite de profundidad aumentando el nivel de uno en uno hasta encontrar el destino.

2. Algoritmos Voraces y de Optimización:
   - Algoritmo A*: Busca la ruta más corta combinando la distancia de los pasillos con una función de estimación en línea recta (Distancia Euclidiana) usando las coordenadas de las mesas.
   - Kruskal: Revisa todos los pasillos disponibles y calcula la forma de conectar todo el restaurante con los metros mínimos de pasillo sin formar bucles encerrados.
   - Prim: Consigue el mismo árbol mínimo de pasillos que Kruskal, pero empieza a crecer de forma continua desde un nodo central (como la Cocina).

---

## Cómo Ejecutar el Proyecto

1. Descarga y descomprime la carpeta del proyecto.
2. Abre la carpeta raíz dentro de Visual Studio Code (o el IDE que uses).
3. Asegúrate de tener instalado Java 17 o superior y las extensiones de Java.
4. Deja que Maven descargue las dependencias que pusimos en el archivo "pom.xml" (como Lombok y Validation).
5. Abre el archivo de la aplicación principal que tiene el método main y dale en "Run" o "Play".
6. Una vez que el servidor Tomcat se levante en el puerto 8080, abre tu navegador y entra a: http://localhost:8080/

---

## Ejemplo de JSON para Pruebas

El sistema ya viene con un grafo de prueba cargado en la pantalla web, pero si se quiere probar de forma manual en Postman, se puede usar este formato en el cuerpo de la petición (POST):

```json
{
  "inicioId": "cocina",
  "finId": "mesa_ahogadas",
  "dirigido": false,
  "limite": 3,
  "nodos": [
    {"id": "cocina", "nombre": "Cocina Principal", "x": 0.0, "y": 0.0},
    {"id": "barra", "nombre": "Barra de Bebidas", "x": 2.5, "y": 4.0},
    {"id": "caja", "nombre": "Caja de Cobro", "x": 6.0, "y": 0.0},
    {"id": "mesa_birria", "nombre": "Zona de Birria", "x": 5.0, "y": 3.5},
    {"id": "mesa_ahogadas", "nombre": "Zona de Tortas Ahogadas", "x": 9.0, "y": 4.5}
  ],
  "aristas": [
    {"origen": "cocina", "destino": "barra", "distancia": 4.5},
    {"origen": "cocina", "destino": "caja", "distancia": 6.0},
    {"origen": "barra", "destino": "mesa_birria", "distancia": 3.2},
    {"origen": "caja", "destino": "mesa_birria", "distancia": 3.0},
    {"origen": "mesa_birria", "destino": "mesa_ahogadas", "distancia": 4.1},
    {"origen": "barra", "destino": "mesa_ahogadas", "distancia": 6.8}
  ]
}
