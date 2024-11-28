package com.aluracursos.litealura.principal;

import com.aluracursos.litealura.model.Autor;
import com.aluracursos.litealura.model.Libro;
import com.aluracursos.litealura.service.LibroService;
import com.aluracursos.litealura.utils.IdiomaUtils;
import org.springframework.http.converter.json.GsonBuilderUtils;
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

        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros para el autor: " + nombreAutor);
        } else {
            System.out.println("Libros encontrados para el autor " + nombreAutor + ":");
            libros.forEach(System.out::println);
        }
        System.out.println();
    }

    private void mostrarTodosLosLibros() {
        List<Libro> libros = libroService.obtenerTodosLosLibros();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }

        // Cálculo dinámico de los anchos de columna
        int anchoTitulo = Math.max(
                "Título".length(),
                libros.stream().mapToInt(libro -> libro.getTitulo().length()).max().orElse("Título".length())
        );
        int anchoAutor = Math.max(
                "Autor(es)".length(),
                libros.stream()
                        .mapToInt(libro -> libro.getAutor() != null && libro.getAutor().getNombre() != null
                                ? libro.getAutor().getNombre().length()
                                : "Desconocido".length()
                        ).max().orElse("Autor(es)".length())
        );
        int anchoIdioma = Math.max(
                "Idioma(s)".length(),
                libros.stream().mapToInt(libro -> libro.getIdioma() != null ? libro.getIdioma().length() : 2).max()
                        .orElse("Idioma(s)".length())
        );
        int anchoDescargas = Math.max(
                "Descargas".length(),
                libros.stream().mapToInt(libro -> String.valueOf(libro.getNumeroDeDescargas()).length()).max()
                        .orElse("Descargas".length())
        );

        // Encabezado
        System.out.printf(
                "%-5s %-" + anchoTitulo + "s %-" + anchoAutor + "s %-" + anchoIdioma + "s %-" + anchoDescargas
                        + "s%n",
                "ID", "Título", "Autor(es)", "Idioma(s)", "Descargas"
        );
        System.out.println("-".repeat(5 + anchoTitulo + anchoAutor + anchoIdioma + anchoDescargas + 15));

        // Filas
        for (Libro libro : libros) {
            System.out.printf(
                    "%-5d %-" + anchoTitulo + "s %-" + anchoAutor + "s %-" + anchoIdioma + "s %-" + anchoDescargas
                            + "d%n",
                    libro.getId(),
                    libro.getTitulo(),
                    libro.getAutor() != null && libro.getAutor().getNombre() != null
                            ? libro.getAutor().getNombre()
                            : "Desconocido",
                    libro.getIdioma() != null ? libro.getIdioma() : "N/A",
                    libro.getNumeroDeDescargas()
            );
        }
        System.out.println();
    }

  private void buscarLibroporTitulo(){
        System.out.println("Escribe el título del libro que deseas buscar:");
        var nombreLibro = teclado.nextLine();
        List<Libro> libros = libroService.buscarLibroPorTitulo(nombreLibro);

        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros con ese titulo");
        }

      // Cálculo dinámico de los anchos de columna
      int anchoTitulo = Math.max(
              "Título".length(),
              libros.stream().mapToInt(libro -> libro.getTitulo().length()).max().orElse("Título".length())
      );
      int anchoAutor = Math.max(
              "Autor(es)".length(),
              libros.stream()
                      .mapToInt(libro -> libro.getAutor() != null && libro.getAutor().getNombre() != null
                              ? libro.getAutor().getNombre().length()
                              : "Desconocido".length()
                      ).max().orElse("Autor(es)".length())
      );
      int anchoIdioma = Math.max(
              "Idioma(s)".length(),
              libros.stream().mapToInt(libro -> libro.getIdioma() != null ? libro.getIdioma().length() : 2).max()
                      .orElse("Idioma(s)".length())
      );
      int anchoDescargas = Math.max(
              "Descargas".length(),
              libros.stream().mapToInt(libro -> String.valueOf(libro.getNumeroDeDescargas()).length()).max()
                      .orElse("Descargas".length())
      );

      // Encabezado
      System.out.printf(
              "%-5s %-" + anchoTitulo + "s %-" + anchoAutor + "s %-" + anchoIdioma + "s %-" + anchoDescargas
                      + "s%n",
              "ID", "Título", "Autor(es)", "Idioma(s)", "Descargas"
      );
      System.out.println("-".repeat(5 + anchoTitulo + anchoAutor + anchoIdioma + anchoDescargas + 15));

      // Filas
      for (Libro libro : libros) {
          System.out.printf(
                  "%-5d %-" + anchoTitulo + "s %-" + anchoAutor + "s %-" + anchoIdioma + "s %-" + anchoDescargas
                          + "d%n",
                  libro.getId(),
                  libro.getTitulo(),
                  libro.getAutor() != null && libro.getAutor().getNombre() != null
                          ? libro.getAutor().getNombre()
                          : "Desconocido",
                  libro.getIdioma() != null ? libro.getIdioma() : "N/A",
                  libro.getNumeroDeDescargas()
          );
      }
      System.out.println();
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
        libros.forEach(System.out::println);
    }

    private void mostrarTodosLosAutores() {
        List<Autor> autores = libroService.obtenerTodosLosAutores(); // Método que devuelve todos los autores
        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados.");
            return;
        }

        int anchoNombre = Math.max(
                "Nombre".length(),
                autores.stream().mapToInt(a -> a.getNombre().length()).max().orElse(30)
        );

        // Encabezado de la tabla
        System.out.printf("%-5s %-" + anchoNombre + "s %-15s %-15s%n",
                "ID", "Nombre", "Año Nacimiento", "Año Fallecimiento");
        System.out.println("-".repeat(5 + anchoNombre + 15 + 15));

        // Filas de la tabla
        for (Autor autor : autores) {
            System.out.printf(
                    "%-5d %-" + anchoNombre + "s %-15s %-15s%n",
                    autor.getId(),
                    autor.getNombre(),
                    autor.getAnioNacimiento() != null ? autor.getAnioNacimiento() : "N/A",
                    autor.getAnioFallecimiento() != null ? autor.getAnioFallecimiento() : "N/A"
            );
        }
        System.out.println();
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

        //Ancho dinámico del ancho de columnas
        int anchoNombre = Math.max(
                "Nombre".length(),
                autores.stream().mapToInt(a -> a.getNombre().length()).max().orElse(10)
        );

        System.out.printf("%-5s %-" + anchoNombre + "s %-15s %-15s%n", "ID", "Nombre", "Año Nacimiento",
                "Año Fallecimiento");
        System.out.println("-".repeat(5 + anchoNombre + 30));

        for (Autor autor : autores) {
            System.out.printf(
                    "%-5d %-" + anchoNombre + "s %-15d %-15s%n",
                    autor.getId(),
                    autor.getNombre(),
                    autor.getAnioNacimiento(),
                    autor.getAnioFallecimiento() != null ? autor.getAnioFallecimiento()
                            : "Vivo"
            );
        }
        System.out.println();
    }

    private void mostrarEstadisticasPorIdioma(){
        Map<String, Long> estadisticas = libroService.mostrarEstadisticasLibrosPorIdioma();
        System.out.println("Estadisticas de libros por idioma:");
        System.out.printf("%-20s%-10s\n", "Idioma", "Cantidad");
        System.out.println("---------------------------------");
        estadisticas.forEach((codigoIdioma, cantidad) -> {
            String nombreIdioma = IdiomaUtils.obtenerNombreIdioma(codigoIdioma);
            System.out.printf("%-20s%-10d\n", nombreIdioma, cantidad);
        });
        System.out.println();
    }

    
}
