# Release Notes

## Análisis Versión Actual

Versión actual: v1.0.0

Cambios recientes:

- Implementación API REST
- Documentación Swagger/OpenAPI
- Containerización de Docker
- Tests de controladores con Mock
- Tests unitario de dominio y servicio
- Tests de integración con persistencia JPA
- Mejora de la arquitectura mediante DTOs
- Gestión de estados y trazabilidad de solicitudes
- Preparación del pipeline de release y automatización

## Revisión del versionado semántico

Siguiente versión: v1.1.0

Justificación:

Los cambios realizados añaden nuevas funcionalidades compatibles con las versiones anteriores del sistema, sin romper comportamiento existente ni modificar contratos incompatibles.

Según Semantic Versioning:

- MAJOR → No aplica, ya que no existen cambios incompatibles.
- MINOR → Sí aplica, porque se han añadido nuevas funcionalidades importantes.
- PATCH → No es suficiente debido al alcance de las mejoras realizadas.
