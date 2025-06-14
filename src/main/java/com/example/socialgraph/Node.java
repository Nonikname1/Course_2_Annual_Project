package com.example.socialgraph;

import java.util.Objects;

/**
 * Класс, представляющий узел (вершину) в социальном графе.
 * Узел идентифицируется по уникальному идентификатору (id) и имеет имя (name).
 * Сравнение узлов производится только по их идентификатору.
 */
public class Node {
    private final String id;
    private final String name;

    /**
     * Создает новый узел с указанным идентификатором и именем.
     *
     * @param id уникальный идентификатор узла (не может быть null)
     * @param name имя узла (не может быть null)
     * @throws IllegalArgumentException если id или name равны null
     */
    public Node(String id, String name) {
        if (id == null || name == null) {
            throw new IllegalArgumentException("id and name cannot be null");
        }
        this.id = id;
        this.name = name;
    }

    /**
     * Возвращает идентификатор узла.
     *
     * @return идентификатор узла
     */
    public String getId() { return id; }

    /**
     * Возвращает имя узла.
     *
     * @return имя узла
     */
    public String getName() { return name; }

    /**
     * Сравнивает узлы по их идентификатору.
     *
     * @param o объект для сравнения
     * @return true если узлы имеют одинаковый идентификатор, false в противном случае
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node node)) return false;
        return id.equals(node.id);
    }

    /**
     * Возвращает хеш-код узла, основанный на его идентификаторе.
     *
     * @return хеш-код узла
     */
    @Override
    public int hashCode() { return Objects.hash(id); }

    /**
     * Возвращает строковое представление узла в формате "имя (идентификатор)".
     *
     * @return строковое представление узла
     */
    @Override
    public String toString() {
        return name + " (" + id + ")";
    }
}