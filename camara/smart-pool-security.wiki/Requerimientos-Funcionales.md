# Requerimientos funcionales

Total: **64 requerimientos** (RF01 a RF64), agrupados por módulo. Para saber cuáles están construidos, consulta [Estado de implementación](Estado-de-Implementacion).

## Gestión de usuarios

| ID | Requerimiento | Descripción |
|---|---|---|
| **RF01** | Registro de usuarios | El sistema debe permitir registrar nuevos usuarios proporcionando información como nombre, correo electrónico, contraseña y rol. |
| **RF02** | Inicio de sesión | El sistema debe permitir a los usuarios autenticarse mediante correo electrónico y contraseña. |
| **RF03** | Gestión de roles | El sistema debe permitir manejar diferentes roles, como administrador, supervisor y operador. |
| **RF04** | Gestión de usuarios | El administrador debe poder consultar, modificar, activar, desactivar y eliminar usuarios. |
| **RF05** | Recuperación de contraseña | El sistema debe permitir recuperar el acceso a una cuenta mediante el correo electrónico registrado. |

## Gestión de piscinas

| ID | Requerimiento | Descripción |
|---|---|---|
| **RF06** | Registrar piscina | El sistema debe permitir registrar una nueva piscina indicando información como nombre, ubicación, capacidad y descripción. |
| **RF07** | Consultar piscinas | El usuario autorizado debe poder visualizar las piscinas registradas. |
| **RF08** | Actualizar piscina | El sistema debe permitir modificar la información de una piscina. |
| **RF09** | Eliminar piscina | El administrador debe poder eliminar o desactivar una piscina registrada. |
| **RF10** | Estado de la piscina | El sistema debe mostrar el estado actual de cada piscina, por ejemplo: **Normal, En observación, Alerta o Emergencia**. |

## Gestión de cámaras

| ID | Requerimiento | Descripción |
|---|---|---|
| **RF11** | Registrar cámara | El sistema debe permitir asociar una cámara a una piscina. |
| **RF12** | Configurar cámara | El usuario autorizado debe poder configurar parámetros de la cámara, como nombre, ubicación, resolución y estado. |
| **RF13** | Consultar estado de cámara | El sistema debe mostrar si una cámara está **conectada, desconectada, en mantenimiento o con error**. |
| **RF14** | Visualización de cámara | El sistema debe permitir visualizar el contenido proporcionado por las cámaras autorizadas. |
| **RF15** | Activar y desactivar cámara | El usuario autorizado debe poder activar o desactivar el monitoreo de una cámara. |
| **RF16** | Asociar cámara con zona | El sistema debe permitir definir qué zona de la piscina está siendo monitoreada por cada cámara. |

## Inteligencia artificial

| ID | Requerimiento | Descripción |
|---|---|---|
| **RF17** | Analizar información de las cámaras | El sistema debe enviar la información necesaria al servicio de inteligencia artificial para realizar el análisis. |
| **RF18** | Detectar personas | La IA debe identificar la presencia de personas dentro de las zonas monitoreadas. |
| **RF19** | Analizar comportamiento | La IA debe analizar determinados movimientos y comportamientos que puedan representar una situación de riesgo. |
| **RF20** | Detectar posible ahogamiento | El sistema debe identificar patrones compatibles con una posible situación de ahogamiento. |
| **RF21** | Determinar nivel de riesgo | La IA debe clasificar una situación detectada según un nivel de riesgo, por ejemplo:<br>• 🟢 Bajo<br>• 🟡 Medio<br>• 🟠 Alto<br>• 🔴 Crítico |
| **RF22** | Generar evento de detección | Cuando la IA detecte una situación relevante, el sistema debe crear automáticamente un evento. |
| **RF23** | Registrar resultado de IA | El sistema debe almacenar el resultado del análisis realizado por la IA. |
| **RF24** | Mostrar nivel de confianza | El sistema debe poder mostrar el nivel de confianza asociado a una detección de IA cuando el servicio utilizado proporcione dicho dato. |

## Sistema de alertas

| ID | Requerimiento | Descripción |
|---|---|---|
| **RF25** | Generar alerta automática | El sistema debe generar una alerta cuando se detecte una situación de riesgo. |
| **RF26** | Clasificar alertas | Las alertas deben clasificarse según su nivel de gravedad. |
| **RF27** | Mostrar alertas en tiempo real | El sistema debe mostrar las nuevas alertas en el panel de monitoreo. |
| **RF28** | Enviar notificaciones | El sistema debe enviar una notificación al personal autorizado cuando se produzca una alerta. |
| **RF29** | Confirmar alerta | El usuario autorizado debe poder confirmar que recibió una alerta. |
| **RF30** | Atender alerta | El usuario debe poder marcar una alerta como atendida. |
| **RF31** | Escalar alerta | Si una alerta crítica no es atendida durante un período determinado, el sistema debe poder escalarla a otro usuario responsable. |
| **RF32** | Cancelar alerta | El usuario autorizado debe poder cerrar una alerta cuando determine que la situación ya fue atendida o que se trató de una falsa alarma. |

