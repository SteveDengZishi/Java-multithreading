package main.java;

import main.java.consumer.Consumer;
import main.java.producer.Producer;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args){

        List<Integer> taskQueue = new ArrayList<>();
        int capacity = 10;

        Producer p1 = new Producer(taskQueue, capacity, 5000, "p1");
        Producer p2 = new Producer(taskQueue, capacity, 7500, "p2");
        Consumer c1 = new Consumer(taskQueue, 8000, "c1");
        Consumer c2 = new Consumer(taskQueue, 9000, "c2");

        Thread producer1 = new Thread(p1, "Producer1");
        Thread producer2 = new Thread(p2, "Producer2");
        Thread consumer1 = new Thread(c1, "Consumer1");
        Thread consumer2 = new Thread(c2, "Consumer2");

        consumer1.start();
        consumer2.start();
        producer1.start();
        producer2.start();
    }
}
