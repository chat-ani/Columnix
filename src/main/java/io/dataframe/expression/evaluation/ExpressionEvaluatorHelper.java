package io.dataframe.expression.evaluation;

public class ExpressionEvaluatorHelper {

    /**
     * Validates that two operands can participate in an ordered comparison.
     *
     * <p>Both operands must be of the same runtime type and implement
     * {@link Comparable}. This validation is required for relational
     * comparison operators such as {@code >}, {@code >=}, {@code <},
     * and {@code <=}.
     *
     * @param left the left operand
     * @param right the right operand
     * @throws IllegalArgumentException if the operands have different runtime
     *                                  types or do not implement
     *                                  {@link Comparable}
     */
    protected static void requireComparableOperands(Object left, Object right) {

        if (left instanceof Boolean) {
            throw new IllegalArgumentException("Boolean values do not support relational comparisons.");
        }

        if (!left.getClass().equals(right.getClass())) {
            throw new IllegalArgumentException("Comparison operands must have the same type.");
        }

        if (!(left instanceof Comparable<?>)) {
            throw new IllegalArgumentException("Comparison operands must implement Comparable.");
        }
    }

    /**
     * Compares two comparable operands.
     * <p>
     * The operands are assumed to have already passed validation through
     * {@link #requireComparableOperands(Object, Object)}.
     * </p>
     *
     * @param left  the left operand
     * @param right the right operand
     * @return a negative integer, zero, or a positive integer as the left operand
     *         is less than, equal to, or greater than the right operand
     */
    @SuppressWarnings("unchecked")
    protected static int compare(Object left, Object right) {

        return ((Comparable<Object>) left).compareTo(right);
    }

    /**
     * Validates that the supplied operands are of type {@link Boolean}.
     * <p>
     * Logical operators require both operands to evaluate to boolean values.
     * </p>
     *
     * @param left the left operand
     * @param right the right operand
     * @throws IllegalArgumentException if either operand is not a {@link Boolean}
     */
    protected static void requireBooleanOperands(Object left, Object right) {

        if (!(left instanceof Boolean) || !(right instanceof Boolean)) {
            throw new IllegalArgumentException("Logical operands must be of type Boolean.");
        }
    }

    /**
     * Validates that the supplied operand is of type {@link Boolean}.
     * <p>
     * The logical NOT operator requires its operand to evaluate to a boolean value.
     * </p>
     *
     * @param value the operand to validate
     * @throws IllegalArgumentException if the operand is not a {@link Boolean}
     */
    protected static void requireBooleanOperand(Object value) {

        if (!(value instanceof Boolean)) {
            throw new IllegalArgumentException("Logical operand must be of type Boolean.");
        }
    }
}
