package com.example.socialgraph.vizual;

import com.example.socialgraph.Graph;
import com.example.socialgraph.Node;
import com.example.socialgraph.SocialGraph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class GraphPanel extends JPanel {
    private final Map<Node, Point> nodePositions = new HashMap<>();
    private final List<Node> nodes = new ArrayList<>();
    private Graph<Node> graph = new SocialGraph<>();
    private final List<Node> selectedNodes = new ArrayList<>();
    private int nodeCounter = 0;

    public GraphPanel() {
        setBackground(Color.WHITE);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Node clicked = getNodeAt(e.getPoint());
                if (clicked != null) {
                    toggleSelection(clicked);
                } else {
                    String id = "" + nodeCounter;
                    String name = "User " + nodeCounter;
                    Node newNode = new Node(id, name);

                    if (graph.addNode(newNode)) {
                        nodePositions.put(newNode, e.getPoint());
                        nodes.add(newNode);
                        nodeCounter++;
                        repaint();
                    }
                }
            }
        });
    }

    private Node getNodeAt(Point point) {
        for (Node node : nodes) {
            Point p = nodePositions.get(node);
            if (p != null && p.distance(point) <= 15) return node;
        }
        return null;
    }

    private void toggleSelection(Node node) {
        if (selectedNodes.contains(node)) selectedNodes.remove(node);
        else if (selectedNodes.size() < 2) selectedNodes.add(node);
        else {
            selectedNodes.clear();
            selectedNodes.add(node);
        }
        repaint();
    }

    public void connectSelectedNodes() {
        if (selectedNodes.size() == 2) {
            Node n1 = selectedNodes.get(0);
            Node n2 = selectedNodes.get(1);
            if (!graph.hasEdge(n1, n2)) {
                graph.addEdge(n1, n2);
            }
        }
        selectedNodes.clear();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Set<String> drawnEdges = new HashSet<>();
        for (Node from : nodes) {
            for (Node to : graph.getConnections(from)) {
                String key = from.toString().compareTo(to.toString()) < 0 ?
                        from + "--" + to :
                        to + "--" + from;
                if (!drawnEdges.contains(key)) {
                    Point p1 = nodePositions.get(from);
                    Point p2 = nodePositions.get(to);
                    if (p1 != null && p2 != null) {
                        g2.setColor(Color.GRAY);
                        g2.drawLine(p1.x, p1.y, p2.x, p2.y);
                        drawnEdges.add(key);
                    }
                }
            }
        }

        for (Node node : nodes) {
            Point p = nodePositions.get(node);
            if (p == null) continue;

            boolean selected = selectedNodes.contains(node);
            g2.setColor(selected ? Color.GREEN : Color.ORANGE);
            g2.fillOval(p.x - 15, p.y - 15, 30, 30);

            g2.setColor(Color.BLACK);
            g2.drawOval(p.x - 15, p.y - 15, 30, 30);
            g2.drawString(node.getName(), p.x - 20, p.y - 20);
        }
    }

    public Graph<Node> getGraph() {
        return graph;
    }

    public void setGraph(Graph<Node> newGraph) {
        this.graph = newGraph;
        nodes.clear();
        nodePositions.clear();
        nodes.addAll(graph.getAllNodes());

        nodeCounter = nodes.size();

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int radius = Math.min(centerX, centerY) - 50;
        int n = nodes.size();

        for (int i = 0; i < n; i++) {
            double angle = 2 * Math.PI * i / n;
            int x = centerX + (int) (radius * Math.cos(angle));
            int y = centerY + (int) (radius * Math.sin(angle));
            nodePositions.put(nodes.get(i), new Point(x, y));
        }
        selectedNodes.clear();
        repaint();
    }
}
