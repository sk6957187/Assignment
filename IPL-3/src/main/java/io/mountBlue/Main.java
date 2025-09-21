package io.mountBlue;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Calling calling = new Calling();
        ExecutorService executor = Executors.newCachedThreadPool();
        boolean flag = true;

        while (flag) {
            System.out.println("----------main menu----------");
            System.out.println("0. Exit");
            System.out.println("1. Create thread.");
            System.out.print("Enter the choice in main menu: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 0:
                    flag = false;
                    executor.shutdown();
                    System.out.println("Thank you for using IPL-3...!!");
                    break;
                case 1:
                    try {
                        executor.submit(() -> {
                            Thread t = Thread.currentThread();
                            System.out.println("\n-----New thread generated-----");
                            System.out.println("Thread Name : " + t.getName());
                            System.out.println("Thread ID   : " + t.getId());
                            System.out.println("Thread State: " + t.getState());
                            System.out.println("Thread Priority: " + t.getPriority());
                            System.out.println("Is Daemon?  : " + t.isDaemon());
                            System.out.println("--------------------------");
                            calling.calling();
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    System.out.println("Wrong input in main menu..!!");
                    break;
            }
        }
    }
}
