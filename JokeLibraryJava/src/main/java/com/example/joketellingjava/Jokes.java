package com.example.joketellingjava;

import java.util.Random;

public class Jokes {

    private static String[] jokes = new String[]{
            "Joke 1",
            "Joke 2",
            "Joke 3",
            "Joke 4"
    };

    public static String getJoke() {
        Random rnd = new Random();
        return jokes[rnd.nextInt(jokes.length)];
    }

}
