import enums.Categoria;
import enums.EstadoLibro;
import exceptions.LibroNoDisponibleException;
import interfaces.Prestable;
import interfaces.Reservable;
import model.Libro;
import model.Prestamo;
import model.Usuario;

import java.util.*;

public class SistemaBiblioteca implements Prestable, Reservable {

  private Map<String, Libro> catalogo = new HashMap<>();
  private Map<String, Queue<Usuario>> listasEspera = new HashMap<>();
  private Map<Categoria, Set<Libro>> librosPorCategoria = new EnumMap<>(Categoria.class);
  private Set<Usuario> usuariosRegistrados = new HashSet<>();
  private Map<Usuario, List<Prestamo>> historialPrestamos = new HashMap<>();


  public void agregarLibro(Libro libro) {
    catalogo.put(libro.getIsbn(), libro);
    librosPorCategoria
        .computeIfAbsent(libro.getCategoria(), k -> new HashSet<>())
        .add(libro);
  }

  public Libro buscarLibroPorISBN(String isbn) {
    return catalogo.get(isbn);
  }

  public Set<Libro> getLibrosPorCategoria(Categoria categoria) {
    return librosPorCategoria.getOrDefault(categoria, Collections.emptySet());
  }

  public void registrarUsuario(Usuario usuario) {
    usuariosRegistrados.add(usuario);
    historialPrestamos.put(usuario, new ArrayList<>());
  }


  @Override
  public void prestarLibro(Libro libro, Usuario usuario) throws LibroNoDisponibleException {
    if (!usuariosRegistrados.contains(usuario)) {
      throw new LibroNoDisponibleException("Usuario no registrado");
    }

    if (libro.getEstado() != EstadoLibro.DISPONIBLE) {
      throw new LibroNoDisponibleException("Libro no disponible: " + libro.getTitulo());
    }

    libro.setEstado(EstadoLibro.PRESTADO);
    Prestamo prestamo = new Prestamo(libro, usuario);
    historialPrestamos.get(usuario).add(prestamo);
  }

  @Override
  public void devolverLibro(Libro libro, Usuario usuario) {
    libro.setEstado(EstadoLibro.DISPONIBLE);
  }

  @Override
  public void reservarLibro(Libro libro, Usuario usuario) throws LibroNoDisponibleException {
    if (!usuariosRegistrados.contains(usuario)) {
      throw new LibroNoDisponibleException("Usuario no registrado");
    }

    if (libro.getEstado() == EstadoLibro.DISPONIBLE) {
      throw new LibroNoDisponibleException("Libro disponible, no necesita reserva");
    }

    Queue<Usuario> cola = listasEspera.getOrDefault(libro.getIsbn(), new LinkedList<>());
    cola.offer(usuario);
    listasEspera.put(libro.getIsbn(), cola);
  }

  @Override
  public void cancelarReserva(Libro libro, Usuario usuario) {
    Queue<Usuario> cola = listasEspera.get(libro.getIsbn());
    if (cola != null) {
      cola.remove(usuario);
    }
  }

  @Override
  public Usuario siguienteEnCola(Libro libro) {
    Queue<Usuario> cola = listasEspera.get(libro.getIsbn());
    return (cola != null && !cola.isEmpty()) ? cola.peek() : null;
  }

  // Getters para testing
  public Map<String, Libro> getCatalogo() { return catalogo; }
  public Map<String, Queue<Usuario>> getListasEspera() { return listasEspera; }
  public Set<Usuario> getUsuariosRegistrados() { return usuariosRegistrados; }

}