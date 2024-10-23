package org.example.Model;

public class Client implements Comparable<Client>{
    private int ID;
    private int arrivalTime;
    private int serviceTime;
    private int workTime;

    private int waitingTime;


    public Client(int ID, int arrivalTime, int serviceTime){
        this.ID = ID;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
        this.workTime = serviceTime;
        this.waitingTime = 0;

    }
    public Client(){
        this.ID = -1;
    }
    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getID() {
        return ID;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    @Override
    public String toString() {
        return "("+ID+","+arrivalTime+","+serviceTime+");";
    }

    @Override
    public int compareTo(Client o) {
        return this.arrivalTime - o.arrivalTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    public int getWorkTime() {
        return workTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }
}
