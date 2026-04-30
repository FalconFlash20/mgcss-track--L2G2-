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
## Problema 3 – Refactorización en constructores en tests

### Descripción
En varios tests, dentro de los bloques `assertThrows` se creaban instancias de objetos que incluían llamadas a métodos..

### Código antes

```java
assertThrows(IllegalArgumentException.class, () -> {
    new Solicitud("desc", null, LocalDateTime.now(), cliente());
});
```

### Análisis
La presencia de múltiples invocaciones de métodos auxiliares dentro del bloque oscurece la causa raíz del fallo y viola la regla de responsabilidad única en las expresiones lambda.

### Refactor aplicado
Se separó la preparación de los argumentos en variables locales previas a la aserción, manteniendo el constructor como la única acción dentro de la lambda.
### Código después

```java
LocalDateTime ahora = LocalDateTime.now();
Cliente c = cliente();

assertThrows(IllegalArgumentException.class, () -> {
    new Solicitud("desc", null, ahora, c);
});
```

### Resultado
- Se elimina la ambigüedad en la captura de excepciones
- Código más legible y alineado 

### Casos afectados
Este refactor se ha aplicado en los siguientes métodos:
- noCrearSolicitudSinEstado()
- noCrearSolicitudSinFecha()
- noCrearSolicitudConFechaFutura()
- errorClienteNull()

---
## Problema 4 – Lambdas innecesarios

### Descripción
Se ha detectado, un Code Smell donde se ha usado expresiones lambda para realizar una llamada a un método existente
### Código antes

```java
assertThrows(IllegalStateException.class, () -> c.ascenderCliente());
```

### Análisis
El uso de lambdas es innecesario cuando el único propósito es invocar un método existente. La sintaxis de referencia a métodos es más idiomática.

### Refactor aplicado
Se reemplazó la expresión lambda por una referencia a método utilizando el operador ::
### Código después

```java
assertThrows(IllegalStateException.class, c::ascenderCliente);
```

### Resultado
- Código más conciso y legible

### Casos afectados
Este refactor se ha aplicado en los siguientes métodos:
- noCerrarEnProceso()
- noInciarProcesoSiNoAbierta()
- errorAscenderSiYaEsPremium()
- ErrorAlDesbloqueaCuentaNoBloqueada()
- noCerrarSolicitudYaCerrada()
- noIniciarProcesoSiYaEstaEnProceso()
- noIniciarProcesoSiEstaCerrada()
- errorAscenderSiYaEsPremium()
- ErrorAlDesbloquearCuentaYaBloqueada()
- noCerrarSolicitudYaCerrada()
- activarYaActivo()
- desactivarTecnicoYaInactivo()

---
## Problema 5 – Variable local no usada

### Descripción
Se declaraban variables locales que no eran utilizadas dentro del test.

### Código antes

```java
assertThrows(IllegalArgumentException.class, () -> {
    Solicitud solicitud = new Solicitud("desc", null, LocalDateTime.now(), cliente());
});
```

### Análisis
La variable solicitud se declara pero no se utiliza posteriormente.Además, introduce ruido innecesario y puede confundir sobre si dicha variable tiene algún propósito en el test.

### Refactor aplicado
Se elimina la variable innecesaria, manteniendo únicamente la ejecución que provoca la excepción.

### Código después

```java
assertThrows(IllegalArgumentException.class,
    () -> new Solicitud("desc", null, LocalDateTime.now(), cliente())
);
```

### Resultado
- Eliminación de código muerto
- Código más directo y claro 

### Casos afectados
Este refactor se ha aplicado en los siguientes métodos:
- noCrearSolicitudSinEstado()
- noCrearSolicitudSinFecha()
- noCrearSolicitudConFechaFutura()
- errorClienteNull()

---

