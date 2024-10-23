package org.example.Controller;

import org.example.Model.Client;
import org.example.Model.Queues;

import java.util.List;

public class QueueStrategy implements Strategy{
    @Override
    public void addClient(List<Queues> listOfQ, Client client) {
        //System.out.println(client.getID());
        int minClients = Integer.MAX_VALUE;
        Queues queToAdd = new Queues();
        for(Queues que: listOfQ){
            int nrOfClients = que.getNrOfClients().intValue();
            if(minClients >= nrOfClients){
                minClients = nrOfClients;
                queToAdd = que;
            }
        }
        queToAdd.addClient(client);
    }

}
