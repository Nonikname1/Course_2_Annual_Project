package com.example.socialgraph;


public class Main {
    public static void main(String[] args) {
        try {
            // Генерация случайного графа из 10 узлов, до 3 связей на узел
            Graph<Node> graph = GraphGenerator.generateRandomGraph(10, 3);

            // Сохраняем .dot
            String dotPath = "random-graph.dot";
            String pngPath = "random-graph.png";
            GraphFileWriter.writeToDotFile(graph, dotPath);
            System.out.println("DOT-файл создан: " + dotPath);

            // Генерация .png через Graphviz CLI
            GraphvizRenderer.generatePng(dotPath, pngPath);
            System.out.println("Граф визуализирован: " + pngPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
