# ADR-01: Column-Oriented Storage

## Status

Accepted

## Context

The project aims to build a high-performance analytical DataFrame library similar to pandas.

Analytical workloads frequently operate on entire columns rather than individual rows.

## Decision

Store data in a column-oriented layout.

Each Column owns its data independently.

A DataFrame is composed of multiple immutable columns.

## Consequences

Advantages

- Better cache locality for analytical operations
- Efficient vectorized execution in future versions
- Lower memory overhead for column-wise operations
- Easier implementation of filtering, aggregation and statistics

Trade-offs

- Row reconstruction is slightly more expensive.