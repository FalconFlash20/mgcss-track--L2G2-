# MEMORIA TÉCNICA DEL PROYECTO MGCSS-TRACK

## Mantenimiento y Gestión del Cambio en Sistemas Software

### Curso 2025/2026

**Autores**

* Alejandro Martín Correa
* Francisco Jossué López Fiestas

---

# 1. Introducción

## 1.1 Objetivo del proyecto

El proyecto MGCSS-Track tiene como objetivo desarrollar una plataforma para la gestión de solicitudes de servicio técnico que permita registrar incidencias, asignar técnicos, controlar estados, realizar seguimiento del ciclo de vida de las solicitudes y obtener métricas operativas del sistema.
El desarrollo se ha realizado siguiendo una metodología incremental basada en calidad software, pruebas automatizadas, integración continua y gestión del cambio.
A diferencia de proyectos centrados únicamente en la implementación funcional, esta práctica persigue simular la evolución real de un producto software sometido a cambios continuos de requisitos, control de calidad y procesos de despliegue profesionales.

---

# 2. Arquitectura del sistema

## 2.1 Decisión arquitectónica

Desde las primeras sesiones se decidió adoptar una arquitectura por capas con separación explícita de responsabilidades.
La estructura final quedó organizada de la siguiente forma:

```text
com.mgcss
├── api
│   ├── controller
│   └── dto
├── domain
├── service
├── infrastructure
│   └── persistence
```

Esta decisión se tomó para:

* Reducir acoplamiento.
* Facilitar mantenimiento futuro.
* Permitir evolución independiente de cada capa.
* Cumplir las restricciones arquitectónicas definidas en el enunciado.

## 2.2 Responsabilidad de cada capa

### Domain

Contiene las entidades y reglas de negocio.
Entidades principales:

* Solicitud
* Cliente
* Tecnico

Toda la lógica de negocio crítica se implementó aquí.

Ejemplos:

* Cerrar solicitudes.
* Reabrir solicitudes.
* Asignación de técnicos.
* Gestión de SLA.
* Historial de estados.

### Service

Coordina los casos de uso del sistema.
Se decidió mantener los servicios ligeros, delegando la lógica de negocio al dominio para evitar anemias del modelo.
### Infrastructure

Implementa la persistencia mediante Spring Data JPA.

### API

Expone la funcionalidad mediante endpoints REST y DTOs.

---

# 3. Entrega 1 – Cimientos y Pipeline

## 3.1 Estrategia de ramas

Se adoptó desde el inicio la estrategia:

```text
main
feature/*
```

Motivos:

* Protección de la rama principal.
* Integración mediante Pull Requests.
* Trazabilidad de cambios.

Todas las funcionalidades se desarrollaron en ramas feature independientes.

## 3.2 Convención de commits

Se utilizaron Conventional Commits:

```text
feat:
fix:
test:
docs:
refactor:
chore:
```

Ventajas:

* Historial comprensible.
* Generación automática de Release Notes.
* Mejor trazabilidad.

## 3.3 Integración Continua

Se configuró GitHub Actions para:

* Compilar el proyecto.
* Ejecutar tests.
* Validar Pull Requests.

La principal decisión fue automatizar la validación desde las primeras sesiones para detectar errores de integración lo antes posible.

---

# 4. Entrega 2 – Calidad Estática y Dominio

## 4.1 Integración de SonarCloud

Se integró SonarCloud en el pipeline.
Objetivos:
* Control de cobertura.
* Detección de bugs.
* Identificación de deuda técnica.
* Seguimiento de complejidad.

## 4.2 Aplicación de TDD

Se aplicó el ciclo:
1. RED
2. GREEN
3. REFACTOR

Las primeras reglas implementadas fueron:

### Cierre de solicitudes

Una solicitud únicamente puede cerrarse si se encuentra en estado EN_PROCESO.

### Asignación de técnicos

Únicamente puede asignarse un técnico activo.

## 4.3 Decisión sobre la lógica de negocio

Se decidió que las validaciones críticas residieran en la entidad Solicitud.

Razones:

* Mayor cohesión.
* Protección de invariantes.
* Menor duplicación.
* Facilidad para la evolución posterior.

---

# 5. Entrega 3 – Servicios y Persistencia

## 5.1 Introducción de la capa de servicios

Se creó SolicitudService.
Su responsabilidad es:
* Orquestar casos de uso.
* Gestionar repositorios.
* Coordinar operaciones.

