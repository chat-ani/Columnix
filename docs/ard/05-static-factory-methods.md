# ADR-05: Static Factory Methods

## Status

Accepted

## Context

Constructors become difficult to read as APIs evolve.

Factory methods provide meaningful names and allow future optimizations.

## Decision

Prefer static factory methods over public constructors.

Examples

DataFrame.of(...)

Schema.of(...)

IntColumn.of(...)

## Consequences

Advantages

- Readable API
- Encapsulation of validation
- Future caching opportunities
- Flexible object creation