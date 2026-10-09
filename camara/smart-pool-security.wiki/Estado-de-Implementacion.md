# Estado de implementación

Resumen basado en el código entregado en este repositorio.

| | Implementado | Parcial o simulado | Pendiente |
|---|---|---|---|
| **Requerimientos funcionales (64)** | 25 | 22 | 17 |
| **Requerimientos no funcionales (45)** | 11 | 6 | 28 |

**Leyenda:** ✅ Implementado · 🟡 Parcial o simulado · ⬜ Pendiente

> Los datos de IA (personas, movimiento, riesgo y confianza) y el video de las cámaras son **simulados**. Dónde dice "Implementado", la funcionalidad existe, pero con esos datos de demostración.
>
> El documento del proyecto habla de un panel web desplegado en la nube. El frontend de este repositorio es una **aplicación de escritorio JavaFX**; el despliegue en la nube sigue pendiente (RNF38).

## Requerimientos funcionales

### Gestión de usuarios

| ID | Requerimiento | Estado | Nota |
|---|---|---|---|
| RF01 | Registro de usuarios | 🟡 Parcial o simulado | Se crean usuarios con nombre, correo y rol; aún sin contraseña |
| RF02 | Inicio de sesión | ⬜ Pendiente |  |
| RF03 | Gestión de roles | 🟡 Parcial o simulado | Los tres roles existen; todavía no restringen el acceso |
| RF04 | Gestión de usuarios | ✅ Implementado | Consultar, editar, activar/desactivar y eliminar usuarios |
| RF05 | Recuperación de contraseña | ⬜ Pendiente |  |

### Gestión de piscinas

| ID | Requerimiento | Estado | Nota |
|---|---|---|---|
| RF06 | Registrar piscina | 🟡 Parcial o simulado | Existe el modelo y los datos de demo; falta el endpoint y la pantalla de registro |
| RF07 | Consultar piscinas | ✅ Implementado | Pantalla Pools y endpoint GET /api/pools |
| RF08 | Actualizar piscina | ⬜ Pendiente |  |
| RF09 | Eliminar piscina | ⬜ Pendiente |  |
| RF10 | Estado de la piscina | ✅ Implementado | Normal, Observation, Alert y Emergency con color propio |

### Gestión de cámaras

| ID | Requerimiento | Estado | Nota |
|---|---|---|---|
| RF11 | Registrar cámara | 🟡 Parcial o simulado | Existe el modelo; falta el endpoint y la pantalla de registro |
| RF12 | Configurar cámara | 🟡 Parcial o simulado | El backend permite cambiar el estado; falta la pantalla de configuración |
| RF13 | Consultar estado de cámara | ✅ Implementado | Online, Offline, Maintenance y Error |
| RF14 | Visualización de cámara | 🟡 Parcial o simulado | Vista simulada; todavía no hay video real |
| RF15 | Activar y desactivar cámara | 🟡 Parcial o simulado | El backend permite cambiar el estado; falta el botón en la interfaz |
| RF16 | Asociar cámara con zona | 🟡 Parcial o simulado | Se guarda la ubicación de cada cámara; no hay zonas |

### Inteligencia artificial

| ID | Requerimiento | Estado | Nota |
|---|---|---|---|
| RF17 | Analizar información de las cámaras | 🟡 Parcial o simulado | Análisis simulado (interfaz AiAnalyzer) |
| RF18 | Detectar personas | 🟡 Parcial o simulado | Conteo de personas simulado |
| RF19 | Analizar comportamiento | 🟡 Parcial o simulado | Análisis de movimiento simulado |
| RF20 | Detectar posible ahogamiento | ⬜ Pendiente |  |
| RF21 | Determinar nivel de riesgo | 🟡 Parcial o simulado | Los cuatro niveles están implementados; el cálculo es simulado |
| RF22 | Generar evento de detección | 🟡 Parcial o simulado | Se pueden crear eventos críticos simulados |
| RF23 | Registrar resultado de IA | ⬜ Pendiente |  |
| RF24 | Mostrar nivel de confianza | 🟡 Parcial o simulado | Se muestra una confianza simulada |

### Sistema de alertas

| ID | Requerimiento | Estado | Nota |
|---|---|---|---|
| RF25 | Generar alerta automática | 🟡 Parcial o simulado | Alertas críticas simuladas; aún no las genera una IA real |
| RF26 | Clasificar alertas | ✅ Implementado | Clasificación por nivel de riesgo |
| RF27 | Mostrar alertas en tiempo real | ✅ Implementado | Panel actualizado y aviso en tiempo real (Server-Sent Events) |
| RF28 | Enviar notificaciones | 🟡 Parcial o simulado | Solo notificaciones dentro de la aplicación |
| RF29 | Confirmar alerta | ✅ Implementado | Botón Confirm |
| RF30 | Atender alerta | ✅ Implementado | Botón Attend |
| RF31 | Escalar alerta | ⬜ Pendiente |  |
| RF32 | Cancelar alerta | 🟡 Parcial o simulado | Se puede cerrar (Close); no distingue la falsa alarma |

