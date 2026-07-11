# ADR-12: Column System Design

- **Status:** Accepted
- **Date:** 2026-07-12

## Context

A DataFrame is fundamentally composed of columns. The column abstraction therefore serves as one of the core building blocks of the JavaDataFrame library.

The column system must satisfy the following requirements:

- Strong static typing.
- Immutability.
- Thread safety.
- High memory efficiency.
- Fast random access.
- Consistent API across all column types.
- Extensibility for future data types.

The design should also remain simple enough for Version 1 while providing a foundation for future execution, filtering, serialization, and analytical operations.

---

## Decision

### Abstract Base Class

All column implementations inherit from a common abstract class.

```java
public abstract class Column
```

The base class is responsible for:

- Column name validation.
- Data type ownership.
- Common metadata.
- Shared API contract.

Every concrete column must provide:

- `size()`
- `type()`
- `name()`

---

### Strongly Typed Columns

Each supported data type is represented by its own concrete immutable implementation.

Current Version 1 includes:

- `IntColumn`
- `LongColumn`
- `DoubleColumn`
- `BooleanColumn`
- `StringColumn`

Each implementation stores values using the most efficient underlying representation.

Examples:

```java
int[]
long[]
double[]
boolean[]
String[]
```

No boxing or wrapper collections are used.

---

### Immutability

All column implementations are immutable.

Construction performs defensive copying of the supplied arrays.

Methods exposing internal arrays also return defensive copies.

Example:

```java
this.values = values.clone();
```

and

```java
public int[] values() {
    return values.clone();
}
```

This prevents accidental modification of internal state.

---

### Validation

Validation responsibilities are centralized within `ValidationUtils`.

Common validation includes:

- non-null values
- non-blank names
- unique names
- non-null array elements (reference types)

This avoids duplicated validation logic throughout the library.

---

### Primitive Columns

Primitive columns use Java primitive arrays.

Advantages:

- minimal memory overhead
- no boxing
- improved cache locality
- fast random access

---

### Reference Columns

Reference-based columns (currently `StringColumn`) store immutable object references.

Current Version 1 rules:

- null elements are rejected
- empty strings are allowed
- blank strings are allowed
- whitespace is preserved exactly as provided

The library intentionally does not support missing values in Version 1.

---

### Equality

Column equality is structural.

Two columns are equal when:

- names are equal
- types are equal
- values are equal

Primitive arrays use:

```java
Arrays.equals(...)
```

Reference arrays also use:

```java
Arrays.equals(...)
```

---

### Thread Safety

All column implementations are thread-safe because:

- all fields are final
- no mutable internal state is exposed
- defensive copies are returned

No synchronization is required.

---

### API Consistency

Every concrete column exposes the same public API.

Example:

```java
Column.of(...)

column.get(index)

column.values()

column.size()

column.type()

column.name()
```

This provides a predictable developer experience regardless of the underlying data type.

---

### Deferred Abstractions

An intermediate abstraction such as:

```java
AbstractPrimitiveColumn
```

was intentionally rejected for Version 1.

Although primitive column implementations share similar code, the duplication is currently minimal and introducing another inheritance layer would increase architectural complexity without providing sufficient benefit.

This decision will be revisited if future implementations reveal substantial duplicated behavior.

---

## Consequences

### Advantages

- Strong static typing.
- Excellent runtime performance.
- Simple implementation.
- Easy to understand.
- Consistent public API.
- Safe immutable design.
- Easy future extension.

### Disadvantages

- Small amount of duplicated implementation across primitive columns.
- Separate implementation required for each supported data type.

These disadvantages are considered acceptable for Version 1.

---

## Future Considerations

Future versions may introduce:

- `FloatColumn`
- `ShortColumn`
- `ByteColumn`
- `DateColumn`
- `LocalDateColumn`
- `InstantColumn`
- `BigDecimalColumn`
- `UUIDColumn`

If significant duplication emerges across many implementations, introducing an `AbstractPrimitiveColumn` or a similar abstraction may become justified.

Support for missing values may also be introduced in a future version through a dedicated nullability model rather than Java `null` references.

---

## Alternatives Considered

### Generic Column<T>

Rejected.

Reasons:

- Boxing overhead.
- Loss of primitive performance.
- Additional runtime costs.
- Reduced cache efficiency.

---

### Single Object[] Storage

Rejected.

Reasons:

- No compile-time type safety.
- Boxing for primitives.
- Increased memory usage.
- Slower access.

---

### AbstractPrimitiveColumn

Deferred.

Current implementations remain small, readable, and easy to maintain.

Additional abstraction would provide little practical benefit for Version 1.

---

## References

- ADR-10: Expression System Design