/**
 * Реализация интерфейса Graph, представляющая социальный граф (неориентированный граф).
 * Граф хранит связи между узлами в виде списка смежности.
 *
 * @param <T> тип элементов в графе (должен быть неизменяемым и правильно реализовывать equals и hashCode)
 */
package com.example.socialgraph;

import java.util.*;

public class SocialGraph<T> implements Graph<T> {

    private final Map<T, Set<T>> adjacencyList = new HashMap<>();

    /**
     * Добавляет узел в граф.
     *
     * @param node узел для добавления
     * @return true если узел был добавлен, false если узел уже существует или равен null
     */
    @Override
    public boolean addNode(T node) {
        if (node == null) return false;
        if (adjacencyList.containsKey(node)) return false;
        adjacencyList.put(node, new HashSet<>());
        return true;
    }

    /**
     * Добавляет ребро между двумя узлами в графе (неориентированное ребро).
     *
     * @param from начальный узел
     * @param to конечный узел
     * @return true если ребро было добавлено, false если:
     *         - любой из узлов равен null
     *         - любой из узлов отсутствует в графе
     *         - узлы совпадают (петли не допускаются)
     */
    @Override
    public boolean addEdge(T from, T to) {
        if (from == null || to == null) return false;
        if (!adjacencyList.containsKey(from) || !adjacencyList.containsKey(to)) return false;
        if (from.equals(to)) return false; // избегаем петель

        // неориентированный граф: добавляем связь в обе стороны
        boolean changed = adjacencyList.get(from).add(to);
        adjacencyList.get(to).add(from);
        return changed;
    }

    /**
     * Возвращает множество узлов, связанных с заданным узлом.
     *
     * @param node узел, для которого запрашиваются связи
     * @return неизменяемое множество связанных узлов, или пустое множество если:
     *         - узел равен null
     *         - узел отсутствует в графе
     */
    @Override
    public Set<T> getConnections(T node) {
        if (node == null || !adjacencyList.containsKey(node)) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(adjacencyList.get(node));
    }

    /**
     * Проверяет наличие ребра между двумя узлами.
     *
     * @param from начальный узел
     * @param to конечный узел
     * @return true если ребро существует, false если:
     *         - любой из узлов равен null
     *         - начальный узел отсутствует в графе
     */
    @Override
    public boolean hasEdge(T from, T to) {
        if (from == null || to == null) return false;
        if (!adjacencyList.containsKey(from)) return false;
        return adjacencyList.get(from).contains(to);
    }

    /**
     * Возвращает все узлы графа.
     *
     * @return неизменяемое множество всех узлов графа
     */
    @Override
    public Set<T> getAllNodes() {
        return Collections.unmodifiableSet(adjacencyList.keySet());
    }
}