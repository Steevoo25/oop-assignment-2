import java.util.Scanner;

public class Toolkit {
    private static final Scanner stdIn = new Scanner(System.in);

    public static final String GOODBYE_MESSAGE = "Thank you for playing";

    public static String getInputForMessage(String message) {
        System.out.println(message);
        return stdIn.nextLine().trim();
    }

    public static String printArray(String[] array) {
        StringBuilder sb = new StringBuilder();
        for (String s : array) {
            s = s.trim();
            sb.append(s).append(Defaults.getNumberSeparator());
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}