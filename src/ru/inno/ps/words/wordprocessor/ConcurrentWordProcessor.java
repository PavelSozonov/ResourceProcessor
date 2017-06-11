package ru.inno.ps.words.wordprocessor;

import ru.inno.ps.words.resource.Resource;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by pavel on 09.06.17.
 *
 * Вариант 2
 *
 * Необходимо разработать программу, которая получает на вход список ресурсов,
 * содержащих текст, и проверяет уникальность каждого слова.
 * Каждый ресурс должен быть обработан в отдельном потоке, текст не должен
 * содержать инностранных символов, только кириллица, знаки препинания и цифры.
 * В случае нахождения дубликата, программа должна прекращать выполнение
 * с соответсвующим сообщением. Все ошибки должны быть корректно обработаны,
 * все API покрыто модульными тестами
 */
public class ConcurrentWordProcessor {

    private Resource[] resources;
    private volatile boolean duplicateFound = false;
    private Set<String> dictionary = new HashSet<>();
    private Object monitor = new Object();

    public ConcurrentWordProcessor(Resource... resources) {
        this.resources = resources;
    }

    public boolean checkDuplicates() {
        ExecutorService threadExecutor = Executors.newCachedThreadPool();
        for (Resource resource : resources) {
            threadExecutor.execute(() -> {
                extractWordsUntilDuplicateFound(resource);
            });
        }
        threadExecutor.shutdown();
        while (!threadExecutor.isTerminated()) {}

        //monitor.wait();

        if (duplicateFound) return false; // Duplicate was found
        return true; // Duplicate was not found
    }

    private void extractWordsUntilDuplicateFound(Resource resource) {
        String nextWord;
        boolean stop = false;

        while (!stop) {
            nextWord = resource.nextWord();
            if (nextWord == null || duplicateFound) {
                stop = true;
            } else {
                if (!addWordToSet(nextWord)) {
                    duplicateFound = true;
                    System.out.printf("Duplicate is found: %s\n", nextWord);
                }
            }
        }
    }

    private synchronized boolean addWordToSet(String word) {
        return dictionary.add(word);
    }
}
