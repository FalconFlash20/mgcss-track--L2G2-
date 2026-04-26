# Refactor Notes

## Problema 1 – Lambdas con múltiples invocaciones en assertThrows

### Descripción
En varios tests se utilizaban lambdas dentro de `assertThrows` que contenían más de una invocación de método.

### Código antes

```java
assertThrows(IllegalArgumentException.class, () -> {
    s.asignarTecnico(tecnicoInactivo());
});
```

### Análisis
La lambda contiene más de una llamada:
- `tecnicoInactivo()`
- `s.asignarTecnico(...)`

Esto genera ambigüedad sobre qué método lanza la excepción y produce un warning de Sonar. Además, reduce la claridad del test.

### Refactor aplicado
Se extrae la creación del objeto fuera de la lambda, dejando una única invocación dentro de la misma.

### Código después

```java
Tecnico t = tecnicoInactivo();

assertThrows(IllegalArgumentException.class, () -> {
    s.asignarTecnico(t);
});
```

### Resultado
- La lambda contiene una única invocación
- Se elimina el warning de Sonar
- Mejora la claridad del test
- No se modifica el comportamiento

### Casos afectados
Este refactor se ha aplicado en los siguientes métodos:
- asignaTecnicoInactivo()
- noAsignarTecnicoSiYaTieneUno()
- noAsignarTecnicoSiSolicitudCerrada()
- noCrearSolicitudSinEstado()
- noCrearSolicitudSinFecha()
- noCrearSolicitudConFechaFutura()
- errorClienteNull()

---

## Problema 2 – Duplicación de tests

### Descripción
Existían dos tests que validaban el mismo comportamiento:
- cambiarSolicitudAbiertaAEnProceso()
- iniciarProcesoCorrectamente()

### Análisis
Ambos métodos comprobaban la transición de estado de ABIERTA a EN_PROCESO, lo que supone duplicación de lógica de test.

### Refactor aplicado
Se elimina uno de los métodos duplicados, manteniendo un único test representativo.

### Resultado
- Eliminación de duplicación
- Mejora de la mantenibilidad
- Reducción de código redundante

---
