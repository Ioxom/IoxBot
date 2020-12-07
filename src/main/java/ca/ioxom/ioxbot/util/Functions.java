package ca.ioxom.ioxbot.util;

public class Functions {
    public static String generateRandomColour() {
        String[] colours = {"00ff00", "48ddf7", "c211aa", "d91139", "1010e3", "ebe717", "fa7d16"};
        return colours[(int) Math.round(Math.random() * 7)];
    }
}
