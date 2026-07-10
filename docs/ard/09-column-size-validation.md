# ADR-09: Column Size Validation

## Status

Accepted

## Context

Every DataFrame represents a rectangular table.

Every column must contain exactly the same number of rows.

## Decision

Validate column lengths during DataFrame construction.

Reject inconsistent input by throwing ColumnSizeMismatchException.

## Consequences

Advantages

- Guaranteed table consistency
- Simpler algorithms
- Strong invariants

Trade-offs

Small validation cost during construction.