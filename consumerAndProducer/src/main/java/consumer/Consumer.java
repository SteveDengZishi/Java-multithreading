package main.java.consumer;

import java.util.List;

public class Consumer implements Runnable{
    private final List<Integer> taskQueue;
    private final int speed;
    private final String name;

    public Consumer(List<Integer> queue, int sleepTime, String name){
        this.taskQueue=queue;
        this.speed = sleepTime;
        this.name = name;
    }

    @Override
    public void run() {
        while(true){
            try{
                consume();
            } catch(InterruptedException ex){
                System.out.println("Caught Interrupted Exception while Consuming");
                ex.printStackTrace();
            }
        }
    }

    private void consume() throws InterruptedException{
        synchronized (taskQueue){
            while(taskQueue.isEmpty()){
                //give up the lock on taskQueue
                System.out.println("Current taskQueue is empty, waiting for new items.");
                taskQueue.wait();
            }

            int item = taskQueue.remove(0);
            System.out.println("Consumed item " + item + " in " + this);

            //release lock and notify other waited thread on the taskQueue when consumed item that reduce max size
            taskQueue.notifyAll();
        }
        System.out.println("After consume from "+ name +", still " + taskQueue.size() + " items pending in queue.");
        Thread.sleep(speed);
    }

    List<Integer> getTaskQueue(){
        return this.taskQueue;
    }

    @Override
    public String toString(){
        return "Consumer " + name;
    }
}
