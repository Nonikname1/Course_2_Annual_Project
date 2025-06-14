package com.example.socialgraph;

import java.io.FileWriter;
import java.io.IOException;

public class GraphFileWriter {

    public static <T> void writeToDotFile(Graph<T> graph, String filepath) throws IOException {
        String dot = GraphvizSerializer.serialize(graph);
        try (FileWriter writer = new FileWriter(filepath)) {
            writer.write(dot);
        }
    }
}

