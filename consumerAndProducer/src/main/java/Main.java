package main.java;

import main.java.consumer.Consumer;
import main.java.producer.Producer;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args){

        List<Integer> taskQueue = new ArrayList<>();
        int capacity = 10;

        Producer p = new Producer(taskQueue, capacity);
        Consumer c = new Consumer(taskQueue);

        Thread producer1 = new Thread(p, "Producer");
        Thread consumer1 = new Thread(c, "Consumer");

        producer1.start();
        consumer1.start();
    }
}
