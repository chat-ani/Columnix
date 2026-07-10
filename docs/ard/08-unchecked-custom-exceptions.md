# ADR-08: Unchecked Custom Exceptions

## Status

Accepted

## Context

Library users should receive meaningful exceptions without being forced to catch recoverable programming errors.

## Decision

Create custom exceptions extending DataFrameException.

DataFrameException extends RuntimeException.

## Consequences

Advantages

- Better error messages
- Domain-specific exceptions
- Cleaner application code

Examples

DuplicateColumnException

ColumnNotFoundException

ColumnSizeMismatchException