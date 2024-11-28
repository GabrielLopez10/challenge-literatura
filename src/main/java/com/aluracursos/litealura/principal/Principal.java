package com.aluracursos.litealura.principal;

import com.aluracursos.litealura.model.Autor;
import com.aluracursos.litealura.model.Libro;
import com.aluracursos.litealura.service.LibroService;
import com.aluracursos.litealura.utils.IdiomaUtils;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Principal {
    private final Scanner teclado = new Scanner(System.in);
    private final LibroService libroService;

    public Principal(LibroService libroService){
        this.libroService = libroService;
    }

    public void muestraElMenu(){
        int opcion;

        do {
            System.out.println("""
                    *** MENÚ DE GESTIÓN DE LIBROS ***
                    1 - Guardar un libro
                    2 - Guardar libros por autor
                    3 - Mostrar todos los libros
                    4 - Buscar libro por título
                    5 - Buscar libros por autor
                    6 - Mostrar todos los autores
                    7 - Mostrar autores vivos en un año especifíco
                    8 - Mostrar estadisticas de libros por idioma
                    
                    0 - Salir
                    """);
            System.out.print("Selecciona una opción: ");
            while (!teclado.hasNextInt()) {
                System.out.println("Por favor, ingresa un número valido.");
                teclado.next();
            }
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1 -> buscarYGuardarLibro();
                case 2 -> buscarYGuardarLibrosPorAutor();
                case 3 -> mostrarTodosLosLibros();
                case 4 -> buscarLibroporTitulo();
                case 5 -> buscarLibrosPorAutor();
                case 6 -> mostrarTodosLosAutores();
                case 7 -> mostrarAutoresVivos();
                case 8 -> mostrarEstadisticasPorIdioma();

                case 0 -> System.out.println("Cerrando la aplicación...");
                default -> System.out.println("Opción no valida.");
            }
        } while (opcion != 0);
    }

    private void buscarYGuardarLibro() {
        System.out.println("Ingrese el título del libro: ");
        String titulo = teclado.nextLine();
        try {
            Libro libro = libroService.buscarYGuardarLibro(titulo);
            System.out.println("Libro guardado:\n " + libro);
        } catch (Exception e){
            System.err.println("\nError al buscar el libro: " + e.getMessage());
        }
        System.out.println();
    }

    private void buscarYGuardarLibrosPorAutor() {
        System.out.println("Escribe el nombre de autor para cargar todos sus libros");
        String nombreAutor = teclado.nextLine();
        libroService.buscarYGuardarLibrosPorAutor(nombreAutor);

        List<Libro> libros = libroService.buscarLibroPorAutor(nombreAutor);

        List<Object[]> filas = new ArrayList<>();
        libros.forEach(libro -> filas.add(
                new Object[] {
                        libro.getId(),
                        libro.getTitulo(),
                        libro.getAutor() != null ? libro.getAutor().getNombre() : "Desconocido",
                        libro.getIdioma() != null ? libro.getIdioma() : "N/A",
                        libro.getNumeroDeDescargas()
                }));

        String[] encabezados = {"ID", "Título", "Autor", "Idioma", "Descargas"};

        imprimirTablaDinamica(filas, encabezados);
    }

    private void mostrarTodosLosLibros() {
        List<Libro> libros = libroService.obtenerTodosLosLibros();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }

        List<Object[]> filas = new ArrayList<>();
        libros.forEach(libro -> filas.add(
                new Object[] {
                        libro.getId(),
                        libro.getTitulo(),
                        libro.getAutor() != null ? libro.getAutor().getNombre() : "Desconocido",
                        libro.getIdioma() != null ? libro.getIdioma() : "N/A",
                        libro.getNumeroDeDescargas()
                }));

        String[] encabezados = {"ID", "Título", "Autor", "Idioma", "Descargas"};

        imprimirTablaDinamica(filas, encabezados);
    }

  private void buscarLibroporTitulo(){
        System.out.println("Escribe el título del libro que deseas buscar:");
        var nombreLibro = teclado.nextLine();
        List<Libro> libros = libroService.buscarLibroPorTitulo(nombreLibro);

        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros con ese titulo");
        }

      List<Object[]> filas = new ArrayList<>();
      libros.forEach(libro -> filas.add(
              new Object[] {
                      libro.getId(),
                      libro.getTitulo(),
                      libro.getAutor() != null ? libro.getAutor().getNombre() : "Desconocido",
                      libro.getIdioma() != null ? libro.getIdioma() : "N/A",
                      libro.getNumeroDeDescargas()
              }));

      String[] encabezados = {"ID", "Título", "Autor", "Idioma", "Descargas"};

      imprimirTablaDinamica(filas, encabezados);
    }

    private void buscarLibrosPorAutor(){
        System.out.println("Escribe el nombre del autor que deseas buscar:");
        String autor = teclado.nextLine();
        List<Libro> libros = libroService.buscarLibroPorAutor(autor);

        if(libros.isEmpty()){
            System.out.println("No se encontraron libros para ese autor: " + autor);
            return;
        }
        System.out.println("*** LIBROS DEL AUTOR ***");
        List<Object[]> filas = new ArrayList<>();
        libros.forEach(libro -> filas.add(
                new Object[] {
                        libro.getId(),
                        libro.getTitulo(),
                        libro.getAutor() != null ? libro.getAutor().getNombre() : "Desconocido",
                        libro.getIdioma() != null ? libro.getIdioma() : "N/A",
                        libro.getNumeroDeDescargas()
                }));

        String[] encabezados = {"ID", "Título", "Autor", "Idioma", "Descargas"};

        imprimirTablaDinamica(filas, encabezados);
    }

    private void mostrarTodosLosAutores() {
        List<Autor> autores = libroService.obtenerTodosLosAutores(); // Método que devuelve todos los autores
        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados.");
            return;
        }

        List<Object[]> filas = new ArrayList<>();
        autores.forEach(autor -> filas.add(
                new Object[]{
                        autor.getId(),
                        autor.getNombre(),
                        autor.getAnioNacimiento(),
                        autor.getAnioFallecimiento() != null ? autor.getAnioFallecimiento()
                                : "Vivo"
                }));

        String[] encabezados = {"ID", "Nombre", "Año Nacimiento", "Año Fallecimiento"};

        imprimirTablaDinamica(filas, encabezados);
    }

    private void mostrarAutoresVivos() {
        System.out.println("Ingresa el año para buscar autores vivos en ese momento:");
        while (!teclado.hasNextInt()) {
            System.out.println("Por favor, ingresa un año valido.");
            teclado.next();
        }
        int anio = teclado.nextInt();
        teclado.nextLine();

        List<Autor> autores = libroService.obtenerAutoresVivosEnAnio(anio);
        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores vivos en el año: " + anio);
            return;
        }

        List<Object[]> filas = new ArrayList<>();
        autores.forEach(autor -> filas.add(
                new Object[]{
                        autor.getId(),
                        autor.getNombre(),
                        autor.getAnioNacimiento(),
                        autor.getAnioFallecimiento() != null ? autor.getAnioFallecimiento()
                                : "Vivo"
                }));

        String[] encabezados = {"ID", "Nombre", "Año Nacimiento", "Año Fallecimiento"};

        imprimirTablaDinamica(filas, encabezados);
    }

    private void mostrarEstadisticasPorIdioma(){
        Map<String, Long> estadisticas = libroService.mostrarEstadisticasLibrosPorIdioma();
        System.out.println("Estadisticas de libros por idioma:");
        List<Object[]> filas = new ArrayList<>();
        estadisticas.forEach((codigoIdioma, cantidad) -> {
            String nombreIdioma = IdiomaUtils.obtenerNombreIdioma(codigoIdioma);
            filas.add((new Object[] {nombreIdioma, cantidad}));
        });

        String[] encabezados = {"Idioma", "Cantidad"};

        System.out.println("\nEstadísticas de libros por idiomas:");
        imprimirTablaDinamica(filas, encabezados);
    }

    private void imprimirTablaDinamica(List<Object[]> filas, String[] encabezados) {

        int[] anchoColumnas = new int[encabezados.length];

        for (int i = 0; i < encabezados.length; i++) {
            anchoColumnas[i] = encabezados[i].length();
        }

        for (Object[] fila : filas) {
            for (int i = 0; i < fila.length; i++) {
                int longitudDato = fila[i] != null ? fila[i].toString().length() : 0;
                anchoColumnas[i] = Math.max(anchoColumnas[i], longitudDato);
            }
        }

        StringBuilder encabezado = new StringBuilder();
        for (int i = 0; i < encabezados.length; i++) {
            encabezado.append(String.format("%-" + anchoColumnas[i] + "s", encabezados[i]));
            if (i < encabezados.length - 1) {
                encabezado.append(" | ");
            }
        }
        System.out.println(encabezado);

        String lineaSeparadora = "-".repeat(encabezado.length());
        System.out.println(lineaSeparadora);

        for (Object[] fila : filas) {
            StringBuilder filaString = new StringBuilder();
            for (int i = 0; i <  fila.length; i++) {
                filaString.append(String.format("%-" + anchoColumnas[i] + "s", fila[i] != null
                        ? fila[i].toString() : "N/A"));

                if (i < fila.length - 1) {
                    filaString.append(" | ");
                }
            }
            System.out.println(filaString);

            System.out.println(lineaSeparadora);
        }
    }
}
