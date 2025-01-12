![catalogo logo](https://res.cloudinary.com/dfzw74nlk/image/upload/v1732840509/oy6q4bxcy8gwentsyyty.png)
<p align="center">
  <img width="460" height="300" src="https://picsum.photos/460/30](https://res.cloudinary.com/dfzw74nlk/image/upload/v1732840509/oy6q4bxcy8gwentsyyty.png">
</p>
# Catálogo de Libros 📚


## Descripción
Este es un proyecto en Java y Spring que permite consultar y gestionar un catálogo de libros
utilizando la API de Gutendex.

## Características ✨
- Buscar libros por título o autor.
- Estadísticas por idioma.
- Top 10 libros más descargados.
- Uso de una tabla dinámica para mostrar resultados en la consola.
- Persistencia de datos en PostgreSQL.

## Tecnologías utilizadas 🛠️
- **Lenguaje:** Java 17 o superior.
- **Framework:** Spring Boot
  - Spring Data JPA
  - Spring Web
- **Base de Datos:** PostgreSQL
- **API:** Gutendex.

## Configuración 🚀

### Paso 1: Clonar el repositorio

Clona el repositorio en tu maquina local:

```
git clone https://github.com/GabrielLopez10/challenge-literatura
cd challenge-literatura
```

### Paso 2: Configurar la base de datos

Crea una base de datos PostgreSQL llamada `liteAlura` (o el nombre que prefieras). 
Luego, configura las credenciales en el archivo `application.properties` ubicado en `src/main/resources`:

```
spring.application.name=litealura
spring.datasource.url=jdbc:postgresql://${DB_HOST}/litealura
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=org.postgresql.Driver
hibernate.dialect=org.hibernate.dialect.HSQLDialect

spring.jpa.hibernate.ddl-auto=update

spring.jpa.show-sql=true

spring.jpa.properties.hibernate.format_sql=true
```
la variables de entorno estan configurada en el propio Intellij, si lo prefieres podes incluirla
en el propio sistema.

### Paso 3: Compilar y ejecutar el proyecto en tu terminal ▶️

Ejecutar el siguiente comando para empaquetar y ejecutar la aplicación en un solo paso:

```
mvn spring-boot:run
```

o si lo prefieres podes ejecutarlo directo en el IntelliJ.

## Capturas de Pantalla 📸
![menu](https://res.cloudinary.com/dfzw74nlk/image/upload/v1732844109/catalogo%20de%20libros%20screenshots/s03dxmmuq3dbsb12ruwf.png)
![lista 1](https://res.cloudinary.com/dfzw74nlk/image/upload/v1732844109/catalogo%20de%20libros%20screenshots/j7p4fi3dhimbjwqnde4l.png)
![lista 2](https://res.cloudinary.com/dfzw74nlk/image/upload/v1732844109/catalogo%20de%20libros%20screenshots/bc5bzfp8hlyw9fsbtwpe.png)
![lista 3](https://res.cloudinary.com/dfzw74nlk/image/upload/v1732844109/catalogo%20de%20libros%20screenshots/xxgtz5dsm4pu3oerasun.png)
![lista 4](https://res.cloudinary.com/dfzw74nlk/image/upload/v1732844109/catalogo%20de%20libros%20screenshots/zf5ljf6yniwztktvfhir.png)
