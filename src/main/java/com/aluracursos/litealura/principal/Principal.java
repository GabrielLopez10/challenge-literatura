package com.aluracursos.litealura.principal;

import com.aluracursos.litealura.model.Libro;
import com.aluracursos.litealura.service.LibroService;
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
                    1 - Buscar Libros por Título
                    2 - Lista de todos los libros buscados
                    3 - Mostrar Libros
                    4 - Buscar Libros por Autor
                    
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
                case 1 -> buscarLibroporTitulo();
                case 2 -> mostrarTodosLosLibros();
                case 3 -> mostrarLibrosPorTitulo();
                case 4 -> buscarYMostrarLibrosPorAutor();
              //case 5 -> mostrarLibroPorAutor();
                case 0 -> System.out.println("Cerrando la aplicación...");
                default -> System.out.println("Opción no valida.");
            }
        } while (opcion != 0);
    }

    private void buscarLibroporTitulo() {
        System.out.println("Escribe el nombre del libro que deseas buscar");
        String nombreLibro = teclado.nextLine();
        try {
            Libro libro = libroService.buscarYGuardarLibro(nombreLibro);
            System.out.println("Libro guardado:\n " + libro);
        } catch (Exception e){
            System.err.println("\nError al buscar el libro: " + e.getMessage());
        }
    }

    private void buscarYMostrarLibrosPorAutor() {
        System.out.println("Escribe el nombre de autor para cargar todos sus libros");
        String autor = teclado.nextLine();
        libroService.buscarYGuardarLibrosPorAutor(autor);

        List<Libro> libros = libroService.buscarLibroPorAutor(autor);

        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros para el autor: " + autor);
        } else {
            System.out.println("Libros encontrados para el autor " + autor + ":");
            libros.forEach(System.out::println);
        }
    }

    private void mostrarTodosLosLibros(){
        List<Libro> libros = libroService.obtenerTodosLosLibros();
        if(libros.isEmpty()){
            System.out.println("No hay libros registrados.");
        } else {
            libros.stream()
                    .sorted(Comparator.comparing(Libro::getIdioma))
                    .forEach(System.out::println);
        }
    }

    private void mostrarLibrosPorTitulo(){
        System.out.println("Escribe el título del libro que deseas buscar:");
        String nombreLibro = teclado.nextLine();
        Optional<Libro> libro = libroService.buscarLibroPorTitulo(nombreLibro);

        libro.ifPresentOrElse(
                l -> System.out.println("El libro es:\n " + l),
                () -> System.out.println("Libro no encontrado.")
        );
    }

    /*private void mostrarLibroPorAutor(){
        System.out.println("Escribe el nombre del autor que deseas buscar:");
        String autor = teclado.nextLine();
        List<Libro> libros = libroService.buscarLibroPorAutor(autor);

        if(libros.isEmpty()){
            System.out.println("No se encontraron libros para ese autor: " + autor);
        } else {
            libros.forEach(System.out::println);
        }
    }*/
}
