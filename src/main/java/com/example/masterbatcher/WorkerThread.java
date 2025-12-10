package com.example.masterbatcher;

public class WorkerThread extends Thread {

    private final BatchService batchService;

    public WorkerThread(BatchService batchService, String name) {
        super(name);
        this.batchService = batchService;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                int processed = batchService.processBatch();
                if (processed == 0) {
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                interrupt();
                break;
            } catch (Exception e) {
                System.err.println(getName() + " error: " + e.getMessage());
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ie) {
                    interrupt();
                    break;
                }
            }
        }
    }
}
