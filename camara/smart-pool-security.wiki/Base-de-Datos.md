# Base de datos

Las tablas se crean automáticamente a partir de las entidades Java (Hibernate, `ddl-auto=update`); no hace falta escribir SQL.

| Tabla | Columnas principales |
|---|---|
| `pool` | `id`, `name`, `location`, `cameras`, `people`, `status`, `risk` |
| `camera` | `id` (por ejemplo `CAM-001`), `pool`, `location`, `status`, `monitoring`, `resolution`, `last_update` |
| `app_user` | `id`, `name`, `email`, `role`, `active` |
| `security_alert` | `id` (por ejemplo `AL-1001`), `alert_date`, `alert_time`, `pool`, `camera`, `risk`, `description`, `status` |
| `security_alert_observations` | `security_alert_id`, `observations` |

> En `camera` y `security_alert`, la piscina y la cámara se guardan como texto (nombre e identificador), no como llave foránea. Cambiarlo a relaciones está en la [Hoja de ruta](Hoja-de-Ruta).

## Opciones de base de datos

| Opción | Cuándo usarla | Configuración |
|---|---|---|
| **H2 en archivo** (por defecto) | Desarrollo y demostración, sin instalar nada | `application.properties`. Los datos se guardan en `backend/data/`. Consola: `http://localhost:8080/h2-console` (URL `jdbc:h2:file:./data/smartpool`, usuario `sa`, sin contraseña). |
| **MySQL** | Entorno más cercano a producción | Perfil `mysql`: edita la contraseña en `application-mysql.properties` y ejecuta con ese perfil. La base `smartpool` se crea sola. |

Los datos de demostración se cargan solo cuando la base está vacía (`DataSeeder`). Para empezar de cero con H2, cierra el backend y borra la carpeta `backend/data/`.


---
[⬅ Volver al inicio](Home)
