package com.aluracursos.litealura.repository;

import com.aluracursos.litealura.model.Autor;
import com.aluracursos.litealura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    List<Libro> findByTituloContainsIgnoreCase(String nombreLibro);
    List<Libro> findByAutor_NombreContainsIgnoreCase(String nombreAutor);

    @Query("SELECT l FROM Libro l JOIN FETCH l.autor")
    List<Libro> findAllWithAutor();

    Optional<Object> findByTituloAndAutor(String titulo, Autor autor);

    List<Libro> findByTituloContainsIgnoreCaseAndAutor(String titulo, Autor autor);
}
