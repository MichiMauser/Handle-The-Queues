package org.example.Controller;

import org.example.Model.Client;
import org.example.Model.Queues;

import java.util.List;

public interface Strategy {
    public void addClient(List<Queues> queues, Client client);
}
