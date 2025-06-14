package com.example.socialgraph;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Генератор случайных социальных графов.
 * Позволяет создавать графы с заданным количеством узлов и связей между ними.
 * Имена для узлов загружаются из внешнего файла.
 */
public class GraphGenerator {

    /**
     * Генерирует случайный неориентированный граф с указанными параметрами.
     *
     * @param numberOfNodes количество узлов в графе (должно быть положительным)
     * @param maxConnectionsPerNode максимальное количество связей на один узел (не может быть отрицательным)
     * @return сгенерированный граф типа SocialGraph<Node>
     * @throws IllegalArgumentException если параметры некорректны
     */
    public static Graph<Node> generateRandomGraph(int numberOfNodes, int maxConnectionsPerNode) {
        if (numberOfNodes <= 0 || maxConnectionsPerNode < 0) {
            throw new IllegalArgumentException("Invalid parameters");
        }

        List<String> names = loadNamesFromFile("name.txt", numberOfNodes);

        Graph<Node> graph = new SocialGraph<>();
        Random random = new Random();
        List<Node> nodes = new ArrayList<>();

        // Создание узлов
        for (int i = 0; i < numberOfNodes; i++) {
            Node node = new Node(String.valueOf(i), names.get(i));
            graph.addNode(node);
            nodes.add(node);
        }

        // Создание связей между узлами
        for (Node from : nodes) {
            int connections = random.nextInt(maxConnectionsPerNode + 1);
            int attempts = 0;
            while (graph.getConnections(from).size() < connections && attempts < nodes.size() * 2) {
                Node to = nodes.get(random.nextInt(nodes.size()));
                if (!from.equals(to) && !graph.hasEdge(from, to)) {
                    graph.addEdge(from, to);
                }
                attempts++;
            }
        }

        return graph;
    }

    /**
     * Загружает список имен из файла.
     *
     * @param filePath путь к файлу с именами
     * @param requiredCount требуемое количество имен
     * @return список имен из файла
     * @throws IllegalArgumentException если в файле недостаточно имен
     * @throws RuntimeException если произошла ошибка при чтении файла
     */
    private static List<String> loadNamesFromFile(String filePath, int requiredCount) {
        try {
            List<String> names = Files.readAllLines(Paths.get(filePath));
            if (names.size() < requiredCount) {
                throw new IllegalArgumentException("Недостаточно имён в " + filePath +
                        ". Требуется: " + requiredCount + ", найдено: " + names.size());
            }
            return names;
        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения файла " + filePath + ": " + e.getMessage(), e);
        }
    }
}