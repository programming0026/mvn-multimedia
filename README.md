# Reproductor de video MP4

Reproductor de video sencillo hecho con JavaFX: abre un archivo MP4 y permite reproducirlo, pausarlo y detenerlo.

## Características

- Interfaz JavaFX con selector de archivos.
- Controles Play, Pause y Stop.
- Libera los recursos del video al abrir otro o al cerrar la ventana.

## Requisitos

- JDK 21 y Maven 3.9 o superior.
- No hace falta instalar JavaFX: Maven descarga las dependencias.

## Compilar y ejecutar

```bash
mvn javafx:run
```

## Notas

- Solo reproduce archivos `.mp4` con códecs compatibles con JavaFX (H.264 + AAC).
- Se empaqueta en un JAR con todas las dependencias mediante `mvn clean package`.

## Licencia

Código de ejemplo de uso libre con fines educativos.
