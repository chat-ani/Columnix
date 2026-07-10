# ADR-003: Preserve Column Order

## Status

Accepted

## Context

Most tabular datasets rely on deterministic column ordering.

Users expect exported CSV files, console output and serialization to preserve insertion order.

## Decision

Schema preserves the exact order in which columns are supplied.

## Consequences

- Deterministic output
- Consistent column indexing
- Improved interoperability