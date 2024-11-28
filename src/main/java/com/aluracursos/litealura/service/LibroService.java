package com.aluracursos.litealura.service;

import com.aluracursos.litealura.model.Autor;
import com.aluracursos.litealura.model.DatosLibros;
import com.aluracursos.litealura.model.DatosLibrosWrapper;
import com.aluracursos.litealura.model.Libro;
import com.aluracursos.litealura.repository.AutorRepository;
import com.aluracursos.litealura.repository.LibroRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LibroService {
    private final ConsumoAPI consumoAPI;
    private final ConvierteDatos conversor;
    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;
    private static final String URL_BASE = "https://gutendex.com/books/";
    private static final Logger logger = LoggerFactory.getLogger(LibroService.class);

    public LibroService(ConsumoAPI consumoAPI, ConvierteDatos conversor, LibroRepository libroRepository, AutorRepository autorRepository){
        this.consumoAPI = consumoAPI;
        this.conversor = conversor;
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public Libro buscarYGuardarLibro(String nombreLibro) {
        // Obtener los datos del libro desde la API
        String json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "+"));
        DatosLibrosWrapper biblioteca = conversor.obtenerDatos(json, DatosLibrosWrapper.class);

        // Validar si no hay resultados
        if (biblioteca.resultados() == null || biblioteca.resultados().isEmpty()) {
            throw new RuntimeException("No se encontraron libros con ese nombre");
        }

        // Obtener el primer libro encontrado
        System.out.println("Primer libro encontrado: " + biblioteca.resultados().get(0).titulo());
        DatosLibros datosLibros = biblioteca.resultados().get(0);

        // Obtener el autor a
        Autor autor = datosLibros.autor().isEmpty()
                ? new Autor("Autor Desconocido", null, null)
                : obtenerOCrearAutor(
                        datosLibros.autor().get(0).nombre(),
                        datosLibros.autor().get(0).anioNacimiento(),
                        datosLibros.autor().get(0).anioFallecimiento()
        );

        // Crear el libro
        Libro libro = new Libro(datosLibros, autor);

        List<Libro> libroExistente = libroRepository.findByTituloContainsIgnoreCaseAndAutor(libro.getTitulo(), autor);
        if (!libroExistente.isEmpty()) {
            throw new RuntimeException("El libro ya existe en la base de datos: " + libro.getTitulo());
        }

        // Guardar el libro en la base de datos
        return libroRepository.save(libro);
    }

    public void buscarYGuardarLibrosPorAutor(String nombreAutor) {
        // Realiza la consulta a la API con el nombre del autor que recibe como parámetro.
        String json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + nombreAutor.replace(" ", "+"));
        DatosLibrosWrapper biblioteca = conversor.obtenerDatos(json, DatosLibrosWrapper.class);

        if (biblioteca != null && biblioteca.resultados() != null && !biblioteca.resultados().isEmpty()) {
            for (DatosLibros datosLibros : biblioteca.resultados()) {
                // Obtener los autores del libro
                if (datosLibros.autor() != null && !datosLibros.autor().isEmpty()) {
                    Autor autor = obtenerOCrearAutor(
                            datosLibros.autor().get(0).nombre(),
                            datosLibros.autor().get(0).anioNacimiento(),
                            datosLibros.autor().get(0).anioFallecimiento()
                    );

                    // Verificar si el libro ya existe antes de guardarlo
                    if (libroYaExiste(datosLibros.titulo(), autor)) {
                        logger.info("El libro con el título '{}' ya existe en la base de datos. Se omitirá.", datosLibros.titulo());
                        continue;  // Si el libro ya existe, no lo guardamos de nuevo
                    }

                    // Crear y guardar el libro con los autores correctos (lista de autores)
                    Libro libro = new Libro(datosLibros, autor);  // Pasar la lista de autores
                    libro.setAutor(autor);
                    libroRepository.save(libro);
                    logger.info("Se guardó el libro: '{}' del autor(s): '{}'", datosLibros.titulo(), autor.getNombre());
                }
            }
            logger.info("Se guardaron los libros del autor: '{}'", nombreAutor);
        } else {
            logger.info("No se encontraron libros para el autor: '{}'", nombreAutor);
        }
    }



    private Autor obtenerOCrearAutor(String nombreCompletoAutor, Integer anioNacimiento, Integer anioFallecimiento) {
        return autorRepository.findByNombreIgnoreCase(nombreCompletoAutor)
                .orElseGet(() -> autorRepository.save(new Autor(nombreCompletoAutor, anioNacimiento, anioFallecimiento)));
    }


    private boolean libroYaExiste(String titulo, Autor autor) {
        return libroRepository.findByTituloAndAutor(titulo, autor).isPresent();
    }


    public List<Libro> obtenerTodosLosLibros(){
        return libroRepository.findAllWithAutor();
    }

    public List<Libro> buscarLibroPorAutor(String nombreAutor){
        return libroRepository.findByAutor_NombreContainsIgnoreCase(nombreAutor);
    }

    public List<Autor> obtenerTodosLosAutores() {
        return autorRepository.findAll();
    }

    public List<Autor> obtenerAutoresVivosEnAnio(int anio) {
        return autorRepository.findAll().stream()
                .filter(a -> {
                    Integer anioNacimiento = a.getAnioNacimiento();
                    Integer anioFallecimiento = a.getAnioFallecimiento();

                    if (anioNacimiento == null) {
                        return false;
                    }

                    return anioNacimiento <= anio && (anioFallecimiento == null || anioFallecimiento > anio);
                })
                .collect(Collectors.toList());
    }
}
