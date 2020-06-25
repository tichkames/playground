package com.hod.threads;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class ThreadsDemo {

    public static void test1() {
        System.out.println(Thread.activeCount());
        System.out.println(Runtime.getRuntime().availableProcessors());
    }

    public static void test2() {
        var executor = Executors.newFixedThreadPool(2);

        try {
            executor.submit(() -> {
                System.out.println(Thread.currentThread().getName());
            });
        } finally {
            executor.shutdown();
        }
    }

    //blocking code
    public static void test3() {
        var executor = Executors.newFixedThreadPool(2);

        try {
            var future = executor.submit(() -> {
                LongTask.simulate();
                return 1;
            });

            System.out.println("Do more work!");

            try {
                var result = future.get();
                System.out.println("Result " + result);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

        } finally {
            executor.shutdown();
        }
    }

    //asynchronous code aka non blocking
    public static void test4() {
        Runnable task = () -> System.out.println("task");
        var future = CompletableFuture.runAsync(task);

        Supplier<Integer> task2 = () -> 1;
        var future2 = CompletableFuture.supplyAsync(task2);

        try {
            System.out.println(future2.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    //implementing async api
    public static void test5() {
        var service = new MailService();
//        service.send();
        service.sendAsync();
        System.out.println("Hello World!");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //running code on completion
    public static void test6() {
        var future = CompletableFuture.supplyAsync(() -> 1);
        future.thenRun(() -> System.out.println("Done"));
    }

    //running code on completion and passing in the result
    public static void test7() {
        var future = CompletableFuture.supplyAsync(() -> 1);
        future.thenAcceptAsync(result -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println(result);
        });

        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //handling exception
    public static void test8() {
        var future = CompletableFuture.supplyAsync(() -> {
            System.out.println("Getting current weather");
            throw new IllegalStateException();
        });

        try {
            var temp = future.exceptionally(ex -> 1).get();
            System.out.println("temperature: " + temp);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    //transforming a completable future
    public static void test9() {
        var future = CompletableFuture.supplyAsync(() -> 20);

        try {
            var result = future
//                    .thenApply(celsius -> (celsius * 1.8) + 32)
                    .thenApply(ThreadsDemo::toFahrenheit)
                    .get();

            System.out.println("Converted temperature: " + result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //can also be implemented as
        future
            .thenApply(ThreadsDemo::toFahrenheit)
            .thenAccept(f -> System.out.println(f));
    }

    //composing completable futures
    public static void test10() {
        //id -> email
        //email -> playlist
        var future = CompletableFuture.supplyAsync(() -> "email");
        future.thenCompose(email -> CompletableFuture.supplyAsync(() -> "playlist"))
                .thenAccept(playlist -> System.out.println(playlist));

        //better way
        getUserEmailAsync()
                .thenCompose(email -> CompletableFuture.supplyAsync(() -> "playlist"))
                .thenAccept(playlist -> System.out.println(playlist));
    }

    //combining completable futures
    public static void test11() {
        // 20 USD
        // 0.9
        var first = CompletableFuture.supplyAsync(() -> "20USD")
                .thenApply(str -> {
                    var price = str.replace("USD", "");
                    return Integer.parseInt(price);
                });
        var second = CompletableFuture.supplyAsync(() -> 0.9);

        first
            .thenCombine(second, (price, exchangeRate) -> price * exchangeRate)
            .thenAccept(result -> System.out.println(result));
    }

    //waiting for completion of many tasks
    public static void test12() {
        var first = CompletableFuture.supplyAsync(() -> 1);
        var second = CompletableFuture.supplyAsync(() -> 2);
        var third = CompletableFuture.supplyAsync(() -> 3);

        var all = CompletableFuture.allOf(first, second, third);
        all.thenRun(() -> {

            try {
                System.out.println(first.get());
                System.out.println(second.get());
                System.out.println(third.get());

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            System.out.println("All tasks completed successfully");
        });
    }

    //waiting for first task
    public static void test13() {
        var first = CompletableFuture.supplyAsync(() -> {
            LongTask.simulate();
            return 20;
        });

        var second = CompletableFuture.supplyAsync(() -> 20);

        var fastest = CompletableFuture.anyOf(first, second);
        fastest.thenAccept(temp -> System.out.println(temp));
    }

    //handling timeouts
    public static void test14() {
        var future = CompletableFuture.supplyAsync(() -> {
            LongTask.simulate();
            return  1;
        });

        try {
//            var result = future.orTimeout(1, TimeUnit.SECONDS)
//                    .get();
            var result = future.completeOnTimeout(2, 1, TimeUnit.SECONDS)
                    .get();

            System.out.println(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static int toFahrenheit(int celsius) {
        return (int) (celsius * 1.8) + 32;
    }

    public static CompletableFuture<String> getUserEmailAsync() {
        return CompletableFuture.supplyAsync(() -> "email");
    }
}
