package org.example;

public class Methods {

    public static String transformName(String name) {
        return name + " transformed";
    }

    public static void printTransformName(String name) {
        System.out.println(transformName(name));
    }
}