Se evitó introducir lógica compleja en esta capa.

## 5.2 Uso de Mockito

Se utilizaron mocks para:
* Aislar dependencias.
* Validar interacciones.
* Reducir tiempos de ejecución.

## 5.3 Persistencia con JPA

Se decidió utilizar las propias entidades de dominio como entidades persistentes.
Ventajas:
* Menor complejidad.
* Menos clases duplicadas.
* Desarrollo más rápido.

Inconveniente:
* Mayor acoplamiento entre dominio y persistencia.

## 5.4 Base de datos H2

Se utilizó H2 en memoria para:
* Tests reproducibles.
* Configuración mínima.
* Integración sencilla con Spring Boot.

---

# 6. Entrega 4 – Refactorización y Gestión del Cambio

## 6.1 Refactorización guiada por métricas

Se analizaron métricas de SonarCloud para detectar:
* Complejidad elevada.
* Duplicación.
* Code Smells.

Las mejoras realizadas incluyeron:
* Extracción de métodos.
* Simplificación de validaciones.
* Eliminación de código repetido.

## 6.2 Gestión del cambio

Durante esta entrega se introdujo un cambio importante en el núcleo del dominio.

### Cambio 1: Reapertura de solicitudes

Nueva regla:
Una solicitud cerrada puede volver a estado EN_PROCESO.
Se añadió:
```java
reabrir()
```

### Cambio 2: Historial de estados

Se incorporó:
```java
List<EstadoSolicitud> historialEstados
```
Cada transición queda registrada automáticamente.

## 6.3 Persistencia del historial

Se decidió persistir el histórico utilizando:
```java
@ElementCollection
```
Motivos:
* Simplicidad.
* Menor sobrecarga.
* No requerir una entidad adicional.

---

# 7. Entrega 5 – API y Despliegue

## 7.1 API REST

Se desarrolló una API REST completa.
Endpoints principales:
* Crear solicitud
* Consultar solicitud
* Listar solicitudes
* Asignar técnico
* Cambiar estado
* Reabrir solicitud

## 7.2 Uso de DTOs

Se creó:
* SolicitudRequestDTO
* SolicitudResponseDTO

### Decisión de diseño

Se eligió mapeo manual dentro de los controladores.
Motivos:
* Evitar dependencias externas.
* Mayor control.
* Simplicidad.

## 7.3 Swagger/OpenAPI

Toda la API fue documentada utilizando OpenAPI.
Beneficios:
* Pruebas rápidas.
* Contrato visible.
* Facilita auditorías.

## 7.4 Containerización con Docker

Se construyó una imagen Docker para garantizar:

* Portabilidad entre entornos.
* Reproducibilidad de ejecuciones.
* Facilidad de despliegue.
* Independencia de la configuración local del desarrollador.

Docker permitió ejecutar exactamente la misma aplicación en desarrollo, integración continua y producción, reduciendo problemas derivados de diferencias de configuración entre sistemas.

Las instrucciones completas de construcción y ejecución se detallan posteriormente en el apartado de Instalación y Ejecución.
---

# 8. Entrega Final – Release Management

## 8.1 Versionado semántico

Se utilizó:
```text
MAJOR.MINOR.PATCH
```
Ejemplos:
```text
v1.0.0
v1.1.0
```
## 8.2 Automatización de releases

Se implementó un workflow específico:
```text
release.yml
```
Responsabilidades:
* Generar artefactos.
* Ejecutar tests.
* Verificar Sonar.
* Crear Releases automáticamente.

## 8.3 Docker Hub

La imagen Docker se publica de forma versionada para garantizar trazabilidad entre:
```text
Commit → Tag → Release → Imagen Docker
```
---

# 9. Extensiones Opcionales Implementadas

## 9.1 GitHub Projects
Se utilizó GitHub Projects para:
* Gestión de iteraciones.
* Seguimiento de tareas.
* Organización de Issues.

## 9.2 Métricas SLA automatizadas

Se implementaron:
* SLA calculado automáticamente.
* SLA cumplido.
* SLA vencido.

Reglas:
* Cliente PREMIUM → 48 horas.
* Cliente STANDARD → 96 horas.

## 9.3 Dashboard de métricas

Se desarrolló una interfaz utilizando Thymeleaf.
Métricas mostradas:
### Solicitudes
* Total
* Abiertas
* En proceso
* Cerradas

