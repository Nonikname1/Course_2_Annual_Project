package com.example.socialgraph.vizual;

import com.example.socialgraph.Graph;
import com.example.socialgraph.GraphFileReader;
import com.example.socialgraph.GraphFileWriter;
import com.example.socialgraph.Node;
import com.example.socialgraph.GraphvizRenderer;
import com.example.socialgraph.GraphGenerator;


import javax.swing.*;
import java.awt.*;

import java.io.File;
import java.io.IOException;

public class GraphVisualizerApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Граф друзей");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 700);

            GraphPanel graphPanel = new GraphPanel();

            JButton addFriendButton = new JButton("Добавить в друзья");
            addFriendButton.addActionListener(e -> graphPanel.connectSelectedNodes());


            JPanel controlPanel = new JPanel();
            controlPanel.add(addFriendButton);

            frame.setLayout(new BorderLayout());
            frame.add(graphPanel, BorderLayout.CENTER);
            frame.add(controlPanel, BorderLayout.SOUTH);

            JMenuBar menuBar = new JMenuBar();
            JMenu fileMenu = new JMenu("Файл");

            String projectDir = System.getProperty("user.dir");

            JMenuItem loadItem = new JMenuItem("Загрузить из .dot");
            loadItem.addActionListener(e -> {
                JFileChooser chooser = new JFileChooser(projectDir);
                int res = chooser.showOpenDialog(frame);
                if (res == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    try {
                        Graph<Node> loadedGraph = GraphFileReader.readFromDotFile(
                                file.getAbsolutePath(),
                                s -> {
                                    String name = s.split("\\s*\\(")[0].trim();
                                    return new Node(name, name);
                                }
                        );
                        graphPanel.setGraph(loadedGraph);
                        graphPanel.repaint();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(frame, "Ошибка загрузки файла: " + ex.getMessage());
                    }
                }
            });

            SpinnerNumberModel nodesModel = new SpinnerNumberModel(10, 1, 100, 1);
            JSpinner numberOfNodesSpinner = new JSpinner(nodesModel);
            JLabel nodesLabel = new JLabel("Количество узлов:");

            SpinnerNumberModel connectionsModel = new SpinnerNumberModel(3, 0, 100, 1);
            JSpinner maxConnectionsSpinner = new JSpinner(connectionsModel);
            JLabel connectionsLabel = new JLabel("Максимум связей на узел:");

            JButton generateGraphButton = new JButton("Сгенерировать граф");
            generateGraphButton.addActionListener(e -> {
                try {
                    int numberOfNodes = (Integer) numberOfNodesSpinner.getValue();
                    int maxConnections = (Integer) maxConnectionsSpinner.getValue();

                    Graph<Node> randomGraph = GraphGenerator.generateRandomGraph(numberOfNodes, maxConnections);
                    graphPanel.setGraph(randomGraph);
                    graphPanel.repaint();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Ошибка генерации графа: " + ex.getMessage());
                }
            });


            JMenuItem saveItem = new JMenuItem("Сохранить в .dot");
            saveItem.addActionListener(e -> {
                JFileChooser chooser = new JFileChooser(projectDir);
                int res = chooser.showSaveDialog(frame);
                if (res == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    try {
                        GraphFileWriter.writeToDotFile(graphPanel.getGraph(), file.getAbsolutePath());
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(frame, "Ошибка сохранения файла: " + ex.getMessage());
                    }
                }
            });

            JMenuItem savePngItem = new JMenuItem("Сохранить как PNG");
            savePngItem.addActionListener(e -> {
                JFileChooser chooser = new JFileChooser(projectDir);
                chooser.setDialogTitle("Сохранить PNG");
                int res = chooser.showSaveDialog(frame);
                if (res == JFileChooser.APPROVE_OPTION) {
                    File pngFile = chooser.getSelectedFile();
                    String pngPath = pngFile.getAbsolutePath();
                    if (!pngPath.toLowerCase().endsWith(".png")) {
                        pngPath += ".png";
                    }

                    String tempDotPath = pngPath + ".temp.dot";

                    try {
                        // Сохраняем временный .dot файл
                        GraphFileWriter.writeToDotFile(graphPanel.getGraph(), tempDotPath);

                        // Генерируем PNG через Graphviz
                        GraphvizRenderer.generatePng(tempDotPath, pngPath);

                        // Удаляем временный файл
                        new File(tempDotPath).delete();

                        JOptionPane.showMessageDialog(frame, "PNG успешно сохранён:\n" + pngPath);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frame, "Ошибка при экспорте PNG:\n" + ex.getMessage());
                    }
                }
            });



            fileMenu.add(loadItem);
            fileMenu.add(saveItem);
            fileMenu.add(savePngItem);
            menuBar.add(fileMenu);
            frame.setJMenuBar(menuBar);
            controlPanel.add(generateGraphButton);
            controlPanel.add(nodesLabel);
            controlPanel.add(numberOfNodesSpinner);
            controlPanel.add(connectionsLabel);
            controlPanel.add(maxConnectionsSpinner);
            frame.setVisible(true);
        });
    }
}
