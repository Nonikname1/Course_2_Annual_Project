package com.example.socialgraph;

import java.util.Set;

/**
 * Интерфейс для представления графа (неориентированного по умолчанию).
 * Определяет базовые операции для работы с графами.
 *
 * @param <T> тип элементов графа (должен корректно реализовывать equals() и hashCode())
 */
public interface Graph<T> {

    /**
     * Добавляет узел в граф.
     *
     * @param node узел для добавления
     * @return true если узел был добавлен, false если узел уже существует
     * @throws IllegalArgumentException если node равен null
     */
    boolean addNode(T node);

    /**
     * Добавляет ребро между двумя узлами графа.
     * Для неориентированного графа добавляет связь в обоих направлениях.
     *
     * @param from начальный узел
     * @param to конечный узел
     * @return true если ребро было добавлено, false если ребро уже существует
     * @throws IllegalArgumentException если любой из узлов равен null
     * @throws IllegalStateException если любой из узлов отсутствует в графе
     */
    boolean addEdge(T from, T to);

    /**
     * Возвращает множество узлов, смежных с заданным.
     *
     * @param node узел для поиска смежных
     * @return неизменяемое множество смежных узлов
     * @throws IllegalArgumentException если node равен null
     * @throws IllegalStateException если узла нет в графе
     */
    Set<T> getConnections(T node);

    /**
     * Проверяет наличие ребра между узлами.
     *
     * @param from начальный узел
     * @param to конечный узел
     * @return true если ребро существует, false в противном случае
     * @throws IllegalArgumentException если любой из узлов равен null
     * @throws IllegalStateException если любой из узлов отсутствует в графе
     */
    boolean hasEdge(T from, T to);

    /**
     * Возвращает все узлы графа.
     *
     * @return неизменяемое множество всех узлов графа
     */
    Set<T> getAllNodes();
}