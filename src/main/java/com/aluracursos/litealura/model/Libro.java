package com.aluracursos.litealura.model;

import jakarta.persistence.*;

import java.util.stream.Collectors;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(unique = true)
    private String titulo;

    private String autor;

    private String idioma;

    private int numeroDeDescargas;

    public Libro() {}

    public Libro(DatosLibros datosLibros){
        this.titulo = datosLibros.titulo();
        this.autor = (datosLibros.autor() != null && !datosLibros.autor().isEmpty())
        ? datosLibros.autor().get(0).nombre()
        : "Autor Desconocido";
        this.idioma = (datosLibros.idioma() != null && !datosLibros.idioma().isEmpty()
        ?datosLibros.idioma().get(0)
                :"Idioma desconocido");
        this.numeroDeDescargas = datosLibros.numeroDeDescargas();
    }

    @Override
    public String toString() {
        String autores = (autor != null && !autor.isEmpty()) ? autor : "Autor no disponible";

        String idiomas = (idioma != null && !idioma.isEmpty()) ? String.join(", ", idioma)
                : "Idioma no disponible";

        return String.format(
                        "Título: '%s', \n" +
                        " Autor(es): %s, \n" +
                        " Idioma(s): %s, \n" +
                        " Número de Descargas: %d\n",
                titulo, autores, idiomas, numeroDeDescargas
        );
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
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
