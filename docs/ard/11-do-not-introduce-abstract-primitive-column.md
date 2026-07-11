# ADR-11: Do Not Introduce AbstractPrimitiveColumn

- **Status:** Accepted
- **Date:** 2026-07-12
- **Authors:** Anirban Chatterjee

## Context

JavaDataFrame currently provides the following primitive column implementations:

- `IntColumn`
- `LongColumn`
- `DoubleColumn`

These classes share a significant amount of implementation, including:

- immutable storage
- defensive copying
- factory methods
- size calculation
- equality
- hash code generation
- string representation

At first glance, this duplication suggests introducing an `AbstractPrimitiveColumn` base class.

Before proceeding, the design was evaluated from the perspectives of type safety, maintainability, API clarity, and Java language limitations.

---

## Decision

**An `AbstractPrimitiveColumn` will not be introduced in Version 1.**

Primitive column implementations will continue to inherit directly from `Column` and provide their own concrete implementations.

This decision intentionally favors explicit implementations over premature abstraction.

---

## Rationale

### Java generics do not support primitive types

Primitive arrays (`int[]`, `long[]`, `double[]`, etc.) cannot be represented using Java generics.

Any attempt to create a generic primitive column abstraction would require:

- using `Object`
- introducing unsafe casts
- boxing primitive values
- relying on reflection

Each of these approaches reduces type safety, performance, or readability.

---

### The duplicated code is small and stable

Most duplicated methods are simple and self-contained.

Examples include:

- `size()`
- `get(int index)`
- `values()`
- `equals(Object)`
- `hashCode()`
- `toString()`

The current duplication is minimal and does not introduce significant maintenance overhead.

---

### Favor readability over abstraction

Explicit implementations are easier to understand and debug.

Each primitive column clearly exposes its storage type and behavior without requiring additional abstraction layers.

This aligns with the project's philosophy of introducing abstractions only when they simplify the design rather than merely reducing line count.

---

### Version 1 prioritizes a stable public API

The primary goal of Version 1 is to establish a clean, predictable, and immutable column API.

Introducing additional inheritance solely to remove implementation duplication does not provide meaningful value to library users.

---

## Consequences

### Advantages

- Simple class hierarchy
- Strong type safety
- No boxing overhead
- Easy to understand implementations
- Straightforward debugging
- Minimal abstraction complexity

### Disadvantages

- Small amount of duplicated implementation
- Future primitive columns will initially repeat similar code

These disadvantages are considered acceptable given the benefits of clarity and maintainability.

---

## Future Considerations

This decision may be revisited in a future release if primitive columns begin sharing significant **behavior**, rather than merely implementation.

Examples include:

- statistical operations
    - `sum()`
    - `average()`
    - `min()`
    - `max()`
- slicing
- filtering
- copying
- aggregation
- vectorized operations

If substantial behavioral duplication emerges, introducing an `AbstractPrimitiveColumn` (or another suitable abstraction) should be reconsidered.

At that point, the abstraction should exist to share reusable behavior rather than simply reduce duplicated code.

---

## Alternatives Considered

### Generic `AbstractPrimitiveColumn<T>`

Rejected.

Java generics cannot model primitive types without boxing or additional complexity.

---

### Object-based storage

Rejected.

Using `Object` for primitive arrays sacrifices compile-time type safety and requires unchecked casts.

---

### Current explicit implementations

Accepted.

The current design provides the best balance of simplicity, readability, performance, and maintainability for Version 1.

---

## Related ADRs

- ADR-01: Column-Oriented Storage
- ADR-02: Immutable DataFrame Design
- ADR-10: Expression System Design