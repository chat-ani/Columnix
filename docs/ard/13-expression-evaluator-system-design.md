# ADR-13: Expression Evaluation Engine Design

- **Status:** Accepted
- **Date:** 2026-07-13
- **Deciders:** Anirban Chatterjee
- **Technical Story:** Introduce an execution engine capable of evaluating expression trees against DataFrame rows.

---

## Context

The JavaDataFrame library provides an immutable expression system consisting of:

- `LiteralExpression`
- `ColumnExpression`
- `ComparisonExpression`
- `LogicalExpression`
- `NotExpression`

These classes model expression syntax but intentionally contain no execution logic.

To support future DataFrame operations such as filtering, computed columns, and query execution, the library requires an engine capable of evaluating an expression for a specific row of a DataFrame.

The evaluation mechanism must remain independent from the expression hierarchy while preserving immutability and extensibility.

---

## Decision

A dedicated `ExpressionEvaluator` will be introduced as an internal execution component.

The evaluator will recursively traverse expression trees and compute a value for a given DataFrame row.

Package structure:

```
io.dataframe.expression
│
├── Expression
├── ColumnExpression
├── LiteralExpression
├── ComparisonExpression
├── LogicalExpression
├── NotExpression
│
└── evaluation
    └── ExpressionEvaluator
```

The evaluator is intentionally separated from expression classes in order to preserve the single responsibility of each component.



Design Diagram:

```plantuml
                 Expression
                     │
         ┌───────────┼────────────┐
         │           │            │
ColumnExpression  LiteralExpression
         │           │
         └──────┬────┘
                │
      ComparisonExpression
                │
      LogicalExpression
                │
        NotExpression
                │
                ▼
      ExpressionEvaluator
                │
                ▼
      DataFrame + rowIndex
                │
                ▼
           Object result
```


---

## Expression Responsibilities

Expression objects are responsible only for representing query syntax.

They do **not** perform evaluation.

For example,

```
Age > 18
```

is represented as

```
ComparisonExpression
    ├── ColumnExpression("Age")
    └── LiteralExpression(18)
```

The expression tree stores structure only.

Execution is delegated to the evaluation engine.

---

## Evaluation Strategy


Expression evaluation is implemented recursively.

Each expression delegates evaluation of its child expressions before computing its own result.

Example:

```
(Age > 18) AND (Salary > 50000)
```

Execution flow:

```
LogicalExpression
        │
        ├── evaluate(left)
        │         │
        │         ▼
        │   ComparisonExpression
        │
        └── evaluate(right)
                  │
                  ▼
            ComparisonExpression
```

Leaf expressions terminate recursion.

```
LiteralExpression
        │
        ▼
return literal value

ColumnExpression
        │
        ▼
lookup DataFrame column
return row value
```

---

## Evaluation API

Version 1 exposes a single evaluation method.

```java
static Object evaluate(
        Expression expression,
        DataFrame dataFrame,
        int rowIndex
)
```

Parameters:

- `Expression` — expression tree to evaluate
- `DataFrame` — source of column values
- `rowIndex` — row being evaluated

Return type:

```
Object
```

The evaluator returns `Object` because different expressions naturally produce different value types.

Examples:

| Expression | Result |
|------------|---------|
| LiteralExpression | Integer, Long, Double, Boolean, String |
| ColumnExpression | Column value |
| ComparisonExpression | Boolean |
| LogicalExpression | Boolean |
| NotExpression | Boolean |

---

## Column Access

To avoid coupling the evaluator to every concrete column implementation, the abstract `Column` class provides a type-independent accessor.

```java
Object value(int rowIndex)
```

Every concrete column implements this method.

Example:

```
IntColumn
LongColumn
DoubleColumn
BooleanColumn
StringColumn
```

This allows the evaluator to interact only with the `Column` abstraction.

Instead of

```
instanceof IntColumn
instanceof LongColumn
instanceof DoubleColumn
...
```

evaluation becomes

```java
dataFrame
    .column(columnName)
    .value(rowIndex);
```

This follows polymorphism and significantly reduces coupling.

---

## Visibility

`ExpressionEvaluator` is intentionally package-private.

It is considered an internal implementation detail.

Public APIs such as

```java
DataFrame.filter(...)
```

will delegate internally to the evaluator.

Library users are not expected to invoke the evaluator directly.

---

## Why No ExpressionEvaluator Interface?

Version 1 intentionally provides only a single evaluation implementation.

Introducing an interface would create an unnecessary abstraction with only one implementation.

The project follows the YAGNI ("You Aren't Gonna Need It") principle and introduces abstractions only when multiple implementations become necessary.

If future requirements introduce alternative evaluation strategies (parallel execution, compiled expressions, vectorized execution, etc.), an interface may be introduced without affecting public APIs.

---

## Consequences

### Advantages

- Clear separation between syntax and execution.
- Expression classes remain immutable and lightweight.
- Evaluation logic is centralized.
- Minimal coupling through `Column.value(int)`.
- Easy to extend with additional expression types.
- Internal implementation remains hidden from library users.

### Trade-offs

- Recursive evaluation introduces boxing through `Object`.
- Version 1 performs no expression optimization.
- Evaluation is row-by-row rather than vectorized.

These trade-offs are acceptable for the initial implementation and keep the design simple.

---

## Future Considerations

Future versions may introduce:

- Arithmetic expressions
- Function expressions
- Aggregate expressions
- Query optimizer
- Vectorized execution engine
- Execution context abstraction
- Expression compilation
- Parallel evaluation strategies

The current architecture allows these enhancements without modifying the expression hierarchy.

---

## References

- ADR-10: Expression System Design
- ADR-12: Column System Design