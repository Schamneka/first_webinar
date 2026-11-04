import org.example.Methods;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;

import java.util.Random;
import java.util.stream.Stream;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

@Tag("autotests")
public class MethodTests {

    private static final Random RANDOM = new Random();

    @BeforeEach
    void beforeTest() {
        System.out.println("========================Test method start");
    }

    @AfterEach
    void afterTest() {
        System.out.println("Test method end");
        System.out.println("========================");
    }


    // Задача 1 (isEven)
    @RepeatedTest(value = 10, name = "Задача 1: isEven, запуск {currentRepetition}/{totalRepetitions}")
    void isEvenTest() {
        int number = randomInt(-1_000, 1_001);
        boolean expected = (number % 2 == 0);

        System.out.println("Задача 1: " + Methods.isEven(number));

        assertEquals(expected, Methods.isEven(number));
    }

    //Задача 2 (checkAccess)
    @RepeatedTest(value = 10, name = "Задача 2: checkAccess, запуск {currentRepetition}/{totalRepetitions}")
    void checkAccessTest() {
        int ageCheck = randomInt(0, 101);

        String expected = ageCheck > 18 ? "Allowed" : "Denied";
        String actual = Methods.checkAccess(ageCheck);

        System.out.println("Задача 2: " + actual);

        assertEquals(expected, actual);
    }

    //Задача 3 (isPositive)
    @RepeatedTest(value = 10, name = "Задача 3: isPositive, запуск {currentRepetition}/{totalRepetitions}")
    void isPositiveTest() {
        int sign = randomInt(-1_000, 1_001);
        boolean expected = (sign >= 0);

        System.out.println("Задача 3: " + Methods.isPositive(sign));

        assertEquals(expected, Methods.isPositive(sign));
    }

    //Задача 4 (getGrade)
    @RepeatedTest(value = 10, name = "Задача 4: getGrade, запуск {currentRepetition}/{totalRepetitions}")
    void getGradeTest() {
        int point = randomInt(-101, 101);
        String expected = expectedGrade(point);

        System.out.println("Задача 4: " + Methods.getGrade(point));

        assertEquals(expected, Methods.getGrade(point));
    }

    //Задача 5 (blastOff)
    @RepeatedTest(value = 10, name = "Задача 5: blastOff, запуск {currentRepetition}/{totalRepetitions}")
    void blastOffTest() {
        int start = randomInt(1, 11);
        String expected = expectedBlastOff(start);

        System.out.println("Задача 5: " + Methods.blastOff(start));

        assertEquals(expected, Methods.blastOff(start));
    }

    //Задача 6 (sumToN)
    @RepeatedTest(value = 10, name = "Задача 6: sumToN, запуск {currentRepetition}/{totalRepetitions}")
    void sumToNTest() {
        int sumOfAll = randomInt(1, 101);
        int expected = expectedSumToN(sumOfAll);

        System.out.println("Задача 6: " + Methods.sumToN(sumOfAll));

        assertEquals(expected, Methods.sumToN(sumOfAll));
    }

    //Задача 7 (hasBug)
    @RepeatedTest(value = 10, name = "Задача 7: hasBug, запуск {currentRepetition}/{totalRepetitions}")
    void hasBugTest() {
        String[] messages = generateMessagesWithOptionalBug();
        boolean expected = containsBug(messages);

        System.out.println("Задача 7: " + Methods.hasBug(messages));

        assertEquals(expected, Methods.hasBug(messages));
    }

    //Задача 8 (getEvenInRange)
    @RepeatedTest(value = 10, name = "Задача 8: getEvenInRange, запуск {currentRepetition}/{totalRepetitions}")
    void getEvenInRangeTest() {
        int start = randomInt(-30, 31);
        int end = randomInt(start, 51);
        String expected = expectedEvenInRange(start, end);

        System.out.println("Задача 8: " + Methods.getEvenInRange(start, end));

        assertEquals(expected, Methods.getEvenInRange(start, end));
    }

