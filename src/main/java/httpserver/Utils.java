package httpserver;

import java.io.IOException;

public class Utils {
    public static boolean quit(String keyword) {
        return (keyword.equalsIgnoreCase("quit"));
    }

    public static void print(String message) {
        System.out.println(message);
    }

    public static void error(String message, IOException e) {
        System.out.println(message);
        System.out.println(e);
        System.exit(1);
    }
}
