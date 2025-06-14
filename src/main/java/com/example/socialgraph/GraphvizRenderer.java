package com.example.socialgraph;

import java.io.IOException;

/**
 * Класс для визуализации графов в формате PNG с использованием Graphviz.
 * Требует установленного в системе пакета Graphviz.
 */
public class GraphvizRenderer {

    /**
     * Генерирует PNG-изображение графа из DOT-файла.
     *
     * @param dotFilePath путь к файлу с описанием графа в формате DOT
     * @param outputImagePath путь для сохранения результирующего PNG-файла
     * @throws IOException если произошла ошибка ввода-вывода при работе с файлами
     * @throws InterruptedException если процесс был прерван
     * @throws RuntimeException если Graphviz вернул код ошибки
     *
     * @example Пример использования:
     * {@code
     * GraphvizRenderer.generatePng("graph.dot", "graph.png");
     * }
     *
     * @systemRequirements Требует установки Graphviz в системе и доступности
     * команды 'dot' в PATH окружения
     */
    public static void generatePng(String dotFilePath, String outputImagePath)
            throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(
                "dot", "-Tpng", dotFilePath, "-o", outputImagePath
        );
        pb.redirectErrorStream(true);
        Process process = pb.start();
        int exitCode = process.waitFor();

        if (exitCode != 0) {
            throw new RuntimeException("Ошибка генерации PNG: код " + exitCode);
        }
    }
}