package com.example.masterbatcher;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Value;

@SpringBootApplication
public class MasterbatcherApplication implements CommandLineRunner {

    private final BatchService batchService;

    @Value("${queue.thread-count:3}")
    private int threadCount;

    public MasterbatcherApplication(BatchService batchService) {
        this.batchService = batchService;
    }

    public static void main(String[] args) {
        SpringApplication.run(MasterbatcherApplication.class, args);
    }

    @Override
    public void run(String... args) {
        for (int i = 0; i < threadCount; i++) {
            new WorkerThread(batchService, "Worker-" + i).start();
        }
    }
}
