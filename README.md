# Rick and Morty App

Aplicación Android desarrollada como parte del examen técnico para la empresa PASE  
Permite explorar personajes de la API pública [Rick and Morty](https://rickandmortyapi.com/).

## Requisitos previos
- *Android Studio Narwhal 3 Feature Drop* o superior
- *JVM 17*
- Dispositivo físico o emulador con *Android 7.0 (API 24)* o superior

## Cómo ejecutar el proyecto
1. Clonar este repositorio:
   ```bash
   git clone https://github.com/ivanFloresG/RickAndMortyPT.git

2. Abrir el proyecto en Android Studio.

3. Configurar tu API Key de Google Maps (ver sección Configuración de Google Maps).

4. Sincronizar dependencias con Gradle Sync.

5. Conectar un dispositivo físico o iniciar un emulador.

6. Ejecutar la aplicación con el botón Run ▶️ en Android Studio.


## Configuración de Google Maps
La aplicación utiliza Google Maps SDK para mostrar ubicaciones.

### Cómo configurar API Key
1. Ingresar a [Google Cloud Console](https://console.cloud.google.com/).
2. Crear un proyecto o usa uno existente.
3. Habilitar *Google Maps SDK for Android API*.
4. Generar una *API Key* desde la sección APIs & Services > Credentials.
5. Copiar API Key en el archivo local.properties del proyecto:
   MAPS_API_KEY=api_key