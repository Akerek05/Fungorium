//import java.util.Scanner;
//
///**
// * Egyszerű naplózó osztály, amely belépési és kilépési naplózást biztosít a metódusokhoz,
// * valamint interaktív kérdésekre ad választ a felhasználótól.
// */
//public class Logger {
//    private static int depth = 0;
//
//    /**
//     * Behúzások generálása a napló mélysége alapján.
//     *
//     * @return Behúzás szóközökkel
//     */
//    private static String indent () {
//        return "    ".repeat(depth);
//    }
//
//    /**
//     * Metódus belépésének naplózása.
//     *
//     * @param methodName A metódus neve
//     * @param variableName A változó(k), amit naplózunk
//     */
//    public static void enter (String methodName, String variableName){
//        System.out.println(indent() + "->" + methodName + "(" + variableName + ")");
//        depth++;
//    }
//
//    /**
//     * Metódus kilépésének naplózása.
//     *
//     * @param methodName A metódus neve
//     * @param variableName A változó(k), amit naplózunk
//     */
//    public static void exit(String methodName, String variableName){
//        --depth;
//        System.out.println(indent() + "<-" + methodName + "(" + variableName + ")");
//    }
//
//    /**
//     * A felhasználótól kérdez meg valamit, és y/n alapján visszaad egy logikai értéket.
//     *
//     * @param message A kérdés szövege
//     * @return Igaz, ha a válasz "y", hamis különben
//     */
//    public static boolean askUser(String message){
//        Scanner scanner = new Scanner (System.in);
//        System.out.println(indent() + message + "[y/n]");
//        String response = scanner.nextLine().trim().toLowerCase();
//        return response.equals("y");
//    }
//}
