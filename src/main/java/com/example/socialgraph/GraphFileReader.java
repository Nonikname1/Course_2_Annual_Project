package com.example.socialgraph;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Function;

public class GraphFileReader {

    public static <T> Graph<T> readFromDotFile(String filepath, Function<String, T> stringToNode) throws IOException {
        String dot = Files.readString(Paths.get(filepath));
        return GraphvizDeserializer.deserialize(dot, stringToNode);
    }
}
