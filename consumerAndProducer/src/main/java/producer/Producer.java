package main.java.producer;

import java.util.List;

public class Producer implements Runnable{

    private final List<Integer> taskQueue;
    private final int MAX_CAPACITY;

    public Producer(List<Integer> queue, int capacity){
        this.taskQueue = queue;
        this.MAX_CAPACITY = capacity;
    }
    @Override
    public void run() {
        int counter = 0;
        while(true){
            try{
                produce(counter++);
            } catch (InterruptedException ex){
                System.out.println("Caught interrupted exception");
                ex.printStackTrace();
            }
        }
    }

    private void produce(int counter) throws InterruptedException{
        //entered lock on taskQueue
        synchronized (taskQueue){
            while(taskQueue.size() == MAX_CAPACITY){
                System.out.println("Producer is at max capacity of 10. Please wait for consumer to consume");
                //give up the lock until some other thread calls notifyAll
                taskQueue.wait();
            }

            System.out.println("Allocating resources to produce item " + counter);
            Thread.sleep(1000);
            taskQueue.add(counter);
            System.out.println("Item " + counter + " produced successfully");

            //we need this explicit call to notify when consumer finished consume and wait on empty taskQueue
            taskQueue.notifyAll();
        }
    }

    public List<Integer> getTaskQueue() {
        return taskQueue;
    }

    public int getMAX_CAPACITY() {
        return MAX_CAPACITY;
    }
}
