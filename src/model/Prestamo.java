package model;

import java.util.Date;

public class Prestamo {
  private Libro libro;
  private Usuario usuario;
  private Date fechaPrestamo;
  private Date fechaDevolucion;

  public Prestamo(Libro libro, Usuario usuario) {
    this.libro = libro;
    this.usuario = usuario;
    this.fechaPrestamo = new Date();
    this.fechaDevolucion = null;
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

  public Date getFechaDevolucion() {
    return fechaDevolucion;
  }


  public void setFechaDevolucion(Date fechaDevolucion) {
    this.fechaDevolucion = fechaDevolucion;
  }


  public void devolver() {
    this.fechaDevolucion = new Date();
  }

  public boolean estaActivo() {
    return fechaDevolucion == null;
  }
}