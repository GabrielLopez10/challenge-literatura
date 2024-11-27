package com.aluracursos.litealura.service;

import com.aluracursos.litealura.model.DatosLibros;
import com.aluracursos.litealura.model.DatosLibrosWrapper;
import com.aluracursos.litealura.model.Libro;
import com.aluracursos.litealura.repository.LibroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibroService {
    private final ConsumoAPI consumoAPI;
    private final ConvierteDatos conversor;
    private final LibroRepository repositorio;
    private static final String URL_BASE = "https://gutendex.com/books/";

    public LibroService(ConsumoAPI consumoAPI, ConvierteDatos conversor, LibroRepository repositorio){
        this.consumoAPI = consumoAPI;
        this.conversor = conversor;
        this.repositorio = repositorio;
    }

    public Libro buscarYGuardarLibro(String nombreLibro) {
        String json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "+"));
        DatosLibrosWrapper biblioteca = conversor.obtenerDatos(json, DatosLibrosWrapper.class);

        if(biblioteca.resultados() == null || biblioteca.resultados().isEmpty()){
            throw new RuntimeException("No se encontraron libros con ese nombre");
        }

        System.out.println("Primer libro encontrado: " + biblioteca.resultados().get(0).titulo());

        DatosLibros datosLibros = biblioteca.resultados().get(0);

        Libro libro = new Libro(datosLibros);
        return repositorio.save(libro);
    }

    public void buscarYGuardarLibrosPorAutor(String autor) {
        // Verificar si ya existen libros de ese autor en la base de datos
        List<Libro> librosExistentes = repositorio.findByAutorContainsIgnoreCase(autor);

        if (!librosExistentes.isEmpty()) {
            System.out.println("Ya existen libros cargados para el autor: " + autor);
            return; // Salir del método para evitar duplicados
        }

        // Realizar la búsqueda por autor en la API
        String json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + autor.replace(" ", "+"));
        DatosLibrosWrapper biblioteca = conversor.obtenerDatos(json, DatosLibrosWrapper.class);

        if (biblioteca != null && biblioteca.resultados() != null && !biblioteca.resultados().isEmpty()) {
            // Recorrer todos los libros encontrados
            for (DatosLibros datosLibros : biblioteca.resultados()) {
                Libro libro = new Libro(datosLibros);
                repositorio.save(libro); // Guardar en la base de datos
                System.out.println("Libro guardado: " + libro);
            }
        } else {
            System.out.println("No se encontraron libros para el autor: " + autor);
        }
    }


    public List<Libro> obtenerTodosLosLibros(){
        return repositorio.findAll();
    }

    public Optional<Libro> buscarLibroPorTitulo(String titulo){
        return repositorio.findByTituloContainsIgnoreCase(titulo);
    }

    public List<Libro> buscarLibroPorAutor(String autor){
        return repositorio.findByAutorContainsIgnoreCase(autor);
    }
}
