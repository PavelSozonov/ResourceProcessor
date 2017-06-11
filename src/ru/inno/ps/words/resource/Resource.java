package ru.inno.ps.words.resource;

/**
 * Created by pavel on 09.06.17.
 */
public interface Resource {

    /**
     * Returns the next word from the resource or null if there are no more words in this resource
     *
     * @return nextWord or null if there are no more words
     */
    String nextWord() throws IllegalArgumentException;
}
