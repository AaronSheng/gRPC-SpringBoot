package com.aaron.client;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Aaron Sheng on 8/4/17.
 */
public class GrpcPressTest {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Input threads number: ");
        int number = scanner.nextInt();

        System.out.print("Input request interval: ");
        int interval = scanner.nextInt();

        for (int i = 0; i < number; i++) {
            Thread thread = new Thread(new TestRunnable(interval));
            thread.start();
        }
    }

    private static class TestRunnable implements Runnable {
        private int interval;

        public TestRunnable(int interval) {
            this.interval = interval;
        }

        @Override
        public void run() {
            System.out.printf("Thread %-4s started\n", Thread.currentThread().getId());

            try {
                GrpcClient grpcClient = new GrpcClient("127.0.0.1", 9000);
                System.out.printf("Thread %-4s connected\n", Thread.currentThread().getId());

                while (true) {
                    Thread.sleep(interval * 1000);
                    grpcClient.hello(String.valueOf(Thread.currentThread().getId()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
