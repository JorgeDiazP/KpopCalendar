# Kpop Calendar / Calendario Kpop

Kpop Calendar es una aplicación nativa Android que despliega un listado de videoclips de YouTube de música K-Pop ordenados en forma de calendario.

La aplicación está disponible en [Google Play](https://play.google.com/store/apps/details?id=com.jorgediazp.kpopcalendar).

El propósito del proyecto fué crear una aplicación para mi portfolio con las últimas tendencias en desarrollo Android: **Kotlin**, **Clean Architecture**, **MVVM**, **Jetpack Compose**, **Corrutinas**, **Flows**, **Material 3**, etc.; y a la vez que sea de interés para un grupo de usuarios. 

En este caso, fans del género K-pop que quieran estar al día de los lanzamientos de videoclips, puedan ir a días siguientes y visualizar teasers de las próximas canciones, y también puedan ver qué lanzamientos hubo anteriormente.

Kpop Calendar muestra las siguientes funcionalidades en un menú principal con una barra de navegación inferior:


## Calendario

Por defecto, la aplicación se sitúa en el día de hoy y despliega todos los videoclips o teasers del mes actual.
 
Las URLs de los vídeos y la información de las canciones están almacenadas en un servidor remoto. Se necesita conexión a Internet.

Se puede navegar por el mes haciendo swipe y las barras superior e inferior se colapsan.
Cuando se pulsa en el botón de "play", se reproduce el videoclip.

![Navegación del Calendario en el mes actual](https://github.com/JorgeDiazP/KpopCalendar/blob/master/images/calendario%201_small.gif?raw=true)

Si se pulsa el icono superior con forma de edición de calendario, aparece un díalogo para seleccionar otro día, y se cargan todos los videoclips de ese mes.

Si se pulsa el icono superior de círculo con número (el número indica el día de hoy), la aplicación vuelve a situarse en el día de hoy.

![Navegación del Calendario a otros meses](https://github.com/JorgeDiazP/KpopCalendar/blob/master/images/calendario%202_small.gif?raw=true)


## Buscar

La aplicación incorpora un buscador simple que despliega los resultados en orden de lanzamiento, de más reciente a más antiguo.

Por limitaciones del servidor, actualmente sólo se puede buscar introduciendo el título de la canción o el nombre del artista; no funciona con Strings parciales.

![Buscador](https://github.com/JorgeDiazP/KpopCalendar/blob/master/images/buscar_small.gif?raw=true)

## Me gusta

Los videoclips desplegados en el calendario o en el buscador tienen un corazón en la parte superior derecha.

Si se pulsa el corazón, la información del videoclip se guarda en una BBDD local, se añade a este listado y el corazón se pone en verde.

Si se vuelve a pulsar el corazón, el videoclip se elimina del listado.

![Me Gusta](https://github.com/JorgeDiazP/KpopCalendar/blob/master/images/me%20gusta_small.gif?raw=true)

## Notificaciones

La aplicación permite recibir notificaciones de la consola de Firebase Cloud Messaging, con el objetivo de avisar a los usuarios de los lanzamientos del día, noticias o conciertos.


## Stack Tecnológico

- Lenguaje de programación: **Kotlin**
- **Clean Architecture**  
- Patrón de presentación: **MVVM**
- UI: **Jetpack Compose** con **Material 3**
- Patrón Observer en la capa de presentación: **StateFlow**
- Procesos asíncronos: **Corrutinas** y **Flows**  
- Datos en remoto: **Firebase Cloud Firestore** (La información de los videoclips se transforma a ficheros JSON utilizando **ChatGPT**. Estos ficheros se suben a Firestore mediante un script de **Node.js**)
- Datos en local: **Room** (Sólo para guardar los videoclips marcados como "Me gusta")
- Inyección de dependencias: **Hilt** 
- Reproducción de vídeos de YouTube: [android-youtube-player](https://github.com/PierfrancescoSoffritti/android-youtube-player)
- Otras herramientas de **Firebase**: **Analytics**, **Crashlytics** y **Remote Config**  
- Control de versiones: **Gitflow**
