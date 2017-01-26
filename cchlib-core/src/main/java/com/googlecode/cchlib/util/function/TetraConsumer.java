package com.googlecode.cchlib.util.function;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Represents an operation that accepts three input arguments and returns no
 * result. This is the four-arity specialization of {@link Consumer}.
 * Unlike most other functional interfaces, {@code TetraConsumer} is expected
 * to operate via side-effects.
 *
 * @param <T> the type of the first argument to the operation
 * @param <U> the type of the second argument to the operation
 * @param <V> the type of the third argument to the operation
 * @param <W> the type of the forth argument to the operation
 *
 * @see Consumer
 * @see BiConsumer
 * @see TriConsumer
 * @since 4.2
 */
@FunctionalInterface
public interface TetraConsumer<T, U, V, W>
{
    /**
     * Performs this operation on the given arguments.
     *
     * @param t the first input argument
     * @param u the second input argument
     * @param v the third input argument
     * @param w the forth input argument
     */
    void accept(T t, U u, V v, W w);

    /**
     * Returns a composed {@code TetraConsumer} that performs, in sequence, this
     * operation followed by the {@code after} operation. If performing either
     * operation throws an exception, it is relayed to the caller of the composed
     * operation. If performing this operation throws an exception, the
     * {@code after} operation will not be performed.
     *
     * @param after
     *            the operation to perform after this operation
     * @return a composed {@code TetraConsumer} that performs in sequence this
     *         operation followed by the {@code after} operation
     * @throws NullPointerException
     *             if {@code after} is null
     */
    default TetraConsumer<T, U, V, W> andThen(
        final TetraConsumer<? super T, ? super U, ? super V, ? super W> after
        )
    {
        Objects.requireNonNull(after);

        return (l, r, u, w) -> {
            accept(l, r, u, w);
            after.accept(l, r, u, w);
        };
    }
}