## Gestión de incidentes

| ID | Requerimiento | Descripción |
|---|---|---|
| **RF33** | Registrar incidente | Cada situación crítica detectada debe registrarse como un incidente. |
| **RF34** | Consultar incidentes | Los usuarios autorizados deben poder consultar los incidentes registrados. |
| **RF35** | Filtrar incidentes | El sistema debe permitir filtrar incidentes por fecha, piscina, cámara, nivel de riesgo y estado. |
| **RF36** | Consultar detalle del incidente | El sistema debe mostrar información detallada sobre cada incidente. |
| **RF37** | Historial de incidentes | El sistema debe conservar un historial de los incidentes detectados. |
| **RF38** | Agregar observaciones | El personal autorizado debe poder agregar observaciones a un incidente. |
| **RF39** | Clasificar resultado | El usuario autorizado debe poder indicar si el incidente fue real, falso positivo o requiere revisión. |

## Dashboard

| ID | Requerimiento | Descripción |
|---|---|---|
| **RF40** | Mostrar dashboard principal | El sistema debe proporcionar un panel principal con información general del sistema. |
| **RF41** | Mostrar piscinas monitoreadas | El dashboard debe mostrar las piscinas que están siendo monitoreadas. |
| **RF42** | Mostrar cámaras activas | El dashboard debe indicar cuántas cámaras están activas, desconectadas o presentan errores. |
| **RF43** | Mostrar alertas activas | El dashboard debe mostrar las alertas pendientes de atención. |
| **RF44** | Mostrar estadísticas | El sistema debe presentar estadísticas sobre detecciones, alertas e incidentes. |
| **RF45** | Mostrar estado general | El sistema debe proporcionar una vista general del estado de seguridad de las piscinas. |

## APIs e integración

| ID | Requerimiento | Descripción |
|---|---|---|
| **RF46** | API de autenticación | El sistema debe proporcionar una API para gestionar autenticación y autorización. |
| **RF47** | API de piscinas | El backend debe proporcionar endpoints para crear, consultar, actualizar y eliminar piscinas. |
| **RF48** | API de cámaras | El backend debe proporcionar endpoints para administrar las cámaras. |
| **RF49** | API de alertas | El sistema debe proporcionar una API para crear, consultar, actualizar y cerrar alertas. |
| **RF50** | API de incidentes | El sistema debe proporcionar endpoints para gestionar los incidentes. |
| **RF51** | API de inteligencia artificial | El backend debe comunicarse con un servicio de IA mediante una API. |
| **RF52** | API de notificaciones | El sistema debe integrarse con un servicio externo para enviar notificaciones. |
| **RF53** | Comunicación entre servicios | Los diferentes componentes del sistema deben comunicarse mediante APIs definidas. |

## Base de datos

| ID | Requerimiento | Descripción |
|---|---|---|
| **RF54** | Almacenar usuarios | El sistema debe almacenar la información de los usuarios. |
| **RF55** | Almacenar piscinas | El sistema debe almacenar la información de las piscinas. |
| **RF56** | Almacenar cámaras | El sistema debe almacenar la información y configuración de las cámaras. |
| **RF57** | Almacenar detecciones | El sistema debe almacenar las detecciones realizadas por la IA. |
| **RF58** | Almacenar alertas | El sistema debe almacenar las alertas generadas. |
| **RF59** | Almacenar incidentes | El sistema debe almacenar los incidentes y su historial. |
| **RF60** | Registrar acciones | El sistema debe registrar acciones importantes realizadas por los usuarios. |

## Reportes

| ID | Requerimiento | Descripción |
|---|---|---|
| **RF61** | Generar reportes | El sistema debe permitir generar reportes sobre las actividades de monitoreo. |
| **RF62** | Reporte de incidentes | El sistema debe permitir generar reportes de incidentes ocurridos durante un período determinado. |
| **RF63** | Reporte de alertas | El sistema debe permitir consultar estadísticas de las alertas. |
| **RF64** | Exportar información | El sistema debe permitir exportar determinados reportes en formatos como PDF o CSV. |


---
[⬅ Volver al inicio](Home)
