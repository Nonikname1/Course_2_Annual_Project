package com.example.socialgraph;

import java.util.*;

/**
 * Класс для сериализации графов в формат DOT (Graphviz).
 * Преобразует объект графа в строковое представление, пригодное для визуализации.
 */
public class GraphvizSerializer {

    /**
     * Сериализует граф в строку формата DOT.
     *
     * @param <T> тип узлов графа
     * @param graph граф для сериализации (не может быть null)
     * @return строковое представление графа в формате DOT
     * @throws IllegalArgumentException если переданный граф равен null
     *
     * @example Пример результата:
     * <pre>
     * graph SocialGraph {
     *     "Узел 1";
     *     "Узел 2";
     *     "Узел 1" -- "Узел 2";
     * }
     * </pre>
     *
     * @description Формат вывода:
     * 1. Все узлы перечисляются отдельными строками
     * 2. Все связи между узлами добавляются после узлов
     * 3. Каждое ребро добавляется только один раз (для неориентированных графов)
     */
    public static <T> String serialize(Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Граф не может быть null");
        }

        StringBuilder builder = new StringBuilder();
        builder.append("graph SocialGraph {\n");

        // Сначала добавляем все узлы
        for (T node : graph.getAllNodes()) {
            builder.append("    \"").append(node.toString()).append("\";\n");
        }

        // Затем добавляем все связи
        Set<String> addedEdges = new HashSet<>();
        for (T node : graph.getAllNodes()) {
            for (T neighbor : graph.getConnections(node)) {
                String edgeKey = formatEdgeKey(node, neighbor);
                if (!addedEdges.contains(edgeKey)) {
                    builder.append("    \"").append(node).append("\" -- \"")
                            .append(neighbor).append("\";\n");
                    addedEdges.add(edgeKey);
                }
            }
        }

        builder.append("}");
        return builder.toString();
    }

    /**
     * Формирует уникальный ключ для ребра между двумя узлами.
     * Для неориентированного графа порядок узлов не важен.
     *
     * @param <T> тип узлов
     * @param a первый узел
     * @param b второй узел
     * @return строковый ключ вида "node1--node2" (узлы отсортированы)
     */
    private static <T> String formatEdgeKey(T a, T b) {
        List<String> pair = new ArrayList<>(Arrays.asList(a.toString(), b.toString()));
        Collections.sort(pair);
        return pair.get(0) + "--" + pair.get(1);
    }
}