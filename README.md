###  Catálogo de Libros: `HashMap<String, Libro>`
- **ISBN como clave**: Garantiza unicidad y acceso directo O(1)
- **Búsqueda eficiente**: Critical para operaciones frecuentes de consulta

### Listas de Espera: `HashMap<String, Queue<Usuario>>` 
- **Queue**: Orden FIFO natural para reservas
- **Por ISBN**: Acceso rápido a la cola específica de cada libro

### Libros por Categoría: `EnumMap<Categoria, HashSet<Libro>>`
- **Set**: Elimina duplicados dentro de cada categoría
- **EnumMap**: Optimizado para claves de tipo enum

### Usuarios Registrados: `HashSet<Usuario>`
- **Unicidad**: Basado en equals/hashCode del ID de usuario
- **Verificación rápida**: O(1) para validar membresía

### Historial de Préstamos: `HashMap<Usuario, ArrayList<Prestamo>>`
- **List**: Permite múltiples préstamos del mismo libro y mantiene orden temporal
- **Acceso por usuario**: O(1) para obtener historial completo
