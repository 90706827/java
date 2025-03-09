package com.jimmy.thread.task;

import com.google.common.base.Stopwatch;
import com.jimmy.thread.pool.CustomThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @Description
 * @Author zhangguoq
 **/
public class CompletableFutureTask extends CustomThreadPool {
    private static final Logger logger = LoggerFactory.getLogger(CompletableFutureTask.class);

    private void sleep(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void thenRun() throws ExecutionException, InterruptedException {
        CompletableFuture future = CompletableFuture.runAsync(() -> {
            logger.info("{} 执行任务-runAsync", Thread.currentThread().getName());
        }, pool).thenRunAsync(() -> {
            logger.info("{} 执行任务-thenRunAsync-pool", Thread.currentThread().getName());
        }, pool);
        future.get();
    }

    public void thenApply() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            logger.info("{} 执行任务-supplyAsync", Thread.currentThread().getName());
            return 1;
        }, pool).thenApplyAsync(temp -> {
            logger.info("{} 执行任务-thenApplyAsync-pool", Thread.currentThread().getName());
            return temp + 1;
        }, pool);
        logger.info("执行结果：{}", future.join());
    }

    public void thenAccept() {
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            logger.info("{} 执行任务-supplyAsync", Thread.currentThread().getName());
            sleep();
            return 1;
        }, pool).thenAcceptAsync(temp -> {
            logger.info("{} 执行任务-thenApplyAsync-pool", Thread.currentThread().getName());
            sleep();
        }, pool);
        logger.info("执行结果：{}", future.join());
    }

    public void combine() {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            logger.info("{} 执行任务-1", Thread.currentThread().getName());
            sleep();
            return 1;
        }, pool);
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            logger.info("{} 执行任务-2", Thread.currentThread().getName());
            sleep();
            return 2;
        }, pool);
        CompletableFuture<Integer> future3 = future1.thenCombineAsync(future2, (r1, r2) -> {
            logger.info("{} 执行任务-3-{}", Thread.currentThread().getName(), r1 + r2);
            return r1 + r2;
        }, pool);
        logger.info("执行结果：{}", future3.join());
    }

    public void compose() {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            logger.info("{} 执行任务-1", Thread.currentThread().getName());
            sleep();
            return 1;
        }, pool).thenComposeAsync((temp) -> CompletableFuture.supplyAsync(() -> {
            logger.info("{} 执行任务-3", Thread.currentThread().getName());
            sleep();
            return temp + 1;
        }, pool));
        logger.info("执行结果：{}", future1.join());
    }

    public void whenComplete() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            logger.info("{} 执行任务-supplyAsync", Thread.currentThread().getName());
            if (true) {
                throw new RuntimeException();
            }
            return "任务1执行结果";
        }, pool).whenCompleteAsync((temp, throwable) -> {
            if (throwable instanceof Exception) {
                logger.info("对异常做出相应处理");
            }
            logger.info("{} 执行任务-whenCompleteAsync-{}", Thread.currentThread().getName());
        }, pool);
        logger.info("执行结果：{}", future.join());
    }

    public void handle() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            logger.info("{} 执行任务-supplyAsync", Thread.currentThread().getName());
            if (true) {
                throw new RuntimeException();
            }
            return "任务1执行结果";
        }, pool).handleAsync((temp, throwable) -> {
            if (throwable instanceof Exception) {
                logger.info("对异常做出相应处理");
            }
            logger.info("{} 执行任务-whenCompleteAsync-{}", Thread.currentThread().getName(), temp);
            return "任务2执行结果";
        }, pool);
        logger.info("执行结果：{}", future.join());
    }

    public void exceptionally() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            logger.info("{} 执行任务-exceptionally", Thread.currentThread().getName());
            if (true) {
                throw new RuntimeException();
            }
            return "任务1执行结果";
        }, pool).exceptionally((e) -> {
            if (e instanceof Exception) {
                logger.info("对异常做出相应处理");
            }
            return "程序异常";
        });
        logger.info("执行结果：{}", future.join());
    }

    public void allOrAny() {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            logger.info("{} 执行任务-1", Thread.currentThread().getName());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.info("{} 执行任务-1-over", Thread.currentThread().getName());
            return 1;
        }, pool);
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            logger.info("{} 执行任务-2", Thread.currentThread().getName());
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.info("{} 执行任务-2-over", Thread.currentThread().getName());
            return 2;
        }, pool);

        logger.info("{}", "abcd");
            logger.info("{} 执行结果：{}", Thread.currentThread().getName(), future1.join());
        logger.info("{}", "abcd");
            logger.info("{} 执行结果：{}", Thread.currentThread().getName(), future2.join());
        sleep();
        logger.info("{}", "abcd");
//        logger.info("执行结果：{}", CompletableFuture.allOf(future1, future2).join());
//        logger.info("执行结果：{}", CompletableFuture.anyOf(future1, future2).join());
    }

    public void list() {
        List<CompletableFuture<Integer>> futureList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            futureList.add(CompletableFuture.supplyAsync(() -> {
                logger.info("{} 执行任务-1", Thread.currentThread().getName());
                sleep();
                return 1;
            }, pool));
        }
        logger.info(String.valueOf(CompletableFuture.allOf(futureList.toArray(new CompletableFuture[0])).join()));
    }

    public static void main(String[] args) {
        CompletableFutureTask task = new CompletableFutureTask();
        Stopwatch stopwatch = Stopwatch.createStarted();
        try {
//            task.thenRun();
            task.allOrAny();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        task.thenApply();
//        task.combine();
//        task.compose();
//        task.whenComplete();
//        task.thenAccept();
//        task.handle();
//        task.exceptionally();

//        task.list();

        logger.info("用时：{}", stopwatch.stop().elapsed(TimeUnit.MILLISECONDS));
    }

}