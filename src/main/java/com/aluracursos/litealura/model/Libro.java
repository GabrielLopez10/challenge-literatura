package com.aluracursos.litealura.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "autor_id", nullable = false)
    private Autor autor;

    private String idioma;

    private int numeroDeDescargas;

    public Libro() {}

    public Libro(DatosLibros datosLibros, Autor autor){
        this.titulo = datosLibros.titulo();
        this.autor = autor;
        this.idioma = (datosLibros.idioma() != null && !datosLibros.idioma().isEmpty())
                ? datosLibros.idioma().get(0)
                : "Idioma desconocido";
        this.numeroDeDescargas = datosLibros.numeroDeDescargas();
    }

    @Override
    public String toString() {
        return String.format(
                "Título: '%s', Autor: '%s', Idioma: '%s', Número de Descargas: %d",
                titulo,
                autor != null ? autor.getNombre() : "Autor no disponible",
                idioma,
                numeroDeDescargas
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public int getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(int numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }
}
