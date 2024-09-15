<table align= "center">
  <tr>
    <td><img src="https://github.com/user-attachments/assets/23888328-f831-4250-ac0b-b3e0db55a89c" alt="Java" width="50" /></td>
    <td><img src="https://github.com/user-attachments/assets/c6b75730-c3cf-41a1-82fb-d40e17bfd097" alt="Spring Boot" width="50" /></td>
    <td><img src="https://github.com/user-attachments/assets/d8081359-309e-4e88-bba4-b5238536c88e" alt="Kubernetes" width="80" /></td>
    <td><img src="https://github.com/user-attachments/assets/aa5fb147-5b81-416e-b656-15e28ad566b9" alt="Docker" width="50" /></td>
    <td><img src="https://github.com/user-attachments/assets/f3c6c52e-9c3d-419f-b0a0-9c34d01c3728" alt="MySQL" width="50" /></td>
    <td><img src="https://github.com/user-attachments/assets/bdca9eef-307e-4309-822c-95edebe52906" alt="PostgreSQL" width="50" /></td>
    <td><img src="https://github.com/user-attachments/assets/114f2c7f-7cda-4494-ad3e-2a03fbdadab4" alt="JWT" width="80" /></td>
  </tr>
</table>

# API REST para Gestión de Cursos y Usuarios

<p>
Este proyecto es una API REST desarrollada para gestionar cursos y usuarios, implementando operaciones CRUD (Crear, Leer, Actualizar, Eliminar). La API está desplegada usando Docker para la contenerización y Kubernetes para la orquestación, garantizando escalabilidad y alta disponibilidad.
</p>

## Tecnologías Utilizadas

- Java: Lenguaje de programación para desarrollar el backend.
- Spring Boot: Framework para crear la API REST.
- MySQL: Base de datos relacional utilizada para almacenar la información de los usuarios.
- PostgreSQL: Base de datos relacional alternativa para almacenar información de los cursos.
- Docker: Herramienta para contenerización de la aplicación.
- Kubernetes: Para la orquestación y despliegue escalable en múltiples entornos.
- OAuth2: Protocolo para la autorización y autenticación segura.
- JWT (JSON Web Tokens): Utilizado para la autenticación basada en tokens.

## Funcionalidades

- Usuarios: Registro, edición, eliminación y consulta de usuarios.
- Cursos: Gestión de cursos, con funcionalidades para añadir, actualizar, eliminar y obtener información de los cursos.
- Autenticación: Sistema de autenticación basado en JWT (JSON Web Tokens) y OAuth2.
- Validaciones: Validación de datos del lado del servidor para garantizar la integridad de los datos.

## Requisitos Previos

- Docker instalado en tu sistema.
- Kubernetes configurado para la orquestación.
- Driver de Docker para ejecutar Kubernetes.

## Instrucciones de Uso

<p >
- Clonar codigo
</p>

````
git clone https://github.com/Luchan63/API-para-Gesti-n-de-Cursos-y-Usuarios-en-Docker-Kubernetes.git
cd nombre-del-proyecto
````
<p>
Construir la imagen con Docker
</p>

````
docker build -t nombre-del-proyecto .
````
<p>
Desplegar con Kubernetes
</p>

````
kubectl apply -f deployment.yml
````
## Autor
**Luis Anibal Figuereo Ali**

Desarrollador Web Junior
