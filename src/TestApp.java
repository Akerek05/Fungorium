//import java.util.Scanner;
//
///**
// * Tesztosztály a játék fő funkcióinak ellenőrzésére manuálisan és logikai lépések követésével.
// */
//public class Testing {
//
//    /**
//     * Teszteli a Tekton törését és a hozzá kapcsolódó elemek (rovar, gomba, fonal) eltávolítását.
//     */
//    static void tektonBreak() {
//        System.out.println("tektonBreak test.");
//        Map map = new Map();
//        map.addTekton();
//        map.addTekton();
//        Mushroom m1 = new Mushroom(map.tektons.get(0));
//        Insect i1 = new Insect(map.tektons.get(0));
//        ShroomString s1 = new ShroomString(map.tektons.get(0), map.tektons.get(1));
//
//        map.tektons.get(0).arrayOfInsect.add(i1);
//        map.tektons.get(0).arrayOfMushroom.add(m1);
//        map.tektons.get(0).arrayOfString.add(s1);
//        map.tektons.get(1).arrayOfString.add(s1);
//        map.tektons.get(1).stringNeighbours.add(map.tektons.get(0));
//        map.tektons.get(0).stringNeighbours.add(map.tektons.get(1));
//
//        map.tektons.get(0).breakTekton();
//        System.out.println();
//    }
//
//    /**
//     * Teszteli a gomba spóra szórását szomszédos Tektonra.
//     */
//    static void spreadSpore() {
//        System.out.println("spreadSpore test.");
//        Map map = new Map();
//        map.addTekton();
//        map.addTekton();
//        Mushroom m1 = new Mushroom(map.tektons.get(0));
//        map.tektons.get(0).arrayOfMushroom.add(m1);
//        m1.spreadSpore(map.tektons.get(1));
//        System.out.println();
//    }
//
//    /**
//     * Teszteli egy rovar mozgatását egyik Tektonról a másikra.
//     */
//    static void insectMove() {
//        System.out.println("insectMove test.");
//        Map map = new Map();
//        map.addTekton();
//        map.addTekton();
//        Insect i1 = new Insect(map.tektons.get(0));
//        i1.moveToTekton(map.tektons.get(1));
//        System.out.println();
//    }
//
//    /**
//     * Teszteli, hogy a rovar képes-e fonalat elvágni.
//     */
//    static void stringCut() {
//        System.out.println("stringCut test.");
//        Map map = new Map();
//        map.addTekton();
//        map.addTekton();
//        Insect i1 = new Insect(map.tektons.get(0));
//        ShroomString s1 = new ShroomString(map.tektons.get(0), map.tektons.get(1));
//        map.tektons.get(0).addString(map.tektons.get(1));
//        i1.cutString(s1);
//        System.out.println();
//    }
//
//    /**
//     * Teszteli a gomba fejlesztését UpgradedMushroom-ra.
//     */
//    static void upgradeMushroom() {
//        System.out.println("upgradeMushroom test.");
//        Map map = new Map();
//        map.addTekton();
//        Mushroom m1 = new Mushroom(map.tektons.get(0));
//        map.tektons.get(0).arrayOfMushroom.add(m1);
//        m1.upgradeMushroom();
//        System.out.println();
//    }
//
//    /**
//     * Teszteli az alapértelmezett Spore elfogyasztásának hatását.
//     */
//    static void defaultSporeConsume() {
//        System.out.println("defaultSporeConsume test.");
//        Map map = new Map();
//        map.addTekton();
//        Insect i1 = new Insect(map.tektons.get(0));
//        Spore s1 = new Spore(map.tektons.get(0), 1, -1);
//        map.tektons.get(0).arrayOfInsect.add(i1);
//        map.tektons.get(0).arrayOfSpore.add(s1);
//        i1.eatSpore(s1);
//        System.out.println();
//    }
//
//    /**
//     * Teszteli a KillerSpore elfogyasztásának hatását (rovar halála).
//     */
//    static void killerSporeConsume() {
//        System.out.println("killerSporeConsume test.");
//        Map map = new Map();
//        map.addTekton();
//        Insect i1 = new Insect(map.tektons.get(0));
//        KillerSpore s1 = new KillerSpore(map.tektons.get(0));
//        map.tektons.get(0).arrayOfInsect.add(i1);
//        map.tektons.get(0).arrayOfSpore.add(s1);
//        i1.eatSpore(s1);
//        System.out.println();
//    }
//
//    /**
//     * Teszteli a ParalyzeSpore bénító hatását.
//     */
//    static void paralyzeSporeConsume() {
//        System.out.println("paralyzeSporeConsume test.");
//        Map map = new Map();
//        map.addTekton();
//        Insect i1 = new Insect(map.tektons.get(0));
//        ParalyzeSpore s1 = new ParalyzeSpore(map.tektons.get(0));
//        map.tektons.get(0).arrayOfInsect.add(i1);
//        map.tektons.get(0).arrayOfSpore.add(s1);
//        i1.eatSpore(s1);
//        System.out.println();
//    }
//
//    /**
//     * Teszteli a NoCutSpore hatását: vágás tiltása.
//     */
//    static void noCutSporeConsume() {
//        System.out.println("noCutSporeConsume test.");
//        Map map = new Map();
//        map.addTekton();
//        Insect i1 = new Insect(map.tektons.get(0));
//        NoCutSpore s1 = new NoCutSpore(map.tektons.get(0));
//        map.tektons.get(0).arrayOfInsect.add(i1);
//        map.tektons.get(0).arrayOfSpore.add(s1);
//        i1.eatSpore(s1);
//        System.out.println();
//    }
//
//    /**
//     * Teszteli a BuffSpore hatását: akciópont növelése.
//     */
//    static void buffSporeConsume() {
//        System.out.println("buffSporeConsume test.");
//        Map map = new Map();
//        map.addTekton();
//        Insect i1 = new Insect(map.tektons.get(0));
//        BuffSpore s1 = new BuffSpore(map.tektons.get(0));
//        map.tektons.get(0).arrayOfInsect.add(i1);
//        map.tektons.get(0).arrayOfSpore.add(s1);
//        i1.eatSpore(s1);
//        System.out.println();
//    }
//
//    /**
//     * Teszteli a DebuffSpore hatását: akciópont csökkentése.
//     */
//    static void debuffSporeConsume() {
//        System.out.println("debuffSporeConsume test.");
//        Map map = new Map();
//        map.addTekton();
//        Insect i1 = new Insect(map.tektons.get(0));
//        DebuffSpore s1 = new DebuffSpore(map.tektons.get(0));
//        map.tektons.get(0).arrayOfInsect.add(i1);
//        map.tektons.get(0).arrayOfSpore.add(s1);
//        i1.eatSpore(s1);
//        System.out.println();
//    }
//
//    /**
//     * Teszteli az UpgradedMushroom BFS-alapú spóra szórását.
//     */
//    static void SpreadSporeWithUpgraded() {
//        System.out.println("SpreadSporeWithUpgraded test.");
//        Map map = new Map();
//        map.tektons.add(new Tekton());
//        map.tektons.add(new Tekton());
//        map.tektons.add(new Tekton());
//        map.tektons.get(0).neighbours.add(map.tektons.get(1));
//        map.tektons.get(1).neighbours.add(map.tektons.get(0));
//        map.tektons.get(1).neighbours.add(map.tektons.get(2));
//        map.tektons.get(2).neighbours.add(map.tektons.get(1));
//        map.tektons.get(0).arrayOfMushroom.add(new UpgradedMushroom(map.tektons.get(0)));
//        map.tektons.get(0).arrayOfMushroom.get(0).spreadSpore(map.tektons.get(2));
//        System.out.println();
//    }
//
//    /**
//     * Teszteli, hogy fonal végén gomba nőhet-e.
//     */
//    static void GrowMushroom() {
//        System.out.println("GrowMushroom test.");
//        Map map = new Map();
//        map.addTekton();
//        map.addTekton();
//        ShroomString s1 = new ShroomString(map.tektons.get(0), map.tektons.get(1));
//        map.tektons.get(1).stringNeighbours.add(map.tektons.get(0));
//        map.tektons.get(0).stringNeighbours.add(map.tektons.get(1));
//        map.tektons.get(0).arrayOfString.add(s1);
//        map.tektons.get(1).arrayOfString.add(s1);
//        map.tektons.get(0).growBody();
//        System.out.println();
//    }
//
//    /**
//     * Teszt: fonal növesztése MultipleStringTektonra.
//     */
//    static void GrowStringOnMultipleTekton() {
//        System.out.println("GrowStringOnMultiple test.");
//        Map map = new Map();
//        Tekton t1 = new Tekton();
//        Tekton t2 = new MultipleStringTekton();
//        t1.neighbours.add(t1);
//        t2.neighbours.add(t2);
//        Mushroom m1 = new Mushroom(t1);
//        t1.arrayOfMushroom.add(m1);
//        m1.growString(t1, t2);
//        System.out.println();
//    }
//
//    /**
//     * Teszt: nem élhető Tektonon nőhet-e gomba.
//     */
//    static void GrowMushroomOnUnlivableTekton() {
//        System.out.println("GrowMushroomOnLifelessTekton test.");
//        Map map = new Map();
//        Tekton t1 = new UnlivableTekton();
//        Tekton t2 = new Tekton();
//        map.tektons.add(t1);
//        map.tektons.add(t2);
//        ShroomString s1 = new ShroomString(t1, t2);
//        t1.arrayOfString.add(s1);
//        t1.growBody(); // Nem szabad történnie semminek
//        System.out.println(t1.getMushroom());
//        System.out.println();
//    }
//
//    /**
//     * Teszt: gombafonal növesztése Mushroom által.
//     */
//    static void GrowStringTest() {
//        System.out.println("GrowString test.");
//        Map map = new Map();
//        map.tektons.add(new Tekton());
//        map.tektons.add(new Tekton());
//        map.tektons.get(0).arrayOfMushroom.add(new Mushroom(map.tektons.get(0)));
//        map.tektons.get(0).arrayOfMushroom.get(0).growString(map.tektons.get(0), map.tektons.get(1));
//        System.out.println();
//    }
//
//    /**
//     * A tesztprogram belépési pontja.
//     */
//    public static void main(String[] args) {
//        boolean run = true;
//        Scanner sc = new Scanner(System.in);
//        while(run) {
//            System.out.println("Choose a testing mode:" +
//                    "\n(1) TektonBreak test \n(2) InsectMove test \n(3) StringCut test \n(4) SpreadSpore \n(5) UpgradeMushroom test" +
//                    "\n(6) DefaultSporeConsume test \n(7) KillerSporeConsume test \n(8) ParalyzeSporeConsume test \n(9) NoCutSporeConsume test" +
//                    "\n(10) BuffSporeConsume test \n(11) DebuffSporeConsume test \n(12) SpreadSporeWithUpgrade test \n(13) GrowMushroomOnUnlivableTekton test" +
//                    "\n(14) GrowMushroom test \n(15) GrowString test \n(16) GrowStringOnMultipleTekton test \n(0) Exit");
//            String input = sc.nextLine();
//            switch (input) {
//                case "1" :
//                    tektonBreak();
//                    break;
//                case "2" :
//                    insectMove();
//                    break;
//                case "3" :
//                    stringCut();
//                    break;
//                case "4" :
//                    spreadSpore();
//                    break;
//                case "5" :
//                    upgradeMushroom();
//                    break;
//                case "6" :
//                    defaultSporeConsume();
//                    break;
//                case "7":
//                    killerSporeConsume();
//                    break;
//                case "8" :
//                    paralyzeSporeConsume();
//                    break;
//                case "9" :
//                    noCutSporeConsume();
//                    break;
//                case "10":
//                    buffSporeConsume();
//                    break;
//                case "11" :
//                    debuffSporeConsume();
//                    break;
//                case "12" :
//                    SpreadSporeWithUpgraded();
//                    break;
//                case "13" :
//                    GrowMushroomOnUnlivableTekton();
//                    break;
//                case "14" :
//                    GrowMushroom();
//                    break;
//                case "15" :
//                    GrowStringTest();
//                    break;
//                case "16" :
//                    GrowStringOnMultipleTekton();
//                    break;
//                case "0" :
//                    run = false;
//                    break;
//                default :
//                    break;
//
//            }
//        }
//    }
//}
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream; // Eltérő megközelítéshez kellhet, de most a fájlba írás közvetlenebb
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream; // Szükséges a System.out átirányításához
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

