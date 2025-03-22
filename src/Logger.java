import java.util.Scanner;

public class Logger {
    private static int depth = 0;
    private static String indent () {
        return "    ".repeat(depth);
    }
    public static void enter (String methodName, String variableName){
        System.out.println(indent() + "->" + methodName + "(" + variableName + ")");
        depth++;
    }
    public static void exit(String methodName, String variableName){
        --depth;
        System.out.println(indent() + "<-" + methodName + "(" + variableName + ")");
    }
    //Megkérdezi a felhasználót és a válaszának megfelelően visszatér True(y) vagy False(n) értékekkel
    public static boolean askUser(String message){
        Scanner scanner = new Scanner (System.in);
        System.out.println(indent() + message + "[y/n]");
        String response = scanner.nextLine().trim().toLowerCase();
        return response.equals("y");
    }
}

