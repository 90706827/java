package com.example.demo.thread.CompletableFuture;

import java.util.concurrent.CompletableFuture;

/**
 * @ClassName CompletableFutureTest
 * @Description CompletableFuture
 * @Author Mr.Jangni
 * @Date 2018/9/18 17:27
 * @Version 1.0
 **/
public class CompletableFutureTest {
    //    public T 	get()方法是阻塞的。
    //    public T 	get(long timeout, TimeUnit unit) 方法是阻塞的 可以设置等待时间。
    //    public T 	getNow(T valueIfAbsent) 如果结果已经计算完则返回结果或抛异常，否则返回给定的valueIfAbsent的值。
    //    public T 	join()返回计算的结果或抛出一个uncheckd异常。
    public static void main(String[] args) {
        try {

// Async结尾的方法都是可以异步执行的，
// 如果指定了线程池，会在指定的线程池中执行，如果没有指定，默认会在ForkJoinPool.commonPool()中执行。
// 关键的入参只有一个Function，它是函数式接口，所以使用Lambda表示起来会更加优雅。
//  它的入参是上一个阶段计算后的结果，返回值是经过转化后结果。
//    public <U> CompletionStage<U> thenApply(Function<? super T,? extends U> fn);
//    public <U> CompletionStage<U> thenApplyAsync(Function<? super T,? extends U> fn);
//    public <U> CompletionStage<U> thenApplyAsync(Function<? super T,? extends U> fn,Executor executor);
            String result = CompletableFuture.supplyAsync(() -> "thenApply hello").thenApply(s -> s + " world").join();
            System.out.println(result);

//            thenAccept是针对结果进行消耗，因为他的入参是Consumer，有入参无返回值
//            public CompletionStage<Void> thenAccept(Consumer<? super T> action);
//            public CompletionStage<Void> thenAcceptAsync(Consumer<? super T> action);
//            public CompletionStage<Void> thenAcceptAsync(Consumer<? super T> action,Executor executor);
            CompletableFuture.supplyAsync(() -> "thenAccept hello").thenAccept(s -> System.out.println(s + " world"));

//            thenRun对上一步的计算结果不关心，主线程对thenRun的计算结果不关心。
//            thenRun它的入参是一个Runnable的实例，表示当得到上一步的结果时的操作。
//            public CompletionStage<Void> thenRun(Runnable action);
//            public CompletionStage<Void> thenRunAsync(Runnable action);
//            public CompletionStage<Void> thenRunAsync(Runnable action,Executor executor);
            CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "hello1";
            }).thenRun(() -> {
                System.out.println("thenRun hello world");
            }).join();

// public <U,V> CompletionStage<V> thenCombine(CompletionStage<? extends U> other,BiFunction<? super T,? super U,? extends V> fn);
// public <U,V> CompletionStage<V> thenCombineAsync(CompletionStage<? extends U> other,BiFunction<? super T,? super U,? extends V> fn);
// public <U,V> CompletionStage<V> thenCombineAsync(CompletionStage<? extends U> other,BiFunction<? super T,? super U,? extends V> fn,Executor executor);
            String result1 = CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "thenCombine hello";
            }).thenCombine(CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "world";
            }), (s1, s2) -> s1 + "-" + s2).join();
            System.out.println(result1);
// public <U> CompletionStage<Void> thenAcceptBoth(CompletionStage<? extends U> other,BiConsumer<? super T, ? super U> action);
// public <U> CompletionStage<Void> thenAcceptBothAsync(CompletionStage<? extends U> other,BiConsumer<? super T, ? super U> action);
// public <U> CompletionStage<Void> thenAcceptBothAsync(CompletionStage<? extends U> other,BiConsumer<? super T, ? super U> action,     Executor executor);
            CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "thenAcceptBoth hello";
            }).thenAcceptBoth(CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "world";
            }), (s1, s2) -> System.out.println(s1 + " " + s2)).join();
//            在两个CompletionStage都运行完执行。
//            不关心这两个CompletionStage的结果，只关心这两个CompletionStage执行完毕，之后在进行操作（Runnable）。
//            public CompletionStage<Void> runAfterBoth(CompletionStage<?> other,Runnable action);
//            public CompletionStage<Void> runAfterBothAsync(CompletionStage<?> other,Runnable action);
//            public CompletionStage<Void> runAfterBothAsync(CompletionStage<?> other,Runnable action,Executor executor);
            CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "s1";
            }).runAfterBothAsync(CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "s2";
            }), () -> System.out.println("runAfterBothAsync hello world")).join();

