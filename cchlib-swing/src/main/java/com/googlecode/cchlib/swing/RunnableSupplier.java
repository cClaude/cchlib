package com.googlecode.cchlib.swing;

import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

/**
 * Allow to have an object implementing {@link Runnable} and
 * {@link Supplier}
 *
 * @param <R> Result type
 *
 * @see RunnableSupplierHelper
 * @see RunnableSupplierHelper#newRunnableSupplier(Supplier)
 */
public interface RunnableSupplier<R> extends Runnable
{
    /**
     * Returns the expected result, if {@link #getExecutionException()} is
     * null and if {@link #isDone()} is true.
     *
     * @return the expected result
     */
    R getResult();

    /**
     * Should return true when result is done or when an
     * exception has occur.
     *
     * @return true in {@link Runnable#run()} as return, false
     * otherwise.
     */
    boolean isDone();

    /**
     * Returns the cause exception or null if none
     *
     * @return the cause exception or null if none
     */
    ExecutionException getExecutionException();
}
