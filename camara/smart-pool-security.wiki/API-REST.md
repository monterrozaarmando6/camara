# API REST

Base: `http://localhost:8080/api`. Formato: JSON. Todavía no hay autenticación (ver [Hoja de ruta](Hoja-de-Ruta)).

## Piscinas

| Método | Ruta | Descripción |
|---|---|---|
| GET | `/pools` | Lista las piscinas |
| GET | `/pools/{id}` | Detalle de una piscina |

## Cámaras

| Método | Ruta | Descripción |
|---|---|---|
| GET | `/cameras` | Lista las cámaras. Filtro opcional: `?pool=` |
| GET | `/cameras/{id}` | Detalle de una cámara |
| GET | `/cameras/{id}/analysis` | Análisis de IA (hoy simulado, con `"simulated": true`) |
| PATCH | `/cameras/{id}/status?value=ONLINE` | Cambia el estado: `ONLINE`, `OFFLINE`, `MAINTENANCE` o `ERROR` |

## Alertas e incidentes

| Método | Ruta | Descripción |
|---|---|---|
| GET | `/alerts` | Lista de alertas, la más reciente primero. Filtros opcionales: `pool`, `camera`, `risk`, `status`, `date` |
| GET | `/alerts/{id}` | Detalle |
| POST | `/alerts/{id}/confirm` | Pasa a `CONFIRMED` (solo desde `NEW`) |
| POST | `/alerts/{id}/attend` | Pasa a `ATTENDED` (desde `NEW` o `CONFIRMED`) |
| POST | `/alerts/{id}/close` | Pasa a `CLOSED` (si no está cerrada) |
| POST | `/alerts/{id}/observations` | Agrega una observación. Cuerpo: `{"text": "..."}` |
| POST | `/alerts/simulate-critical` | Crea una alerta crítica **simulada** |
| GET | `/alerts/stream` | Alertas críticas en tiempo real (Server-Sent Events) |

El historial de incidentes es esta misma lista con filtros.

## Usuarios

| Método | Ruta | Descripción |
|---|---|---|
| GET | `/users` | Lista los usuarios |
| POST | `/users` | Crea un usuario |
| PUT | `/users/{id}` | Actualiza nombre, correo y rol |
| PATCH | `/users/{id}/active?value=false` | Activa o desactiva |
| DELETE | `/users/{id}` | Elimina |

## Estadísticas

| Método | Ruta | Descripción |
|---|---|---|
| GET | `/statistics` | Totales, alertas por riesgo, piscinas por estado y detecciones semanales (demo) |

## Valores posibles

- **Risk:** `LOW`, `MEDIUM`, `HIGH`, `CRITICAL`
- **AlertStatus:** `NEW`, `CONFIRMED`, `ATTENDED`, `CLOSED`
- **PoolStatus:** `NORMAL`, `OBSERVATION`, `ALERT`, `EMERGENCY`
- **CameraStatus:** `ONLINE`, `OFFLINE`, `MAINTENANCE`, `ERROR`
- **Role:** `ADMINISTRATOR`, `SUPERVISOR`, `OPERATOR`

## Errores

Los errores devuelven JSON con el código y el mensaje:

```json
{ "status": 409, "message": "Alert AL-1006 cannot change from CLOSED to CONFIRMED" }
```

- `404`: recurso no encontrado
- `409`: cambio de estado no permitido
- `400`: datos inválidos


---
[⬅ Volver al inicio](Home)
