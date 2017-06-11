package ru.inno.ps.words;

import ru.inno.ps.words.wordprocessor.ConcurrentWordProcessor;
import ru.inno.ps.words.resource.FileResource;

/**
 * Created by pavel on 09.06.17.
 */
public class Client {
    public static void main(String[] args) {
        ConcurrentWordProcessor wordProcessor = new ConcurrentWordProcessor(new FileResource("file1.txt"),
                new FileResource("file2.txt"));

        boolean duplicatesNotFound = wordProcessor.checkDuplicates();

        if (duplicatesNotFound) {
            System.out.println("There are no duplicates");
        } else {
            System.out.println("There are duplicates");
        }
    }
}