    //Задача 9 (findMax)
    @ParameterizedTest(name = "Задача 9: findMax, набор №{index}")
    @MethodSource("randomArraysProvider")
    void findMaxTest(int[] array) {
        // null и пустой массив должны привести к исключению
        if (array == null || array.length == 0) {
            assertThrows(IllegalArgumentException.class, () -> Methods.findMax(array));
            return;
        }

        int expected = expectedFindMax(array);

        System.out.println("Задача 9: " + Methods.findMax(array));

        assertEquals(expected, Methods.findMax(array));
    }

    static Stream<int[]> randomArraysProvider() {
        return Stream.of(
                generateArray(10, 1, 100),     // положительные числа
                generateArray(15, -100, -1),   // отрицательные числа
                generateArray(20, -50, 50),    // смешанные числа
                generateArray(1, -1000, 1000), // один элемент
                generateArray(100, -10_000, 10_001), // большой массив
                new int[]{},                   // пустой массив — исключение
                (int[]) null                   // null — исключение
        );
    }

    //Задача 10 (reverse)
    @ParameterizedTest(name = "Задача 10: reverse, набор №{index}")
    @MethodSource("randomStringArraysProvider")
    void reverseTest(String[] array) {
        String[] expected = expectedReverse(array);

        String[] actual = Methods.reverse(array);

        System.out.println("Задача 10: " + Arrays.toString(actual));

        assertArrayEquals(expected, actual);
    }


    static Stream<Arguments> randomStringArraysProvider() {
        return Stream.of(
                Arguments.of((Object) generateStringArray(10, 3, 10)), // обычный массив строк
                Arguments.of((Object) generateStringArray(1, 1, 5)),   // один элемент
                Arguments.of((Object) generateStringArray(0, 0, 0)),   // пустой массив — вернёт пустой
                Arguments.of((Object) generateStringArray(20, 0, 12))  // строки случайной длины, включая пустые
        );
    }

    //Задача 11 (calcAverage)
    @ParameterizedTest(name = "Задача 11: calcAverage, набор №{index}")
    @MethodSource("randomIntListsProvider")
    void calcAverageTest(List<Integer> list) {
        // null и пустой список должны привести к исключению
        if (list == null || list.isEmpty()) {
            assertThrows(IllegalArgumentException.class, () -> Methods.calcAverage(list));
            return;
        }

        double expected = expectedAverage(list);

        System.out.println("Задача 11: " + Methods.calcAverage(list));

        assertEquals(expected, Methods.calcAverage(list), 1e-9);
    }

    static Stream<List<Integer>> randomIntListsProvider() {
        return Stream.of(
                generateList(10, 1, 100),       // положительные числа
                generateList(15, -100, -1),     // отрицательные числа
                generateList(20, -50, 50),      // смешанные числа
                generateList(1, -1000, 1000),   // один элемент — среднее равно ему самому
                generateList(100, -10_000, 10_001), // большой список
                new ArrayList<>(),              // пустой список — исключение
                null                            // null — исключение
        );
    }

    //Задача 12 (removeSpecificName)
    @ParameterizedTest(name = "Задача 12: removeSpecificName, набор №{index}")
    @MethodSource("randomNameListsProvider")
    void removeSpecificNameTest(List<String> list, String nameToRemove) {
        List<String> expected = expectedRemove(list, nameToRemove);

        List<String> actual = Methods.removeSpecificName(list, nameToRemove);

        System.out.println("Задача 12: " + actual);

        assertEquals(expected, actual);
    }

