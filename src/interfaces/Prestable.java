package interfaces;

import exceptions.LibroNoDisponibleException;
import model.Libro;
import model.Usuario;

public interface Prestable {
  void prestarLibro(Libro libro, Usuario usuario) throws LibroNoDisponibleException;

  void devolverLibro(Libro libro, Usuario usuario);
}