package org.example.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Queues implements  Runnable {
    private BlockingQueue<Client> clientsQueue = new LinkedBlockingQueue<>();
    private AtomicInteger waitPeriod = new AtomicInteger();
    private AtomicInteger nrOfClients =  new AtomicInteger();
    private int ID ;
    private Client client;

    private static AtomicInteger allServiceTime = new AtomicInteger(0);
    private static AtomicInteger allwaitingTime = new AtomicInteger(0);
    private static AtomicInteger inQueue = new AtomicInteger(0);
    private static AtomicInteger outOfQueue = new AtomicInteger(0);

    public Queues(int ID){
        this.waitPeriod.set(0);
        this.ID = ID;
    }
    public Queues(){
        this.waitPeriod.set(0);
    }
    public void addClient(Client c){
        this.clientsQueue.add(c);
        c.setWaitingTime(waitPeriod.get());
        this.waitPeriod.addAndGet(c.getServiceTime());
        this.nrOfClients.addAndGet(1);
    }


    @Override
    public void run() {
        while(true) {

                try {
                    client = clientsQueue.take();
                    inQueue.addAndGet(1);
                    allwaitingTime.addAndGet(client.getWaitingTime());

                    int service = client.getServiceTime();
                    while (service > 0){
                        Thread.sleep(1000);
                        client.setServiceTime(client.getServiceTime() - 1);
                        waitPeriod.decrementAndGet();
                        service--;
                    }
                   // System.out.println(client);
                    outOfQueue.addAndGet(1);
                    allServiceTime.addAndGet(client.getWorkTime());
                    client = null;
                    this.nrOfClients.decrementAndGet();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
        }
    }

    public List<Client> getClients(){
        return new ArrayList<>(clientsQueue);
    }

    public AtomicInteger getNrOfClients() {
        return nrOfClients;
    }
    public AtomicInteger getWaitPeriod(){
        return waitPeriod;
    }

    public int getID() {
        return ID;
    }

    public Client getClient() {
        return client;
    }

    public static AtomicInteger getOutOfQueue() {
        return outOfQueue;
    }
    public static AtomicInteger getAllServiceTime(){
        return allServiceTime;
    }

    public static AtomicInteger getAllwaitingTime() {
        return allwaitingTime;
    }

    public static AtomicInteger getInQueue() {
        return inQueue;
    }
}