### Clientes
* Total
* Premium
* Standard
* Bloqueados
* Verificados

### Técnicos
* Total
* Activos
* Inactivos
* Hardware
* Software

---

# 10. Estrategia de Testing
## Tests Unitarios
Objetivo:
* Validar reglas de negocio.
Herramientas:
* JUnit 5

## Tests de Servicio

Objetivo:
* Verificar coordinación entre componentes.
Herramientas:
* Mockito

## Tests de Integración

Objetivo:
* Validar persistencia real.
Herramientas:
* H2
* DataJpaTest

## Tests de Controlador

Objetivo:
* Validar API REST.
Herramientas:
* MockMvc
* WebMvcTest

---

# 11. Métricas de Calidad

Las métricas monitorizadas durante el proyecto fueron:

* Coverage
* Bugs
* Vulnerabilities
* Code Smells
* Duplicación
* Technical Debt
* Maintainability Rating

El objetivo principal fue mantener el Quality Gate siempre en verde.
## Estado de Calidad (SonarCloud)
### Resumen General
| Quality Gate | Cobertura | Mantenibilidad | Fiabilidad | Seguridad |
| :---: | :---: | :---: | :---: | :---: |
| [![Quality gate](https://sonarcloud.io/api/project_badges/quality_gate?project=FalconFlash20_mgcss-track--L2G2-)](https://sonarcloud.io/summary/new_code?id=FalconFlash20_mgcss-track--L2G2-) | [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=FalconFlash20_mgcss-track--L2G2-&metric=coverage)](https://sonarcloud.io/summary/new_code?id=FalconFlash20_mgcss-track--L2G2-) | [![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=FalconFlash20_mgcss-track--L2G2-&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=FalconFlash20_mgcss-track--L2G2-) | [![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=FalconFlash20_mgcss-track--L2G2-&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=FalconFlash20_mgcss-track--L2G2-) | [![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=FalconFlash20_mgcss-track--L2G2-&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=FalconFlash20_mgcss-track--L2G2-) |

### Detalle de Métricas
* **Deuda Técnica:** [![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=FalconFlash20_mgcss-track--L2G2-&metric=sqale_index)](https://sonarcloud.io/summary/new_code?id=FalconFlash20_mgcss-track--L2G2-)
* **Code Smells:** [![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=FalconFlash20_mgcss-track--L2G2-&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=FalconFlash20_mgcss-track--L2G2-)
* **Bugs:** [![Bugs](https://sonarcloud.io/api/project_badges/measure?project=FalconFlash20_mgcss-track--L2G2-&metric=bugs)](https://sonarcloud.io/summary/new_code?id=FalconFlash20_mgcss-track--L2G2-)
* **Vulnerabilidades:** [![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=FalconFlash20_mgcss-track--L2G2-&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=FalconFlash20_mgcss-track--L2G2-)
* **Duplicidad:** [![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=FalconFlash20_mgcss-track--L2G2-&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=FalconFlash20_mgcss-track--L2G2-)
* **Líneas de Código:** [![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=FalconFlash20_mgcss-track--L2G2-&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=FalconFlash20_mgcss-track--L2G2-)

## 11.1 Análisis de la Deuda Técnica

Durante el desarrollo del proyecto se ha utilizado SonarCloud como herramienta principal para la identificación y seguimiento de deuda técnica. El análisis continuo de las métricas permitió detectar problemas relacionados con la mantenibilidad, legibilidad y calidad general del código, que fueron corregidos progresivamente mediante tareas de refactorización.

### Problemas Detectados
Uno de los problemas más frecuentes detectados por SonarCloud fue el uso de expresiones lambda complejas en varios tests unitarios. Estas expresiones contenían múltiples invocaciones de métodos dentro de bloques `assertThrows`, dificultando la identificación precisa del origen de las excepciones. Para resolver este problema se extrajo la preparación de objetos fuera de las lambdas, manteniendo una única invocación dentro de cada aserción.
También se identificaron casos de duplicación de código en los tests.
Otro aspecto detectado fue la existencia de código comentado y fragmentos obsoletos que permanecían en el proyecto tras diversas iteraciones de desarrollo. Estos elementos fueron eliminados para mejorar la legibilidad y reducir los Code Smells reportados por SonarCloud.
Asimismo, la clase `SolicitudResponseDTO` presentaba inicialmente un constructor con un número elevado de parámetros, lo que dificultaba su mantenimiento y aumentaba el riesgo de errores durante la creación de objetos. Para solucionar este problema se implementó el patrón Builder, obteniendo una construcción más segura, legible y mantenible de los DTOs.

### Gestión de la Deuda Técnica
La estrategia adoptada durante el proyecto consistió en corregir de forma temprana los problemas detectados por las herramientas de análisis estático para evitar la acumulación de deuda técnica a largo plazo. Esta política permitió mantener una base de código estable y preparada para la incorporación de nuevas funcionalidades sin incrementar significativamente la complejidad del sistema.

Especial relevancia tuvo la incorporación de la funcionalidad de reapertura de solicitudes y el historial de estados. Antes de implementar este cambio se realizó un análisis de impacto para identificar las partes afectadas del sistema y minimizar los riesgos asociados a la modificación de reglas de negocio ya existentes.

### Situación Final
Tras las distintas tareas de refactorización realizadas a lo largo del proyecto, se consiguió reducir significativamente la deuda técnica identificada durante las primeras iteraciones. El uso continuado de SonarCloud permitió mantener bajo control aspectos como la duplicación de código, los Code Smells, la complejidad de determinados métodos y la cobertura de pruebas.

Como resultado, la versión final presenta una arquitectura más mantenible, un código más limpio y una mejor preparación para futuras evoluciones del sistema, cumpliendo los criterios de calidad establecidos durante el desarrollo del proyecto.

---

# 12. Problemas Encontrados y Soluciones

## Gestión del cambio

Problema:
La reapertura de solicitudes afectó al modelo principal.
Solución:
Introducción de historial y refactorización incremental.

## Cobertura Sonar
Problema:
Métodos nuevos reducían cobertura.
Solución:
Incorporación de tests específicos para nuevas ramas de ejecución.

## Dockerización

Problema:
Diferencias entre entorno local y contenedor.
Solución:
Uso de configuración externalizada y pruebas completas del contenedor.

---

# 13. Instalación y Ejecución
Con el objetivo de garantizar la reproducibilidad del proyecto, se documenta el procedimiento completo de instalación y ejecución en un entorno local.

## Requisitos Previos
Para ejecutar la aplicación es necesario disponer de:
* Java 17
* Git
* Gradle (o utilizar el Gradle Wrapper incluido en el proyecto)
* Docker (opcional)
---
## Clonar el repositorio
```bash
git clone https://github.com/FalconFlash20/mgcss-track--L2G2-.git
```
---
## Acceder al proyecto
```bash
cd mgcss-track--L2G2-/P1
```
---
## Ejecutar la aplicación
Utilizando el Gradle Wrapper incluido en el proyecto:
```bash
./gradlew bootRun
```
Una vez iniciada la aplicación, estará disponible en:
```text
http://localhost:8080
```
---
## Documentación Swagger
La documentación completa de la API REST puede consultarse desde:
```text
http://localhost:8080/swagger-ui.html
```
---
## Dashboard de Métricas
La aplicación incorpora un dashboard desarrollado con Thymeleaf para visualizar métricas operativas del sistema.
Disponible en:
```text
http://localhost:8080/dashboard
```
---
## Ejecución mediante Docker
También es posible ejecutar la aplicación mediante contenedores Docker.
### Construcción de la imagen
```bash
docker build -t mgcss-track .
```
### Ejecución del contenedor
```bash
docker run -p 8080:8080 mgcss-track
```
Una vez desplegado el contenedor, la aplicación quedará disponible en:
```text
http://localhost:8080
```
---
## Ejecución desde una Release
El proyecto incorpora un proceso automatizado de generación de Releases mediante GitHub Actions.
Cada versión publicada genera automáticamente:
* Artefacto JAR ejecutable.
* Release Notes.
* Imagen Docker versionada.
Para ejecutar una versión liberada basta con descargar el artefacto generado y lanzar:
```bash
java -jar P1-0.0.1-SNAPSHOT.jar
```
Este procedimiento garantiza la trazabilidad completa entre:
```text
Commit → Tag → Release → Artefacto
```
y permite reproducir exactamente cualquier versión publicada del sistema.

---

# 14. Conclusiones

El proyecto ha permitido aplicar de forma práctica conceptos fundamentales de ingeniería software:
* TDD.
* Gestión del cambio.
* Integración continua.
* Calidad estática.
* Persistencia.
* APIs REST.
* Docker.
* Release Management.
