package ru.inno.ps.words.resource;

import java.io.*;

/**
 * Created by pavel on 11.06.17.
 */
public class FileResource implements Resource {
    private String fileName;
    private InputStream fileStream;
    private DataInputStream inputStream;
    private BufferedReader bufferedReader;

    public FileResource(String fileName) {
        this.fileName = fileName;
        try {
            fileStream = new FileInputStream(fileName);
            inputStream = new DataInputStream(fileStream);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String nextWord() throws IllegalArgumentException {
        int currentSymbol;
        StringBuilder word = new StringBuilder();
        try {
            boolean stop = false;

            while (!stop) {
                currentSymbol = bufferedReader.read();
                if (currentSymbol == -1) {
                    stop = true;
                } else {
                    if (currentSymbol == ' ') {
                        if (word.length() != 0) {
                            stop = true;
                        }
                    } else {
                        if (latinLetter((char)currentSymbol)) {
                            throw new IllegalArgumentException("Illegal latin symbols are found");
                        }
                        word.append((char)currentSymbol);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (word.length() == 0) return null;
        return word.toString();
    }

    private boolean latinLetter(char letter) {
        if ((letter >= 'a' && letter <= 'z') || (letter >= 'A' && letter <= 'Z')) return true;
        return false;
    }

    @Override
    protected void finalize() throws Throwable {
        bufferedReader.close();
        inputStream.close();
        fileStream.close();
    }
}
