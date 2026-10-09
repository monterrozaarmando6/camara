# Hoja de ruta

Lo que falta para cubrir el [documento del proyecto](Estado-de-Implementacion).

## Seguridad y usuarios
- Inicio de sesión con contraseña cifrada y token (RF02, RNF05 y RNF06).
- Permisos por rol: administrador, supervisor y operador (RF03 y RNF07).
- Recuperación de contraseña (RF05).
- Protección de la API y comunicación con HTTPS (RNF08 y RNF10).
- Variables de entorno para claves y credenciales (RNF09 y RNF39).

## Piscinas y cámaras
- Endpoints y pantallas para registrar, editar y eliminar piscinas y cámaras (RF06, RF08, RF09, RF11 y RF12).
- Relaciones por llave foránea entre alertas, cámaras y piscinas.

## Cámaras reales
- Leer video por RTSP desde el backend (por ejemplo con JavaCV) y analizar solo 1 o 2 fotogramas por segundo.
- Una interfaz `CameraSource` con implementaciones para RTSP, webcam y simulada (patrones Adapter y Factory).
- Mostrar la imagen más reciente en el frontend (RF14).
- La URL de la cámara, con usuario y clave, se guarda solo en el backend.

## Inteligencia artificial
1. Contar personas con un modelo de detección (por ejemplo YOLO en formato ONNX), usando la interfaz `AiAnalyzer`.
2. Reglas de movimiento: persona inmóvil o desaparecida durante cierto tiempo.
3. Modelo entrenado para ahogamiento, si el proyecto lo exige. Requiere datos etiquetados y pruebas con videos grabados.
- Guardar cada detección (RF23 y RF57) y medir falsos positivos y negativos (RNF29 y RNF30).

## Alertas y reportes
- Notificaciones externas por correo o mensaje (RF28 y RF52).
- Escalamiento de alertas críticas no atendidas (RF31).
- Clasificar incidentes como real, falso positivo o por revisar (RF39).
- Registro de acciones de usuarios (RF60).
- Reportes con exportación a PDF o CSV (RF61 a RF64).

## Calidad y nube
- Documentar la API con Swagger/OpenAPI y versionarla (RNF35 y RNF37).
- Pruebas automáticas.
- Despliegue en la nube y monitoreo (RNF38 y RNF40).

## Privacidad
El video de personas es información personal. Define cuánto tiempo se conservan las imágenes y los incidentes (RNF44), limita el acceso a usuarios autorizados (RNF43) y avisa de forma visible que hay cámaras. Si el proyecto opera en Colombia, aplica la Ley 1581 de 2012 de protección de datos personales.

## Advertencia
El sistema debe presentarse siempre como **apoyo** al personal de seguridad. Antes de usarlo en una piscina real, pruébalo con videos grabados.


---
[⬅ Volver al inicio](Home)
