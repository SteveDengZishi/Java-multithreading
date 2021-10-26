package main.java.producer;

import java.util.List;

public class Producer implements Runnable{

    private final List<Integer> taskQueue;
    private final int MAX_CAPACITY;
    private final int speed;
    private final String name;

    public Producer(List<Integer> queue, int capacity, int sleepTime, String name){
        this.taskQueue = queue;
        this.MAX_CAPACITY = capacity;
        this.speed = sleepTime;
        this.name = name;
    }
    @Override
    public void run() {
        int counter = 0;
        while(true){
            try{
                produce(counter++);
            } catch (InterruptedException ex){
                System.out.println("Caught interrupted exception when producing item");
                ex.printStackTrace();
            }
        }
    }

    private void produce(int counter) throws InterruptedException{
        //entered lock on taskQueue
        synchronized (taskQueue){
            //use while to setup wait condition
            while(taskQueue.size() == MAX_CAPACITY){
                System.out.println("Producer is at max capacity of 10. Please wait for consumer to consume");
                //give up the lock until some other thread calls notifyAll
                taskQueue.wait();
            }
            taskQueue.add(counter);
            System.out.println("Produced item " + counter + " in " + this);

            //we need this explicit call to notify when consumer finished consume and wait on empty taskQueue
            taskQueue.notifyAll();
        }
        Thread.sleep(speed);
    }

    public List<Integer> getTaskQueue() {
        return taskQueue;
    }

    public int getMAX_CAPACITY() {
        return MAX_CAPACITY;
    }

    @Override
    public String toString(){
        return "Producer " + name;
    }
}
