# Requerimientos no funcionales

Total: **45 requerimientos** (RNF01 a RNF45), agrupados por categoría. Para saber cuáles se cumplen hoy, consulta [Estado de implementación](Estado-de-Implementacion).

## Rendimiento

| ID | Requerimiento | Descripción |
|---|---|---|
| **RNF01** | Tiempo de respuesta | Las operaciones normales del sistema deben responder en un tiempo adecuado para garantizar una experiencia fluida. |
| **RNF02** | Procesamiento de alertas | Las alertas generadas por eventos críticos deben procesarse con baja latencia. |
| **RNF03** | Concurrencia | El sistema debe permitir que múltiples usuarios consulten el sistema simultáneamente. |
| **RNF04** | Procesamiento de IA | El sistema debe optimizar el procesamiento de las detecciones para evitar retrasos innecesarios. |

## Seguridad

| ID | Requerimiento | Descripción |
|---|---|---|
| **RNF05** | Autenticación segura | El sistema debe utilizar mecanismos seguros para autenticar a los usuarios. |
| **RNF06** | Contraseñas protegidas | Las contraseñas no deben almacenarse en texto plano. |
| **RNF07** | Autorización | Los usuarios solamente deben poder acceder a las funcionalidades correspondientes a su rol. |
| **RNF08** | Protección de APIs | Las APIs deben estar protegidas contra accesos no autorizados. |
| **RNF09** | Protección de claves | Las claves de servicios externos e inteligencia artificial no deben estar expuestas en el frontend. |
| **RNF10** | Comunicación segura | La comunicación entre cliente y servidor debe utilizar HTTPS. |
| **RNF11** | Protección de datos | La información almacenada debe contar con mecanismos adecuados de protección. |
| **RNF12** | Registro de actividades | Las operaciones importantes deben quedar registradas para permitir auditoría. |

## Disponibilidad

| ID | Requerimiento | Descripción |
|---|---|---|
| **RNF13** | Disponibilidad del sistema | La aplicación debe estar disponible para los usuarios autorizados durante la operación normal. |
| **RNF14** | Recuperación ante errores | El sistema debe poder recuperarse de errores de servicios externos sin provocar la caída completa de la aplicación. |
| **RNF15** | Manejo de desconexiones | El sistema debe detectar cuando una cámara o servicio deja de estar disponible. |

## Escalabilidad

| ID | Requerimiento | Descripción |
|---|---|---|
| **RNF16** | Escalabilidad horizontal | La arquitectura debe permitir agregar nuevos servidores o instancias cuando aumente la cantidad de usuarios. |
| **RNF17** | Escalabilidad de cámaras | El sistema debe permitir agregar nuevas cámaras y piscinas sin modificar completamente la aplicación. |
| **RNF18** | Escalabilidad de IA | La arquitectura debe permitir cambiar o ampliar el servicio de inteligencia artificial. |

## Mantenibilidad

| ID | Requerimiento | Descripción |
|---|---|---|
| **RNF19** | Arquitectura modular | El sistema debe estar dividido en componentes independientes. |
| **RNF20** | Código mantenible | El código debe seguir buenas prácticas de programación y principios de diseño. |
| **RNF21** | Patrones de diseño | El sistema debe implementar patrones de diseño apropiados para solucionar problemas recurrentes de arquitectura y desarrollo. |
| **RNF22** | Documentación | Las APIs y componentes principales deben estar documentados. |
| **RNF23** | Separación de responsabilidades | Cada componente debe tener responsabilidades claramente definidas. |

## Usabilidad

| ID | Requerimiento | Descripción |
|---|---|---|
| **RNF24** | Interfaz intuitiva | La interfaz debe ser sencilla de comprender para los usuarios. |
| **RNF25** | Dashboard comprensible | La información de seguridad debe presentarse de manera clara y organizada. |
| **RNF26** | Identificación visual de riesgos | Los diferentes niveles de riesgo deben poder distinguirse fácilmente. |
| **RNF27** | Diseño responsive | La aplicación debe adaptarse a computadores, tablets y dispositivos móviles. |

## Inteligencia artificial

| ID | Requerimiento | Descripción |
|---|---|---|
| **RNF28** | Precisión del modelo | El sistema debe utilizar un modelo de IA adecuado para el análisis de situaciones de riesgo. |
| **RNF29** | Control de falsos positivos | El sistema debe incorporar mecanismos para reducir la generación de alertas incorrectas. |
| **RNF30** | Control de falsos negativos | El sistema debe priorizar la detección de situaciones potencialmente peligrosas. |
| **RNF31** | Trazabilidad de la IA | Las detecciones realizadas por la IA deben poder ser consultadas posteriormente. |
| **RNF32** | Independencia del proveedor | La arquitectura debe permitir reemplazar el servicio de IA por otro proveedor sin reconstruir completamente el sistema. |

## APIs

| ID | Requerimiento | Descripción |
|---|---|---|
| **RNF33** | APIs RESTful | Las APIs principales deben seguir principios REST. |
| **RNF34** | Formato de comunicación | Las APIs deben utilizar formatos estándar como JSON para el intercambio de información. |
| **RNF35** | Versionamiento | Las APIs deben contar con mecanismos de versionamiento. |
| **RNF36** | Manejo de errores | Las APIs deben devolver respuestas de error claras y estructuradas. |
| **RNF37** | Documentación de APIs | Los endpoints deben estar documentados mediante herramientas como Swagger/OpenAPI. |

## Cloud

| ID | Requerimiento | Descripción |
|---|---|---|
| **RNF38** | Despliegue en la nube | La aplicación debe estar desplegada en una infraestructura cloud. |
| **RNF39** | Configuración mediante variables de entorno | Las credenciales y configuraciones sensibles deben manejarse mediante variables de entorno. |
| **RNF40** | Monitoreo | El sistema debe permitir supervisar el estado de los servicios desplegados. |
| **RNF41** | Recuperación | Los servicios deben contar con mecanismos básicos para recuperarse ante fallos. |

## Privacidad y datos

| ID | Requerimiento | Descripción |
|---|---|---|
| **RNF42** | Protección de información personal | El sistema debe proteger la información personal de los usuarios. |
| **RNF43** | Control de acceso a imágenes | Las imágenes o información proveniente de las cámaras solamente deben estar disponibles para usuarios autorizados. |
| **RNF44** | Retención de información | El sistema debe definir cuánto tiempo se conservarán las grabaciones, detecciones e incidentes. |
| **RNF45** | Eliminación de información | El sistema debe permitir eliminar información cuando corresponda de acuerdo con las políticas definidas. |


---
[⬅ Volver al inicio](Home)
