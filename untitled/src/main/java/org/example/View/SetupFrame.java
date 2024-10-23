package org.example.View;

import org.example.Controller.SelectionPolicy;
import org.example.Controller.SimulationManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SetupFrame extends JFrame implements ActionListener{
    // User input: no clients, no queues, simulation interval, min max arrive time, min max service time
    GridBagConstraints grid = new GridBagConstraints();

    JTextField clText = new JTextField("");
    JTextField qText= new JTextField("");
    JTextField simText = new JTextField("");
    JTextField minAText = new JTextField("");
    JTextField maxAText = new JTextField("");
    JTextField minSText = new JTextField("");
    JTextField maxSText = new JTextField("");
    JButton startSimulation = new JButton("Start Simulation");

    String[] strategies = {"Time Strategy", "Shortest Queue Strategy"};
    JComboBox strategyList = new JComboBox(strategies); // button

    SimulationManager simulationManager;
    public SetupFrame(SimulationManager simulationManager) {
        this.simulationManager = simulationManager;
        setLayout(new GridBagLayout());
        Insets insets = new Insets(1,5,1,5);

        grid.insets = insets;
        grid.gridx = 0;
        grid.gridy = 0;
        add(new JLabel("No Clients"),grid);

        grid.gridx = 0;
        grid.gridy = 1;
        add(new JLabel("No Queues"),grid);

        grid.gridx = 0;
        grid.gridy = 2;
        add(new JLabel("Simulation time"),grid);

        grid.gridx = 0;
        grid.gridy = 3;
        add(new JLabel("Min arrive time"),grid);

        grid.gridx = 0;
        grid.gridy = 4;
        add(new JLabel("Max arrive time"),grid);

        grid.gridx = 0;
        grid.gridy = 5;
        add(new JLabel("Min service time"),grid);

        grid.gridx = 0;
        grid.gridy = 6;
        add(new JLabel("Max service time"),grid);

        grid.gridx = 0;
        grid.gridy = 7;
        add(new JLabel("Strategy"),grid);

        grid.gridx = 2;
        grid.gridy = 0;
        clText.setPreferredSize(new Dimension(30,20));
        add(clText,grid);

        grid.gridx = 2;
        grid.gridy = 1;
        qText.setPreferredSize(new Dimension(30,20));
        add(qText,grid);

        grid.gridx = 2;
        grid.gridy = 2;
        simText.setPreferredSize(new Dimension(30,20));
        add(simText,grid);

        grid.gridx = 2;
        grid.gridy = 3;
        minAText.setPreferredSize(new Dimension(30,20));
        add(minAText,grid);

        grid.gridx = 2;
        grid.gridy = 4;
        maxAText.setPreferredSize(new Dimension(30,20));
        add(maxAText,grid);

        grid.gridx = 2;
        grid.gridy = 5;
        minSText.setPreferredSize(new Dimension(30,20));
        add(minSText,grid);

        grid.gridx = 2;
        grid.gridy = 6;
        maxSText.setPreferredSize(new Dimension(30,20));
        add(maxSText,grid);

        grid.gridx = 2;
        grid.gridy = 7;
       // straText.setPreferredSize(new Dimension(30,20));
        add(strategyList,grid);

        grid.gridx = 1;
        grid.gridy = 8;
        startSimulation.addActionListener(this);
        add(startSimulation, grid);


        pack();
        setSize(500,400);
        setTitle("QueueManager");
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String args[]){
        SetupFrame setupFrame = new SetupFrame(new SimulationManager());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            simulationManager.maxProcessingTime = Integer.parseInt(maxSText.getText());
            simulationManager.minProcessingTime = Integer.parseInt(minSText.getText());
            simulationManager.maxArriveTime = Integer.parseInt(maxAText.getText());
            simulationManager.minArriveTime = Integer.parseInt(minAText.getText());
            simulationManager.timeLimit = Integer.parseInt(simText.getText());
            simulationManager.nrOfClients = Integer.parseInt(clText.getText());
            simulationManager.nrOfQueues = Integer.parseInt(qText.getText());
            String selectedItem = (String) strategyList.getSelectedItem();
            if (selectedItem.equals("Time Strategy")) {
                simulationManager.policy = SelectionPolicy.SHORTEST_TIME;
            } else {
                simulationManager.policy = SelectionPolicy.SHORTEST_QUEUE;
            }

        }catch (NumberFormatException ev){
            System.out.println(ev);
            JOptionPane.showMessageDialog(null,"Complete all the fields!","Error",JOptionPane.ERROR_MESSAGE);
        }
        simulationManager.setUp();
        ExecutorService executorService;
        executorService = Executors.newFixedThreadPool(1);
        executorService.execute(simulationManager);

    }
}
