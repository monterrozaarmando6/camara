# Smart Pool Security · Cámaras Inteligentes para Piscinas

Aplicación para apoyar la seguridad en piscinas mediante cámaras inteligentes e inteligencia artificial, que detecta posibles situaciones de riesgo —especialmente posibles casos de ahogamiento— y genera alertas para que una persona pueda intervenir.

> ⚠️ **Prototipo universitario.** La inteligencia artificial y las cámaras están simuladas. El sistema es una **herramienta de apoyo**; no reemplaza al salvavidas ni al personal de seguridad.

## Contenido de la wiki

| Página | Qué encontrarás |
|---|---|
| [Descripción del proyecto](Descripcion-del-Proyecto) | Qué es, objetivo, funcionalidades y propósito |
| [Arquitectura y flujo](Arquitectura-y-Flujo) | Capas, flujo principal, nube y arquitectura implementada |
| [Requerimientos funcionales](Requerimientos-Funcionales) | RF01 a RF64, por módulo |
| [Requerimientos no funcionales](Requerimientos-No-Funcionales) | RNF01 a RNF45, por categoría |
| [Patrones de diseño](Patrones-de-Diseno) | Patrones aplicados en el código y dónde |
| [Estado de implementación](Estado-de-Implementacion) | Qué requerimientos ya están y cuáles faltan |
| [API REST](API-REST) | Endpoints del backend |
| [Base de datos](Base-de-Datos) | Tablas y columnas |
| [Guía de instalación y ejecución](Guia-de-Instalacion) | Cómo ejecutar frontend, backend y base de datos |
| [Hoja de ruta](Hoja-de-Ruta) | Siguientes pasos: cámaras reales, IA, seguridad |

## Componentes del repositorio

- **frontend/**: aplicación de escritorio en Java con JavaFX.
- **backend/**: API REST en Java con Spring Boot.
- **Base de datos**: H2 en archivo por defecto, MySQL como opción.
- **docs/**: documento original del proyecto.
