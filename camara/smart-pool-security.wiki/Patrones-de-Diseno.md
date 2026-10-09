# Patrones de diseño

El proyecto pertenece a una materia de patrones de diseño. Se usaron solo donde resuelven un problema real de la aplicación.

| Patrón | Dónde está | Para qué sirve |
|---|---|---|
| **Singleton** | `MockDataService` y `ToastManager` (frontend); servicios y repositorios de Spring (backend) | Una sola instancia compartida por toda la aplicación. |
| **Observer** | Frontend: `ObservableList` con `ListChangeListener` para el banner crítico, la campana y las pantallas. Backend: `CriticalAlertEvent` con `@EventListener` y `SseEmitter` | Reaccionar cuando se crea o cambia una alerta sin que las clases se conozcan entre sí. |
| **Strategy** | `AiAnalyzer` con `SimulatedAiAnalyzer`; `DataService` con `MockDataService` y `RestDataService` | Cambiar el análisis de IA o la fuente de datos sin modificar el resto del código. |
| **Factory** | `Btn.create(...)` y el mapa de pantallas con `Supplier` en `AppController` | Crear botones y pantallas en un solo lugar. |
| **Adapter** | `RestDataService` | Traduce el JSON del backend a las clases del frontend. Una cámara real se integraría igual. |
| **Repository** | `PoolRepository`, `CameraRepository`, `UserRepository`, `AlertRepository` | Separar el acceso a datos de la lógica de negocio. |
| **Inyección de dependencias** | Constructores de los servicios y controladores del backend | Facilitar los cambios y las pruebas. |

Esto responde a los requerimientos RNF18, RNF21, RNF23 y RNF32 (independencia del proveedor de IA, patrones apropiados y separación de responsabilidades).


---
[⬅ Volver al inicio](Home)
