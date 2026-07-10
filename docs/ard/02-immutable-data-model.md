# ADR-02: Immutable Data Model

## Status

Accepted

## Context

Mutable data structures introduce synchronization issues, accidental side effects and unpredictable behavior.

## Decision

The following core components are immutable.

- DataFrame
- Schema
- Column

Every transformation returns a new object.

## Consequences

Advantages

- Thread-safe by design
- Easier debugging
- Functional programming style
- Safe object sharing

Trade-offs

- Additional allocations
- Slightly higher memory usage