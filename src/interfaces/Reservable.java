package interfaces;

import exceptions.LibroNoDisponibleException;
import model.Libro;
import model.Usuario;

public interface Reservable {
  void reservarLibro(Libro libro, Usuario usuario) throws LibroNoDisponibleException;
}