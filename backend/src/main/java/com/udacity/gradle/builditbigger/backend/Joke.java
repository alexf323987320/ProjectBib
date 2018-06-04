package com.udacity.gradle.builditbigger.backend;

/** The object model for the data we are sending through endpoints */
public class Joke{

    private int mJokeNumber;
    private String mJokeString;

    public Joke(int jokeNumber) {
        mJokeNumber = jokeNumber;
        mJokeString = com.example.joketellingjava.Jokes.getJoke() + "(requested number = " + jokeNumber + ")";
    }

    public String getStringJoke() {
        return mJokeString;
    }

}