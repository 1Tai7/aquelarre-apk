# Proyecto Android con Firebase - Aquelarre

Este proyecto es una aplicación Android desarrollada en Kotlin que utiliza Firebase para [Describe la funcionalidad principal de tu aplicación, por ejemplo, autenticación, almacenamiento de datos, etc.].

## Requisitos

* Android Studio 
* SDK de Android 
* Cuenta de Firebase
* Kotlin

## Configuración

1.  **Clonar el repositorio:**

    ```bash
    git clone [https://github.com/theodelrieu?tab=repositories](https://github.com/theodelrieu?tab=repositories)
    ```

2.  **Abrir el proyecto en Android Studio:**

    * Abre Android Studio y selecciona "Open an existing Android Studio project".
    * Navega hasta la carpeta donde clonaste el repositorio y selecciona el directorio del proyecto.

3.  **Configurar Firebase:**

    * **Crear un proyecto en Firebase:**
        * Ve a la consola de Firebase ([https://console.firebase.google.com/](https://console.firebase.google.com/)).
        * Haz clic en "Add project" y sigue los pasos para crear un nuevo proyecto.
    * **Registrar la aplicación Android en Firebase:**
        * En la consola de Firebase, selecciona tu proyecto.
        * Haz clic en el icono de Android para agregar una nueva aplicación.
        * Ingresa el nombre del paquete de tu aplicación (lo puedes encontrar en el archivo `build.gradle` del módulo `app`).
        * Descarga el archivo `google-services.json` y colócalo en el directorio `app` de tu proyecto Android Studio.
    * **Agregar dependencias de Firebase:**
        * En el archivo `build.gradle` del proyecto (a nivel de proyecto), agrega las siguientes dependencias en el bloque `dependencies`:

    ```gradle
    classpath("com.google.gms:google-services:4.4.0") //o la version mas reciente
    ```

        * En el archivo `build.gradle` del módulo `app`, agrega las siguientes dependencias en el bloque `dependencies`:

    ```gradle
    implementation(platform("com.google.firebase:firebase-bom:32.7.0")) // o la version mas reciente
    implementation("com.google.firebase:firebase-analytics-ktx") //para analytics
    implementation("com.google.firebase:firebase-auth-ktx") //para autenticacion
    implementation ("com.google.firebase:firebase-firestore-ktx") // para firestore
    implementation ("com.google.firebase:firebase-storage-ktx") // para storage
    ```

        * Aplica el plugin de Google Services en la parte superior del archivo `build.gradle` del módulo `app`:

    ```gradle
    apply plugin: 'com.google.gms.google-services'
    ```

        * Sincroniza el proyecto con los archivos Gradle.

4.  **Ejecutar la aplicación:**

    * Conecta un dispositivo Android físico o inicia un emulador.
    * Haz clic en el botón "Run" en Android Studio.

## Funcionalidades

* [Lista de las funcionalidades principales de tu aplicación, por ejemplo:]
    * Autenticación de usuarios con correo electrónico y contraseña.
    * Registrarte como usuario.
    * Ver diferentes Post 
    * Subida y descarga de archivos a Cloud Storage.

## Dependencias

* [Lista de las dependencias utilizadas en el proyecto, por ejemplo:]
    * Firebase Authentication
    * Firebase Firestore
    * Firebase Storage
    * Firebase Analytics
    * Kotlin Coroutines

## Consideraciones

* Asegúrate de tener una conexión a Internet para que Firebase funcione correctamente.
* Para pruebas en emulador, es posible que necesites configurar las Google Play Services.

## Contacto

* [Tu nombre] - [Tu correo electrónico]

## Licencia

* [Tipo de licencia]