package org.example.Controller;

import org.example.Model.Client;
import org.example.Model.Queues;

import java.util.List;

public class TimeStrategy implements  Strategy{
    @Override
    public void addClient(List<Queues> listOfQ, Client client) {
        int minWaitTime = Integer.MAX_VALUE;
        Queues queToAdd = new Queues();
        for(Queues que: listOfQ){
            int waitTime = que.getWaitPeriod().intValue();
            if(minWaitTime >=  waitTime){
                minWaitTime = waitTime;
                queToAdd = que;
            }
        }
        queToAdd.addClient(client);
    }
}
