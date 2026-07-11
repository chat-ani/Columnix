# ADR-10: Expression System Design

- **Status:** Accepted
- **Date:** 2026-07-11
- **Decision Makers:** Anirban Chatterjee

---

## Context

JavaDataFrame provides analytical operations such as filtering, computed columns, and future query transformations.

Rather than executing operations immediately, the library represents user-defined operations as immutable expression trees. This approach separates expression construction from execution and provides a flexible foundation for future optimizations, validation, and query planning.

A consistent expression model is required to ensure maintainability, extensibility, and predictable behavior across the library.

---

## Decision

The library models expressions as an immutable Abstract Syntax Tree (AST).

Every expression implements the `Expression` interface and represents a single node within the expression tree.

Expression objects are immutable value objects and contain no execution logic.

The Version 1 expression system consists of:

- `Expression`
- `ColumnExpression`
- `LiteralExpression`
- `ComparisonExpression`
- `LogicalExpression`
- `NotExpression`

Supported operators are represented using dedicated enumerations:

- `ComparisonOperator`
- `LogicalOperator`

---

## Design Principles

### Immutable

Every expression is immutable after construction.

All state is stored in `private final` fields.

---

### Value Objects

Expression classes implement:

- `equals()`
- `hashCode()`
- `toString()`

Two expressions are considered equal when they represent the same logical structure.

---

### Static Factory Methods

Expression instances are created through static factory methods.

Example:

```java
ComparisonExpression.of(
    ColumnExpression.of("Age"),
    LiteralExpression.of(30),
    ComparisonOperator.GREATER_THAN
);
```

Constructors remain private to preserve validation and implementation flexibility.

---

### Validation During Construction

All constructor arguments are validated during object creation.

Invalid expressions fail fast by throwing custom exceptions.

This guarantees that every constructed expression is in a valid state.

---

### Expressions Represent Structure Only

Expressions describe *what* should be evaluated.

They do not contain evaluation or execution logic.

Execution responsibilities belong to future components such as:

- ExpressionEvaluator
- DataFrame filtering
- Query planner
- Optimizer

This separation follows the Single Responsibility Principle.

---

### Recursive Tree Structure

Expressions are recursive.

For example:

```text
NOT (
    (Age > 30)
    AND
    (Salary >= 50000)
)
```

is represented as:

```text
                 NotExpression
                        │
                        │
               LogicalExpression
                /             \
               /               \
     ComparisonExpression   ComparisonExpression
```

This recursive design enables arbitrary expression composition.

---

### Unary and Binary Operations

Unary and binary operations are modeled by separate classes.

Examples:

Unary:

- `NotExpression`

Binary:

- `ComparisonExpression`
- `LogicalExpression`

This avoids ambiguous APIs and accurately represents the structure of the expression tree.

---

### Dedicated Operator Types

Operators are represented by dedicated enums rather than strings.

Benefits include:

- Compile-time safety
- Better readability
- Easier maintenance
- Simpler evaluation

---

## Consequences

### Advantages

- Immutable and thread-safe design
- Strong type safety
- Clear separation between construction and execution
- Easy to extend with new expression types
- Suitable for future optimization and query planning
- Predictable equality semantics
- Consistent public API

### Trade-offs

- Expression construction is slightly more verbose.
- Execution requires a separate evaluation layer.
- Additional expression types increase the number of AST nodes.

These trade-offs are considered acceptable in exchange for a clean and extensible architecture.

---

## Future Work

The expression system provides the foundation for future features including:

- ExpressionEvaluator
- DataFrame.filter(...)
- DataFrame.select(...)
- Expression DSL (`Expressions`)
- Arithmetic expressions
- Function expressions
- Query optimization
- Predicate simplification

---

## Alternatives Considered

### Embedding evaluation logic inside expressions

Rejected.

Mixing representation with execution violates separation of concerns and makes optimization more difficult.

---

### Mutable expressions

Rejected.

Immutable value objects are simpler, thread-safe, and easier to reason about.

---

### Single expression class with operator field

Rejected.

Dedicated expression types better model unary and binary operations, improve readability, and simplify future maintenance.

---

## References

- Fowler, *Patterns of Enterprise Application Architecture*
- GoF Design Patterns
- Apache Spark Catalyst Expression Tree
- Apache Calcite
- DuckDB Query Planner