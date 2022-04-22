package com.jimmy.async.core;

import java.util.concurrent.CompletableFuture;

/**
 * @author jimmy
 */
public interface AsyncHandler<T,R> {
    /**
     *
     * @return
     */
    CompletableFuture<R> handler(T t);

}
