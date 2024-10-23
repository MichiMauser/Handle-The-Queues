package org.example.Controller;

import org.example.Model.Client;
import org.example.Model.Queues;

import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Scheduler {
    private List<Queues> queueList = new ArrayList<>();
    private int maxQueues;
    private int maxClients;
    private Strategy strategy = new QueueStrategy(); // by default
    ExecutorService executor;
    public Scheduler(int maxQueues, int maxClients){
        executor = Executors.newFixedThreadPool(maxClients);
        for(int i = 0; i < maxQueues; i++){
            Queues queue = new Queues(i+1);
            queueList.add(queue);
            executor.execute(queue);
        }
    }
    public void changeStrategy(SelectionPolicy policy){
        if(policy == SelectionPolicy.SHORTEST_TIME) strategy = new TimeStrategy();
        if(policy == SelectionPolicy.SHORTEST_QUEUE) strategy = new QueueStrategy();
    }
    public void dispatchClient(Client c){
        //System.out.println(c.getID());
        if(strategy instanceof TimeStrategy ){
            strategy.addClient(this.queueList, c);

        }else if(strategy instanceof QueueStrategy){
            strategy.addClient(this.queueList, c);

        }
    }
    public List<Queues> getQueues(){
        return queueList;
    }



}
