package com.aluracursos.litealura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibros(
        @JsonAlias("title") String titulo,

        @JsonAlias("authors") List<Author> autor,

        @JsonAlias("languages") List<String> idioma,

        @JsonAlias("download_count") int numeroDeDescargas
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Author(
            @JsonAlias("name") String nombre,
            @JsonAlias("birth_year") Integer anioNacimiento,
            @JsonAlias("death_year") Integer anioFallecimiento
    ) {}
}
