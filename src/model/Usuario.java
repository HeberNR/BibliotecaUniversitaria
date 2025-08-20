package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Usuario {
  private String id;
  private String nombre;
  private List<Prestamo> historialPrestamos;

  public Usuario(String id, String nombre) {
    this.id = id;
    this.nombre = nombre;
    this.historialPrestamos = new ArrayList<>();
  }

  // Getters
  public String getId() {
    return id;
  }

  public String getNombre() {
    return nombre;
  }

  public List<Prestamo> getHistorialPrestamos() {
    return historialPrestamos;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Usuario usuario = (Usuario) o;
    return Objects.equals(id, usuario.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
