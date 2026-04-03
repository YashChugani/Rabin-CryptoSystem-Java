package rabin.util;

public class ConsoleUtil {

    public static final String RESET = "\u001B[0m";

    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    public static void printHeader(String text) {
        System.out.println(CYAN + "\n=== " + text + " ===" + RESET);
    }

    public static void success(String msg) {
        System.out.println(GREEN + "[OK] " + msg + RESET);
    }

    public static void error(String msg) {
        System.out.println(RED + "[ERR] " + msg + RESET);
    }

    public static void info(String msg) {
        System.out.println(WHITE + msg + RESET);
    }

    public static void step(String msg) {
        System.out.println(YELLOW + "-> " + msg + RESET);
    }

    public static void delay() {
        try {
            Thread.sleep(600);
        } catch (InterruptedException ignored) {}
    }
}