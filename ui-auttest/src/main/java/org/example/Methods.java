package org.example;

import java.util.ArrayList;
import java.util.List;

public class Methods {

    // Задача 1
    public static boolean isEven(int n) {
        return n % 2 == 0;
    }

    // Задача 2
    public static String checkAccess(int age) {
        if (age > 18) {
            return "Allowed";
        } else {
            return "Denied";
        }
    }


    // Задача 3
    public static boolean isPositive(int n) {
        return n >= 0 ? true : false;
    }

    // Задача 4
    public static String getGrade(int score) {
        if (score >= 0 && score <= 20) {
            return "E";
        } else if (score <= 40) {
            return "D";
        } else if (score <= 60) {
            return "C";
        } else if (score <= 80) {
            return "B";
        } else if (score <= 100) {
            return "A";
        }

        return "Error";
    }

    // Задача 5
    public static String blastOff(int start) {
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

    // Задача 6
    public static int sumToN(int n) {
        int sum = 0;

        for (int i = 1; i <= n; i++) {
            sum += i;
        }

        return sum;
    }

    // Задача 7
    public static boolean hasBug(String[] messages) {
        for (String message : messages) {
            if (message != null && message.equalsIgnoreCase("Bug")) {
                return true;
            }
        }

        return false;
    }

    // Задача 8
    public static String getEvenInRange(int start, int end) {
        StringBuilder result = new StringBuilder();

        for (int i = start; i <= end; i++) {
            if (isEven(i)) {
                if (result.length() > 0) {
                    result.append(" ");
                }

                result.append(i);
            }
        }

        return result.toString();
    }

    // Задача 9
    public static int findMax(int[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Массив не должен быть пустым");
        }

        int max = arr[0];

        for (int number : arr) {
            if (number > max) {
                max = number;
            }
        }

        return max;
    }

    // Задача 10
    public static String[] reverse(String[] arr) {
        String[] reversed = new String[arr.length];

        for (int i = 0; i < arr.length; i++) {
            reversed[i] = arr[arr.length - 1 - i];
        }

        return reversed;
    }

    // Задача 11
    public static double calcAverage(List<Integer> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("Список не должен быть пустым");
        }

        int sum = 0;

        for (Integer number : list) {
            sum += number;
        }

        return (double) sum / list.size();
    }

    // Задача 12
    public static List<String> removeSpecificName(
            List<String> list,
            String nameToRemove
    ) {
        List<String> result = new ArrayList<>();

        for (String name : list) {
            if (!name.equals(nameToRemove)) {
                result.add(name);
            }
        }

        return result;
    }
}