//            两个CompletionStage，谁计算的快，我就用那个CompletionStage的结果进行下一步的转化操作。
//            public <U> CompletionStage<U> applyToEither(CompletionStage<? extends T> other,Function<? super T, U> fn);
//            public <U> CompletionStage<U> applyToEitherAsync(CompletionStage<? extends T> other,Function<? super T, U> fn);
//            public <U> CompletionStage<U> applyToEitherAsync(CompletionStage<? extends T> other,Function<? super T, U> fn,Executor executor);
            String result2 = CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "s1";
            }).applyToEither(CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "applyToEither hello world";
            }), s -> s).join();
            System.out.println(result2);
//            两个CompletionStage，谁计算的快，我就用那个CompletionStage的结果进行下一步的消耗操作。
//            public CompletionStage<Void> acceptEither(CompletionStage<? extends T> other,Consumer<? super T> action);
//            public CompletionStage<Void> acceptEitherAsync(CompletionStage<? extends T> other,Consumer<? super T> action);
//            public CompletionStage<Void> acceptEitherAsync(CompletionStage<? extends T> other,Consumer<? super T> action,Executor executor);
            CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "s1";
            }).acceptEither(CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "acceptEither hello world";
            }), System.out::println).join();

//            两个CompletionStage，任何一个完成了都会执行下一步的操作（Runnable）。
//            public CompletionStage<Void> runAfterEither(CompletionStage<?> other,Runnable action);
//            public CompletionStage<Void> runAfterEitherAsync(CompletionStage<?> other,Runnable action);
//            public CompletionStage<Void> runAfterEitherAsync(CompletionStage<?> other,Runnable action,Executor executor);
            CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "s1";
            }).runAfterEither(CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "s2";
            }), () -> System.out.println("runAfterEither hello world")).join();

//            当运行时出现了异常，可以通过exceptionally进行补偿。
//            public CompletionStage<T> exceptionally(Function<Throwable, ? extends T> fn);
            String result3 = CompletableFuture.supplyAsync(() -> {
                if (1 == 1) {
                    throw new RuntimeException("测试一下异常情况");
                }
                return "s1";
            }).exceptionally(e -> {
                System.out.println(e.getMessage());
                return "exceptionally hello world";
            }).join();
            System.out.println(result3);
//            当运行完成时，对结果的记录。这里的完成时有两种情况，一种是正常执行，返回值。另外一种是遇到异常抛出造成程序的中断。
//            这里为什么要说成记录，因为这几个方法都会返回CompletableFuture，当Action执行完毕后它的结果返回原始的
//            CompletableFuture的计算结果或者返回异常。所以不会对结果产生任何的作用。
//            public CompletionStage<T> whenComplete(BiConsumer<? super T, ? super Throwable> action);
//            public CompletionStage<T> whenCompleteAsync(BiConsumer<? super T, ? super Throwable> action);
//            public CompletionStage<T> whenCompleteAsync(BiConsumer<? super T, ? super Throwable> action,Executor executor);
            String result4 = CompletableFuture.supplyAsync(() -> {
                if (1 == 1) {
                    throw new RuntimeException("测试一下异常情况");
                }
                return "s1";
            }).whenComplete((s, t) -> {
                System.out.println(s);
                System.out.println(t.getMessage());
            }).exceptionally(e -> {
                System.out.println(e.getMessage());
                return "whenComplete hello world";
            }).join();
            System.out.println(result4);

//            运行完成时，对结果的处理。这里的完成时有两种情况，一种是正常执行，返回值。另外一种是遇到异常抛出造成程序的中断。
//            public <U> CompletionStage<U> handle(BiFunction<? super T, Throwable, ? extends U> fn);
//            public <U> CompletionStage<U> handleAsync(BiFunction<? super T, Throwable, ? extends U> fn);
//            public <U> CompletionStage<U> handleAsync(BiFunction<? super T, Throwable, ? extends U> fn,Executor executor);
            String result5 = CompletableFuture.supplyAsync(() -> {
                //出现异常
                if (false) {
                    throw new RuntimeException("测试一下异常情况");
                }
                return "s1";
            }).handle((s, t) -> {
                if (t != null) {
                    return "hello world";
                }
                return s;
            }).join();
            System.out.println(result5);

            //设定返回值
//            test1();
            //设定返回异常
//            test2();
            //supplyAsync 方法
//            test3();
            //anyOf 取返回最快的CompletableFuture的值； allOf 顺序执行Future
//            test4();
            //thenCompose 方法允许你对两个异步操作进行流水线，第一个操作完成时，将其结果作为参数传递给第二个操作。
//            test5();
            // thenCombine 方法 将第一个任务与第二个任务组合一起执行，都执行完成后，将两个任务的结果合并
