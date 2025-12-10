# Proyecto Final: Analizador de Resultados Históricos de Fútbol Internacional

Este proyecto permite analizar estadísticas básicas de resultados históricos de fútbol internacional a partir de un archivo CSV. Desarrollado en Java con enfoque en Programación Orientada a Objetos, interfaz gráfica y colaboración vía GitHub.

## Tecnologías
- Java
- Swing (GUI)
- Git & GitHub
- SonarCloud


# Estructura del Proyecto

El proyecto está organizado en las siguientes carpetas principales:

- **src/**
  - Contiene todo el código fuente del programa.
  - Subcarpetas:
    - **gui/** → VentanaPrincipal.java (interfaz gráfica)
    - **logic/** → AnalizadorDatos.java (lógica del análisis)
    - **main/** → Main.java (punto de entrada)
    - **model/** → Partido.java (modelo de datos)

- **data/**
  - Contiene el archivo results.csv con los datos de los partidos.

- **bin/**
  - Contiene los archivos .class generados al compilar el proyecto.

- **.github/workflows/**
  - Contiene el archivo build.yml para automatizar tareas con GitHub Actions.

- **.gitignore**
  - Lista de archivos y carpetas que Git debe ignorar.
  

# Arquitectura del Proyecto

## Clases principales
- `Partido`: representa un partido de fútbol.
- `AnalizadorDatos`: carga y analiza los datos del CSV.
- `VentanaPrincipal`: interfaz gráfica para seleccionar equipos y ver estadísticas.
- `Main`: clase principal que inicia la aplicación.

## Principios OOP aplicados
- Encapsulamiento
- Abstracción
- Modularidad


# Instalación y Ejecución

## Requisitos
- Java 11+
- IDE (VS Code, IntelliJ, etc.)

## Pasos
1. Clonar el repositorio
2. Abrir el proyecto en tu IDE
3. Ejecutar `Main.java`
4. Seleccionar equipo en la GUI para ver estadísticas


# Estadísticas por Equipo

Al seleccionar un equipo en la interfaz gráfica se muestran:
- Victorias
- Derrotas
- Empates
- Goles a favor


# Guía de Contribución

## Flujo de trabajo
- Crear rama por funcionalidad
- Hacer commits descriptivos
- Crear Pull Request
- Revisar y aprobar

## Uso de GitHub Projects
- Tablero Kanban con tareas
- Estados: Backlog, To Do, In Progress, Done
