import com.example.socialgraph.Graph;
import com.example.socialgraph.GraphGenerator;
import com.example.socialgraph.Node;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class GraphGeneratorTest {

    @Test
    void testGenerateGraphWithZeroNodesThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            GraphGenerator.generateRandomGraph(0, 5);
        });
    }

    @Test
    void testGenerateGraphWithNegativeNodesThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            GraphGenerator.generateRandomGraph(-1, 5);
        });
    }

    @Test
    void testGenerateGraphWithNegativeConnectionsThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            GraphGenerator.generateRandomGraph(10, -1);
        });
    }

    @Test
    void testGenerateGraphWithOneNodeCreatesEmptyGraph() {
        Graph<Node> graph = GraphGenerator.generateRandomGraph(1, 0);
        assertEquals(1, graph.getAllNodes().size());
        assertTrue(graph.getConnections(graph.getAllNodes().iterator().next()).isEmpty());
    }

    @Test
    void testGenerateGraphWithZeroConnectionsCreatesNodesOnly() {
        int nodeCount = 10;
        Graph<Node> graph = GraphGenerator.generateRandomGraph(nodeCount, 0);
        assertEquals(nodeCount, graph.getAllNodes().size());

        for (Node node : graph.getAllNodes()) {
            assertTrue(graph.getConnections(node).isEmpty());
        }
    }

    @Test
    void testGraphContainsCorrectNumberOfNodes() {
        int nodeCount = 7;
        Graph<Node> graph = GraphGenerator.generateRandomGraph(nodeCount, 3);
        assertEquals(nodeCount, graph.getAllNodes().size());
    }

    @Test
    void testGraphNodesHaveUniqueIds() {
        int nodeCount = 10;
        Graph<Node> graph = GraphGenerator.generateRandomGraph(nodeCount, 2);
        Set<Node> nodes = graph.getAllNodes();

        long uniqueIdCount = nodes.stream()
                .map(Node::getId)
                .distinct()
                .count();

        assertEquals(nodeCount, uniqueIdCount);
    }

    @Test
    void testNoSelfLoopsInGeneratedGraph() {
        int nodeCount = 10;
        int maxConnections = 5;
        Graph<Node> graph = GraphGenerator.generateRandomGraph(nodeCount, maxConnections);

        for (Node node : graph.getAllNodes()) {
            assertFalse(graph.getConnections(node).contains(node));
        }
    }

    @Test
    void testNoDuplicateEdgesInGeneratedGraph() {
        int nodeCount = 8;
        int maxConnections = 3;
        Graph<Node> graph = GraphGenerator.generateRandomGraph(nodeCount, maxConnections);

        for (Node node : graph.getAllNodes()) {
            Set<Node> connections = graph.getConnections(node);
            assertEquals(connections.size(), connections.stream().distinct().count());
        }
    }

    @Test
    void testEdgeSymmetryInGeneratedGraph() {
        int nodeCount = 6;
        int maxConnections = 2;
        Graph<Node> graph = GraphGenerator.generateRandomGraph(nodeCount, maxConnections);

        for (Node from : graph.getAllNodes()) {
            for (Node to : graph.getConnections(from)) {
                assertTrue(graph.hasEdge(from, to));
                assertTrue(graph.hasEdge(to, from));
            }
        }
    }

    @Test
    void testConnectionsDoNotExceedMaxConnections() {
        int nodeCount = 10;
        int maxConnections = 4;
        Graph<Node> graph = GraphGenerator.generateRandomGraph(nodeCount, maxConnections);

        for (Node node : graph.getAllNodes()) {
            assertTrue(graph.getConnections(node).size() <= maxConnections,
                    "Узел " + node.getId() + " имеет " + graph.getConnections(node).size() + " связей");
        }
    }

    @Test
    void testAllNodesHaveNames() {
        int nodeCount = 5;
        Graph<Node> graph = GraphGenerator.generateRandomGraph(nodeCount, 1);
        for (Node node : graph.getAllNodes()) {
            assertNotNull(node.getName());
            assertFalse(node.getName().isEmpty());
        }
    }

    @Test
    public void testGenerateRandomGraph_ZeroNodes_ThrowsException() {
        // Проверяем, что при нулевом числе узлов выбрасывается исключение
        assertThrows(IllegalArgumentException.class, () ->
                GraphGenerator.generateRandomGraph(0, 3));
    }

    @Test
    public void testGenerateRandomGraph_NegativeConnections_ThrowsException() {
        // Проверяем, что при отрицательном числе связей выбрасывается исключение
        assertThrows(IllegalArgumentException.class, () ->
                GraphGenerator.generateRandomGraph(5, -1));
    }


}
