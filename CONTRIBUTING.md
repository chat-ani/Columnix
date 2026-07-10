# Contributing

Thank you for your interest in contributing to JavaDataFrame.

## Development Philosophy

This project emphasizes:

- Clean architecture
- Immutability by default
- Comprehensive unit testing
- Well-documented public APIs
- Small, focused commits
- Design discussions before implementation

## Coding Standards

### Java

- Follow standard Java naming conventions.
- Prefer composition over inheritance.
- Favor immutability.
- Use factory methods instead of public constructors where appropriate.

### JavaDoc

Every public class and every public method must include JavaDoc.

### Tests

Every new feature must include unit tests.

Every bug fix must include a regression test.

### Exceptions

Never throw generic exceptions for domain problems.

Prefer custom exceptions.

Example:

✔ DuplicateColumnException

✘ IllegalArgumentException

### Pull Requests

Every PR should include:

- Motivation
- Design decisions
- Tests added
- Future considerations

### Commit Messages

Use Conventional Commits.

Examples:

feat(core): add immutable DataFrame

fix(schema): reject duplicate column names

docs: add architecture documentation