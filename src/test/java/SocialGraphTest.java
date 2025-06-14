import com.example.socialgraph.SocialGraph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class SocialGraphTest {

    private SocialGraph<String> graph;
    private final String nodeA = "A";
    private final String nodeB = "B";
    private final String nodeC = "C";
    private final String nodeD = "D";

    @BeforeEach
    public void setUp() {
        graph = new SocialGraph<>();
    }

    @Test
    public void testAddNode_Success() {
        assertTrue(graph.addNode("Alice"));
        assertTrue(graph.getAllNodes().contains("Alice"));
    }

    @Test
    public void testAddNode_Duplicate() {
        graph.addNode("Bob");
        assertFalse(graph.addNode("Bob"));
    }

    @Test
    public void testAddNode_Null() {
        assertFalse(graph.addNode(null));
    }

    @Test
    public void testAddEdge_Success() {
        graph.addNode("Alice");
        graph.addNode("Bob");
        assertTrue(graph.addEdge("Alice", "Bob"));
        assertTrue(graph.hasEdge("Alice", "Bob"));
        assertTrue(graph.hasEdge("Bob", "Alice"));
    }

    @Test
    public void testAddEdge_DuplicateEdge() {
        graph.addNode("Alice");
        graph.addNode("Bob");
        graph.addEdge("Alice", "Bob");
        assertFalse(graph.addEdge("Alice", "Bob")); // edge already exists
    }

    @Test
    public void testAddEdge_NonexistentNodes() {
        graph.addNode("Alice");
        assertFalse(graph.addEdge("Alice", "Charlie")); // Charlie not added
    }

    @Test
    public void testAddEdge_SelfLoop() {
        graph.addNode("Alice");
        assertFalse(graph.addEdge("Alice", "Alice"));
    }

    @Test
    public void testGetConnections() {
        graph.addNode("Alice");
        graph.addNode("Bob");
        graph.addEdge("Alice", "Bob");
        Set<String> connections = graph.getConnections("Alice");
        assertEquals(1, connections.size());
        assertTrue(connections.contains("Bob"));
    }

    @Test
    public void testGetConnections_NullNode() {
        assertTrue(graph.getConnections(null).isEmpty());
    }

    @Test
    public void testGetConnections_NonexistentNode() {
        assertTrue(graph.getConnections("NonExistent").isEmpty());
    }

    @Test
    public void testHasEdge_True() {
        graph.addNode("Alice");
        graph.addNode("Bob");
        graph.addEdge("Alice", "Bob");
        assertTrue(graph.hasEdge("Alice", "Bob"));
    }

    @Test
    public void testHasEdge_False() {
        graph.addNode("Alice");
        graph.addNode("Bob");
        assertFalse(graph.hasEdge("Alice", "Bob"));
    }

    @Test
    public void testHasEdge_NonexistentNode() {
        graph.addNode("Alice");
        assertFalse(graph.hasEdge("Alice", "Charlie"));
    }

    @Test
    public void testGetAllNodes() {
        graph.addNode("Alice");
        graph.addNode("Bob");
        Set<String> nodes = graph.getAllNodes();
        assertEquals(2, nodes.size());
        assertTrue(nodes.contains("Alice"));
        assertTrue(nodes.contains("Bob"));
    }

    // Тесты для addNode()
    @Test
    void addNode_shouldAddNewNode() {
        assertTrue(graph.addNode(nodeA));
        assertTrue(graph.getAllNodes().contains(nodeA));
    }

    @Test
    void addNode_shouldReturnFalseForNull() {
        assertFalse(graph.addNode(null));
    }

    @Test
    void addNode_shouldReturnFalseForDuplicate() {
        graph.addNode(nodeA);
        assertFalse(graph.addNode(nodeA));
    }

    // Тесты для addEdge()
    @Test
    void addEdge_shouldAddConnectionBetweenNodes() {
        graph.addNode(nodeA);
        graph.addNode(nodeB);

        assertTrue(graph.addEdge(nodeA, nodeB));
        assertTrue(graph.hasEdge(nodeA, nodeB));
        assertTrue(graph.hasEdge(nodeB, nodeA)); // проверка симметричности
    }

    @Test
    void addEdge_shouldReturnFalseForNullNodes() {
        assertFalse(graph.addEdge(null, nodeB));
        assertFalse(graph.addEdge(nodeA, null));
        assertFalse(graph.addEdge(null, null));
    }

    @Test
    void addEdge_shouldReturnFalseForNonExistentNodes() {
        graph.addNode(nodeA);
        assertFalse(graph.addEdge(nodeA, nodeB));
        assertFalse(graph.addEdge(nodeB, nodeA));
    }

    @Test
    void addEdge_shouldNotAllowSelfLoops() {
        graph.addNode(nodeA);
        assertFalse(graph.addEdge(nodeA, nodeA));
    }

    @Test
    void addEdge_shouldReturnFalseForExistingEdge() {
        graph.addNode(nodeA);
        graph.addNode(nodeB);
        graph.addEdge(nodeA, nodeB);

        assertFalse(graph.addEdge(nodeA, nodeB)); // повторное добавление
        assertFalse(graph.addEdge(nodeB, nodeA)); // и в обратную сторону
    }

    // Тесты для getConnections()
    @Test
    void getConnections_shouldReturnEmptySetForNewNode() {
        graph.addNode(nodeA);
        assertTrue(graph.getConnections(nodeA).isEmpty());
    }

    @Test
    void getConnections_shouldReturnEmptySetForNull() {
        assertTrue(graph.getConnections(null).isEmpty());
    }

    @Test
    void getConnections_shouldReturnEmptySetForNonExistentNode() {
        assertTrue(graph.getConnections(nodeA).isEmpty());
    }

    @Test
    void getConnections_shouldReturnAllConnections() {
        graph.addNode(nodeA);
        graph.addNode(nodeB);
        graph.addNode(nodeC);
        graph.addEdge(nodeA, nodeB);
        graph.addEdge(nodeA, nodeC);

        Set<String> connections = graph.getConnections(nodeA);
        assertEquals(2, connections.size());
        assertTrue(connections.contains(nodeB));
        assertTrue(connections.contains(nodeC));
    }

    // Тесты для hasEdge()
    @Test
    void hasEdge_shouldReturnFalseForNonExistentNodes() {
        assertFalse(graph.hasEdge(nodeA, nodeB));
    }

    @Test
    void hasEdge_shouldReturnFalseForNullNodes() {
        assertFalse(graph.hasEdge(null, nodeB));
        assertFalse(graph.hasEdge(nodeA, null));
    }

    @Test
    void hasEdge_shouldReturnTrueForConnectedNodes() {
        graph.addNode(nodeA);
        graph.addNode(nodeB);
        graph.addEdge(nodeA, nodeB);

        assertTrue(graph.hasEdge(nodeA, nodeB));
        assertTrue(graph.hasEdge(nodeB, nodeA));
    }

    // Тесты для getAllNodes()
    @Test
    void getAllNodes_shouldReturnEmptySetForNewGraph() {
        assertTrue(graph.getAllNodes().isEmpty());
    }

    @Test
    void getAllNodes_shouldReturnAllAddedNodes() {
        graph.addNode(nodeA);
        graph.addNode(nodeB);
        graph.addNode(nodeC);

        Set<String> nodes = graph.getAllNodes();
        assertEquals(3, nodes.size());
        assertTrue(nodes.contains(nodeA));
        assertTrue(nodes.contains(nodeB));
        assertTrue(nodes.contains(nodeC));
    }

    @Test
    void getAllNodes_shouldReturnUnmodifiableSet() {
        graph.addNode(nodeA);
        Set<String> nodes = graph.getAllNodes();

        assertThrows(UnsupportedOperationException.class, () -> {
            nodes.add(nodeB);
        });
    }

    // Интеграционные тесты
    @Test
    void multipleOperations_shouldWorkCorrectly() {
        // Добавление узлов
        assertTrue(graph.addNode(nodeA));
        assertTrue(graph.addNode(nodeB));
        assertTrue(graph.addNode(nodeC));
        assertTrue(graph.addNode(nodeD));

        // Добавление связей
        assertTrue(graph.addEdge(nodeA, nodeB));
        assertTrue(graph.addEdge(nodeA, nodeC));
        assertTrue(graph.addEdge(nodeB, nodeD));

        // Проверка связей
        assertTrue(graph.hasEdge(nodeA, nodeB));
        assertTrue(graph.hasEdge(nodeA, nodeC));
        assertTrue(graph.hasEdge(nodeB, nodeD));
        assertFalse(graph.hasEdge(nodeA, nodeD));
        assertFalse(graph.hasEdge(nodeC, nodeD));

        // Проверка количества связей
        assertEquals(2, graph.getConnections(nodeA).size());
        assertEquals(2, graph.getConnections(nodeB).size());
        assertEquals(1, graph.getConnections(nodeC).size());
        assertEquals(1, graph.getConnections(nodeD).size());

        // Проверка всех узлов
        assertEquals(4, graph.getAllNodes().size());
    }
}
