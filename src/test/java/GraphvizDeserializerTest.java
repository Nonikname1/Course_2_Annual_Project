import com.example.socialgraph.GraphvizDeserializer;
import com.example.socialgraph.Node;
import com.example.socialgraph.SocialGraph;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;


class GraphvizDeserializerTest {

    private static final Function<String, Node> NODE = s -> new Node(s, s);

    @Test
    void shouldDeserializeNodesWithoutEdges() {
        String dot = """
                graph {
                  "A";
                  "B";
                }
                """;

        SocialGraph<Node> graph = GraphvizDeserializer.deserialize(dot, NODE);

        assertEquals(Set.of(NODE.apply("A"), NODE.apply("B")), graph.getAllNodes());
        assertFalse(graph.hasEdge(NODE.apply("A"), NODE.apply("B")));
    }

    @Test
    void shouldDeserializeSingleUndirectedEdge() {
        String dot = """
                graph {
                  "A";
                  "B";
                  "A" -- "B";
                }
                """;

        SocialGraph<Node> graph = GraphvizDeserializer.deserialize(dot, NODE);

        Node a = NODE.apply("A");
        Node b = NODE.apply("B");

        assertTrue(graph.getAllNodes().containsAll(Set.of(a, b)));
        assertTrue(graph.hasEdge(a, b));
        assertTrue(graph.hasEdge(b, a));          // неориентированный граф
    }

    @Test
    void shouldIgnoreDuplicatesAndSelfLoops() {
        String dot = """
                graph {
                  "A";
                  "A";               // повтор узла
                  "B";
                  "A" -- "B";
                  "A" -- "B";        // повтор ребра
                  "A" -- "A";        // петля — должна игнорироваться
                  }
                """;

        SocialGraph<Node> graph = GraphvizDeserializer.deserialize(dot, NODE);

        Node a = NODE.apply("A");
        Node b = NODE.apply("B");

        // узлы
        assertEquals(2, graph.getAllNodes().size());
        assertTrue(graph.getAllNodes().containsAll(Set.of(a, b)));

        // единственное ребро A-B
        assertTrue(graph.hasEdge(a, b));
        assertFalse(graph.hasEdge(a, a));
        assertFalse(graph.hasEdge(b, b));
    }
}
