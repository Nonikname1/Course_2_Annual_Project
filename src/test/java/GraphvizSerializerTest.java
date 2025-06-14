import com.example.socialgraph.GraphvizSerializer;
import com.example.socialgraph.Node;
import com.example.socialgraph.SocialGraph;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


class GraphvizSerializerTest {

    @Test
    void serializeMustThrowOnNullGraph() {
        assertThrows(IllegalArgumentException.class,
                () -> GraphvizSerializer.serialize(null));
    }

    @Test
    void serializeGraphWithNodesButNoEdges() {
        SocialGraph<String> graph = new SocialGraph<>();
        graph.addNode("A");
        graph.addNode("B");

        String dot = GraphvizSerializer.serialize(graph);

        // заголовок
        assertTrue(dot.startsWith("graph SocialGraph"));
        // узлы
        assertTrue(dot.contains("\"A\";"));
        assertTrue(dot.contains("\"B\";"));
        // отсутствие рёбер
        assertFalse(dot.contains("--"));
    }

    @Test
    void serializeAddsEachUndirectedEdgeOnlyOnce() {
        SocialGraph<Node> graph = new SocialGraph<>();
        Node alice = new Node("1", "Alice");
        Node bob   = new Node("2", "Bob");

        graph.addNode(alice);
        graph.addNode(bob);
        graph.addEdge(alice, bob);   // первое добавление
        graph.addEdge(bob, alice);   // попытка дублирования

        String dot = GraphvizSerializer.serialize(graph);

        long edgeCount = Arrays.stream(dot.split("\\R"))
                .filter(l -> l.contains("\"Alice (1)\" -- \"Bob (2)\"")
                        || l.contains("\"Bob (2)\" -- \"Alice (1)\""))
                .count();

        assertEquals(1, edgeCount, "Ребро должно присутствовать ровно один раз");
    }
}
