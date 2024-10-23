package org.example.View;

import org.example.Model.Client;
import org.example.Model.Queues;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class SimulationFrame extends JFrame {
    private JPanel simulationPanel;
    private Map<Integer, JLabel> queueLabels;
    private JLabel timeLabel;
    private JLabel clientLabel = new JLabel("");
    public SimulationFrame(int nrOfQueues) {
        setTitle("Simulation");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        queueLabels = new HashMap<>();

        simulationPanel = new JPanel();
        simulationPanel.setLayout(new GridLayout(1, nrOfQueues + 1));

        for (int i = 1; i <= nrOfQueues; i++) {
            JLabel queueLabel = new JLabel("Q" + i + ":\n");
            queueLabels.put(i, queueLabel);
            simulationPanel.add(queueLabel);
        }
        timeLabel = new JLabel("Time: 0");
        getContentPane().add(simulationPanel, BorderLayout.CENTER);
        getContentPane().add(timeLabel, BorderLayout.NORTH);
        getContentPane().add(clientLabel,BorderLayout.SOUTH);

        setVisible(true);
    }
    public void updateQueues(List<Queues> queues) {
        for (Queues queue : queues) {
            JLabel queueLabel = queueLabels.get(queue.getID());
            StringBuilder queueText = new StringBuilder("<html><div style='text-align: left;'>");
            if (queue.getClient() != null) {
                Client client = queue.getClient();
                queueText.append("<span style='color: black;'>Q").append(queue.getID()).append(":</span><br/>");
                queueText.append("<span style='color: green;'>C").append(client.getID()).append(": (").append(client.getArrivalTime()).append(", ").append(client.getServiceTime()).append(")</span><br/>");
            } else {
                queueText.append("<span style='color: black;'>Q").append(queue.getID()).append(":</span><br/>");
            }
            for (Client client : queue.getClients()) {
                queueText.append("<span style='color: yellow;'>C").append(client.getID()).append(": (").append(client.getArrivalTime()).append(", ").append(client.getServiceTime()).append(")</span><br/>");
            }
            queueText.append("</div></html>");
            queueLabel.setText(queueText.toString());
        }
        revalidate();
        repaint();
    }
    public void updateTime(int time){
        timeLabel.setText("Time: " + time);
    }
    public void updateClientList(List<Client> clients){
        StringBuilder labelText = new StringBuilder("<html>");
        for (Client client : clients) {
            labelText.append("C").append(client.getID()).append(": (").append(client.getArrivalTime()).append(", ").append(client.getServiceTime()).append(")<br/>");
        }
        labelText.append("</html>");

        // Assuming there's a label to display the client list
        clientLabel.setText(labelText.toString());
    }
}