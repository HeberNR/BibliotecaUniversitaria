package model;

import enums.Categoria;
import enums.EstadoLibro;

import java.util.Objects;

public class Libro {
  private String isbn;
  private String titulo;
  private String autor;
  private Categoria categoria;
  private EstadoLibro estado;

  public Libro(String isbn, String titulo, String autor, Categoria categoria) {
    this.isbn = isbn;
    this.titulo = titulo;
    this.autor = autor;
    this.categoria = categoria;
    this.estado = EstadoLibro.DISPONIBLE;
  }

  // Getters y Setters
  public String getIsbn() { return isbn; }
  public String getTitulo() { return titulo; }
  public String getAutor() { return autor; }
  public Categoria getCategoria() { return categoria; }
  public EstadoLibro getEstado() { return estado; }
  public void setEstado(EstadoLibro estado) { this.estado = estado; }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Libro libro = (Libro) o;
    return Objects.equals(isbn, libro.isbn);
  }

  @Override
  public int hashCode() {
    return Objects.hash(isbn);
  }

  @Override
  public String toString() {
    return "Libro{" + "isbn='" + isbn + '\'' + ", titulo='" + titulo + '\'' + '}';
  }
}