//            test6();
            //thenAccept 方法 将两个任务的结果合并
//            test7();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void test1() throws Exception {
        CompletableFuture<String> completableFuture = new CompletableFuture();
        new Thread(new Runnable() {
            @Override
            public void run() {
                //模拟执行耗时任务
                System.out.println("task doing...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //告诉completableFuture任务已经完成
                completableFuture.complete("result");
            }
        }).start();
        //获取任务结果，如果没有完成会一直阻塞等待
        String result = completableFuture.get();
        System.out.println("计算结果:" + result);
    }

    public static void test2() throws Exception {
        CompletableFuture<String> completableFuture = new CompletableFuture();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("task doing...");
                    throw new RuntimeException("抛异常了");
                } catch (Exception e) {
                    //告诉completableFuture任务发生异常了
                    completableFuture.completeExceptionally(e);
                }
            }
        }).start();
        //获取任务结果，如果没有完成会一直阻塞等待
        String result = completableFuture.get();
        System.out.println("计算结果:" + result);
    }

    public static void test3() throws Exception {
        //supplyAsync内部使用ForkJoinPool线程池执行任务
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            //模拟执行耗时任务
            System.out.println("task doing...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //返回结果
            return "result3";
        });
        System.out.println("计算结果:" + completableFuture.get());
    }

    public static void test4() throws Exception {

        CompletableFuture<String> completableFuture1 = CompletableFuture.supplyAsync(() -> {
            //模拟执行耗时任务
            System.out.println("task1 doing...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //返回结果
            return "result1";
        });

        CompletableFuture<String> completableFuture2 = CompletableFuture.supplyAsync(() -> {
            //模拟执行耗时任务
            System.out.println("task2 doing...");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //返回结果
            return "result2";
        });

        CompletableFuture<Object> anyResult = CompletableFuture.anyOf(completableFuture1, completableFuture2);

        System.out.println("第一个完成的任务结果:" + anyResult.get());

        CompletableFuture<Void> allResult = CompletableFuture.allOf(completableFuture1, completableFuture2);

        //阻塞等待所有任务执行完成
        allResult.join();
        System.out.println("所有任务执行完成");
    }

    public static void test5() throws Exception {

        CompletableFuture<String> completableFuture1 = CompletableFuture.supplyAsync(() -> {
            //模拟执行耗时任务
            System.out.println("task1 doing...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //返回结果
            return "result51";
        });

        //等第一个任务完成后，将任务结果传给参数result，执行后面的任务并返回一个代表任务的completableFuture
        CompletableFuture<String> completableFuture2 = completableFuture1.thenCompose(result -> CompletableFuture.supplyAsync(() -> {
            //模拟执行耗时任务
            System.out.println("task2 doing..." + result);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //返回结果
            return "result52";
        }));

        System.out.println(completableFuture2.get());

    }

    public static void test6() throws Exception {

        CompletableFuture<Integer> completableFuture1 = CompletableFuture.supplyAsync(() -> {
            //模拟执行耗时任务
            System.out.println("task1 doing...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //返回结果
            return 100;
        });

        //将第一个任务与第二个任务组合一起执行，都执行完成后，将两个任务的结果合并
        CompletableFuture<Integer> completableFuture2 = completableFuture1.thenCombine(
                //第二个任务
                CompletableFuture.supplyAsync(() -> {
                    //模拟执行耗时任务
                    System.out.println("task2 doing...");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //返回结果
                    return 2000;
                }),
                //合并函数
                (result1, result2) -> result1 + result2);

        System.out.println(completableFuture2.get());

    }


    public static void test7() throws Exception {

        CompletableFuture<Integer> completableFuture1 = CompletableFuture.supplyAsync(() -> {
            //模拟执行耗时任务
            System.out.println("task1 doing...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //返回结果
            return 100;
        });

        //注册完成事件
        completableFuture1.thenAccept(result -> System.out.println("task1 done,result:" + result));

        CompletableFuture<Integer> completableFuture2 =
                //第二个任务
                CompletableFuture.supplyAsync(() -> {
                    //模拟执行耗时任务
                    System.out.println("task2 doing...");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //返回结果
                    return 2000;
                });

        //注册完成事件
        completableFuture2.thenAccept(result -> System.out.println("task2 done,result:" + result));

        //将第一个任务与第二个任务组合一起执行，都执行完成后，将两个任务的结果合并
        CompletableFuture<Integer> completableFuture3 = completableFuture1.thenCombine(completableFuture2,
                //合并函数
                (result1, result2) -> {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return result1 + result2;
                });

        System.out.println(completableFuture3.get());

    }
}
