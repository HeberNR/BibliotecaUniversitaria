package model;

import java.util.Date;

public class Prestamo {
  private Libro libro;
  private Usuario usuario;
  private Date fechaPrestamo;
  private Date fechaDevolucion; // ✅ NUEVO campo requerido

  public Prestamo(Libro libro, Usuario usuario) {
    this.libro = libro;
    this.usuario = usuario;
    this.fechaPrestamo = new Date();
    this.fechaDevolucion = null; // Inicialmente null
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
  } // ✅ NUEVO getter

  // ✅ NUEVO setter para fecha de devolución
  public void setFechaDevolucion(Date fechaDevolucion) {
    this.fechaDevolucion = fechaDevolucion;
  }

  // ✅ MÉTODO para marcar como devuelto
  public void devolver() {
    this.fechaDevolucion = new Date();
  }

  public boolean estaActivo() {
    return fechaDevolucion == null;
  }
}