### Gestión de incidentes

| ID | Requerimiento | Estado | Nota |
|---|---|---|---|
| RF33 | Registrar incidente | 🟡 Parcial o simulado | Las alertas cumplen el papel de incidentes |
| RF34 | Consultar incidentes | ✅ Implementado | Pantalla Incident History |
| RF35 | Filtrar incidentes | ✅ Implementado | Filtros por fecha, piscina, cámara, riesgo y estado |
| RF36 | Consultar detalle del incidente | ✅ Implementado | Botón View Details |
| RF37 | Historial de incidentes | ✅ Implementado | Las alertas cerradas quedan en el historial |
| RF38 | Agregar observaciones | ✅ Implementado | Botón Add Observation |
| RF39 | Clasificar resultado | ⬜ Pendiente |  |

### Dashboard

| ID | Requerimiento | Estado | Nota |
|---|---|---|---|
| RF40 | Mostrar dashboard principal | ✅ Implementado | Dashboard principal |
| RF41 | Mostrar piscinas monitoreadas | ✅ Implementado | Tarjetas de piscinas en el dashboard |
| RF42 | Mostrar cámaras activas | ✅ Implementado | Tarjeta de cámaras activas y desconectadas |
| RF43 | Mostrar alertas activas | ✅ Implementado | Tarjeta de alertas activas y alertas recientes |
| RF44 | Mostrar estadísticas | ✅ Implementado | Pantalla System Statistics |
| RF45 | Mostrar estado general | ✅ Implementado | Estado general en el dashboard |

### APIs e integración

| ID | Requerimiento | Estado | Nota |
|---|---|---|---|
| RF46 | API de autenticación | ⬜ Pendiente |  |
| RF47 | API de piscinas | 🟡 Parcial o simulado | Solo consulta (GET) |
| RF48 | API de cámaras | 🟡 Parcial o simulado | Consulta y cambio de estado |
| RF49 | API de alertas | 🟡 Parcial o simulado | Consultar, confirmar, atender y cerrar; la creación es solo simulada |
| RF50 | API de incidentes | 🟡 Parcial o simulado | Los incidentes se gestionan con la API de alertas |
| RF51 | API de inteligencia artificial | ⬜ Pendiente |  |
| RF52 | API de notificaciones | ⬜ Pendiente |  |
| RF53 | Comunicación entre servicios | ✅ Implementado | Frontend y backend se comunican por API REST |

### Base de datos

| ID | Requerimiento | Estado | Nota |
|---|---|---|---|
| RF54 | Almacenar usuarios | ✅ Implementado | Tabla app_user |
| RF55 | Almacenar piscinas | ✅ Implementado | Tabla pool |
| RF56 | Almacenar cámaras | ✅ Implementado | Tabla camera |
| RF57 | Almacenar detecciones | ⬜ Pendiente |  |
| RF58 | Almacenar alertas | ✅ Implementado | Tabla security_alert |
| RF59 | Almacenar incidentes | ✅ Implementado | Alertas, observaciones e historial en base de datos |
| RF60 | Registrar acciones | ⬜ Pendiente |  |

### Reportes

| ID | Requerimiento | Estado | Nota |
|---|---|---|---|
| RF61 | Generar reportes | ⬜ Pendiente |  |
| RF62 | Reporte de incidentes | ⬜ Pendiente |  |
| RF63 | Reporte de alertas | ⬜ Pendiente |  |
| RF64 | Exportar información | ⬜ Pendiente |  |


## Requerimientos no funcionales

### Rendimiento

| ID | Requerimiento | Estado | Nota |
|---|---|---|---|
| RNF01 | Tiempo de respuesta | ⬜ Pendiente |  |
| RNF02 | Procesamiento de alertas | ⬜ Pendiente |  |
| RNF03 | Concurrencia | ⬜ Pendiente |  |
| RNF04 | Procesamiento de IA | ⬜ Pendiente |  |

### Seguridad

