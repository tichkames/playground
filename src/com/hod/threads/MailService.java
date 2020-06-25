package com.hod.threads;

import java.util.concurrent.CompletableFuture;

public class MailService {

    //synchronous
    public void send() {
        LongTask.simulate();
        System.out.println("Mail was sent.");
    }

    //async
    public CompletableFuture<Void> sendAsync() {
        return CompletableFuture.runAsync(() -> send());
    }
}
