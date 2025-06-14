package com.example.socialgraph;

import java.util.Scanner;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Класс для десериализации графов из формата DOT (Graphviz).
 * Позволяет преобразовывать описание графа в формате DOT в объект SocialGraph.
 */
public class GraphvizDeserializer {

    /**
     * Десериализует граф из строки в формате DOT.
     *
     * @param <T> тип узлов графа
     * @param dot строка с описанием графа в формате DOT
     * @param stringToNode функция преобразования строки в узел графа типа T
     * @return граф SocialGraph<T>, построенный на основе входных данных
     * @throws IllegalArgumentException если входная строка имеет некорректный формат
     *
     * @example Пример использования:
     * {@code
     * SocialGraph<Node> graph = GraphvizDeserializer.deserialize(
     *     "graph { \"A\"; \"B\"; \"A\" -- \"B\"; }",
     *     str -> new Node(str, str));
     * }
     *
     * @description Поддерживается следующий синтаксис DOT:
     * - Объявление узла: "node_id";
     * - Объявление ребра: "node1" -- "node2";
     */
    public static <T> SocialGraph<T> deserialize(String dot, Function<String, T> stringToNode) {
        SocialGraph<T> graph = new SocialGraph<>();
        Pattern nodePattern = Pattern.compile("\\s*\"([^\"]+)\"\\s*;");
        Pattern edgePattern = Pattern.compile("\\s*\"([^\"]+)\"\\s*--\\s*\"([^\"]+)\"\\s*;");

        Scanner scanner = new Scanner(dot);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();

            Matcher edgeMatcher = edgePattern.matcher(line);
            Matcher nodeMatcher = nodePattern.matcher(line);

            if (edgeMatcher.matches()) {
                String str1 = edgeMatcher.group(1);
                String str2 = edgeMatcher.group(2);
                T node1 = stringToNode.apply(str1);
                T node2 = stringToNode.apply(str2);
                graph.addNode(node1);
                graph.addNode(node2);
                graph.addEdge(node1, node2);
            } else if (nodeMatcher.matches()) {
                String str = nodeMatcher.group(1);
                T node = stringToNode.apply(str);
                graph.addNode(node);
            }
        }

        return graph;
    }
}