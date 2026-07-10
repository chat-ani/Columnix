# ADR-07: Non-Generic Column API

## Status

Accepted

## Context

A generic Column<T> API introduces boxing, wildcard complexity and runtime casting during DataFrame operations.

## Decision

Do not make Column generic.

Instead, create specialized implementations for each supported data type.

## Consequences

Advantages

- Cleaner API
- Better JVM optimization
- Primitive storage
- Lower memory overhead

Trade-offs

Additional implementation classes