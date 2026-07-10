# ADR-06: Abstract Column Design

## Status

Accepted

## Context

Different data types require specialized storage and behavior.

Sharing common functionality avoids duplication.

## Decision

Column is an abstract base class.

Concrete implementations include:

- IntColumn
- LongColumn (future)
- DoubleColumn (future)
- BooleanColumn (future)
- StringColumn (future)

## Consequences

Advantages

- Shared validation
- Extensible design
- Type-specific optimizations