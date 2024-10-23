package org.example.Controller;

import org.example.Model.Client;
import org.example.Model.Queues;
import org.example.View.SetupFrame;
import org.example.View.SimulationFrame;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Time;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class SimulationManager implements Runnable{

    public int timeLimit; // simulation time
    public int maxProcessingTime;
    public int minProcessingTime;
    public int nrOfQueues;
    public int nrOfClients;
    public int maxArriveTime;
    public int minArriveTime;
    public SelectionPolicy policy = SelectionPolicy.SHORTEST_TIME;
    private Scheduler scheduler;
    private SimulationFrame simulationFrame;
    private List<Client> clientList = new ArrayList<>();
    SetupFrame setupFrame;
    public SimulationManager(){
        setupFrame = new SetupFrame(this);
    }
    // setUp method for intializing the wanted data
    public void setUp(){
        scheduler = new Scheduler(nrOfQueues, nrOfClients);
        // System.out.println(nrOfClients+" "+nrOfQueues);
        scheduler.changeStrategy(policy);
        generateRandomClients();
        simulationFrame = new SimulationFrame(nrOfQueues);  // added last

    }
    private void generateRandomClients(){
        Random random = new Random();
        for(int i = 0; i < nrOfClients; i++){
            int serviceWorkTime = random.nextInt(minProcessingTime, maxProcessingTime + 1);
            int arriveTime = random.nextInt(minArriveTime,maxArriveTime + 1);
            clientList.add(new Client(i+1, arriveTime , serviceWorkTime));
        }
        Collections.sort(clientList);
    }

    @Override
    public void run() {
        int currTime = 0;
        int maxClients = -1;
        int peakHour = 0;
        int waitingTime = 0;
        int nrOfClientsIn = 0;
        try {
            FileWriter file = new FileWriter("output.txt");

            while(currTime < timeLimit){

                System.out.println("Time "+currTime);
                file.write("Time "+currTime+"\n");
                Iterator<Client> iterator = clientList.iterator();

                // parcurgere
                while(iterator.hasNext()){

                    Client iterClient = iterator.next();
                    if(iterClient.getArrivalTime() == currTime){
                        iterator.remove();
                        scheduler.dispatchClient(iterClient);
                    }
                }
                simulationFrame.updateQueues(scheduler.getQueues());
                simulationFrame.updateTime(currTime);
                simulationFrame.updateClientList(clientList);

                // print list of clients
                System.out.print("Waiting clients: ");
                file.write("Waiting clients: ");
                for(Client c: clientList){
                    System.out.print(c);
                    file.write(c.toString());
                }
                System.out.println();
                file.write("\n");
                // printare queue closed/open
                for(Queues queues: scheduler.getQueues()){
                    if(queues.getNrOfClients().intValue() == 0){
                        System.out.println("Queue "+queues.getID()+": closed");
                        file.write("Queue "+queues.getID()+": closed\n");
                    }else{
                        System.out.print("Queue "+queues.getID()+": ");
                        System.out.println(queues.getClient());

                        file.write("Queue "+queues.getID()+": ");
                        file.write(queues.getClient().toString()+"\n");
                    }
                }

                //peak hour
                int currentMaxClients = 0;
                for(Queues queues: scheduler.getQueues()){
                    currentMaxClients += queues.getNrOfClients().intValue();
                }

                if(maxClients <= currentMaxClients){
                    maxClients = currentMaxClients;
                    peakHour = currTime;
                }

                System.out.println();
                file.write("\n");

                //stopping the simulation if all clients are processed
                boolean stopRunning = true;
                for(Queues q: scheduler.getQueues()){
                    if(q.getClient() != null)
                        stopRunning = false;
                }
                if(stopRunning && clientList.isEmpty()){
                    break;
                }

                currTime++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
            System.out.println(Queues.getAllServiceTime().floatValue()/Queues.getOutOfQueue().get());
            file.write("\n"+"Peak hour "+peakHour+"\n");
            file.write("Avg service time "+Queues.getAllServiceTime().floatValue()/Queues.getOutOfQueue().get()+"\n");
            file.write("Avg waiting time "+Queues.getAllwaitingTime().floatValue()/Queues.getInQueue().get());
            file.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public static void main(String[] args){
        SimulationManager sim = new SimulationManager();

    }
}
