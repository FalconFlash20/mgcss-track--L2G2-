# MGCSS - Sistema de Gestión de Incidencias
## Tecnologías y Requisitos

* **Java:** 17 (LTS)
* **Framework:** Spring Boot 3.x
* **Gestor de Dependencias:** Gradle
* **Persistencia:** Spring Data JPA / Hibernate
* **Base de Datos:** H2 (In-memory) / Configurable para PostgreSQL o MySQL
* **Arquitectura:** Clean Architecture (Dominio, Servicio, API, Infraestructura)

## Estructura del Proyecto

```text
com.mgcss
├── api
│   ├── Controller      # Endpoints REST (Cliente, Tecnico, Solicitud)
│   └── DTO             # Objetos de transferencia (Request/Response Records/Classes)
├── domain              # Entidades y lógica de negocio pura (Repository Interfaces)
├── infrastructure
│   └── persistence     # Implementaciones JPA de los repositorios (Doble herencia)
└── service             # Servicios de aplicación y coordinación de lógica
```
### Gestión de Datos con DTO (Data Transfer Objects)
#### Implementación Técnica
Se han definido clases de transferencia específicas para cada operación de la API:
* **RequestDTOs:** (ej. `SolicitudRequestDTO`) Utilizados para capturar y validar los datos de entrada del cliente.
* **ResponseDTOs:** (ej. `SolicitudResponseDTO`) Utilizados para estructurar la información de salida, transformando las entidades JPA antes de ser enviadas como JSON.
#### Estrategia de Mapeo: Métodos Privados de Conversión
A diferencia de otros proyectos que utilizan librerías externas (como MapStruct o ModelMapper), en este sistema se ha optado por un **mapeo manual mediante métodos privados** dentro de cada `Controller`.
**¿Por qué esta decisión?**
1.  **Control Total y Transparencia:** Al escribir manualmente el método, tenemos un control absoluto sobre qué campos se transforman y cómo. Esto facilita la depuración.
2.  **Reducción de Dependencias:** Evitamos sobrecargar el archivo `build.gradle` con librerías adicionales, manteniendo el proyecto ligero y con un tiempo de compilación menor.
3.  **Simplicidad en el Dominio:** El mapeo manual es directo y no requiere configuraciones complejas de mapeadores externos.
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
