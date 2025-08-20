
import enums.Categoria;
import exceptions.LibroNoDisponibleException;
import model.Libro;
import model.Usuario;

public class Main {
  public static void main(String[] args) {
    SistemaBiblioteca biblioteca = new SistemaBiblioteca();

    System.out.println("=== SISTEMA DE BIBLIOTECA UNIVERSITARIA ===");

    // 1. Registrar usuarios
    Usuario usuario1 = new Usuario("U001", "Ana García");
    Usuario usuario2 = new Usuario("U002", "Carlos López");
    Usuario usuario3 = new Usuario("U003", "María Rodríguez");

    biblioteca.registrarUsuario(usuario1);
    biblioteca.registrarUsuario(usuario2);
    biblioteca.registrarUsuario(usuario3);

    System.out.println("✅ Usuarios registrados: " + biblioteca.getUsuariosRegistrados().size());

    // 2. Agregar libros al catálogo
    Libro libro1 = new Libro("978-0134685991", "Effective Java", "Joshua Bloch", Categoria.PROGRAMACION);
    Libro libro2 = new Libro("978-0321356680", "Java Concurrency in Practice", "Brian Goetz", Categoria.PROGRAMACION);
    Libro libro3 = new Libro("978-0486404525", "Cálculo Avanzado", "Michael Spivak", Categoria.MATEMATICAS);

    biblioteca.agregarLibro(libro1);
    biblioteca.agregarLibro(libro2);
    biblioteca.agregarLibro(libro3);

    System.out.println("✅ Libros en catálogo: " + biblioteca.getCatalogoLibros().size()); // ✅ CAMBIADO

    // 3. Préstamo de libro (éxito)
    try {
      biblioteca.prestarLibro(libro1, usuario1);
      System.out.println("✅ " + usuario1.getNombre() + " prestó: " + libro1.getTitulo());
    } catch (LibroNoDisponibleException e) {
      System.out.println("❌ Error en préstamo: " + e.getMessage());
    }

    // 4. Intentar prestar mismo libro (debe fallar)
    try {
      biblioteca.prestarLibro(libro1, usuario2);
      System.out.println("✅ " + usuario2.getNombre() + " prestó: " + libro1.getTitulo());
    } catch (LibroNoDisponibleException e) {
      System.out.println("❌ " + usuario2.getNombre() + " no pudo prestar: " + e.getMessage());
    }

    // 5. Reservar libro no disponible
    try {
      biblioteca.reservarLibro(libro1, usuario2);
      System.out.println("✅ " + usuario2.getNombre() + " reservó: " + libro1.getTitulo());
    } catch (LibroNoDisponibleException e) {
      System.out.println("❌ Error en reserva: " + e.getMessage());
    }

    // 6. Otra reserva para mismo libro
    try {
      biblioteca.reservarLibro(libro1, usuario3);
      System.out.println("✅ " + usuario3.getNombre() + " reservó: " + libro1.getTitulo());
    } catch (LibroNoDisponibleException e) {
      System.out.println("❌ Error en reserva: " + e.getMessage());
    }

    // 7. Mostrar cola de espera
    System.out.println("👥 Cola de espera para '" + libro1.getTitulo() + "': " +
        biblioteca.getListaEsperaLibros().get(libro1.getIsbn()).size() + " personas"); // ✅ CAMBIADO

    // 8. Devolver libro
    biblioteca.devolverLibro(libro1, usuario1);
    System.out.println("✅ " + usuario1.getNombre() + " devolvió: " + libro1.getTitulo());

    // 9. Ver siguiente en cola
    Usuario siguiente = biblioteca.obtenerSiguienteEnCola(libro1); // ✅ CAMBIADO
    if (siguiente != null) {
      System.out.println("👉 Siguiente en cola: " + siguiente.getNombre());
    }

    // 10. Mostrar estadísticas finales
    System.out.println("\n=== ESTADÍSTICAS FINALES ===");
    System.out.println("📚 Total libros: " + biblioteca.getCatalogoLibros().size()); // ✅ CAMBIADO
    System.out.println("👥 Total usuarios: " + biblioteca.getUsuariosRegistrados().size());
    System.out.println("⏳ Reservas activas: " + biblioteca.getListaEsperaLibros().size()); // ✅ CAMBIADO
  }
}