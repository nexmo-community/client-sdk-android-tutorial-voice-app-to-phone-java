package com.vonage.tutorial.voice;

public class Config {

    public static User getAlice() {
        return new User(
                "Alice",
                "" // TODO: "set Alice JWT token"
        );
    }
}