    static Stream<Arguments> randomNameListsProvider() {
        String[] names = {"Аня", "Боря", "Вика", "Гоша", "Дима"};

        return Stream.of(
                // список из 10 имён, удаляем случайное из пула
                Arguments.of(generateNameList(names, 10), names[RANDOM.nextInt(names.length)]),
                // удаляемого имени в списке может не быть
                Arguments.of(generateNameList(names, 5), "Несуществующее имя"),
                // удаляемое имя занимает все позиции
                Arguments.of(generateNameList(new String[]{"Аня"}, 7), "Аня"),
                // один элемент
                Arguments.of(generateNameList(names, 1), names[RANDOM.nextInt(names.length)]),
                // пустой список — должен вернуть пустой
                Arguments.of(new ArrayList<String>(), "Аня"),
                // null — метод упадёт с NullPointerException
                Arguments.of((List<String>) null, "Аня")
        );
    }

    private static int randomInt(int origin, int bound) {
        return RANDOM.nextInt(bound - origin) + origin;
    }

    private static int[] generateArray(int size, int min, int max) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = RANDOM.nextInt(max - min + 1) + min;
        }
        return array;
    }

    private static List<Integer> generateList(int size, int min, int max) {
        List<Integer> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(RANDOM.nextInt(max - min + 1) + min);
        }
        return list;
    }

    private static String[] generateStringArray(int size, int minLen, int maxLen) {
        String[] array = new String[size];
        for (int i = 0; i < size; i++) {
            array[i] = generateRandomString(minLen, maxLen);
        }
        return array;
    }

    private static String generateRandomString(int minLen, int maxLen) {
        int length = RANDOM.nextInt(maxLen - minLen + 1) + minLen;
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append((char) (RANDOM.nextInt('z' - 'a' + 1) + 'a'));
        }
        return sb.toString();
    }

    private static List<String> generateNameList(String[] pool, int size) {
        List<String> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(pool[RANDOM.nextInt(pool.length)]);
        }
        return list;
    }

    private static String[] generateMessagesWithOptionalBug() {
        String[] possible = {"Info", "Warning", "Success", "Error", "Completed", "Bug", "bug", "BUG"};
        int len = RANDOM.nextInt(6) + 3;
        String[] messages = new String[len];
        for (int i = 0; i < len; i++) messages[i] = possible[RANDOM.nextInt(possible.length)];
        return messages;
    }

    private static int expectedFindMax(int[] array) {
        return Arrays.stream(array).max().orElseThrow();
    }

    private static String[] expectedReverse(String[] array) {
        String[] reversed = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            reversed[i] = array[array.length - 1 - i];
        }
        return reversed;
    }

    private static double expectedAverage(List<Integer> list) {
        int sum = 0;
        for (Integer number : list) {
            sum += number;
        }
        return (double) sum / list.size();
    }

    private static List<String> expectedRemove(List<String> list, String nameToRemove) {
        List<String> result = new ArrayList<>();
        for (String name : list) {
            if (!name.equals(nameToRemove)) {
                result.add(name);
            }
        }
        return result;
    }

    private static boolean containsBug(String[] messages) {
        for (String word : messages) {
            if ("Bug".equalsIgnoreCase(word)) return true;
        }
        return false;
    }

    private static String expectedGrade(int score) {
        if (score >= 0 && score <= 20) return "E";
        if (score >= 21 && score <= 40) return "D";
        if (score >= 41 && score <= 60) return "C";
        if (score >= 61 && score <= 80) return "B";
        if (score >= 81 && score <= 100) return "A";
        return "Error";
    }

    public static String expectedBlastOff(int start) {
        StringBuilder result = new StringBuilder();

        for (int i = start; i >= 1; i--) {
            if (result.length() > 0)
                result.append(" ");

            result.append(i);
        }

        if (result.length() > 0)
            result.append(" ");

        result.append("Поехали!");

        return result.toString();
    }

    public static int expectedSumToN(int sumOfAll) {
        int sum = 0;

        for (int i = 1; i <= sumOfAll; i++) {
            sum += i;
        }

        return sum;
    }

    private static String expectedEvenInRange(int start, int end) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i <= end; i++) {
            if (i % 2 == 0) {
                if (sb.length() > 0) sb.append(" ");
                sb.append(i);
            }
        }
        return sb.toString();
    }
}


