# MGCSS - Sistema de Gestión de Incidencias

## Descripción

MGCSS es una aplicación desarrollada con Spring Boot para la gestión de incidencias técnicas, permitiendo registrar solicitudes, asignar técnicos, gestionar estados, controlar SLA y realizar seguimiento completo del ciclo de vida de cada incidencia.

El proyecto ha sido desarrollado siguiendo principios de Clean Architecture, TDD, Integración Continua y Gestión del Cambio.

---

# Tecnologías Utilizadas

| Tecnología        | Versión |
| ----------------- | ------- |
| Java              | 17      |
| Spring Boot       | 3.x     |
| Gradle            | 8.x     |
| Spring Data JPA   | Sí      |
| Hibernate         | Sí      |
| H2 Database       | Sí      |
| Swagger / OpenAPI | Sí      |
| Docker            | Sí      |
| GitHub Actions    | Sí      |
| SonarCloud        | Sí      |
| Thymeleaf         | Sí      |

---

# Arquitectura

El sistema está organizado siguiendo una separación clara de responsabilidades:

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

## Capas

### Domain

Contiene las entidades y reglas de negocio.

Ejemplos:

* Solicitud
* Cliente
* Tecnico

### Service

Orquesta casos de uso y coordina operaciones.

### Infrastructure

Implementa persistencia mediante Spring Data JPA.

### API

Expone la funcionalidad mediante endpoints REST.

---

# Funcionalidades Implementadas

## Gestión de Solicitudes

* Crear solicitud
* Consultar solicitud
* Listar solicitudes
* Asignar técnico
* Cambiar estado
* Reabrir solicitud

## Gestión del Cambio

Implementación del cambio solicitado durante la Entrega 4:

* Reapertura de solicitudes cerradas
* Historial completo de estados
* Persistencia del histórico mediante JPA

## Gestión SLA

Cálculo automático del SLA

### Cliente Premium

48 horas

### Cliente Standard

96 horas

Además se calculan automáticamente:

* SLA cumplidos
* SLA vencidos

---

# API REST

Endpoints principales:

| Método | Endpoint                             |
| ------ | ------------------------------------ |
| POST   | /api/solicitudes                     |
| GET    | /api/solicitudes                     |
| GET    | /api/solicitudes/{id}                |
| PUT    | /api/solicitudes/{id}/asignarTecnico |
| PUT    | /api/solicitudes/{id}/cambiarEstado  |
| PATCH  | /api/solicitudes/{id}/reabrir        |

---

# Documentación Swagger

Disponible en:

```text
http://localhost:8080/swagger-ui.html
```

Incluye:

* Documentación OpenAPI
* DTOs documentados
* Códigos HTTP
* Casos de uso de prueba

---

# Persistencia

Base de datos:

* H2 Database

Tecnologías:

* Spring Data JPA
* Hibernate

Se utilizan repositorios JPA para:

* Solicitud
* Cliente
* Tecnico

---

# Testing

El proyecto incluye:

## Tests Unitarios

* Dominio
* Servicios

Herramientas:

* JUnit 5
* Mockito

## Tests de Integración

* Persistencia JPA
* H2 Database

## Tests de Controlador

* MockMvc
* WebMvcTest

Cobertura validada mediante SonarCloud.

---

# Calidad del Código

## SonarCloud

Se utilizan Quality Gates para garantizar:

* Cobertura mínima
* Ausencia de bugs críticos
* Ausencia de vulnerabilidades
* Control de deuda técnica

Badges SonarCloud:

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
---

# Integración Continua

GitHub Actions ejecuta automáticamente:

* Compilación
* Tests
* Análisis Sonar

Cada Pull Request debe superar el pipeline antes de ser integrado.

---

# Release Management

Versionado semántico:

```text
MAJOR.MINOR.PATCH
```

Ejemplos:

```text
v1.0.0
v1.1.0
```

Proceso automatizado:

1. Merge a main
2. Creación de tag
3. Push del tag
4. Ejecución de release.yml
5. Generación automática de Release
6. Publicación de artefactos

---

# Docker

Construcción:

```bash
docker build -t mgcss-track .
```

Ejecución:

```bash
docker run -p 8080:8080 mgcss-track
```

---

# Dashboard de Métricas

Implementado mediante Thymeleaf.

URL:

```text
http://localhost:8080/dashboard
```

Métricas disponibles:

## Solicitudes

* Total solicitudes
* Abiertas
* En proceso
* Cerradas

## Clientes

* Total clientes
* Premium
* Standard
* Bloqueados
* Verificados

## Técnicos

* Total técnicos
* Activos
* Inactivos
* Hardware
* Software

---

# Gestión de Proyecto

Se ha utilizado GitHub Projects para:

* Gestión de iteraciones
* Seguimiento de Issues
* Control de tareas
* Trazabilidad de cambios

---

# Estrategia de Ramas

```text
main
feature/*
```

Todas las funcionalidades se desarrollan mediante Pull Request.

---

# Convenciones de Commits

Se emplean Conventional Commits:

```text
feat:
fix:
refactor:
test:
docs:
chore:
```

---

# Autores
* López Fiestas, Francisco Jossué
* Martín Correa, Alejandro
