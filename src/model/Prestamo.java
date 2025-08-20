package model;

import java.util.Date;

public class Prestamo {
  private Libro libro;
  private Usuario usuario;
  private Date fechaPrestamo;

  public Prestamo(Libro libro, Usuario usuario) {
    this.libro = libro;
    this.usuario = usuario;
    this.fechaPrestamo = new Date();
  }

  // Getters
  public Libro getLibro() {
    return libro;
  }

  public Usuario getUsuario() {
    return usuario;
  }

  public Date getFechaPrestamo() {
    return fechaPrestamo;
  }
}
