# Guía de instalación y ejecución

## Requisitos

- **JDK 17 o superior** (recomendado 21): https://adoptium.net
- **IntelliJ IDEA Community** (trae Maven): https://www.jetbrains.com/idea/download
- Opcional: **MySQL Server** y MySQL Workbench

## 1. Obtener el código

```
git clone https://github.com/TU_USUARIO/smart-pool-security.git
```

También puedes usar **Code → Download ZIP** en la página del repositorio.

## 2. Ejecutar el backend (primero)

1. En IntelliJ: **File → Open** y elige `backend/pom.xml` → **Open as Project**.
2. Espera a que descargue las dependencias.
3. Abre `SmartPoolApplication` y pulsa el triángulo verde de `main`.
4. Cuando aparezca `Started SmartPoolApplication`, abre `http://localhost:8080/api/pools`: debes ver JSON.

Déjalo corriendo mientras uses la aplicación.

## 3. Ejecutar el frontend

1. En otra ventana de IntelliJ: **File → Open** y elige `frontend/pom.xml`.
2. En el panel **Maven**, abre **smart-pool-security → Plugins → javafx → javafx:run**.

El encabezado debe decir **"System online · Connected to backend"**. Si dice "Demo mode", el backend no estaba encendido.

Para apuntar a otro servidor, agrega esta opción de ejecución (VM options):
`-Dsmartpool.api=http://servidor:8080/api`

## 4. Usar MySQL (opcional)

1. Instala MySQL y anota la contraseña de `root`.
2. En `backend/src/main/resources/application-mysql.properties`, cambia `CHANGE_THIS_PASSWORD`. **No subas tu contraseña real a GitHub.**
3. En IntelliJ: **Run → Edit Configurations → SmartPoolApplication → Active profiles: `mysql`**.
4. Ejecuta el backend.

## Problemas frecuentes

| Problema | Solución |
|---|---|
| `release version 17 not supported` | Instala un JDK 17 o superior y selecciónalo en **File → Project Structure → SDK** |
| `Port 8080 was already in use` | Cierra el programa que usa el puerto 8080 |
| El frontend dice "Demo mode" | Inicia primero el backend |
| `Access denied for user 'root'` | La contraseña de `application-mysql.properties` no coincide con la de MySQL |
| `Communications link failure` | MySQL no está corriendo: inícialo desde los Servicios de Windows |


---
[⬅ Volver al inicio](Home)
