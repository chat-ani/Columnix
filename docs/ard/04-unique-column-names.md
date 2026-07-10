# ADR-04: Unique Column Names

## Status

Accepted

## Context

Duplicate column names introduce ambiguity during lookup and transformation.

## Decision

Schema rejects duplicate column names.

Column names are case-sensitive.

## Consequences

Advantages

- Fast lookup
- Clear API
- Predictable behavior

Trade-offs

Users must rename duplicate columns before creating a DataFrame.