// Feltételezzük, hogy a Map és a hozzá tartozó osztályok (Tekton, Insect, stb.)
// elérhetők a classpath-on (pl. ugyanabban a projektben/package-ben vannak).
// Ha más package-ben vannak, importálni kell őket.
// import your_package_name.Map;
// import your_package_name.*; // Vagy egyesével

public class TestApp {

    private static final String TEST_BASE_DIR = ".\\src\\test_cases";
    private static final String INPUT_FILE = "input.txt";
    private static final String EXPECTED_RESULT_FILE = "expectedresult.txt";
    private static final String ACTUAL_RESULT_FILE = "result.txt";

    public static void main(String[] args) {
        System.out.println("Select test case by entering 'all' or the number of the test you’d like to run:");
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine().trim();

        TestApp runner = new TestApp();

        if ("all".equalsIgnoreCase(userInput)) {
            runner.runAllTests();
        } else {
            try {
                int testNumber = Integer.parseInt(userInput);
                if (testNumber <= 0) {
                    System.err.println("Invalid test number. Please enter a positive integer.");
                } else {
                    runner.runSingleTest(testNumber);
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid input. Please enter 'all' or a valid test number.");
            }
        }

        scanner.close();
    }

    public void runSingleTest(int testNumber) {
        Path testDir = Paths.get(TEST_BASE_DIR, String.valueOf(testNumber));
        if (!Files.isDirectory(testDir)) {
            System.err.println("Test case directory not found: " + testDir);
            return;
        }

        System.out.println("Running test #" + testNumber + "...");
        boolean success = executeAndCompareTest(testDir);

        if (success) {
            System.out.println("SUCCESS");
        } else {
            System.out.println("FAILURE");
            printTestFailureDetails(testDir);
        }
    }

    public void runAllTests() {
        List<Integer> availableTests = findAvailableTests();
        if (availableTests.isEmpty()) {
            System.out.println("No test cases found in '" + TEST_BASE_DIR + "'.");
            return;
        }

        System.out.println("Running all tests (" + availableTests.size() + " found)...");

        List<Boolean> results = new ArrayList<>();
        List<Integer> failedTestNumbers = new ArrayList<>();

        for (int testNumber : availableTests) {
            Path testDir = Paths.get(TEST_BASE_DIR, String.valueOf(testNumber));
            System.out.println(" -> Running test #" + testNumber);
            boolean success = executeAndCompareTest(testDir);
            results.add(success); // Eredmény tárolása (bár a failedTestNumbers miatt ez redundáns lehet)
            if (!success) {
                failedTestNumbers.add(testNumber);
            }
        }

        System.out.println("\n--- Test Run Summary ---");
        if (failedTestNumbers.isEmpty()) {
            System.out.println("SUCCESS");
        } else {
            String failedTestsString = failedTestNumbers.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", "));
            System.out.println("Amount of tests FAILED: " + failedTestNumbers.size());
            System.out.println("FAILED tests: " + failedTestsString);

            System.out.println("\n--- Failure Details ---");
            for (int failedTestNumber : failedTestNumbers) {
                Path testDir = Paths.get(TEST_BASE_DIR, String.valueOf(failedTestNumber));
                System.out.println("\nDetails for FAILED test #" + failedTestNumber + ":");
                printTestFailureDetails(testDir);
            }
        }
    }

    private List<Integer> findAvailableTests() {
        List<Integer> testNumbers = new ArrayList<>();
        Path baseDir = Paths.get(TEST_BASE_DIR);
        if (!Files.isDirectory(baseDir)) {
            return testNumbers;
        }

        try (var stream = Files.list(baseDir)) {
            stream.filter(Files::isDirectory)
                    .map(path -> path.getFileName().toString())
                    .filter(name -> name.matches("\\d+"))
                    .mapToInt(Integer::parseInt)
                    .filter(num -> num > 0)
                    .sorted()
                    .forEach(testNumbers::add);
        } catch (IOException e) {
            System.err.println("Error listing test directories in '" + TEST_BASE_DIR + "': " + e.getMessage());
        }
        return testNumbers;
    }

    /**
     * Végrehajtja egy adott teszteset lépéseit:
     * 1. Beolvassa az input.txt-t.
     * 2. Létrehoz egy Map példányt.
     * 3. Átirányítja a System.out-ot a result.txt-be.
     * 4. Futtatja a parancsokat a Map példányon.
     * 5. Visszaállítja a System.out-ot.
     * 6. Összehasonlítja a result.txt és expected_result.txt tartalmát.
     *
     * @param testDir A teszteset könyvtárának útvonala.
     * @return true, ha a teszt sikeres, false egyébként.
     */
    private boolean executeAndCompareTest(Path testDir) {
        Path inputFile = testDir.resolve(INPUT_FILE);
        Path expectedFile = testDir.resolve(EXPECTED_RESULT_FILE);
        Path actualFile = testDir.resolve(ACTUAL_RESULT_FILE);

        if (!Files.isReadable(inputFile)) {
            System.err.println("  Error: Cannot read input file: " + inputFile);
            writeErrorToActualResult(actualFile, "Error: Cannot read input file: " + inputFile);
            return false;
        }
        if (!Files.isReadable(expectedFile)) {
            System.err.println("  Warning: Cannot read expected result file: " + expectedFile + ". Assuming failure.");
            writeErrorToActualResult(actualFile, "Error: Expected result file not found or not readable: " + expectedFile);
            return false;
        }

        // Töröljük a régi result.txt-t, ha létezik, hogy tiszta lappal induljunk
        try {
            Files.deleteIfExists(actualFile);
        } catch (IOException e) {
            System.err.println("  Warning: Could not delete previous result file: " + actualFile + " - " + e.getMessage());
            // Folytatjuk, a FileOutputStream felül fogja írni, ha tudja.
        }


        try {
            // 2-5. Map parancsok futtatása és kimenet rögzítése
            runMapCommandsAndCaptureOutput(inputFile, actualFile);

            // 6. Összehasonlítás
            return compareFiles(actualFile, expectedFile);

        } catch (IOException e) {
            System.err.println("  Error during test file I/O for " + testDir.getFileName() + ": " + e.getMessage());
            writeErrorToActualResult(actualFile, "Error during test file I/O: " + e.getMessage());
            return false;
        } catch (Exception e) {
            // Elkapja a Map futása közben keletkező nem várt hibákat is (pl. NullPointerException)
            System.err.println("  FATAL Error during Map command execution for " + testDir.getFileName() + ": " + e.getMessage());
            e.printStackTrace(System.err); // Kiírja a stack trace-t a TestRunner konzoljára
            writeErrorToActualResult(actualFile, "FATAL Error during Map command execution: " + e.toString() + "\nCheck TestRunner console for stack trace.");
            return false;
        }
    }

    /**
     * Futtatja a parancsokat az input fájlból egy új Map példányon,
     * és a System.out kimenetet az output fájlba irányítja.
     *
     * @param inputFile A bemeneti parancsokat tartalmazó fájl.
     * @param outputFile Az eredményfájl, ahova a kimenetet írjuk.
     * @throws IOException Ha fájl olvasási/írási hiba történik.
     * @throws Exception Ha a Map parancs feldolgozása közben hiba történik.
     */
    private void runMapCommandsAndCaptureOutput(Path inputFile, Path outputFile) throws IOException {
        PrintStream originalOut = System.out; // Eredeti System.out mentése
        Map gameMap = new Map(); // Új, tiszta Map példány minden teszthez

        // Try-with-resources biztosítja a streamek lezárását
        try (FileOutputStream fos = new FileOutputStream(outputFile.toFile()); // Kimeneti fájl stream (felülírja, ha létezik)
             PrintStream printStreamToFile = new PrintStream(fos, true, StandardCharsets.UTF_8); // PrintStream a fájlhoz
             BufferedReader reader = Files.newBufferedReader(inputFile, StandardCharsets.UTF_8)) { // Bemeneti fájl olvasó

            System.setOut(printStreamToFile); // <<< Átirányítjuk a System.out-ot a fájlba >>>

            String line;
            while ((line = reader.readLine()) != null) {
                // Üres sorok vagy kommentek kihagyása (opcionális)
                String trimmedLine = line.trim();
                if (!trimmedLine.isEmpty() && !trimmedLine.startsWith("#")) {
                    try {
                        gameMap.command(trimmedLine); // Futtatjuk a parancsot a Map példányon
                    } catch (Exception e) {
                        // Ha egy parancs hibát dob, azt is a kimeneti fájlba írjuk
                        System.out.println("!!! EXCEPTION while processing command: " + trimmedLine);
                        e.printStackTrace(System.out); // Ez most a fájlba írja a stack trace-t
                        // Dönthetünk úgy, hogy itt megáll a teszt futása, vagy folytatódik
                        // A jelenlegi kód folytatja a következő paranccsal.
                        // Ha meg kell állítani: throw new RuntimeException("Error processing command: " + trimmedLine, e);
                    }
                } else if (!trimmedLine.isEmpty()) {
                    // Opcionális: Kiírjuk a kommenteket is a result fájlba, hogy könnyebb legyen követni
                    System.out.println(line);
                }
            }
            // A PrintStream és FileOutputStream automatikusan lezáródik a try-with-resources végén

        } finally {
            System.setOut(originalOut); // <<< Visszaállítjuk az eredeti System.out-ot MINDENKÉPPEN >>>
        }
    }


    private boolean compareFiles(Path file1, Path file2) {
        try {
            List<String> lines1 = Files.readAllLines(file1, StandardCharsets.UTF_8);
            List<String> lines2 = Files.readAllLines(file2, StandardCharsets.UTF_8);

            // Sorvégi üres sorok eltávolítása (Java 20 kompatibilis módon)
            // Ellenőrizzük, hogy a lista nem üres, mielőtt indexelnénk
            while (!lines1.isEmpty() && lines1.get(lines1.size() - 1).isBlank()) {
                lines1.remove(lines1.size() - 1); // Utolsó elem eltávolítása index alapján
            }
            while (!lines2.isEmpty() && lines2.get(lines2.size() - 1).isBlank()) {
                lines2.remove(lines2.size() - 1); // Utolsó elem eltávolítása index alapján
            }

            // Listák összehasonlítása (méret és tartalom)
            return lines1.equals(lines2);

        } catch (IOException e) {
            System.err.println("  Error comparing files '" + file1.getFileName() + "' and '" + file2.getFileName() + "': " + e.getMessage());
            return false; // Hiba esetén nem tekintjük egyezőnek
        } catch (IndexOutOfBoundsException e) {
            // Ez elvileg nem fordulhat elő a !isEmpty() ellenőrzés miatt, de biztonság kedvéért
            System.err.println("  Error comparing files (Index issue): " + e.getMessage());
            return false;
        }
    }

    private void printTestFailureDetails(Path testDir) {
        Path actualFile = testDir.resolve(ACTUAL_RESULT_FILE);
        Path expectedFile = testDir.resolve(EXPECTED_RESULT_FILE);

        System.out.println("--- Actual Result (" + ACTUAL_RESULT_FILE + ") ---");
        printFileContent(actualFile);

        System.out.println("\n--- Expected Result (" + EXPECTED_RESULT_FILE + ") ---");
        printFileContent(expectedFile);
        System.out.println("--- End of Details ---");
    }

    private void printFileContent(Path file) {
        if (!Files.isReadable(file)) {
            System.out.println("[Error: Cannot read file: " + file + "]");
            return;
        }
        try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            String line;
            int lineCount = 0;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                lineCount++;
                // Opcionális: Limitelhetjük a kiírt sorok számát, ha túl hosszúak
                // if (lineCount > 100) {
                //     System.out.println("[... output truncated ...]");
                //     break;
                // }
            }
            if (lineCount == 0) {
                System.out.println("[File is empty]");
            }
        } catch (IOException e) {
            System.out.println("[Error reading file " + file.getFileName() + ": " + e.getMessage() + "]");
        }
    }

    /**
     * Segédfüggvény, ami hiba esetén megpróbálja a hibát kiírni a result.txt-be.
     */
    private void writeErrorToActualResult(Path actualFile, String errorMessage) {
        try {
            Files.writeString(actualFile, errorMessage, StandardCharsets.UTF_8);
        } catch (IOException ioex) {
            System.err.println("  Additionally, failed to write error message to " + actualFile + ": " + ioex.getMessage());
        }
    }
}
