# JavaDataFrame

> A high-performance, immutable, column-oriented DataFrame library for Java.

JavaDataFrame is an open-source library inspired by the analytical capabilities of tools like **pandas**, while embracing Java's type safety, performance, and object-oriented design principles.

The project is being built from the ground up with a strong emphasis on **clean architecture**, **immutability**, **developer experience**, and **extensibility**.

---

## ✨ Vision

Our goal is to provide a modern DataFrame implementation for Java that is:

- 🚀 Fast for analytical workloads
- 🔒 Immutable by design
- 🧩 Extensible and maintainable
- 📖 Well documented
- ✅ Thoroughly tested

Rather than simply replicating existing libraries, JavaDataFrame focuses on providing a clean and intuitive API backed by sound software engineering principles.

---

## 🚀 Features

### Current

- ✅ Immutable DataFrame model
- ✅ Column-oriented storage
- ✅ Strong schema validation
- ✅ Immutable Schema
- ✅ Immutable Columns
- ✅ Static factory methods
- ✅ Custom exception hierarchy
- ✅ Comprehensive unit tests
- ✅ JavaDoc for all public APIs

### Planned

- 🚧 LongColumn
- 🚧 DoubleColumn
- 🚧 StringColumn
- 🚧 BooleanColumn
- 🚧 CSV Reader / Writer
- 🚧 Filtering
- 🚧 Sorting
- 🚧 Group By
- 🚧 Aggregations
- 🚧 Join Operations
- 🚧 Statistical Functions

---

## 🏗️ Project Structure

```
src/main/java
└── io.dataframe
    ├── column
    ├── core
    ├── exception
    └── types
```

---

## 📚 Current Architecture

```
               DataFrame
                    │
            ┌───────┴────────┐
            │                │
         Schema          Row Count
            │
     ┌──────┴──────┐
     │             │
 Column Map     Column List
                    │
       ┌────────────┴────────────┐
       │
    IntColumn
```

---

## 📖 Example

```java
Column age = IntColumn.of("Age", 20, 30, 40);
Column salary = IntColumn.of("Salary", 50000, 60000, 70000);

DataFrame employees = DataFrame.of(age, salary);

System.out.println(employees.rowCount());
System.out.println(employees.columnCount());
```

---

## 📂 Documentation

Additional project documentation can be found in the `docs` directory.

- Architecture Overview
- Architecture Decision Records (ADR)
- Contributing Guidelines

---

## 🧪 Quality Goals

Every feature added to the project follows these principles:

- Public APIs are documented with JavaDoc.
- Every new feature includes unit tests.
- Public objects are immutable by default.
- Design decisions are documented using ADRs.
- Backward compatibility is considered before API changes.

---

## 🛣️ Roadmap

### Core Foundation

- [x] DataType
- [x] Column
- [x] IntColumn
- [x] Schema
- [x] DataFrame
- [x] Custom Exceptions

### Data Types

- [ ] LongColumn
- [ ] DoubleColumn
- [ ] StringColumn
- [ ] BooleanColumn

### Data Processing

- [ ] Filter
- [ ] Sort
- [ ] Group By
- [ ] Aggregation
- [ ] Join

### Input / Output

- [ ] CSV Reader
- [ ] CSV Writer
- [ ] JSON Support
- [ ] Parquet (Future)

---

## 🤝 Contributing

Contributions, discussions, feature requests, and bug reports are welcome.

Please read the **CONTRIBUTING.md** guide before submitting a pull request.

---

## 📄 License

This project is licensed under the Apache License, version 2.0.

---

## ⭐ Project Status

JavaDataFrame is currently under active development.

The API is evolving as the core architecture is finalized, with stability and long-term maintainability taking priority over rapid feature additions.