| ID | Requerimiento | Estado | Nota |
|---|---|---|---|
| RNF05 | Autenticación segura | ⬜ Pendiente |  |
| RNF06 | Contraseñas protegidas | ⬜ Pendiente |  |
| RNF07 | Autorización | ⬜ Pendiente |  |
| RNF08 | Protección de APIs | ⬜ Pendiente |  |
| RNF09 | Protección de claves | ⬜ Pendiente |  |
| RNF10 | Comunicación segura | ⬜ Pendiente |  |
| RNF11 | Protección de datos | ⬜ Pendiente |  |
| RNF12 | Registro de actividades | ⬜ Pendiente |  |

### Disponibilidad

| ID | Requerimiento | Estado | Nota |
|---|---|---|---|
| RNF13 | Disponibilidad del sistema | ⬜ Pendiente |  |
| RNF14 | Recuperación ante errores | 🟡 Parcial o simulado | El frontend abre con datos de demo si el backend no responde |
| RNF15 | Manejo de desconexiones | ⬜ Pendiente |  |

### Escalabilidad

| ID | Requerimiento | Estado | Nota |
|---|---|---|---|
| RNF16 | Escalabilidad horizontal | ⬜ Pendiente |  |
| RNF17 | Escalabilidad de cámaras | 🟡 Parcial o simulado | Piscinas y cámaras son datos en la base; falta registrarlas desde la API |
| RNF18 | Escalabilidad de IA | ✅ Implementado | La interfaz AiAnalyzer permite cambiar el servicio de IA sin tocar el resto |

### Mantenibilidad

| ID | Requerimiento | Estado | Nota |
|---|---|---|---|
| RNF19 | Arquitectura modular | ✅ Implementado | Frontend y backend separados; backend en capas controller, service y repository |
| RNF20 | Código mantenible | 🟡 Parcial o simulado | Código ordenado y comentado; faltan pruebas automáticas |
| RNF21 | Patrones de diseño | ✅ Implementado | Ver la página Patrones de diseño |
| RNF22 | Documentación | 🟡 Parcial o simulado | README y wiki; faltan los endpoints en Swagger/OpenAPI |
| RNF23 | Separación de responsabilidades | ✅ Implementado | Cada capa y cada clase tiene una responsabilidad definida |

### Usabilidad

| ID | Requerimiento | Estado | Nota |
|---|---|---|---|
| RNF24 | Interfaz intuitiva | ✅ Implementado | Menú lateral, tarjetas y pantallas por tema |
| RNF25 | Dashboard comprensible | ✅ Implementado | Dashboard con tarjetas y estado de cada piscina |
| RNF26 | Identificación visual de riesgos | ✅ Implementado | Verde, amarillo, naranja y rojo según el riesgo |
| RNF27 | Diseño responsive | 🟡 Parcial o simulado | La interfaz se adapta al tamaño de la ventana; no hay versión móvil |

### Inteligencia artificial

| ID | Requerimiento | Estado | Nota |
|---|---|---|---|
| RNF28 | Precisión del modelo | ⬜ Pendiente |  |
| RNF29 | Control de falsos positivos | ⬜ Pendiente |  |
| RNF30 | Control de falsos negativos | ⬜ Pendiente |  |
| RNF31 | Trazabilidad de la IA | ⬜ Pendiente |  |
| RNF32 | Independencia del proveedor | ✅ Implementado | Igual que RNF18 |

### APIs

| ID | Requerimiento | Estado | Nota |
|---|---|---|---|
| RNF33 | APIs RESTful | ✅ Implementado | Endpoints REST bajo /api |
| RNF34 | Formato de comunicación | ✅ Implementado | Intercambio de datos en JSON |
| RNF35 | Versionamiento | ⬜ Pendiente |  |
| RNF36 | Manejo de errores | ✅ Implementado | Errores en JSON con código y mensaje |
| RNF37 | Documentación de APIs | ⬜ Pendiente |  |

### Cloud

| ID | Requerimiento | Estado | Nota |
|---|---|---|---|
| RNF38 | Despliegue en la nube | ⬜ Pendiente |  |
| RNF39 | Configuración mediante variables de entorno | 🟡 Parcial o simulado | La configuración está en archivos .properties; falta pasarla a variables de entorno |
| RNF40 | Monitoreo | ⬜ Pendiente |  |
| RNF41 | Recuperación | ⬜ Pendiente |  |

### Privacidad y datos

| ID | Requerimiento | Estado | Nota |
|---|---|---|---|
| RNF42 | Protección de información personal | ⬜ Pendiente |  |
| RNF43 | Control de acceso a imágenes | ⬜ Pendiente |  |
| RNF44 | Retención de información | ⬜ Pendiente |  |
| RNF45 | Eliminación de información | ⬜ Pendiente |  |



---
[⬅ Volver al inicio](Home)
