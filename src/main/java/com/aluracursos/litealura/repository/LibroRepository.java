package com.aluracursos.litealura.repository;

import com.aluracursos.litealura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    Optional<Libro> findByTituloContainsIgnoreCase(String nombreLibro);
    List<Libro> findByAutorContainsIgnoreCase(String autor);
}
