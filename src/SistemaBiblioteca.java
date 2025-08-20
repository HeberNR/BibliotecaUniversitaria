
import interfaces.Prestable;
import interfaces.Reservable;
import enums.EstadoLibro;
import enums.Categoria;
import exceptions.LibroNoDisponibleException;
import model.*;

import java.util.*;

public class SistemaBiblioteca implements Prestable, Reservable {

  // ESTRUCTURAS EXACTAS del enunciado
  private Map<String, Libro> catalogoLibros = new HashMap<>();
  private Map<String, Queue<Usuario>> listaEsperaLibros = new HashMap<>();
  private Map<Categoria, Set<Libro>> librosPorCategoria = new HashMap<>();
  private Set<Usuario> usuariosRegistrados = new HashSet<>();
  private Map<Usuario, List<Prestamo>> historialPrestamos = new HashMap<>();

  public void agregarLibro(Libro libro) {
    catalogoLibros.put(libro.getIsbn(), libro);
    librosPorCategoria
        .computeIfAbsent(libro.getCategoria(), k -> new HashSet<>())
        .add(libro);
  }

  public Libro buscarLibroPorISBN(String isbn) {
    return catalogoLibros.get(isbn);
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
    // ✅ VALIDACIÓN de usuario registrado (requerimiento)
    if (!usuariosRegistrados.contains(usuario)) {
      throw new LibroNoDisponibleException("Usuario no registrado: " + usuario.getNombre());
    }

    if (libro.getEstado() != EstadoLibro.DISPONIBLE) {
      throw new LibroNoDisponibleException("Libro no disponible: " + libro.getTitulo());
    }

    libro.setEstado(EstadoLibro.PRESTADO);
    Prestamo prestamo = new Prestamo(libro, usuario);

    usuario.agregarPrestamo(prestamo);
    historialPrestamos.get(usuario).add(prestamo);
  }

  @Override
  public void devolverLibro(Libro libro, Usuario usuario) {
    // ✅ BUSCAR préstamo activo y marcar fecha devolución
    Optional<Prestamo> prestamoActivo = historialPrestamos.get(usuario).stream()
        .filter(p -> p.getLibro().equals(libro) && p.estaActivo())
        .findFirst();

    if (prestamoActivo.isPresent()) {
      prestamoActivo.get().devolver();
    }

    libro.setEstado(EstadoLibro.DISPONIBLE);

    notificarSiguienteEnCola(libro);
  }

  @Override
  public void reservarLibro(Libro libro, Usuario usuario) throws LibroNoDisponibleException {
    if (!usuariosRegistrados.contains(usuario)) {
      throw new LibroNoDisponibleException("Usuario no registrado: " + usuario.getNombre());
    }

    if (libro.getEstado() == EstadoLibro.DISPONIBLE) {
      throw new LibroNoDisponibleException("Libro disponible, no necesita reserva: " + libro.getTitulo());
    }

    Queue<Usuario> cola = listaEsperaLibros.getOrDefault(libro.getIsbn(), new LinkedList<>());
    cola.offer(usuario);
    listaEsperaLibros.put(libro.getIsbn(), cola);
  }

  private void notificarSiguienteEnCola(Libro libro) {
    Queue<Usuario> cola = listaEsperaLibros.get(libro.getIsbn());
    if (cola != null && !cola.isEmpty()) {
      Usuario siguiente = cola.poll();
      System.out.println("📩 Notificando a " + siguiente.getNombre() +
          ": El libro '" + libro.getTitulo() + "' está disponible");
    }
  }

  public Usuario obtenerSiguienteEnCola(Libro libro) {
    Queue<Usuario> cola = listaEsperaLibros.get(libro.getIsbn());
    return (cola != null && !cola.isEmpty()) ? cola.peek() : null;
  }

  public Map<String, Libro> getCatalogoLibros() {
    return catalogoLibros;
  }

  public Map<String, Queue<Usuario>> getListaEsperaLibros() {
    return listaEsperaLibros;
  }

  public Set<Usuario> getUsuariosRegistrados() {
    return usuariosRegistrados;
  }

  public Map<Usuario, List<Prestamo>> getHistorialPrestamos() {
    return historialPrestamos;
  }
}